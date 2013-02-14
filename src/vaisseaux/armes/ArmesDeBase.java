package vaisseaux.armes;

import jeu.Endless;
import menu.CSG;
import physique.Physique;
import affichage.animation.AnimationTirFeu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;

/**
 * Arme de base qui fait une boule de feu
 * @author Julien
 *
 */
public class ArmesDeBase extends Armes implements Poolable{
	
	// ** ** caracteristiques g�n�rales
	public static final int LARGEUR= CSG.LARGEUR_ECRAN / 27;
	public static final int DEMI_LARGEUR = LARGEUR/2;
	public static final int HAUTEUR = (int) (LARGEUR * 1.5);
	public static final int DEMI_HAUTEUR = HAUTEUR / 2; 
	private static final int VITESSE_MAX = 600;
	public static final float CADENCETIR = .12f;
	private final int FORCE = 8;
	public static Pool<ArmesDeBase> pool = Pools.get(ArmesDeBase.class);
	//private AnimationDeBase animation;
	private static AnimationTirFeu animation = new AnimationTirFeu();
	private float tpsAnimation = 0;
	private static Sound son = Gdx.audio.newSound(Gdx.files.internal("sons/156895__halgrimm__shot-2-0.wav"));
	// ** ** particules
	public ParticleEffect particleEffect = new ParticleEffect();
	
	/**
	 * ATTENTION ici le init s'occupe d'ajouter � la bonne liste
	 */
	@Override
	public void init(float posX, float posY, int dirX, int dirY, boolean ennemi) {
		son.play(CSG.profil.volumeArme);
		position.x = posX;
		position.y = posY;
		if (ennemi) {
			listeTirsDesEnnemis.add(this);
			direction.y = -1;
		} else {
			direction.y = 1;
			liste.add(this);
		}
	    particleEffect.load(Gdx.files.internal("particules/boulefeu.p"), Gdx.files.internal("particules"));
	    particleEffect.start();
	}
	
	@Override
	public void reset() {
	}
	
	/**
	 * Cr�e l'objet, il faut appeler la m�thode init apr�s
	 */
	public ArmesDeBase() {
		super();
	}

	@Override
	public void afficher(SpriteBatch batch){
		tpsAnimation += Endless.delta;
//		batch.draw(animation.getTexture(tpsAnimation) , position.x, position.y, LARGEUR, HAUTEUR);
		particleEffect.setPosition(position.x + DEMI_LARGEUR, position.y + DEMI_HAUTEUR);
		particleEffect.draw(batch, Endless.delta);
	}

	
	@Override
	public boolean mouvementEtVerif() {
		if (Physique.mouvementDeBase(direction, position, VITESSE_MAX, HAUTEUR, LARGEUR) == false){
			pool.free(this);
			return false;
		}
		return true;
	}

	@Override
	public int getForce() {
		return FORCE + CSG.profil.NvArmeDeBase;
	}

	@Override
	public int getLargeur() {
		return LARGEUR;
	}

	@Override
	public int getHauteur() {
		return HAUTEUR;
	}


	@Override
	public void free() {
		pool.free(this);
	}
}
