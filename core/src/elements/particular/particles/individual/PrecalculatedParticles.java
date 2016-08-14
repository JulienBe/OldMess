package elements.particular.particles.individual;

import com.badlogic.gdx.utils.Array;

import elements.generic.weapons.player.Fireball;
import jeu.CSG;
import jeu.Stats;
import jeu.colors.Bunch;
import jeu.colors.RandomishColor;

public class PrecalculatedParticles {

	public static final float INITIAL_WIDTH = Stats.PIXEL, INITIAL_HALF_WIDTH = INITIAL_WIDTH / 2;
	private static final float stepSparkles = 0.095f;
	public static final float[] colorsRed = initAlphasRed(stepSparkles, 0, true), colorsBlue = initAlphasBlue(stepSparkles, 0, true), colorsYellowToGreen = initAlphasYellowToGreen(stepSparkles, 0, true);
	public static final float[] colorsThruster = initAlphasThruster(stepSparkles, 0, true);
	public static final float[] widths = initWidths(Stats.uDiv4, Bunch.BLUE.length(), INITIAL_WIDTH);
	public final static float[] widthsFireballParticules = initWidths(Stats.u, 0.75f, Fireball.DIMENSIONS.width);
	public final static float[] halfWidthsFireballParticules = CSG.getDifferences(widthsFireballParticules);
	public final static float[] colorsFireball = initColors(widthsFireballParticules.length, 1, 0.9f, 0);
	public final static float[] colorsPinkWeapon = initColors(7, 4, 1, 0.3f);
	public static final float[] halfWidths = CSG.getDifferences(widths);
	public static final float[] dirY = initDirY(widths);
	// not evolving
	public static final float[] RANDDOM_REDS = getColors(colorsRed.length, RandomishColor.RED), RANDDOM_BLUES = getColors(colorsRed.length, RandomishColor.BLUE), RANDDOM_GREENS = getColors(colorsRed.length, RandomishColor.GREEN);
	
	private static float[] getColors(int nb, RandomishColor painter) {
		float tmp[] = new float[nb];
		for(int i = 0; i < nb; i++) {
			tmp[i] = painter.colorGenerator.getColor();
		}
		return tmp;
	}
	
	private static final float stepColorsOverTime = 0.065f;
	public static final float[]
			colorsOverTimeMuzzle = initAlphasBlue(stepColorsOverTime * 100, 0.15f, true),
			
			colorsOverTimeRed = initAlphasRed(stepColorsOverTime, 0.15f, true),
			colorsOverTimeBlue = initAlphasBlue(stepColorsOverTime, 0.15f, true),
			colorsOverTimeYellowToGreen = initAlphasYellowToGreen(stepColorsOverTime, 0.15f, true),
			colorsOverTimeCyanToGreen = initAlphasCyanToGreen(stepColorsOverTime, 0.15f, true),
			
			colorsOverTimeRedLong = initAlphasRed(stepColorsOverTime / 2, 0.15f, true),
			colorsOverTimeBlueLong = initAlphasBlue(stepColorsOverTime / 2, 0.1f, true),
			colorsOverTimeYellowToGreenLong = initAlphasYellowToGreen(stepColorsOverTime / 2, 0.10f, true),
			colorsOverTimeCyanToGreenLong = initAlphasCyanToGreen(stepColorsOverTime / 2, 0.10f, true),
			
			colorsOverTimeRedLong4 = initAlphasRed(stepColorsOverTime / 4, 0.15f, true),
			colorsOverTimeBlueLong4 = initAlphasBlue(stepColorsOverTime / 4, 0.1f, true),
			colorsOverTimeYellowToGreenLong4 = initAlphasYellowToGreen(stepColorsOverTime / 4, 0.10f, true);
	public static final float[]
			widthsColorOverTime = initWidths(-(INITIAL_WIDTH * 4 / colorsOverTimeRed.length) , colorsOverTimeRed.length  + 2, INITIAL_WIDTH * 4),
			widthsColorOVerTime2 = CSG.getDouble(widthsColorOverTime),
			widthsColorOverTimeLong = initWidths(-(INITIAL_WIDTH * 4 / colorsOverTimeRedLong.length) , colorsOverTimeRedLong.length  + 2, INITIAL_WIDTH * 4),
			widthsColorOverTimeLongDifferences = CSG.getDifferences(widthsColorOverTimeLong),
			
			widthsColorOverTimeLong4 = initWidths( -((INITIAL_WIDTH * 4 / colorsOverTimeRedLong4.length)), colorsOverTimeRedLong4.length + 2, INITIAL_WIDTH * 4),
			widthsColorOverTimeLong4Differences = CSG.getDifferences(widthsColorOverTimeLong4);
	public static final float[] halfWidthsColorOverTime = CSG.getDifferences(widthsColorOverTime);
	
