package splittingcriteria;

/**
 *
 * @author Djordje
 */
public class RegressionError {

    public static double calculateRegressionError(double[][] targetVariables, double[][] prototype) {
        double regressionError = 0;

        double[][] localPrototype = FindPrototype.findOptimalPrototype(targetVariables);

        double numerator = calculateEuclideanDistance(targetVariables, localPrototype);

        double divisor = 0;

        for (int i = 0; i < targetVariables.length; i++) {
            divisor += Math.pow((targetVariables[i][0] - prototype[0][0]), 2)
                    + Math.pow((targetVariables[i][1] - prototype[0][1]), 2);
        }

        if (divisor == 0) {
            return 0;
        } else {
            return numerator / divisor;
        }
    }

    private static double calculateEuclideanDistance(double[][] targetVariables, double[][] localPrototype) { //ovo je prototype.
        double euclideanDistance = 0;
        for (int i = 0; i < targetVariables.length; i++) {
            euclideanDistance
                    += Math.pow((targetVariables[i][0] - localPrototype[0][0]), 2)
                    + Math.pow((targetVariables[i][1] - localPrototype[0][1]), 2);
        }
        return euclideanDistance;
    }

}
