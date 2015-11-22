package elements.generic.enemies.individual.lvl3;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.enemies.individual.lvl1.ShooterFrag;


public class ShooterFrag3 extends ShooterFrag {
	
	public static final Pool<ShooterFrag3> POOL = Pools.get(ShooterFrag3.class);
	
	@Override	public void free() {				POOL.free(this);				}
	@Override
	public int getXp() {
		return super.getXp() * 2;
	}
}
