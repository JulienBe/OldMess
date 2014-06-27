package elements.generic.enemies.individual.lvl3;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.enemies.individual.lvl1.Diabolo;
import jeu.CSG;
import jeu.Stats;

public class Diabolo3 extends Diabolo {
	
	public static final Pool<Diabolo3> POOL = Pools.get(Diabolo3.class);
	private static final int LVL = 3, HP = getModulatedPv(Stats.HP_DIABOLO, LVL), XP = getXp(BASE_XP, LVL);
	private static final float SPEED = Diabolo.SPEED18, HALF_SPEED = SPEED / 2, FIRERATE = 0.15f;
	private int shotNumber = 0;
	
	@Override	protected void interval() {		shotNumber = AbstractShot.interval(this, 4, 2, shotNumber);	}
	@Override	protected float getDemiVitesse() {		return HALF_SPEED;				}
	@Override	public void free() {					POOL.free(this);				}
	@Override 	public float getFirerate() {			return FIRERATE;				}
	@Override	public int getBonusValue() {			return BASE_XP;					}
	@Override	public float getSpeed() {				return SPEED;					}
	@Override	protected int getMaxHp() {				return HP;						}
	@Override	public int getXp() {					return XP;						}
	@Override 	protected float getPhaseDuration() {	return 14;						}
	@Override	public int getNumberOfShots() {			return 4;						}
}
