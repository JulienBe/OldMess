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
import elements.particular.particles.ParticuleBundles;

public class ColorOverTime implements Poolable {
	
	private float x, y, speedX, speedY;
	private int index;
	private ParticuleBundles bundle;
	private static final Vector2 tmpVector = new Vector2();

	public static final Pool<ColorOverTime> POOL = new Pool<ColorOverTime>() {
		@Override
		protected ColorOverTime newObject() {
			return new ColorOverTime();
		}
	};
	
	/**
	 * Each particle will use randomly one bundle. 
	 * MUST HAVE AT LEAST ONE BUNDLE  
	 */
	public static void add(Array<ColorOverTime> array, Enemy e, int nb, ParticuleBundles... bundles) {
		for (int i = 0; i < nb; i++)
			array.add(POOL.obtain().initGaussian(e, bundles[CSG.R.nextInt(bundles.length)]));
	}
	/**
	 * Each particle will use randomly one bundle. 
	 * MUST HAVE AT LEAST ONE BUNDLE  
	 */
	public static void add(Array<ColorOverTime> array, Enemy e, ParticuleBundles... bundles) {
		for (int i = 0; i < EndlessMode.fps; i++)
			array.add(POOL.obtain().init(e, bundles[CSG.R.nextInt(bundles.length)]));
	}
	/**
	 * Each particle will use randomly one bundle. 
	 * MUST HAVE AT LEAST ONE BUNDLE  
	 */
	public static void add(float x, float y, Array<ColorOverTime> array, ParticuleBundles... bundles) {
		for (int i = 0; i < EndlessMode.fps; i++)
			array.add(POOL.obtain().init(x, y, bundles[CSG.R.nextInt(bundles.length)]));
	}
	
	private ColorOverTime init(float x, float y, ParticuleBundles bundle) {
		this.x = x;
		this.y = y;
		
		speedY = (float) (CSG.R.nextGaussian() * Stats.V_PARTICLE_EXPLOSION_SLOW);
		speedX = (float) (CSG.R.nextGaussian() * Stats.V_PARTICLE_EXPLOSION_SLOW);
		
		this.bundle = bundle;
		index = 0;
		return this;
	}
	private ColorOverTime init(Enemy e, ParticuleBundles bundle) {
		x = (e.pos.x + (e.getDimensions().width * CSG.R.nextFloat()) );
		y = (e.pos.y + (e.getDimensions().height * CSG.R.nextFloat()) );
		

		speedY = (float) (((CSG.R.nextGaussian()) * Stats.V_PARTICLE_EXPLOSION_SLOW) + e.dir.y * CSG.R.nextFloat());
		speedX = (float) (((CSG.R.nextGaussian()) * Stats.V_PARTICLE_EXPLOSION_SLOW) + e.dir.x * CSG.R.nextFloat());
		
		this.bundle = bundle;
		index = 0;
		
		return this;
	}
	
	private ColorOverTime initGaussian(Enemy e, ParticuleBundles bundle) {
		speedY = (float) (CSG.R.nextGaussian() * Stats.U2);
		speedX = (float) (CSG.R.nextGaussian() * Stats.U2);
		
		this.bundle = bundle;
		index = CSG.R.nextInt(bundle.colors.length/3);
		
		x = (float) ((e.pos.x + e.getDimensions().halfWidth - (bundle.widths[index] / 2)) + CSG.R.nextGaussian() * e.getDimensions().width / 5);
		y = (e.pos.y + e.getDimensions().halfHeight - (bundle.widths[index] / 2));
		return this;
	}

	@Override
	public void reset() {	}
	
	public static void act(Array<ColorOverTime> array, SpriteBatch batch) {
		if (EndlessMode.alternate && !EndlessMode.triggerStop) {
			for (ColorOverTime e : array) {
				batch.setColor(e.bundle.colors[e.index]);
				batch.draw(AssetMan.dust, e.x, e.y, e.bundle.widths[e.index], e.bundle.widths[e.index]);
				
				e.x += e.speedX * EndlessMode.delta;
				e.y += e.speedY * EndlessMode.delta;
				
				e.x -= e.bundle.differences[e.index];
				e.y -= e.bundle.differences[e.index];
				// bundles are checked, bundle.colors is always at maximum the same lenght as bundle.widths 
				if (++e.index >= e.bundle.colors.length) {
					array.removeValue(e, true);
					POOL.free(e);
				}
			}
		} else {
			for (ColorOverTime e : array) {
				batch.setColor(e.bundle.colors[e.index]);
				batch.draw(AssetMan.dust, e.x, e.y, e.bundle.widths[e.index], e.bundle.widths[e.index]);
			}
		}
		batch.setColor(CSG.gm.palette().white);
	}
	/**
	 * Will blow the particles away from the player 
	 */
	public static void blow(PlayerWeapon a, Array<ColorOverTime> array) {
		for (ColorOverTime e : array) {
			tmpVector.x = a.dir.x;
			tmpVector.y = a.dir.y;
			tmpVector.x = (e.x) - Player.xCenter;
			tmpVector.y = (e.y) - Player.yCenter;
			tmpVector.nor();
			tmpVector.scl(Explosion.BOMB_SCALE);
			e.speedX += a.dir.x;
			e.speedY += a.dir.y;
		}
	}

	public static void clear(Array<ColorOverTime> array) {
		POOL.freeAll(array);
		array.clear();
	}
}