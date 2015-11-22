package elements.generic.enemies.individual.lvl4;

import jeu.Stats;
import assets.sprites.Animations;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.enemies.individual.lvl3.ZigZag3;

public class ZigZag4 extends ZigZag3 {

	public static final Pool<ZigZag4> POOL = Pools.get(ZigZag4.class);
	private static final Vector2 smokeVector = new Vector2(0, Stats.u);
	
	@Override	public Animations getAnimation() {			return Animations.ZIG_ZAG_GREEN;	}
	@Override	protected Vector2 getSmokeVector() {		return smokeVector;					}
	@Override	public void free() {						POOL.free(this);					}
	@Override	public int getColor() {						return GREEN;						}
	@Override	protected float getFloatFactor() {			return 2;							}
	@Override
	public int getXp() {
		return super.getXp() * 3;
	}
}
