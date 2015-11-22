package elements.generic.weapons.player;

import jeu.CSG;
import jeu.mode.EndlessMode;
import behind.SoundMan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;

import elements.particular.players.Player;

public class PinkWeaponManager extends WeaponManager {
	// rotation 85 : [-0.9961947:0.087155804]
	// rotation 15 : [-0.25881904:0.9659258]
	// rotation 10 : -0.17364818:0.9848077
	// rotation 5 : [-0.08715574:0.9961947]
	private static final Sound SOUND = Gdx.audio.newSound(Gdx.files.internal("sons/156895__halgrimm__shot-2-0.wav"));
	private static float posX, posY;
	private static boolean alternate = true;
	
	public void init(Player player){
		posX = Player.xCenter - PinkWeapon.DIMENSIONS.halfWidth / 3;
		posY = Player.POS.y + player.height;
		alternate = !alternate;
		if (EndlessMode.alternate)
			SoundMan.playBulletSound(SOUND);
		// 5
		if (alternate)			PinkWeapon.POOL.obtain().init(posX, posY, -0.08715574f, 0.9961947f);
		else					PinkWeapon.POOL.obtain().init(posX, posY, 0.08715574f, 0.9961947f);
		switch (CSG.profile.lvlPinkWeapon) {
		case 8:			shoot(-0.70710677f, -0.70710677f);
		case 7:			shoot(0.70710677f, 0.70710677f);
		case 6:			shoot(0.9848077f, 0.17364822f); 			// 80
		case 5:			shoot(0.9961947f, 0.087155804f);			// 85
		case 4:			shoot(1, 0);								// 90
		case 3:			shoot(0.25881904f, 0.9659258f);				// 15
		case 2:			shoot(0.17364818f, 0.9848077f);				// 10
		}
	}

	private void shoot(float angleX, float angleY) {
		if (!alternate)		PinkWeapon.POOL.obtain().init(posX, posY, -angleX, angleY);
		else				PinkWeapon.POOL.obtain().init(posX, posY, angleX, angleY);
	}

	@Override	public String getLabel() {				return PinkWeapon.LABEL;			}
	@Override	protected float getCadenceTir() {		return PinkWeapon.FIRERATETIR;		}
	@Override	public int nv() {						return CSG.profile.lvlPinkWeapon;	}

	@Override
	public Color playerColor() {
		return Color.GREEN;
	}
	
}
