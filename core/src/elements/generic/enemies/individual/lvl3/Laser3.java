package elements.generic.enemies.individual.lvl3;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.enemies.individual.lvl1.Laser;


public class Laser3 extends Laser {
	
	public static final Pool<Laser3> POOL = Pools.get(Laser3.class);
	private int shotNumber = 0;
	
	@Override
	protected void shoot() {
		super.shoot();
		interval();
	}

	protected void interval() {
		shotNumber = AbstractShot.interval(this, 2, 1, shotNumber);
	}
	
	@Override	public void free() {					POOL.free(this);							}
	@Override	protected float getRotateTime() {		return 16.2f;									}
	@Override
	public int getXp() {
		return super.getXp() * 2;
	}
	
}
