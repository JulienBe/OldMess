package elements.procedural;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by julein on 12/08/16.
 */
public class ColorUtils {
    public static Color lighter(Color color, float factor) {
        float red = color.r * (1.0f + factor);
        float green = color.g * (1.0f + factor);
        float blue = color.b * (1.0f + factor);

        if (red > 1)
            red = 1;
        if (green > 1)
            green = 1;
        if (blue > 1)
            blue = 1;

        return new Color(red, green, blue, color.a);
    }
}
