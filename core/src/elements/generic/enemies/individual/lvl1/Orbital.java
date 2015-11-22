package elements.generic.enemies.individual.lvl1;

import jeu.Stats;
import jeu.mode.extensions.Score;
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
import elements.generic.weapons.enemies.ViciousBullet;
import elements.particular.players.Player;

public class Orbital extends Enemy {
	
	protected static final Dimensions DIMENSIONS = Dimensions.VICIOUS;
	public static final float INIT_NEXT_SHOT = 1;
	public static final Pool<Orbital> POOL = Pools.get(Orbital.class);
	private int xp, hp;
	
	public void init(float x, float y) {
		nextShot = INIT_NEXT_SHOT;
		Positionner.MIDDLE.set(this);
		xp = (int) (Score.score / 80f);
		if (xp > 500)
			xp = 500;
		hp = xp / 2 ;
	}
	
	@Override
	protected void move() {
		Mover.orbitPlayer(this, Stats.U10);
	}
	
	@Override
	protected void shoot() {
		TMP_POS.set(pos.x + DIMENSIONS.halfWidth - ViciousBullet.DIMENSIONS.halfWidth, pos.y + DIMENSIONS.halfWidth - ViciousBullet.DIMENSIONS.halfWidth);
		TMP_DIR.set((pos.x + DIMENSIONS.halfWidth) - Player.xCenter, (pos.y + DIMENSIONS.halfHeight) - Player.yCenter);
		AbstractShot.straight(Gatling.VICIOUS, TMP_POS, TMP_DIR, Stats.U20);
	}

	@Override	protected Sound getExplosionSound() {		return SoundMan.explosion5;											}
	@Override	public EnemyStats getEnemyStats() {			return EnemyStats.BALL;												}
	@Override	public Animations getAnimation() {			return Animations.BALL;												}
	@Override	public Dimensions getDimensions() {			return DIMENSIONS;													}
	@Override	public void free() {						POOL.free(this);													}
	@Override	public int getXp() {						return xp;															}
	@Override	public int getBonusValue() {				return xp;															}
	@Override	protected int getMaxHp() {					return hp;															}
}
