package elements.generic.weapons.player;

import behind.SoundMan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import jeu.CSG;
import jeu.mode.EndlessMode;
import elements.particular.players.*;

public class SunManager extends WeaponManager {
	
	private static final Vector2 VECTOR = new Vector2(0, 1);
	public static final float[] ROTATIONS = { 360 / 2, 360 / 3, 360 / 4, 360 / 5, 360 / 6, 360 / 7, 360 / 8 };
	
	@Override
	protected void init(Player player) {
		if (EndlessMode.alternate)
			SoundMan.playBulletSound(SoundMan.shotRocket);
		SunWeapon.init(VECTOR.rotate(8), player);
		for (int i = 1; i < CSG.profile.lvlSunWeapon; i++)
			SunWeapon.init(VECTOR.rotate(ROTATIONS[CSG.profile.lvlSunWeapon - 2]), player);
	}

	@Override	public String getLabel() {				return SunWeapon.LABEL;				}
	@Override	protected float getCadenceTir() {		return SunWeapon.FIRERATETIR;		}
	@Override	public int nv() {						return CSG.profile.lvlSunWeapon;		}

	@Override
	public Color playerColor() {
		return Color.ORANGE;
	}
}
