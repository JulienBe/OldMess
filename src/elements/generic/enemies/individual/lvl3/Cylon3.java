package elements.generic.enemies.individual.lvl3;

import assets.sprites.Animations;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.behavior.Mover;
import elements.generic.enemies.individual.lvl1.Cylon;
import elements.particular.particles.Particles;
import elements.particular.particles.individual.PrecalculatedParticles;
import jeu.Stats;
import jeu.mode.EndlessMode;

public class Cylon3 extends Cylon {
	
	public static final Pool<Cylon3> POOL = Pools.get(Cylon3.class);
	private static final int HP = getModulatedPv(Stats.HP_CYLON, 3), HP_BAD = (int) (HP * 0.66f), HP_WORST = (int) (HP * 0.33f), XP = getXp(BASE_XP, 3);

	@Override
	protected void move() {
		TMP_POS.set(0, DIMENSIONS.halfWidth);
		TMP_POS.rotate(angle);
		if (EndlessMode.alternate)
			Particles.smoke((pos.x + DIMENSIONS.halfWidth) + TMP_POS.x, (pos.y + DIMENSIONS.halfWidth) + TMP_POS.y, true, PrecalculatedParticles.colorsBlue, -dir.x / 20, -dir.y / 20);
		Mover.goToPlayer(this, 0.01f);	
	}
	@Override	public Animations getAnimation() {		return Animations.CYLON_BLUE;			}
	@Override   protected int getPvWorst() {			return HP_WORST;						}
	@Override	public void free() {					POOL.free(this);						}
	@Override	public int getBonusValue() {			return BASE_XP;							}
	@Override 	protected int getPvBad() {				return HP_BAD;							}
	@Override	public int getColor() {					return BLUE;							}
	@Override	protected int getMaxHp() {				return HP;								}
	@Override	public int getXp() {					return XP;								}
}
