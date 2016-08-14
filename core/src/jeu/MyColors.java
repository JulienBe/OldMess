package jeu;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by julein on 14/08/16.
 */
public enum MyColors {

    BLUE(new Color(0x0f7cccff), "Blue"),
    RED(new Color(0x935610ff), "Red"),
    GREEN(new Color(0x44ac4aff), "Green");
    public static final MyColors[] COLORS = {BLUE, RED, GREEN};

    public Color color;
    public float fColor;
    public String name;

    MyColors(Color color, String name) {
        this.color = color;
        this.fColor = color.toFloatBits();
        this.name = name;
    }

    public static MyColors randomColor() {
        return COLORS[CSG.R.nextInt(COLORS.length)];
    }
}
