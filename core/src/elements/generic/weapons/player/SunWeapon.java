package elements.generic.weapons.player;

import jeu.CSG;
import jeu.Stats;
import assets.sprites.Animations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.generic.components.Dimensions;
import elements.particular.players.Player;
import elements.particular.particles.Particles;
import elements.particular.players.*;

public class SunWeapon extends PlayerWeapon implements Poolable {

	public static final Dimensions DIMENSIONS = Dimensions.SUN_WEAPON;
	public static final float FIRERATETIR = .060f;
	public static final String LABEL = "SunWeapon";
	public static final Pool<SunWeapon> POOL = new Pool<SunWeapon>(30) {		protected SunWeapon newObject() {			return new SunWeapon();		}	};
	
	public final float color;
	
	public SunWeapon() {
		color = COLORS[CSG.R.nextInt(COLORS.length)];
	}

	@Override
	public void displayOnScreen(SpriteBatch batch) {	
		batch.setColor(color);
		batch.draw(Animations.BLUE_BALL.anim.getTexture(this), pos.x, pos.y, DIMENSIONS.width, DIMENSIONS.height);
		batch.setColor(CSG.gm.palette().white);
	}
	
	@Override	public Dimensions getDimensions() {		return DIMENSIONS;												}
	@Override	public void free() {					POOL.free(this);												}
	@Override	public int getPower() {					return 8;														}
	public static Object getLabel() {					return LABEL;	}

	public static void init(Vector2 dir, Player player) {
		final SunWeapon s = POOL.obtain();
		s.dir.set(dir.x, dir.y).scl(Stats.SUN_SPEED);
		s.pos.set((Player.xCenter) - DIMENSIONS.halfWidth, (Player.yCenter) + player.halfHeight);
		PLAYER_LIST.add(s);
		Particles.shotSparkles(s.pos.x - DIMENSIONS.quartWidth, s.pos.y + DIMENSIONS.halfHeight, dir.angle());
	}
}
