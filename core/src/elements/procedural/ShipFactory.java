package elements.procedural;

import java.awt.*;

/**
 * Created by julein on 11/08/16.
 */
public class ShipFactory {

    public final Metric timeCreate = new Metric(),
            timeRemoveEmpty1 = new Metric(),
            timeRemoveEmpty2 = new Metric(),
            timeMirror = new Metric(),
            timeAddBorders = new Metric(),
            timeFillEmpty = new Metric(),
            timeDepth = new Metric(),
            timeNoise = new Metric(),
            timeAddExtra = new Metric();
    private final int rows, cols;
    private final Rng rng;
    private final Parameters param;
    private final Steps steps;

    public ShipFactory(Parameters param, Steps steps, Rng rng) {
        rows = param.maxHeight;
        cols = param.minHeight;
        this.param = param;
        this.steps = steps;
        this.rng = rng;
    }

    public Grid create() {
        timeCreate.start();
        Grid grid = createBaseGrid();
        timeCreate.end();

        timeAddExtra.start();
        grid.addExtras(steps, param, rng);
        timeAddExtra.end();

        timeRemoveEmpty1.start();
        grid.removeEmptyCells();
        timeRemoveEmpty1.end();
        if (grid.height() < param.minHeight || grid.height() > param.maxHeight)
            return create();

        timeMirror.start();
        grid.mirrorCopyGridHorizontally();
        timeMirror.end();

        timeAddBorders.start();
        grid.addBorders();
        timeAddBorders.end();

        timeRemoveEmpty2.start();
        grid.removeEmptyCells();
        timeRemoveEmpty2.end();

        timeFillEmpty.start();
        grid.fillEmptySurroundedPixelsInGrid();
        timeFillEmpty.end();

        timeDepth.start();
        grid.setPixelDepth();
        timeDepth.end();

        if (validateGrid(grid, param)) {
            timeNoise.start();
            grid.addStuctureToFlatAreas(param, rng);
            timeNoise.end();
            return grid;
        } else {
            return create();
        }
    }

    private boolean validateGrid(Grid grid, Parameters parameters) {
        boolean result = true;
        int noOfFilledPixels = 0, noOfSecondaryPixels = 0;

        for (int x = 0; x < grid.height(); x++) {
            for (int y = 0; y < grid.rowWidth(x); y++) {
                if (grid.get(x, y).value == Pixel.State.FILLED)
                    noOfFilledPixels++;
                else if (grid.get(x, y).value == Pixel.State.SECONDARY)
                    noOfSecondaryPixels++;
            }
        }
        int nbOfPixels = noOfFilledPixels + noOfSecondaryPixels;
        float colorPercentage = (float)noOfSecondaryPixels / (float)nbOfPixels;

        if (noOfSecondaryPixels == 0)
            return false;
        if (colorPercentage > parameters.colorMaxPercentage || colorPercentage < parameters.colorMinPercentage)
            return false;
        return result;
    }

    // TODO : remove awt point ?
    private Grid createBaseGrid() {
        Grid grid = new Grid(rows, cols);

        grid.addPattern(Pixel.State.FILLED, rng);
        Point point = new Point((int) (rows * param.tendencyToBeWide), cols - 1);

        int actualSteps = rng.intBetween(steps.minSubStep, steps.maxSubSteps);
        int actualSubSteps = rng.intBetween(steps.minSteps, steps.maxSteps);

        for (int i = 0; i < actualSteps; i++) {
            if (point == null) {
                // we are passed the first step lets find the lowest most pixel that is closest to the middle, and go again from there...
                // top down
                for (int x = 0; x < rows; x++) {
                    // left to right
                    for (int y = 0; y < cols; y++)
                        if (grid.get(x, y).value == Pixel.State.FILLED) {
                            point = new Point(x, y);
                        }
                }
            }
            for (int y = 0; y < actualSubSteps; y++)
                point = grid.processPoint(point, param, rng);
            point = null;
        }
        return grid;
    }

}
