package jeu.colors;

import com.badlogic.gdx.utils.Array;
import jeu.CSG;

/**
 * Created by julein on 14/08/16.
 */
public enum Bunch {

    RED(initAlphasRed(0.065f, 0.15f, true)),
    BLUE(initAlphasBlue(0.065f, 0.15f, true)),
    GREEN(initAlphasYellowToGreen(0.065f, 0.15f, true));

    float[] colors;

    Bunch(float[] gradient) {
        this.colors = gradient;
    }

    public int length() {
        return colors.length;
    }

    public float get(int index) {
        return colors[index];
    }

    private static float[] initAlphasBlue(float step, float min, boolean white) {
        float alpha = 1;
        Array<Float> tmp = new Array<Float>();
        initArray(white, tmp, CSG.gm.palette().convertARGB(1, 0.95f, 1, 1), CSG.gm.palette().convertARGB(1, 0.50f, 1, 1), CSG.gm.palette().convertARGB(1, 0.25f, 1, 1));
        while (alpha > min) {
            tmp.add(CSG.gm.palette().convertARGB(alpha, 0.25f, Math.min(1, alpha * 2), 1));
            alpha -= step;
        }
        return CSG.convert(tmp);
    }

    private static float[] initAlphasRed(float step, float min, boolean white) {
        float alpha = 1;
        Array<Float> tmp = new Array<Float>();
        initArray(white, tmp, CSG.gm.palette().convertARGB(1, 1, 1, 0.95f), CSG.gm.palette().convertARGB(1, 1, 1, 0.50f), CSG.gm.palette().convertARGB(1, 1, 1, 0.25f));
        while (alpha > min) {
            tmp.add(CSG.gm.palette().convertARGB(alpha, 1, Math.min(1, alpha * 2), 0.05f));
            alpha -= step;
        }
        return CSG.convert(tmp);
    }

    private static float[] initAlphasYellowToGreen(float step, float min, boolean white) {
        float alpha = 1;
        Array<Float> tmp = new Array<Float>();
        initArray(white, tmp, CSG.gm.palette().convertARGB(1, 1, 1, 0.95f), CSG.gm.palette().convertARGB(1, 1, 1, 0.55f), CSG.gm.palette().convertARGB(1, 1, 1, 0.25f));
        while (alpha > min) {
            tmp.add(CSG.gm.palette().convertARGB(alpha, Math.min(1, alpha * 2), 1, 0.25f));
            alpha -= step;
        }
        return CSG.convert(tmp);
    }

    private static void initArray(boolean white, Array<Float> tmp, float value, float value2, float value3) {
        if (white) {
            tmp.add(value);
            tmp.add(value2);
            tmp.add(value3);
        }
    }

}
