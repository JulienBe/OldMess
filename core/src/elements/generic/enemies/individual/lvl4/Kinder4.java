package elements.generic.enemies.individual.lvl4;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.individual.lvl3.Kinder3;
import elements.generic.weapons.enemies.KinderWeapon;


public class Kinder4 extends Kinder3 {
	
	public static final Pool<Kinder4> POOL = Pools.get(Kinder4.class);
	private static final float PHASE_DURATION = 22;
	
	@Override
	protected void shoot() {
		super.shoot();
		if (index == 1) {
			TMP_POS.set((pos.x + DIMENSIONS.halfWidth - KinderWeapon.DIMENSIONS.halfWidth), (pos.y + DIMENSIONS.halfHeight - KinderWeapon.DIMENSIONS.halfHeight));
			TMP_DIR.set(-dir.x, -dir.y);
			TMP_POS.add(TMP_DIR.x / 3, TMP_DIR.y / 3);
			AbstractShot.straight(Gatling.CYAN_BULLET, TMP_POS, TMP_DIR, 1.5f);
		}
	}
	
	@Override	public void free() {					POOL.free(this);				}
	@Override	public float getPhaseDuration() {		return PHASE_DURATION;			}
	@Override
	public int getXp() {
		return super.getXp() * 3;
	}
}

