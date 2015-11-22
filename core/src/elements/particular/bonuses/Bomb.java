package elements.particular.bonuses;

import jeu.Stats;
import jeu.mode.EndlessMode;
import assets.AssetMan;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.particular.particles.Particles;
import elements.particular.players.*;

public class Bomb extends BonusTimeMvt implements Poolable{

	public static final Pool<Bomb> POOL = Pools.get(Bomb.class);
	private static int alreadyDropped = 1;
	private static final float SPEED = Stats.BOMB_SPEED;
	
	@Override
	public void taken(Player player) {
		Particles.bomb(this);
		EndlessMode.ajoutBombe();
		super.taken(player);
	}
	
	public static void mightAppear(float x, float y) {
		if (cptBonus > BOMB_FREQ * (alreadyDropped * alreadyDropped * INCREASE_FREQ)) {
			POOL.obtain().init(x, y);
			alreadyDropped++;
		}
	}

	public static void resetStats() {						alreadyDropped = 1;					}

	@Override	protected float getMvt() {					return -(SPEED + (tps*tps));		}
	@Override	protected TextureRegion getTexture() {		return AssetMan.bomb;				}
	@Override	public void free() {						POOL.free(this);					}
}
