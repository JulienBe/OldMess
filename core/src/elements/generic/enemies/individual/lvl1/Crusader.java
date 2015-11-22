package elements.generic.enemies.individual.lvl1;

import jeu.Stats;
import behind.SoundMan;
import assets.sprites.Animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.Dimensions;
import elements.generic.components.EnemyStats;
import elements.generic.components.positionning.Positionner;
import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.Enemy;
import elements.generic.weapons.player.PlayerWeapon;

public class Crusader extends Enemy {
	
	protected static final Dimensions DIMENSIONS = Dimensions.CRUSADER;
	public static final Pool<Crusader> POOL = Pools.get(Crusader.class);
	protected static final float INIT_NEXT_SHOT = 4, ROTATION_BETWEEN_SHOTS = 4;
	protected float shootingAngle;
	protected int shotNumber = 3;
	private boolean goodShape = true;

	@Override
	public void added(float x, float y) {
		nextShot = INIT_NEXT_SHOT;
		shootingAngle = 0;
		goodShape = true;
		dir.x = 0;
		dir.y = -getEnemyStats().getSpeed();
    super.added(x, y);
	}
	
	@Override
	protected void shoot() {
		TMP_POS.set(pos.x, pos.y + DIMENSIONS.halfHeight/2 );
		TMP_DIR.set(0, -1).rotate(-shootingAngle++);
		AbstractShot.straight(Gatling.KINDER_WEAPON, TMP_POS, TMP_DIR, Stats.U10);
		
		TMP_POS.x += DIMENSIONS.threeQuarterWidth;
		TMP_DIR.x = -TMP_DIR.x;
		AbstractShot.straight(Gatling.KINDER_WEAPON, TMP_POS, TMP_DIR, Stats.U10);
		
		shootingAngle += ROTATION_BETWEEN_SHOTS;
		interval();
	}

	protected void interval() {
		shotNumber = AbstractShot.interval(this, 3, 2, shotNumber);
	}
	
	@Override
	public boolean stillAlive(PlayerWeapon p) {
		if (hp <= getEnemyStats().getHalfHp()) 
			goodShape = false;
		return super.stillAlive(p);
	}
	
	@Override	public Animations getAnimation() {			return Animations.BLUE_CRUSADER;											}
	@Override	protected Sound getExplosionSound() {		return SoundMan.explosion6;													}
	@Override	public EnemyStats getEnemyStats() {			return EnemyStats.CRUSADER;													}
	@Override	public Dimensions getDimensions() {			return DIMENSIONS;															}
	@Override	public int getShotNumber() {				return shotNumber;															}
	@Override	public boolean isInGoodShape() {			return goodShape;															}
	@Override	public void free() {						POOL.free(this);															}
	@Override	public void addShots(int i) {				shotNumber += i;															}
	@Override	public int getColor() {						return BLUE;																}
	@Override	public int getNumberOfShots() {				return 3;																	}
}
