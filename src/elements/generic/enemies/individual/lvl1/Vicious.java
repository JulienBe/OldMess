package elements.generic.enemies.individual.lvl1;

import jeu.Stats;
import jeu.mode.extensions.Score;
import assets.SoundMan;
import assets.sprites.Animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.Dimensions;
import elements.generic.components.positionning.Positionner;
import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.Enemy;
import elements.generic.weapons.enemies.ViciousBullet;

public class Vicious extends Enemy {
	
	protected static final Dimensions DIMENSIONS = Dimensions.VICIOUS;
	public static final int EXPLOSION = 50;
	public static final float FIRERATE = 3 * MOD_FIRERATE, INIT_NEXT_SHOT = 1, SPEED3 = getModulatedSpeed(3, 1);
	public static final Pool<Vicious> POOL = Pools.get(Vicious.class);
	private int xp, hp;
	
	public void init() {
		nextShot = INIT_NEXT_SHOT;
		dir.set(0, -getSpeed());
		Positionner.MIDDLE.set(this);
		xp = (int) (Score.score / 80f);
		if (xp > 500)
			xp = 500;
		hp = xp / 2 ;
	}
	
	@Override
	protected void shoot() {
		dir.y -= Stats.uDiv4;
		TMP_POS.set(pos.x + DIMENSIONS.halfWidth - ViciousBullet.DIMENSIONS.halfWidth, pos.y + DIMENSIONS.halfWidth - ViciousBullet.DIMENSIONS.halfWidth);
		TMP_DIR.set(-dir.x * 3, -dir.y * 3);
		AbstractShot.shotgun(Gatling.VICIOUS, TMP_DIR, TMP_POS, 4, 4, 120, 2, DIMENSIONS.halfWidth);
	}

	@Override	protected Sound getExplosionSound() {		return SoundMan.explosion5;											}
	@Override	public Animations getAnimation() {			return Animations.INSECT;											}
	@Override	public Dimensions getDimensions() {			return DIMENSIONS;													}
	@Override	public int getExplosionCount() {			return EXPLOSION;													}
	@Override 	public float getFirerate() {				return FIRERATE;													}
	@Override	public void free() {						POOL.free(this);													}
	@Override	public float getSpeed() {					return SPEED3;														}
	@Override	public int getXp() {						return xp;															}
	@Override	public int getBonusValue() {				return xp;															}
	@Override	protected int getMaxHp() {					return hp;															}
}
