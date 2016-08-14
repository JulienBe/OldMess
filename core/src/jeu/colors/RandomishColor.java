package jeu.colors;

import jeu.CSG;

/**
 * Created by julein on 14/08/16.
 */
public enum RandomishColor {

    RED(new ColorGenerator() {
        public float getColor() {
            return CSG.gm.palette().convertARGB(1, 1f, (CSG.R.nextFloat() / 2) + 0.49f, CSG.R.nextFloat() / 8);
        }
    }),
    BLUE(new ColorGenerator() {
        public float getColor() {
            return CSG.gm.palette().convertARGB(1, CSG.R.nextFloat() / 8, (CSG.R.nextFloat() / 2) + 0.49f, .85f);
        }
    }),
    GREEN(new ColorGenerator() {
        public float getColor() {
            return CSG.gm.palette().convertARGB(1, (CSG.R.nextFloat() / 2) + 0.49f, 1, CSG.R.nextFloat() / 8);
        }
    }),
    GREEN_CYAN(new ColorGenerator() {
        public float getColor() {
            if (CSG.R.nextBoolean())
                // 0 - 0.125		1		0.5 - 1
                return CSG.gm.palette().convertARGB(1, CSG.R.nextFloat() / 8, 1, (CSG.R.nextFloat() / 2) + 0.49f);
            // 0.5 - 1			1		0 - 0.125
            return CSG.gm.palette().convertARGB(1, (CSG.R.nextFloat() / 2) + 0.49f, 1, CSG.R.nextFloat() / 8);
        }
    });

    public ColorGenerator colorGenerator;

    RandomishColor(ColorGenerator colorGenerator) {
        this.colorGenerator = colorGenerator;
    }

}
