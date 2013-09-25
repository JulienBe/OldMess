package vaisseaux.ennemis;

import jeu.Endless;
import menu.CSG;
import vaisseaux.ennemis.particuliers.Rocher;
import vaisseaux.ennemis.particuliers.boss.EnnemiBossMine;
import vaisseaux.ennemis.particuliers.boss.EnnemiBossQuad;
import vaisseaux.ennemis.particuliers.boss.EnnemiPorteNef;
import vaisseaux.ennemis.particuliers.nv1.Avion;
import vaisseaux.ennemis.particuliers.nv1.BouleQuiSArrete;
import vaisseaux.ennemis.particuliers.nv1.Cylon;
import vaisseaux.ennemis.particuliers.nv1.DeBase;
import vaisseaux.ennemis.particuliers.nv1.Insecte;
import vaisseaux.ennemis.particuliers.nv1.Kinder;
import vaisseaux.ennemis.particuliers.nv1.Laser;
import vaisseaux.ennemis.particuliers.nv1.PorteRaisin;
import vaisseaux.ennemis.particuliers.nv1.QuiTir;
import vaisseaux.ennemis.particuliers.nv1.QuiTir2;
import vaisseaux.ennemis.particuliers.nv1.QuiTourne;
import vaisseaux.ennemis.particuliers.nv1.Toupie;
import vaisseaux.ennemis.particuliers.nv1.ZigZag;
import vaisseaux.ennemis.particuliers.nv2.BouleTirCote;
import vaisseaux.ennemis.particuliers.nv2.BouleTirCoteRotation;
import vaisseaux.ennemis.particuliers.nv2.Kinder2;
import vaisseaux.ennemis.particuliers.nv3.AvionNv3;
import vaisseaux.ennemis.particuliers.nv3.EnnemiBouleQuiSArreteNv3;
import vaisseaux.ennemis.particuliers.nv3.EnnemiCylonNv3;
import vaisseaux.ennemis.particuliers.nv3.EnnemiDeBaseNv3;
import vaisseaux.ennemis.particuliers.nv3.EnnemiInsecteNv3;
import vaisseaux.ennemis.particuliers.nv3.EnnemiKinderNv3;
import vaisseaux.ennemis.particuliers.nv3.EnnemiPorteRaisinNv3;
import vaisseaux.ennemis.particuliers.nv3.EnnemiQuiTir2Nv3;
import vaisseaux.ennemis.particuliers.nv3.EnnemiQuiTourneNv3;
import vaisseaux.ennemis.particuliers.nv3.EnnemiToupieNv3;
import vaisseaux.ennemis.particuliers.nv3.QuiTirNv3;
import vaisseaux.ennemis.particuliers.nv3.ZigZagNv3;

public class Progression {

	// palier : Le nombre d'appels avant 
	// dur�e phase normale : le nombre d'appels avant de rentrer en mode boss. rappel : 1 appel = 1 seconde POUR LE MOMENT
	// palier : le nombre par le quel on multiplie le niveau pour si on va ou non passer un niveau en le comparant au score (voir dans le code)
	private static final int PALIER = 15, DUREE_GRACE = 20, DUREE_PHASE_NORMALE = 37, NV_DE_BASE = 2;
	// Niveau : c'est la difficult� � la quelle on est arriv�
	private static int niveau = NV_DE_BASE;
	private static int pointsDispos = 0, alternerNbPoints = 0, nbAppels = 0, nbBoss = 0;
	// etat : sert � d�finir dans quel mode on se trouve, il peut avoir normal, tempsDeGrace ou Boss.
	private static EtatProgression etat = EtatProgression.Normal;
	// La fr�quence d'apparition en secondes
	public static final float FREQ_APPARATION = 1;

	private Progression() {	}