	private static float[] initWidths(float max, float mult, float initial) {
		Array<Float> tmp = new Array<Float>();
		float w = initial;
		while (w >= max) {
			tmp.add(w);
			w *= mult;
		}
		return CSG.convert(tmp);
	}

	private static float[] initWidths(float step, int max, float init) {
		float f = init;
		Array<Float> tmp = new Array<Float>();
		for (int i = 0; i <= max; i++) {
			tmp.add(f);
			f += step;
		}
		return CSG.convert(tmp);
	}

	private static float[] initDirY(float[] widths2) {
		float alpha = CSG.height / 50;
		Array<Float> tmp = new Array<Float>();
		while (alpha > 0) {
			tmp.add(alpha);
			alpha -= 0.075f;
		}
		return CSG.convert(tmp);
	}
	
	/**
	 * fireball
	 * @param nbFrames
	 * @param div
	 * @param g
	 * @param b
	 * @return
	 */
	private static float[] initColors(int nbFrames, int div, float g, float b) {
		int cpt = 0;
		final float step = 0.95f / nbFrames;
		Array<Float> tmp = new Array<Float>();
		while (cpt < nbFrames) {
			tmp.add(CSG.gm.palette().convertARGB(1, b, g / div, 1));
			g -= step;
			cpt++;
		}
		return CSG.convert(tmp);
	}
	
	private static float[] initAlphasCyanToGreen(float step, float min, boolean white) {
		float alpha = 1;
		Array<Float> tmp = new Array<Float>();
		if (white) {
			tmp.add(CSG.gm.palette().convertARGB(1, 0.95f, 1, 1));
			tmp.add(CSG.gm.palette().convertARGB(1, 0.55f, 1, 1));
			tmp.add(CSG.gm.palette().convertARGB(1, 0.25f, 1, 1));
		}
		while (alpha > min) {
			tmp.add(CSG.gm.palette().convertARGB(alpha, 0.25f, 1, alpha));
			alpha -= step;
		}
		return CSG.convert(tmp);
	}
	
	private static float[] initAlphasYellowToGreen(float step, float min, boolean white) {
		float alpha = 1;
		Array<Float> tmp = new Array<Float>();
		if (white) {
			tmp.add(CSG.gm.palette().convertARGB(1, 1, 1, 0.95f));
			tmp.add(CSG.gm.palette().convertARGB(1, 1, 1, 0.55f));
			tmp.add(CSG.gm.palette().convertARGB(1, 1, 1, 0.25f));
		}
		while (alpha > min) {
			tmp.add(CSG.gm.palette().convertARGB(alpha, Math.min(1, alpha * 2), 1, 0.25f));
			alpha -= step;
		}
		return CSG.convert(tmp);
	}
	
	private static float[] initAlphasThruster(float step, float min, boolean white) {
		float alpha = 1;
		Array<Float> tmp = new Array<Float>();
		if (white) {
			tmp.add(CSG.gm.palette().convertARGB(1, 0.95f, 1, 1));
			tmp.add(CSG.gm.palette().convertARGB(1, 0.50f, 1, 1));
			tmp.add(CSG.gm.palette().convertARGB(1, 0.25f, 1, 1));
			tmp.add(CSG.gm.palette().convertARGB(1, 0.12f, 1, 1));
		}
		while (alpha > min) {
			tmp.add(CSG.gm.palette().convertARGB(alpha, 0.12f, 1 + alpha/2, 1));
			alpha -= step;
		}
		return CSG.convert(tmp);
	}
	
	private static float[] initAlphasBlue(float step, float min, boolean white) {
		float alpha = 1;
		Array<Float> tmp = new Array<Float>();
		if (white) {
			tmp.add(CSG.gm.palette().convertARGB(1, 0.95f, 1, 1));
			tmp.add(CSG.gm.palette().convertARGB(1, 0.50f, 1, 1));
			tmp.add(CSG.gm.palette().convertARGB(1, 0.25f, 1, 1));
		}
		while (alpha > min) {
			tmp.add(CSG.gm.palette().convertARGB(alpha, 0.25f, Math.min(1, alpha*2), 1));
			alpha -= step;
		}
		return CSG.convert(tmp);
	}

	private static float[] initAlphasRed(float step, float min, boolean white) {
		float alpha = 1;
		Array<Float> tmp = new Array<Float>();
		if (white) {
			tmp.add(CSG.gm.palette().convertARGB(1, 1, 1, 0.95f));
			tmp.add(CSG.gm.palette().convertARGB(1, 1, 1, 0.50f));
			tmp.add(CSG.gm.palette().convertARGB(1, 1, 1, 0.25f));
		}
		while (alpha > min) {
			tmp.add(CSG.gm.palette().convertARGB(alpha, 1, Math.min(1, alpha * 2), 0.05f));
			alpha -= step;
		}
		return CSG.convert(tmp);
	}
	
}
