package elements.particular.particles.individual.explosions;

import jeu.CSG;
import jeu.Stats;
import jeu.mode.EndlessMode;
import assets.AssetMan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.generic.enemies.Enemy;
import elements.generic.weapons.player.PlayerWeapon;
import elements.particular.players.Player;
import elements.particular.particles.Particles;

public class SparklesColorOverTime implements Poolable {

	private float x, y, speedX, speedY, angle;
	private int index;
	public static final float WIDTH = Stats.uDiv4;
	private static final float HALF_WIDTH = WIDTH / 2;
	public static final float HEIGHT = Stats.U3, HALF_HEIGHT = HEIGHT / 2;
	private float[] colors;
	private static final Vector2 tmpVector = new Vector2();

	public static final Pool<SparklesColorOverTime> POOL = new Pool<SparklesColorOverTime>() {
		@Override
		protected SparklesColorOverTime newObject() {
			return new SparklesColorOverTime();
		}
	};
	
	private static void add(float x, float y, float angle, float[] colors, float dirX, float dirY, Array<SparklesColorOverTime> array) {
		final SparklesColorOverTime p = POOL.obtain();
		p.x = x;
		p.y = y;
		p.index = 0;
		p.angle = angle;
		p.colors = colors;
		p.speedX = dirX;
		p.speedY = dirY;
		array.add(p);
	}
	
	public static void add(float x, float y, float angle, float[] colors, Array<SparklesColorOverTime> array) {
		add(x, y, angle, colors, 0, 0, array);
	}
	
	public static void add(float x, float y, float angle, float[] colors, float speed) {
		CSG.tmpDir.set(speed, 0).rotate(angle);
		add(x, y, angle, colors, CSG.tmpDir.x, CSG.tmpDir.y, Particles.COLOR_OVER_TIME);
	}
	
	/**
	 * Will use alternatively one or the other colors[] for each particle
	 * @param explosionColorOverTime
	 * @param e
	 * @param yellowToGreenLong
	 * @param cyanToGreenLong
	 */
	public static void add(Array<SparklesColorOverTime> explosionColorOverTime, Enemy e, float[] colors1, float[] colors2) {
		boolean alternate = false;
		for (int i = -EndlessMode.fps; i < EndlessMode.fps; i++)
			if (alternate)		explosionColorOverTime.add(POOL.obtain().init(e.pos.x + e.getDimensions().halfWidth, e.pos.y + e.getDimensions().halfHeight, e, colors1));
			else				explosionColorOverTime.add(POOL.obtain().init(e.pos.x + e.getDimensions().halfWidth, e.pos.y + e.getDimensions().halfHeight, e, colors2));
	}
	
	public static void add(Array<SparklesColorOverTime> explosionColorOverTime, Enemy e, float[] colors) {
		for (int i = -EndlessMode.fps; i < EndlessMode.fps; i++) 
			explosionColorOverTime.add(POOL.obtain().init(e.pos.x + e.getDimensions().halfWidth, e.pos.y + e.getDimensions().halfHeight, e, colors));
	}
	
	public SparklesColorOverTime init(float x, float y, float[] colors, float angle) {
		this.x = x - HALF_WIDTH;
		this.y = y - HALF_WIDTH;
		this.colors = colors;
		index = CSG.R.nextInt(colors.length / 2);
		this.angle = angle;
		return this;
	}
	
	public SparklesColorOverTime init(float x, float y, Enemy e, float[] colors) {
		speedY = (float) (((CSG.R.nextGaussian()) * Stats.V_PARTICULE_EXPLOSION) + e.dir.y * CSG.R.nextFloat()) * 4.5f;
		speedX = (float) (((CSG.R.nextGaussian()) * Stats.V_PARTICULE_EXPLOSION) + e.dir.x * CSG.R.nextFloat()) * 4.5f;
		tmpVector.x = speedX;
		tmpVector.y = speedY;
		init(x, y, colors, tmpVector.angle());
		return this;
	}
	
	private SparklesColorOverTime init(float x, float y, float[] colors, PlayerWeapon p) {
		tmpVector.x = -p.dir.x;
		tmpVector.y = -p.dir.y;
		tmpVector.rotate((float) (CSG.R.nextGaussian() * 15));
		init(x, y, colors, tmpVector.angle());
		tmpVector.nor().scl(Stats.U270 / 2);
		speedX = tmpVector.x;
		speedY = tmpVector.y;
		return this;
	}
	
	@Override
	public void reset() {	}
	
	public static void act(Array<SparklesColorOverTime> explosions, SpriteBatch batch) {
		if (EndlessMode.alternate && !EndlessMode.triggerStop) {
			for (SparklesColorOverTime e : explosions) {
				batch.setColor(e.colors[e.index]);
				batch.draw(AssetMan.dust, e.x, e.y, HALF_HEIGHT, HALF_WIDTH, HEIGHT, WIDTH, 1f, 1f, e.angle);
				e.x += e.speedX * EndlessMode.delta;
				e.y += e.speedY * EndlessMode.delta;
				
				if (++e.index >= e.colors.length) {
					explosions.removeValue(e, true);
					POOL.free(e);
				}
			}
		} else {
			for (SparklesColorOverTime e : explosions) {
				batch.setColor(e.colors[e.index]);
				batch.draw(AssetMan.dust, e.x, e.y, HALF_HEIGHT, HALF_WIDTH, HEIGHT, WIDTH, 1f, 1f, e.angle);
			}
		}
		batch.setColor(CSG.gm.palette().white);
	}

	public static void clear(Array<SparklesColorOverTime> explosions) {
		POOL.freeAll(explosions);
		explosions.clear();
	}

	public static void bulletImpact(Array<SparklesColorOverTime> explosionColorOverTime, PlayerWeapon p, float[] colors) {
		explosionColorOverTime.add(POOL.obtain().init(p.pos.x + p.getDimensions().halfWidth, p.pos.y + p.getDimensions().halfHeight, colors, p));
		explosionColorOverTime.add(POOL.obtain().init(p.pos.x + p.getDimensions().halfWidth, p.pos.y + p.getDimensions().halfHeight, colors, p));
	}

	public static void blow(PlayerWeapon a, Array<SparklesColorOverTime> explosionColorOverTime) {
		for (SparklesColorOverTime e : explosionColorOverTime) {
			a.dir.set(e.x - Player.xCenter, e.y - Player.yCenter).nor().scl(Explosion.BOMB_SCALE);
			e.speedX += a.dir.x;
			e.speedY += a.dir.y;
			tmpVector.x = e.speedX;
			tmpVector.y = e.speedY;
			e.angle = tmpVector.angle();
		}
	}

}
