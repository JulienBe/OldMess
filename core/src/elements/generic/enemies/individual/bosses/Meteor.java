package elements.generic.enemies.individual.bosses;

import jeu.CSG;
import jeu.mode.EndlessMode;
import assets.AssetMan;
import assets.sprites.Animations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;

import elements.generic.components.Dimensions;
import elements.generic.components.EnemyStats;
import elements.generic.enemies.Enemy;
import elements.particular.particles.Particles;
import elements.particular.particles.ParticuleBundles;
import elements.particular.particles.individual.explosions.ColorOverTime;

public class Meteor extends Enemy {
	
	public static final Dimensions DIMENSIONS = Dimensions.METEOR;
	public static final Pool<Meteor> POOL = new Pool<Meteor>(16) {		protected Meteor newObject() {			return new Meteor();		}	};
	private final float angularSpeed = (float) (CSG.R.nextGaussian() * 20); 
	
	public void init(float x, float y) {
		dir.set(0, -getEnemyStats().getSpeed());
		pos.set((CSG.R.nextFloat() * CSG.screenWidth) - DIMENSIONS.halfWidth, CSG.height);
		angle = CSG.R.nextFloat() * 360;
	};
	
	protected void displayOnScreen(SpriteBatch batch) {
		angle += angularSpeed * EndlessMode.delta;
		batch.draw(AssetMan.planet, pos.x, pos.y, DIMENSIONS.halfWidth, DIMENSIONS.halfWidth, DIMENSIONS.width, DIMENSIONS.width, 1, 1, angle);
		batch.setColor(CSG.gm.palette().white);
		if (EndlessMode.alternate)
			ColorOverTime.add(Particles.EXPLOSION_COLOR_OVER_TIME, this, 10, ParticuleBundles.SMOKE);
	};

	@Override	public EnemyStats getEnemyStats() {		return EnemyStats.METEOR;	}
	@Override	protected void free() {					POOL.free(this);			}
	@Override	public Dimensions getDimensions() {		return DIMENSIONS;			}
	@Override	public Animations getAnimation() {		return null;				}

}
