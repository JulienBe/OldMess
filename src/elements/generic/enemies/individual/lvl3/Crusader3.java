package elements.generic.enemies.individual.lvl3;

import jeu.CSG;
import jeu.Stats;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.shots.AbstractShot;
import elements.generic.enemies.individual.lvl1.Crusader;

public class Crusader3 extends Crusader {

	public static final Pool<Crusader3> POOL = Pools.get(Crusader3.class);
	private static final int HP = getModulatedPv(Stats.CRUASER_HP, 3), HALF_HP = HP / 2, XP = getXp(BASE_XP, 3);
	private static final float FIRERATE = 0.55f;
	
	@Override
	protected void interval() {
		shotNumber = AbstractShot.interval(this, 3, 1 + CSG.R.nextFloat() * 2, shotNumber);
	}
	
	@Override	public float getFirerate() {		return FIRERATE;				}
	@Override	public void free() {				POOL.free(this);				}	
	@Override	public int getBonusValue() {		return BASE_XP;					}
	@Override	protected int getPallierPv() {		return HALF_HP;					}
	@Override	protected int getMaxHp() {			return HP;						}
	@Override	public int getXp() {				return XP;						}
}
