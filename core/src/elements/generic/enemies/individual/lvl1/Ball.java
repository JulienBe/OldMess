package elements.generic.enemies.individual.lvl1;

import jeu.Physic;
import jeu.Stats;
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
import elements.generic.weapons.enemies.PinkBullet;
import elements.generic.weapons.player.PlayerWeapon;

public class Ball extends Enemy {
	
	protected static final float INIT_NEXT_SHOT = 3f;
	protected static final Dimensions DIMENSIONS = Dimensions.BALL;
	public static final Pool<Ball> POOL = Pools.get(Ball.class);

	@Override
	public void added(float x, float y) {
		nextShot = INIT_NEXT_SHOT;
		dir.set(0, - getEnemyStats().getSpeed());
    super.added(x, y);
	}

	@Override
	public float getAngle() {
		if (!EndlessMode.alternate && !EndlessMode.triggerStop)
			angle = Physic.getAngleWithPlayer(pos, DIMENSIONS.halfWidth, DIMENSIONS.halfWidth) + 90;
		return angle;
	}
	
	@Override
	protected void shoot() {
		TMP_POS.set(pos.x + DIMENSIONS.halfWidth - PinkBullet.DIMENSIONS.halfWidth, pos.y + DIMENSIONS.halfHeight - PinkBullet.DIMENSIONS.halfHeight);
		AbstractShot.shootOnPlayer(Gatling.CYAN_BULLET, TMP_POS, DIMENSIONS.halfWidth, Stats.U12);
	}
	
	@Override
	public void move() {
		if (now < 30)	Mover.ball(this, 1.002f);
		else			Mover.goAway(this, 0.007f);
	}
	
	@Override	protected Sound getExplosionSound() {		return SoundMan.explosion3;						}
	@Override	public Animations getAnimation() {			return Animations.BALL;							}
	@Override	public EnemyStats getEnemyStats() {			return EnemyStats.BALL;							}
	@Override	public Dimensions getDimensions() {			return DIMENSIONS;								}
	@Override	public void free() {						POOL.free(this);								}
	@Override	public int getColor() {						return BLUE;									}
	@Override	public boolean stillAlive(PlayerWeapon a) {
		nextShot -= 1;
		return super.stillAlive(a);					
	}

}
