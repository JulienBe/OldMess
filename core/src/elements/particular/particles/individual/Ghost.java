package elements.particular.particles.individual;

import assets.sprites.AnimPlayer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.particular.players.Player;
import jeu.CSG;

public class Ghost implements Poolable {

	private float x, y, alpha;
	private Player player;
	private int numero;
	public static final Pool<Ghost> POOL = new Pool<Ghost>() {
		@Override
		protected Ghost newObject() {
			return new Ghost();
		}
	};

	public static void act(Array<Ghost> ghosts, SpriteBatch batch) {
		for (Ghost ghost : ghosts) {
			batch.setColor(1, 1, 1, ghost.alpha);
			batch.draw(AnimPlayer.TEXTURES[ghost.numero], ghost.x, ghost.y, ghost.player.width, ghost.player.height);
			ghost.alpha -= 0.048f;
			if (ghost.alpha <= 0.1f) {
				ghosts.removeValue(ghost, true);
				POOL.free(ghost);
			}
		}
		batch.setColor(CSG.gm.palette().white);
	}

	@Override
	public void reset() {
	}

	public void init(int etat, Player player) {
		x = Player.POS.x;
		y = Player.POS.y;
		alpha = 1;
		numero = etat;
    this.player = player;
	}

	public static void clear(Array<Ghost> ghosts) {
		POOL.freeAll(ghosts);
		ghosts.clear();
	}
}
