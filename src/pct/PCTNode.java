package pct;

import java.util.ArrayList;
import java.util.List;
import splittingcriteria.RegressionError;
import splittingcriteria.SplittingCandidate;

/**
 *
 * @author Djordje
 */
public class PCTNode {

    private int clusterName;
    private double[][] dataset;
    private double[][] targetVariables;
    private double[][] globalPrototype;
    private ArrayList<SplittingCandidate> splittingCandidates;
    private boolean isLeaf;
    private List<PCTNode> successors;
    private PCTNode predecessor;
    private double regressionError;
    private double targetVariance;

    private int splittingRuleColumn;
    private double splittingRuleValue;

    public PCTNode() {
        successors = new ArrayList<>();
        splittingCandidates = new ArrayList<>();
    }

    public PCTNode(double[][] dataset, double[][] targetVariables, double[][] globalPrototype, PCTNode predecessor) {
        this.dataset = dataset;
        this.targetVariables = targetVariables;
        this.predecessor = predecessor;
        splittingCandidates = new ArrayList<>();
        successors = new ArrayList<>();
        this.globalPrototype = globalPrototype;
        regressionError = RegressionError.calculateRegressionError(targetVariables, globalPrototype);
    }

    /**
     * @return the isLeaf
     */
    public boolean isIsLeaf() {
        return isLeaf;
    }

    /**
     * @param isLeaf the isLeaf to set
     */
    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * @return the successors
     */
    public List<PCTNode> getSuccessors() {
        return successors;
    }

    /**
     * @param successors the successors to set
     */
    public void setSuccessors(List<PCTNode> successors) {
        this.successors = successors;
    }

    /**
     * @return the predecessor
     */
    public PCTNode getPredecessor() {
        return predecessor;
    }

    /**
     * @param predecessor the predecessor to set
     */
    public void setPredecessor(PCTNode predecessor) {
        this.predecessor = predecessor;
    }

    /**
     * @return the regressionError
     */
    public double getRegressionError() {
//        regressionError = RegressionError.calculateRegressionError(targetVariables, globalPrototype);
        return regressionError;
    }

    /**
     * @param regressionError the regressionError to set
     */
    public void setRegressionError(double regressionError) {
        this.regressionError = regressionError;
    }

    //TODO
    public void testExample() {
    }

    /**
     * @return the dataset
     */
    public double[][] getDataset() {
        return dataset;
    }

    /**
     * @param dataset the dataset to set
     */
    public void setDataset(double[][] dataset) {
        this.dataset = dataset;
    }

    /**
     * @return the targetVariables
     */
    public double[][] getTargetVariables() {
        return targetVariables;
    }

    /**
     * @param targetVariables the targetVariables to set
     */
    public void setTargetVariables(double[][] targetVariables) {
        this.targetVariables = targetVariables;
    }

    /**
     * @return the splittingCandidates
     */
    public ArrayList<SplittingCandidate> getSplittingCandidates() {
        return splittingCandidates;
    }

    /**
     * @param splittingCandidates the splittingCandidates to set
     */
    public void setSplittingCandidates(ArrayList<SplittingCandidate> splittingCandidates) {
        this.splittingCandidates = splittingCandidates;
    }

    /**
     * @return the targetVariance
     */
    public double getTargetVariance() {
        return targetVariance;
    }

    /**
     * @param targetVariance the targetVariance to set
     */
    public void setTargetVariance(double targetVariance) {
        this.targetVariance = targetVariance;
    }

    /**
     * @return the clusterName
     */
    public int getClusterName() {
        return clusterName;
    }

    /**
     * @param clusterName the clusterName to set
     */
    public void setClusterName(int clusterName) {
        this.clusterName = clusterName;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the globalPrototype
     */
    public double[][] getGlobalPrototype() {
        return globalPrototype;
    }

    /**
     * @param globalPrototype the globalPrototype to set
     */
    public void setGlobalPrototype(double[][] globalPrototype) {
        this.globalPrototype = globalPrototype;
    }

    /**
     * @return the splittingRuleColumn
     */
    public int getSplittingRuleColumn() {
        return splittingRuleColumn;
    }

    /**
     * @param splittingRuleColumn the splittingRuleColumn to set
     */
    public void setSplittingRuleColumn(int splittingRuleColumn) {
        this.splittingRuleColumn = splittingRuleColumn;
    }

    /**
     * @return the splittingRuleValue
     */
    public double getSplittingRuleValue() {
        return splittingRuleValue;
    }

    /**
     * @param splittingRuleValue the splittingRuleValue to set
     */
    public void setSplittingRuleValue(double splittingRuleValue) {
        this.splittingRuleValue = splittingRuleValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PCTNode) {
            return ((PCTNode) obj).getSplittingRuleColumn() == this.splittingRuleColumn
                    && ((PCTNode) obj).getSplittingRuleValue() == this.splittingRuleValue
                    && ((PCTNode) obj).getTargetVariance() == this.targetVariance;
        }
        return false;
    }

}
