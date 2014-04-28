package jeu;
import java.util.Random;

import jeu.dynamicTutorial.TemporaryText;
import jeu.dynamicTutorial.Tutorial;
import menu.screens.Menu;
import menu.tuto.OnClick;
import menu.ui.Bouton;
import shaders.Bloom;
import assets.AssetMan;
import assets.SoundMan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import elements.generic.Player;
import elements.generic.enemies.Enemy;
import elements.generic.enemies.Progression;
import elements.generic.enemies.individual.bosses.BossMine;
import elements.generic.enemies.individual.bosses.BossQuad;
import elements.generic.enemies.individual.bosses.BossSat;
import elements.generic.enemies.individual.bosses.Ombrelle;
import elements.generic.enemies.individual.lvl1.Boule;
import elements.generic.enemies.individual.lvl1.Cylon;
import elements.generic.enemies.individual.lvl1.DeBase;
import elements.generic.enemies.individual.lvl1.Insecte;
import elements.generic.enemies.individual.lvl1.Kinder;
import elements.generic.enemies.individual.lvl1.Laser;
import elements.generic.enemies.individual.lvl1.Plane;
import elements.generic.enemies.individual.lvl1.PorteRaisin;
import elements.generic.enemies.individual.lvl1.QuiTir;
import elements.generic.enemies.individual.lvl1.QuiTirTriangle;
import elements.generic.enemies.individual.lvl1.Toupie;
import elements.generic.enemies.individual.lvl1.Vicious;
import elements.generic.enemies.individual.lvl1.ZigZag;
import elements.generic.enemies.individual.lvl2.BouleTirCoteRotation;
import elements.generic.enemies.individual.lvl3.BouleNv3;
import elements.generic.enemies.individual.lvl3.CylonNv3;
import elements.generic.enemies.individual.lvl3.DeBaseNv3;
import elements.generic.enemies.individual.lvl3.Group3;
import elements.generic.enemies.individual.lvl3.InsecteNv3;
import elements.generic.enemies.individual.lvl3.KinderNv3;
import elements.generic.enemies.individual.lvl3.LaserNv3;
import elements.generic.enemies.individual.lvl3.Plane3;
import elements.generic.enemies.individual.lvl3.PorteRaisinNv3;
import elements.generic.enemies.individual.lvl3.QuiTirNv3;
import elements.generic.enemies.individual.lvl3.QuiTirTriangle3;
import elements.generic.enemies.individual.lvl3.QuiTourneNv3;
import elements.generic.enemies.individual.lvl3.ToupieNv3;
import elements.generic.enemies.individual.lvl3.ZigZagNv3;
import elements.generic.enemies.individual.lvl4.BouleNv4;
import elements.generic.enemies.individual.lvl4.CylonNv4;
import elements.generic.enemies.individual.lvl4.DeBaseNv4;
import elements.generic.enemies.individual.lvl4.InsecteNv4;
import elements.generic.enemies.individual.lvl4.KinderNv4;
import elements.generic.enemies.individual.lvl4.LaserNv4;
import elements.generic.enemies.individual.lvl4.Plane4;
import elements.generic.enemies.individual.lvl4.PorteRaisinNv4;
import elements.generic.enemies.individual.lvl4.QuiTirNv4;
import elements.generic.enemies.individual.lvl4.QuiTirTriangle4;
import elements.generic.enemies.individual.lvl4.ToupieNv4;
import elements.generic.enemies.individual.lvl4.ZigZagNv4;
import elements.generic.weapons.Weapons;
import elements.particular.bonuses.Bonus;
import elements.particular.bonuses.BonusStop;
import elements.particular.bonuses.XP;
import elements.particular.other.WaveEffect;
import elements.particular.particles.Particles;

/**
 * Classe principale gerant le mode infini du jeu
 * @author Julien
 */
public class EndlessMode implements Screen {
	
	private Game game;
	private static SpriteBatch batch = CSG.batch;
	private Bloom bloom = CSG.bloom;
	private GL20 gl;
	public static boolean alternate = true, pause = false, scoreSent = false, triggerStop = false, lost = false;
	
	private static Player ship;
	
	public static String strScore = "0";
	public static float now = 0, score = 0;
	private static float rScore;
	private static int multi;
	private static String strMulti = "x1";
	
	public static final int X_CHRONO = CSG.DIXIEME_LARGEUR/2 - CSG.screenHalfWidth;
	public static final int FONT_HEIGHT = CSG.HEIGHT_DIV10/3;
	// *************************  J  A  U  G  E  *************************
	private final TextureRegion red;
	private static final int TIER_LARGEUR_JAUGE =  CSG.screenWidth/18, HAUTEUR_JAUGE = CSG.SCREEN_HEIGHT/75;
	// *************************  D  P  A  D  ****************************

