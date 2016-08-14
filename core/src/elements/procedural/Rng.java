package elements.procedural;

import java.util.Random;

/**
 * Created by julein on 12/08/16.
 */
public class Rng {

    private final long seed;
    private final Random rand;

    public Rng(long seed) {
        this.seed = seed;
        this.rand = new Random(seed);
    }

    public int gaussianFlooredScaler(float mul) { return (int)(Math.abs(rand.nextGaussian()) * mul); }
    public int intBetween(int min, int max) { return min + rand.nextInt(max); }
    public int anInt(int max) { return rand.nextInt(max); }
    public boolean aBoolean() { return rand.nextBoolean(); }
    public float aFloat() { return rand.nextFloat(); }
}
