package elements.generic.weapons.enemies;

import jeu.CSG;
import jeu.Physic;
import jeu.Stats;
import jeu.mode.EndlessMode;

import com.badlogic.gdx.math.Vector2;
import elements.generic.weapons.Weapon;
import elements.particular.players.Player;
import elements.particular.bonuses.XP;

public abstract class EnemyWeapon extends Weapon {
	
	private static float tmpFloat;
	public static float nextGraze = 0;
	private static final Vector2 tmpV = new Vector2();
	protected static final float ALTERNATE_COLOR = CSG.gm.palette().convertARGB(1, 0.8f, 0.7f, 08f), KINDER_WEAPON_COLOR = CSG.gm.palette().convertARGB(1, 1f, 0.5f, 0.7f);
	
	public boolean testCollisionVaisseau(Player player) {
		tmpV.set(Player.xCenter, Player.yCenter);
		tmpFloat = tmpV.dst(pos.x + getDimensions().halfWidth, pos.y + getDimensions().halfHeight);
		
		for (int i = 0; i < Player.shield; i++)
			tmpFloat -= Stats.uDiv2;
		
		if (tmpFloat < getDimensions().width + Stats.U2 || tmpFloat < getDimensions().height + Stats.U2) {
			if (nextGraze < EndlessMode.now) {
				final XP xp = XP.POOL.obtain();
				xp.init(pos.x + getDimensions().halfWidth, pos.y + getDimensions().halfHeight, 10);
				nextGraze = EndlessMode.now + .1f;
			}
			if (tmpFloat < getDimensions().halfWidth || tmpFloat < getDimensions().halfHeight) {
				player.touched();
				return true;
			}
		}
		return false;
	}

//	public boolean testCollsionAdds() {
//		return Physic.isAddTouched(pos, getDimensions().height, getDimensions().height);
//	}
	
	public void init(Vector2 position, float halfWidth, float demiHauteur, float modifVitesse) {
		position.set(position.x + halfWidth - getDimensions().halfWidth, position.y + demiHauteur - getDimensions().halfHeight);
		ENEMIES_LIST.add(this);
	}

	public void init(Vector2 position, float modifVitesse, boolean boss) {
		this.pos.set(position.x, position.y);
		dir.set(0, -modifVitesse);
		if (boss) {
			BOSSES_LIST.add(this);
		} else {
			ENEMIES_LIST.add(this);
		}
	}

	public void init(Vector2 position, float modifVitesse, Vector2 direction, boolean boss) { 
		this.pos.set(position.x, position.y);
		this.pos.y = position.y;
		this.dir.set(direction.x, direction.y).scl(modifVitesse);
		this.angle = dir.angle() + 90;
		if (boss) {
			BOSSES_LIST.add(this);
		} else {
			ENEMIES_LIST.add(this);
		}
	}
	
	public void init(Vector2 position, float modifVitesse, float angle) {
		this.pos.set(position.x, position.y);
		this.dir.x = 0;
		this.dir.y = 1 * modifVitesse;
		dir.rotate(angle);
		this.pos.x += dir.x / 1.35f;
		this.pos.y += dir.y / 1.35f;
		ENEMIES_LIST.add(this);
	}

	public void init(Vector2 position, Vector2 direction) {
		this.pos.set(position.x, position.y);
		this.dir.set(direction.x, direction.y);
		ENEMIES_LIST.add(this);
	}
	
	public void init(Vector2 position, Vector2 direction, float modifVitesse) {
		this.pos.set(position.x, position.y);
		this.dir.set(direction.x, direction.y).scl(modifVitesse);
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
