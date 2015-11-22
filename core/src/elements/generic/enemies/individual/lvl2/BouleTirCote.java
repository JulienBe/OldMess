package elements.generic.enemies.individual.lvl2;

import jeu.CSG;
import behind.SoundMan;
import assets.sprites.Animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.Dimensions;
import elements.generic.components.EnemyStats;
import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.Enemy;
import elements.generic.weapons.enemies.OrangeBullet;

public class BouleTirCote extends Enemy {
	
	protected static final Dimensions DIMENSIONS = Dimensions.BALL_SIDE_SHOOT;
	private static final float OFFSET = DIMENSIONS.halfWidth - OrangeBullet.DIMENSIONS.halfWidth;
	public static final Pool<BouleTirCote> POOL = Pools.get(BouleTirCote.class);
	protected int numeroTir;
	
	public void init(float x, float y) {
		dir.set(0, getEnemyStats().getSpeed());
		nextShot = 1;
		numeroTir = 1;
		pos.set(CSG.halfWidth, CSG.height);
		angle = CSG.halfWidth - (pos.x + DIMENSIONS.width*2);
		angle /= 4;
		dir.rotate(angle);
		angle += 180;
	}
	
	@Override
	protected void shoot() {
		shoot(0);
	}
	
	protected void shoot(float angle) {
		TMP_POS.set(pos.x + OFFSET, pos.y + OFFSET);
		TMP_DIR.set(dir.y, -dir.x).rotate(angle);
		AbstractShot.leftRight(Gatling.ORANGE_BULLET, TMP_POS, TMP_DIR, 1.5f);
		numeroTir = AbstractShot.interval(this, 6, 1, numeroTir);
	}

	@Override	public EnemyStats getEnemyStats() {			return EnemyStats.BALL_SIDE_SHOT;	}
	@Override	protected Sound getExplosionSound() {		return SoundMan.explosion4;			}
	@Override	public Animations getAnimation() {			return Animations.BALL;				}
	@Override	public Dimensions getDimensions() {			return DIMENSIONS;					}
	@Override	public void free() {						POOL.free(this);					}
}
