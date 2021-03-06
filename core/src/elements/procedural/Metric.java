package elements.procedural;

/**
 * Created by julein on 11/08/16.
 */
public class Metric {
    long total = 0, start = 0;

    public void start() {
        start = System.currentTimeMillis();
    }

    public void end() {
        total += System.currentTimeMillis() - start;
    }

    @Override
    public String toString() {
        return " = " + total;
    }
}
