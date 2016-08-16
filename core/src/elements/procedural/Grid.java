package elements.procedural;

import com.badlogic.gdx.utils.Array;

import java.awt.*;

/**
 * Created by julein on 11/08/16.
 */
public class Grid {

    private int previousNeighbour = 0, keptLine = 0;
    Pixel[][] pixels;

    Grid(int rows, int cols) {
        pixels = initEmptyGrid(rows, cols);
    }

    private Pixel[][] initEmptyGrid(int rows, int cols) {
        Pixel[][] grid = new Pixel[rows][cols];
        for (int x = 0; x < rows; x++)
            for (int y = 0; y < cols; y++)
                grid[x][y] = new Pixel();
        return grid;
    }

    public int height() {
        return pixels.length;
    }
    int rowWidth(int index) {
        return pixels[index].length;
    }
    public Pixel get(int x, int y) {
        return pixels[x][y];
    }

    void addExtras(Steps stepsConst, Parameters param, Rng rng) {
        int steps = rng.intBetween(stepsConst.minSteps - 10, stepsConst.maxSteps - 10);
        int subSteps = rng.intBetween(stepsConst.minSubStep - 10, stepsConst.maxSubSteps - 10);

        for (int i = 0; i < steps; i++) {
            Point point = getRandomFilledPoint(rng);
            for (int y = 0; y < subSteps; y++)
                point = processPoint(point, param, rng);
        }
    }

    // TODO remove awt point
    private Point getRandomFilledPoint(Rng rng) {
        Point point = null;
        while (point == null) {
            int x = rng.intBetween(1, height() - 1);
            int y = rng.intBetween(1, rowWidth(0) - 1);
            Pixel possiblePixel = get(x, y);
            if (possiblePixel.value == Pixel.State.FILLED) {
                point = new Point();
                point.x = x;
                point.y = y;
            }
        }
        return point;
    }

    void removeEmptyCells() {
        Pixel[][] flooredGrid;
        int lastFilledRow = 0;
        int lastFilledCol = 0;
        int firstFilledRow = height();
        int firstFilledColumn = rowWidth(0);
        for (int x = 0; x < height(); x++) {
            boolean empty = true;
            for (int y = 0; y < rowWidth(0); y++) {
                boolean colEmpty = true;
                if (get(x, y).value != Pixel.State.EMPTY) {
                    if (firstFilledRow > x)
                        firstFilledRow = x;
                    if (firstFilledColumn > y)
                        firstFilledColumn = y;
                    empty = false;
                    colEmpty = false;
                }
                if (!colEmpty && y > lastFilledCol)
                    lastFilledCol = y;
            }
            if (!empty)
                lastFilledRow = x;
        }
        flooredGrid = new Pixel[lastFilledRow - (firstFilledRow - 1)][lastFilledCol - (firstFilledColumn - 1)];
        int newRow = 0;
        for (int r = firstFilledRow; r < lastFilledRow + 1; r++) {
            int newCol = 0;
            for (int c = firstFilledColumn; c < lastFilledCol + 1; c++) {
                flooredGrid[newRow][newCol] = get(r, c);
                newCol++;
            }
            newRow++;
        }
        pixels = flooredGrid;
    }

    void mirrorCopyGridHorizontally() {
        int rows = height();
        int cols = rowWidth(0) * 2;

        Pixel[][] fullGrid = new Pixel[rows][cols];

        // Copy left to right
        for (int x = 0; x < rows; x++)
            for (int y = 0; y < (cols / 2); y++) {
                fullGrid[x][y] = pixels[x][y];
                fullGrid[x][(cols - 1) - y] = pixels[x][y];
            }
        pixels = fullGrid;
    }

    void addBorders() {
        extendGrid(2);

        // omg could this be less readable ?
        for (int x = 0; x < height(); x++) {
            for (int y = 0; y < rowWidth(x); y++) {
                if (get(x, y).value == Pixel.State.FILLED) {
                    // Top
                    if (get(x == 0 ? 0 : x - 1, y).value != Pixel.State.FILLED && get(x == 0 ? 0 : x - 1, y).value != Pixel.State.SECONDARY)
                        set(x == 0 ? 0 : x - 1, y, Pixel.State.BORDER);
                    // Left
                    if (get(x, y == 0 ? 0 : y - 1).value != Pixel.State.FILLED && get(x, y == 0 ? 0 : y - 1).value != Pixel.State.SECONDARY)
                        set(x, y == 0 ? 0 : y - 1, Pixel.State.BORDER);
                    // Right
                    if (get(x, y == (rowWidth(0) / 2) - 1 ? (rowWidth(0) / 2) - 1 : y + 1).value != Pixel.State.FILLED && get(x, y == (rowWidth(0) / 2) - 1 ? (rowWidth(0) / 2) - 1 : y + 1).value != Pixel.State.SECONDARY)
                        set(x, y == (rowWidth(0) / 2) - 1 ? (rowWidth(0) / 2) - 1 : y + 1, Pixel.State.BORDER);
                    // Bottom
                    if (get(x == height() - 1 ? height() - 1 : x + 1, y).value != Pixel.State.FILLED && get(x == height() - 1 ? height() - 1 : x + 1, y).value != Pixel.State.SECONDARY)
                        set(x == height() - 1 ? height() - 1 : x + 1, y, Pixel.State.BORDER);
                }
            }
        }
    }

