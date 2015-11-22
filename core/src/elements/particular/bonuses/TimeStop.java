package elements.particular.bonuses;

import jeu.Stats;
import jeu.mode.EndlessMode;
import assets.AssetMan;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Pool.Poolable;
import elements.particular.players.*;

public class TimeStop extends BonusTimeMvt implements Poolable{

	public static final Pool<TimeStop> POOL = Pools.get(TimeStop.class);
	private static int alreadyDropped = 1;
	private static final float SPEED = Stats.V_BONUS_STOP;

	@Override
	public void taken(Player player) {
		EndlessMode.addBonusStop();
		super.taken(player);
	}
	
	public static void mightAppear(float x, float y) {
		if (cptBonus > STOP_FREQ * (alreadyDropped * alreadyDropped * INCREASE_FREQ)) {
			POOL.obtain().init(x, y);
			alreadyDropped++;
		}
	}

	public static void resetStats() {						alreadyDropped = 1;			}
	@Override	public void free() {						POOL.free(this);			}
	@Override	protected float getMvt() {					return -(SPEED * tps);		}
	@Override	protected TextureRegion getTexture() {		return AssetMan.stopBonus;	}
}
