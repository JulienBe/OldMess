package elements.generic.enemies.individual.lvl3;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.enemies.individual.lvl1.RoundAndRound;

public class RoundAndRound3 extends RoundAndRound {

	public static final Pool<RoundAndRound3> POOL = Pools.get(RoundAndRound3.class);
	
	@Override
	protected void shoot() {
		super.shoot();
		shoot(-1);
	}
	protected void interval() {
		shotInterval = AbstractShot.interval(this, 12, 1.5f, shotInterval);
	}
	@Override	public void free() {				POOL.free(this);				}
	@Override
	public int getXp() {
		return super.getXp() * 2;
	}
}
