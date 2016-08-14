package elements.particular.particles.individual.thruster;

import assets.AssetMan;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import elements.particular.particles.individual.PrecalculatedParticles;
import jeu.CSG;
import jeu.TimeKeeper;
import jeu.colors.Bunch;
import jeu.mode.EndlessMode;

public class EnemyThruster {

	private float x, y, dirX, dirY;
	private int index;
	private Bunch colors;
	private static final Pool<EnemyThruster> POOL = new Pool<EnemyThruster>() {		protected EnemyThruster newObject() {			return new EnemyThruster();		}	};

	private EnemyThruster() {}

	public static EnemyThruster get() { return POOL.obtain(); }

	public EnemyThruster init(float x, float y, float dirX, float dirY, Bunch colors) {
		this.x = x - PrecalculatedParticles.INITIAL_HALF_WIDTH;
		this.y = y - PrecalculatedParticles.INITIAL_HALF_WIDTH;
        this.dirX = dirX;
        this.dirY = dirY;
		this.colors = colors;
		index = 0;
		return this;
	}

	public static void act(Array<EnemyThruster> particles, SpriteBatch batch) {
		for (EnemyThruster p : particles) {
			batch.setColor(p.colors.get(p.index));
			batch.draw(AssetMan.debris,
					p.x, p.y,
					PrecalculatedParticles.widths[p.index], PrecalculatedParticles.widths[p.index]);
			if (EndlessMode.triggerStop)
				continue;
			p.x -= PrecalculatedParticles.halfWidths[p.index];
			p.y -= PrecalculatedParticles.halfWidths[p.index];
            p.x += TimeKeeper.delta * p.dirX;
            p.y += TimeKeeper.delta * p.dirY;

			if (++p.index >= p.colors.length()) {
				particles.removeValue(p, true);
				POOL.free(p);
			}
		}
		batch.setColor(CSG.gm.palette().white);
	}



    public static void clear(Array<EnemyThruster> smoke) {
		POOL.freeAll(smoke);
		smoke.clear();
	}

}
