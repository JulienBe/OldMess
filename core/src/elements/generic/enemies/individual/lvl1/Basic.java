package elements.generic.enemies.individual.lvl1;

import jeu.mode.EndlessMode;
import behind.SoundMan;
import assets.sprites.Animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Pool;

import elements.generic.components.Dimensions;
import elements.generic.components.EnemyStats;
import elements.generic.components.positionning.Positionner;
import elements.generic.enemies.Enemy;
import elements.particular.particles.Particles;

public class Basic extends Enemy {
	
	public static final Dimensions DIMENSIONS = Dimensions.BASIC;
	public static final int OFFSET_SMOKE = (int) (DIMENSIONS.height * .75f);
	public static final Pool<Basic> POOL = new Pool<Basic>(16) {		protected Basic newObject() {			return new Basic();		}	};

  @Override
  public void added(float x, float y) {
    super.added(x, y);
    dir.y = -getEnemyStats().getSpeed();
  }

  @Override
	public void move() {
		TMP_POS.set(0, DIMENSIONS.halfHeight);
		if (EndlessMode.alternate)
			Particles.smokeMoving((pos.x + DIMENSIONS.halfWidth) + TMP_POS.x, (pos.y + DIMENSIONS.halfHeight) + TMP_POS.y, true, getColor());
		super.move();
	}
	
	@Override	public Animations getAnimation() {		return Animations.BASIC_ENEMY_RED;	}
	@Override	protected Sound getExplosionSound() {	return SoundMan.explosion6;			}
	@Override	public EnemyStats getEnemyStats() {		return EnemyStats.BASIC;			}
	@Override	public Dimensions getDimensions() {		return DIMENSIONS;					}
	@Override	protected void free() {								POOL.free(this);					}

}