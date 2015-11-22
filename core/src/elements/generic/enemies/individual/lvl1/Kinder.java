package elements.generic.enemies.individual.lvl1;

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
import elements.generic.weapons.enemies.KinderWeapon;


public class Kinder extends Enemy {

	protected static final Dimensions DIMENSIONS = Dimensions.KINDER;
	protected static final float INIT_NEXT_SHOT = Animations.KINDER_TIME_OPEN, PHASE_DURATION = 12;
	public static final Pool<Kinder> POOL = Pools.get(Kinder.class);
	protected int index, shotNumber = 0;
	
	public void init(float x, float y) {
		Positionner.SIDES.set(this);
		nextShot = INIT_NEXT_SHOT;
		index = 0;
	}
	
	@Override
	public void move() {
		angle = dir.angle() + 90;
		switch (index) {
		case 0 :
			if (now > Animations.KINDER_TIME_OPEN)
				index++;
			Mover.straight(this);
			break;
		case 1 :
			if (now > getPhaseDuration())
				index++;
			Mover.rotateNoMove(this, 40);
			break;
		case 2 :
			Mover.goAway(this, 0.01f);
		}
	}
	
	@Override
	protected void shoot() {
		if (index == 1) {
			TMP_POS.set(pos.x + DIMENSIONS.halfWidth - KinderWeapon.DIMENSIONS.halfWidth, pos.y + DIMENSIONS.halfHeight - KinderWeapon.DIMENSIONS.halfWidth);
			AbstractShot.straight(Gatling.KINDER_WEAPON, TMP_POS, TMP_DIR.set(-dir.x, -dir.y), -1.5f);
			
			interval(0);
		}
	}

	protected void interval(int init) {
		shotNumber = AbstractShot.interval(this, 2, 1, shotNumber);
	}
	
	public float getPhaseDuration() {						return PHASE_DURATION;							}
	@Override	protected Sound getExplosionSound() {		return SoundMan.explosion6;						}
	@Override	public EnemyStats getEnemyStats() {			return EnemyStats.KINDER;						}
	@Override	public Animations getAnimation() {			return Animations.KINDER;						}
	@Override	public Dimensions getDimensions() {			return DIMENSIONS;								}
	@Override	public void free() {						POOL.free(this);								}
}

