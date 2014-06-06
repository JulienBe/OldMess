package elements.generic.enemies.individual.lvl4;

import jeu.CSG;
import jeu.Stats;

import com.badlogic.gdx.utils.Pool;

import elements.generic.enemies.individual.lvl1.Group;
import elements.generic.enemies.individual.lvl3.Group3;

public class Group4 extends Group3 {
	
	public static final Pool<Group4> POOL = new Pool<Group4>() {
		@Override
		protected Group4 newObject() {
			return new Group4();
		}
	};
	private static final int HP = getModulatedPv(Stats.HP_GROUP, 4);
	private static final int XP = getXp(BASE_XP, 4);
	private static final float SPEED = getModulatedSpeed(Group.SPEED, 4), FIRERATE = Group.FIRERATE * 0.8f;
	
	public static Group4 initAll() {
		Group4 e = POOL.obtain();
		Group4 f = POOL.obtain();
		Group4 g = POOL.obtain();
		e.init(CSG.gameZoneWidth - WIDTH);
		f.init(CSG.gameZoneWidth - WIDTH * 2.1f);
		g.init(CSG.gameZoneWidth - WIDTH * 3.2f);
		return e;
	}
	@Override	public float getFirerate() {							return FIRERATE;							}
	@Override	public int getNumberOfShots() {							return 5;	}
	@Override	public int getXp() {									return XP;	}
	@Override	public int getBonusValue() {							return BASE_XP;	}
	@Override	protected int getMaxHp() {								return HP;	}
	@Override	public void free() {									POOL.free(this);	}
	@Override	public float getSpeed() {								return SPEED;	}
	
}
