package elements.generic.weapons.player;

import jeu.CSG;
import jeu.Stats;
import assets.AssetMan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.particular.particles.Particles;
import elements.particular.particles.individual.PrecalculatedParticles;

/**
 * Arme de base qui fait une boule de feu
 * @author Julien
 *
 */
public class Fireball extends PlayerWeapon implements Poolable{
	
	public static final int WIDTH = (int) MINWIDTH, halfWidth = WIDTH/2, width033 = (int) (WIDTH * .33f), WIDTH066 = (int) (WIDTH * .66f), WIDTH05 = (int) (WIDTH*.03f), WIDTH10 = (int) (WIDTH*.06f),
			WIDTH15 = (int) (WIDTH*.09f), WIDTH20 = (int) (WIDTH*.12f), WIDTH25 = (int) (WIDTH*.15f), WIDTH30 = (int) (WIDTH*.18f), WIDTH35 = (int) (WIDTH*.21f), WIDTH40 = (int) (WIDTH*.24f);
	public static final float CADENCETIR = initCadence(.12f, 1);
	public static final String LABEL = "ArmeDeBase";
	public static final Pool<Fireball> POOL = new Pool<Fireball>(10) {
		@Override
		protected Fireball newObject() {
			return new Fireball();
		}
	};
	
	public Fireball() {
		dir.x = 0;
		dir.y = Stats.V_ARME_DE_BASE;
		dir.rotate((float) (CSG.R.nextFloat()-0.5f) * 10);
	}
	
	public static float[] couleurs = {
		AssetMan.convertARGB(1, 108f/255f, 24f/255f, 	0),
		AssetMan.convertARGB(1, 128f/255f, 24f/255f, 	0),
		AssetMan.convertARGB(1, 146f/255f, 28f/255f, 	0),
		AssetMan.convertARGB(1, 152f/255f, 24f/255f, 	0),
		AssetMan.convertARGB(1, 156f/255f, 28f/255f, 	0),
		
		AssetMan.convertARGB(1, 174f/255f, 22f/255f,	0),
		AssetMan.convertARGB(1, 184f/255f, 22f/255f, 	0),
		AssetMan.convertARGB(1, 132f/255f, 40f/255f, 	0),
		AssetMan.convertARGB(1, 208f/255f, 20f/255f, 	0),
		AssetMan.convertARGB(1, 218f/255f, 20f/255f, 	0),
		
		AssetMan.convertARGB(1, 228f/255f, 96f/255f, 	12/255f),
		AssetMan.convertARGB(1, 232f/255f, 136f/255f, 	24/255f),
		AssetMan.convertARGB(1, 238f/255f, 96f/255f, 	12/255f),
		AssetMan.convertARGB(1, 240f/255f, 172f/255f, 	36/255f),
		AssetMan.convertARGB(1, 245f/255f, 122f/255f, 	36/255f),
		
		AssetMan.convertARGB(1, 245f/255f, 136f/255f, 	24/255f),
		AssetMan.convertARGB(1, 248f/255f, 150f/255f, 	48/255f),
		AssetMan.convertARGB(1, 248f/255f, 200f/255f, 	48/255f),
		AssetMan.convertARGB(1, 252f/255f, 208f/255f, 	60/255f),
		AssetMan.convertARGB(1, 252f/255f, 228f/255f, 	60/255f),
		
		AssetMan.convertARGB(1, 252f/255f, 248f/255f, 148/255f)};
	
	/**
	 * ATTENTION ici le init s'occupe d'ajouter � la bonne liste
	 */
	public void init(float posX, float posY) {
		pos.x = posX;
		pos.y = posY;
		PLAYER_LIST.add(this);
	}

	@Override	public void draw(SpriteBatch batch) {
		Particles.ajoutArmeDeBase(this);	
		Particles.ajoutArmeDeBase(this);	
		Particles.ajoutArmeDeBase(this);	
	}
	@Override	public int getWidth() {						return WIDTH;	}	
	@Override	public int getHeight() {					return WIDTH;	}
	@Override	public void free() {						POOL.free(this);	}
	@Override	public float getColor() {					return couleurs[R.nextInt(couleurs.length)];	}
	@Override	public int getHalfWidth() {					return halfWidth;	}
	@Override	public int getHalfHeight() {				return halfWidth;	}
	@Override		public float[] getColors() {			return PrecalculatedParticles.colorsOverTimeRed;		}

	public static String getLabel() {	return "ArmeDeBase";	}
}