	private static final OrthographicCamera cam = new OrthographicCamera(CSG.screenWidth, CSG.SCREEN_HEIGHT);
	public static int difficulty, nbBonusStop = 0, nbBombes = 0;
	public static float bloomOriginalIntensity = 1, delta = 0, timeStopBonus = 0, delta15 = 0, deltaDiv3, delta2, deltaU, deltaMicroU, UnPlusDelta, unPlusDelta2, unPlusDelta3, deltaPlusExplosion;
	public static boolean effetBloom = false, xpAjout = false, drawMenu = false, konamiCode = false, movementTransition = false;
	private static boolean choosen = false, willUseStopBonus = false;
	private static int menuX = 0, menuY = 0;
	private int advice = 0;
	private static final Vector2 TMP_POS = new Vector2(), TMP_DIR = new Vector2();
	private Bouton boutonUpgrade = null, boutonTwitter = null, boutonRestart = null;
	private static Bouton boutonBack = null;
	// screenshake
	private static boolean shake = false;
	private static float chronoShake = 0, shakeTmpForce = 0, shakeTotalMvtX, shakeTotalMvtY;
	public static final Random R = new Random();
	
//	private static ShaderProgram shaderMort = ShaderMort.init(), originalShader;
	public static ShaderProgram originalShader;
	private static Matrix4 tmpCombined; 
	public static float originalDelta = 0, timeSinceLost = 0;
	public static int fps, perf = 3, explosions = 0;
	private boolean toastSent = false;
	private float nextScore = 0, justTouched = 0;
	public static final float STOP = 3;
	public static boolean invicibility = false, freeze = false;
	private Tutorial tuto = new Tutorial();
	private TemporaryText getReady = new TemporaryText(Strings.GET_READY);
	private boolean started = false;

	public EndlessMode(Game game, SpriteBatch batch, int level) {
		super();
		Gdx.input.setCatchBackKey(true);
		EndlessMode.batch = batch;
		originalShader = SpriteBatch.createDefaultShader();
		this.game = game;
		ship = new Player();
		difficulty = level;
		init();
		ship.initialiser();
		gl = Gdx.graphics.getGL20();
		gl.glViewport(0, 0, CSG.screenWidth, CSG.SCREEN_HEIGHT);
		red = AssetMan.getTextureRegion("rougefonce");
		bloom = CSG.bloom;
	}

	private void init() {
//		batch.setShader(originalShader);
		if (CSG.bloom != null && CSG.profile.bloom) {
			bloom.setBloomIntesity(CSG.profile.intensiteBloom);
		} else {
			CSG.profile.bloom = false;
		}
		ship.reInit(); // Pour remettre les positions mais garder shield et adds
		if (Gdx.app.getVersion() != 0)
			CSG.myRequestHandler.showAds(false); // desactiver adds. A VIRER POUR LA RELEASE
		// ** DEPLACEMENT ZONE DE JEU
		cam.position.set(CSG.gameZoneWidth/2, CSG.SCREEN_HEIGHT / 2, 0);
		
		CSG.reset();
        scoreSent = false;
        xpAjout = false;
        pause = false;
        lost = false;
        score = 0;
        now = 0;
        timeSinceLost = 0;
        Enemy.clear();
        Gdx.graphics.setVSync(true);
        SoundMan.playMusic();
		strScore = String.valueOf(score);
		timeStopBonus = 0;
		nbBonusStop = 0;
		triggerStop = false;
		nbBombes = 0;
		boutonUpgrade = null;
		boutonTwitter = null;
		boutonRestart = null;
		boutonBack = null;
		
		shake = false;
		chronoShake = 0;
		Weapons.clear();
		Progression.reset();
		cam.position.z = 1;
		if (difficulty == 1)
			Player.activateShield();
		Progression.reset();
		toastSent = false;
		nextScore = 0;
		rScore = 0;
		multi = 1;
		strMulti = "x1";
		getReady.reset();
		started = false;
	}

	@Override
	public void render(float delta) {
		if (freeze) {
			delta = 0;
		}
		if ((Gdx.input.isTouched() || Gdx.input.justTouched()) && !lost)
			ship.mouvements();
		originalDelta = delta;
		if (Gdx.app.getVersion() == 0)
			tests();
		cam();
		
		if (CSG.profile.bloom)
			bloomActive();
		else
			gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  //+10% de perfs !!. Si pas de bloom il faut le mettre
		
		explosions = 0;
		batch.begin();
		
		Particles.background(batch);
//		projet.act(batch);
		if (!pause && !freeze) {
			if (delta < 1) { 
				EndlessMode.delta = delta;
				if ( (drawMenu || choosen) && CSG.profile.typeControle != CSG.CONTROLE_ACCELEROMETRE)
					EndlessMode.delta = delta / 7;
				if (CSG.profile.isFirstTime()) 
					tuto.act(batch);
//				score += (EndlessMode.delta + EndlessMode.delta + EndlessMode.delta);
				majDeltas();
			}
			// S I   O N   A   P A S   E N C O R E   P E R D U
			if (!lost && !triggerStop) {
				if (movementTransition)
					transitionVersMouvement();
				affichageNonPerdu();
			} else {
				if (triggerStop) {
					affichageEtUpdateStop();
				} else {
//					batch.setShader(shaderMort);
					if (lost && !scoreSent && !konamiCode){
						if (CSG.profile.bloom)
							bloom.setBloomIntesity(-1f);
						final int monScore = (int) score;
						switch (difficulty) {
						case 1:		CSG.google.submitScore("CgkIrsqv7rIVEAIQKQ", monScore );	break;
						case 2:		CSG.google.submitScore("CgkIrsqv7rIVEAIQKg", monScore );	break;
						case 3:		CSG.google.submitScore("CgkIrsqv7rIVEAIQKw", monScore );	break;
						case 4:		CSG.google.submitScore(Strings.LVL4LB, monScore );			break;
						}
						scoreSent = true;
					}
				}
				affichagePerdu();
				if (!triggerStop && lost) drawFinalScoreAndAdvices(batch);
			}					 
			update();
		} else { // D O N C   E N   P A U S E
			if (Gdx.input.isKeyPressed(Keys.BACK) && (now > justTouched + .1)) {
				goToMenu();
			}
			affichagePerdu();
			afficherResume();
			if (Gdx.input.justTouched()) {
				drawMenu = false;
				pause = false;
				justTouched = now;
			}
		}
		WaveEffect.draw(batch);
		if (triggerStop)
			stopActivated();
		if (CSG.profile.isFirstTime()) 
			tuto.act(batch);
		if (!started) {
			getReady.act(batch, true);
			if (Gdx.input.isTouched())
				started = true;
		}
		batch.end();
		if (CSG.profile.bloom)
			bloom.render();
		now += EndlessMode.delta;
		if (shake)
			screenShake();
		Enemy.deadBodiesEverywhere();
		fps = Gdx.graphics.getFramesPerSecond();
		perf = fps / 10;
		deltaPlusExplosion = EndlessMode.delta + explosions;
	}

