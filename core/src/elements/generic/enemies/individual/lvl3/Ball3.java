package elements.generic.enemies.individual.lvl3;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.enemies.individual.lvl1.Ball;

public class Ball3 extends Ball {
	
	public static final Pool<Ball3> POOL = Pools.get(Ball3.class);
	protected int shotNumber;
	
	@Override
	public void reset() {
		shotNumber = 0;
		super.reset();
	}

	@Override
	protected void shoot() {
		super.shoot();
		shotNumber = AbstractShot.interval(this, 3, 2, shotNumber);
	}
	
	@Override	public void free() {							POOL.free(this);						}
	@Override
	public int getXp() {
		return super.getXp() * 2;
	}
}