    private void extendGrid(int extendAmount) {
        Pixel[][] extendedGrid = initEmptyGrid(height() + extendAmount, rowWidth(0) + extendAmount);
        for (int x = 0; x < height(); x++)
            for (int y = 0; y < rowWidth(0); y++)
                extendedGrid[x + (extendAmount / 2)][y + (extendAmount / 2)] = get(x, y);
        pixels = extendedGrid;
    }

    public void set(int x, int y, Pixel.State state) {
        pixels[x][y].value = state;
    }

    void correctHeight() {
        Array<Integer> toBeDiscarded = new Array<Integer>();
        for (int i = 0; i < width(); i++)
            if (colHasOnly(Pixel.State.EMPTY, i))
                toBeDiscarded.add(i);
    }

    private boolean colHasOnly(Pixel.State state, int index) {
        for (int i = 0; i < height(); i++)
            if (pixels[index][height()].value != state)
                return false;
        return true;
    }

    void fillEmptySurroundedPixelsInGrid() {
        for (int x = 0; x < height(); x++) {
            for (int y = 0; y < rowWidth(0); y++) {
                if (get(x, y).value == Pixel.State.EMPTY) {
                    boolean filledPixelAbove = false;
                    boolean filledPixelOnTheLeft = false;
                    boolean filledPixelOnTheRight = false;

                    for (int x1 = x - 1; x1 > 0; x1--)
                        if (get(x1, y).value == Pixel.State.FILLED) {
                            filledPixelAbove = true;
                            break;
                        }
                    for (int y1 = y - 1; y1 > 0; y1--)
                        if (get(x, y1).value == Pixel.State.FILLED) {
                            filledPixelOnTheLeft = true;
                            break;
                        }
                    for (int y1 = y + 1; y1 < rowWidth(0); y1++)
                        if (get(x, y1).value == Pixel.State.FILLED) {
                            filledPixelOnTheRight = true;
                            break;
                        }
                    if (filledPixelAbove && filledPixelOnTheLeft && filledPixelOnTheRight)
                        get(x, y).value = Pixel.State.SECONDARY;
                }
            }
        }
    }

    void setPixelDepth() {
        for (int x = 0; x < height(); x++) {
            for (int y = 0; y < rowWidth(0); y++) {
                if (get(x, y).value != Pixel.State.EMPTY && get(x, y).value != Pixel.State.BORDER) {
                    Pixel.State pixelValue = get(x, y).value;

                    int noOfSamePixelsAbove = 0;
                    int noOfSamePixelsBelow = 0;
                    int noOfSamePixelsOnTheLeft = 0;
                    int noOfSamePixelsOnTheRight = 0;

                    for (int r1 = x - 1; r1 > 0; r1--)
                        if (pixelValue == get(r1, y).value)
                            noOfSamePixelsAbove++;

                    for (int r1 = x + 1; r1 < height(); r1++)
                        if (pixelValue == get(r1, y).value)
                            noOfSamePixelsBelow++;

                    for (int c1 = y - 1; c1 > 0; c1--)
                        if (pixelValue == get(x, c1).value)
                            noOfSamePixelsOnTheLeft++;

                    for (int c1 = y + 1; c1 < rowWidth(0); c1++)
                        if (pixelValue == get(x, c1).value)
                            noOfSamePixelsOnTheRight++;

                    int depth1 = Math.min(noOfSamePixelsAbove, noOfSamePixelsBelow);
                    int depth2 = Math.min(noOfSamePixelsOnTheLeft, noOfSamePixelsOnTheRight);

                    get(x, y).depth = Math.min(depth1, depth2) * 2;
                }
            }
        }
    }

    // TODO : inverted here too
    void addStuctureToFlatAreas(Parameters param, Rng rnd) {
        for (int x = 0; x < height(); x++) {
            int streakLength = rnd.gaussianFlooredScaler(param.streakMul);
            int currentStreak = 0;
            for (int y = 0; y < rowWidth(0); y++) {
                if (get(x, y).value == Pixel.State.SECONDARY) {
                    boolean filledPixelAbove = get(x - 1, y).value != Pixel.State.EMPTY;
                    boolean filledPixelBelow = get(x + 1, y).value != Pixel.State.EMPTY;
                    boolean filledPixelOnTheLeft = get(x, y - 1).value != Pixel.State.EMPTY;
                    boolean filledPixelOnTheRight = get(x, y + 1).value != Pixel.State.EMPTY;
                    if (currentStreak++ < streakLength)
                        setFillIfAll(x, y, filledPixelAbove, filledPixelBelow, filledPixelOnTheLeft, filledPixelOnTheRight);
                    else if (rnd.anInt(4) == 0)
                        setNoiseValue(param, x, y, filledPixelAbove, filledPixelBelow, filledPixelOnTheLeft, filledPixelOnTheRight);
                }
            }
        }
    }

