package elements.generic.enemies.individual.lvl4;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.behavior.Mover;
import elements.generic.enemies.individual.lvl3.RoundAndRound3;
import jeu.CSG;
import jeu.mode.EndlessMode;

public class RoundAndRound4 extends RoundAndRound3 {

	public static final Pool<RoundAndRound4> POOL = Pools.get(RoundAndRound4.class);
	private static float tmp;
	
	@Override
	protected void move() {
		if (now < 6.5f)
			super.move();
		else {
			tmp = Math.min(now - 6f, 3);
			if (pos.x + DIMENSIONS.halfWidth > CSG.halfWidth) {
				tmp *= 90;
				Mover.rotate(this, tmp);
				angle += tmp * EndlessMode.deltaDiv2;
			} else { 
				tmp *= -90;
				Mover.rotate(this, tmp);
				angle += tmp * EndlessMode.deltaDiv2;
			}
		}
	}
	@Override	public void free() {				POOL.free(this);				}
	@Override
	public int getXp() {
		return super.getXp() * 3;
	}
}
