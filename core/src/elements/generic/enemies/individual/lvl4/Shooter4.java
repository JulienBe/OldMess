package elements.generic.enemies.individual.lvl4;

import jeu.Stats;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.individual.lvl3.Shooter3;
import elements.generic.weapons.enemies.Fireball;

public class Shooter4 extends Shooter3 {
	
	public static final Pool<Shooter4> POOL = Pools.get(Shooter4.class);
	
	@Override
	protected void shoot() {
		TMP_POS.set(pos.x + (DIMENSIONS.halfWidth - Fireball.DIMENSIONS.halfWidth), pos.y - Fireball.DIMENSIONS.height);
		AbstractShot.shootDownRandom(Gatling.FIREBALL, TMP_POS, Stats.U12, 10);
		interval(-1);
	}
	
	@Override	public void free() {				POOL.free(this);				}
	@Override
	public int getXp() {
		return super.getXp() * 3;
	}
}
