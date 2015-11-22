package elements.particular.particles.individual.explosions;

import jeu.CSG;
import jeu.Stats;
import jeu.mode.EndlessMode;
import assets.AssetMan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.generic.weapons.player.PlayerWeapon;
import elements.particular.players.Player;

public class ExplosionImpactBullet implements Poolable {
	
	private static final float WIDTH_SMALL = CSG.screenWidth / 80, SPEED = Stats.U2;
	private static final float MIN_WIDTH = 1;
	private static final Pool<ExplosionImpactBullet> POOL = new Pool<ExplosionImpactBullet>(100) {
		@Override
		protected ExplosionImpactBullet newObject() {
			return new ExplosionImpactBullet();
		}
	};
	private float speedX, speedY, x, y, color, width;
	private int ttl;
	public static final int GAUSSIAN_FACTOR = 100;
	
	@Override
	public void reset() {	}

	public static void add(Array<ExplosionImpactBullet> explosions, PlayerWeapon pw) {
		for (int i = -EndlessMode.perf; i < (pw.getPower()/2)+EndlessMode.perf; i++)
			explosions.add(POOL.obtain().init(pw));
	}

	
	private ExplosionImpactBullet init(PlayerWeapon pw) {
		speedY = (float) ((CSG.R.nextGaussian()) * SPEED) + (pw.dir.y/10);
		speedX = (float) ((CSG.R.nextGaussian()) * SPEED) + (pw.dir.x/10);
		
		width = Math.abs((float) ((CSG.R.nextGaussian() * WIDTH_SMALL))) + MIN_WIDTH;
		x = (pw.pos.x - width/2) + (pw.getDimensions().width*CSG.R.nextFloat());
		y = (pw.pos.y - width/2) + (pw.getDimensions().height*CSG.R.nextFloat());
		ttl = (int) (CSG.R.nextFloat() * 5) + 5;
		color = pw.getColor();
		return this;
	}

	private static float tmp;
	public static void act(Array<ExplosionImpactBullet> explosions, SpriteBatch batch) {
		if (EndlessMode.alternate) {
			for (final ExplosionImpactBullet p : explosions) {
				batch.setColor(p.color);
				batch.draw(AssetMan.dust, p.x, p.y, p.width, p.width);
				if (EndlessMode.triggerStop)
					continue;
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
					POOL.free(p);
					explosions.removeValue(p, true);
				}
			}
		} else
			for (final ExplosionImpactBullet p : explosions) {
				batch.setColor(p.color);
				batch.draw(AssetMan.dust, p.x, p.y, p.width, p.width);
			}
		batch.setColor(CSG.gm.palette().white);
	}

	public static void clear(Array<ExplosionImpactBullet> explosions) {
		POOL.freeAll(explosions);
		explosions.clear();
	}
	

	public static void blow(PlayerWeapon a, Array<ExplosionImpactBullet> explosions) {
		for (ExplosionImpactBullet e : explosions) {
			a.dir.x = (e.x) - Player.xCenter;
			a.dir.y = (e.y) - Player.yCenter;
			a.dir.nor();
			a.dir.scl(Explosion.BOMB_SCALE);
			e.speedX += a.dir.x;
			e.speedY += a.dir.y;
		}
	}
}
