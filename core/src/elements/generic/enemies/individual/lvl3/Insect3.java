package elements.generic.enemies.individual.lvl3;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.enemies.individual.lvl1.Insect;


public class Insect3 extends Insect {
	
	public static final Pool<Insect3> POOL = Pools.get(Insect3.class);
	
	@Override	protected int getNumberOfShotsRandom() {	return 6;						}
	@Override	public void free() {						POOL.free(this);				}
	@Override
	public int getXp() {
		return super.getXp() * 2;
	}
}
