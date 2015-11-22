package elements.generic.enemies.individual.lvl4;

import jeu.Stats;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.individual.lvl3.Crusader3;

public class Crusader4 extends Crusader3 {

	public static final Pool<Crusader4> POOL = Pools.get(Crusader4.class);
	
	@Override
	protected void shoot() {
		super.shoot();
		TMP_DIR.y = -TMP_DIR.y;
		AbstractShot.straight(Gatling.KINDER_WEAPON, TMP_POS, TMP_DIR, Stats.U10);
		
		TMP_DIR.x = -TMP_DIR.x;
		TMP_POS.x -= DIMENSIONS.threeQuarterWidth;
		AbstractShot.straight(Gatling.KINDER_WEAPON, TMP_POS, TMP_DIR, Stats.U10);
	}
	
	@Override	public void free() {					POOL.free(this);				}
	@Override
	public int getXp() {
		return super.getXp() * 3;
	}
}
