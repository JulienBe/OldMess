package elements.particular.particles;

import jeu.Physic;
import jeu.mode.EndlessMode;

import com.badlogic.gdx.math.Vector2;

import elements.particular.players.Player;
import elements.particular.particles.individual.PrecalculatedParticles;
import elements.particular.particles.individual.SparklesColorOverTimeWide;
import elements.particular.particles.individual.explosions.SparklesColorOverTime;

public class OvaleParticuleGenerator {
	
	private Vector2 positionEmiter = new Vector2();
	private static final Vector2 tmp = new Vector2();
	private float angle, speed = 40;
	private final float originalScale;
	
	public void grow(float f) {
		positionEmiter.scl(f);
	}
	
	public OvaleParticuleGenerator(float hauteur) {
		hauteur /= 5.3f;
		init(hauteur);
		originalScale = positionEmiter.len();
	}

	public void init(float hauteur) {
		positionEmiter.x = hauteur;
		positionEmiter.y = 0;
	}

	public void add(float centreX, float centreY) {
		positionEmiter.rotate(EndlessMode.delta15 * speed);
		
		tmp.x = (centreX - SparklesColorOverTime.HALF_HEIGHT) + positionEmiter.x;
		tmp.y = centreY + positionEmiter.y;
		
		angle = Physic.getAngleWithPlayer(tmp, SparklesColorOverTime.HALF_HEIGHT, 0);
		angle += 110 - (Player.shield * 10);
		
		SparklesColorOverTimeWide.add(
				(centreX - SparklesColorOverTime.HALF_HEIGHT) + positionEmiter.x,
				centreY + positionEmiter.y,
				angle,
				PrecalculatedParticles.colorsOverTimeBlue,
				Particles.COLOR_OVER_TIME_FOREGROUND, 1);
		
		SparklesColorOverTimeWide.add(
				(centreX - SparklesColorOverTime.HALF_HEIGHT) - positionEmiter.x,
				centreY - positionEmiter.y,
				angle,
				PrecalculatedParticles.colorsOverTimeYellowToGreen,
				Particles.COLOR_OVER_TIME_FOREGROUND, 1);
		
		if (!EndlessMode.triggerStop) {
			SparklesColorOverTimeWide.add(
					(centreX - SparklesColorOverTime.HALF_HEIGHT) + positionEmiter.x * 0.9f,
					centreY - positionEmiter.y * 0.9f,
					-angle,
					PrecalculatedParticles.colorsOverTimeYellowToGreen,
					Particles.COLOR_OVER_TIME_FOREGROUND, 1);
			
			SparklesColorOverTimeWide.add(
					(centreX - SparklesColorOverTime.HALF_HEIGHT) - positionEmiter.x * 0.9f,
					centreY + positionEmiter.y * 0.9f,
					-angle,
					PrecalculatedParticles.colorsOverTimeBlue,
					Particles.COLOR_OVER_TIME_FOREGROUND, 1);
		}
	}

	public void lvlChanged(int bouclier) {
		positionEmiter.nor();
		positionEmiter.scl(originalScale);
		positionEmiter.scl(1 + (bouclier * 0.6f));
		speed = 40;
		speed += (bouclier * 0.66f) * speed;
	}
	
}
