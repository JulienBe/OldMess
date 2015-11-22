package elements.generic.enemies.individual.lvl3;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.enemies.individual.lvl1.Plane;

public class Plane3 extends Plane {
	
	public static final Pool<Plane3> POOL = Pools.get(Plane3.class);
	
	@Override	public void free() {					POOL.free(this);				}
	@Override
	public int getXp() {
		return super.getXp() * 2;
	}
}
