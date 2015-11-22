package jeu.mode;

import elements.particular.players.Pfire1;
import menu.screens.Menu;
import jeu.CSG;
import jeu.Physic;
import jeu.Stats;
import jeu.Strings;
import jeu.mode.extensions.Buttons;
import jeu.mode.extensions.DesktopTests;
import jeu.mode.extensions.Score;
import jeu.mode.extensions.ScreenShake;
import jeu.mode.extensions.Transition;
import jeu.mode.extensions.Tutorial;
import behind.shaders.Bloom;
import assets.AssetMan;
import behind.SoundMan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import elements.generic.enemies.Enemy;
import elements.generic.weapons.Weapon;
import elements.particular.players.Player;
import elements.particular.bonuses.Bonus;
import elements.particular.bonuses.XP;
import elements.particular.other.WaveEffect;
import elements.particular.particles.Particles;

/**
 * Classe principale gerant le mode infini du jeu
 * @author Julien
 */
public class EndlessMode implements Screen {
	
	public static final Vector2 RESOLUTION = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	private Game game;
	private GL20 gl;
	private static SpriteBatch batch = CSG.batch;
	public static Bloom bloom = CSG.bloom;
	public static boolean alternate = true, pause = false, scoreSent = false, triggerStop = false, lost = false, effetBloom = false, xpAdded = false, konamiCode = false, invicibility = false, freeze = false, slowDown = false, invoque = true, started = false;
	private static Player ship;
	public static float now = 0, bloomOriginalIntensity = 1, delta = 0, timeStopBonus = 0, delta15 = 0, deltaDiv3, deltaDiv2, delta2, deltaU, deltaMicroU, UnPlusDelta, unPlusDelta2, unPlusDelta3, delta25, delta4, originalDelta = 0, timeSinceLost = 0;
	public static final int X_CHRONO = CSG.widthDiv10/2 - CSG.halfWidth, FONT_HEIGHT = CSG.heightDiv10/3, TIER_WIDTH_JAUGE =  CSG.screenWidth/18, HEIGHT_JAUGE = CSG.height/75;
	public static int difficulty = 1, freezeBonus = 0, bombs = 0, fps, perf = 3, menuX = 0, menuY = 0, oneToFour = 1;
	public static final OrthographicCamera cam = new OrthographicCamera(CSG.screenWidth, CSG.height);
	private int advice = 0;
	private static Matrix4 tmpCombined; 
	private boolean toastSent = false;
	private float justTouched = 0;
	public static final float STOP = 3;
	private Tutorial tuto = new Tutorial();
	public static Transition transition = new Transition();
	public static final ShaderProgram greyscale = CSG.SHADER.getShader("greyscale.glsl"),
            original = SpriteBatch.createDefaultShader(),
            timeStop = CSG.SHADER.getShader("timestop.glsl"), touched = CSG.SHADER.getShader("touched.glsl");

	public EndlessMode(Game game, SpriteBatch batch, int level, int cheat) {
		Gdx.input.setCatchBackKey(true);
		this.game = game;
		ship = new Pfire1();
		difficulty = level;
		init();
		gl = Gdx.graphics.getGL20();
		gl.glViewport(0, 0, CSG.screenWidth, CSG.height);
		bloom = CSG.bloom;
		batch.setShader(original);
	}

	public static void init() {
		CSG.talkToTheWorld.loadInterstitial();
		if (CSG.bloom != null)		bloom.setBloomIntesity(CSG.profile.bloomIntensity);
		else						bloom = new Bloom();
		ship.init();
		CSG.talkToTheWorld.showAds(false); 
		cam.position.set(CSG.halfWidth, CSG.halfHeight, 0);
		scoreSent = false;
		xpAdded = false;
		pause = false;
		lost = false;
		now = 0;
		bombs = 0;
		freezeBonus = 0;
		timeSinceLost = 0;
		timeStopBonus = 0;
		CSG.reset();
		Score.init();
		Enemy.clear();
		Weapon.clear();
		Buttons.init();
		transition.reset();
		SoundMan.playMusic();
		triggerStop = false;
		cam.position.z = 1;
		CSG.profilManager.persist();
		started = false;
		CSG.gm.orchestrator.reset();
	}

