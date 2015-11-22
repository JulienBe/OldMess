package elements.generic.enemies.individual.lvl1;

import jeu.CSG;
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
import elements.generic.weapons.enemies.OrangeBullet;

public class Diabolo extends Enemy {
	
	protected static final Dimensions DIMENSIONS = Dimensions.DIABOLO;
	public static final int PHASE_DURATION = 15;
	protected static final float WIDTH2 = DIMENSIONS.width * 2, INIT_NEXT_SHOT = 3;
	public static final Pool<Diabolo> POOL = Pools.get(Diabolo.class);
	private int shotNumber, index;

	@Override
	public void added(float x, float y) {
		if (CSG.R.nextBoolean())
			pos.set(CSG.halfWidth - Stats.U10, CSG.height);
		else
			pos.set(CSG.halfWidth + Stats.U10, CSG.height);
		dir.set(-getEnemyStats().getSpeed() / 5, -getEnemyStats().getSpeed());
		nextShot = INIT_NEXT_SHOT;
		index = 0;
    super.added(x, y);
	}
	
	@Override
	protected void move() {
		switch (index) {
		case 0:
			Mover.straight(this);
			if (pos.y + WIDTH2 < CSG.halfHeight)
				index++;
			break;
		case 1:
			Mover.ovale(this, 0.05f);
			if (now > getPhaseDuration())
				index++;
			break;
		case 2:
			Mover.goAway(this, 0.03f);
		}
	}

	@Override
	protected void shoot() {
		TMP_POS.set(pos.x + DIMENSIONS.halfWidth - OrangeBullet.DIMENSIONS.halfWidth, pos.y + DIMENSIONS.halfWidth - OrangeBullet.DIMENSIONS.halfHeight);
		AbstractShot.straight(Gatling.ORANGE_BULLET, TMP_POS, TMP_DIR.set(-dir.x, -dir.y).nor(), Stats.U10);
		interval();
	}

	protected void interval() {							AbstractShot.rafale(this);						}
	protected float getPhaseDuration() {				return 6.65f;									}
	@Override	protected Sound getExplosionSound() {	return SoundMan.explosion1;						}
	@Override	public Animations getAnimation() {		return Animations.DIABOLO;						}
	@Override	public EnemyStats getEnemyStats() {		return EnemyStats.DIABOLO;						}
	@Override	public Dimensions getDimensions() {		return DIMENSIONS;								}
	@Override	public int getShotNumber() {			return shotNumber;								}
	@Override	public void addShots(int i) {			shotNumber += i;								}
	@Override	public void free() {					POOL.free(this);								}
	@Override	public int getNumberOfShots() {			return 2;										}
}
