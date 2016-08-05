package assets.sprites;

import jeu.mode.EndlessMode;
import assets.AssetMan;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import elements.particular.particles.Particles;

public class AnimPlayer {
	
	private static float timeRight, timeLeft;
	public static final TextureRegion[] TEXTURES = initAnimation();
	public static int state = 2;
	private static final float TIME = .15f; 

	public static TextureRegion[] initAnimation() {
		TextureRegion[] tr = new TextureRegion[5];

		tr[0] = AssetMan.getTextureRegion("joueur1");
		tr[1] = AssetMan.getTextureRegion("joueur2");
		tr[2] = AssetMan.getTextureRegion("joueur3");
		tr[3] = AssetMan.getTextureRegion("joueur4");
		tr[4] = AssetMan.getTextureRegion("joueur5");
		return tr;
	}

//	public static TextureRegion getTexture() {
////		if (EndlessMode.triggerStop)
////			Particles.addGhost(state);
//		return TEXTURES[state];
//	}
//
//	/**
//	 * A appeler quand on va vers la droite
//	 */
//	public static void toLeft() {			// Si besoin : on changerait l'orientation du background ici
//		if (timeRight > TIME)				// Si on va vers la droite depuis un moment
//			state = 0;
//		else {
//			timeRight += EndlessMode.delta;			// Sinon on commence seulement l'anim est pas la m�me
//			state = 1;
//			timeLeft = 0;
//		}
//	}
//
//	public static void toRight() {			// Si besoin : on changerait l'orientation du background ici
//		if (timeLeft > TIME)
//			state = 4;
//		else {
//			state = 3;
//			timeLeft += EndlessMode.delta;
//			timeRight = 0;
//		}
//	}
//
//	public static void straight() {				// Si besoin : on remettrait l'orientation du background a 0 ici
//		if (state == 2) return; 					// on est deja droit
//
//		if (timeRight > 0) {					// on allait a droite avant
//			state = 1;
//			timeRight -= EndlessMode.delta;
//		} else {
//			if (timeLeft > 0) {				// on allait a gauche avant
//				state = 3;
//				timeLeft -= EndlessMode.delta;
//			} else
//				state = 2;
//		}
//	}
	
}
