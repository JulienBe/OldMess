package elements.generic.enemies.individual.lvl3;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.enemies.individual.lvl1.Plane;
import jeu.Stats;

public class Plane3 extends Plane {
	
	public static final Pool<Plane3> POOL = Pools.get(Plane3.class);
	private static final int HP = getModulatedPv(Stats.PLANE_HP, 3), DEMI_HP = HP / 2, XP = getXp(BASE_XP, 3);
	protected static final float SPEED = Plane.SPEED19 * Stats.VNV3, HALF_SPEED = SPEED / 2, FIRERATE = 0.3f * MOD_FIRERATE;
	
	@Override
	public float getSpeed() {
		if (isInGoodShape())
			return SPEED;
		return HALF_SPEED;
	}
	@Override	protected int getMaxHp() {				return HP;						}
	@Override	public int getXp() {					return XP;						}
	@Override	public int getBonusValue() {			return BASE_XP;					}
	@Override	protected int getHalfHp() {				return DEMI_HP;					}
	@Override	public float getFirerate() {			return FIRERATE;				}
	@Override	public void free() {					POOL.free(this);				}
}
