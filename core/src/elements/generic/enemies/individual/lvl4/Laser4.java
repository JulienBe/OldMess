package elements.generic.enemies.individual.lvl4;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.individual.lvl3.Laser3;
import elements.generic.weapons.enemies.LaserWeapon;


public class Laser4 extends Laser3 {
	
	public static final Pool<Laser4> POOL = Pools.get(Laser4.class);
	
	
	@Override
	protected void shoot() {
		TMP_POS.set((pos.x + DIMENSIONS.halfWidth - LaserWeapon.DIMENSIONS.halfWidth) + (dir.x / 3), (pos.y + DIMENSIONS.halfHeight - LaserWeapon.DIMENSIONS.halfHeight) + (dir.y / 3));
		AbstractShot.straight(Gatling.LASER, TMP_POS, dir, 1.5f);
		AbstractShot.straight(Gatling.LASER, TMP_POS, dir, 1.6f);
		AbstractShot.straight(Gatling.LASER, TMP_POS, dir, 1.7f);
		interval();
	}
	
	@Override	public void free() {						POOL.free(this);						}
	@Override
	public int getXp() {
		return super.getXp() * 3;
	}
}