    private void setNoiseValue(Parameters param, int r, int c, boolean... check) {
        /**
         * TODO : Rework to make it more flexible
         */
        if (Utility.countTrues(check) == check.length) {
//            int secondary = Rng.anInt(param.greyNoisePercentage);
//            int border = Rng.anInt(param.blackNoisePercentage);
//            int filled = Rng.anInt(param.colorNoisePercentage);
//            if (secondary > border && secondary > filled)
//                grid[r][c].value = Pixel.State.SECONDARY;
//            if (border > secondary && border > filled)
//                grid[r][c].value = Pixel.State.BORDER;
//            if (filled > secondary && filled > border)
            if (c % 2 == 0)
                get(r, c).value = Pixel.State.FILLED;
            else
                get(r, c).value = Pixel.State.SECONDARY;
        }
    }

    private void setFillIfAll(int r, int c, boolean... check) {
        if (Utility.countTrues(check) == check.length)
            get(r, c).value = Pixel.State.FILL_STRUCTURE;
    }

    public void addPattern(Pixel.State state, Rng rnd) {
        int iterations = rnd.anInt(12);
        int length = rnd.anInt(30);
        int startX = rnd.anInt(height());
        int startY = rnd.anInt(rowWidth(startX));
        for (int i = 0; i < iterations; i++)
            drawLine(state, length, -1, i % 2, startY + i * 4, startX + rowWidth(0) - i);
    }


    public void drawLine(Pixel.State state, int iterations, int xOrientation, int yOrientation, int xStarter, int yStarter) {
        int x = xStarter;
        int y = yStarter;
        for (int i = 0; i < iterations; i++) {
            if (coordWithinGrid(x, y))
                get(x, y).value = state;
            // continuing if not valid allows for picking a not valid starting point
            x += xOrientation;
            y += yOrientation;
        }
    }

    public boolean coordWithinGrid(int x, int y) {
        return x >= 0 && y >= 0 && x < height() && y < rowWidth(x);
    }

    public Point processPoint(Point point, Parameters param, Rng rng) {
        if (get(point.x, point.y).value == Pixel.State.EMPTY)
            get(point.x, point.y).value = Pixel.State.FILLED;
        return getRandomAdjacentPoint(point, param, rng);
    }
    private Point getRandomAdjacentPoint(Point point, Parameters parameters, Rng rng) {
        Point[] neighbours = getNeightboursPoints(point);

        // go to a random neighbour
        Point newPoint = null;
        if (rng.aFloat() * keptLine < parameters.tendancyToKeepLine) {
            newPoint = neighbours[previousNeighbour];
            keptLine++;
        } else {
            keptLine = 0;
        }
        while (newPoint == null) {
            int ri = rng.intBetween(0, neighbours.length);
            if (neighbours[ri] != null) {
                newPoint = neighbours[ri];
                previousNeighbour = ri;
            }
        }
        return newPoint;
    }
    private Point[] getNeightboursPoints(Point point) {
        Point[] neighbours = new Point[11];
        Point top = new Point(point.x - 1, point.y);
        Point topLeft = new Point(point.x - 1, point.y - 1);
        Point topRight = new Point(point.x - 1, point.y + 1);
        Point bottom = new Point(point.x + 1, point.y);
        Point bottomLeft = new Point(point.x + 1, point.y - 1);
        Point bottomRight = new Point(point.x + 1, point.y + 1);
        Point left = new Point(point.x, point.y - 1);
        Point right = new Point(point.x, point.y + 1);

        assignIfValid(0, top, neighbours);
        assignIfValid(2, left, neighbours);
        assignIfValid(3, right, neighbours);
        assignIfValid(1, bottom, neighbours);
        assignIfValid(4, topLeft, neighbours);
        assignIfValid(5, topRight, neighbours);
        assignIfValid(6, bottomLeft, neighbours);
        assignIfValid(7, bottomRight, neighbours);
        assignIfValid(8, top, neighbours);
        assignIfValid(9, bottom, neighbours);
        assignIfValid(9, right, neighbours);

        return neighbours;
    }
    private void assignIfValid(int index, Point point, Point[] points) {
        if (coordWithinGrid(point.x, point.y))
            points[index] = point;
    }

    public void outputGridAsAscii() {
        for (int x = 0; x < height(); x++) {
            StringBuilder strBld = new StringBuilder();
            for (int y = 0; y < rowWidth(0); y++) {
                switch (get(x, y).value) {
                    case EMPTY:
                        strBld.append(" ");
                        break;
                    case FILLED:
                        strBld.append(".");
                        break;
                    case BORDER:
                        strBld.append("x");
                        break;
                }
            }
            System.out.println(strBld.toString());
        }
    }

    public int width() {
        int max = 0;
        for (int x = 0; x < height(); x++)
            max = rowWidth(x) > max ? rowWidth(x) : max;
        return max;
    }
}
