package splittingcriteria;

/**
 *
 * @author Djordje
 */
public class FindPrototype {
    
    public static double[][] findOptimalPrototype(double[][] targetVariables){
        double[][] prototype = new double[1][2];
        
        int N = targetVariables.length;
        
        for (int i = 0; i < N; i++) {
            prototype[0][0] += targetVariables[i][0];
            prototype[0][1] += targetVariables[i][1];
        }
        prototype[0][0] = prototype[0][0]/N;
        prototype[0][1] = prototype[0][1]/N;
       
        return prototype;
    }
    
}
