package elements.particular.bonuses;

import jeu.EndlessMode;
import jeu.Stats;
import assets.AssetMan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.generic.Player;

public class BonusBouclier extends BonusTimeMvt implements Poolable{

	public static final Pool<BonusBouclier> POOL = Pools.get(BonusBouclier.class);
	private static int alreadyDropped = 1;
	private static final float SPEED = Stats.V_BONUS_BOUCLIER;
	private float angle = 0;

	@Override
	public void taken() {
		Player.activateShield();
		super.taken();
	}
	
	@Override
	void drawMeMoveMe(SpriteBatch batch) {
		tps += EndlessMode.delta;
		batch.draw(getTexture(), pos.x, pos.y, HALF_WIDTH, HALF_WIDTH, WIDTH, WIDTH, 1, 1, angle);
		pos.y += getMvt() * EndlessMode.delta;
	}

	@Override
	public void free() {
		POOL.free(this);
	}
	
	public static void mightAppear(float x, float y) {
		if (cptBonus > SHIELD_FREQ * (alreadyDropped * alreadyDropped * INCREASE_FREQ)){
			POOL.obtain().init(x, y);
			alreadyDropped++;
		}
	}

	public static void resetStats() {
		alreadyDropped = 1;
	}

	@Override
	protected float getMvt() {
		return -(SPEED + (tps * 15));
	}

	@Override
	protected TextureRegion getTexture() {
		return AssetMan.shield;
	}
}