	// Ce sont les listes des ennemis
	private final static Invocable[] LISTE_LV1 = {
		Insecte.pool.obtain(),
		Laser.pool.obtain(),
		PorteRaisin.pool.obtain(),
		Avion.pool.obtain(),
		QuiTir2.pool.obtain(),
		Kinder.pool.obtain(),
		Cylon.pool.obtain(),
		Toupie.pool.obtain(),
		QuiTourne.pool.obtain(),
		BouleQuiSArrete.pool.obtain(),
		QuiTir.pool.obtain(),
		ZigZag.pool.obtain(),
		DeBase.pool.obtain(),
		};
	private final static Invocable[] LISTE_LV2 = {
		Insecte.pool.obtain(),
		Kinder2.pool.obtain(),
		BouleTirCote.pool.obtain(),
		Laser.pool.obtain(),
		Kinder2.pool.obtain(),
		BouleTirCoteRotation.pool.obtain(),
		PorteRaisin.pool.obtain(),
		Avion.pool.obtain(),
		QuiTir2.pool.obtain(), 
		Kinder.pool.obtain(), 
		Cylon.pool.obtain(), 
		Toupie.pool.obtain(), 
		QuiTourne.pool.obtain(), 
		BouleQuiSArrete.pool.obtain(),
		QuiTir.pool.obtain(),
		ZigZag.pool.obtain(),
		DeBase.pool.obtain(),
		};
	private final static Invocable[] LISTE_LV3 = {
		EnnemiInsecteNv3.pool.obtain(),
		BouleTirCote.pool.obtain(), 
		EnnemiPorteRaisinNv3.pool.obtain(), 
		AvionNv3.pool.obtain(),
		EnnemiQuiTir2Nv3.pool.obtain(), 
		EnnemiKinderNv3.pool.obtain(), 
		EnnemiCylonNv3.pool.obtain(), 
		EnnemiToupieNv3.pool.obtain(), 
		EnnemiQuiTourneNv3.pool.obtain(), 
		EnnemiBouleQuiSArreteNv3.pool.obtain(),
		QuiTirNv3.pool.obtain(),
		ZigZagNv3.pool.obtain(),
		EnnemiDeBaseNv3.pool.obtain(),
		};
	
	
	/*
	 * On passe ici toutes les secondes, en pratique il faut regarder dans la methode qui attribue les points
	 *  mais sur un cycle de 6 secondes il y a deux fois ou on ne fait pas pop d'ennemi
	 */
	public static void invoqueEnnemis() {
		// on monte rapidement de niveau jusqu'au niveau 10. Et �a augmente suivant la difficult� choisie (come get some,...)
		if (niveau < 10)  
			niveau += Endless.modeDifficulte;
		// Si on a fait un certain nombre d'appels on passe en mode boss
		if (nbAppels++ > DUREE_PHASE_NORMALE) {
			etat = EtatProgression.Boss;
			nbAppels = 0;
		}
		// On multiplie le palier (15 POUR LE MOMENT) par le niveau, si c'est plus petit on incr�mente le niveau, de sorte que le niveau aura toujours un rapport de : score / palier
		if (Endless.score > PALIER * niveau)
			niveau++; 

		// On va regarder l'�tat, et donc appele la fonctionne correspondante, il faut donc regarder plus bas :)
		switch (etat) {
		case Normal:			popNormal();			break;
		case TempsDeGrace:		grace();				break;
		case Boss:
			if (Ennemis.LISTE.size > 0) return;
			switch (Endless.modeDifficulte) {
			case 1:				popBoss();				break;
			case 2:				popBoss2();				break;
			case 3:				popBoss3();				break;
			}
		}
	}
	
	/**
	 * Fait apparaitre des ennemis standards
	 */
	private static void popNormal() {
		// d�termine le nombre de points dispos
		calculPoints();
		if (pointsDispos == 0) return;
	
		// Il va utiliser une liste d'ennemi en fonction de la difficult�, les listes sont d�finies plus haut.
		// Il parcourt sa liste, si il a assez de points, il "ach�te" l'ennemi. Il commence par les plus chers jusqu'aux moins chers
		// Attention que si il a assez pour mettons deux ennemis insectes, il n'en ach�te qu'un avant de passer au suivant.
		// Autre point : Si il lui reste des points � la fin c'est tant pis
		switch (Endless.modeDifficulte) {
		case 1: 
			for (Invocable inv : LISTE_LV1) {
				if (pointsDispos >= inv.getXp()) {
					inv.invoquer();
					pointsDispos -= inv.getXp();
				}
			}
			break;
		case 2: 
			for (Invocable inv : LISTE_LV2) {
				if (pointsDispos >= inv.getXp()) {
					inv.invoquer();
					pointsDispos -= inv.getXp();
				}
			}
			break;
		case 3:
			for (Invocable inv : LISTE_LV3) {
				if (pointsDispos >= inv.getXp()) {
					inv.invoquer();
					pointsDispos -= inv.getXp();
				}
			}
			break;
		}
	}
	
