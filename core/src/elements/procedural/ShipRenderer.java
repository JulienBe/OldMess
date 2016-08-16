package elements.procedural;

import assets.AssetMan;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import jeu.colors.Bunch;
import jeu.colors.MyColors;

/**
 * Created by julein on 12/08/16.
 */
public class ShipRenderer {

    private MyColors secondaryColor = MyColors.randomColor();
    private final Color border = new Color(0.05f, 0.05f, 0.05f, 1),
            empty = new Color(1, 1, 1, 0),
            secondary = secondaryColor.color,
            filled = new Color(0.164f, 0.164f, 0.164f, 1);

    public void render(Grid grid, SpriteBatch batch, int size) {
        for (int x = 0; x < grid.height(); x++) {
            for (int y = 0; y < grid.rowWidth(x); y++) {
                Pixel p = grid.get(x, y);
                switch(p.value) {
                    case EMPTY:             batch.setColor(empty);                                  break;
                    case FILLED:            batch.setColor(getFilledColor(p.depth));   break;
                    case SECONDARY:         batch.setColor(getSecondaryColor(p.depth));      break;
                    case FILL_STRUCTURE:    batch.setColor(getFillStrucColor(p.depth));  break;
                    case BORDER:            batch.setColor(border);         break;
                }
                batch.draw(AssetMan.debris, x * size, y * size, size, size);
            }
        }
    }

    private Color getSecondaryColor(float depth) {
        return ColorUtils.lighter(secondary, depth * 0.1f);
    }

    private Color getFillStrucColor(float depth) {
        return ColorUtils.lighter(secondary, depth * 0.25f);
    }

    public Color getFilledColor(float depth) {
        return ColorUtils.lighter(filled, depth * 0.05f > 3 ? 3 : depth * 0.05f);
    }

    public MyColors colors() {
    return secondaryColor;
  }
}