	private void stopActivated() {
		ship.draw(batch);
	}

	private void cam() {
		cam.update();
		tmpCombined = cam.combined;
		if (tmpCombined != null)
			batch.setProjectionMatrix(tmpCombined);
	}

	public static void majDeltas() {
		alternate = !alternate;
		deltaPlusExplosion = EndlessMode.delta + explosions;
		delta15 = delta * 15;
		deltaDiv3 = delta / 3;
		delta2 = delta * 2;
		deltaU = delta * Stats.U;
		deltaMicroU =  Stats.microU * delta;
		UnPlusDelta = 1 + delta;
		unPlusDelta2 = 1 + delta2;
		unPlusDelta3 = UnPlusDelta + delta2;
	}

	private static final float SHAKE_MIN = 1.1f, SHAKE_MAX = 4;//, SHAKE_MUL = 2;
	private void screenShake() {
		if (chronoShake <= SHAKE_MIN) {
			shake = false;
			cam.position.y = CSG.halfHeight;
			
//			if (cam.position.x < CSG.screenHalfWidth) 	
			cam.position.x = CSG.screenHalfWidth;
//			else if (cam.position.x > CSG.DEMI_CAMERA)
//				cam.position.x = CSG.DEMI_CAMERA;
		} else {
			shakeTmpForce = (float) ((R.nextFloat()/2) * (chronoShake*Stats.U));//SHAKE_MUL)) * Stats.u;
			if (shakeTotalMvtX < 0) {
				cam.position.x += shakeTmpForce;
				shakeTotalMvtX += shakeTmpForce;
			} else {
				cam.position.x -= shakeTmpForce;
				shakeTotalMvtX -= shakeTmpForce;
			}
			shakeTmpForce = (float) ((R.nextFloat()/2) * (chronoShake*Stats.U));//SHAKE_MUL)) * Stats.u;
			if (shakeTotalMvtY < 0) {
				cam.position.y += shakeTmpForce;
				shakeTotalMvtY += shakeTmpForce;
			} else {
				cam.position.y -= shakeTmpForce;
				shakeTotalMvtY -= shakeTmpForce;
			}
			
			// avant game zone
//			if (cam.position.x < CSG.screenHalfWidth) 	
//				cam.position.x = CSG.screenHalfWidth;
//			else if (cam.position.x > CSG.screenHalfWidth)
//				cam.position.x = CSG.screenHalfWidth;
			chronoShake /= 1.01f + delta2;
		}
	}
	
	private void tests() {
		//		if (Gdx.input.isKeyPressed(Keys.A)) {
//			Ennemis.LISTE.add(DeBase.pool.obtain());
//		}
//		CSG.profil.NvArmeBalayage = 1;
//		CSG.profil.NvArmeDeBase = 1;
//		CSG.profil.NvArmeHantee = 1;
//		CSG.profil.NvArmeTrois = 1;
		if (Gdx.input.isKeyPressed(Keys.PAGE_DOWN))	{
			cam.translate(0, 0, 1);
			cam();
			SoundMan.playBruitage(SoundMan.bonusTaken);
		}
		if (Gdx.input.isKeyPressed(Keys.END))	Group3.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.A)) 	BossSat.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.Z)) 	DeBase.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.Z)) 	DeBaseNv3.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.Z)) 	DeBaseNv4.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.E))		ZigZag.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.E))		ZigZagNv3.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.E))		ZigZagNv4.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.R))		addBonusStop();
		if (Gdx.input.isKeyPressed(Keys.T))		QuiTir.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.T))		QuiTirNv3.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.T))		QuiTirNv4.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.Y))		QuiTirTriangle.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.Y))		QuiTirTriangle3.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.Y))		QuiTirTriangle4.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.U))		Boule.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.U))		BouleNv3.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.U))		BouleNv4.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.O))		Ombrelle.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.P))		BouleTirCoteRotation.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.Q))		Toupie.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.Q))		ToupieNv3.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.Q))		ToupieNv4.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.D))		XP.POOL.obtain().init(400, 400, 300);
