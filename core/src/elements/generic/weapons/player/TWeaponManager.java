package elements.generic.weapons.player;

import jeu.CSG;
import jeu.mode.EndlessMode;
import behind.SoundMan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;

import elements.particular.players.Player;

public class TWeaponManager extends WeaponManager {
	
	private static final Sound SOUND = Gdx.audio.newSound(Gdx.files.internal("sons/armehantee.wav"));
	private static float posX, posY;
	
	public void init(Player player){
		posX = Player.xCenter - TWeapon.DIMENSIONS.halfWidth;
		posY = Player.POS.y + player.height;
		if (EndlessMode.alternate)
			SoundMan.playBulletSound(SOUND);
		switch (CSG.profile.lvlTWeapon) {
		case 1:			nv1();			break;
		case 2:			nv2();			break;
		case 4:			nv4();			break;
		case 5:			nv5();			break;
		case 6:			nv6();			break;
		case 7:			nv7();			break;
		default:		nv3();			break;
		}
	}

	private void nv7() {
		TWeapon.POOL.obtain().init(posX - TWeapon.DIMENSIONS.halfWidth, posY - TWeapon.DIMENSIONS.halfWidth);
		TWeapon.POOL.obtain().init(posX + TWeapon.DIMENSIONS.halfWidth, posY - TWeapon.DIMENSIONS.halfWidth);
		TWeapon.POOL.obtain().init(posX - TWeapon.DIMENSIONS.halfWidth, posY);
		TWeapon.POOL.obtain().init(posX + TWeapon.DIMENSIONS.halfWidth, posY);
		TWeapon.POOL.obtain().init(posX - TWeapon.DIMENSIONS.width, posY - TWeapon.DIMENSIONS.halfWidth);
		TWeapon.POOL.obtain().init(posX + TWeapon.DIMENSIONS.width, posY - TWeapon.DIMENSIONS.halfWidth);
		TWeapon.POOL.obtain().init(posX, posY + TWeapon.DIMENSIONS.halfWidth);
	}

	private static void nv6() {
		TWeapon.POOL.obtain().init(posX - TWeapon.DIMENSIONS.halfWidth, posY);
		TWeapon.POOL.obtain().init(posX + TWeapon.DIMENSIONS.halfWidth, posY);
		TWeapon.POOL.obtain().init(posX - TWeapon.DIMENSIONS.width, posY - TWeapon.DIMENSIONS.halfWidth);
		TWeapon.POOL.obtain().init(posX + TWeapon.DIMENSIONS.width, posY - TWeapon.DIMENSIONS.halfWidth);
		TWeapon.POOL.obtain().init(posX, posY + TWeapon.DIMENSIONS.halfWidth);
		TWeapon.POOL.obtain().init(posX, posY - TWeapon.DIMENSIONS.halfWidth);
	}

	private static void nv5() {
		TWeapon.POOL.obtain().init(posX - TWeapon.DIMENSIONS.halfWidth, posY);
		TWeapon.POOL.obtain().init(posX + TWeapon.DIMENSIONS.halfWidth, posY);
		TWeapon.POOL.obtain().init(posX - TWeapon.DIMENSIONS.width, posY - TWeapon.DIMENSIONS.halfWidth);
		TWeapon.POOL.obtain().init(posX + TWeapon.DIMENSIONS.width, posY - TWeapon.DIMENSIONS.halfWidth);
		TWeapon.POOL.obtain().init(posX, posY + TWeapon.DIMENSIONS.halfWidth);
	}

	private static void nv4() {
		TWeapon.POOL.obtain().init(posX - TWeapon.DIMENSIONS.halfWidth, posY);
		TWeapon.POOL.obtain().init(posX + TWeapon.DIMENSIONS.halfWidth, posY);
		TWeapon.POOL.obtain().init(posX - TWeapon.DIMENSIONS.width, posY - TWeapon.DIMENSIONS.halfWidth);
		TWeapon.POOL.obtain().init(posX + TWeapon.DIMENSIONS.width, posY - TWeapon.DIMENSIONS.halfWidth);
	}

	private static void nv3() {
		TWeapon.POOL.obtain().init(posX - TWeapon.DIMENSIONS.width, posY);
		TWeapon.POOL.obtain().init(posX + TWeapon.DIMENSIONS.width, posY);
		TWeapon.POOL.obtain().init(posX, posY + TWeapon.DIMENSIONS.halfWidth);
	}

	private static void nv2() {
		TWeapon.POOL.obtain().init(posX - TWeapon.DIMENSIONS.halfWidth, posY);
		TWeapon.POOL.obtain().init(posX + TWeapon.DIMENSIONS.halfWidth, posY);
	}

	private static void nv1() {
		TWeapon.POOL.obtain().init(posX, posY);
	}

	@Override	public String getLabel() {				return TWeapon.LABEL;				}
	@Override	public int nv() {						return CSG.profile.lvlTWeapon;	}
	@Override
	protected float getCadenceTir() {
		if (CSG.profile.lvlTWeapon == 8)
			return TWeapon.FIRERATETIRLVL8;
		return TWeapon.FIRERATETIR;		
	}

	@Override
	public Color playerColor() {
		return Color.WHITE;
	}
}
