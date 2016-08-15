package elements.procedural;

/**
 * Created by julein on 11/08/16.
 */
public class Steps {

    public final int minSteps, maxSteps, minSubStep,maxSubSteps;

    public Steps(int minSteps, int maxSteps, int minSubStep, int maxSubSteps) {
        this.minSteps = minSteps;
        this.maxSteps = maxSteps;
        this.minSubStep = minSubStep;
        this.maxSubSteps = maxSubSteps;
    }
}
