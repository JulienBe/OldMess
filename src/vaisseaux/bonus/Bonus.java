package vaisseaux.bonus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public abstract class Bonus {
	
public static Array<Bonus> liste = new Array<Bonus>(30);
	
	public float posX;
	public float posY;
	
	/**
	 * Cr�e un bonus en initialisant le vecteur
	 * @param x
	 * @param y
	 * @param val
	 */
	public Bonus(float x, float y) {
		this.posX = x;
		this.posY = y;
		liste.add(this);
	}

	/**
	 * Affiche l'xp et la fait tourner
	 * @param batch
	 * @param delta 
	 */
	public static void affichageEtMouvement(SpriteBatch batch, float delta) {
		for(Bonus b : liste)
			b.afficherEtMvt(batch, delta);
	}


	abstract void afficherEtMvt(SpriteBatch batch, float delta);

	public abstract float getDemiLargeurColl();

	public abstract float getDemiHauteurColl();

	public abstract float getLargeurColl();
	
	public abstract float getHauteurColl();

	public abstract void pris();
}
