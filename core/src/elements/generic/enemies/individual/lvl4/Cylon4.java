package elements.generic.enemies.individual.lvl4;

import assets.sprites.Animations;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.behavior.Mover;
import elements.generic.enemies.individual.lvl3.Cylon3;
import elements.particular.particles.Particles;
import elements.particular.particles.individual.PrecalculatedParticles;
import jeu.mode.EndlessMode;

public class Cylon4 extends Cylon3 {
	
	public static final Pool<Cylon4> POOL = Pools.get(Cylon4.class);
	
	@Override
	protected void move() {
		TMP_POS.set(0, DIMENSIONS.halfWidth).rotate(angle);
		if (EndlessMode.alternate)
			Particles.smoke((pos.x + DIMENSIONS.halfWidth) + TMP_POS.x, (pos.y + DIMENSIONS.halfWidth) + TMP_POS.y, true, PrecalculatedParticles.colorsYellowToGreen, -dir.x / 20, -dir.y / 20);
		Mover.goToPlayer(this, 0.017f);	
	}
	
	@Override	public Animations getAnimation() {		return Animations.CYLON_GREEN;					}
	@Override	public void free() {					POOL.free(this);								}
	@Override	public int getColor() {					return GREEN;									}
	@Override
	public int getXp() {
		return super.getXp() * 3;
	}
}
