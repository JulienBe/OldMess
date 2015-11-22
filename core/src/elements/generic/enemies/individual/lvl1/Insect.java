package elements.generic.enemies.individual.lvl1;

import jeu.Stats;
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
import elements.generic.weapons.Weapon;

public class Insect extends Enemy {
	
	protected static final Dimensions DIMENSIONS = Dimensions.INSECT;
	public static final Pool<Insect> POOL = Pools.get(Insect.class);
	protected static final float INIT_NEXT_SHOT = 1;
	private boolean goodShape;
	
	public void init(float x, float y) {
		nextShot = INIT_NEXT_SHOT;
		dir.set(0, -getEnemyStats().getSpeed()).rotate(-20);
		Positionner.MIDDLE.set(this);
		goodShape = true;
		angle = dir.angle() + 90;
	}
	
	@Override
	protected void move() {
		Mover.insectMove(this, 4, 12, Stats.uSur8);
	}

	@Override
	protected void shoot() {
		TMP_POS.set(pos.x + DIMENSIONS.halfWidth, pos.y + DIMENSIONS.halfWidth);
		TMP_DIR.set(dir.x, dir.y).nor().scl(Stats.U10);
		AbstractShot.shotgun(Gatling.INSECT, TMP_DIR, TMP_POS, 3, getNumberOfShotsRandom(), 15, 0, DIMENSIONS.halfWidth);
	}

	protected int getNumberOfShotsRandom() {				return 3;															}
	@Override	protected Sound getExplosionSound() {		return SoundMan.explosion5;											}
	@Override	public Animations getAnimation() {			return Animations.INSECT;											}
	@Override	public EnemyStats getEnemyStats() {			return EnemyStats.INSECT;											}
	@Override	public Dimensions getDimensions() {			return DIMENSIONS;													}
	@Override	public boolean isInGoodShape() {			return goodShape;													}
	@Override	public void free() {						POOL.free(this);													}
	@Override	public float getAngle() {					return angle;														}
	@Override
	public boolean isTouched(Weapon a) {
		if (hp < getEnemyStats().getHalfHp()) {
			goodShape = false;
		}
		return super.isTouched(a);
	}

}