	/** calcul des points. Se base sur le niveau. rien sur 3, c'est normal, en fait �a permet de faire un cycle
	 * premier appel : on donne comme points : le nouveau * le mode / 10 
	 * deuxieme : niveau * mode / 6
	 * troisieme ...
	 * quatrieme : La c'est sp�cial, on ne donne aucun point. Ca permet de faire une pause.
	 * cinquieme : ...
	 * sixi�me et dernier : La pareil, rien.
	 */
	private static void calculPoints() {
		alternerNbPoints++;
		switch (alternerNbPoints) { 
		case 0:			pointsDispos = niveau * Endless.modeDifficulte / 10;			break;
		case 1:			pointsDispos = niveau * Endless.modeDifficulte / 6;			break;
		case 2:			pointsDispos = niveau * Endless.modeDifficulte / 3;			break;
		case 4:			pointsDispos = niveau * Endless.modeDifficulte / 2;			break;
		case 5:
			pointsDispos = 0;
			alternerNbPoints = 0;
			break;
		}
	}
	
	/**
	 * Fait apparaitre les boss
	 */
	private static void popBoss() {
		// D'abord 1 porte nef, puis 2, puis un boss quad puis un mine.
		switch (nbBoss) {
		case 0:
			Ennemis.LISTE.add(EnnemiPorteNef.pool.obtain());		// 1 porte nef
			break;
		case 1:														// 2 portes nefs
			Ennemis.LISTE.add(EnnemiPorteNef.pool.obtain());
			EnnemiPorteNef e = EnnemiPorteNef.pool.obtain();			// celui ci plus et plus tard
			e.position.x += EnnemiPorteNef.LARGEUR;
			e.position.y += EnnemiPorteNef.DEMI_LARGEUR;
			Ennemis.LISTE.add(e);
			break;
		case 2:
			Ennemis.LISTE.add(EnnemiBossQuad.pool.obtain());
			break;
		default:
			Ennemis.LISTE.add(EnnemiBossMine.pool.obtain());
			break;
		}
		etat = EtatProgression.TempsDeGrace;
		nbAppels = 0;
		nbBoss++;
	}

	/**
	 * Fait apparaitre les boss suivant le schema niveau 2
	 */
	private static void popBoss2() {
		// D'abord 2 porte nef,puis un boss quad puis un mine.
		switch (nbBoss) {
		case 0:
			Ennemis.LISTE.add(EnnemiPorteNef.pool.obtain());
			EnnemiPorteNef e = EnnemiPorteNef.pool.obtain();
			e.position.x += EnnemiPorteNef.LARGEUR;
			e.position.y += EnnemiPorteNef.DEMI_LARGEUR;
			Ennemis.LISTE.add(e);

			Rocher rocher = Rocher.pool.obtain();
			rocher.position.x = CSG.DEMI_LARGEUR_ECRAN;

			Rocher rocher2 = Rocher.pool.obtain();
			rocher2.position.x = CSG.LARGEUR_BORD;

			Rocher.pool.obtain();
			Rocher.pool.obtain();
			break;
		case 1:
			Ennemis.LISTE.add(EnnemiBossQuad.pool.obtain());
			Rocher.pool.obtain();
			Rocher.pool.obtain();
			break;
		default:
			Ennemis.LISTE.add(EnnemiBossMine.pool.obtain());
			break;
		}
		etat = EtatProgression.TempsDeGrace;
		nbAppels = 0;
		nbBoss++;
	}

	
	private static void popBoss3() {
		// Fait apparaitre un boss quad puis un mine
		switch (nbBoss) {
		case 0:			Ennemis.LISTE.add(EnnemiBossQuad.pool.obtain());			break;
		default:		Ennemis.LISTE.add(EnnemiBossMine.pool.obtain());			break;
		}
		etat = EtatProgression.TempsDeGrace;
		nbAppels = 0;
		nbBoss++;
	}
	
	/*
	 * C'est le temps de grace, rien n'apparait, il s'arrete si on a d�pass� la dur�e de grace ou si le boss est mort
	 */
	private static void grace() {
		if (nbAppels > DUREE_GRACE || Ennemis.LISTE.size == 0) {
			nbAppels = 0;
			etat = EtatProgression.Normal;
		}
	}
	
	
	public static void reset() {
		niveau = NV_DE_BASE;
		nbAppels = 0;
		etat = EtatProgression.Normal;
		nbBoss = 0;
	}

}