//		if (Gdx.input.isKeyPressed(Keys.F))		QuiTourne.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.F))		QuiTourneNv3.ref.invoquer();
//		if (Gdx.input.isKeyPressed(Keys.F))		QuiTourneNv4.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.J))		Kinder.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.J))		KinderNv3.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.J))		KinderNv4.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.M))		Cylon.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.M))		CylonNv3.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.M))		CylonNv4.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.X))		Plane.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.X))		Plane3.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.X))		Plane4.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.V))		Laser.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.V))		LaserNv3.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.V))		LaserNv4.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.N))		PorteRaisin.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.N))	PorteRaisinNv3.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.N))	PorteRaisinNv4.ref.invoquer();
		
		
		if (Gdx.input.isKeyPressed(Keys.F1))	invicibility = true;
		if (Gdx.input.isKeyPressed(Keys.F2))	invicibility = false;
		if (Gdx.input.isKeyPressed(Keys.F3))	freeze = true;
		if (Gdx.input.isKeyPressed(Keys.F4))	freeze = false;
		if (Gdx.input.isKeyPressed(Keys.F5))	Enemy.bombe();
		if (Gdx.input.isKeyPressed(Keys.F6))	Insecte.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.F6))	InsecteNv3.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.F6))	InsecteNv4.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.F9))	BossMine.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.F10))	BossQuad.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.F11))	Vicious.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.F12))	DeBaseNv4.ref.invoquer();
		if (Gdx.input.isKeyPressed(Keys.F3))	Bonus.LIST.add(BonusStop.POOL.obtain());
		if (Gdx.input.isKeyPressed(Keys.F4)) {
			Bonus.LIST.add(XP.POOL.obtain());
			CSG.profile.bfg = true;
		}
