package jeu.mode.extensions;

import jeu.CSG;
import jeu.mode.EndlessMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import elements.generic.enemies.Enemy;
import elements.particular.players.Player;
import elements.particular.bonuses.Bonus;
import elements.particular.bonuses.Shield;
import elements.particular.bonuses.TimeStop;
import elements.particular.bonuses.XP;

public class DesktopTests {

	public static void debug() {
		if (EndlessMode.oneToFour != 2)
			return;
		
		if (Gdx.input.isKeyPressed(Keys.A))
		if (Gdx.input.isKeyPressed(Keys.B))		Shield.POOL.obtain().init(CSG.R.nextFloat() * CSG.screenWidth, 200);
		
		
		if (Gdx.input.isKeyPressed(Keys.F1))	EndlessMode.invicibility = true;
		if (Gdx.input.isKeyPressed(Keys.F2))	EndlessMode.invicibility = false;
		if (Gdx.input.isKeyPressed(Keys.F3))	EndlessMode.freeze = true;
		if (Gdx.input.isKeyPressed(Keys.F4))	EndlessMode.freeze = false;
		if (Gdx.input.isKeyPressed(Keys.F5))	Player.nextShot += 150;
		if (Gdx.input.isKeyPressed(Keys.F6))	Player.nextShot -= 150;
		if (Gdx.input.isKeyPressed(Keys.F7))	Enemy.attackAllEnemies(Enemy.bomb);
		if (Gdx.input.isKeyPressed(Keys.F8)) {
			EndlessMode.ajoutBombe();
		}
		if (Gdx.input.isKeyPressed(Keys.F11))	EndlessMode.invoque = true;
		if (Gdx.input.isKeyPressed(Keys.F12))	EndlessMode.invoque = false;
		if (Gdx.input.isKeyPressed(Keys.F3))	Bonus.LIST.add(TimeStop.POOL.obtain());
		if (Gdx.input.isKeyPressed(Keys.F4)) {
			Bonus.LIST.add(XP.POOL.obtain());
			CSG.profile.bfg = true;
		}
		if (Gdx.input.isKeyPressed(Keys.F5)) Score.score++;
	}

}
