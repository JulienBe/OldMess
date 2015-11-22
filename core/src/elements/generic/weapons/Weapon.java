package elements.generic.weapons;

import jeu.Physic;
import jeu.mode.EndlessMode;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.generic.Element;
import elements.generic.weapons.enemies.EnemyWeapon;
import elements.generic.weapons.player.PlayerWeapon;
import elements.particular.bonuses.Bonus;
import elements.particular.players.*;
import elements.particular.particles.Particles;

public abstract class Weapon extends Element implements Poolable {
	
	public static final Array<PlayerWeapon> PLAYER_LIST = new Array<PlayerWeapon>(false, 50), ADDS = new Array<PlayerWeapon>(false, 10);
	public static final Array<EnemyWeapon> ENEMIES_LIST = new Array<EnemyWeapon>(false, 50), BOSSES_LIST = new Array<EnemyWeapon>(false, 50);
	protected static final Rectangle COLLISION = new Rectangle();
	private static boolean testCollision = false, shieldHs = false;
	public static int color = 0;
	private static final int XP_WITH_SHIELD = 7;

	public static void drawAndMove(SpriteBatch batch, Player player) {
		playerWeapons(batch);
		testCollision = !testCollision;
		// Attention que si y'a une collision on skip le reste de la liste. D'ou l'effet bizarre quand on est invicible
		act(batch, ENEMIES_LIST, player);
		act(batch, BOSSES_LIST, player);
    }

	private static void playerWeapons(SpriteBatch batch) {
		for (final PlayerWeapon pWeapon : PLAYER_LIST) {
			pWeapon.displayOnScreen(batch);
			pWeapon.now += EndlessMode.delta;
			if (pWeapon.mouvementEtVerif() == false) {
				PLAYER_LIST.removeValue(pWeapon, true);
				pWeapon.free();
			}
			// collision
//			batch.draw(AssetMan.red, pWeapon.getRectangleCollision().x, pWeapon.getRectangleCollision().y, pWeapon.getRectangleCollision().width, pWeapon.getRectangleCollision().height);
		}
		for (final PlayerWeapon pWeapon : ADDS) {
			pWeapon.displayOnScreen(batch);
			pWeapon.now += EndlessMode.delta;
			if (pWeapon.mouvementEtVerif() == false) {
				ADDS.removeValue(pWeapon, true);
				pWeapon.free();
			}
		}
	}

	private static void act(SpriteBatch batch, Array<EnemyWeapon> weapons, Player player) {
		for (final EnemyWeapon eWeapon : weapons) {
			eWeapon.now += EndlessMode.delta;
			eWeapon.displayOnScreen(batch);
			if (testCollision) {
				if (eWeapon.testCollisionVaisseau(player) && !EndlessMode.lost) {
					removeEnemyWeapon(eWeapon, weapons);
					continue;
				}
//			} else if (eWeapon.testCollsionAdds()) {
//				removeEnemyWeapon(eWeapon, weapons);
				continue;
			}
			eWeapon.move();
			if (!Physic.isOnScreen(eWeapon.pos, eWeapon.getDimensions().height, eWeapon.getDimensions().width))
				removeEnemyWeapon(eWeapon, weapons);
		}
		if (shieldHs) {
			for (final EnemyWeapon w : weapons) {
				Particles.popOutWeapon(w);
				Bonus.addXp(XP_WITH_SHIELD, w.pos.x + w.getDimensions().halfWidth, w.pos.y + w.getDimensions().halfHeight);
				w.free();
			}
			shieldHs = false;
			weapons.clear();
		}
	}
	
	private static void removeEnemyWeapon(EnemyWeapon a, Array<EnemyWeapon> lIST) {
		lIST.removeValue(a, true);
		a.free();
	}
	
	public static void affichage(SpriteBatch batch) {
		for (final PlayerWeapon a : PLAYER_LIST)	a.displayOnScreen(batch);
		for (final Weapon a : ENEMIES_LIST)			a.displayOnScreen(batch);
		for (final Weapon a : BOSSES_LIST)			a.displayOnScreen(batch);
		for (final PlayerWeapon a : ADDS)			a.displayOnScreen(batch);
	}

	public static void clear() {
		for (final Weapon a : ADDS)						a.free();
		for (final Weapon a : PLAYER_LIST)				a.free();
		for (int i = 0; i < BOSSES_LIST.size; i++) 		BOSSES_LIST.get(i).free();
		for (int i = 0; i < ENEMIES_LIST.size; i++)		ENEMIES_LIST.get(i).free();
		
		ADDS.clear();
		PLAYER_LIST.clear();
		ENEMIES_LIST.clear();
		BOSSES_LIST.clear();
		
		EnemyWeapon.nextGraze = 0;
	}

	public boolean mouvementEtVerif() {								return Physic.mvt(getDimensions().width, getDimensions().height, dir, pos);		}
	public Rectangle getRectangleCollision() {						return getRectangleCollision(COLLISION);										}
	public static void shieldHs() {									shieldHs = true;																}
	public abstract void free();
	@Override	public void setAngle(float mvtToPlayerWithAngle) {															}

}
