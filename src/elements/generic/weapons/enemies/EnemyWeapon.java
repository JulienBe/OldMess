package elements.generic.weapons.enemies;

import jeu.Physic;
import jeu.Stats;
import jeu.mode.EndlessMode;
import assets.AssetMan;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.generic.weapons.Weapon;
import elements.particular.Player;
import elements.particular.bonuses.XP;

public abstract class EnemyWeapon extends Weapon implements Poolable {
	
	private static float tmpFloat;
	public static float nextGraze = 0;
	private static final Vector2 tmpV = new Vector2();
	protected static final float ALTERNATE_COLOR = AssetMan.convertARGB(1, 0.8f, 0.7f, 08f), KINDER_WEAPON_COLOR = AssetMan.convertARGB(1, 1f, 1f, 0.3f);
	
	public boolean testCollisionVaisseau() {
		tmpV.x = Player.xCenter;
		tmpV.y = Player.yCenter;
		tmpFloat = tmpV.dst(pos.x + getDimensions().halfWidth, pos.y + getDimensions().halfHeight);
		
//		if (Player.bouclier)
		for (int i = 0; i < Player.shield; i++)
			tmpFloat -= Stats.uSur2;
		
		if (tmpFloat < getDimensions().width + Stats.UU || tmpFloat < getDimensions().height + Stats.UU) {
			if (nextGraze < EndlessMode.now) {
				final XP xp = XP.POOL.obtain();
				xp.init(pos.x + getDimensions().halfWidth, pos.y + getDimensions().halfHeight, 10);
//				xp.direction.x = -dir.x * EndlessMode.delta;
//				xp.direction.y = -dir.y * EndlessMode.delta;
				nextGraze = EndlessMode.now + .1f;
			}
			if (tmpFloat < getDimensions().halfWidth || tmpFloat < getDimensions().halfHeight) {
				Player.touched();
				return true;
			}
		}
		return false;
	}

	public boolean testCollsionAdds() {
		return Physic.isAddTouched(pos, getDimensions().width, getDimensions().height);
	}
	
	@Override	public Vector2 getPosition() {						return pos;					}
	@Override	public Vector2 getDirection() {						return dir;					}
	@Override	public boolean getWay() {							return false;				}
	@Override	public float getNow() {								return now;					}
	
	public void init(Vector2 position, float dEMI_WIDTH, float demiHauteur, float modifVitesse) {
		position.x = position.x + dEMI_WIDTH - getDimensions().halfWidth;
		position.y = position.y + demiHauteur - getDimensions().halfHeight;
		ENEMIES_LIST.add(this);
	}

	/**
	 * L'envoie vers le bas
	 * @param position
	 * @param modifVitesse
	 * @param boss TODO
	 */
	public void init(Vector2 position, float modifVitesse, boolean boss) {
		this.pos.x = position.x;
		this.pos.y = position.y;
		dir.x = 0;
		dir.y = -1 * modifVitesse;
		if (boss) {
			BOSSES_LIST.add(this);
		} else {
			ENEMIES_LIST.add(this);
		}
	}

	public void init(Vector2 position, float modifVitesse, Vector2 direction, boolean boss) { 
		this.pos.x = position.x;
		this.pos.y = position.y;
		this.dir.x = direction.x * modifVitesse;
		this.dir.y = direction.y * modifVitesse;
		this.angle = dir.angle() + 90;
		System.out.println(dir);
		if (boss) {
			BOSSES_LIST.add(this);
		} else {
			ENEMIES_LIST.add(this);
		}
	}
	
	public void init(Vector2 position, float modifVitesse, float angle) {
		this.pos.x = position.x;
		this.pos.y = position.y;
		this.dir.x = 0;
		this.dir.y = 1 * modifVitesse;
		dir.rotate(angle);
		this.pos.x += dir.x / 1.35f;
		this.pos.y += dir.y / 1.35f;
		ENEMIES_LIST.add(this);
	}

	public void init(Vector2 position, Vector2 direction) {
		this.pos.x = position.x;
		this.pos.y = position.y;
		this.dir.x = direction.x;
		this.dir.y = direction.y;
		ENEMIES_LIST.add(this);
	}
	
	public void init(Vector2 position, Vector2 direction, float modifVitesse) {
		this.pos.x = position.x;
		this.pos.y = position.y;
		this.dir.x = direction.x * modifVitesse;
		this.dir.y = direction.y * modifVitesse;
		ENEMIES_LIST.add(this);
	}

	public static void clear() {
		for (Weapon a : PLAYER_LIST)
			a.free();
		for (Weapon a : ENEMIES_LIST)
			a.free();
		PLAYER_LIST.clear();
		ENEMIES_LIST.clear();
	}

}
