package elements.generic.enemies.individual.lvl3;

import assets.sprites.Animations;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.EnemyStats;
import elements.generic.components.behavior.Mover;
import elements.generic.enemies.individual.lvl1.Basic;

public class Basic3 extends Basic {
	
	public static final Pool<Basic3> POOL = Pools.get(Basic3.class);

	@Override
	public void move() {
		Mover.detectX(this, 0.2f);
		super.move();
	}
	@Override	public Animations getAnimation() {			return Animations.BASIC_ENEMY_BLUE;	}
	@Override	public void free() {										POOL.free(this);					}
	@Override	public int getColor() {									return BLUE;						}

  @Override
  public int getXp() {
    return super.getXp() * 2;
  }
}
