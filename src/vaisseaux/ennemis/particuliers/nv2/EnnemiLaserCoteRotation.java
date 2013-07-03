package vaisseaux.ennemis.particuliers.nv2;

import jeu.Endless;
import jeu.Physique;
import jeu.Stats;
import menu.CSG;
import vaisseaux.armes.ArmeEnnemiLaserCoteRotation;
import vaisseaux.ennemis.Ennemis;
import vaisseaux.ennemis.TypesEnnemis;
import assets.SoundMan;
import assets.animation.AnimationBouleBleuRouge;
import assets.animation.AnimationExplosion1;
import assets.particules.ParticulesExplosionPetite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class EnnemiLaserCoteRotation extends Ennemis{

	public static final int LARGEUR = CSG.LARGEUR_ECRAN / 21;
	public static final int DEMI_LARGEUR = LARGEUR/2;
	public static final float VITESSE_ANGLE = 100f;
	public static Pool<EnnemiLaserCoteRotation> pool = Pools.get(EnnemiLaserCoteRotation.class);
	// ** ** animations
	protected float maintenant;
	protected float tpsAnimationExplosion;
	private ParticulesExplosionPetite explosion;
	// ** ** moins aleatoire
	private static int nbEnnemisAvant = 0;
	private static float posXInitiale;
	private float angle = 0;
	private Vector2 direction = new Vector2();
	private float prochainTir;
	private int numeroTir;
	
	@Override
	protected void mort() {
		SoundMan.playBruitage(SoundMan.explosiontoupie);
		if (CSG.profil.particules){
			explosion = ParticulesExplosionPetite.pool.obtain();
			explosion.setPosition(position.x + DEMI_LARGEUR, position.y + DEMI_LARGEUR);
			explosion.start();
		} else {	tpsAnimationExplosion = 0;	}
	}

	/**
	 * Contructeur sans argument, appele par le pool
	 */
	public EnnemiLaserCoteRotation() {
		super(CSG.LARGEUR_BORD,	CSG.HAUTEUR_ECRAN + LARGEUR, Stats.PVMAX_LASER_COTE_PETIT);
		placement();
	}

	private void placement() {
		direction.x = 0;
		direction.y = -1f;
		prochainTir = 1;
		numeroTir = 1;
		maintenant = 0;
		position.y = CSG.HAUTEUR_ECRAN + LARGEUR;
		if (nbEnnemisAvant == 0) {
			posXInitiale = Physique.getEmplacementX(DEMI_LARGEUR);
			position.x = posXInitiale;
			angle = 50 + (float) (Math.random() * -120);
		} else {
			if (posXInitiale + DEMI_LARGEUR > CSG.DEMI_LARGEUR_ZONE_JEU) {
				position.x = posXInitiale - LARGEUR * nbEnnemisAvant;
				angle = (float) ((Math.random() * 50) + 15);
			} else {
				position.x = posXInitiale + LARGEUR * nbEnnemisAvant;
				angle = (float) ((Math.random() * -50) - 15);
			}
		}
		nbEnnemisAvant++;
		if (nbEnnemisAvant > 4) nbEnnemisAvant = 0;
		direction.rotate(angle);
		angle += -90;
	}
	
	/**
	 * Initialise l'ennemi
	 */
	public void init() {
		tpsAnimationExplosion = 0;
		if (CSG.profil.particules & explosion == null)	explosion = ParticulesExplosionPetite.pool.obtain();
	}

	@Override
	public void reset() {
		mort = false;
		if (!CSG.profil.particules)		tpsAnimationExplosion = 0;
		pv = Stats.PVMAX_LASER_COTE_PETIT;
		placement();
	}

	@Override
	public void afficher(SpriteBatch batch) {
		if (mort){
			explosion.draw(batch, Endless.delta);
		} else {
			batch.draw(AnimationBouleBleuRouge.getTexture(maintenant) , position.x, position.y, DEMI_LARGEUR, DEMI_LARGEUR, LARGEUR, LARGEUR, 1, 1, angle, false);
			maintenant += Endless.delta;
		}
	}
	@Override
	public void afficherSansParticules(SpriteBatch batch) {
		if (mort){
			batch.draw(AnimationExplosion1.getTexture(tpsAnimationExplosion), position.x, position.y, LARGEUR, LARGEUR);
			tpsAnimationExplosion += Endless.delta;
		} else {
			batch.draw(AnimationBouleBleuRouge.getTexture(maintenant) , position.x, position.y, DEMI_LARGEUR, DEMI_LARGEUR, LARGEUR, LARGEUR, 1, 1, angle, false);
			maintenant += Endless.delta;
		}
	}

	@Override
	public boolean mouvementEtVerif() {
		angle += VITESSE_ANGLE * Endless.delta;
		if( (mort && explosion.isComplete()) | Physique.mouvementDeBase(direction, position, Stats.VITESSE_ENNEMI_LASER_COTE_PETIT, LARGEUR) == false){
			pool.free(this);
			if (explosion != null) explosion.free();
			return false;
		}
		return true;
	}
	@Override
	public boolean mouvementEtVerifSansParticules() {
		angle += VITESSE_ANGLE * Endless.delta;
		if( (mort && tpsAnimationExplosion > AnimationExplosion1.tpsTotalAnimationExplosion1)
				|| Physique.mouvementDeBase(direction, position, Stats.VITESSE_ENNEMI_LASER_COTE_PETIT, LARGEUR) == false){
			pool.free(this);
			return false;
		}
		return true;
	}

	@Override
	protected void tir() {
		if (!mort && prochainTir < maintenant) {
			ArmeEnnemiLaserCoteRotation gauche = ArmeEnnemiLaserCoteRotation.pool.obtain();
			gauche.init(position.x + DEMI_LARGEUR - ArmeEnnemiLaserCoteRotation.DEMI_LARGEUR, position.y + DEMI_LARGEUR - ArmeEnnemiLaserCoteRotation.DEMI_LARGEUR, angle, true);

			ArmeEnnemiLaserCoteRotation droite = ArmeEnnemiLaserCoteRotation.pool.obtain();
			droite.init(position.x + DEMI_LARGEUR - ArmeEnnemiLaserCoteRotation.DEMI_LARGEUR, position.y + DEMI_LARGEUR - ArmeEnnemiLaserCoteRotation.DEMI_LARGEUR, angle, false);
			
			prochainTir = (maintenant + ArmeEnnemiLaserCoteRotation.CADENCETIR * numeroTir);
			if (++numeroTir > 12) numeroTir = 1;
		}
	}

	@Override
	public Rectangle getRectangleCollision() {
		collision.set(position.x, position.y, LARGEUR, LARGEUR);
		return collision;
	}

	@Override
	public int getXp() {
		return TypesEnnemis.EnnemiLaserCoteRotationNv2.COUT;
	}

	@Override
	public int getHauteur() {
		return LARGEUR;
	}

	@Override
	public int getLargeur() {
		return LARGEUR;
	}
	
	@Override
	public int getDemiHauteur() {
		return DEMI_LARGEUR;
	}

	@Override
	public int getDemiLargeur() {
		return DEMI_LARGEUR;
	}
	
}