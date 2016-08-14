package elements.particular.bonuses;

import jeu.CSG;
import jeu.Physic;
import jeu.Stats;
import jeu.mode.EndlessMode;
import jeu.mode.extensions.Score;
import assets.AssetMan;
import behind.SoundMan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public class XP extends Bonus implements Poolable {

	public int valeur = 2;
	public static final Pool<XP> POOL = new Pool<XP>(30) {
		@Override
		protected XP newObject() {
			return new XP();
		}
	};
	public static final int WIDTH = (int) ((int) Stats.BONUS_WIDTH / 3f), HALF = WIDTH/2, WIDTH_INF = (int) (WIDTH + Stats.u), HALF_INF_WIDTH = WIDTH_INF / 2, WIDTH2 = WIDTH * 2;
	public final Vector2 direction = new Vector2();
	private float color, angle, inflate;
	private static final int WANDERER = 1;
	public static final int HOMMING = 2;
	public static final int INFLATE = 3;
	private static final int SHOOTING_STAR = 4;
	private static final float LIMIT_RIGHT = CSG.screenWidth - WIDTH, LIMIT_LEFT = 0, LIMIT_UP = CSG.height - WIDTH;
	public int state;
	
	public void init(float x, float y, int xp) {
		x -= HALF;
		y -= HALF;
		x += CSG.R.nextFloat() * Stats.U3;
		y += CSG.R.nextFloat() * Stats.U3;
		valeur = xp;
		pos.x = x;
		pos.y = y;
		limites();
		color = getColor(xp);
		state = WANDERER;
		inflate = 0;
		Bonus.XP_LIST.add(this);
//		Score.upScore(xp);
		angle = CSG.R.nextFloat() * 360;
	}
	
	private float getColor(float xp) {
		return CSG.gm.palette().convertARGB(1, 0.2f, 0.5f + (xp / XPMINN), 1);
	}

	private void limites() {
		if (pos.x < LIMIT_LEFT)
			pos.x = LIMIT_LEFT;
		else if (pos.x > LIMIT_RIGHT)
			pos.x = LIMIT_RIGHT;
		
		if (pos.y > LIMIT_UP) 
			pos.y = LIMIT_UP;
		
	}

	@Override
	public void reset() {									}
	@Override
	public void free() {							POOL.free(this);	}

	private static final Vector2 tmp = new Vector2();
	private static float tmpFloat;
	public static void act(Array<XP> xpList, SpriteBatch batch) {
		for (XP xp : xpList) {
			if (EndlessMode.alternate) {
				switch (xp.state) {
				case WANDERER:
					analyzeDistance(xp, HOMMING, Stats.U10);
					drawStandard(batch, xp, xp.angle);
					xp.angle++;
					break;
				case HOMMING:
					xp.angle += xp.valeur;
//					Physic.mvtToPlayer(xp.direction, xp.pos, Stats.U20 + xp.valeur * 2, WIDTH, HALF);
//					Physic.mvtToPlayer(xp.direction, xp.pos, Stats.U20 + xp.valeur * 2, (int)xp.height, (int)xp.height / 2);
					Physic.mvtToPlayer(xp.direction, xp.pos, Stats.U20 + xp.valeur * 2, WIDTH, HALF, 1);
					analyzeDistance(xp, INFLATE, Stats.U10);
					drawStandard(batch, xp, xp.angle);
					break;
				case INFLATE:
					xp.angle += xp.valeur;
					xp.inflate += EndlessMode.delta * Stats.U20;
					tmpFloat = xp.inflate / 2;
					drawMultipleTimes(batch, xp, xp.angle);
					if (xp.inflate >= WIDTH_INF) {
//					if (xp.inflate >= xp.height * 2) {
						xp.color = CSG.gm.palette().setAlpha(xp.color, 0.3f);
						xp.state = SHOOTING_STAR;
						xp.direction.x = CSG.halfWidth - xp.pos.x;
						xp.direction.y = -xp.pos.y;
						xp.direction.nor();
						xp.direction.scl(Stats.U12);
					}
					break;
				case SHOOTING_STAR:
					drawStandard(batch, xp, xp.angle);
					break;
				}
			} else {
				switch (xp.state) {
				case WANDERER:
					xp.pos.y -= EndlessMode.delta15;
					drawStandard(batch, xp, xp.angle);
					break;
				case HOMMING:
					drawStandard(batch, xp, xp.angle);
					break;
				case INFLATE:
					tmpFloat = xp.inflate / 2;
					drawMultipleTimes(batch, xp, xp.angle);
					break;
				case SHOOTING_STAR:
					drawAndMove(batch, xp, -xp.angle);
					if (Math.abs(CSG.halfWidth - xp.pos.x) < Stats.U && xp.pos.y < CSG.heightDiv20) {
						taken(xp);
					}
					break;
				}
			}
//			if (xp.pos.y + WIDTH_INF < 0) {
//				XP_LIST.removeValue(xp, false);
//				POOL.free(xp);
//			}
		}
		batch.setColor(CSG.gm.palette().white);
	}

	private static void taken(XP xp) {
		SoundMan.playBruitage(SoundMan.xp);
		Score.upScore(xp.valeur);
		XP_LIST.removeValue(xp, false);
		POOL.free(xp);
	}

	private static void drawAndMove(SpriteBatch batch, XP xp, float angle) {
		batch.setColor(xp.color);
		batch.draw(AssetMan.xp, xp.pos.x, xp.pos.y, HALF_INF_WIDTH, HALF_INF_WIDTH, WIDTH_INF, WIDTH_INF, 1, 1, angle);
//		batch.draw(AssetMan.xp, xp.pos.x, xp.pos.y, xp.height, xp.height);
//		batch.draw(AssetMan.xp, xp.pos.x, xp.pos.y, WIDTH_INF, WIDTH_INF);
		move(xp);
	}

	private static void drawMultipleTimes(SpriteBatch batch, XP xp, float angle) {
		batch.setColor(xp.color);
//		batch.draw(AssetMan.xp, xp.pos.x, xp.pos.y, WIDTH, WIDTH);
//		batch.draw(AssetMan.xp, xp.pos.x, xp.pos.y, xp.height, xp.height);
		batch.draw(AssetMan.xp, xp.pos.x - tmpFloat, xp.pos.y - tmpFloat, HALF + tmpFloat, HALF + tmpFloat, WIDTH + xp.inflate, WIDTH + xp.inflate, 1, 1, angle);
//		batch.draw(AssetMan.xp, xp.pos.x - tmpFloat, xp.pos.y - tmpFloat, HALF + tmpFloat, HALF + tmpFloat, WIDTH + xp.inflate, WIDTH + xp.inflate, 1, 1, -angle);
	}

	private static void drawStandard(SpriteBatch batch, XP xp, float angle) {
		batch.setColor(xp.color);
		batch.draw(AssetMan.xp, xp.pos.x, xp.pos.y, HALF, HALF, WIDTH, WIDTH, 1, 1, angle);
//		batch.draw(AssetMan.xp, xp.pos.x, xp.pos.y, xp.height, xp.height);
//		batch.draw(AssetMan.xp, xp.pos.x, xp.pos.y, WIDTH, WIDTH);
	}

	private static void move(XP xp) {
		xp.pos.x += xp.direction.x * EndlessMode.delta15;
		xp.pos.y += xp.direction.y * EndlessMode.delta15;
	}

	private static void analyzeDistance(XP xp, int nextState, float detectionRange) {
		tmp.x = xp.pos.x + HALF_WIDTH;
		tmp.y = xp.pos.y + HALF_WIDTH;
		if (Physic.distanceFromPlayer(tmp) < detectionRange)
			xp.state = nextState;
	}

	@Override
	void drawMeMoveMe(SpriteBatch batch) {
	}

	@Override
	protected TextureRegion getTexture() {
		return AssetMan.xp;
	}

//	public void init(float x, float y, int valeur, float height, float height) {
//		this.pos.x = x;
//		this.pos.y = y;
//		this.valeur = 10;
//		Bonus.XP_LIST.add(this);
//		angle = 0;
//		color = getColor(valeur);
//		state = WANDERER;
//		inflate = 0;
//	}

}