//		if (Gdx.input.isKeyPressed(Keys.H))
//			try {
//				sendInfos();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		if (Gdx.input.isKeyPressed(Keys.F5)) score++;
	}
	
	private float facteurTransition = 20;
	private final static float stepTransition = .1f;
	private void transitionVersMouvement() {
		// On ne d�cremente le chrono de ralentissement que quand on est au facteur de ralentissement max
		if (facteurTransition > 1) {
			facteurTransition -= stepTransition;
			SoundMan.transitionUp(((20 - facteurTransition)/20));
		} else {
			SoundMan.setOriginalVolume();
			movementTransition = false;
			facteurTransition = 20;
		}
		EndlessMode.delta /= facteurTransition;
		majDeltas();
		alternate = !alternate;
	}

	private void affichageEtUpdateStop() {
		timeStopBonus -= delta;
		batch.draw(red, (Player.POS.x + Player.DEMI_LARGEUR) - (TIER_LARGEUR_JAUGE * timeStopBonus)/2, Player.POS.y - HAUTEUR_JAUGE * 3, TIER_LARGEUR_JAUGE * timeStopBonus, HAUTEUR_JAUGE);
		if (timeStopBonus < 0) {
			triggerStop = false;
			movementTransition = true;
			if (--nbBonusStop > 0)
				timeStopBonus += STOP;
		}
	}

	private void bloomActive() {
		if (triggerStop) {
			bloom.setBloomIntesity(CSG.profile.intensiteBloom + (timeStopBonus*2));
		} else {
			if (!shake) {
				bloom.setBloomIntesity(CSG.profile.intensiteBloom);
			} else {
				bloom.setBloomIntesity(CSG.profile.intensiteBloom + (chronoShake * 1.9f) );
			}
		}
		bloom.capture();
	}

	private void afficherResume() {
		if (boutonBack == null) {
			Particles.ajoutPanneau(TMP_POS, TMP_DIR);
			boutonBack = new Bouton(Strings.BACK, CSG.menuFont, 
					(int) CSG.menuFont.getBounds(Strings.BACK).width, 
					(int) CSG.menuFont.getBounds(Strings.BACK).height, 
					(int) ((cam.position.x) - CSG.menuFont.getBounds(Strings.BACK).width / 2),
					Menu.HAUTEUR_BOUTON * 3,
					new OnClick() {
					public void onClick() {
					}
			});
		}
		if (Gdx.input.justTouched()) {
			if (boutonBack != null && 
					Physic.isPointInRect(Gdx.input.getX() + cam.position.x / 2, CSG.SCREEN_HEIGHT - Gdx.input.getY(),
							0, Menu.HAUTEUR_BOUTON * 1.1f, CSG.gameZoneWidth, Menu.HAUTEUR_BOUTON * 3)) {
				goToMenu();
				boutonBack = null;
			}
		}
		if (boutonBack != null)
			boutonBack.draw(batch);
	}

	private void goToMenu() {
		CSG.profilManager.persist();
		game.setScreen(new Menu(game));
		Enemy.clear();
		if (CSG.profile.xpDispo >= 30000) {
			CSG.google.unlockAchievementGPGS(Strings.ACH_30k_XP);
		}
	}

	private static void mettrePause() {
		pause = true;
		CSG.profilManager.persist();
	}
	
	private void drawFinalScoreAndAdvices(SpriteBatch batch) {
		timeSinceLost += originalDelta;
		
		if (timeSinceLost < 0.7f)
			return;
		if (Gdx.app.getVersion() != 0)
			CSG.myRequestHandler.showAds(true);
		
		if (!xpAjout && !konamiCode) {
			CSG.profile.addXp((int) score);
			xpAjout = true;
		}
//		batch.setShader(originalShader);
		initAndDrawButtons(batch);
		
		if (score < 1000) {
			if (advice == 0)
				advice = (int) (1 + Math.random() * 5);
			switch (4) {
				case 3:		afficherConseil(Strings.ADVICE3);								break;
				case 4:		afficherConseil(Strings.ADVICE4, AssetMan.bomb, batch);			break;
				case 5:		afficherConseil(Strings.ADVICE5, AssetMan.stopBonus, batch);	break;
			}
		}
		float width = CSG.menuFont.getBounds(strScore).width;
		if (width < CSG.menuFont.getBounds(strScore).width) {
			width = CSG.menuFont.getBounds(strScore).width;
		}
		batch.setColor(AssetMan.BLACK);
		batch.draw(AssetMan.dust, 0, CSG.halfHeight - CSG.menuFont.getBounds(strScore).height*2, CSG.screenWidth, CSG.menuFont.getBounds(strScore).height * 6);
		batch.setColor(AssetMan.WHITE);
		CSG.menuFont.draw(batch, Strings.DEAD, ((cam.position.x-CSG.screenHalfWidth)) + ((CSG.screenHalfWidth - (CSG.menuFont.getBounds(Strings.DEAD).width)/2)),
				CSG.halfHeight + CSG.menuFontSmall.getBounds(Strings.DEAD).height * 3);
		
		CSG.menuFont.draw(batch, strScore, ((cam.position.x-CSG.screenHalfWidth)) + ((CSG.screenHalfWidth - (CSG.menuFont.getBounds(strScore).width)/2)),
				CSG.halfHeight + CSG.menuFontSmall.getBounds(strScore).height);
		
		if (boutonUpgrade != null) {
			boutonUpgrade.draw(batch);
			if (boutonTwitter != null)
				boutonTwitter.draw(batch);
			Particles.drawUi(batch);
		}
	}

	private void initAndDrawButtons(SpriteBatch batch) {
		if (boutonRestart == null) {
			boutonRestart = new Bouton(Strings.RESTART_BUTTON, CSG.menuFont, 
					(int) CSG.menuFont.getBounds(Strings.RESTART_BUTTON).width, 
					(int) CSG.menuFont.getBounds(Strings.RESTART_BUTTON).height, 
					(int) ((cam.position.x) - CSG.menuFont.getBounds(Strings.RESTART_BUTTON).width / 2),
					Menu.HAUTEUR_BOUTON,
					new OnClick() {
					public void onClick() {
						init();
					}
			});
		} else {
			boutonRestart.draw(batch);
		}
		if (Player.weapon.nv() < Profil.NV_ARME_MAX) {
			if (boutonUpgrade == null) {
				boutonUpgrade = new Bouton(Strings.UPGRADE_BUTTON, CSG.menuFont, 
						(int) CSG.menuFont.getBounds(Strings.UPGRADE_BUTTON).width, 
						(int) CSG.menuFont.getBounds(Strings.UPGRADE_BUTTON).height, 
						(int) ((cam.position.x) - CSG.menuFont.getBounds(Strings.UPGRADE_BUTTON).width / 2),
						(int) (Menu.HAUTEUR_BOUTON * 2.5f),
						new OnClick() {
					public void onClick() {
						if(CSG.profile.getCoutUpArme() <= CSG.profile.xpDispo) {
							CSG.profile.upArme();
							CSG.profilManager.persist();
							init();
						} else {
							CSG.google.buyXp();
						}
					}
				});
			} else {
				boutonUpgrade.draw(batch);
			}
		} 
		if (boutonTwitter == null) {
			Particles.ajoutPanneau(TMP_POS, TMP_DIR);
			boutonTwitter = new Bouton(Strings.BRAG_TWITTER, CSG.menuFont, 
					(int) CSG.menuFont.getBounds(Strings.BRAG_TWITTER).width, 
					(int) CSG.menuFont.getBounds(Strings.BRAG_TWITTER).height, 
					(int) ((cam.position.x) - CSG.menuFont.getBounds(Strings.BRAG_TWITTER).width / 2),
					Menu.HAUTEUR_BOUTON * 4,
					new OnClick() {
					public void onClick() {
						CSG.google.bragTwitter("I made " + (int)score + " on #MESS" + difficulty + " #androidgames");
					}
			});
		} else {
			boutonTwitter.draw(batch);
		}
	}

	private float prevDelta;
	private void affichagePerdu() {
		prevDelta = delta;
		delta = 0;
		stopActivated();
		Enemy.draw(batch);
		delta = prevDelta;
		Particles.draw(batch);
		Particles.drawImpacts(batch);
		Weapons.affichage(batch);
		ui();
		initAndDrawButtons(batch);
	}

	protected void moveCamY(float y) {
		if (boutonBack != null)				boutonBack.camMoveY(y);
		if (boutonTwitter != null)			boutonTwitter.camMoveY(y);
		if (boutonUpgrade != null)	boutonUpgrade.camMoveY(y);
		cam.position.y += y;
	}
	
	protected void moveCamX(float x) {
		if (boutonBack != null)				boutonBack.camMoveX(x);
		if (boutonTwitter != null)			boutonTwitter.camMoveX(x);
		if (boutonUpgrade != null)	boutonUpgrade.camMoveX(x);
		cam.position.x += x;
	}
	
	public static void effetBloom() {
		effetBloom = true;
		bloomOriginalIntensity = 50;
	}

	private void ui() {
		CSG.outlineScoreFont.draw(batch, strMulti, (cam.position.x - CSG.screenTierWidth) - CSG.outlineScoreFont.getBounds(strMulti).width/2, FONT_HEIGHT + CSG.outlineScoreFont.getBounds(strMulti).height/2);
		CSG.scoreFont.draw(batch, strMulti, (cam.position.x - CSG.screenTierWidth) - CSG.scoreFont.getBounds(strMulti).width/2, FONT_HEIGHT + CSG.scoreFont.getBounds(strMulti).height/2);
		if (!lost) {
			// bottom score
			CSG.outlineScoreFont.draw(batch, strScore, (cam.position.x) - CSG.outlineScoreFont.getBounds(strScore).width/2, FONT_HEIGHT + CSG.outlineScoreFont.getBounds(strScore).height/2);
			CSG.scoreFont.draw(batch, strScore, (cam.position.x) - CSG.scoreFont.getBounds(strScore).width/2, FONT_HEIGHT + CSG.scoreFont.getBounds(strScore).height/2);
		}
		if (CSG.profile.manualBonus) {
			// ****  A F F I C H E R   S T O P  ****
			switch(nbBonusStop) {
			default :
//			case 3:	batch.draw(AssetMan.stopBonus, cam.position.x + X_CHRONO + Bonus.WIDTH * 2 + Bonus.HALF_WIDTH * 2, HAUTEUR_POLICE * 2, Bonus.WIDTH, Bonus.WIDTH);
			case 2:	batch.draw(AssetMan.stopBonus, cam.position.x + X_CHRONO + Bonus.WIDTH + Bonus.HALF_WIDTH, FONT_HEIGHT * 2, Bonus.WIDTH, Bonus.WIDTH);
			case 1:	batch.draw(AssetMan.stopBonus, cam.position.x + X_CHRONO, FONT_HEIGHT * 2, Bonus.WIDTH, Bonus.WIDTH);
			case 0:
			}
			switch(nbBombes) {
			default :
			case 3:	batch.draw(AssetMan.bomb, CSG.screenHalfWidth + cam.position.x + X_CHRONO + Bonus.WIDTH * 3 + Bonus.HALF_WIDTH * 3, Bonus.HALF_WIDTH, Bonus.WIDTH, Bonus.WIDTH);
			case 2:	batch.draw(AssetMan.bomb, CSG.screenHalfWidth + cam.position.x + X_CHRONO + Bonus.WIDTH * 2 + Bonus.HALF_WIDTH * 2, Bonus.HALF_WIDTH, Bonus.WIDTH, Bonus.WIDTH);
			case 1:	batch.draw(AssetMan.bomb, CSG.screenHalfWidth + cam.position.x + X_CHRONO + Bonus.WIDTH * 1 + Bonus.HALF_WIDTH * 1, Bonus.HALF_WIDTH, Bonus.WIDTH, Bonus.WIDTH);
			case 0:
			}
		}
	}

	private void affichageNonPerdu() {
		Bonus.drawAndMove(batch);
		stopActivated();
		Enemy.affichageEtMouvement(batch);
		Particles.drawImpacts(batch);
		Particles.draw(batch);
		Weapons.drawAndMove(batch);
		ui();
	}

	private void update() {
		if (!started)
			return;
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.POWER) || Gdx.input.isKeyPressed(Keys.HOME) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			justTouched = now;
			mettrePause();
		}
		if (!lost) {
			mouvement();
			if (alternate) {
//				strScore = String.valueOf((int)score);
				Progression.invoqueBaseOnScore();
				if (!triggerStop)
					Physic.collisionsTest();
				score();
 			}
			if (!drawMenu && !freeze)	ship.tir();
		} else { // Donc si on a perdu
			if (toastSent) {
				CSG.google.toast("You might want to check the highscore menu !");
				toastSent = false;
			}
			if (Gdx.input.justTouched()) {
				Bouton.testClick(boutonUpgrade, cam.position.x / 2);
				Bouton.testClick(boutonRestart, cam.position.x / 2);
				Bouton.testClick(boutonTwitter, cam.position.x / 2);
			}
		}
	}

	private void score() {
		if (nextScore < now && !lost) {
			score += 5;
			strScore = String.valueOf((int)score);
			nextScore = now + 1;
		}
		if (rScore > 0 && !triggerStop) {
			rScore -= EndlessMode.delta2;
			if (rScore < (float)multi/20f) {
				if (multi > 1) {
					multi--;
					rScore = 1;
					strMulti = "x" + multi;
				} else {
					rScore = 0;
				}
			}
		}
		if (rScore < 0.15f) {
			CSG.scoreFont.setColor(0, .35f, 1, 1);
		} else {
			CSG.scoreFont.setColor(0, rScore, 1, 1);
		}
		CSG.scoreFont.setScale(CSG.originalScoreFontScale + rScore);
		CSG.outlineScoreFont.setScale((CSG.originalScoreFontScale + rScore) * 1.2f);
	}

	public static void mouvement() {
		if (Gdx.input.justTouched())
			justeTouche();
		else {
			if (Gdx.input.isTouched())
				touche();
			else
				pasTouche();
		}	
		if (CSG.profile.typeControle == CSG.CONTROLE_ACCELEROMETRE & !drawMenu)
			ship.accelerometre();
	}

	private static void pasTouche() {
		boutonBack = null;
		if (drawMenu && CSG.profile.manualBonus)	{
			if (nbBonusStop > 0 && !triggerStop) batch.draw(AssetMan.stopBonus,(menuX - Bonus.DISPLAY_WIDTH) + (cam.position.x-CSG.screenHalfWidth) - Player.DEMI_LARGEUR, menuY, Bonus.DISPLAY_WIDTH, Bonus.DISPLAY_WIDTH);
			else batch.draw(AssetMan.stopBonusGrey,(menuX - Bonus.DISPLAY_WIDTH) + (cam.position.x-CSG.screenHalfWidth) - Player.DEMI_LARGEUR, menuY, Bonus.DISPLAY_WIDTH, Bonus.DISPLAY_WIDTH);
			
//			if (chronoRalentir > .01f) batch.draw(AssetMan.temps, menuX + (cam.position.x-CSG.DEMI_LARGEUR_ECRAN) - VaisseauJoueur.DEMI_LARGEUR, menuY + Bonus.DISPLAY_WIDTH, Bonus.DISPLAY_WIDTH,Bonus.DISPLAY_WIDTH);
//			else batch.draw(AssetMan.tempsGris, menuX + (cam.position.x-CSG.DEMI_LARGEUR_ECRAN) - VaisseauJoueur.DEMI_LARGEUR, menuY + Bonus.DISPLAY_WIDTH, Bonus.DISPLAY_WIDTH,Bonus.DISPLAY_WIDTH);
			
			if (nbBombes > 0) batch.draw(AssetMan.bomb, (menuX + Bonus.DISPLAY_WIDTH) + (cam.position.x-CSG.screenHalfWidth) - Player.DEMI_LARGEUR, menuY, Bonus.DISPLAY_WIDTH,Bonus.DISPLAY_WIDTH);
			else batch.draw(AssetMan.bombGrey, (menuX + Bonus.DISPLAY_WIDTH) + (cam.position.x-CSG.screenHalfWidth) - Player.DEMI_LARGEUR, menuY, Bonus.DISPLAY_WIDTH,Bonus.DISPLAY_WIDTH);
		} else if (!choosen) {
			drawMenu = true;
			menuX = Gdx.input.getX();
			menuY = CSG.SCREEN_HEIGHT - Gdx.input.getY();
		}
	}

	private static void touche() {
		if (!choosen) {
			if (Gdx.app.getVersion() == 0) clavier();
//			if (CSG.profile.typeControle == CSG.CONTROLE_DPAD) afficherDPAD();  
			if (willUseStopBonus) {
				activateStop();
				willUseStopBonus = false;
			}
		}
	}

	private static void activateStop() {
		SoundMan.halfVolume();
		triggerStop = true;
		Particles.addChronoGenerator();
		WaveEffect.add(Player.xCenter, Player.yCenter, AssetMan.GREEN);
	}

	private static void justeTouche() {
		if (CSG.profile.typeControle == CSG.CONTROLE_DPAD) {
			Player.prevX = Gdx.input.getX();
			Player.prevY = Gdx.input.getY();
		}
		if (drawMenu) { 		// ---- SELECTION
			if (nbBonusStop > 0 && Physic.isPointInRect(Gdx.input.getX(), CSG.SCREEN_HEIGHT - Gdx.input.getY(), (menuX - Bonus.DISPLAY_WIDTH) - Player.DEMI_LARGEUR, menuY, Bonus.DISPLAY_WIDTH, Bonus.DISPLAY_WIDTH)) {
				willUseStopBonus = true;
				choosen = true;
			} else if (nbBombes > 0 && Physic.isPointInRect(Gdx.input.getX(), CSG.SCREEN_HEIGHT - Gdx.input.getY(), (menuX + Bonus.DISPLAY_WIDTH) - Player.DEMI_LARGEUR, menuY, Bonus.DISPLAY_WIDTH,Bonus.DISPLAY_WIDTH)) {
				Enemy.bombe();
				nbBombes--;
				choosen = true;
			}
			drawMenu = false;
		} else { 						// ---- REPRISE JEU
			choosen = false;
		}
	}

