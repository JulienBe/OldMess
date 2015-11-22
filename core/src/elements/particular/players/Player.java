package elements.particular.players;

import jeu.CSG;
import jeu.Stats;
import jeu.mode.EndlessMode;
import jeu.mode.extensions.Transition;
import assets.AssetMan;
import behind.SoundMan;
import assets.sprites.AnimPlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import elements.generic.enemies.Enemy;
import elements.generic.weapons.Weapon;
import elements.generic.weapons.player.WeaponManager;
import elements.particular.particles.OvaleParticuleGenerator;
import elements.particular.particles.Particles;
import elements.particular.particles.individual.PrecalculatedParticles;

public abstract class Player {

/*
	public static final int WIDTH_ADD = (int) (WIDTH/1.5f), HALF_WIDTH_ADD = WIDTH_ADD/2, WIDTH_DIV_10 = WIDTH / 10,
		HEIGHT = (int) ((float)WIDTH * 1.5f), HALF_HEIGHT = HEIGHT / 2, HEIGHT_MAX_ADD = HEIGHT + HALF_HEIGHT,
		HALF_HEIGHT_ADD = HEIGHT / 8, HEIGHT_DIV4 = HEIGHT / 4, HEIGHT_DIV8 = HEIGHT/8,
		DECALAGE_ADD = WIDTH + HALF_WIDTH - WIDTH_ADD,
		DECALAGE_TIR_ADD_X_GAUCHE = (int) (-HALF_WIDTH - HALF_WIDTH_ADD + ArmeAdd.DIMENSIONS.halfWidth),
		DECALAGE_TIR_ADD_X_DROITE = (int) (DECALAGE_ADD - HALF_WIDTH_ADD + ArmeAdd.DIMENSIONS.halfWidth),
		LIMITE_X_GAUCHE = 0 - HALF_WIDTH, LIMITE_X_DROITE = CSG.screenWidth - HALF_WIDTH, LIMITE_Y_GAUCHE = 0 - HALF_HEIGHT, LIMITE_Y_DROITE = CSG.height - HALF_HEIGHT,
		LEFT_ADD1 = 0x0001, LEFT_ADD2 = 0x0002, RIGHT_ADD1 = 0x0004, RIGHT_ADD2 = 0x0008;
*/
	public static float xCenter = 0, yCenter = 0, nextShot = 0, prevX, prevY, destX, destY, addX, addY, centerLeft1AddX, centerAdd1Y, centerRight1AddX, centerLeft2AddX, centerAdd2Y,
			centerRight2AddX, vitesseFoisdelta = 0, tmpCalculDeplacement = 0, originalAccelX = 0, originalAccelY = 0, angleAddDroite = -90, camXmoinsDemiEcran = CSG.halfWidth;
	public static WeaponManager weapon = CSG.profile.getArmeSelectionnee();
	private final OvaleParticuleGenerator bouclierParticules;
	private static boolean shieldHS = false;
	public static final Vector2 POS = new Vector2();
	public static boolean alterner = true;
	public static int shield = 0;
	public static float tpsBouclierHs = 0;
	private float shotTime = 0;
	public final float width, halfWidth, height, halfHeight, limitXRight, limitYTop;
	
  public Player(float width, float height) {
    this.width = width;
    this.height = height;
    halfWidth = width / 2;
    halfHeight = height / 2;
    limitXRight = CSG.screenWidth - halfWidth;
    limitYTop = CSG.height - halfHeight;
    bouclierParticules = new OvaleParticuleGenerator(height * 2);
    init();
  }

	public void init() {
    POS.set(CSG.halfWidth - halfWidth, CSG.height * 0.33f);
		nextShot = 0;
		prevX = POS.x;
		prevY = POS.y;
		if (CSG.profile.controls == CSG.CONTROLE_ACCELEROMETRE) {
			originalAccelX = Gdx.input.getAccelerometerX();
			originalAccelY = Gdx.input.getAccelerometerY();
		}
		shieldHS = false;
		tpsBouclierHs = 0;
		xCenter = POS.x + halfWidth;
		yCenter = POS.y + halfHeight;
    shield = 0;
	}
	
	public void draw(SpriteBatch batch) {
		Particles.addThrusterParticles(this);
		float x = Player.xCenter, y = Player.POS.y;
		
		batch.setColor(PrecalculatedParticles.colorsOverTimeCyanToGreen[CSG.R.nextInt(PrecalculatedParticles.colorsOverTimeCyanToGreen.length)]);
		batch.draw(AssetMan.dust, x - (Stats.U3 / 2), y - Stats.U3, Stats.U3, Stats.U8);
		batch.setColor(PrecalculatedParticles.colorsOverTimeCyanToGreen[CSG.R.nextInt(PrecalculatedParticles.colorsOverTimeCyanToGreen.length / 2)]);
		batch.draw(AssetMan.dust, x - Stats.U, y - Stats.U3, Stats.U2, Stats.U8);
		batch.setColor(PrecalculatedParticles.colorsOverTimeCyanToGreen[CSG.R.nextInt(PrecalculatedParticles.colorsOverTimeCyanToGreen.length / 3)]);
		batch.draw(AssetMan.dust, x - Stats.u, y - Stats.U3, Stats.U, Stats.U6);
		batch.setColor(PrecalculatedParticles.colorsOverTimeCyanToGreen[0]);
		batch.draw(AssetMan.dust, x - Stats.uDiv2, y - Stats.u * 3, Stats.u, Stats.U5);
		batch.setColor(PrecalculatedParticles.colorsOverTimeCyanToGreen[0]);
		batch.draw(AssetMan.dust, x - Stats.U, y - Stats.u * 3, Stats.U2, Stats.U6);
		batch.setColor(1, 0.8f, 0.4f, 1);
		batch.draw(AssetMan.playerFireLevel1, POS.x, POS.y, width, height);
		shield();
		elements.particular.particles.Smoke.draw(batch);
		batch.setColor(CSG.gm.palette().white);
	}

