package elements.generic.weapons.player;

import com.badlogic.gdx.graphics.Color;

import behind.SoundMan;
import elements.generic.weapons.Weapon;
import elements.particular.players.Player;
import jeu.CSG;
import jeu.mode.EndlessMode;

public class SpaceInvaderManager extends WeaponManager {
	
	@Override
	public float init(float nextShot, Player player) {
		if (EndlessMode.triggerStop && nextShot < EndlessMode.now) {
			if (++Weapon.color >= PlayerWeapon.COLORS.length)
				Weapon.color = 0;
			SoundMan.playBulletSound(SoundMan.shotRocket);
			SpaceInvaderWeapon.POOL.obtain().init(Player.xCenter - SpaceInvaderWeapon.DIMENSIONS.halfWidth, Player.POS.y + player.height);
			return EndlessMode.now + 2f;
		} 
		return super.init(nextShot, player);
	}
	
	@Override
	protected void init(Player player) {
		if (Weapon.PLAYER_LIST.size != 0)
			return;
		SoundMan.playBulletSound(SoundMan.shotRocket);
		SpaceInvaderWeapon.POOL.obtain().init(Player.xCenter - SpaceInvaderWeapon.DIMENSIONS.halfWidth, Player.POS.y + player.height);
	}

	@Override	public String getLabel() {				return SpaceInvaderWeapon.LABEL;			}
	@Override	protected float getCadenceTir() {		return SpaceInvaderWeapon.FIRERATETIR;		}
	@Override	public int nv() {						return CSG.profile.lvlSpaceInvader;	}

	@Override
	public Color playerColor() {
		return Color.MAGENTA;
	}
}