//	private static void afficherDPAD() {
//		if (CSG.profile.typeControle == CSG.CONTROLE_DPAD && Gdx.input.isTouched()){
//			//			F L E C H E   D R O I T E
//			batch.draw(ARROW,(cam.position.x-CSG.screenHalfWidth) + Player.prevX + DEMI_LARGEUR_FLECHE, CSG.SCREEN_HEIGHT - (Player.prevY + DEMI_HAUTEUR_FLECHE),
//					DEMI_LARGEUR_FLECHE, DEMI_HAUTEUR_FLECHE, LARGEUR_FLECHE, HAUTEUR_FLECHE, 1, 1, 0);
//			//			F L E C H E   G A U C H E
//			batch.draw(ARROW,(cam.position.x-CSG.screenHalfWidth) + Player.prevX - (LARGEUR_FLECHE+DEMI_LARGEUR_FLECHE), CSG.SCREEN_HEIGHT - (Player.prevY + DEMI_HAUTEUR_FLECHE),
//					DEMI_LARGEUR_FLECHE, DEMI_HAUTEUR_FLECHE, LARGEUR_FLECHE, HAUTEUR_FLECHE, 1, 1, 180);
//			//			F L E C H E   H A U T
//			batch.draw(ARROW,(cam.position.x-CSG.screenHalfWidth) + Player.prevX - DEMI_LARGEUR_FLECHE, CSG.SCREEN_HEIGHT - (Player.prevY - DEMI_HAUTEUR_FLECHE), DEMI_LARGEUR_FLECHE,
//					DEMI_HAUTEUR_FLECHE, LARGEUR_FLECHE, HAUTEUR_FLECHE, 1, 1, 90);
//			//			F L E C H E   B A S
//			batch.draw(ARROW,(cam.position.x-CSG.screenHalfWidth) + Player.prevX - DEMI_LARGEUR_FLECHE, CSG.SCREEN_HEIGHT - (Player.prevY + DEMI_HAUTEUR_FLECHE + HAUTEUR_FLECHE),
//					DEMI_LARGEUR_FLECHE, DEMI_HAUTEUR_FLECHE, LARGEUR_FLECHE, HAUTEUR_FLECHE, 1, 1, 270);
//		}
//	}

	private static void clavier() {
		if (!drawMenu & !choosen) 	ship.mouvements();
		if (Gdx.input.isKeyPressed(Keys.LEFT)) 	ship.mvtLimiteVitesse(-500, 0);
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) ship.mvtLimiteVitesse(500, 0);
		if (Gdx.input.isKeyPressed(Keys.UP)) 	ship.mvtLimiteVitesse(0, 500);
		if (Gdx.input.isKeyPressed(Keys.DOWN)) 	ship.mvtLimiteVitesse(0, -500);