	@Override
	public void render(float delta) {
		delta = Math.min(delta, 0.1f);
		
		if (!started) {
			delta = 0;
			CSG.talkToTheWorld.showAds(false);
			CSG.talkToTheWorld.displayInterstitial();
			if (Gdx.input.justTouched()) {
				SoundMan.playMusic();
				started = true;
			}
		}
		
		if (slowDown) {
			int cpt = 0;
			while (cpt < 500000000) {
				cpt++;
				delta = 1f / 60f;
			}
		}
		
		alternate();
		cam();
		
		if (freeze)									delta /= 20;
		if (Gdx.input.isTouched() && !lost)			ship.mouvements();
		if (Gdx.app.getVersion() == 0)				DesktopTests.debug();
		
		originalDelta = delta;
		
		bloomActive();
		
		batch.begin();
		if (lost) {
			batch.setShader(greyscale);
			Particles.background(batch);
			batch.setShader(original);
		} else
			Particles.background(batch);
		if (triggerStop) {
			batch.setShader(timeStop);
			timeStop.setUniformf("iGlobalTime", 0.5f);
		}
		if (!pause && !freeze) {
			EndlessMode.delta = delta;
			if (!Gdx.input.isTouched())				EndlessMode.delta = delta / 7;
			majDeltas(Gdx.input.isTouched());
			if (!lost && !triggerStop) {
				transition.act();
				affichageNonPerdu();
			} else {
				if (triggerStop) {
					affichageEtUpdateStop();
				} else {
					if (lost && !scoreSent && !konamiCode){
						bloom.setBloomIntesity(-1f);
						final int monScore = Score.getRoundScore();
						switch (difficulty) {
						case 1:		CSG.talkToTheWorld.submitScore("CgkIrsqv7rIVEAIQKQ", monScore );	break;
						case 2:		CSG.talkToTheWorld.submitScore("CgkIrsqv7rIVEAIQKg", monScore );	break;
						case 3:		CSG.talkToTheWorld.submitScore("CgkIrsqv7rIVEAIQKw", monScore );	break;
						case 4:		CSG.talkToTheWorld.submitScore(Strings.LVL4LB, monScore );			break;
						}
						scoreSent = true;
					}
					batch.setShader(greyscale);
				}
				
				affichagePerdu();
				if (!triggerStop)
					batch.setShader(original);
			}					 
			update();
		} else { // D O N C   E N   P A U S E
			if (Gdx.input.isKeyPressed(Keys.BACK) && (now > justTouched + .1))
				Buttons.goToMenu(game);
			affichagePerdu();
			if (Gdx.input.justTouched()) {
				pause = false;
				justTouched = now;
				if (Physic.isPointInRect(Gdx.input.getX() + EndlessMode.cam.position.x / 2, CSG.height - Gdx.input.getY(), 0, Menu.BUTTON_HEIGHT * 1.1f, CSG.screenWidth, Menu.BUTTON_HEIGHT * 3)) {
					Buttons.goToMenu(game);
				}
			}
		}
		WaveEffect.draw(batch);
		if (triggerStop)					stopActivated();
		if (CSG.profile.isFirstTime())		tuto.act(batch);
		Particles.foreground(batch);
		batch.end();
		bloom.render();
		
		
		batch.begin();
		if (pause)
			Buttons.backButton(batch, game);
		if (lost && !triggerStop)
			drawFinalScoreAndAdvices(batch);
		Score.draw(batch, lost);
		batch.end();
		
		now += EndlessMode.delta;
		ScreenShake.act();
		Enemy.deadBodiesEverywhere();
	}

	private void alternate() {
		alternate = !alternate;
		if (++oneToFour > 4)
			oneToFour = 1;
	}

