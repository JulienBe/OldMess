package elements.generic.enemies.individual.lvl1;

import jeu.Stats;
import jeu.mode.EndlessMode;
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
import elements.generic.weapons.enemies.Fireball;
import elements.generic.weapons.player.PlayerWeapon;


public class Shooter extends Enemy {
	
	protected static final Dimensions DIMENSIONS = Dimensions.SHOOTER;
	protected static final float xOffset = DIMENSIONS.halfWidth - Fireball.DIMENSIONS.halfWidth/1.5f, INIT_NEXT_SHOT = 1.5f;
	public static final Pool<Shooter> POOL = Pools.get(Shooter.class);
	private boolean goodShape = true;

	@Override
	public void added(float x, float y) {
		nextShot = 2f;
		dir.set(0, -getEnemyStats().getSpeed());
		goodShape = true;
		super.added(x, y);
	}
	
	@Override
	protected void move() {
		if (!isInGoodShape())
			pos.x += getDerive() * EndlessMode.delta;
		super.move();
	}
	
	@Override
	public boolean stillAlive(PlayerWeapon a) {
		if (hp < getEnemyStats().getHalfHp()) {
			dir.rotate(2);
			goodShape = false;
		} else {
			goodShape = true;
		}
		return super.stillAlive(a);
	}
	
	@Override
	protected void shoot() {
		TMP_POS.set(pos.x + (DIMENSIONS.halfWidth - Fireball.DIMENSIONS.halfWidth),  pos.y - Fireball.DIMENSIONS.height);
		AbstractShot.shootDown(Gatling.FIREBALL, TMP_POS, Stats.U12);
	}
	
	@Override	protected Sound getExplosionSound() {		return SoundMan.explosion5;				}
	@Override	public Animations getAnimation() {			return Animations.SHOOTER;				}
	@Override	public EnemyStats getEnemyStats() {			return EnemyStats.SHOOTER;				}
	@Override	public Dimensions getDimensions() {			return DIMENSIONS;						}
	@Override	public boolean isInGoodShape() {			return goodShape;						}
	@Override	public void free() {						POOL.free(this);						}
	@Override	public int getColor() {						return BLUE;							}
	protected float getDerive() {							return getEnemyStats().getSpeed() / 4;	}


}
