package menu.screens;

import jeu.CSG;
import jeu.Strings;
import menu.tuto.OnClick;
import menu.ui.Button;
import behind.SoundMan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MenuOptions extends AbstractScreen {

	private static final int LINE2 = 4;
	private static final int LINE3 = 6;
	private static final int LINE4 = 8;
	private static final int LINE5 = 14;
	private static final int LINE6 = 12;
	private static final int LINE7 = 10;
//	private static final Credits BOTTOM_TXT = new Credits("    SENSITIVITY WILL APPLY TO ANY CONTROL YOU CHOOSE, 1 IS NEUTRAL, ABOVE 1 IS MORE SENSITIVE   ");

	public MenuOptions(final Game game) {
		super(game);
		
		Gdx.input.setCatchBackKey(true);
		add(buttonBack);
		// ************************ A R M E S ****************************************************************

		// ************************ B R U I T A G E S ********************************************************
		final Button bruit = new Button(BRUITAGE_VOL + (int) (CSG.profile.effectsVolume * 10), CSG.menuFontSmall, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, (CSG.screenWidth / 2) - Menu.SMALL_BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE2, new OnClick() {
			public void onClick() {}
		});
		add(bruit);
		add(new Button(MOINS, CSG.menuFont, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, CSG.screenWidth / Menu.PADDING, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE2 + Menu.MINI_BOUTON_HEIGHT / 2, new OnClick() {
			public void onClick() {
				CSG.profile.diminuerVolumeBruitage();
				bruit.setTexte(BRUITAGE_VOL + (int) (CSG.profile.effectsVolume * 10));
				SoundMan.playBruitage(SoundMan.shotRocket);
			}
		}));
		add(new Button(PLUS, CSG.menuFont, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, CSG.screenWidth - (CSG.screenWidth / Menu.PADDING) - Menu.MINI_BOUTON_WIDTH, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE2 + Menu.MINI_BOUTON_HEIGHT / 2, new OnClick() {
			public void onClick() {
				CSG.profile.augmenterVolumeBruitage();
				bruit.setTexte(BRUITAGE_VOL + (int) (CSG.profile.effectsVolume * 10));
				SoundMan.playBruitage(SoundMan.shotRocket);
			}
		}));
		// ************************ M U S I Q U E S ************************************************************
		final Button musique = new Button(MUSIQUE_VOL + (int) (CSG.profile.musicVolume * 10), CSG.menuFontSmall, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, (CSG.screenWidth / 2) - Menu.SMALL_BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE3, new OnClick() {
			public void onClick() {
			}
		});
		add(musique);
		add(new Button(MOINS, CSG.menuFont, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, CSG.screenWidth / Menu.PADDING, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE3 + Menu.MINI_BOUTON_HEIGHT / 2, new OnClick() {
			public void onClick() {
				CSG.profile.diminuerVolumeMusique();
				musique.setTexte(MUSIQUE_VOL + (int) (CSG.profile.musicVolume * 10));
				SoundMan.playMusic();
				if (CSG.profile.musicVolume < 0.1f) {
					SoundMan.stopMusic();
				}
			}
		}));
		add(new Button(PLUS, CSG.menuFont, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, CSG.screenWidth - (CSG.screenWidth / Menu.PADDING) - Menu.MINI_BOUTON_WIDTH, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE3 + Menu.MINI_BOUTON_HEIGHT / 2, new OnClick() {
			public void onClick() {
				CSG.profile.augmenterVolumeMusique();
				musique.setTexte(MUSIQUE_VOL + (int) (CSG.profile.musicVolume * 10));
				SoundMan.playMusic();
			}
		}));

		// ****************************** B L O O M ************************************************************
		String bloomTxt = "BLOOM OFF";
		bloomTxt = "BLOOM ON";
		final Button bloom = new Button(bloomTxt, true, CSG.menuFontSmall, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, (CSG.screenWidth / 2) - Menu.SMALL_BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE4);
		add(bloom);

		add(new Button(MOINS, CSG.menuFont, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, CSG.screenWidth / Menu.PADDING, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE4 + Menu.MINI_BOUTON_HEIGHT / 2, new OnClick() {
			public void onClick() {
				CSG.profile.downBloom();
				majBloom();
				bloom.setTexte(INTENSITY + CSG.profile.getBloomString());
			}
		}));
		add(new Button(PLUS, CSG.menuFont, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, CSG.screenWidth - (CSG.screenWidth / Menu.PADDING) - Menu.MINI_BOUTON_WIDTH, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE4 + Menu.MINI_BOUTON_HEIGHT / 2, new OnClick() {
			public void onClick() {
				CSG.initBloom();
				CSG.profile.upBloom();
				majBloom();
				bloom.setTexte(INTENSITY + CSG.profile.getBloomString());
			}
		}));
		// ****************************** B O N U S ************************************************************
		String bonusTxt = "Automatic bonus";
		if (CSG.profile.manualBonus)
			bonusTxt = "Manual bonus";
		final Button bonus = new Button(bonusTxt, false, CSG.menuFont, BUTTON_WIDTH, BUTTON_HEIGHT, (CSG.screenWidth / 2) - Menu.BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE5);
		bonus.setClick(new OnClick() {
			@Override
			public void onClick() {
				CSG.profile.manualBonus = !CSG.profile.manualBonus;
				if (CSG.profile.manualBonus)
					bonus.setTexte("Manual bonus");
				else 
					bonus.setTexte("Automatic bonus");
			}
		});
		add(bonus);
		String screenshaketxt = "Screenshake : on";
		if (CSG.profile.screenshake == false)
			screenshaketxt = "Screenshake : off";
		final Button screenshake = new Button(screenshaketxt, false, CSG.menuFont, BUTTON_WIDTH, BUTTON_HEIGHT, (CSG.screenWidth / 2) - Menu.BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE6);
		screenshake.setClick(new OnClick() {
			@Override
			public void onClick() {
				CSG.profile.screenshake = !CSG.profile.screenshake;
				if (CSG.profile.screenshake)
					screenshake.setTexte("Screenshake : on");
				else 
					screenshake.setTexte("Screenshake : off");
			}
		});
		add(screenshake);
		
		// sensitivity
		
		final Button sensitivity = new Button(Strings.SENSITIVITY + CSG.profile.getSensitivityString(), CSG.menuFontSmall, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, (CSG.screenWidth / 2) - Menu.SMALL_BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE7, new OnClick() {
			public void onClick() {
			}
		});
		add(sensitivity);
		add(new Button(MOINS, CSG.menuFont, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, CSG.screenWidth / Menu.PADDING, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE7 + Menu.MINI_BOUTON_HEIGHT / 2, new OnClick() {
			public void onClick() {
				CSG.profile.downSensitivity();
				sensitivity.setTexte(Strings.SENSITIVITY + CSG.profile.getSensitivityString());
			}
		}));
		add(new Button(PLUS, CSG.menuFont, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, CSG.screenWidth - (CSG.screenWidth / Menu.PADDING) - Menu.MINI_BOUTON_WIDTH, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE7 + Menu.MINI_BOUTON_HEIGHT / 2, new OnClick() {
			public void onClick() {
				CSG.profile.upSensitivity();
				sensitivity.setTexte(Strings.SENSITIVITY + CSG.profile.getSensitivityString());
			}
		}));
	}

	private void majBloom() {
		CSG.bloom.setBloomIntesity(CSG.profile.bloomIntensity);
		CSG.profilManager.persist();
	}
}
