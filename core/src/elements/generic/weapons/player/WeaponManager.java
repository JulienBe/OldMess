package elements.generic.weapons.player;

import com.badlogic.gdx.graphics.Color;

import jeu.CSG;
import jeu.Profil;
import jeu.mode.EndlessMode;
import elements.generic.weapons.Weapon;
import elements.particular.players.*;

public abstract class WeaponManager {
	
	public float init(float nextShot, Player player) {
		if (EndlessMode.now > nextShot) {
			if (++Weapon.color >= PlayerWeapon.COLORS.length)
				Weapon.color = 0;
			init(player);
			return EndlessMode.now + getCadenceTir();
		}
		return nextShot;
	}
	
	public static WeaponManager changerArme(WeaponManager arme) {
		if (arme.getLabel() == BlueSweepWeapon.LABEL) 	return new FireballManager();
		if (arme.getLabel() == TWeapon.LABEL) 			return new BlueSweepWeaponManager();
		if (arme.getLabel() == PinkWeapon.LABEL) 		{
			if (CSG.profile.lvlSweepWeapon >= Profil.LVL_UNLOCK && CSG.profile.lvlFireball >= Profil.LVL_UNLOCK && CSG.profile.lvlTWeapon >= Profil.LVL_UNLOCK && CSG.profile.lvlPinkWeapon >= Profil.LVL_UNLOCK) {
				return new SunManager();
			} else {
				return new TWeaponManager();
			}
		}
		if (arme.getLabel() == SunWeapon.LABEL)				return new SpaceInvaderManager();
		if (arme.getLabel() == SpaceInvaderWeapon.LABEL)	return new TWeaponManager();
		return new PinkWeaponManager();
	}
	
	public static WeaponManager getWeaponManager(String label) {
		if (label == BlueSweepWeapon.LABEL) 	return new BlueSweepWeaponManager();
		if (label == TWeapon.LABEL) 			return new TWeaponManager();
		if (label == Fireball.LABEL) 			return new FireballManager();
		if (label == SpaceInvaderWeapon.LABEL) 	return new SpaceInvaderManager();
		if (label == SunWeapon.LABEL) 			return new SunManager();
		return new PinkWeaponManager();
	}

	public abstract Color playerColor();
	protected abstract float getCadenceTir();
	public abstract String getLabel();
	protected abstract void init(Player player);
	public abstract int nv();

}
