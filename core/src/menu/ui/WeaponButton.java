package menu.ui;

import jeu.CSG;
import jeu.Physic;
import jeu.Profil;
import jeu.Stats;
import assets.AssetMan;
import assets.sprites.AnimPlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import elements.generic.weapons.player.WeaponManager;
import elements.particular.players.Player;

public class WeaponButton extends AbstractButton {
	
	public static final float width = CSG.screenWidth / 7, widthDiv10 = width / 10, height = width * 1.3f, padding = (CSG.screenWidth / 7) / 7, heightBackground = height * 1.1f;
	private static final float unselectedWidth = width * 0.8f, unselectedHeight = height * 0.8f;
	private final TextureRegion tr;
	private final int num;
	private boolean selected;
	private final String label;
	private static float xText = 10;
	
	public WeaponButton(TextureRegion tr, int num, String label, float y) {
		this.tr = tr;
		this.num = num;
		this.label = label;
		setSelected(label);
		this.y = y;
		this.x = ((num * width) + (padding * (num+1))) + UiParticle.HALF_HEIGHT;
		setBarres();
	}

	private void setBarres() {
		barres.clear();
		// top 
		horizontalBarre(x, y + height - UiParticle.HEIGHT, width - UiParticle.HEIGHT);
		// bottom
		horizontalBarre(x, y, width - UiParticle.HEIGHT);
		// left
		verticalBarre(x - UiParticle.HALF_HEIGHT, y, height);
		verticalBarre(x + width - UiParticle.HEIGHT, y, height);
		UiParticle.nbr = 0;
	}
	
	public void setSelected(String label) {
		if (CSG.profile.selectedWeapon.equals(label))	selected = true;
		else											selected = false;
	}

	/**
	 * return true if a weapon has been selected
	 * @param batch
	 * @return 
	 */
	public void draw(SpriteBatch batch, Player player) {
		batch.setColor(CSG.gm.palette().black);
		batch.draw(AssetMan.backgroundButton, x, y, width, heightBackground);
		for (UiParticle b : barres)
			b.draw(batch, barres.size);
		float offsetShip = (width - player.width)/2;
		if (selected) {
			batch.setColor(CSG.gm.palette().white);
			batch.draw(tr, x, y, width, height);
			batch.draw(AnimPlayer.TEXTURES[2], x + offsetShip, y - player.height, player.width, player.height);
		} else {
			batch.setColor(CSG.gm.palette().alpha70);
			batch.draw(tr, x + widthDiv10, y, unselectedWidth, unselectedHeight);
		}
		
		if ( (num == 5 || num == 4) && CSG.profile.isAllWeaponsLvlOk(Profil.LVL_UNLOCK) == false) {
			CSG.menuFontSmall.draw(CSG.batch, "Unlock the 2 remaining weapons by getting the others at level 6 or higher ", xText, 4 + CSG.fontsDimensions.getHeight(CSG.menuFontSmall, "W"));
			xText -= 0.3f;
			batch.setColor(CSG.gm.palette().red);
			batch.draw(AssetMan.dust, x, y, 0, 		0, width*1.2f, 	Stats.u, 1, 1, 40);
			batch.draw(AssetMan.dust, x, y, width, 	0, width, 		Stats.u, 1, 1, -35);
			if (-CSG.fontsDimensions.getWidth(CSG.menuFontSmall, "Unlock the 2 remaining weapons by getting the others at level 6 or higher ") > xText)
				xText = CSG.screenWidth;
		} else {
			if (Gdx.input.justTouched() && Physic.isPointInSquare(Gdx.input.getX(), CSG.height - Gdx.input.getY(), x, y, width)) {
				CSG.profile.setArmeSelectionnee(label);
				Player.weapon = WeaponManager.getWeaponManager(label);
			}
		}
		batch.setColor(CSG.gm.palette().white);
		
		setSelected(label);
	}
	
}
