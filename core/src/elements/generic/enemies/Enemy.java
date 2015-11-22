package elements.generic.enemies;

import jeu.CSG;
import jeu.Physic;
import jeu.Stats;
import jeu.Strings;
import jeu.mode.EndlessMode;
import jeu.mode.extensions.ScreenShake;
import jeu.mode.extensions.Transition;
import assets.AssetMan;
import behind.SoundMan;
import assets.sprites.Animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

import elements.generic.Element;
import elements.generic.components.Dimensions;
import elements.generic.components.EnemyStats;
import elements.generic.weapons.Weapon;
import elements.generic.weapons.enemies.EnemyWeapon;
import elements.generic.weapons.player.PlayerWeapon;
import elements.particular.players.Player;
import elements.particular.bonuses.Bonus;
import elements.particular.other.WaveEffect;
import elements.particular.particles.Particles;
import elements.particular.particles.individual.PrecalculatedParticles;

public abstract class Enemy extends Element implements Poolable {

  public final static Array<Enemy> LIST = new Array<Enemy>(40);
  protected static final Vector2 TMP_POS = new Vector2(), TMP_DIR = new Vector2();
  public static final int RED = 0, BLUE = 1, GREEN = 2;
  protected static final float ALERT_WIDTH = 10, ALERT_HALF_WIDTH = ALERT_WIDTH / 2, DETECT_RANGE = Stats.WIDTH_DIV_10, MOD_FIRERATE = 1.2f;
  protected static final Rectangle COLLISION = new Rectangle();
  protected static float tmpDeltaMulImpact;
  public boolean dead = false;
  public float nextShot = 1;
  protected int hp;

  public void added(float x, float y) {
    pos.x = x;
    pos.y = y;
    reset();
    LIST.add(this);
  }

  protected int getMaxHp() {
    return getEnemyStats().getHp();
  }

  public static void affichageEtMouvement(SpriteBatch batch) {
    for (final Enemy e : LIST) {
      if (e.dead)
        continue;
      e.now += EndlessMode.delta;
      e.displayWarning(batch);
      e.displayOnScreen(batch);
      e.mightShoot();
      e.move();
      if (!Physic.isOnScreenWithTolerance(e.pos, e.getDimensions().halfHeight, e.getDimensions().halfWidth))
        e.dead = true;
      // collision
//			batch.draw(AssetMan.red, e.getRectangleCollision().x, e.getRectangleCollision().y, e.getRectangleCollision().width, e.getRectangleCollision().height);
    }
  }

  private void mightShoot() {
    if (now > nextShot) {
      nextShot = now + getFirerate();
      shoot();
    }
  }

  private void displayWarning(SpriteBatch batch) {
    if (Physic.isNotDisplayed(this)) {
      if ((pos.x + getDimensions().width) < 0 && (pos.x + getDimensions().width) > -DETECT_RANGE) {
        // left
        batch.setColor(1, 0, 0.5f, 1);
        batch.draw(AssetMan.dust, -ALERT_HALF_WIDTH, pos.y - Stats.U3, ALERT_WIDTH, (DETECT_RANGE + (pos.x + getDimensions().width)) + Stats.U6);
        batch.setColor(CSG.gm.palette().white);
      } else if (pos.x > CSG.screenWidth && pos.x < Stats.WIDTH_PLUS_MARGIN) {
        // right
        batch.setColor(1, 0, 0.5f, 1);
        batch.draw(AssetMan.dust, CSG.screenWidth - ALERT_HALF_WIDTH, pos.y - Stats.U3, ALERT_WIDTH, (DETECT_RANGE + (CSG.halfWidth - pos.x)) + Stats.U6);
        batch.setColor(CSG.gm.palette().white);
      }
    }
  }

  public static float f = 0;

  public static void draw(SpriteBatch batch) {
    for (final Enemy e : LIST)
      e.displayOnScreen(batch);
  }

  public boolean stillAlive(PlayerWeapon a) {
    switch (getColor()) {
      case GREEN:
        Particles.enemyHit(this, PrecalculatedParticles.RANDDOM_GREENS);
        break;
      case BLUE:
        Particles.enemyHit(this, PrecalculatedParticles.RANDDOM_BLUES);
        break;
      default:
        Particles.enemyHit(this, PrecalculatedParticles.RANDDOM_REDS);
        break;
    }
    Particles.addPartEnemyTouched(a, this);
    tmpDeltaMulImpact = EndlessMode.delta * Stats.IMPACT;
    hp -= a.getPower();
    if (hp <= 0) {
      die();
      return false;
    }
    pos.add(a.dir.x * tmpDeltaMulImpact, a.dir.y * tmpDeltaMulImpact);
    if (pos.x + getDimensions().width < -Stats.WIDTH_DIV_10) pos.x = Stats.WIDTH_DIV_10 - getDimensions().width;
    if (pos.x > Stats.WIDTH_PLUS_MARGIN) pos.x = Stats.WIDTH_PLUS_MARGIN;
    if (pos.y > CSG.heightPlus4 + getDimensions().height) pos.y = CSG.heightPlus4 + getDimensions().height - 1;
    return true;
  }

  public boolean stillAliveEnemyWeapon(EnemyWeapon a) {
    tmpDeltaMulImpact = EndlessMode.delta * Stats.IMPACT;
    pos.add(a.dir.x * tmpDeltaMulImpact, a.dir.y * tmpDeltaMulImpact);
    hp -= 30;
    if (hp <= 0) {
      die();
      return false;
    }
    return true;
  }