	private void shield() {
		if (shield > 0) {
			bouclierParticules.add(xCenter , yCenter - Stats.u);
		} else if (shieldHS) {
			tpsBouclierHs += EndlessMode.delta;
			if (tpsBouclierHs > 1f)
				shieldHS = false;
			bouclierParticules.grow(EndlessMode.unPlusDelta3);
			bouclierParticules.add(xCenter , yCenter - Stats.u);
		}
	}

	private int getTouchY() {
		return CSG.height - Gdx.input.getY();
	}

	private float getTouchX() {
		return(Gdx.input.getX() - halfWidth);
	}
	
	private float clicX = 0, clicY = 0, originalClicX = CSG.halfWidth, originalClicY = CSG.halfHeight, wantedMvtX, wantedMvtY, chronoDroit;

	public void mouvements() {
		xCenter = POS.x + halfWidth;
		yCenter = POS.y + halfHeight;

		if (Gdx.input.justTouched()) {
			originalClicX = getTouchX();
			originalClicY = getTouchY();
		} else {
			clicX = getTouchX();
			clicY = getTouchY();

			wantedMvtX = (originalClicX - clicX);
			wantedMvtY = (originalClicY - clicY);

			POS.x -= wantedMvtX * CSG.profile.sensitivity;
			POS.y -= wantedMvtY * CSG.profile.sensitivity;

			if (wantedMvtX > 0) {
				chronoDroit = 0;
				AnimPlayer.toLeft();
			} else if (wantedMvtX < 0) {
				AnimPlayer.toRight();
				chronoDroit = 0;
			} else {
				chronoDroit += EndlessMode.delta;
				if (chronoDroit > 0.3f)
					AnimPlayer.straight();
			}
			originalClicX = getTouchX();
			originalClicY = getTouchY();
		}
		limitesEtCentre();
	}

	/**
	 * Le vaisseau va aux coordonn�es pass�es
	 * @param x
	 * @param y
	 */
	@SuppressWarnings("unused")
	private void mvtTeleport(int x, int y) {
		POS.x = x;
		POS.y = y;
	}
	
	/**
	 * oblige le vaisseau a rester dans les limites de l'�cran
	 */
	private void limitesEtCentre() {
		updateCenters();
		
		if (POS.x < -halfWidth) 		  POS.x = -halfWidth;
		else if (POS.x > limitXRight) POS.x = limitXRight;
		if (POS.y < -halfHeight) 		  POS.y = -halfHeight;
		else if (POS.y > limitYTop)	  POS.y = limitYTop;
		
		if (EndlessMode.cam.position.x > CSG.screenWidth - CSG.halfWidth)
			EndlessMode.cam.position.x = CSG.screenWidth - CSG.halfWidth;
		if (EndlessMode.cam.position.x < CSG.halfWidth)
			EndlessMode.cam.position.x = CSG.halfWidth;
		
//		light.setPosition(xCenter, POS.y);
//		light.setDistance(1);
	}

	public void shot(){
		nextShot = weapon.init(nextShot, this);
	}
	
	public static void changeWeapon(){
		weapon = WeaponManager.changerArme(weapon);
		CSG.profile.setArmeSelectionnee(weapon.getLabel());
	}

	public void touched() {
		if (EndlessMode.konamiCode)
			return;
		if (shield == 0) {
			if (!shieldHS)
				EndlessMode.lost();
		} else {
			popOutShield();
			Weapon.shieldHs();
		}
	}

	public void activateShield() {
		bouclierParticules.init(height);
		if (shield < 3) {
			shield++;
		}
		bouclierParticules.lvlChanged(shield);
	}

	public void touchedEnnemy(Enemy enemy) {
		if (shield == 0) {
			if (!shieldHS)
				EndlessMode.lost();
		} else {
			popOutShield();
			enemy.stillAlive(Enemy.superBomb);
		}
	}
	
	public void updateCenters() {
		xCenter = POS.x + halfWidth;
		yCenter = POS.y + halfHeight;
	}
	
	private void popOutShield() {
		EndlessMode.transition.activate(10, Transition.POP_OUT_SHIELD);
		shieldHS = true;
		tpsBouclierHs = 0;
		SoundMan.playBruitage(SoundMan.bigExplosion);
		shield--;
		bouclierParticules.lvlChanged(shield);
	}

}
