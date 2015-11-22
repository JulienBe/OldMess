package elements.generic.components;

import jeu.Stats;
import jeu.mode.EndlessMode;

public enum EnemyStats {
	
	BASIC(
			8,
			9, Stats.MULTI_SPEED[1], Stats.MULTI_SPEED[2] * 2, Stats.MULTI_SPEED[3] * 2,
			1,
			25),
	GROUP(
			60,
			4,
			18,
			35,
			0.8f,	0.95f,	0.9f,	0.8f),
	SHOOTER(
			27,
			5,
			10,
			40,
			1.5f,	0.8f,	0.45f,	0.35f), 
	ZIGZAG(
			13,
			1,
			3,
			40),
	BALL(
			40,
			25,
			12,
			35,
			5, 	0.8f, 	0.3f,	0.2f),
	BALL_SIDE_SHOT(
			60,
			25,
			73,
			40,
			0.9f, 0.9f, 0.8f, 0.7f),
	SAT(
			430,
			3,
			80,
			80),
	DIABOLO(
			67,
			9,
			19,
			25,
			0.33f,	0.8f,	0.5f,	0.4f),
	ROUND_N_ROUND(
			80,
			10,
			21,
			30,
			.5f, 	.85f,	.7f,	.5f), 
	CYLON(
			90,
			6,
			12,
			35,
			3.6f),
	KINDER(
			120,
			10,
			32,
			45,
			0.55f, 	0.9f, 	0.4f, 	0.35f),
	BOSS_QUAD(
			510,
			10,
			200,
			200),
	CRUSADER(
			320,
			4, 50, 1, 1,
			91,
			40,
			0.01f),
	PLANE(
			150,
			9.5f,
			45,
			55,
			1,	0.9f,	0.33f,	0.28f),
	SHOOTER_FRAG(
			200,
			6,
			39,
			50,
			2.3f, 	0.9f,	0.5f,	0.45f),
	BOSS_MINE(
			780,
			24,
			200,
			200),
	LASER(
			190,
			7,
			32,
			50,
			1,	0.8f,	0.33f,	0.30f),
	INSECT(
			380,
			6,
			86,
			50,
			1.9f),
	ADD_SAT(
			25,
			7,
			15,
			25,
			1.8f),
	VICIOUS(
			0,
			2,
			0,
			50,
			3.8f),
	METEOR(
			500,
			26,
			500,
			200
					);
	
	public int explosion = 0;
	public int[] hps = {0, 0, 0, 0}, halfHps = {0, 0, 0, 0}, oneThirdHps = {0, 0, 0, 0}, twoThirdsHps = {0, 0, 0, 0}, xp = {0, 0, 0, 0}, bonusValue = {0, 0, 0, 0};
	public float[] speeds = {0, 0, 0, 0}, halfSpeeds = {0, 0, 0, 0}, firerates = {0, 0, 0, 0};
	
	private EnemyStats(int hp, float speed, int xp, int explosion, float firerate) {
		this(hp, speed, xp, explosion);
		firerates[0] = firerate;
		firerates[1] = firerate;
		firerates[2] = firerate * 0.95f;
		firerates[3] = firerate * 0.9f;
	}

	private EnemyStats(int hp, float speed, int xp, int explosion, float firerate, float firerateMul2, float firerateMul3, float firerateMul4) {
		this(hp, speed, xp, explosion);
		firerates[0] = firerate;
		firerates[1] = firerate * firerateMul2;
		firerates[2] = firerate * firerateMul3;
		firerates[3] = firerate * firerateMul4;
	}

	private EnemyStats(int hp, float speed, int xp, int explosion) {
		this(hp, xp, explosion);
		speed *= Stats.U;
		for (int i = 0; i < 4; i++) {
			speeds[i] = speed * Stats.MULTI_SPEED[i];
			speeds[i] = speed * Stats.MULTI_SPEED[i];
		}
		initHalfSpeeds();
	}

	private EnemyStats(int hp, int xp, int explosion) {
		for (int i = 0; i < 4; i++) {
			this.xp[i] = (int) (xp * Stats.MULTI_XP[i]);
			bonusValue[i] = xp;

			hps[i] = (int) (hp * Stats.MULTI_HP[i]);
			halfHps[i] = hps[i] / 2;
			oneThirdHps[i] = hps[i] / 3;
			twoThirdsHps[i] = (int) (hps[i] * 0.666f);
		}
		this.explosion = explosion;
	}

	private EnemyStats(int hp, float speed, float speedMul2, float speedMul3, float speedMul4, int xp, int explosion) {
		this(hp, xp, explosion);
		speed *= Stats.U;
		speeds[0] = speed;
		speeds[1] = speed * speedMul2;
		speeds[2] = speed * speedMul3;
		speeds[3] = speed * speedMul4;
		initHalfSpeeds();
	}

	private void initHalfSpeeds() {
		for (int i = 0; i < 4; i++)
			halfSpeeds[i] = speeds[i] / 2;
	}

	public float getSpeed() {
		return speeds[EndlessMode.difficulty-1];
	}

	public int getHalfHp() {
		return halfHps[EndlessMode.difficulty-1];
	}

	public int getTwoThirdsHps() {
		return twoThirdsHps[EndlessMode.difficulty-1];
	}
	
	public int getOneThirdHps() {
		return oneThirdHps[EndlessMode.difficulty-1];
	}

	public int getHp() {
		return hps[EndlessMode.difficulty-1];
	}

	public float getHalfSpeed() {
		return halfSpeeds[EndlessMode.difficulty-1];
	}

	public int getXp() {
		return xp[EndlessMode.difficulty-1];
	}

	public int getBonusValue() {
		return bonusValue[EndlessMode.difficulty-1];
	}

	public float getFirerate() {
		return firerates[EndlessMode.difficulty-1];
	}
}
