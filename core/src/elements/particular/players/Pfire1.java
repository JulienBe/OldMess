package elements.particular.players;

import assets.AssetMan;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import elements.particular.particles.individual.PrecalculatedParticles;
import elements.particular.particles.individual.smoke.MovingSquareSmoke;
import jeu.CSG;
import jeu.Stats;

/**
 * Created by julien on 11/22/15.
 */
public class Pfire1 extends Player {

  public static final float WIDTH = Stats.U4,
          HEARTH_WIDTH = (WIDTH / 16) * 4, HEARTH_HALF_WIDTH = HEARTH_WIDTH / 2,
          PROPEL_HEIGHT = (WIDTH / 16) * 3, PROPEL_HALF_HEIGHT = PROPEL_HEIGHT / 2,
          COLOR = CSG.gm.palette().convertARGB(1, 0, 0.75f, 1),
          PROPEL_Y_OFFSET = WIDTH * (0.5f/16f),
          SPEED_MUL = Stats.u / 1000;
  public static final float[] COLORS = PrecalculatedParticles.colorsOverTimeBlueLong4;
  private Array<MovingSquareSmoke> smoke = new Array<MovingSquareSmoke>();
  private int index = 0, yIndex = 0;
  private boolean up = true;

  public Pfire1() {
    super(WIDTH, WIDTH);
  }

  @Override
  public void draw(SpriteBatch batch) {
    colors();
    yDir();
    yParticles();
    yIndex = (COLORS.length - 1) - yIndex;
    mainSprite(batch);
    hearthSprite(batch);

    MovingSquareSmoke.act(smoke, batch);

    drawPropel(batch);

    super.draw(batch);
  }

  private void drawPropel(SpriteBatch batch) {
    batch.setColor(COLORS[yIndex]);
    batch.draw(AssetMan.playerFireLevel1Propel,
            POS.x + halfWidth - PROPEL_HALF_HEIGHT, POS.y + PROPEL_Y_OFFSET,
            PROPEL_HALF_HEIGHT, HEARTH_HALF_WIDTH,
            PROPEL_HEIGHT, HEARTH_WIDTH, 1, 1, 90);
    if (xDir <= 1) {
      batch.setColor(COLORS[
              ((int) ((COLORS.length - 1 - xDir) % COLORS.length))
              ]);
    }
    batch.draw(AssetMan.playerFireLevel1Propel,
            POS.x + width - PROPEL_HEIGHT - PROPEL_HALF_HEIGHT, POS.y + halfHeight - PROPEL_Y_OFFSET - PROPEL_HALF_HEIGHT,
            PROPEL_HALF_HEIGHT, HEARTH_HALF_WIDTH,
            PROPEL_HEIGHT, HEARTH_WIDTH, 1, 1, 180);
    batch.setColor(COLORS[COLORS.length - 1]);
    if (xDir >= 1) {
      batch.setColor(COLORS[
              ((int) ((COLORS.length - 1 + xDir) % COLORS.length))
              ]);
    }
    batch.draw(AssetMan.playerFireLevel1Propel,
            POS.x + PROPEL_HEIGHT - PROPEL_HALF_HEIGHT, POS.y + halfHeight - PROPEL_Y_OFFSET - PROPEL_HALF_HEIGHT, PROPEL_HEIGHT, HEARTH_WIDTH);
  }

  private void hearthSprite(SpriteBatch batch) {
    batch.setColor(COLORS[index]);
    batch.draw(AssetMan.playerFireLevel1Hearth, POS.x + halfWidth - HEARTH_HALF_WIDTH, POS.y + halfHeight - HEARTH_HALF_WIDTH, HEARTH_WIDTH, HEARTH_WIDTH);
  }

  private void mainSprite(SpriteBatch batch) {
    batch.setColor(COLOR);
    batch.draw(AssetMan.playerFireLevel1, POS.x, POS.y, width, height);
  }

  private void yDir() {
    if (yDir < 1)
      yDir = 1;
    else if (yDir >= COLORS.length)
      yDir = COLORS.length - 1;
    yIndex = (int) yDir;
  }

  private void colors() {
    if (index + 1 >= COLORS.length)   up = false;
    else if (index <= 0)                                                      up = true;
    if (up) index++;
    else    index--;
  }

  private void yParticles() {
    final float speed = -(yIndex * yIndex * SPEED_MUL * (0.9f + (CSG.R.nextFloat() / 5) ));
    for (int i = 0; i < yDir; i += 8) {
      smoke.add(MovingSquareSmoke.POOL.obtain().init(
              POS.x + halfWidth + (CSG.R.nextFloat() * MovingSquareSmoke.HALF_WIDTH) - MovingSquareSmoke.HALF_WIDTH,
              POS.y + (CSG.R.nextFloat() * MovingSquareSmoke.WIDTH) - MovingSquareSmoke.HALF_WIDTH,
              PrecalculatedParticles.colorsOverTimeBlue,
              0, speed,
              10 + CSG.R.nextInt(PrecalculatedParticles.colorsOverTimeBlue.length)
      ));
    }
    if (xDir > 2)
      for (int i = 0; i < Math.abs(xDir); i += 1) {
        smoke.add(MovingSquareSmoke.POOL.obtain().init(
                POS.x - MovingSquareSmoke.WIDTH + CSG.R.nextFloat() * MovingSquareSmoke.WIDTH,
                POS.y + halfWidth + (PROPEL_Y_OFFSET * (-4 + CSG.R.nextFloat() * 3)),
                PrecalculatedParticles.colorsOverTimeBlue,
                -Stats.u, -Stats.uDiv2,
                10 + CSG.R.nextInt(PrecalculatedParticles.colorsOverTimeBlue.length)
        ));
      }
    if (xDir < -2)
      for (int i = 0; i < Math.abs(xDir); i += 1) {
        smoke.add(MovingSquareSmoke.POOL.obtain().init(
                POS.x + width - MovingSquareSmoke.WIDTH + CSG.R.nextFloat() * MovingSquareSmoke.WIDTH,
                POS.y + halfWidth + (PROPEL_Y_OFFSET * (-4 + CSG.R.nextFloat() * 3)),
                PrecalculatedParticles.colorsOverTimeBlue,
                Stats.u, -Stats.uDiv2,
                10 + CSG.R.nextInt(PrecalculatedParticles.colorsOverTimeBlue.length)
        ));
      }
  }
}
