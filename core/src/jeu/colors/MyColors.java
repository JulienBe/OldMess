package jeu.colors;

import com.badlogic.gdx.graphics.Color;
import jeu.CSG;

/**
 * Created by julein on 14/08/16.
 */
public enum MyColors {

    BLUE(new Color(0x0f7cccff), "Blue", RandomishColor.BLUE, Bunch.BLUE),
    RED(new Color(0x935610ff), "Red", RandomishColor.RED, Bunch.RED),
    GREEN(new Color(0x44ac4aff), "Green", RandomishColor.GREEN, Bunch.GREEN);
    public static final MyColors[] COLORS = {BLUE, RED, GREEN};

    public Color color;
    public float fColor;
    public String name;
    public RandomishColor randomishColor;
    public Bunch bunch;

    MyColors(Color color, String name, RandomishColor randomishColor, Bunch gradient) {
        this.color = color;
        this.fColor = color.toFloatBits();
        this.name = name;
        this.randomishColor = randomishColor;
        this.bunch = gradient;
    }

    public static MyColors randomColor() {
        return COLORS[CSG.R.nextInt(COLORS.length)];
    }
}
