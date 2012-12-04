package vaisseaux.joueur;

import menu.CSG;
import vaisseaux.TypesArmes;
import vaisseaux.Vaisseaux;
import vaisseaux.armes.ArmesBalayage;
import vaisseaux.armes.ArmesDeBase;
import vaisseaux.armes.ManagerArmeBalayage;
import affichage.animation.AnimationVaisseau;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe g�rant les vaisseaux du types de base
 * @author Julien
 *
 */
public class VaisseauType1 extends Vaisseaux {

	// ** ** dimensions du vaisseau et autre
	public static final int LARGEUR = CSG.LARGEUR_ECRAN / 9;
	public static final int DEMI_LARGEUR = LARGEUR/2;
	public static final int HAUTEUR = CSG.HAUTEUR_ECRAN / 12;
	public static final int DEMI_HAUTEUR = HAUTEUR / 2;
	private final AnimationVaisseau animation;
	// ** ** limites dans l'espace
	private static final int LIMITE_X_GAUCHE = 0 - DEMI_LARGEUR;
	private static final int LIMITE_X_DROITE = CSG.LARGEUR_ECRAN - DEMI_LARGEUR;
	private static final int LIMITE_Y_GAUCHE = 0 - DEMI_HAUTEUR;
	private static final int LIMITE_Y_DROITE = CSG.HAUTEUR_ECRAN - DEMI_HAUTEUR;
	private static final int DEGRE_PRECISION_DEPLACEMENT = 3;
	// ** ** parametres pouvant etre modifi�s par des bonus
	private boolean peutSeTeleporter = false;
	private int vitesseMax = 200;
	private long modifCadenceTir = 0;
	private TypesArmes typeArme = CSG.profil.getArmeSelectionnee();
	private TypesArmes[] typeArmePossible = TypesArmes.LISTE_ARME_JOUEUR;
	// ** ** variable utilitaires
	private float dernierTir = 0;
	private static float tmpCalculDeplacement = 0;
	private float maintenant = 0;
	public float oldPosition = 0;

	/**
	 * initialise le vaisseau avec les parametres par d�faut
	 */
	public VaisseauType1() {
		super();
		animation = new AnimationVaisseau(this, 3);
		initialiser();
	}

	/**
	 * positionne au centre et en bas
	 */
	private void initialiser() {
		position = new Vector2(CSG.DEMI_LARGEUR_ECRAN - DEMI_LARGEUR, HAUTEUR/2);
		vitesseMax += CSG.profil.bonusVitesse;
	}
	/**
	 * affiche le vaisseau � l'endroit pr�vu avec la taille standardt
	 * @param batch
	 * @param delta 
	 */
	public void draw(SpriteBatch batch, float delta) {
		animation.afficher(batch, delta);
		// oblig� de faire l'update ici car le mouvement n'est updat� que quand on clique.
		oldPosition = position.x;
	}
	/**
	 * Fait aller le vaisseau � l'endroit cliqu�.
	 * Si il peut se teleporter il y va directement -- Sinon il se d�place suivant sa vitesse max
	 */
	public void mouvements(float delta) {
		int x = Gdx.input.getX() - DEMI_LARGEUR;
		int y = CSG.HAUTEUR_ECRAN - (Gdx.input.getY() + DEMI_HAUTEUR);
		if(peutSeTeleporter){
			mvtTeleport(x, y);
		} else {
			mvtLimiteVitesse(x, y, delta);
		}
		limites();
	}

	/**
	 * Le vaisseau se d�place vers les coordonn�es pass�es avec un cap � sa vitesse max
	 * @param x
	 * @param y
	 */
	private void mvtLimiteVitesse(int x, int y, float delta) {
		// haut gauche : +x -y
		Vector2 deplacement = new Vector2(x - position.x, y - position.y);
		// Test� avec normalisation sur le vecteur : 3x plus lent. nor() prend � lui seul les 4/5 du temps
		tmpCalculDeplacement = deplacement.len();	
		if (tmpCalculDeplacement > DEGRE_PRECISION_DEPLACEMENT) {
			deplacement.div(tmpCalculDeplacement);
			tmpCalculDeplacement = vitesseMax * delta;
			affichage.ParallaxBackground.changerOrientation(deplacement.x * tmpCalculDeplacement);
			deplacement.mul(tmpCalculDeplacement);
			position.add(deplacement);
		}
	}

	/**
	 * Le vaisseau va aux coordonn�es pass�es
	 * @param x
	 * @param y
	 */
	private void mvtTeleport(int x, int y) {
		position.x = x;
		position.y = y;
	}
	
	/**
	 * oblige le vaisseau a rester dans les limites de l'�cran
	 */
	private void limites() {
		if(position.x < LIMITE_X_GAUCHE) position.x = LIMITE_X_GAUCHE;
		if(position.x > LIMITE_X_DROITE) position.x = LIMITE_X_DROITE;
		if(position.y < LIMITE_Y_GAUCHE) position.y = LIMITE_Y_GAUCHE;
		if(position.y > LIMITE_Y_DROITE) position.y = LIMITE_Y_DROITE;
	}

	/**
	 * v�rifie si le vaisseau peut tirer ou pas. Tir au cas ou
	 * @param listeTir
	 */
	public void tir(float delta){
		maintenant += delta;
		// current time millis prend apparement 5 � 6 cycles contre parfois 100 pour nanotime mais c'est moins pr�cis. JE N'AI PAS VERIFIE
		// -- -- Bon c'est naze la il doit y avoir un meilleur moyen de faire
		switch (typeArme) {
			case ArmeDeBase:
				if (maintenant > dernierTir	+ ArmesDeBase.CADENCETIR + modifCadenceTir) {
					ArmesDeBase e = ArmesDeBase.pool.obtain();
					e.init(position.x + DEMI_LARGEUR	- ArmesDeBase.DEMI_LARGEUR, position.y + HAUTEUR, 0, 1, false);
					dernierTir = maintenant;
				}
				break;
			case ArmeBalayage:
				if (maintenant > dernierTir	+ ArmesBalayage.CADENCETIR + modifCadenceTir) {
					ManagerArmeBalayage.init(position.x + DEMI_LARGEUR - ArmesBalayage.DEMI_LARGEUR, position.y + HAUTEUR, 0, 1, false);
					dernierTir = maintenant;
				}
				break;
		}
	}
	
	public void changerArme(){
		typeArme = TypesArmes.changerArme(typeArmePossible, typeArme);
		CSG.profil.setArmeSelectionnee(typeArme);
	}


	public void perdu() {
		
	}

	@Override
	public int getLargeur() {
		return LARGEUR;
	}

	@Override
	public int getHauteur() {
		return HAUTEUR;
	}
}
