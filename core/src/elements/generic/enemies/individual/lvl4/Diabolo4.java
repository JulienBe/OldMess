package elements.generic.enemies.individual.lvl4;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.individual.lvl3.Diabolo3;
import elements.generic.weapons.enemies.OrangeBullet;
import jeu.Stats;

public class Diabolo4 extends Diabolo3 {
	
	public static final Pool<Diabolo4> POOL = Pools.get(Diabolo4.class);

	@Override
	protected void shoot() {
		TMP_POS.set(pos.x + DIMENSIONS.halfWidth - OrangeBullet.DIMENSIONS.halfWidth, pos.y + DIMENSIONS.halfWidth - OrangeBullet.DIMENSIONS.halfHeight);
		TMP_DIR.set(-dir.x, -dir.y).nor().rotate(10);
		AbstractShot.straight(Gatling.ORANGE_BULLET, TMP_POS, TMP_DIR, Stats.U8);
		TMP_DIR.rotate(-20);
		AbstractShot.straight(Gatling.ORANGE_BULLET, TMP_POS, TMP_DIR, Stats.U8);
		interval();
	}
	
	@Override	public void free() {					POOL.free(this);					}
	@Override 	protected float getPhaseDuration() {	return 20;							}
	@Override	public int getNumberOfShots() {			return 6;							}
	@Override
	public int getXp() {
		return super.getXp() * 3;
	}
}
