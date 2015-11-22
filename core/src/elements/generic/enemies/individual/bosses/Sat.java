package elements.generic.enemies.individual.bosses;

import jeu.CSG;
import jeu.mode.EndlessMode;
import behind.SoundMan;
import assets.sprites.Animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import elements.generic.components.Dimensions;
import elements.generic.components.EnemyStats;
import elements.generic.enemies.Enemy;
import elements.generic.weapons.Weapon;

public class Sat extends Enemy {
	
	/*
	 * 		||		1
	 * 	  \\		2
	 *   =			3
	 *    //		4
	 *      ||		5
	 */
	public static final Dimensions DIMENSIONS = Dimensions.SAT;
	public static final int Y_1 = (int) ((DIMENSIONS.width / 40) * 21), Y_2 = (int) ((DIMENSIONS.width / 40) * 35), Y_3 = (int) ((DIMENSIONS.width) - (int)AddStat.DIMENSIONS.width), Y_4 = (int) ((DIMENSIONS.width / 40) * 6), Y_5 = Y_1, X_2 = (int) (DIMENSIONS.width / 12),
			X_3 = (int) ((DIMENSIONS.width / 2) - (int)AddStat.DIMENSIONS.halfWidth), X_4 = (int) (DIMENSIONS.width - X_2 - AddStat.DIMENSIONS.width), X_5 = (int) (DIMENSIONS.width - AddStat.DIMENSIONS.width), ANGLE_2 = 135, ANGLE_3 = 90, ANGLE_4 = 40, ANGLE_5 = -90, PK = 17, HP = 195, 
			HALF_HP = HP / 2;
	public static Pool<Sat> POOL = Pools.get(Sat.class);
	private int launched = 0;
	private boolean goodShape = true;

	public Sat() {
	}

	public void init(float x, float y) {
		pos.set(CSG.screenWidth, CSG.heightDiv10Mul3 - DIMENSIONS.halfWidth);
		dir.x = -getEnemyStats().getSpeed();
		dir.y = 0;
		launched = 0;
		goodShape = true;
	}
	
	@Override
	protected void move() {
		switch (launched) {
		case 0:
			if (pos.x < CSG.widthMinusBorder - DIMENSIONS.halfWidth) {
				add3();
				if (EndlessMode.difficulty > 1) {
					add1();
					add5();
				}
				launched++;
			}
			break;
		case 1:
			if (pos.x < CSG.widthMinusBorder - DIMENSIONS.width) {
				add1();
				add3();
				add5();
				if (EndlessMode.difficulty > 1) {
					add2();
					add4();
				}
				launched++;
			}
			break;
		case 2:
			if (pos.x < CSG.widthMinusBorder - (DIMENSIONS.width + DIMENSIONS.halfWidth)) {
				all();
				launched++;
			}
			break;
		case 3:
			if (pos.x < CSG.widthMinusBorder - DIMENSIONS.halfWidth) {
				all();
				launched++;
			}
		case 4:
			if (pos.x < CSG.widthMinusBorder - DIMENSIONS.width) {
				all();
				launched++;
			}
			break;
		}
		super.move();
	}

	protected void all() {
		add1();
		add2();
		add3();
		add4();
		add5();
	}

	protected void add5() {
		AddStat.pool.obtain().lancer(0, -1, pos.x + X_3, pos.y, ANGLE_5);
	}

	protected void add4() {
		AddStat.pool.obtain().lancer(-0.76604444f, -0.6427876f, pos.x + X_2, pos.y + Y_4, ANGLE_2);
	}

	protected void add1() {
		AddStat.pool.obtain().lancer(0, 1, pos.x + X_3, pos.y + Y_3, ANGLE_3);
	}

	protected void add2() {
		AddStat.pool.obtain().lancer(-0.76604444f, 0.6427876f, pos.x + X_2, pos.y + Y_2, ANGLE_2);
	}

	protected void add3() {
		AddStat.pool.obtain().lancer(-1, 0, pos.x, pos.y + Y_1, 180);
	}
	
	@Override
	public boolean isTouched(Weapon a) {
		if (hp < HALF_HP)
			goodShape = false;
		return super.isTouched(a);
	}
	@Override	protected Sound getExplosionSound() {	return SoundMan.bigExplosion;		}
	@Override	protected int getMaxHp() {				return super.getPvBoss(HP);			}
	@Override	public EnemyStats getEnemyStats() {		return EnemyStats.SAT;				}
	@Override	public Animations getAnimation() {		return Animations.SAT;				}
	@Override	public Dimensions getDimensions() {		return DIMENSIONS;					}
	@Override	public boolean isInGoodShape() {		return goodShape;					}
	@Override	public void free() {					POOL.free(this);					}

}
