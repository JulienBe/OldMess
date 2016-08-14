package jeu;


import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Stats {

	public static final Label.LabelStyle MENU_STYLE = new Label.LabelStyle(CSG.menuFont, CSG.menuFont.getColor());
	// 0.507cm 20 sur s3
	public static final float U = (CSG.height + CSG.screenWidth) / 115f, U2 = U * 2, U3 = U * 3, U4 = U * 4, U5 = U * 5, U6 = U * 6, U8 = U * 8, U10 = U * 10,
			U12 = U * 12, U15 = U * 15, U20 = U * 20, U50 = U * 50, U90 = U * 90, U270 = U * 270, u = U / 2, uDiv75 = u / 75, uDiv450 = u / 450, uDiv2 = u / 2, uDiv4 = u / 4, uSur8 = u / 8,
			IMPACT = U / 240, SCORE = 1, SCREEN_HEIGHT_DIV_100 = CSG.height / 100, WIDTH_DIV_10 = CSG.screenWidth / 10, WIDTH_PLUS_MARGIN = CSG.screenWidth * 1.1f, SCREEN_HEIGHT_PLUS_MARGIN = CSG.height * 1.1f, U25 = U * 25, U22 = U * 22;

	public static final float PIXEL = (int) u;
	public static final float[] MULTI_HP = {1, 1.4f, 1.85f, 2.4f}, MULTI_SPEED = {1, 1.1f, 1.2f, 1.4f};
	public static final float[] MULTI_XP = {CSG.mulLvl1 * CSG.mulSCORE, CSG.mulLvl1 * CSG.mulSCORE * 1.1f, CSG.mulLvl3 * CSG.mulSCORE, CSG.mulLvl4 * CSG.mulSCORE};
	
	// ***********************************************************************************
	// ******************************   B O N U S   **************************************
	// ***********************************************************************************
	
	public static final float V_BONUS = 4 * U;
	public static final float V_BONUS_XP = V_BONUS/1.5f;
	public static final float BOMB_SPEED = V_BONUS/3;
	public static final float V_BONUS_STOP = V_BONUS/2.1f;
	public static final float V_BONUS_TEMPS = V_BONUS/1.4f;
	public static final float V_BONUS_BOUCLIER = (V_BONUS * 1.1f);
	public static final float BONUS_WIDTH = U * 3f, HALF_WIDTH = BONUS_WIDTH/2;
	public static final float DRONE_BONUS_SPEED = V_BONUS/1.8f, BONUS_ADD_ACCELERATION = 15;
	
	// A D D   B O S S   S A T
	public static final float WIDTH_ADD_BOSS_SAT = U * 3f;
	
	// ***********************************************************************************
	// ***********************   A   R   M   E   S   *************************************
	// ***********************************************************************************
	public static final float V_ARME_VICOUS = 20 * U;
	
	// F R A G
	public static final float V_ARME_FRAG = 13 * U;
	public static final float SPEED_FRAGMENTED_WEAPON_AND_MINE = V_ARME_FRAG / 2;
	// A R M E   B O U L E   B L E U E
	public static final float V_ARME_BOULE_BLEU = 10 * U;
	public static final float V_ARME_BOULE_BLEU_LENTE = V_ARME_BOULE_BLEU * 0.8f;
	public static final float V_BOULE_BLEU_RAPIDE = V_ARME_BOULE_BLEU * 1.2f;
	public static final float V_ARME_BOULE_VERTE_RAPIDE = 12 * U;
	public static final float V_ARME_BOULE_VERTE_ROTATION = V_ARME_BOULE_VERTE_RAPIDE / 1.5f;
	// A R M E   K I N D E R
	public static final float V_ARME_KINDER = 13 * U;
	// A R M E   B O S S   Q U A D 
	public static final float V_ARME_BOSS_QUAD = 18 * U;
	// A R M E   S H U R I K E N
	public static final float V_ARME_RAISIN = 19 * U;
	// A R M E   A V I O N
	public static final float V_ARME_AVION = 24 * U;
	// A R M E   E N N E M I   L A S E R
	public static final float V_ARME_LASER = 9 * U;
	// A R M E   I N S E C T E
	public static final float V_INSECTE = 3 * U;
	// =======================  J  O  U  E  U  R  ==============================
	// A R M E   H  A N T E E
	public static final float V_ARME_HANTEE = 120 * U;
	// A R M E   B A L A Y A G E
	public static final float V_ARME_BALAYAGE = 45 * U;
	// A R M E   D E   B A S E
	public static final float V_ARME_DE_BASE = 65 * U;
	// A R M E   T R O I S
	public static final float SPEED_PINK_WEAPON = 80 * U;
	// A R M E   A D D  
	public static final float V_ARME_ADD = 50 * U;
	public static final float SUN_SPEED = 75 * U;
	public static final float V_ARME_SPACE_INVADER = 22 * U;
	
	// ***********************************************************************************
	// ***************   P   A   R   T   I   C   U   L   E   S   *************************
	// ***********************************************************************************
	
	public static final float V_PARTICULE_EXPLOSION = 9 * U;
	public static final float V_PARTICLE_EXPLOSION_SLOW = 4 * U;
	public static final float SPEED_DEBRIS = 2 * U;
	
	public static final int THRUSTER = (int) (U * 14);
	
	
	private static int getHauteur(float largeur, float f) {
		return (int) (largeur * f);
	}
}
