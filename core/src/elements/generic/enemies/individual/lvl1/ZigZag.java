package elements.generic.enemies.individual.lvl1;

import jeu.CSG;
import jeu.Stats;
import jeu.mode.EndlessMode;
import behind.SoundMan;
import assets.sprites.Animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.Dimensions;
import elements.generic.components.EnemyStats;
import elements.generic.components.behavior.Mover;
import elements.generic.components.positionning.Positionner;
import elements.generic.enemies.Enemy;
import elements.particular.particles.Particles;

public class ZigZag extends Enemy {
	
	protected static final Dimensions DIMENSIONS = Dimensions.BASIC;
	protected static final float OFFSET_SMOKE = (int) (DIMENSIONS.height * 0.8f);
	public static final Pool<ZigZag> POOL = Pools.get(ZigZag.class);
	private static final Vector2 smokeVector = new Vector2(0, Stats.uDiv4);

	public void added(float x, float y) {
    if (x > CSG.halfWidth)
		  dir.set(0, -getEnemyStats().getSpeed() * 24);
    else
      dir.set(0, -getEnemyStats().getSpeed() * 24);
    super.added(x, y);
	}
	
	@Override
	public void move() {
		if (EndlessMode.alternate)
			Particles.smokeMoving(pos.x + DIMENSIONS.halfWidth, pos.y + OFFSET_SMOKE, true, getColor(), getSmokeVector());
		Mover.zigZag(this, getFloatFactor());
	}

	protected float getFloatFactor() {					return 1;				}
	protected Vector2 getSmokeVector() {				return smokeVector;		}

	@Override	public Animations getAnimation() {		return Animations.ZIG_ZAG_RED;		}
	@Override	protected Sound getExplosionSound() {	return SoundMan.explosion5;			}
	@Override	public EnemyStats getEnemyStats() {		return EnemyStats.ZIGZAG;			}
	@Override	public Dimensions getDimensions() {		return DIMENSIONS;					}
	@Override	public void free() {					POOL.free(this);					}
}
