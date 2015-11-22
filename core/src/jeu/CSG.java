package jeu;

import java.util.Random;

import behind.FontsDimensions;
import behind.Gm;
import behind.shaders.ShaderToys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.NumberUtils;
import jeu.mode.EndlessMode;
import menu.screens.Loading;
import behind.shaders.Bloom;
import assets.AssetMan;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import elements.generic.enemies.Enemy;
import elements.generic.weapons.Weapon;
import elements.particular.bonuses.Bonus;
import elements.particular.particles.Particles;

public class CSG extends Game implements ApplicationListener {

	public static int halfWidth, oneThirdWidth, halfHeight, screenWidth, borderWidth, widthDiv20, widthDiv100, twoThirdsWidth, widthMinusBorder, widthMinusBorderMul2, height, widthDiv10, heightDiv10, heightDiv100, heightDiv50, heightDiv20, heightPlus4, heightDiv8,
		widthDiv5, widthDiv5Mul2, widthDiv5Mul3, widthDiv5Mul4, heightDiv4, heightDiv10Mul9, heightDiv10Mul8, heightDiv10Mul7, heightDiv10Mul3;
	public static float RATIO;
	public static final int CONTROLE_TOUCH_NON_RELATIVE = 0, CONTROLE_DPAD = 1, CONTROLE_ACCELEROMETRE = 2, CONTROLE_TOUCH_RELATIVE = 3, CONTROLE_MAX = 3;
	public static ProfilManager profilManager;
	public static Profil profile;
	public static BitmapFont menuFont, menuFontSmall, menuFontBlack, scoreFont;//, outlineScoreFont;
	public static AssetMan assetMan;
	public static SpriteBatch batch;
	public static TalkToTheWorld talkToTheWorld;
	public static final Random R = new Random();
	public static final float mulLvl1 = 1, mulLvl2 = 1.15f, mulLvl3 = 2.2f, mulLvl4 = 4f, mulSCORE = 1.1f;
	public static final int dbVersion = 2;
	public static final int NO_CHEAT = 0, BEGIN_70K = 1;
	public static float originalScoreFontScale;
	public static final Vector2 vecteurPosition = new Vector2(), tmpPos = new Vector2(), tmpDir = new Vector2(), tmp2 = new Vector2();
	public static Vector2 vecteurCible = new Vector2();
	public static int tmpInt;
    public static final Gm gm = new Gm();
	public static FontsDimensions fontsDimensions;
    public static final ShaderToys SHADER = new ShaderToys();
	
	public CSG(TalkToTheWorld google) {
		CSG.talkToTheWorld = google;
	}

	@Override
	public void create() {
		log("Create");
		fontsDimensions = new FontsDimensions();
		batch = new SpriteBatch(5460);
		assetMan = new AssetMan();
		// **************  V A R I A B L E S   C O N S T A N T E S  :) ********************
		if (Gdx.app.getVersion() != 0)
			CSG.talkToTheWorld.showAds(true);
		dimensions();
		// ***********************  P R O F I L  ****************************
		profilManager = new ProfilManager();
		profile = profilManager.retrieveProfile();
		initFonts();
		// ***** Une fois que toutes les variables globales sont chargees on lance le loading pour charger les assets
		final Loading loading = new Loading(this);
		setScreen(loading);
	}

	public static void log(String s ) {
		Gdx.app.log("ESGLOG", s);
	}

	public static void initFonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("PolenticalNeonRegular.ttf"));
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		
		float dimension = CSG.height + CSG.screenWidth;
		dimension = dimension / 900;
		if (dimension < 1f)
			dimension = 1f;
		
		menuFontBlack = setFont((int) (25 * dimension),			dimension, generator, param, gm.palette().black);
		originalScoreFontScale = dimension * 0.75f;
		scoreFont = setFont((int) (13 * dimension), 			dimension, generator, param, gm.palette().convertARGB(1, 0, 0, 1));

		menuFontSmall = setFont((int) (12 * dimension), 		dimension, generator, param, gm.palette().convertARGB(.02f, .62f, 0.99f, 1));
		menuFont = setFont((int) (23 * dimension), 		        dimension, generator, param, gm.palette().convertARGB(.02f, .62f, 0.99f, 1));
		
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}

	private static BitmapFont setFont(int size, float dimension, FreeTypeFontGenerator generator, FreeTypeFontParameter param, float color) {
		param.size = size;
		BitmapFont font = generator.generateFont(param);
		font.setColor(new Color(NumberUtils.floatToIntColor(color)));
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return font;
	}

	public static void dimensions() {
		halfWidth = Gdx.graphics.getWidth() / 2;
		halfHeight = Gdx.graphics.getHeight() / 2;
		screenWidth = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		RATIO = screenWidth / height;
		widthDiv20 = screenWidth / 20;
		widthDiv100 = screenWidth / 100;
		borderWidth = screenWidth / 3;
		oneThirdWidth = screenWidth / 3;
		widthMinusBorder = screenWidth - borderWidth;
		widthMinusBorderMul2 = screenWidth - (borderWidth*2);
		widthDiv10 = screenWidth / 10;
		heightDiv10 = height / 10;
		heightDiv100 = height / 100;
		heightDiv50 = height / 50;
		heightDiv8 = height / 8;
		heightDiv20 = height / 20;
		widthDiv5 = screenWidth / 5;
		widthDiv5Mul2 = widthDiv5 * 2;
		widthDiv5Mul3 = widthDiv5 * 3;
		widthDiv5Mul4 = widthDiv5 * 4;
		heightDiv4 = height / 4;
		
		heightPlus4 = height + 4;
		heightDiv10Mul9 = height - heightDiv10;
		heightDiv10Mul8 = height - (heightDiv10 * 2);
		heightDiv10Mul7 = height - (heightDiv10 * 3);
		heightDiv10Mul3 = height - (heightDiv10 * 7);
		
		twoThirdsWidth = (screenWidth / 2) * 3;
	}
	
	public static void reset(){
		Enemy.clear();
		Weapon.clear();
		// Progression.reset(); 
        Bonus.resetAll();
        Particles.clear();
        EndlessMode.reset();
	}

	public static AssetMan getAssetMan() {
		return assetMan;
	}

	public static Bloom bloom;
	public static boolean alternateGraphics = false;
	
	public static void initBloom() {
		try {
			bloom = new Bloom();
			bloom.setBloomIntesity(CSG.profile.bloomIntensity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void begin(float delta) {
		EndlessMode.delta = delta;
		EndlessMode.majDeltas(true);
		EndlessMode.now += delta;
		bloom.capture();
	}

	public static void end() {
		batch.end();
		bloom.render();
	}
	
	public static float[] convert(Array<Float> tmp) {
		float[] array2 = new float[tmp.size];
	    int i=0;
	    for (Float f : tmp) {
	        array2[i] = f.floatValue();
	        i++;
	    }
		return array2;
	}
	
	public static float[] getDifferences(float[] array) {
		final Array<Float> tmp = new Array<Float>();
		tmp.add(0f);
		for (int i = 1; i < array.length; i++)
			tmp.add( (array[i]-array[i-1])/2);
		return CSG.convert(tmp);
	}

	public static float[] getDouble(float[] array) {
		final Array<Float> tmp = new Array<Float>();
		tmp.add(0f);
		for (int i = 1; i < array.length; i++)
			tmp.add(array[i]*2);
		return CSG.convert(tmp);
	}
}
