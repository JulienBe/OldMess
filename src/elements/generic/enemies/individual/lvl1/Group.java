package elements.generic.enemies.individual.lvl1;

import jeu.CSG;
import jeu.Stats;
import assets.AssetMan;
import assets.SoundMan;
import assets.sprites.Animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;

import elements.generic.components.Dimensions;
import elements.generic.components.behavior.Mover;
import elements.generic.components.shots.AbstractShot;
import elements.generic.components.shots.Gatling;
import elements.generic.enemies.Enemy;
import elements.generic.weapons.enemies.Tournante;

public class Group extends Enemy {
	
	protected static final int LVL = 1;
	protected static final Dimensions DIMENSIONS = Dimensions.GROUP;
	public static final Pool<Group> POOL = new Pool<Group>() {		protected Group newObject() {			return new Group();		}	};
	protected static final float FIRERATE = 0.6f, INIT_NEXT_SHOT = .5f, SPEED8 = getModulatedSpeed(8, LVL);
	private static final float COLOR = AssetMan.convertARGB(1, 1f, 0.5f, 0.2f);
	protected static final int BASE_XP = 12;
	private static final int HP = Stats.HP_CYLON, EXPLOSION = 35, XP = getXp(BASE_XP, LVL);
	private int shotNumber = 0;
	
	@Override
	protected void move() {
		Mover.coma(this, 1);
		angle = dir.angle() + 90;
	}
	
	@Override
	protected void setColor(SpriteBatch batch) {
		batch.setColor(COLOR);
	}
	
	@Override
	protected void removeColor(SpriteBatch batch) {
		batch.setColor(AssetMan.WHITE);
	}

	public static Group initAll() {
		Group e = POOL.obtain();
		Group f = POOL.obtain();
		Group g = POOL.obtain();
		e.init(CSG.gameZoneWidth - DIMENSIONS.width);
		f.init(CSG.gameZoneWidth - DIMENSIONS.width * 2.1f);
		g.init(CSG.gameZoneWidth - DIMENSIONS.width * 3.2f);
		return e;
	}
	
	protected void init(float x) {
		now = 5;
		LIST.add(this);
		pos.x = x;
		pos.y = CSG.SCREEN_HEIGHT;
		dir.x = 0;
		dir.y = -getSpeed();
	}

	@Override
	public void reset() {
		super.reset();
		pos.y = CSG.SCREEN_HEIGHT;
		nextShot = INIT_NEXT_SHOT;
		shotNumber = 0;
	}
	
	@Override
	protected void shoot() {
		TMP_POS.set(pos.x + DIMENSIONS.halfWidth - Tournante.DIMENSIONS.halfWidth, pos.y + DIMENSIONS.halfWidth - Tournante.DIMENSIONS.halfWidth);
		TMP_DIR.set(Stats.U12, dir.x + Stats.U12);
		
		AbstractShot.straight(Gatling.TOURNANTE, TMP_POS, TMP_DIR, -1);
		AbstractShot.rafale(this);
	}
	@Override	protected Sound getExplosionSound() {					return SoundMan.explosion6;		}
	@Override	public Animations getAnimation() {						return Animations.DIABOLO;		}
	@Override	public Dimensions getDimensions() {						return DIMENSIONS;				}
	@Override	public int getShotNumber() {							return shotNumber;				}
	@Override	public int getExplosionCount() {						return EXPLOSION;				}
	@Override	public void free() {									POOL.free(this);				}
	@Override	public void addShots(int i) {							shotNumber += i;				}
	@Override	public float getFirerate() {							return FIRERATE;				}
	@Override	public int getBonusValue() {							return BASE_XP;					}
	@Override	public float getSpeed() {								return SPEED8;					}
	@Override	protected int getMaxHp() {								return HP;						}
	@Override	public int getXp() {									return XP;						}
	@Override	public int getNumberOfShots() {							return 3;						}
}
