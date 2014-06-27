package elements.generic.enemies.individual.bosses;

import jeu.CSG;
import jeu.Stats;
import jeu.mode.EndlessMode;
import assets.SoundMan;
import assets.sprites.Animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.Dimensions;
import elements.generic.components.behavior.Mover;
import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.Enemy;
import elements.generic.enemies.Progression;
import elements.generic.weapons.enemies.Rainbow;
import elements.generic.weapons.enemies.Mine;
import elements.generic.weapons.player.PlayerWeapon;

public class BossMine extends Enemy {

	protected static final Dimensions DIMENSIONS = Dimensions.BOSS_MINE;
	public static final Pool<BossMine> POOL = Pools.get(BossMine.class);
	private static final float SPEED6 = getModulatedSpeed(48, 1), FIRERATE = .1f, FIRERATE2 = 1;
	private static int pvPhase2;
	private boolean goodShape, shootDir;
	private int shotNumber = 0;
	private int shotInterval = 0;

	public BossMine() {
		pos.set(CSG.gameZoneHalfWidth - DIMENSIONS.halfWidth, CSG.SCREEN_HEIGHT);
	}
	
	public void reset() {
		init();
		super.reset();
	}

	public void init() {
		goodShape = true;
		nextShot = 3f;
		pos.set(CSG.gameZoneHalfWidth - DIMENSIONS.halfWidth, CSG.SCREEN_HEIGHT);
		dir.set(0, -getSpeed());
	}
	
	@Override
	protected void move() {
		if (isInGoodShape())
			Mover.ancorY(this, CSG.HEIGHT_8_10);
		else 
			Mover.ancorY(this, CSG.HEIGHT_7_10);
		Mover.ancorX(this, 50);
		Mover.ball(this, 1.1f);
		if (!isInGoodShape()) {
			angle += EndlessMode.delta * 90;
		}
	}
	
	@Override
	protected void shoot() {
		if (isInGoodShape()) {
			TMP_POS.set(pos.x + DIMENSIONS.halfWidth - Rainbow.DIMENSIONS.halfWidth, pos.y);
			TMP_DIR.set(0, -1);
			for (int i = 0; i < 5; i++) {
				shootDir = AbstractShot.sweep(Gatling.RAINBOW, TMP_DIR, TMP_POS, Stats.U15, this, shootDir, 4, 0, Math.abs((now % 10) - 5) + 1.5f, shotNumber);
			}
		} else {
			TMP_POS.set(pos.x + DIMENSIONS.halfWidth - Mine.DIMENSIONS.halfWidth, pos.y + DIMENSIONS.halfHeight - Mine.DIMENSIONS.halfWidth);
			// offset
			CSG.tmpPos.set(0, DIMENSIONS.halfHeight).rotate(angle);
			TMP_POS.add(CSG.tmpPos);
			final int rnd = CSG.R.nextInt(4) + 1;
			for (int i = -rnd; i < rnd+1; i++)
				AbstractShot.straight(Gatling.MINE, TMP_POS, TMP_DIR.set(0, -1).rotate(angle + (i * 30)), -Stats.U10);
		}
		shotInterval = AbstractShot.interval(this, EndlessMode.difficulty, 0.2f, shotInterval);
	}
	
	@Override
	public float getFirerate() {
		if (goodShape)
			return FIRERATE;
		return FIRERATE2;
	}
	
	@Override
	public float getSpeed() {
		return SPEED6;
	}
	
	@Override
	public boolean stillAlive(PlayerWeapon p) {
		if (hp <= pvPhase2)
			goodShape = false;
		return super.stillAlive(p);
	}

	@Override
	public void setNextShot(float f) {
		SoundMan.playBruitage(SoundMan.shotRocket);
		nextShot = f;
	}

	@Override	public void addShots(int i) {						shotNumber += i;	}
	@Override	protected Sound getExplosionSound() {				return SoundMan.bigExplosion;		}
	@Override	public Animations getAnimation() {					return Animations.BOSS_MINE;		}
	@Override	public void free() {								POOL.free(this);					}
	@Override	public int getColor() {								return BLUE;						}
	@Override	public int getXp() {								return 200;							}
	@Override	public int getBonusValue() {						return 200;							}
	@Override	public int getExplosionCount() {					return 180;							}
	@Override	public boolean isInGoodShape() {					return goodShape;					}
	@Override	public Dimensions getDimensions() {		return DIMENSIONS;					}
	@Override
	protected int getMaxHp() {
		pvPhase2 = getPvBoss(Stats.HP_BOSS_MINE) / 2;
		return super.getPvBoss(Stats.HP_BOSS_MINE);
	}
	
	@Override
	public void die() {
		Progression.bossDied();
		super.die();
	}

}
