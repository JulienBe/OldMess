package elements.particular.particles.individual.weapon;

import jeu.CSG;
import jeu.Stats;
import assets.AssetMan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.generic.weapons.Weapon;
import elements.particular.particles.individual.PrecalculatedParticles;

public final class PinkParticle implements Poolable {
		
	public static final Pool<PinkParticle> POOL = new Pool<PinkParticle>(100) {
		@Override
		protected PinkParticle newObject() {
			return new PinkParticle();
		}
	};
	public static final float[] colors = PrecalculatedParticles.colorsOverTimeCyanToGreen;
	public static final int MAX_LENGTH = colors.length, HALF_LENGTH = MAX_LENGTH / 2;
	public static float width = Stats.U;
	private float x, y;
	private int index;

	@Override
	public void reset() {}

	public void init(Weapon wp) {
		x = wp.pos.x + Stats.uDiv2;
		y = wp.pos.y + Stats.uDiv2;
		index = 0;
	}

	public static void act(Array<PinkParticle> pinkParticles, SpriteBatch batch) {
		
		for (final PinkParticle p : pinkParticles) {
//				batch.setColor(PrecalculatedParticles.colorsPinkWeapon[p.index]);
//				batch.draw(AssetMan.dust, p.x, p.y, height, height);
			
			batch.setColor(colors[CSG.R.nextInt(MAX_LENGTH)]);
			batch.draw(AssetMan.dust, p.x - Stats.U, p.y - Stats.U, Stats.U2, Stats.U2);
			
			batch.setColor(colors[CSG.R.nextInt(HALF_LENGTH)]);
			batch.draw(AssetMan.dust, p.x - Stats.u, p.y - Stats.u, Stats.U, Stats.U);
			
			batch.setColor(colors[0]);
			batch.draw(AssetMan.dust, p.x - Stats.uDiv2, p.y - Stats.uDiv2, Stats.u, Stats.u);
			if (++p.index >= 4) {
				pinkParticles.removeValue(p, true);
				POOL.free(p);
			}
		}
		batch.setColor(CSG.gm.palette().white);
	}

	public static void clear(Array<PinkParticle> particulesLaser) {
		POOL.freeAll(particulesLaser);
		POOL.clear();
		particulesLaser.clear();
	}
}
