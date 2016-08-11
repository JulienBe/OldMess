package menu.screens;

import jeu.CSG;
import jeu.Strings;
import jeu.mode.EndlessMode;
import menu.tuto.OnClick;
import menu.ui.Button;
import menu.ui.WeaponButton;
import assets.AssetMan;
import behind.SoundMan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.Array;

import elements.generic.weapons.player.BlueSweepWeapon;
import elements.generic.weapons.player.PinkWeapon;
import elements.generic.weapons.player.SpaceInvaderWeapon;
import elements.generic.weapons.player.SunWeapon;
import elements.generic.weapons.player.TWeapon;
import elements.particular.particles.Particles;

public class ChoixDifficulte extends AbstractScreen {

	private final Game game;
	private final Array<WeaponButton> weaponButtons = new Array<WeaponButton>(6);
	private static final float Y_WEAPON = (CSG.height - BUTTON_HEIGHT * 15) - (WeaponButton.width * 2);
	private int etapeCode = 0, cheat = CSG.NO_CHEAT;

	public ChoixDifficulte(Game game) {
		super(game);
		this.game = game;
		setUpScreenElements();
		Gdx.input.setCatchBackKey(true);
	}

	public void setUpScreenElements() {
		add(buttonBack);
		// ** ** ** PIECE OF CAKE ** ** **
		final Button lvl1 = new Button(Strings.LVL1, CSG.menuFont, CSG.screenWidth / PADDING, CSG.height - BUTTON_HEIGHT * 5, BUTTON_WIDTH, BUTTON_HEIGHT, new OnClick() {
			public void onClick() {
				changeMenu(new EndlessMode(game, CSG.batch, 1, cheat));
			}
		});
		add(lvl1);
		// ** ** ** LET'S ROCK ** ** **
		final Button lvl2 = new Button(Strings.LVL2, CSG.menuFont, CSG.screenWidth / PADDING, (int) (CSG.height - BUTTON_HEIGHT * 7.5f), BUTTON_WIDTH, BUTTON_HEIGHT, new OnClick() {
			public void onClick() {
				changeMenu(new EndlessMode(game, CSG.batch, 2, cheat));
			}
		});
		add(lvl2);
		// ** ** ** COME GET SOME ** ** **
		final Button lvl3 = new Button(Strings.LVL3, CSG.menuFont, CSG.screenWidth / PADDING, CSG.height - BUTTON_HEIGHT * 10, BUTTON_WIDTH, BUTTON_HEIGHT, new OnClick() {
			public void onClick() {
				changeMenu(new EndlessMode(game, CSG.batch, 3, cheat));
			}
		});
		add(lvl3);
		if (Gdx.app.getVersion() != 0) {
			CSG.talkToTheWorld.showAds(true);
		}
		final Button lvl4 = new Button(Strings.LVL4, CSG.menuFont, CSG.screenWidth / PADDING, (int) (CSG.height - BUTTON_HEIGHT * 12.5f), BUTTON_WIDTH, BUTTON_HEIGHT, new OnClick() {
			public void onClick() {
				changeMenu(new EndlessMode(game, CSG.batch, 4, cheat));
			}
		});
		add(lvl4);
		final Button chooseWeapon = new Button(Strings.CHOOSE_WEAPON, CSG.menuFont, CSG.screenWidth / PADDING, (int) (CSG.height - BUTTON_HEIGHT * 15.5f), BUTTON_WIDTH, BUTTON_HEIGHT, new OnClick() {
			public void onClick() {
			}
		});
		add(chooseWeapon);
		weaponButtons.add(new WeaponButton(AssetMan.iconDefaultW, 0, PinkWeapon.LABEL, Y_WEAPON));
		weaponButtons.add(new WeaponButton(AssetMan.iconFireballW, 1, elements.generic.weapons.player.Fireball.LABEL, Y_WEAPON));
		weaponButtons.add(new WeaponButton(AssetMan.iconSpreadW, 2, BlueSweepWeapon.LABEL, Y_WEAPON));
		weaponButtons.add(new WeaponButton(AssetMan.iconTW, 3, TWeapon.LABEL, Y_WEAPON));
		weaponButtons.add(new WeaponButton(AssetMan.iconSpaceInvW, 4, SpaceInvaderWeapon.LABEL, Y_WEAPON));
		weaponButtons.add(new WeaponButton(AssetMan.iconSunW, 5, SunWeapon.LABEL, Y_WEAPON));
	}

	@Override
	public void render(float delta) {
		CSG.begin(delta);
		if (Gdx.input.isKeyPressed(Keys.BACK)) 
			keyBackPressed();
		CSG.batch.begin();
		Particles.background(CSG.batch);
		for (int i = 0; i < buttons.size; i++) 
			if (buttons.get(i) != null) buttons.get(i).draw(CSG.batch);
//		for (WeaponButton wb : weaponButtons)
//			wb.draw(CSG.batch);
		CSG.end();
		EndlessMode.majDeltas(true);
		EndlessMode.fps = Gdx.graphics.getFramesPerSecond();
		EndlessMode.perf = EndlessMode.fps / 6;
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) 
			keyBackPressed();
		etapeCode = detectKonamiCode(etapeCode);
		if (etapeCode == 8) {
			SoundMan.playBruitage(SoundMan.bigExplosion);
			cheat = CSG.BEGIN_70K;
			etapeCode++;
		}
	}
	
	

	@Override
	public void keyBackPressed() {
		changeMenu(new Menu(game));
	}
}