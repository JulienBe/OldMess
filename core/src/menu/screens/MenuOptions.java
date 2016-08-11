package menu.screens;

import jeu.CSG;
import jeu.Strings;
import menu.tuto.OnClick;
import menu.ui.Button;
import behind.SoundMan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

class MenuOptions extends AbstractScreen {

	private static final int LINE2 = 4;
	private static final int LINE3 = 6;
	private static final int LINE4 = 8;
	private static final int LINE5 = 14;
	private static final int LINE6 = 12;
	private static final int LINE7 = 10;

	MenuOptions(final Game game) {
		super(game);
		
		Gdx.input.setCatchBackKey(true);
		add(buttonBack);
		// ************************ A R M E S ****************************************************************

		// ************************ B R U I T A G E S ********************************************************
		final Button bruit = new Button(Strings.BUTTON_SFX + (int) (CSG.profile.effectsVolume * 10), CSG.menuFontSmall, (CSG.screenWidth / 2) - Menu.SMALL_BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE2, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, new OnClick() {
			public void onClick() {}
		});
		add(bruit);
		add(new Button(Strings.BUTTON_MINUS, CSG.menuFont, CSG.screenWidth / Menu.PADDING, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE2 + Menu.MINI_BOUTON_HEIGHT / 2, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, new OnClick() {
			public void onClick() {
				CSG.profile.diminuerVolumeBruitage();
				bruit.setTexte(Strings.BUTTON_SFX + (int) (CSG.profile.effectsVolume * 10));
				SoundMan.playBruitage(SoundMan.shotRocket);
			}
		}));
		add(new Button(Strings.BUTTON_PLUS, CSG.menuFont, CSG.screenWidth - (CSG.screenWidth / Menu.PADDING) - Menu.MINI_BOUTON_WIDTH, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE2 + Menu.MINI_BOUTON_HEIGHT / 2, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, new OnClick() {
			public void onClick() {
				CSG.profile.augmenterVolumeBruitage();
				bruit.setTexte(Strings.BUTTON_SFX + (int) (CSG.profile.effectsVolume * 10));
				SoundMan.playBruitage(SoundMan.shotRocket);
			}
		}));
		// ************************ M U S I Q U E S ************************************************************
		final Button music = new Button(Strings.BUTTON_MUSIC + (int) (CSG.profile.musicVolume * 10), CSG.menuFontSmall, (CSG.screenWidth / 2) - Menu.SMALL_BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE3, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, new OnClick() {
			public void onClick() {
			}
		});
		add(music);
		add(new Button(Strings.BUTTON_MINUS, CSG.menuFont, CSG.screenWidth / Menu.PADDING, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE3 + Menu.MINI_BOUTON_HEIGHT / 2, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, new OnClick() {
			public void onClick() {
				CSG.profile.diminuerVolumeMusique();
				music.setTexte(Strings.BUTTON_MUSIC + (int) (CSG.profile.musicVolume * 10));
				SoundMan.playMusic();
				if (CSG.profile.musicVolume < 0.1f) {
					SoundMan.stopMusic();
				}
			}
		}));
		add(new Button(Strings.BUTTON_PLUS, CSG.menuFont, CSG.screenWidth - (CSG.screenWidth / Menu.PADDING) - Menu.MINI_BOUTON_WIDTH, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE3 + Menu.MINI_BOUTON_HEIGHT / 2, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, new OnClick() {
			public void onClick() {
				CSG.profile.augmenterVolumeMusique();
				music.setTexte(Strings.BUTTON_MUSIC + (int) (CSG.profile.musicVolume * 10));
				SoundMan.playMusic();
			}
		}));

		// ****************************** B L O O M ************************************************************
		String bloomTxt;
		bloomTxt = "BLOOM ON";
		final Button bloom = new Button(bloomTxt, CSG.menuFontSmall, (CSG.screenWidth / 2) - Menu.SMALL_BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE4, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);
		add(bloom);

		add(new Button(Strings.BUTTON_MINUS, CSG.menuFont, CSG.screenWidth / Menu.PADDING, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE4 + Menu.MINI_BOUTON_HEIGHT / 2, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, new OnClick() {
			public void onClick() {
				CSG.profile.downBloom();
				majBloom();
				bloom.setTexte(Strings.BUTTON_INTENSITY + CSG.profile.getBloomString());
			}
		}));
		add(new Button(Strings.BUTTON_PLUS, CSG.menuFont, CSG.screenWidth - (CSG.screenWidth / Menu.PADDING) - Menu.MINI_BOUTON_WIDTH, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE4 + Menu.MINI_BOUTON_HEIGHT / 2, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, new OnClick() {
			public void onClick() {
				CSG.initBloom();
				CSG.profile.upBloom();
				majBloom();
				bloom.setTexte(Strings.BUTTON_INTENSITY + CSG.profile.getBloomString());
			}
		}));
		// ****************************** B O N U S ************************************************************
		String bonusTxt = "Automatic bonus";
		if (CSG.profile.manualBonus)
			bonusTxt = "Manual bonus";
		final Button bonus = new Button(bonusTxt, CSG.menuFont, (CSG.screenWidth / 2) - Menu.BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE5, BUTTON_WIDTH, BUTTON_HEIGHT);
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
		if (!CSG.profile.screenshake)
			screenshaketxt = "Screenshake : off";
		final Button screenshake = new Button(screenshaketxt, CSG.menuFont, (CSG.screenWidth / 2) - Menu.BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE6, BUTTON_WIDTH, BUTTON_HEIGHT);
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
		
		final Button sensitivity = new Button(Strings.SENSITIVITY + CSG.profile.getSensitivityString(), CSG.menuFontSmall, (CSG.screenWidth / 2) - Menu.SMALL_BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE7, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, new OnClick() {
			public void onClick() {
			}
		});
		add(sensitivity);
		add(new Button(Strings.BUTTON_MINUS, CSG.menuFont, CSG.screenWidth / Menu.PADDING, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE7 + Menu.MINI_BOUTON_HEIGHT / 2, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, new OnClick() {
			public void onClick() {
				CSG.profile.downSensitivity();
				sensitivity.setTexte(Strings.SENSITIVITY + CSG.profile.getSensitivityString());
			}
		}));
		add(new Button(Strings.BUTTON_PLUS, CSG.menuFont, CSG.screenWidth - (CSG.screenWidth / Menu.PADDING) - Menu.MINI_BOUTON_WIDTH, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * LINE7 + Menu.MINI_BOUTON_HEIGHT / 2, MINI_BOUTON_WIDTH, MINI_BOUTON_HEIGHT, new OnClick() {
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
