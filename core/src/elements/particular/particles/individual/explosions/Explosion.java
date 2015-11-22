package elements.particular.particles.individual.explosions;

import jeu.CSG;
import jeu.Stats;
import jeu.mode.EndlessMode;
import assets.AssetMan;
import behind.SoundMan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.generic.Element;
import elements.generic.enemies.Enemy;
import elements.generic.weapons.enemies.EnemyWeapon;
import elements.generic.weapons.player.PlayerWeapon;
import elements.particular.players.Player;
import elements.particular.particles.Particles;

public class Explosion implements Poolable {
	
	public static final float WIDTH = CSG.screenWidth / 50, MIN_WIDTH = 1, BOMB_SCALE = Stats.U * 15;
	private static final Pool<Explosion> POOL_STANDARD = new Pool<Explosion>(100) {
		@Override
		protected Explosion newObject() {
			return new Explosion();
		}
	};
	private float speedX, speedY, x, y, width, color;
	private int ttl;
	private static int bigger = 0;
	public static final int GAUSSIAN_FACTOR = 50, STANDARD_EXPLOSION = 1, BLUE_EXPLOSION = 2, GREEN_EXPLOSION = 3, LIMIT_BIGGER = 1600;
	private static final Vector2 tmpVector = new Vector2();
	
	@Override
	public void reset() {	}

	private static void create(int max, Array<Explosion> explosions, Enemy e, float[] possibleColors) {
		for (int i = -EndlessMode.fps*2; i < max; i++) 
			explosions.add(POOL_STANDARD.obtain().init(e, possibleColors, e.dir.y * CSG.R.nextFloat(), e.dir.x * CSG.R.nextFloat()));
	}
	
	public static void add(Array<Explosion> explosions, float x, float y, int max, float[] possibleColors) {
		for (int i = -EndlessMode.fps; i < max; i++) 
			Particles.EXPLOSIONS.add(POOL_STANDARD.obtain().init(x, y, possibleColors, 0, 0));
	}
	
	public static void add(Array<Explosion> explosions, Enemy e, float[] possibleColors) {
		if (++bigger > 4 && explosions.size < LIMIT_BIGGER && EndlessMode.perf > 2) {
			bigger = 0;
			create(e.getExplosionCount()*4, explosions, e, possibleColors);
			SoundMan.playBruitage(SoundMan.bigExplosion);
		} else
			create(e.getExplosionCount(), explosions, e, possibleColors);
	}
	
	private Explosion init(Element e, float[] possibleColors, float yOffset, float xOffset) {
		x = (e.pos.x + (e.getDimensions().width * CSG.R.nextFloat()) );
		y = (e.pos.y + (e.getDimensions().height * CSG.R.nextFloat()) );
		return init(x, y, possibleColors, yOffset, xOffset);
	}
	
	private Explosion init(float x, float y, float[] possibleColors, float yOffset, float xOffset) {
		this.x = x;
		this.y = y;
		
		speedY = (float) ((CSG.R.nextGaussian()) * Stats.V_PARTICLE_EXPLOSION_SLOW) + yOffset;
		speedX = (float) ((CSG.R.nextGaussian()) * Stats.V_PARTICLE_EXPLOSION_SLOW) + xOffset;
		
		initWidthTtlColor(possibleColors);
		return this;
	}

	protected void initWidthTtlColor(float[] possibleColors) {
		width = Math.abs((float) ((CSG.R.nextGaussian() * WIDTH))) + MIN_WIDTH;
		ttl = (int) (CSG.R.nextFloat() * 15) + 10;
		color = possibleColors[CSG.R.nextInt(possibleColors.length)];
	}

	public static void add(int max, EnemyWeapon e, float[] possibleColors) {
		for (int i = -EndlessMode.fps; i < max; i++) 
			Particles.EXPLOSIONS.add(POOL_STANDARD.obtain().init(e, possibleColors, 0, 0));
	}
	
	private static float tmp;
	public static void act(Array<Explosion> explosions, SpriteBatch batch) {
		if (EndlessMode.alternate || EndlessMode.triggerStop) {
			for (final Explosion p : explosions) {
				batch.setColor(p.color);
				batch.draw(AssetMan.dust, p.x, p.y, p.width, p.width);
			}
		} else {
			for (final Explosion p : explosions) {
				batch.setColor(p.color);
				batch.draw(AssetMan.dust, p.x, p.y, p.width, p.width);
				p.speedX /= EndlessMode.UnPlusDelta;
				p.speedY /= EndlessMode.UnPlusDelta;
				tmp = p.width - (p.width / EndlessMode.UnPlusDelta);
				p.width -= tmp;
				tmp /= 2;
				p.x += tmp;
				p.y += tmp;
				p.x += p.speedX * EndlessMode.delta2;
				p.y += p.speedY * EndlessMode.delta2;
				if (p.ttl-- < 0 || p.width < Stats.u) {
					POOL_STANDARD.free(p);
					explosions.removeValue(p, true);
				}
			}
		}
		batch.setColor(CSG.gm.palette().white);
	}

	public static void clear(Array<Explosion> explosions) {
		POOL_STANDARD.freeAll(explosions);
		explosions.clear();
	}

	public static void blow(PlayerWeapon a, Array<Explosion> explosions) {
		for (Explosion e : explosions) {
			tmpVector.x = a.dir.x;
			tmpVector.y = a.dir.y;
			tmpVector.x = (e.x) - Player.xCenter;
			tmpVector.y = (e.y) - Player.yCenter;
			tmpVector.nor();
			tmpVector.scl(Explosion.BOMB_SCALE);
		}
	}

}
