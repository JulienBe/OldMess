package objets.ennemis.particuliers.boss;

import objets.armes.ennemi.ArmeEnnemi;
import objets.armes.ennemi.BouleBleueRapide;
import objets.armes.typeTir.TireurAngle;
import objets.armes.typeTir.Tirs;
import objets.ennemis.CoutsEnnemis;
import objets.ennemis.Ennemis;
import jeu.CSG;
import jeu.Physique;
import jeu.Stats;
import assets.SoundMan;
import assets.animation.AnimationEnnemiAileDeployee;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;


public class EnnemiAilesDeployees extends Ennemis implements TireurAngle {
	
	// ** ** caracteristiques g�n�rales
	public static final float LARGEUR= CSG.LARGEUR_ECRAN / 16;
	public static final float DEMI_LARGEUR = LARGEUR/2;
	public static final float CADENCETIR = 3.0f;
	public static final float VITESSE_ANGULAIRE = 100;
	public static final float CADENCE = 1.2f;
	public static final Tirs tir = new Tirs(CADENCE);
	// ** ** caracteristiques variables.
	private float prochainTir = 2;
	public static Pool<EnnemiAilesDeployees> pool = Pools.get(EnnemiAilesDeployees.class);
	// ** ** autre
	private float angle;
	private Vector2 direction = new Vector2(-1,0);


	protected Sound getSonExplosion() {
		return SoundMan.explosionpetittetechercheuse;
	}
	
	protected void free() {
		pool.free(this);
	}

	@Override
	protected int getPvMax() {
		return Stats.PV_AILES_DEPLOYEE;
	}

	public boolean mouvementEtVerif() {
		angle = Physique.mouvementTeteChercheuse(direction, position, Stats.V_ENN_AILES_DEPLOYEE, (int)LARGEUR, VITESSE_ANGULAIRE, (int)DEMI_LARGEUR);
		return super.mouvementEtVerif();
	}

	
	@Override
	protected TextureRegion getTexture() {
		return AnimationEnnemiAileDeployee.getTexture(maintenant);
	}
	
	@Override
	protected float getAngle() {
		return angle + 90;
	}

	protected void tir() {
		tir.tirToutDroit(this, mort, maintenant, prochainTir);
	}

	@Override
	public void reset() {
		super.reset();
		prochainTir = 1.5f;
	}

	public int getXp() {		return CoutsEnnemis.EnnemiAilesDeployee.COUT;	}

	public void setAngle(int i) {		this.angle = i;	}


	public void lancer(float dirX, float dirY, float x, float y, float angle) {
		direction.x = dirX;
		direction.y = dirY;
		position.x = x;
		position.y = y;
		this.angle = angle;
		LISTE.add(this);
	}
	

	public int getHauteur() {			return (int)LARGEUR;	}

	public int getLargeur() {			return (int)LARGEUR;	}

	public int getDemiHauteur() {		return (int)DEMI_LARGEUR;	}

	public int getDemiLargeur() {		return (int)DEMI_LARGEUR;	}

	public ArmeEnnemi getArme() {			return BouleBleueRapide.pool.obtain();	}

	public void setProchainTir(float f) {		prochainTir = f;	}

	public float getModifVitesse() {	return 1;	}

	public float getAngleTir() {		return angle;	}

	public Vector2 getDirectionTir() {	return direction;	}
	

	public Vector2 getPositionDuTir(int numeroTir) {
		TMP_POS.x = (position.x + DEMI_LARGEUR - BouleBleueRapide.DEMI_LARGEUR) + (direction.x * 16);
		TMP_POS.y = (position.y + DEMI_LARGEUR - BouleBleueRapide.DEMI_LARGEUR)+ (direction.y * 16);
		return TMP_POS;
	}
	

	public void invoquer() {			LISTE.add(pool.obtain());	}
	
	@Override
	public float getDirectionY() {		return direction.y;	}
	@Override
	public float getDirectionX() {		return direction.x;	}

	@Override
	protected String getLabel() {
		return getClass().toString();
	}
}
