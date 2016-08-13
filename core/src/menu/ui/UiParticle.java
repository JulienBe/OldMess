package menu.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.TimeUtils;
import jeu.CSG;
import jeu.Stats;
import jeu.mode.EndlessMode;
import assets.AssetMan;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public class UiParticle implements Poolable {
	public static final float HEIGHT = Stats.PIXEL * 2, HALF_HEIGHT = HEIGHT / 2;
	public static final Pool<UiParticle> POOL = new Pool<UiParticle>() {
		@Override
		protected UiParticle newObject() {
			return new UiParticle();
		}
	};
	public static int nbr = 0;
	private float x, y, originX, originY, b;
    private int myNbr;
	private final Vector2 speed = new Vector2();
	private boolean sens;

	public UiParticle init(float x, float y) {
        myNbr = nbr++;
		this.y = (float) (y + CSG.R.nextGaussian() * Stats.U3);
		this.x = (float) (x + CSG.R.nextGaussian() * Stats.U3);
		originX = x;
		originY = y;
		b = (float) Math.sin(nbr);
		sens = true;
		speed.set(0, 0);
		return this;
	}

	public static void newElement() {
	    nbr = 0;
    }

	@Override
	public void reset() {
	}

	public void draw(Batch batch, int size) {
		if (sens) {
            b += Gdx.graphics.getDeltaTime();
        } else {
            b -= Gdx.graphics.getDeltaTime() / 3;
        }
		if (b < 0.0f) {
			sens = true;
            b = 0.0f;
		}
		if (b > 1f) {
            sens = false;
            b = 1f;
        }
        batch.setColor(0.1f, 1 - (b/2), 0.99f, 1);

        int offset = (int) ((TimeUtils.millis() / 100) % size) - myNbr;
        if (offset == 0) {
            batch.draw(AssetMan.debris, x, y, HALF_HEIGHT, HALF_HEIGHT, HEIGHT, HEIGHT, 1.5f, 1.5f, 45);
            b = 0;
        } else
            batch.draw(AssetMan.debris, x, y, HALF_HEIGHT, HALF_HEIGHT, HEIGHT, HEIGHT, 1 + b, 1 + b, 0);
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