  public void die() {
    if (dead)
      return;
    Bonus.addBonus(this);
    Particles.explosion(this);
    SoundMan.playBruitage(getExplosionSound());
    ScreenShake.screenShake(getXp());
    dead = true;
  }

  @Override
  public void reset() {
    hp = getMaxHp();
    dead = false;
    nextShot = 0;
    super.reset();
  }

  public static void bombe() {
    CSG.talkToTheWorld.unlockAchievementGPGS(Strings.ACH_BOMB);
    EndlessMode.transition.activate(10, Transition.BOMB);
    if (LIST.size >= 15)
      CSG.talkToTheWorld.unlockAchievementGPGS(Strings.ACH_15_ENEMY);
    WaveEffect.add(Player.xCenter, Player.yCenter, CSG.gm.palette().convertARGB(1, 1f, (CSG.R.nextFloat() + .8f) / 1.6f, CSG.R.nextFloat() / 8));
    WaveEffect.add(Player.xCenter, Player.yCenter, CSG.gm.palette().convertARGB(1, 1f, (CSG.R.nextFloat() + .8f) / 1.6f, CSG.R.nextFloat() / 8));
    attackAllEnemies(bomb);
  }

  public static void attackAllEnemies(PlayerWeapon a) {
    for (final Enemy e : LIST)
      if (e.dead == false) {
        a.dir.set((e.pos.x + e.getDimensions().halfWidth) - Player.xCenter, (e.pos.y + e.getDimensions().halfWidth) - Player.yCenter);
        e.stillAlive(a);
        a.dir.nor().scl(250);
        e.pos.add(a.dir.x * Stats.IMPACT, a.dir.y * Stats.IMPACT);
      }
    EndlessMode.effetBloom();
    Particles.bombExplosion(a);
  }

  public static void clear() {
    for (final Enemy e : LIST)
      e.free();
    LIST.clear();
  }

  public static void deadBodiesEverywhere() {
    for (int i = 0; i < Enemy.LIST.size; i++)
      if (Enemy.LIST.get(i).dead) {
        Enemy.LIST.get(i).free();
        Enemy.LIST.removeIndex(i);
      }
  }

  public static final PlayerWeapon bomb = getTypicalBullet(250), superBomb = getTypicalBullet(320);

  protected static PlayerWeapon getTypicalBullet(int power) {
    return new PlayerWeapon() {
      @Override
      public void free() {
      }

      @Override
      public float getColor() {
        return 0;
      }

      @Override
      public int getPower() {
        return 250;
      }

      @Override
      public float[] getColors() {
        return PrecalculatedParticles.colorsOverTimeRed;
      }

      @Override
      public void setAngle(float angle) {
      }

      @Override
      public Animations getAnimation() {
        return null;
      }

      @Override
      public Dimensions getDimensions() {
        return Dimensions.ORANGE_BULLET;
      }
    };
  }

  protected static int getXp(int baseXp, int lvl) {
    switch (lvl) {
      case 1:
        return (int) (baseXp * CSG.mulLvl1 * CSG.mulSCORE);
      case 3:
        return (int) (baseXp * CSG.mulLvl3 * CSG.mulSCORE);
      default:
        return (int) (baseXp * CSG.mulLvl4 * CSG.mulSCORE);
    }
  }

  public int getShotNumber() {
    return 0;
  }

  public float getRotation() {
    return 0;
  }

  public int getNumberOfShots() {
    return 0;
  }

  public void addAngle(float f) {
    angle += f;
  }

  public int getColor() {
    return RED;
  }

  public float getAngle() {
    return angle;
  }

  public void setNextShot(float g) {
    nextShot = g;
  }

  public boolean toLeft() {
    return false;
  }

  public void setAngle(float angle) {
    this.angle = angle;
  }

  protected Sound getExplosionSound() {
    return SoundMan.explosion3;
  }

  public int getXp() {
    return getEnemyStats().getXp();
  }

  public int getExplosionCount() {
    return getEnemyStats().explosion;
  }

  public float getFirerate() {
    return getEnemyStats().getFirerate();
  }

  public int getBonusValue() {
    return getEnemyStats().getBonusValue();
  }

  public static int getPvBoss(int pvBoss) {
    return (int) (pvBoss + (pvBoss * (Player.weapon.nv() / 1.3f)));
  }

  public boolean isTouched(Weapon a) {
    return a.getRectangleCollision().overlaps(getRectangleCollision());
  }

  private Rectangle getRectangleCollision() {
    return getRectangleCollision(COLLISION);
  }

  public boolean isOnPlayer() {
    return getRectangleCollision().contains(Player.xCenter, Player.yCenter);
  }

  @Override
  public float getPvPercentage() {
    return (getMaxHp() / hp) * 100;
  }

  @Override
  public boolean isInGoodShape() {
    return false;
  }

  public void setRotation(float f) {
  }

  public void setLeft(boolean b) {
  }

  public void addShots(int i) {
  }

  protected void shoot() {
  }

  public abstract EnemyStats getEnemyStats();

  protected abstract void free();

  public String toString() {
    return getClass() + "{" +
            "dead=" + dead +
            ", nextShot=" + nextShot +
            ", hp=" + hp +
            '}';
  }
}