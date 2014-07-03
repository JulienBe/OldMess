package elements.generic.enemies.individual.lvl4;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.individual.lvl1.Diabolo;
import elements.generic.enemies.individual.lvl3.Diabolo3;
import elements.generic.weapons.enemies.OrangeBullet;
import jeu.Stats;

public class Diabolo4 extends Diabolo3 {
	
	public static final Pool<Diabolo4> POOL = Pools.get(Diabolo4.class);
	private static final int LVL = 4, HP = getModulatedPv(Stats.HP_DIABOLO, LVL), XP = getXp(BASE_XP, LVL);
	private static final float SPEED = Diabolo.SPEED18 * 1.2f, HALF_SPEED = SPEED / 2, FIRERATE = Diabolo.FIRERATE * 0.4f * MOD_FIRERATE;

	@Override
	protected void shoot() {
		TMP_POS.x = (pos.x + DIMENSIONS.halfWidth - OrangeBullet.DIMENSIONS.halfWidth);
		TMP_POS.y = (pos.y + DIMENSIONS.halfWidth - OrangeBullet.DIMENSIONS.halfHeight);
		TMP_DIR.x = -dir.x;
		TMP_DIR.y = -dir.y;
		TMP_DIR.nor();
		TMP_DIR.rotate(10);
		AbstractShot.straight(Gatling.ORANGE_BULLET, TMP_POS, TMP_DIR, Stats.U8);
		TMP_DIR.rotate(-20);
		AbstractShot.straight(Gatling.ORANGE_BULLET, TMP_POS, TMP_DIR, Stats.U8);
		interval();
	}
	
	@Override	protected float getDemiVitesse() {		return HALF_SPEED;					}
	@Override	public void free() {					POOL.free(this);					}
	@Override 	public float getFirerate() {			return FIRERATE;					}
	@Override	public int getBonusValue() {			return BASE_XP;						}
	@Override	public float getSpeed() {				return SPEED;						}
	@Override	protected int getMaxHp() {				return HP;							}
	@Override	public int getXp() {					return XP;							}
	@Override 	protected float getPhaseDuration() {	return 20;							}
	@Override	public int getNumberOfShots() {			return 6;							}
}
