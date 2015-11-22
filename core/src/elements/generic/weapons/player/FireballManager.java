package elements.generic.weapons.player;
import jeu.CSG;
import jeu.mode.EndlessMode;
import behind.SoundMan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;

import elements.particular.players.Player;

public class FireballManager extends WeaponManager {

	private static final Sound SOUND = Gdx.audio.newSound(Gdx.files.internal("sons/156895__halgrimm__shot-2-0.wav"));
	private static float posX, posY;

	public void init(Player player) {
		posX = Player.xCenter - Fireball.DIMENSIONS.halfWidth;
		posY = Player.POS.y + player.height;
		if (EndlessMode.alternate)
			SoundMan.playBulletSound(SOUND);
		switch (CSG.profile.lvlFireball) {
		case 1:			nv1(posX, posY);		break;
		case 2:			nv2(posX, posY);		break;
		case 3:			nv3(posX, posY);		break;
		case 4:			nv4(posX, posY);		break;
		case 5:			nv5(posX, posY);		break;
		case 6:			nv6(posX, posY);		break;
		case 7:			nv7();					break;
		default:		nv8();					break;
		}
	}

	private void nv8() {
		Fireball.POOL.obtain().init(posX, posY);
		Fireball.POOL.obtain().init(posX, posY - Fireball.WIDTH05);
		Fireball.POOL.obtain().init(posX, posY - Fireball.WIDTH10);
		Fireball.POOL.obtain().init(posX, posY - Fireball.WIDTH15);
		Fireball.POOL.obtain().init(posX, posY - Fireball.WIDTH20);
		Fireball.POOL.obtain().init(posX, posY - Fireball.WIDTH25);
		Fireball.POOL.obtain().init(posX, posY - Fireball.WIDTH30);
		Fireball.POOL.obtain().init(posX, posY - Fireball.WIDTH35);
		Fireball.POOL.obtain().init(posX, posY - Fireball.WIDTH40);
	}

	private void nv7() {
		Fireball.POOL.obtain().init(posX - Fireball.DIMENSIONS.width, posY - Fireball.DIMENSIONS.width);
		Fireball.POOL.obtain().init(posX + Fireball.DIMENSIONS.width, posY - Fireball.DIMENSIONS.width);
		Fireball.POOL.obtain().init(posX - Fireball.width033, posY - Fireball.width033);
		Fireball.POOL.obtain().init(posX + Fireball.width033, posY - Fireball.width033);
		Fireball.POOL.obtain().init(posX - Fireball.WIDTH066, posY);
		Fireball.POOL.obtain().init(posX + Fireball.WIDTH066, posY);
		Fireball.POOL.obtain().init(posX, posY + Fireball.DIMENSIONS.halfWidth);
	}

	private static void nv6(float posX, float posY) {
		Fireball.POOL.obtain().init(posX - Fireball.DIMENSIONS.halfWidth, posY);
		Fireball.POOL.obtain().init(posX + Fireball.DIMENSIONS.halfWidth, posY);
		Fireball.POOL.obtain().init(posX - Fireball.DIMENSIONS.width, posY - Fireball.DIMENSIONS.halfWidth);
		Fireball.POOL.obtain().init(posX + Fireball.DIMENSIONS.width, posY - Fireball.DIMENSIONS.halfWidth);
		Fireball.POOL.obtain().init(posX, posY + Fireball.DIMENSIONS.halfWidth);
		Fireball.POOL.obtain().init(posX, posY - Fireball.DIMENSIONS.halfWidth);
	}

	private static void nv5(float posX, float posY) {
		Fireball.POOL.obtain().init(posX - Fireball.DIMENSIONS.halfWidth, posY);
		Fireball.POOL.obtain().init(posX + Fireball.DIMENSIONS.halfWidth, posY);
		Fireball.POOL.obtain().init(posX - Fireball.DIMENSIONS.width, posY - Fireball.DIMENSIONS.halfWidth);
		Fireball.POOL.obtain().init(posX + Fireball.DIMENSIONS.width, posY - Fireball.DIMENSIONS.halfWidth);
		Fireball.POOL.obtain().init(posX, posY + Fireball.DIMENSIONS.halfWidth);
	}

	private static void nv4(float posX, float posY) {
		Fireball.POOL.obtain().init(posX - Fireball.DIMENSIONS.halfWidth, posY);
		Fireball.POOL.obtain().init(posX + Fireball.DIMENSIONS.halfWidth, posY);
		Fireball.POOL.obtain().init(posX - Fireball.DIMENSIONS.width, posY -Fireball.DIMENSIONS.halfWidth);
		Fireball.POOL.obtain().init(posX + Fireball.DIMENSIONS.width, posY -Fireball.DIMENSIONS.halfWidth);
	}

	private static void nv3(float posX, float posY) {
		Fireball.POOL.obtain().init(posX - Fireball.DIMENSIONS.width, posY);
		Fireball.POOL.obtain().init(posX + Fireball.DIMENSIONS.width, posY);
		Fireball.POOL.obtain().init(posX, posY + Fireball.DIMENSIONS.halfWidth);
	}

	private static void nv2(float posX, float posY) {
		Fireball.POOL.obtain().init(posX + Fireball.DIMENSIONS.halfWidth, posY);
		Fireball.POOL.obtain().init(posX - Fireball.DIMENSIONS.halfWidth, posY);
	}

	private static void nv1(float posX, float posY) {
		Fireball.POOL.obtain().init(posX, posY);
	}
	
	@Override	public String getLabel() {					return Fireball.LABEL;				}
	@Override	protected float getCadenceTir() {			return Fireball.FIRERATETIR;		}
	@Override	public int nv() {							return CSG.profile.lvlFireball;	}

	@Override
	public Color playerColor() {
		return Color.CYAN;
	}
}