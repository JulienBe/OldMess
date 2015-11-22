package elements.particular.particles;

import jeu.CSG;
import jeu.Stats;
import jeu.mode.EndlessMode;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import elements.generic.enemies.Enemy;
import elements.generic.weapons.enemies.EnemyWeapon;
import elements.generic.weapons.player.Fireball;
import elements.generic.weapons.player.PinkWeapon;
import elements.generic.weapons.player.PlayerWeapon;
import elements.generic.weapons.player.SunWeapon;
import elements.generic.weapons.player.TWeapon;
import elements.particular.players.Player;
import elements.particular.bonuses.Bomb;
import elements.particular.particles.individual.Ghost;
import elements.particular.particles.individual.PrecalculatedParticles;
import elements.particular.particles.individual.SparklesColorOverTimeWide;
import elements.particular.particles.individual.TimeParticle;
import elements.particular.particles.individual.explosions.ColorOverTime;
import elements.particular.particles.individual.explosions.DebrisExplosion;
import elements.particular.particles.individual.explosions.Explosion;
import elements.particular.particles.individual.explosions.ExplosionImpactBullet;
import elements.particular.particles.individual.explosions.SparklesColorOverTime;
import elements.particular.particles.individual.smoke.MovingSmoke;
import elements.particular.particles.individual.smoke.Smoke;
import elements.particular.particles.individual.trhuster.ThrusterParticle;
import elements.particular.particles.individual.weapon.BlueSweepParticle;
import elements.particular.particles.individual.weapon.FireballParticle;
import elements.particular.particles.individual.weapon.PinkParticle;
import elements.particular.particles.individual.weapon.SpaceInvaderParticle;
import elements.particular.particles.individual.weapon.SunParticle;
import elements.particular.particles.individual.weapon.TWeaponParticles;

public class Particles {
	 	
	public static final int MAX_THRUSTER = 600, MAX_BACKGROUND = 400, MAX_DUST = 5;
	public static int nbFlammes = 0;
	
	public static final Array<Explosion> EXPLOSIONS = new Array<Explosion>();
	public static final Array<ColorOverTime> EXPLOSION_COLOR_OVER_TIME = new Array<ColorOverTime>();
	
	public static final Array<DebrisExplosion> WHITE_SPARKLES_NOT_MOVING = new Array<DebrisExplosion>();
	public static final Array<Explosion> EXPLOSIONS_IMPACT = new Array<Explosion>();
	public static final Array<ExplosionImpactBullet> EXPLOSION_IMPACT_BULLET = new Array<ExplosionImpactBullet>();
	
	public static final Array<SparklesColorOverTime> COLOR_OVER_TIME = new Array<SparklesColorOverTime>();
	public static final Array<SparklesColorOverTimeWide> COLOR_OVER_TIME_FOREGROUND = new Array<SparklesColorOverTimeWide>();
	
	private static final Array<FireballParticle> FIREBALL = new Array<FireballParticle>();
	private static final Array<TWeaponParticles> T_WEAPON = new Array<TWeaponParticles>();
	private static final Array<ThrusterParticle> THRUSTER = new Array<ThrusterParticle>(false, MAX_THRUSTER);
	private static final Array<Ghost> GHOSTS = new Array<Ghost>(false, 20);
	private static final Array<Smoke> SMOKE = new Array<Smoke>(false, 20);
	private static final Array<MovingSmoke> MOVING_SMOKE = new Array<MovingSmoke>(false, 20);
	public static final Array<SunParticle> SUN_WEAPON = new Array<SunParticle>(false, MAX_THRUSTER);
	public static final Array<PinkParticle> PINK_WEAPON = new Array<PinkParticle>(false, MAX_THRUSTER);
	public static final Array<BlueSweepParticle> BLUE_SWEEP_WEAPON = new Array<BlueSweepParticle>();
	public static final Array<SpaceInvaderParticle> SPACE_INVADER = new Array<SpaceInvaderParticle>(300);
	public static final Array<TimeParticle> TIME = new Array<TimeParticle>(40);
	private static float yShip = 0, planetOffset = CSG.height;// + (CSG.height * (CSG.R.nextFloat() + 1));
	private static float alphaBg = 1, mv = 0;
	private static final Rectangle PLANET = new Rectangle();
	
	/**
	 * Display the background and add a star if required
	 * @param batch
	 */
	public static void background(SpriteBatch batch) {
		if (alphaBg > .5f)		mv -= 0.00001f;
		else					mv += 0.00001f;
		alphaBg += mv;
		SparklesColorOverTime.act(COLOR_OVER_TIME, batch);
		Explosion.act(EXPLOSIONS, batch);
		ColorOverTime.act(EXPLOSION_COLOR_OVER_TIME, batch);
	}

