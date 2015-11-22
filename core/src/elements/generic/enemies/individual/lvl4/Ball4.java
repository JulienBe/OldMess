package elements.generic.enemies.individual.lvl4;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.individual.lvl3.Ball3;
import elements.generic.weapons.enemies.PinkBullet;
import jeu.Stats;

public class Ball4 extends Ball3 {
	
	public static final Pool<Ball4> POOL = Pools.get(Ball4.class);

	@Override
	protected void shoot() {
		TMP_POS.x = (pos.x + DIMENSIONS.halfWidth - PinkBullet.DIMENSIONS.halfWidth);
		TMP_POS.y = (pos.y + DIMENSIONS.halfWidth - PinkBullet.DIMENSIONS.halfHeight);
		AbstractShot.shootOnPlayer(Gatling.CYAN_BULLET, TMP_POS, 0, Stats.U12, 3, 7);
		if (++shotNumber > 2) {
			shotNumber = 0;
			nextShot += 2;
		}
	}
	
	@Override	public void free() {							POOL.free(this);							}
	@Override
	public int getXp() {
		return super.getXp() * 3;
	}
}
