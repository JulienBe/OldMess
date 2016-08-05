package assets;

import behind.SoundMan;
import jeu.CSG;
import assets.sprites.AnimPlayer;
import assets.sprites.Animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class AssetMan implements AssetErrorListener {

	public static TextureRegion add, addShip, bomb, bombGrey, shield, stopBonus, stopBonusGrey, dust, debris, player, backgroundButton, shootingStar, red, smoke;
	public static TextureRegion iconDefaultW, iconTW, iconFireballW, iconSpreadW, iconSunW, iconSpaceInvW, tWeapon, star, effect, addBullet, addShipShot, xp, planet;
	public static TextureRegion playerFireLevel1, playerFireLevel1Propel, playerFireLevel1Hearth;
	public final static AssetManager MAN = new AssetManager();
	private TextureAtlas atlas;

	private static final String ATLAS = "atlas/textures.atlas";
	
	public AssetMan() {
		MAN.setErrorListener(this);
	}
	
	public void load() {
		loadSounds();
		loadMusics();
		Texture.setAssetManager(MAN);
	}

	private static void loadMusics() {
		MAN.load("sons/OutsideNorm.ogg", Music.class);
	}

	private static void loadSounds() {
		MAN.load("sons/162792__timgormly__8-bit-explosion1.wav", Sound.class);
		MAN.load("sons/explosionpetittetechercheuse.wav", Sound.class);
		MAN.load("sons/80500__ggctuk__exp-obj-large03.wav", Sound.class);
		MAN.load("sons/explosionboule.wav", Sound.class);
		MAN.load("sons/explosioncylon.wav", Sound.class);
		
		MAN.load("sons/explosionennemibasequishot.wav", Sound.class);
		MAN.load("sons/explosionkinder.wav", Sound.class);
		MAN.load("sons/47252__nthompson__rocketexpl.wav", Sound.class);
		MAN.load("sons/sun weapon.wav", Sound.class);
		MAN.load("sons/xp.wav", Sound.class);
		
		MAN.load("sons/bonus.wav", Sound.class); // bruit quand prend bonus
	}

	public boolean fini() {
		return MAN.update();
	}

	public void loadPartie2() {
		setSounds();
		loadTextureRegions();
		loadAnims();
	}

	private void loadAnims() {
		AnimPlayer.initAnimation();
		Animations.initAnimations();
	}

	private static void loadTextureRegions() {
		xp = getTextureRegion("xp");
		add = getTextureRegion("add");
		star = getTextureRegion("star");
		bomb = getTextureRegion("bombe");
		smoke = getTextureRegion("smoke");
		player = getTextureRegion("player");
		debris = getTextureRegion("debris");
		effect = getTextureRegion("effect");
		planet = getTextureRegion("planet");
		red = getTextureRegion("rougefonce");
		dust = getTextureRegion("rondblanc");
		shield = getTextureRegion("bouclier");
		tWeapon = getTextureRegion("bluebullet");
		bombGrey = getTextureRegion("bombegris");
		addShip = getTextureRegion("addvaisseau");
		addBullet = getTextureRegion("addbullet");
		stopBonus = getTextureRegion("bonusetoile");
		shootingStar = getTextureRegion("etoilefilante");
		addShipShot = getTextureRegion("addvaisseaushot");
		stopBonusGrey = getTextureRegion("bonusetoilegris");
		backgroundButton = getTextureRegion("backgroundbutton");
		iconTW = getTextureRegion("tweapon80");
		iconSpreadW = getTextureRegion("spread80");
		iconSunW = getTextureRegion("secretsun80");
		iconDefaultW = getTextureRegion("default80");
		iconSpaceInvW = getTextureRegion("spaceinv80");
		iconFireballW = getTextureRegion("fireball80");

		playerFireLevel1 = getTextureRegion("playerFire1");
		playerFireLevel1Hearth = getTextureRegion("playerFireHearth");
		playerFireLevel1Propel = getTextureRegion("playerFirePropel");
	}

	public static TextureRegion getTextureRegion(String string) {
		if (Gdx.app.getVersion() > 0) 
			return CSG.assetMan.getAtlas().findRegion(string);
		TextureRegion tr;
		try {
			tr = new TextureRegion(new Texture(Gdx.files.local("c:\\mess\\" + string + ".png")));
		} catch (Exception e) {
			tr = CSG.assetMan.getAtlas().findRegion(string);
		}
		return tr;
	}

	private TextureAtlas getAtlas() {
		if (atlas == null) {
			atlas = new TextureAtlas(Gdx.files.internal(ATLAS));
		}
		return atlas;
	}

	public void reload() {
		MAN.clear();
		CSG.bloom.resume();
		atlas = null;
		load();
		while (!fini()){
		}
		loadPartie2();
	}

	private void setSounds() {
		SoundMan.explosion2 = MAN.get("sons/explosionpetittetechercheuse.wav", Sound.class);
		SoundMan.explosion1 = MAN.get("sons/162792__timgormly__8-bit-explosion1.wav", Sound.class);
		SoundMan.bigExplosion = MAN.get("sons/80500__ggctuk__exp-obj-large03.wav", Sound.class);
		SoundMan.explosion3 = MAN.get("sons/explosionboule.wav", Sound.class);
		SoundMan.explosion4 = MAN.get("sons/explosioncylon.wav", Sound.class);
		
		SoundMan.sunWeapon = MAN.get("sons/sun weapon.wav", Sound.class);
		SoundMan.bonusTaken = MAN.get("sons/bonus.wav", Sound.class);
		SoundMan.explosion5 = MAN.get("sons/explosionennemibasequishot.wav", Sound.class);
		SoundMan.shotRocket = MAN.get("sons/47252__nthompson__rocketexpl.wav", Sound.class);
		SoundMan.explosion6 = MAN.get("sons/explosionkinder.wav", Sound.class);
		
		SoundMan.xp = MAN.get("sons/xp.wav", Sound.class);

		SoundMan.outsideNorm = MAN.get("sons/OutsideNorm.ogg", Music.class);
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		System.out.println("Probleme pour asset " + asset + " -------- " + throwable.getMessage());
	}

}