	public static void draw(SpriteBatch batch) {
		ThrusterParticle.act(THRUSTER, batch);
		PinkParticle.act(PINK_WEAPON, batch);
		SpaceInvaderParticle.act(SPACE_INVADER, batch);
		TWeaponParticles.act(T_WEAPON, batch);
		BlueSweepParticle.act(BLUE_SWEEP_WEAPON, batch);
		FireballParticle.act(FIREBALL, batch);
		SunParticle.act(SUN_WEAPON, batch);
		Ghost.act(GHOSTS, batch);
		Smoke.act(SMOKE, batch);
		MovingSmoke.act(MOVING_SMOKE, batch);
		DebrisExplosion.act(WHITE_SPARKLES_NOT_MOVING, batch);
		TimeParticle.act(TIME, batch);
	}

	public static void drawImpacts(SpriteBatch batch) {
		ExplosionImpactBullet.act(EXPLOSION_IMPACT_BULLET, batch);
	}
	
	public static void addPartEnemyTouched(PlayerWeapon a, Enemy e) {
		SparklesColorOverTime.bulletImpact(COLOR_OVER_TIME, a, a.getColors());
		ExplosionImpactBullet.add(EXPLOSION_IMPACT_BULLET, a);
	}

	public static void addThrusterParticles(Player v) {
		if (THRUSTER.size > 1000)
			return;
		if (yShip-1 < Player.POS.y) {
			for (int i = 0; i < 3; i++)
				elements.particular.particles.Smoke.add(Player.xCenter - v.halfWidth * 0.3f, Player.POS.y, v.halfWidth);
//				THRUSTER.add(ThrusterParticle.POOL.obtain().init(v));

			if (yShip+1 < Player.POS.y)
				for (int i = 0; i < EndlessMode.fps / 10; i++)
					elements.particular.particles.Smoke.add(Player.xCenter - v.halfWidth * 0.3f, Player.POS.y, v.halfWidth);
//					THRUSTER.add(ThrusterParticle.POOL.obtain().init(v));
		}
		yShip = Player.POS.y;
	}
	
	public static Rectangle getPlanetRectangle() {
		PLANET.set(-Stats.U2, planetOffset - EndlessMode.now * 2, Stats.U10, Stats.U10);
		return PLANET;
	}

	public static void clear() {
		// planet
		planetOffset = CSG.height + (CSG.height * CSG.R.nextFloat());
		
		Explosion.clear(EXPLOSIONS);
		Explosion.clear(EXPLOSIONS_IMPACT);
		DebrisExplosion.clear(WHITE_SPARKLES_NOT_MOVING);
		PinkParticle.clear(PINK_WEAPON);
		ThrusterParticle.clear(THRUSTER);
		TWeaponParticles.clear(T_WEAPON);
		SpaceInvaderParticle.clear(SPACE_INVADER);
		FireballParticle.clear(FIREBALL);
		BlueSweepParticle.clear(BLUE_SWEEP_WEAPON);
		SunParticle.clear(SUN_WEAPON);
		Ghost.clear(GHOSTS);
		Smoke.clear(SMOKE);
		MovingSmoke.clear(MOVING_SMOKE);
		ExplosionImpactBullet.clear(EXPLOSION_IMPACT_BULLET);
		TimeParticle.clear(TIME);
		SparklesColorOverTime.clear(COLOR_OVER_TIME);
		SparklesColorOverTimeWide.clear(COLOR_OVER_TIME_FOREGROUND);
		ColorOverTime.clear(EXPLOSION_COLOR_OVER_TIME);
		elements.particular.particles.Smoke.clear();
		nbFlammes = 0;
	}
	
	public static void explosion(Enemy e) {
		switch (e.getColor()) {
		case Enemy.BLUE:
			Explosion.add(EXPLOSIONS, e, PrecalculatedParticles.RANDDOM_BLUES);
			SparklesColorOverTime.add(COLOR_OVER_TIME, e, PrecalculatedParticles.colorsOverTimeBlue);
			ColorOverTime.add(EXPLOSION_COLOR_OVER_TIME, e, ParticuleBundles.BLUE_LONG);
			break;
		case Enemy.GREEN:
			Explosion.add(EXPLOSIONS, e, PrecalculatedParticles.RANDDOM_GREENS);
			SparklesColorOverTime.add(COLOR_OVER_TIME, e, PrecalculatedParticles.colorsOverTimeYellowToGreen, PrecalculatedParticles.colorsOverTimeCyanToGreen);
			ColorOverTime.add(EXPLOSION_COLOR_OVER_TIME, e, ParticuleBundles.YELLOW_TO_GREEN_LONG, ParticuleBundles.CYAN_TO_GREEN_LONG);
			break;
		default:
			Explosion.add(EXPLOSIONS, e, PrecalculatedParticles.RANDDOM_REDS);
			SparklesColorOverTime.add(COLOR_OVER_TIME, e, PrecalculatedParticles.colorsOverTimeRed);
			ColorOverTime.add(EXPLOSION_COLOR_OVER_TIME, e, ParticuleBundles.RED_LONG);
			break;
		}
	}