//		if (Gdx.input.isKeyPressed(Keys.D) && tempsBonusStop > 0) triggerStop = true;
//		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) mettrePause();
	}

	@Override
	public void resize(int width, int height) {		CSG.profilManager.persist();	}

	@Override
	public void show() {	}

	@Override
	public void hide() { 		CSG.profilManager.persist(); }

	@Override
	public void pause() {	}

	@Override
	public void resume() { 		CSG.assetMan.reload(true);	}

	@Override
	public void dispose() {	}
	public static void addBonusStop() {
		if (CSG.profile.manualBonus) {
			nbBonusStop++;
			if (nbBonusStop > 2) {
				activateStop();
			}
		} else {
			activateStop();
		}
		timeStopBonus = STOP;
	}

	public static void ajoutBombe() {
		if (CSG.profile.manualBonus && nbBombes < 3)	nbBombes++;
		else 											Enemy.bombe();
	}
	
	private void afficherConseil(String s, TextureRegion tr, SpriteBatch batch) {
		CSG.menuFontSmall.draw(batch, s, ((cam.position.x-CSG.screenHalfWidth)) + ((CSG.screenHalfWidth - CSG.menuFontSmall.getBounds(s).width/2)),	
				CSG.halfHeight * 1.5f - CSG.menuFontSmall.getBounds(s).height * 4);
		batch.draw(tr, ((cam.position.x-CSG.screenHalfWidth) + CSG.screenHalfWidth) - Bonus.DISPLAY_WIDTH/2 ,
				CSG.halfHeight * 1.5f,
				Bonus.DISPLAY_WIDTH, Bonus.DISPLAY_WIDTH);
	}

	private void afficherConseil(String s) {
		CSG.menuFontSmall.draw(batch, s, ((cam.position.x-CSG.screenHalfWidth)) + ((CSG.screenHalfWidth - CSG.menuFontSmall.getBounds(s).width/2)),	CSG.halfHeight - CSG.menuFontSmall.getBounds(s).height * 4);
	}

	public static void lost() {
		if (!invicibility) {
			lost = true;
			strScore = "Score : " + (int) score;
			timeSinceLost = 0;
		}
	}

	public static boolean aPerdu() {
		return lost;
	}
	
	public static void reset() {
		Particles.clear();
		drawMenu = false;
		choosen = false;
		ship = new Player();
		willUseStopBonus = false;
		menuX = (int) Player.POS.x;
		menuY = (int) Player.POS.y;
		triggerStop = false;
		pause = false;
	}

	public static void screenShake(int xp) {
		if (CSG.profile.screenshake == false)
			return;
		if (shake == false) {
			shakeTotalMvtX = 0;
			shakeTotalMvtY = 0;
		}
		shake = true;
		chronoShake += (float)xp / 50;
		if (chronoShake < SHAKE_MIN)
			chronoShake += SHAKE_MIN;
		else if (chronoShake > SHAKE_MAX)
			chronoShake = SHAKE_MAX;
	}

	public static Camera getCam() {
		return cam;
	}

	public static void rotate(Vector2 dir, float angle) {
		if (alternate)
			dir.rotate(angle);
	}

	public static void upScore(float valeur) {
		if (!lost) {
			valeur /= 5f;
			rScore += (valeur / ((float)(multi+1f)/2f) );
			if (rScore > 1) {
				if (multi < 10) {
					multi++;
					strMulti = "x" + multi;
					rScore = (float)multi / 5f;
				} else {
					rScore = 1;
				}
			}
			score += valeur * multi;
			strScore = String.valueOf((int)score);
			Tutorial.xpTaken();
		}
	}
}
//	public static void mvtCamPositive(float x) {
//		if (EndlessMode.cam.position.x > CSG.screenHalfWidth) {
//			EndlessMode.cam.position.x += x;
//			x *= 0.15f;
//			Player.camXmoinsDemiEcran = EndlessMode.cam.position.x - CSG.screenHalfWidth;
//			Player.POS.x -= x; // On doit d'office decrementer de ce que la camera s'est déplacée pour ne pas additionner les deux vitesses !
//		}
//	}
//
//	public static void mvtCamNegative(float x) {
//		if (EndlessMode.cam.position.x < CSG.DEMI_CAMERA) {
//			EndlessMode.cam.position.x += x;
//			x *= 0.15f;
//			Player.camXmoinsDemiEcran = EndlessMode.cam.position.x - CSG.screenHalfWidth;
//			Player.POS.x -= x; // On doit d'office decrementer de ce que la camera s'est déplacée pour ne pas additionner les deux vitesses !
//		}
//	}
//	
//	public static void mvtCamSuivantDeplacement() {
//		EndlessMode.cam.position.x += EndlessMode.delta * Player.destX * 0.18f;
//		Player.camXmoinsDemiEcran = EndlessMode.cam.position.x - CSG.screenHalfWidth;
//		Player.destX -= EndlessMode.delta * Player.destX * 0.125f; // On doit d'office decrementer de ce que la camera s'est déplacée pour ne pas additionner les deux vitesses !
//	}