	private void stopActivated() {
		ship.draw(batch);
	}

	public static void cam() {
		cam.update();
		tmpCombined = cam.combined;
		if (tmpCombined != null)
			batch.setProjectionMatrix(tmpCombined);
	}

	public static void majDeltas(boolean touched) {
		if (!touched)
			delta /= 5;
		delta15 = delta * 15;
		deltaDiv3 = delta / 3;
		deltaDiv2 = delta / 2;
		delta2 = delta * 2;
		deltaU = delta * Stats.U;
		deltaMicroU =  Stats.uDiv75 * delta;
		UnPlusDelta = 1 + delta;
		unPlusDelta2 = 1 + delta2;
		unPlusDelta3 = UnPlusDelta + delta2;
		delta25 = delta * 25;
		delta4 = delta * 4;
		
		fps = Gdx.graphics.getFramesPerSecond();
		perf = fps / 10;
	}

	private void affichageEtUpdateStop() {
		timeStopBonus -= delta;
		batch.draw(AssetMan.red, (Player.POS.x + ship.halfWidth) - (TIER_WIDTH_JAUGE * timeStopBonus)/2, Player.POS.y - HEIGHT_JAUGE * 3, TIER_WIDTH_JAUGE * timeStopBonus, HEIGHT_JAUGE);
		if (timeStopBonus < 0) {
			triggerStop = false;
			transition.activate(20, Transition.TIME_STOP);
			if (freezeBonus > 0)
				timeStopBonus += STOP;
		}
	}

	private void bloomActive() {
		if (triggerStop)		bloom.setBloomIntesity(CSG.profile.bloomIntensity + (timeStopBonus*2));
		else					ScreenShake.bloomEffect();
		bloom.capture();
	}

	private static void mettrePause() {
		pause = true;
		CSG.profilManager.persist();
	}
	
	private void drawFinalScoreAndAdvices(SpriteBatch batch) {
		timeSinceLost += originalDelta;
		
		if (timeSinceLost < 0.7f)
			return;
		
		if (!xpAdded && !konamiCode) {
			CSG.profile.addXp(Score.getRoundScore());
			xpAdded = true;
		}
		Buttons.initAndDrawButtons(batch);
		
		if (Score.score < 1000) {
			if (advice == 0)
				advice = (int) (1 + Math.random() * 5);
			switch (4) {
				case 3:		displayAdvice(Strings.ADVICE3);									break;
				case 4:		displayAdvice(Strings.ADVICE4, AssetMan.bomb, batch);			break;
				case 5:		displayAdvice(Strings.ADVICE5, AssetMan.stopBonus, batch);		break;
			}
		}
		batch.setColor(CSG.gm.palette().black);
		batch.draw(AssetMan.dust, 0, CSG.halfHeight - CSG.fontsDimensions.getHeight(CSG.menuFont, Score.strScore) * 2, CSG.screenWidth, CSG.fontsDimensions.getHeight(CSG.menuFont, Score.strScore) * 6);
		batch.setColor(CSG.gm.palette().white);
		CSG.menuFont.draw(batch, Strings.DEAD,
				((cam.position.x - CSG.halfWidth)) + ((CSG.halfWidth - (CSG.fontsDimensions.getWidth(CSG.menuFont, Strings.DEAD)) / 2)),
				CSG.halfHeight + CSG.fontsDimensions.getHeight(CSG.menuFontSmall, Strings.DEAD) * 3);

		CSG.menuFont.draw(batch, Score.strScore, ((cam.position.x - CSG.halfWidth)) + ((CSG.halfWidth - (CSG.fontsDimensions.getWidth(CSG.menuFont, Score.strScore)) / 2)),
				CSG.halfHeight);

		Buttons.drawUpgradeAndTwitter(batch);
	}

	private float prevDelta;
	private void affichagePerdu() {
		prevDelta = delta;
		delta = 0;
		stopActivated();
		Enemy.draw(batch);
		delta = prevDelta;
		Particles.draw(batch);
		if (!lost)
			XP.act(Bonus.XP_LIST, batch);
		Particles.drawImpacts(batch);
		Weapon.affichage(batch);
		ui();
	}

