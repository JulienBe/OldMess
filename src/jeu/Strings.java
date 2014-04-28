package jeu;

import java.text.DecimalFormat;

public class Strings {
//	public static final String ADVICE2 = "TRY TO CHANGE THE SENSITIVITY IN THE OPTION";
	public static final String ADVICE3 = "USE YOUR POINTS TO UPGRADE YOUR SHIP",
		ADVICE4 = "USE THE BOMB TO CLEAR THE PATH",
		ADVICE5 = "USE THIS BONUS TO STOP THE TIME";
//	public static final String ADVICE6 = "USE THIS BONUS SLOW THE TIME";
	
	public static final String ACH_LISTEN = "CgkIrsqv7rIVEAIQGg",
		ACH_BOMB = "CgkIrsqv7rIVEAIQGw",
		ACH_15_ENEMY = "CgkIrsqv7rIVEAIQHA",
		ACH_FAVORITE_SHOP = "CgkIrsqv7rIVEAIQHQ",
		ACH_LVL6 = "CgkIrsqv7rIVEAIQHg",
		ACH_LVL8 = "CgkIrsqv7rIVEAIQJw",
		ACH_UNLOCK_SUN = "CgkIrsqv7rIVEAIQJg";
	
	public static final String ACH_30k_XP = "CgkIrsqv7rIVEAIQJA",
		CREATE = "Create",
		FILE = "File",
		SELECT_FRAME = "Let's pick a frame";
	
	public static final String P1 = "HELLO, HERE IS THE BORING STUFF THAT WILL TEACH YOU HOW TO PLAY... THANKS FOR PLAYING !  PAY ATTENTION TO THE BONUS THEY ARE VERY USEFUL",
		SHIP = "Touch the screen to move your ship",
		SHOOTHIM = "Shoot them !",
	//	GG = "ENEMIES WILL GIVE YOU POINTS. \n USE THEM TO IMPROVE YOUR SHIP !",
		TAKE_BONUS = "TAKE THIS BONUS",
		USE_BONUS = "DO NOT TOUCH THE SCREEN TO MAKE THE BONUS MENU APPEAR THEN TOUCH THE BONUS YOU JUST GOT TO USE IT";
	
	public static final String UPGRADE_BUTTON = "Upgrade weapon",
		RESTART_BUTTON = "Restart",
		BACK = "Back to menu",
		DEAD = "Thanks for playing !",
		BRAG_TWITTER = "Brag on Twitter";
	
	public static final String LVL1 = "1. Piece of Cake",
		LVL2 = "2. Let's Rock",
		LVL3 = "3. Come get some",
		LVL4 = "4. Damn I'm Good",
		LVL4LB = "CgkIrsqv7rIVEAIQLA";
	
	public static final String SENSITIVITY = "SENSITIVITY  ",
		TWITTER = "Twitter",
		CHOOSE_WEAPON = "Choose your weapon",
		TUTO_TOUCH = "When you don't touch the screen,\n the game slows down\n but your ship won't shoot",
		TUTO_XP = "Those blue things are points \n Collect them to improve your score !",
		GET_READY = "Get ready !";
	
	public static final DecimalFormat DF = initDf();


	private static DecimalFormat initDf() {
		DecimalFormat DF = new DecimalFormat();
		DF.setMaximumFractionDigits(1);
		DF.setMinimumFractionDigits(1);	
		DF.setDecimalSeparatorAlwaysShown(true);
		return DF;
	}
	
}
