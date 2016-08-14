package elements.particular.particles.individual.smoke;

import jeu.CSG;
import jeu.mode.EndlessMode;
import assets.AssetMan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.particular.particles.individual.PrecalculatedParticles;

public class Smoke implements Poolable{
	
	private float x, y;
	private int index;
	private float[] colors;
	public static final Pool<Smoke> POOL = new Pool<Smoke>() {		protected Smoke newObject() {			return new Smoke();		}	};
	
	public static void act(Array<Smoke> smoke, SpriteBatch batch) {
		for (Smoke s : smoke) {
			batch.setColor(s.colors[s.index]);
			batch.draw(AssetMan.dust, s.x, s.y, PrecalculatedParticles.widths[s.index], PrecalculatedParticles.widths[s.index]);
			if (EndlessMode.triggerStop)
				continue;
			s.x -= PrecalculatedParticles.halfWidths[s.index];
			s.y -= PrecalculatedParticles.halfWidths[s.index];
			
			if (++s.index >= s.colors.length) {
				smoke.removeValue(s, true);
				POOL.free(s);
			}
		}
		batch.setColor(CSG.gm.palette().white);
	}

	public Smoke init(float x, float y, boolean rnd, float[] colors) {
		if (rnd)
			this.x = (x - PrecalculatedParticles.INITIAL_HALF_WIDTH) + ((CSG.R.nextFloat() - .5f) * PrecalculatedParticles.INITIAL_HALF_WIDTH);
		else
			this.x = x - PrecalculatedParticles.INITIAL_HALF_WIDTH;
		this.y = y - PrecalculatedParticles.INITIAL_HALF_WIDTH;
		index = 0;
		this.colors = colors;
		return this;
	}

	public static void clear(Array<Smoke> smoke) {
		POOL.freeAll(smoke);
		smoke.clear();
	}

	@Override
	public void reset() {}

}
