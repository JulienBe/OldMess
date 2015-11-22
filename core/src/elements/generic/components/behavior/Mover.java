package elements.generic.components.behavior;

import jeu.CSG;
import jeu.Physic;
import jeu.mode.EndlessMode;

import com.badlogic.gdx.math.Vector2;

import elements.generic.Element;
import elements.generic.enemies.Enemy;
import elements.particular.players.Player;

public abstract class Mover {
	
	public static void straight(Element e) {
		Physic.mvtNoCheck(e.pos, e.dir);
	}
	
	public static void ball(Element e, float slowFactor) {
		if (EndlessMode.alternate) {
			straight(e);
			e.dir.y /= EndlessMode.UnPlusDelta * slowFactor;
		}
	}

	public static void goAway(Enemy e, float factor) {
		straight(e);
		e.dir.y += e.getEnemyStats().getSpeed() * factor;
	}

	public static void ovale(Element e, float factor) {
		e.dir.x -= ((e.pos.x + e.getDimensions().halfWidth) - CSG.halfWidth) * factor;
		e.dir.y -= ((e.pos.y + e.getDimensions().halfHeight) - CSG.halfHeight) * factor;
		straight(e);
	}

	public static void coma(Element e, float factor) {
		e.dir.x -= (e.getNow() * EndlessMode.delta2 * factor);
		straight(e);
	}
	
	public static void rotateNoMove(Element e, float factor) {
		e.dir.rotate(factor * EndlessMode.deltaDiv2);
	}

	public static void rotate(Element e, float factor) {
		e.dir.rotate(factor * EndlessMode.deltaDiv2);
		straight(e);
	}

	public static void sink(Element e, float f) {
		e.dir.rotate(e.getNow() * EndlessMode.delta * f);
		straight(e);
	}

	public static void U(Enemy e) {
		if (e.pos.y < CSG.heightDiv10Mul3 && !EndlessMode.alternate) {
			if (e.getWay())
				e.dir.rotate(EndlessMode.delta2 * e.getEnemyStats().getSpeed());
			else
				e.dir.rotate(EndlessMode.delta2 * -e.getEnemyStats().getSpeed());
		}
		straight(e);
	}

	public static void straightDetectX(Element e, float factor) {
		detectX(e, factor);
		straight(e);
	}

	public static void detectX(Element e, float factor) {
		e.pos.x += -(EndlessMode.delta * (e.pos.x - Player.POS.x) * factor);
	}

	public static void goToPlayer(Enemy e, float rotation) {
		if (EndlessMode.alternate)
			e.setAngle(Physic.setDirToPlayer(e.dir, e.pos, e.getEnemyStats().getSpeed(), e.getDimensions().width, e.getDimensions().halfWidth, rotation) + 90);
		straight(e);
	}
	
	public static void goToTarget(Enemy e, Vector2 target, float rotation) {
		if (EndlessMode.alternate)
			e.setAngle(Physic.setDirTo(e.dir, e.pos, e.getEnemyStats().getSpeed(), e.getDimensions().width, e.getDimensions().halfWidth, rotation, target));
		straight(e);
	}

	public static void toRight(Element e, float factor) {
		e.dir.y -= factor;
		e.dir.x -= factor;
		straight(e);
	}

	public static void insectMove(Element e, int min, int max, float factor) {
		if (e.getNow() > min && e.getNow() < max) {
			e.setAngle(e.dir.angle() + 90);
			Mover.toRight(e, - (factor * (((max - min)-e.getNow())/min) ));
		} else
			Mover.straight(e);
	}

	public static void zigZag(Element e, float factor) {
		oscillateX(e, factor);
		straight(e);
	}

	/**
	 * @param e
	 * @param factor
	 */
	public static void oscillateX(Element e, float factor) {
		e.dir.x -= (((e.pos.x + e.getDimensions().halfWidth) - CSG.halfWidth) * EndlessMode.delta) * factor;
	}

	public static void yOnly(Element e) {
		e.pos.y += e.dir.y * EndlessMode.delta;
	}

	public static void ancorY(Enemy e, float limit) {
		if (e.pos.y > limit) {
			e.dir.y = -e.getEnemyStats().getSpeed();
		}
	}

	public static void ancorX(Element e, float force) {
		if (e.pos.x + e.getDimensions().width < CSG.halfWidth)
			e.dir.x += force * EndlessMode.delta;
		else if (e.pos.x > CSG.halfWidth)
			e.dir.x -= force * EndlessMode.delta;
	}

	public static float tmp;
	public static void orbitPlayer(Enemy e, float min) {
		CSG.tmpPos.set(Player.xCenter - (e.pos.x + e.getDimensions().halfWidth), Player.yCenter - (e.pos.y - e.getDimensions().halfHeight));
		CSG.tmpDir.set(CSG.tmpPos);
		tmp = CSG.tmpPos.len();
		
		// GET CLOSER
		if (tmp > min) {
			CSG.tmpPos.scl(0.99f);
		// GO AWAY
		} else {
			CSG.tmpPos.scl(1.01f);
		}
		CSG.tmpPos.rotate(EndlessMode.delta15);
		
	}

}
