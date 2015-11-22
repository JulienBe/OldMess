package elements.generic.enemies.individual.lvl4;

import jeu.Stats;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.individual.lvl3.Plane3;
import elements.generic.weapons.enemies.Fireball;

public class Plane4 extends Plane3 {
	
	public static final Pool<Plane4> POOL = Pools.get(Plane4.class);
	private static final float OFFSET_WEAPON_RIGHT = (int) (DIMENSIONS.width - Fireball.DIMENSIONS.halfWidth * 1.5f), OFFSET_WEAPON_LEFT = Fireball.DIMENSIONS.halfWidth / 2, OFFSET_WEAPON_Y = DIMENSIONS.halfHeight - Fireball.DIMENSIONS.height;
	
	@Override
	protected void shootDouble() {
		TMP_POS.set(pos.x - OFFSET_WEAPON_LEFT, pos.y + OFFSET_WEAPON_Y);
		AbstractShot.shootDownRandom(Gatling.FIREBALL, TMP_POS, Stats.U20, 10);
		TMP_POS.x = pos.x + OFFSET_WEAPON_RIGHT;
		AbstractShot.shootDownRandom(Gatling.FIREBALL, TMP_POS, Stats.U20, 10);
	}
	@Override
	protected void shootSingle() {
		AbstractShot.shootDownRandom(Gatling.FIREBALL, TMP_POS, Stats.U20, 10);
	}
	@Override	public void free() {					POOL.free(this);					}
	@Override
	public int getXp() {
		return super.getXp() * 3;
	}
}
