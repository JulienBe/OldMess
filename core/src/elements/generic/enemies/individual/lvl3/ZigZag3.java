package elements.generic.enemies.individual.lvl3;

import jeu.Stats;
import assets.sprites.Animations;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.enemies.individual.lvl1.ZigZag;

public class ZigZag3 extends ZigZag {

	public static final Pool<ZigZag3> POOL = Pools.get(ZigZag3.class);
	private static final Vector2 smokeVector = new Vector2(0, Stats.uDiv2);
	
	@Override	public Animations getAnimation() {			return Animations.ZIG_ZAG_BLUE;	}
	@Override	public void free() {						POOL.free(this);				}
	@Override	public int getColor() {						return BLUE;					}
	@Override	protected float getFloatFactor() {			return 1.5f;					}
	protected Vector2 getSmokeVector() {					return smokeVector;				}
	@Override
	public int getXp() {
		return super.getXp() * 2;
	}
}
