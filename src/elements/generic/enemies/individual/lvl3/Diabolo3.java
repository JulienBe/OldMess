package elements.generic.enemies.individual.lvl3;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.enemies.individual.lvl1.Diabolo;

public class Diabolo3 extends Diabolo {
	
	public static final Pool<Diabolo3> POOL = Pools.get(Diabolo3.class);
	private static final float FIRERATE = 0.15f * MOD_FIRERATE;
	private int shotNumber = 0;
	
	@Override	protected void interval() {		shotNumber = AbstractShot.interval(this, 4, 2, shotNumber);	}
	@Override	public void free() {					POOL.free(this);				}
	@Override 	public float getFirerate() {			return FIRERATE;				}
	@Override 	protected float getPhaseDuration() {	return 14;						}
	@Override	public int getNumberOfShots() {			return 4;						}
}