	public static void effetBloom() {
		effetBloom = true;
		bloomOriginalIntensity = 50;
	}

	private void ui() {
		
		if (CSG.profile.manualBonus) {
			switch(freezeBonus) {
			case 2:	batch.draw(AssetMan.stopBonus, cam.position.x + X_CHRONO + Bonus.WIDTH + Bonus.HALF_WIDTH, FONT_HEIGHT * 2, Bonus.WIDTH, Bonus.WIDTH);
			case 1:	batch.draw(AssetMan.stopBonus, cam.position.x + X_CHRONO, FONT_HEIGHT * 2, Bonus.WIDTH, Bonus.WIDTH);
			case 0:
			}
			switch(bombs) {
			case 3:	batch.draw(AssetMan.bomb, CSG.halfWidth + cam.position.x + X_CHRONO + Bonus.WIDTH * 3 + Bonus.HALF_WIDTH * 3, Bonus.HALF_WIDTH, Bonus.WIDTH, Bonus.WIDTH);
			case 2:	batch.draw(AssetMan.bomb, CSG.halfWidth + cam.position.x + X_CHRONO + Bonus.WIDTH * 2 + Bonus.HALF_WIDTH * 2, Bonus.HALF_WIDTH, Bonus.WIDTH, Bonus.WIDTH);
			case 1:	batch.draw(AssetMan.bomb, CSG.halfWidth + cam.position.x + X_CHRONO + Bonus.WIDTH * 1 + Bonus.HALF_WIDTH * 1, Bonus.HALF_WIDTH, Bonus.WIDTH, Bonus.WIDTH);
			case 0:
			}
		}
	}

	private void affichageNonPerdu() {
		stopActivated();
		Bonus.drawAndMove(batch, ship);
		Enemy.affichageEtMouvement(batch);
		Particles.drawImpacts(batch);
		Particles.draw(batch);
		Weapon.drawAndMove(batch, ship);
		ui();
	}

