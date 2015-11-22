package elements.particular.bonuses;

import elements.particular.players.*;
import jeu.CSG;
import jeu.Physic;
import jeu.Stats;
import jeu.mode.EndlessMode;
import behind.SoundMan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import elements.generic.enemies.Enemy;

public abstract class Bonus {
	

	public static final Array<Bonus> LIST = new Array<Bonus>(40), TAKEN = new Array<Bonus>(16);
	public static final Array<XP> XP_LIST = new Array<XP>(40);
	public final Vector2 pos = new Vector2();
	public static final int WIDTH = (int) Stats.BONUS_WIDTH, HALF_WIDTH = (int) (Stats.BONUS_WIDTH / 2), WIDTH_MUL2 = WIDTH * 2, DISPLAY_WIDTH = (int) (WIDTH * 1.8f), HITBOX = HALF_WIDTH, HITBOX_TOP_RIGHT = (HITBOX * 2) + WIDTH;
	protected static final int TIME_FREQ = 9, 
			ADD_FREQ = (TIME_FREQ * 2)+1, 
			BOMB_FREQ = (int) ((TIME_FREQ * 5.9f)+26), 
			STOP_FREQ = (int) ((TIME_FREQ * 6.7f)+50), 
			SHIELD_FREQ = (int) ((TIME_FREQ * 3.8f)+1);
	protected static final float INCREASE_FREQ = 1.35f;
	protected static int cptBonus = 1, tmpXp;
	public static final int XPMINN = 10, XPMIN2 = XPMINN * 2;
	private static float tmp, tmpY;
	private static final float TRANSPARENCE = CSG.gm.palette().convertARGB(0.45f, .8f, 1, 1);
	private float timeTaken;
	
	/**
	 * Affiches tous les bonus, les fait tourner et test la collision avec le joueur
	 * @param batch
	 */
	public static void drawAndMove(SpriteBatch batch, Player player) {
		batch.setColor(TRANSPARENCE);
		for (final Bonus b : TAKEN) {
			b.timeTaken += EndlessMode.deltaU * b.timeTaken;
			tmp = b.timeTaken / 2;
			batch.draw(b.getTexture(), b.pos.x - tmp, b.pos.y - tmp, WIDTH + b.timeTaken, WIDTH + b.timeTaken);
			if (b.timeTaken > Stats.U5) {
				TAKEN.removeValue(b, true);
				b.free();
			}
		}
		XP.act(XP_LIST, batch);
		batch.setColor(CSG.gm.palette().white);
		for (final Bonus b : LIST) {
			b.drawMeMoveMe(batch);
			b.detectPris(player);
			if (Physic.isOnScreen(b.pos.x, b.pos.y, WIDTH) == false) {
				LIST.removeValue(b, true);
				b.free();
			}
		}
	}

	private void detectPris(Player player) {
		if (Physic.isShipInRect(pos.x - HITBOX, pos.y - HITBOX, HITBOX_TOP_RIGHT, HITBOX_TOP_RIGHT)) {	
			taken(player);
			LIST.removeValue(this, true);
		}
	}

	/**
	 * Test la collision, bouge et affiche
	 * @param batch
	 */
	abstract void drawMeMoveMe(SpriteBatch batch);

	/**
	 * Must also free the Bonus
	 */
	public void taken(Player player) {
		SoundMan.playBruitage(SoundMan.bonusTaken);
		timeTaken = 0.05f;
		TAKEN.add(this);
	}
	public abstract void free();
	
	public static void resetAll() {
		cptBonus = 1;
		for (final Bonus b : LIST)
			b.free();
		for (final Bonus b : TAKEN)
			b.free();
		for (final XP b : XP_LIST)
			b.free();
		Bonus.XP_LIST.clear();
		Bonus.TAKEN.clear();
        Bonus.LIST.clear();
        Drone.resetStats();
        TimeStop.resetStats();
        Bomb.resetStats();
        Shield.resetStats();
	}
	
	/**
	 * Might add a bonus. Always add XP
	 * @param x : The X center of the enemy
	 * @param y : The Y center
	 * @param xp
	 */
	public static void addBonus(Enemy e) {
		cptBonus += e.getBonusValue();
		tmp = e.pos.x + e.getDimensions().halfWidth;
		tmpY = e.pos.y + e.getDimensions().halfHeight;
		Drone.mightAppear(tmp, tmpY);
		TimeStop.mightAppear(tmp, tmpY);
		Bomb.mightAppear(tmp, tmpY);
		Shield.mightAppear(tmp, tmpY);
		
		addXp(e.getXp(), tmp, tmpY);
	}

	public static void addXp(int i, float tmp2, float tmpY2) {
		tmpXp = i;
		while (tmpXp > 0) {
			if (tmpXp > XPMINN) 	XP.POOL.obtain().init(tmp2, tmpY2, XPMINN);
			else					XP.POOL.obtain().init(tmp2, tmpY2, tmpXp);
			tmpXp -= XPMINN;
		}
	}

	public void init(float x, float y) {
		if (y > CSG.height)
			y = CSG.height;
		pos.x = x - HALF_WIDTH;
		pos.y = y - HALF_WIDTH;
		LIST.add(this);
	}
	protected abstract TextureRegion getTexture();
}