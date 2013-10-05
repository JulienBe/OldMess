package objets.ennemis.particuliers.nv1;

import objets.Positionnement;
import objets.armes.ennemi.ArmeEnnemi;
import objets.armes.ennemi.ArmeLaser;
import objets.armes.typeTir.TireurAngle;
import objets.armes.typeTir.Tirs;
import objets.ennemis.CoutsEnnemis;
import objets.ennemis.Ennemis;
import jeu.EndlessMode;
import jeu.Physique;
import jeu.Stats;
import menu.CSG;
import assets.SoundMan;
import assets.animation.AnimationEnnemiAileDeployee;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;


public class Laser extends Ennemis implements TireurAngle {
	
	// ** ** caracteristiques g�n�rales
	public static final int LARGEUR= CSG.LARGEUR_ECRAN / 9;
	public static final int DEMI_LARGEUR = LARGEUR/2;
	private static final float VITESSE = Stats.V_ENN_LASER;
	public static final Tirs TIR = new Tirs(1.1f);
	// ** ** caracteristiques variables.
	protected float prochainTir = 1f;
	public static Pool<Laser> pool = Pools.get(Laser.class);
	private float angle = -90;
	private Vector2 direction = new Vector2();
	private boolean versGauche;
	private static float tmp;

	@Override
	protected Sound getSonExplosion() {
		return SoundMan.explosionennemidebasequitir;
	}
	
	@Override
	protected void free() {
		pool.free(this);
	}
	
	@Override
	public void reset() {
		Positionnement.hautLarge(position, getLargeur(), getHauteur());
		prochainTir = 2f;
		initAngle();
		super.reset();
	}

	public Laser() {
		super();
		Positionnement.hautLarge(position, getLargeur(), getHauteur());
		initAngle();
	}
	
	@Override
	protected int getPvMax() {
		return Stats.PV_LASER;
	}

	private void initAngle() {
		if (position.x + DEMI_LARGEUR > CSG.DEMI_LARGEUR_ZONE_JEU) {
			versGauche = true;
			direction.x = -1;
			angle = -135;
		} else {
			direction.x = 1;
			versGauche = false;
			angle = -45;
		}
		direction.y = -1;
		direction.mul(getVitesse());
	}
	
	@Override
	protected float getVitesse() {
		return VITESSE;
	}

	
	/**
	 * Exactement la m�me que dans la super classe mais �a �vite de faire des getter largeur hauteur...
	 */
	@Override
	public boolean mouvementEtVerif() {
		Physique.mvtSansVerif(position, direction);
		tmp = maintenant * EndlessMode.delta * 5;
		if (maintenant < 16) {
			if (versGauche) {
				direction.rotate(tmp);
				angle += tmp;
			} else {
				direction.rotate(-tmp);
				angle -= tmp;
			}
		}
		return super.mouvementEtVerif();
	}

	@Override
	protected TextureRegion getTexture() {
		return AnimationEnnemiAileDeployee.getTexture(3);
	}
	
	@Override
	public float getAngle() {
		return angle+90;
	}
	
	@Override
	protected void tir() {
		TIR.tirToutDroit(this, mort, maintenant, prochainTir);
	}

	@Override
	public int getXp() {
		return CoutsEnnemis.EnnemiLaser.COUT;
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
	
	@Override
	public ArmeEnnemi getArme() {			return ArmeLaser.pool.obtain();	}
	
	@Override
	public void setProchainTir(float f) {		prochainTir = f;	}

	@Override
	public float getModifVitesse() {	return 0.017f;	}

	@Override
	public float getAngleTir() {			return angle+90;	}
	
	@Override
	public Vector2 getDirectionTir() {
		return direction;
	}
	
	@Override
	public Vector2 getPositionDuTir(int numeroTir) {
		TMP_POS.x = (position.x + DEMI_LARGEUR - ArmeLaser.DEMI_LARGEUR) + (direction.x / 3);
		TMP_POS.y = (position.y + DEMI_LARGEUR - ArmeLaser.DEMI_LARGEUR) + (direction.y / 3);
		return TMP_POS;
	}
	
	@Override
	public void invoquer() {
		LISTE.add(pool.obtain());
	}
	
	@Override
	public float getDirectionY() {
		return direction.y;
	}
	
	@Override
	public float getDirectionX() {
		return direction.x;
	}
}