	public static void armeHantee(TWeapon a) {
		TWeaponParticles.add(T_WEAPON, a);
	}

	public static void ajoutArmeDeBase(Fireball a) {
		FireballParticle.add(a, FIREBALL);
	}

	public static void pinkParticle(PinkWeapon a) {
		final PinkParticle p = PinkParticle.POOL.obtain();
		p.init(a);
		PINK_WEAPON.add(p);
	}

	public static void ajoutSun(SunWeapon sunWeapon, Vector2 vector) {
		final SunParticle p = SunParticle.POOL.obtain();
		p.init(sunWeapon, vector);
		SUN_WEAPON.add(p);
	}

	public static void addGhost(int etat, Player player) {
		final Ghost g = Ghost.POOL.obtain();
		g.init(etat, player);
		GHOSTS.add(g);
	}
	
	public static void smokeMoving(float x, float y, Vector2 dir, int color) {
		MOVING_SMOKE.add(MovingSmoke.POOL.obtain().init(x, y, convertColor(color), dir));
	}

	public static float[] convertColor(int color) {
		switch (color) {
		case Enemy.BLUE:	return PrecalculatedParticles.colorsBlue;
		case Enemy.GREEN:	return PrecalculatedParticles.colorsYellowToGreen;
		default:			return PrecalculatedParticles.colorsRed;
		}
	}
	
	public static void smokeMoving(float x, float y, boolean rnd, int color) {
		MOVING_SMOKE.add(MovingSmoke.POOL.obtain().init(x, y, rnd, convertColor(color)));
	}

	public static void smokeMoving(float x, float y, boolean rnd, int color, Vector2 dir) {
		MOVING_SMOKE.add(MovingSmoke.POOL.obtain().init(x, y, rnd, convertColor(color), dir));
	}

	public static void smoke(float x, float y, boolean rnd, float[] colors) {
		SMOKE.add(Smoke.POOL.obtain().init(x, y, rnd, colors));
	}
	
	public static void smoke(float x, float y, boolean rnd, float[] colors, float dirX, float dirY) {
		MOVING_SMOKE.add(MovingSmoke.POOL.obtain().init(x, y, rnd, colors, dirX, dirY));
	}
	
	private static float angle = 0, tmpX, tmpY;
	public static void popOutWeapon(EnemyWeapon w) {
		angle = 0;
		tmpX = w.pos.x + w.getDimensions().halfWidth;
		tmpY = w.pos.y + w.getDimensions().halfHeight;
		for (int i = 0; i < 10; i++) {
			SparklesColorOverTime.add(tmpX, tmpY, angle, PrecalculatedParticles.colorsOverTimeYellowToGreenLong, Stats.U12);
			SparklesColorOverTime.add(tmpX, tmpY, angle + 18, PrecalculatedParticles.colorsOverTimeBlueLong, Stats.U5);
			angle += 36;
		}
	}
	
	public static void bomb(Bomb bonusBombe) {
		DebrisExplosion.bomb(bonusBombe, WHITE_SPARKLES_NOT_MOVING);
	}

	public static void addChronoGenerator() {
		for (Enemy e : Enemy.LIST)
			TimeParticle.generate(e, TIME);
	}

	public static void bombExplosion(PlayerWeapon a) {
		Explosion.blow(a, EXPLOSIONS);
		ExplosionImpactBullet.blow(a, EXPLOSION_IMPACT_BULLET);
		SparklesColorOverTime.blow(a, COLOR_OVER_TIME);
	}

	public static void foreground(SpriteBatch batch) {
		SparklesColorOverTimeWide.act(COLOR_OVER_TIME_FOREGROUND, batch);
	}

	public static void shotSparkles(float x, float y, float angle) {
		for (int i = 0; i < 2; i++)
			SparklesColorOverTime.add(x, y, angle + (float)CSG.R.nextGaussian() * 25, PrecalculatedParticles.colorsOverTimeMuzzle, (Stats.U50 + (Stats.U270 * CSG.R.nextFloat())) * 0.75f );
	}

	private static float tmpOffsetX, tmpOffsetY;
	public static void enemyHit(Enemy e, float[] colors) {
		for (int i = 0; i < 5; i++) {
			do {
				tmpOffsetX = (float) (CSG.R.nextGaussian() * e.getDimensions().quartWidth);
			} while (Math.abs(tmpOffsetX) > e.getDimensions().halfWidth);
			do {
				tmpOffsetY = (float) (CSG.R.nextGaussian() * e.getDimensions().quartHeight);
			} while (Math.abs(tmpOffsetY) > e.getDimensions().halfHeight);
			Particles.smoke(e.pos.x + e.getDimensions().halfWidth + tmpOffsetX, e.pos.y + e.getDimensions().halfHeight + tmpOffsetY, false, colors);
		}
	}

}
