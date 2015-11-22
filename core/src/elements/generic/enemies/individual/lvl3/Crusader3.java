package elements.generic.enemies.individual.lvl3;

import jeu.CSG;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.enemies.individual.lvl1.Crusader;

public class Crusader3 extends Crusader {

	public static final Pool<Crusader3> POOL = Pools.get(Crusader3.class);
	
	@Override
	protected void interval() {
		shotNumber = AbstractShot.interval(this, 3, 1 + CSG.R.nextFloat() * 2, shotNumber);
	}
	
	@Override	public void free() {				POOL.free(this);				}
	@Override
	public int getXp() {
		return super.getXp() * 2;
	}
}
