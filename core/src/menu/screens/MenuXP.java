package menu.screens;

import elements.particular.players.Pfire1;
import menu.JeuBackground;
import menu.tuto.OnClick;
import menu.ui.Button;
import jeu.CSG;
import jeu.Profil;
import jeu.Strings;
import jeu.mode.EndlessMode;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import elements.particular.players.Player;
import elements.particular.particles.Particles;

class MenuXP extends AbstractScreen{
	
	private Button boutonUpgrade, boutonCadence, boutonUndo;
	private final Button boutonXP;
	private final JeuBackground jeu = new JeuBackground();
	private static int prevNvArmeDeBase = CSG.profile.lvlFireball, prevNvArmeBalayage = CSG.profile.lvlSweepWeapon, prevNvArmeHantee = CSG.profile.lvlTWeapon, prevNvArmeTrois = CSG.profile.lvlPinkWeapon,
			prevNvArmeSun = CSG.profile.lvlSunWeapon, prevVitesse = CSG.profile.dronesFirerate, prevXp = CSG.profile.xp;

	MenuXP(final Game game) {
		super(game);
		Gdx.input.setCatchBackKey(true);
		add(buttonBack);
		// ** B O U T O N X P **
		boutonXP = new Button(CSG.profile.xp + "xp", CSG.menuFont, (CSG.screenWidth / 2) - Menu.BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * 2, BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, new OnClick() {
			public void onClick() {			}
		});
		add(boutonXP);
		// ** ** ** BUTTON WEAPON ** ** **
		add(new Button(Strings.BUTTON_OTHER_WEAP, CSG.menuFont, (CSG.screenWidth / 2) - Menu.BUTTON_WIDTH / 2, -Menu.yOffset + CSG.height - Menu.BUTTON_HEIGHT * 4, BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, new OnClick() {
			public void onClick() {
				Player.changeWeapon();
				CSG.profilManager.persist();
				updateTxtUpgrade();
			}
		}));
		// ** ** ** BUTTON UPGRADE ** **
		boutonUpgrade = new Button("", CSG.menuFontSmall, CSG.screenWidth - (CSG.screenWidth / Menu.PADDING) - Menu.SMALL_BUTTON_WIDTH, Menu.BUTTON_HEIGHT, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, new OnClick() {
			public void onClick() {
				if (CSG.profile.getCoutUpArme() <= CSG.profile.xp) {
					save();
					CSG.profile.upArme();
					afterUpdate();
				} else 
					CSG.talkToTheWorld.buyXp();
			}
		});
		add(boutonUpgrade);
		updateTxtUpgrade();
		// ** ** ** BUTTON FIRERATE ** ** **
		boutonCadence = new Button("", CSG.menuFontSmall, CSG.screenWidth - (CSG.screenWidth / Menu.PADDING) - Menu.SMALL_BUTTON_WIDTH, Menu.BUTTON_HEIGHT * 3, Menu.SMALL_BUTTON_WIDTH, Menu.SMALL_BUTTON_HEIGHT, new OnClick() {
			public void onClick() {
				if (CSG.profile.getCoutCadenceAdd() <= CSG.profile.xp) {
					save();
					CSG.profile.upCadenceAdd();
					afterUpdate();
				} else
					CSG.talkToTheWorld.buyXp();
			}
		});
		updateTxtCadence();
		add(boutonCadence);
		Player player = new Pfire1();
		Player.POS.set((CSG.screenWidth / 2) - player.halfWidth, CSG.height / 3);
		setRenderBackground(false);
	}

	@Override
	public void render(float delta) {
		CSG.begin(delta);
		if (Gdx.input.isKeyPressed(Keys.BACK))		keyBackPressed();
		CSG.batch.begin();
		Particles.background(CSG.batch);
		jeu.render(CSG.batch, delta);
		for (int i = 0; i < buttons.size; i++) 
			if (buttons.get(i) != null)
				buttons.get(i).draw(CSG.batch);
		CSG.menuFontSmall.draw(CSG.batch, "Weapon level : " + CSG.profile.getArmeSelectionnee().nv(), 4, 4 + CSG.fontsDimensions.getHeight(CSG.menuFontSmall, "W"));
		CSG.end();
		EndlessMode.majDeltas(true);
		EndlessMode.alternate = !EndlessMode.alternate;
	}
	
	@Override
	public void keyBackPressed() {
		changeMenu(new Menu(game));
		CSG.profilManager.persist();
	}
	
	private void addUndo() {
		if (boutonUndo == null) {
			CSG.talkToTheWorld.unlockAchievementGPGS(Strings.ACH_FAVORITE_SHOP);
			boutonUndo = new Button("UNDO", CSG.menuFontSmall, CSG.screenWidth / Menu.PADDING, Menu.BUTTON_HEIGHT * 3, Menu.SMALL_BUTTON_WIDTH, Menu.SMALL_BUTTON_HEIGHT, new OnClick()  {
				public void onClick() {		undo();		}
			});
			add(boutonUndo);
		} else 
			add(boutonUndo);
	}

	private void updateTxtCadence() {
		boutonCadence.setTexte("Drones (" + CSG.profile.getCoutCadenceAdd() + ")");
	}

	private void updateTxtXp() {
		boutonXP.setTexte(CSG.profile.xp + "xp");
	}
	
	private void afterUpdate() {
		CSG.profilManager.persist();
		updateTxtUpgrade();
		updateTxtXp();
		addUndo();
	}

	private void save() {
		prevXp = CSG.profile.xp;
		prevNvArmeSun = CSG.profile.lvlSunWeapon;
		prevVitesse = CSG.profile.dronesFirerate;
		prevNvArmeHantee = CSG.profile.lvlTWeapon;
		prevNvArmeDeBase = CSG.profile.lvlFireball;
		prevNvArmeTrois = CSG.profile.lvlPinkWeapon;
		prevNvArmeBalayage = CSG.profile.lvlSweepWeapon;
		updateTxtUpgrade();
		updateTxtCadence();
		updateTxtXp();
	}

	private void undo() {
		CSG.profile.lvlSweepWeapon = prevNvArmeBalayage;
		CSG.profile.lvlPinkWeapon = prevNvArmeTrois;
		CSG.profile.lvlFireball = prevNvArmeDeBase;
		CSG.profile.lvlTWeapon = prevNvArmeHantee;
		CSG.profile.lvlSunWeapon = prevNvArmeSun;
		CSG.profile.dronesFirerate = prevVitesse;
		CSG.profile.xp = prevXp;
		updateTxtUpgrade();
		updateTxtCadence();
		updateTxtXp();
		buttons.removeValue(boutonUndo, true);
	}

	private void updateTxtUpgrade() {
		if (CSG.profile.getArmeSelectionnee().nv() >= Profil.LVL_MAX)		boutonUpgrade.setTexte("LEVEL MAX");
		else 																boutonUpgrade.setTexte("Weapon (" + CSG.profile.getCoutUpArme() + ")");
	}

}