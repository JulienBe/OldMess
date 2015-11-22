package behind.levelbuilder;

import elements.generic.enemies.Enemy;
import elements.generic.enemies.EnemyGroup;
import elements.generic.enemies.EnemyType;
import jeu.CSG;
import jeu.mode.EndlessMode;

/**
 * Created by julien on 11/2/15.
 */
public class Orchestrator {

  private EnemyPattern enemyPattern;
  private int index, patternIndex;
  private EnemyType enemy;
  private static final float GAP = 0.433f;
  private float nextInvoke = 0f;
  private int scoreDiv = 1;

  public void reset() {
    nextInvoke = 2;
  }

  public void invoke(float score) {
    if (nextInvoke > EndlessMode.now)
      return;
    if (enemyPattern == null)
      init(score);
    nextInvoke = EndlessMode.now + GAP;
    for (Nest nest : enemyPattern.nest[index]) {
      Enemy e = enemy.enemies[patternIndex % enemy.enemies.length].obtain();
      e.added(nest.x, nest.y);
    }
    if (++index >= enemyPattern.nest.length) {
      nextInvoke = EndlessMode.now + GAP * 2;
      enemyPattern = null;
    }
  }

  private void init(float score) {
    enemy = getEnemyType(score / scoreDiv);
    scoreDiv %= 4;
    scoreDiv++;
    enemyPattern = startNextWave(score / scoreDiv);
    patternIndex++;
  }

  private EnemyType getEnemyType(float score) {
    int toTry = CSG.gm.rand.nextInt(EnemyGroup.GROUPS.length);
    for (int i = 0; i < EnemyGroup.GROUPS.length; i++) {
      int toTest = (i + toTry) % EnemyGroup.GROUPS.length;
      if (score > EnemyGroup.GROUPS[toTest].cost)
        return EnemyGroup.GROUPS[toTest];
    }
    return EnemyType.BASIC1;
  }

  private EnemyPattern startNextWave(float score) {
    index = 0;
    int toTry = CSG.gm.rand.nextInt(EnemyPattern.ENEMY_PATTERNs.length);
    for (int i = 0; i < EnemyPattern.ENEMY_PATTERNs.length; i++)
      if (score > EnemyPattern.ENEMY_PATTERNs[(i + toTry) % EnemyPattern.ENEMY_PATTERNs.length].cost)
        return EnemyPattern.ENEMY_PATTERNs[(i + toTry) % EnemyPattern.ENEMY_PATTERNs.length];
    return EnemyPattern.DEFAULT_ENEMY_PATTERNs[CSG.gm.rand.nextInt(EnemyPattern.DEFAULT_ENEMY_PATTERNs.length)];
    //return EnemyPattern.ENEMY_PATTERNs[0];
  }
}