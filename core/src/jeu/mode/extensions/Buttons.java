package jeu.mode.extensions;

import jeu.CSG;
import jeu.Physic;
import jeu.Profil;
import jeu.Strings;
import jeu.mode.EndlessMode;
import menu.screens.Menu;
import menu.tuto.OnClick;
import menu.ui.Button;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import elements.generic.enemies.Enemy;
import elements.particular.players.Player;

public class Buttons {

	private static Button boutonBack = null, boutonUpgrade = null, boutonTwitter = null, boutonRestart = null;
	
	public static void init() {
		boutonUpgrade = null;
		boutonTwitter = null;
		boutonRestart = null;
		boutonBack = null;
	}
	
	public static void backButton(SpriteBatch batch, final Game game) {
		if (boutonBack == null) {
			boutonBack = new Button(Strings.BACK, CSG.menuFont, 
					(int) CSG.fontsDimensions.getWidth(CSG.menuFont, Strings.BACK),
					(int) CSG.fontsDimensions.getHeight(CSG.menuFont, Strings.BACK),
					(int) ((EndlessMode.cam.position.x) - CSG.fontsDimensions.getWidth(CSG.menuFont, Strings.BACK) / 2),
					Menu.BUTTON_HEIGHT * 3,
					new OnClick() {
					public void onClick() {
						goToMenu(game);
						boutonBack = null;
					}
			});
		} else {
			boutonBack.draw(batch);
			if (Gdx.input.justTouched() && Physic.isPointInRect(Gdx.input.getX() + EndlessMode.cam.position.x / 2, CSG.height - Gdx.input.getY(), 0, Menu.BUTTON_HEIGHT * 1.1f, CSG.screenWidth, Menu.BUTTON_HEIGHT * 3)) {
				goToMenu(game);
				boutonBack = null;
			}
		}
	}
	
	public static void goToMenu(Game game) {
		CSG.profilManager.persist();
		game.setScreen(new Menu(game));
		Enemy.clear();
		if (CSG.profile.xp >= 30000)
			CSG.talkToTheWorld.unlockAchievementGPGS(Strings.ACH_30k_XP);
	}

	public static void initAndDrawButtons(SpriteBatch batch) {
		if (boutonRestart == null) {
			boutonRestart = new Button(Strings.RESTART_BUTTON, CSG.menuFont, 
					(int) CSG.fontsDimensions.getWidth(CSG.menuFont, Strings.BACK),
					(int) CSG.fontsDimensions.getHeight(CSG.menuFont, Strings.BACK),
					(int) ((EndlessMode.cam.position.x) - CSG.fontsDimensions.getWidth(CSG.menuFont, Strings.BACK) / 2),
					Menu.BUTTON_HEIGHT,
					new OnClick() {
					public void onClick() {
						EndlessMode.init();
					}
			});
		} else {
			boutonRestart.draw(batch);
		}
		if (Player.weapon.nv() < Profil.LVL_MAX) {
			if (boutonUpgrade == null) {
				if (CSG.profile.getCoutUpArme() <= CSG.profile.xp) {
					boutonUpgrade = new Button(Strings.UPGRADE_BUTTON, CSG.menuFont, 
							(int) CSG.fontsDimensions.getWidth(CSG.menuFont, Strings.UPGRADE_BUTTON),
							(int) CSG.fontsDimensions.getHeight(CSG.menuFont, Strings.UPGRADE_BUTTON),
							(int) ((EndlessMode.cam.position.x) - CSG.fontsDimensions.getWidth(CSG.menuFont, Strings.UPGRADE_BUTTON) / 2),
							(int) (Menu.BUTTON_HEIGHT * 3f),
							new OnClick() {
						public void onClick() {
							if(CSG.profile.getCoutUpArme() <= CSG.profile.xp) {
								CSG.profile.upArme();
								CSG.profilManager.persist();
								EndlessMode.init();
							} else {
								CSG.talkToTheWorld.buyXp();
							}
						}
					});
				} else {
					boutonUpgrade = new Button(Strings.BUY_XP_BUTTON, CSG.menuFont, 
							(int) CSG.fontsDimensions.getWidth(CSG.menuFont, Strings.BUY_XP_BUTTON),
							(int) CSG.fontsDimensions.getHeight(CSG.menuFont, Strings.BUY_XP_BUTTON),
							(int) ((EndlessMode.cam.position.x) - CSG.fontsDimensions.getWidth(CSG.menuFont, Strings.BUY_XP_BUTTON) / 2),
							(int) (Menu.BUTTON_HEIGHT * 3f),
							new OnClick() {
						public void onClick() {
							if(CSG.profile.getCoutUpArme() <= CSG.profile.xp) {
								CSG.profile.upArme();
								CSG.profilManager.persist();
								init();
							} else {
								CSG.talkToTheWorld.buyXp();
							}
						}
					});
				}
			} else {
				boutonUpgrade.draw(batch);
			}
		} 
		if (Score.score > 50000) {
			if (boutonTwitter == null) {
				boutonTwitter = new Button(Strings.BRAG_TWITTER, CSG.menuFont, 
						(int) CSG.fontsDimensions.getWidth(CSG.menuFont, Strings.BRAG_TWITTER),
						(int) CSG.fontsDimensions.getHeight(CSG.menuFont, Strings.BRAG_TWITTER),
						(int) ((EndlessMode.cam.position.x) - CSG.fontsDimensions.getWidth(CSG.menuFont, Strings.BRAG_TWITTER) / 2),
						Menu.BUTTON_HEIGHT * 5,
						new OnClick() {
						public void onClick() {
							CSG.talkToTheWorld.bragTwitter("I made " + (int)Score.score + " on #MESS" + EndlessMode.difficulty + " #androidgames");
						}
				});
			} else {
				boutonTwitter.draw(batch);
			}
		}
	}

	public static void drawUpgradeAndTwitter(SpriteBatch batch) {
		if (boutonUpgrade != null) {
			boutonUpgrade.draw(batch);
			if (boutonTwitter != null)
				boutonTwitter.draw(batch);
		}
	}

	public static void testClick() {
		if (Gdx.input.justTouched()) {
			Button.testClick(boutonUpgrade, EndlessMode.cam.position.x / 2);
			Button.testClick(boutonRestart, EndlessMode.cam.position.x / 2);
			Button.testClick(boutonTwitter, EndlessMode.cam.position.x / 2);
		}
	}

	public static void removeBack() {
		boutonBack = null;
	}
	
}