	private void update() {
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.POWER) || Gdx.input.isKeyPressed(Keys.HOME) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			justTouched = now;
			mettrePause();
		}
		if (!lost) {
			mouvement();
			if (alternate) {
				if (invoque)				CSG.gm.orchestrator.invoke(Score.score);
				if (!triggerStop)		Physic.collisionsTest(ship);
				Score.act(now, lost, triggerStop);
 			}
			if (!freeze)	
				ship.shot();
		} else { // Donc si on a perdu
			if (toastSent) {
				CSG.talkToTheWorld.toast("You might want to check the highscore menu !");
				toastSent = false;
			}
			Buttons.testClick();
		}
	}

	public static void mouvement() {
		if (Gdx.input.justTouched())
			justeTouche();
		else if (Gdx.input.isTouched()) {
			menuX = Gdx.input.getX();
			menuY = CSG.height - Gdx.input.getY();
		} else 
			pasTouche();
	}

	private static void pasTouche() {
		Buttons.removeBack();
		if (CSG.profile.manualBonus)	{
			if (freezeBonus > 0 && !triggerStop) batch.draw(AssetMan.stopBonus,(menuX - Bonus.DISPLAY_WIDTH) + (cam.position.x-CSG.halfWidth) - ship.halfWidth, menuY, Bonus.DISPLAY_WIDTH, Bonus.DISPLAY_WIDTH);
			else batch.draw(AssetMan.stopBonusGrey,(menuX - Bonus.DISPLAY_WIDTH) + (cam.position.x-CSG.halfWidth) - ship.halfWidth, menuY, Bonus.DISPLAY_WIDTH, Bonus.DISPLAY_WIDTH);
			
			if (bombs > 0) batch.draw(AssetMan.bomb, (menuX + Bonus.DISPLAY_WIDTH * 1.5f) + (cam.position.x-CSG.halfWidth) - ship.halfWidth, menuY, Bonus.DISPLAY_WIDTH,Bonus.DISPLAY_WIDTH);
			else batch.draw(AssetMan.bombGrey, (menuX + Bonus.DISPLAY_WIDTH * 1.5f) + (cam.position.x-CSG.halfWidth) - ship.halfWidth, menuY, Bonus.DISPLAY_WIDTH,Bonus.DISPLAY_WIDTH);
			
		}
	}

	private static void activateStop() {
		SoundMan.halfVolume();
		triggerStop = true;
		Particles.addChronoGenerator();
		WaveEffect.add(Player.xCenter, Player.yCenter, CSG.gm.palette().green);
	}

	private static void justeTouche() {
		if (freezeBonus > 0 && Physic.isPointInRect(Gdx.input.getX(), CSG.height - Gdx.input.getY(), (menuX - Bonus.DISPLAY_WIDTH * 1.5f) - ship.halfWidth, menuY - Bonus.DISPLAY_WIDTH * 0.5f, Bonus.DISPLAY_WIDTH * 2, Bonus.DISPLAY_WIDTH * 2)) {
			activateStop();
			freezeBonus--;
		} else if (bombs > 0 && Physic.isPointInRect(Gdx.input.getX(), CSG.height - Gdx.input.getY(), (menuX + Bonus.DISPLAY_WIDTH) - ship.halfWidth, menuY - Bonus.DISPLAY_WIDTH * 0.5f, Bonus.DISPLAY_WIDTH * 2, Bonus.DISPLAY_WIDTH * 2)) {
			Enemy.bombe();
			bombs--;
		}
	}

	@Override	public void resize(int width, int height) {			CSG.profilManager.persist();			}
	@Override	public void hide() {								CSG.profilManager.persist();			}
	@Override	public void resume() {								CSG.assetMan.reload();					}
	@Override	public void dispose() {																		}
	@Override	public void pause() {																		}
	@Override	public void show() {																		}

	public static void addBonusStop() {
		if (CSG.profile.manualBonus) {
			freezeBonus++;
			if (freezeBonus > 2) {
				freezeBonus = 2;
				activateStop();
			}
		} else
			activateStop();
		timeStopBonus = STOP;
	}

	public static void ajoutBombe() {
		if (CSG.profile.manualBonus && bombs < 3)	bombs++;
		else 										Enemy.bombe();
	}
	
	private void displayAdvice(String s, TextureRegion tr, SpriteBatch batch) {
		CSG.menuFontSmall.draw(batch, s, ((cam.position.x-CSG.halfWidth)) + ((CSG.halfWidth - CSG.fontsDimensions.getWidth(CSG.menuFontSmall, s) / 2)),
				CSG.halfHeight * 1.5f - CSG.fontsDimensions.getHeight(CSG.menuFontSmall, s) * 4);
		batch.draw(tr, ((cam.position.x-CSG.halfWidth) + CSG.halfWidth) - Bonus.DISPLAY_WIDTH/2, CSG.halfHeight * 1.5f, Bonus.DISPLAY_WIDTH, Bonus.DISPLAY_WIDTH);
	}

	private void displayAdvice(String s) {
		CSG.menuFontSmall.draw(batch, s,
                ((cam.position.x-CSG.halfWidth)) + ((CSG.halfWidth - CSG.fontsDimensions.getWidth(CSG.menuFontSmall, s) / 2)),
                CSG.halfHeight - CSG.fontsDimensions.getHeight(CSG.menuFontSmall, s) * 4);
	}

	public static void lost() {
		if (!invicibility) {
			lost = true;
			Score.lost(!konamiCode);
			timeSinceLost = 0;
		}
	}
	
	public static void reset() {
		Particles.clear();
		menuX = (int) Player.POS.x;
		menuY = (int) Player.POS.y;
		triggerStop = false;
		pause = false;
	}

	public static void rotate(Vector2 dir, float angle) {
		if (alternate)
			dir.rotate(angle);
	}

}