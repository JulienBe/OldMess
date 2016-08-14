package elements.generic.enemies;

import com.badlogic.gdx.utils.Pool;
import elements.generic.enemies.individual.lvl1.*;
import elements.generic.enemies.individual.lvl3.*;
import elements.generic.enemies.individual.lvl4.*;

/**
 * Created by julien on 11/2/15.
 */
public enum EnemyType {

  BASIC1(Basic.POOL, ZigZag.POOL),
  BASIC2(Basic3.POOL, ZigZag3.POOL),
  BASIC3(Basic4.POOL, ZigZag4.POOL),
  VERY_EASY1(Shooter.POOL, Ball.POOL),
  VERY_EASY2(Shooter3.POOL, Ball3.POOL),
  VERY_EASY3(Shooter4.POOL, Ball4.POOL),
  CRUSADER1(Crusader.POOL),
  CRUSADER2(Crusader3.POOL),
  CRUSADER3(Crusader4.POOL),
  CYLON1(Cylon.POOL),
  CYLON2(Cylon3.POOL),
  CYLON3(Cylon4.POOL),
  DIABOLO1(Diabolo.POOL),
  DIABOLO2(Diabolo3.POOL),
  DIABOLO3(Diabolo4.POOL)
  ;

  public Pool<? extends Enemy>[] enemies;
  public int cost;

  EnemyType(Pool<? extends Enemy>... enemies) {
    this.enemies = enemies;
    for (Pool<? extends Enemy> enemy : enemies) {
      Enemy e = enemy.obtain();
      cost += e.getXp() * e.getXp();
      e.free();
    }
    System.out.println(this.cost + "  :  " + this);
  }
}
