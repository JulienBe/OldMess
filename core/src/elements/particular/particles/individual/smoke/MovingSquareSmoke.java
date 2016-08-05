package elements.particular.particles.individual.smoke;

import assets.AssetMan;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import elements.particular.particles.individual.PrecalculatedParticles;
import jeu.CSG;
import jeu.Stats;
import jeu.mode.EndlessMode;

public class MovingSquareSmoke implements Poolable {

  public static final float WIDTH = Stats.U, HALF_WIDTH = WIDTH / 2;
	private float x, y, dirX, dirY;
	private int index, endIndex;
	private float[] colors;
	public static final Pool<MovingSquareSmoke> POOL = new Pool<MovingSquareSmoke>() {
		@Override
		protected MovingSquareSmoke newObject() {
			return new MovingSquareSmoke();
		}
	};

	public MovingSquareSmoke init(float posX, float posY, float[] colors, float dirX, float dirY, int endIndex) {
		index = 0 + CSG.R.nextInt(2);
		this.dirX = dirX;
		this.dirY = dirY;
		this.colors = colors;
    if (endIndex > colors.length - 1)
      endIndex = colors.length - 1;
    this.endIndex = endIndex - 5;
		x = posX;
		y = posY;
    return this;
	}

	public static void act(Array<MovingSquareSmoke> smoke, SpriteBatch batch) {
		for (MovingSquareSmoke s : smoke) {
      batch.setColor(s.colors[s.index]);
			batch.draw(AssetMan.debris, s.x, s.y, Stats.u, Stats.u);
			if (EndlessMode.triggerStop)
				continue;
			s.x += s.dirX;
			s.y += s.dirY;
			if (++s.index >= s.endIndex) {
				smoke.removeValue(s, true);
				POOL.free(s);
        continue;
			}
		}
		batch.setColor(CSG.gm.palette().white);
	}
	
	@Override
	public void reset() {}
	
	public static void clear(Array<MovingSquareSmoke> smoke) {
		POOL.freeAll(smoke);
		smoke.clear();
	}
}
