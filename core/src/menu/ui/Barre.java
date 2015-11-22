package menu.ui;

import jeu.CSG;
import jeu.Stats;
import jeu.mode.EndlessMode;
import assets.AssetMan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Barre implements Poolable {
	public static final float HEIGHT = Stats.u * 0.95f, HALF_HEIGHT = HEIGHT / 2;
	public static final Pool<Barre> POOL = new Pool<Barre>() {
		@Override
		protected Barre newObject() {
			return new Barre();
		}
	};
	private float width = HEIGHT, angle = 90, x, y, originX, originY, b;
	private final Vector2 speed = new Vector2();
	private boolean sens;
	public static int nbr = 0;
	private static float bOffset = 0.1f;

	public Barre init(float x, float y) {
		nbr++;
		this.y = (float) (y + CSG.R.nextGaussian() * (Stats.U3 * nbr));
		this.x = (float) (x + CSG.R.nextGaussian() * (Stats.U3 * nbr));
		originX = x;
		originY = y;
		b = bOffset % 1;
		bOffset += 0.1f;

		sens = true;
		speed.set(0, 0);
		return this;
	}

	@Override
	public void reset() {
	}

	public void draw(SpriteBatch batch) {
		if (b < .3f)
			sens = true;
		if (b > .95f)
			sens = false;
		if (sens)
			b += 0.008f;
		else
			b -= 0.008f;
		batch.setColor(1-b, 1-(b/2), 0.99f, 1);
		batch.draw(AssetMan.debris, x, y, width/2, HEIGHT / 2, width, HEIGHT, 1, 1, angle);
		batch.setColor(CSG.gm.palette().white);
		x += speed.x * EndlessMode.delta;
		y += speed.y * EndlessMode.delta;
		speed.scl(0.9f);
		x += ((originX - x)/4);
		y += ((originY - y)/4);
	}

	public void explode(Vector2 tmptouched) {
		speed.set(x - tmptouched.x, y - tmptouched.y).scl(((CSG.R.nextFloat()/2)+1) * 50).rotate((float) CSG.R.nextGaussian() * 5f);
	}

	public void impulse(Vector2 tmptouched) {
		speed.set(x - tmptouched.x, y - tmptouched.y).scl(7f + CSG.R.nextInt(7));
	}

}
