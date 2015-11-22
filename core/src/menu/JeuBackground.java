package menu;

import elements.particular.players.Pfire1;
import jeu.CSG;
import jeu.Physic;
import jeu.mode.EndlessMode;
import assets.sprites.AnimPlayer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import elements.generic.weapons.Weapon;
import elements.particular.players.Player;
import elements.particular.particles.Particles;

public class JeuBackground {
	
	private Player player = new Pfire1();
	private boolean alterner = true;

	public void reset() {
		CSG.reset();
	}

	public void render(SpriteBatch batch, float delta) {
		player.draw(batch);
		Weapon.drawAndMove(batch, player);
		Particles.draw(batch);		
		player.updateCenters();

		if (alterner) {
			Physic.collisionsTest(player);
			player.shot();
		}
		if (Player.POS.y > player.height) {
			Player.POS.y -= EndlessMode.delta25 * 6;
			AnimPlayer.state = 2;
		} else
			Player.POS.y = player.height;
		
		alterner = !alterner;
	}

	public Player getPlayer() {
		return player;
	}
}

