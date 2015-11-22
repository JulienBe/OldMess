package elements.generic.enemies.individual.lvl1;

import jeu.Physic;
import jeu.mode.EndlessMode;
import behind.SoundMan;
import assets.sprites.Animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.Dimensions;
import elements.generic.components.EnemyStats;
import elements.generic.components.behavior.Mover;
import elements.generic.components.positionning.Positionner;
import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.Enemy;
import elements.generic.weapons.enemies.OrangeBullet;

public class RoundAndRound extends Enemy {
	
	protected static final Dimensions DIMENSIONS = Dimensions.ROUND_N_ROUND;
	public static final int SHOTS_GAP = 5;
	protected static final float INIT_NEXT_SHOT = 0;
	private static final float OFFSET_TIR = DIMENSIONS.halfWidth - OrangeBullet.DIMENSIONS.halfWidth;
	public static final Pool<RoundAndRound> POOL = Pools.get(RoundAndRound.class);
	private boolean shootRight = true, leftOfTheScreen;
	protected int shotNumber = 0, shotInterval;
	
	public void init(float x, float y) {
		dir.set(0, -getEnemyStats().getSpeed());
		Positionner.UP_WIDE.set(this);
		nextShot = INIT_NEXT_SHOT;
		now = 1;
	}

	@Override
	protected void shoot() {
		if (now < 1.5f)
			leftOfTheScreen = Physic.isLeft(pos, DIMENSIONS.halfWidth);
		shoot(1);
	}
	
	protected void shoot(float side) {
		TMP_POS.set(pos.x + OFFSET_TIR, pos.y + OFFSET_TIR);
		shootRight = AbstractShot.sweep(Gatling.ORANGE_BULLET, dir, TMP_POS, 1.5f, this, shootRight, 3, 0, SHOTS_GAP * side, shotNumber);
		interval();
	}

	protected void interval() {
		shotInterval = AbstractShot.interval(this, 6, 2, shotInterval);
	}
	
	@Override	
	protected void move() {
		Mover.U(this);
		if (!EndlessMode.alternate)
			angle = dir.angle() + 90;
	}
	
	@Override	public Animations getAnimation() {				return Animations.ROUND_N_ROUND;	}
	@Override	public EnemyStats getEnemyStats() {				return EnemyStats.ROUND_N_ROUND;	}
	@Override	protected Sound getExplosionSound() {			return SoundMan.explosion6;			}
	@Override	public boolean getWay() {						return leftOfTheScreen;				}
	@Override	public boolean toLeft() {						return leftOfTheScreen;				}
	@Override	public Dimensions getDimensions() {				return DIMENSIONS;					}
	@Override	public void addShots(int i) {					shotNumber += i;					}
	@Override	public void free() {							POOL.free(this);					}
	@Override	public int getColor() {							return GREEN;						}
}
