package elements.particular.particles.individual.thruster;

import java.util.Random;

import jeu.CSG;
import jeu.Stats;
import jeu.mode.EndlessMode;
import assets.AssetMan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.particular.players.Player;
import elements.particular.particles.Particles;

public class ThrusterParticle implements Poolable {
	
	private static final float WIDTH = CSG.screenWidth / 75, HALF_WIDTH = WIDTH / 2;
	public static final Pool<ThrusterParticle> POOL = new Pool<ThrusterParticle>(Particles.MAX_THRUSTER) {
		@Override
		protected ThrusterParticle newObject() {
			return new ThrusterParticle();
		}
	};
	private float x, y, alpha;
	private final float vitesseX, vitesseY, red, diminish;
	private static final Random r = new Random();
	
	public ThrusterParticle() {
		red = r.nextFloat();
		diminish = 6f + (r.nextFloat() * 6f);
		vitesseY =  ((r.nextFloat()+.5f) * -Stats.THRUSTER) - CSG.heightDiv4;
		vitesseX = (float) (CSG.R.nextGaussian() * (CSG.oneThirdWidth * 0.6f));
	}

	public ThrusterParticle init(Player v) {
		x = (float) (Player.xCenter + (CSG.R.nextGaussian() * WIDTH)) - HALF_WIDTH;
		y = Player.POS.y - (HALF_WIDTH + (WIDTH * r.nextFloat()));
		alpha = (4 + CSG.R.nextFloat()) / 5;
		return this;
	}
	
	@Override
	public void reset() {	}
	
	public static void act(Array<ThrusterParticle> flammes, SpriteBatch batch) {
		if (EndlessMode.alternate) {
			for (final ThrusterParticle f : flammes) {
				batch.setColor(f.alpha, f.alpha, 1, f.alpha);
				batch.draw(AssetMan.dust, f.x, f.y, WIDTH, WIDTH);
				f.alpha -= f.diminish * EndlessMode.delta;
				f.x += (f.vitesseX * EndlessMode.delta);
				f.y += (f.vitesseY * EndlessMode.delta);
				if (f.alpha < 0) {
					POOL.free(f);
					flammes.removeValue(f, true);
				}
			}
		} else {
			for (final ThrusterParticle f : flammes) {
				batch.setColor(f.alpha, f.red, 1, f.alpha);
				batch.draw(AssetMan.dust, f.x, f.y, WIDTH, WIDTH);
			}
		}
		batch.setColor(CSG.gm.palette().white);
	}

	public static void clear(Array<ThrusterParticle> flammes) {
		POOL.freeAll(flammes);
		flammes.clear();
	}
}
