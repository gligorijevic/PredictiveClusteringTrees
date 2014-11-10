/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splittingcriteria;

/**
 *
 * @author djordje
 */
public class SplittingCandidate {

    private int splittingRuleColumn;
    private double splittingRuleValue;

    private double[][] dataset1;
    private double[][] dataset2;

    private double[][] targetVariables1;
    private double[][] targetVariables2;

    private double dataset1Variance;
    private double dataset2Variance;

    private double[][] globalPrototype;
    private double splitRegressionError;
    private double dataset1RegressionError;
    private double dataset2RegressionError;

    public SplittingCandidate(double[][] dataset1, double[][] dataset2, double[][] targetVariables1,
            double[][] targetVariables2, double[][] globalPrototype, int splittingRuleColumn, double splittingRuleValue) {
        this.dataset1 = dataset1;
        this.dataset2 = dataset2;
        this.targetVariables1 = targetVariables1;
        this.targetVariables2 = targetVariables2;
        this.dataset1Variance = CalculateVariance.calculateVariance(targetVariables1);
        this.dataset2Variance = CalculateVariance.calculateVariance(targetVariables2);
        this.globalPrototype = globalPrototype;
        this.dataset1RegressionError = RegressionError.calculateRegressionError(targetVariables1, globalPrototype);
        this.dataset2RegressionError = RegressionError.calculateRegressionError(targetVariables2, globalPrototype);
        this.splittingRuleColumn = splittingRuleColumn;
        this.splittingRuleValue = splittingRuleValue;

        int dataset1size = dataset1.length;
        int dataset2size = dataset2.length;

        this.splitRegressionError = 1.0 * dataset1size / (dataset1size + dataset2size) * dataset1RegressionError
                + 1.0 * dataset2size / (dataset1size + dataset2size) * dataset2RegressionError;
    }

    public SplittingCandidate(double[][] targetVariables1,double[][] targetVariables2, 
            double[][] globalPrototype, int splittingRuleColumn, double splittingRuleValue) {
        this.targetVariables1 = targetVariables1;
        this.targetVariables2 = targetVariables2;
        this.dataset1Variance = CalculateVariance.calculateVariance(targetVariables1);
        this.dataset2Variance = CalculateVariance.calculateVariance(targetVariables2);
        this.globalPrototype = globalPrototype;
        this.dataset1RegressionError = RegressionError.calculateRegressionError(targetVariables1, globalPrototype);
        this.dataset2RegressionError = RegressionError.calculateRegressionError(targetVariables2, globalPrototype);
        this.splittingRuleColumn = splittingRuleColumn;
        this.splittingRuleValue = splittingRuleValue;

        int dataset1size = targetVariables1.length;
        int dataset2size = targetVariables2.length;

        this.targetVariables1 = null;
        this.targetVariables2 = null;

        this.splitRegressionError = 1.0 * dataset1size / (dataset1size + dataset2size) * dataset1RegressionError
                + 1.0 * dataset2size / (dataset1size + dataset2size) * dataset2RegressionError;
    }

    /**
     * @return the dataset1
     */
    public double[][] getDataset1() {
        return dataset1;
    }

    /**
     * @param dataset1 the dataset1 to set
     */
    public void setDataset1(double[][] dataset1) {
        this.setDataset1(dataset1);
    }

    /**
     * @return the dataset2
     */
    public double[][] getDataset2() {
        return dataset2;
    }

    /**
     * @param dataset2 the dataset2 to set
     */
    public void setDataset2(double[][] dataset2) {
        this.setDataset2(dataset2);
    }

    /**
     * @return the targetVariables1
     */
    public double[][] getTargetVariables1() {
        return targetVariables1;
    }

    /**
     * @param targetVariables1 the targetVariables1 to set
     */
    public void setTargetVariables1(double[][] targetVariables1) {
        this.setTargetVariables1(targetVariables1);
    }

    /**
     * @return the targetVariables2
     */
    public double[][] getTargetVariables2() {
        return targetVariables2;
    }

    /**
     * @param targetVariables2 the targetVariables2 to set
     */
    public void setTargetVariables2(double[][] targetVariables2) {
        this.setTargetVariables2(targetVariables2);
    }

    /**
     * @return the dataset1Variance
     */
    public double getDataset1Variance() {
        return dataset1Variance;
    }

    /**
     * @param dataset1Variance the dataset1Variance to set
     */
    public void setDataset1Variance(double dataset1Variance) {
        this.dataset1Variance = dataset1Variance;
    }

    /**
     * @return the dataset2Variance
     */
    public double getDataset2Variance() {
        return dataset2Variance;
    }

    /**
     * @param dataset2Variance the dataset2Variance to set
     */
    public void setDataset2Variance(double dataset2Variance) {
        this.dataset2Variance = dataset2Variance;
    }

    /**
     * @return the dataset1RegressionError
     */
    public double getDataset1RegressionError() {
        return dataset1RegressionError;
    }

    /**
     * @param dataset1RegressionError the dataset1RegressionError to set
     */
    public void setDataset1RegressionError(double dataset1RegressionError) {
        this.dataset1RegressionError = dataset1RegressionError;
    }

    /**
     * @return the dataset2RegressionError
     */
    public double getDataset2RegressionError() {
        return dataset2RegressionError;
    }

    /**
     * @param dataset2RegressionError the dataset2RegressionError to set
     */
    public void setDataset2RegressionError(double dataset2RegressionError) {
        this.dataset2RegressionError = dataset2RegressionError;
    }

    /**
     * @return the splitRegressionError
     */
    public double getSplitRegressionError() {
        return splitRegressionError;
    }

    /**
     * @param splitRegressionError the splitRegressionError to set
     */
    public void setSplitRegressionError(double splitRegressionError) {
        this.splitRegressionError = splitRegressionError;
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
     * @return the splittingRuneValue
     */
    public double getSplittingRuneValue() {
        return splittingRuleValue;
    }

    /**
     * @param splittingRuneValue the splittingRuneValue to set
     */
    public void setSplittingRuneValue(double splittingRuneValue) {
        this.splittingRuleValue = splittingRuneValue;
    }

}
