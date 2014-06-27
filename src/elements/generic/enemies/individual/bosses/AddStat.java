package elements.generic.enemies.individual.bosses;

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
import elements.generic.weapons.enemies.CyanBullet;

public class AddStat extends Enemy {
	
	protected static final Dimensions DIMENSIONS = Dimensions.ADD_SAT;
	public static final int LVL = 1, HP = 25, EXPLOSION = 15, BASE_XP = 25, XP = BASE_XP;
	static final float OFFSET_TIR = DIMENSIONS.halfWidth - CyanBullet.DIMENSIONS.halfWidth, FIRERATE = 1.7f, INIT_NEXT_SHOT = 0, SPEED14 = getModulatedSpeed(14, 1);
	public static Pool<AddStat> pool = Pools.get(AddStat.class);

	@Override
	public void reset() {
		super.reset();
		nextShot = INIT_NEXT_SHOT;
		pos.set(200, 200);
		dir.set(0, getSpeed());
	}

	public void lancer(float dirX, float dirY, float x, float y, float angle) {
		dir.set(dirX, dirY).scl(getSpeed());
		pos.set(x, y);
		this.angle = angle + 90;
		LIST.add(this);
	}
	
	@Override
	protected void move() {
		if (now > Animations.ailesDeployees.animationDuration)
			Mover.goToPlayer(this, 0.1f);
		else
			super.move();
	}
	
	@Override
	protected void shoot() {
		TMP_POS.set(pos.x + OFFSET_TIR, pos.y + OFFSET_TIR);
		TMP_DIR.set(dir.x, dir.y).nor();
		AbstractShot.straight(Gatling.CYAN_BULLET, TMP_POS, dir, 3);
	}

	@Override	public Animations getAnimation() {			return Animations.AILE_DEPLOYEES;	}
	@Override	protected Sound getExplosionSound() {		return SoundMan.explosion2;		}
	@Override	public int getExplosionCount() {			return EXPLOSION;				}
	@Override	public void free() {						pool.free(this);				}
	@Override	public float getFirerate() {				return FIRERATE;				}
	@Override	public int getBonusValue() {				return BASE_XP;					}
	@Override	public float getSpeed() {					return SPEED14;					}
	@Override	protected int getMaxHp() {					return HP;						}
	@Override	public int getXp() {						return XP;						}
	@Override	public Dimensions getDimensions() {			return DIMENSIONS;					}
	
}
