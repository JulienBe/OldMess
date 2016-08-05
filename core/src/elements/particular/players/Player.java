package elements.particular.players;

import jeu.CSG;
import jeu.Stats;
import jeu.mode.EndlessMode;
import jeu.mode.extensions.Transition;
import behind.SoundMan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import elements.generic.enemies.Enemy;
import elements.generic.weapons.Weapon;
import elements.generic.weapons.player.WeaponManager;
import elements.particular.particles.OvaleParticuleGenerator;

public abstract class Player {

	public static float xCenter = 0, yCenter = 0, nextShot = 0, prevX, prevY, originalAccelX = 0, originalAccelY = 0;
	public static WeaponManager weapon = CSG.profile.getArmeSelectionnee();
	private final OvaleParticuleGenerator bouclierParticules;
	private static boolean shieldHS = false;
	public static final Vector2 POS = new Vector2();
	public static int shield = 0;
	public static float tpsBouclierHs = 0;
	public final float width, halfWidth, height, halfHeight, limitXRight, limitYTop;
  protected float xDir = 0, yDir = 0;
	
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
		shield();
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

			prevX = POS.x;
      prevY = POS.y;
			POS.x -= wantedMvtX * CSG.profile.sensitivity;
			POS.y -= wantedMvtY * CSG.profile.sensitivity;

			if (wantedMvtX > 0) {
				chronoDroit = 0;
//				toLeft();
//			} else if (wantedMvtX < 0) {
//				toRight();
				chronoDroit = 0;
			} else {
				chronoDroit += EndlessMode.delta;
//				if (chronoDroit > 0.3f)
//					straight();
			}
			originalClicX = getTouchX();
			originalClicY = getTouchY();
		}

    yDir += ((POS.y - prevY) / CSG.heightDiv10) * 30;
    xDir += ((POS.x - prevX) / CSG.widthDiv5) * 10;
    if (yDir < 24)      yDir /= 0.90f;
    else if (yDir > 20) yDir *= 0.98f;

    xDir *= 0.85f;
    limitesEtCentre();
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
