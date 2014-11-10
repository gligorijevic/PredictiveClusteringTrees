package pct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import pct.built.PredictiveClusteringTreeSingleton;
import splittingcriteria.FindPrototype;
import splittingcriteria.RegressionError;
import splittingcriteria.SplittingCandidate;
import util.matrix.Gets;

/**
 *
 * @author Djordje
 */
public class BuildPCT {

    private PCTNode rootNode;
    private double[][] dataset;
    private double[][] targetVariables;
    private final ArrayList<SplittingCandidate> splittingCandidatesInColumns;
    private final ArrayList<SplittingCandidate> splittingCandidates;
    static int numberOfClusters;
    static int currentNumberOfClusters = 0;

    public BuildPCT(int numberOfClusters) {
        BuildPCT.numberOfClusters = numberOfClusters;
        splittingCandidatesInColumns = new ArrayList<>();
        splittingCandidates = new ArrayList<>();
    }

    /**
     * @return the rootNode
     */
    public PCTNode getRootNode() {
        return rootNode;
    }

    /**
     *
     */
    public void setRootNode(PCTNode rootNode) {
        if (rootNode == null) {
            this.rootNode = new PCTNode();
            this.rootNode.setDataset(dataset);
            this.rootNode.setIsLeaf(false);
            this.rootNode.setTargetVariables(targetVariables);
            double[][] globalPrototype = FindPrototype.findOptimalPrototype(targetVariables);
            this.rootNode.setRegressionError(RegressionError.calculateRegressionError(targetVariables, globalPrototype));
            PredictiveClusteringTreeSingleton.getInstance().setPredictiveClusteringTree(rootNode);
        } else {
            this.rootNode = rootNode;
        }
        System.out.println(" ~ Root node has been set, training is initialized...");
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

    public PCTNode inducePredictiveClusteringTree() throws Exception {
        System.out.println("Begin induction of Predictive Clustering Tree...");
        //Iterate over all attributes to determine the split
        System.out.println("Finding the best splitting criterium...");
        for (int i = 0; i < dataset[0].length; i++) {
            System.out.println("    + Checking the " + i + ". column for splitting criterium.");
            try {
                splittingCandidates.add(figureOutSplittingCriterium(i));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                continue;
            }
            splittingCandidatesInColumns.clear();
        }

        if (splittingCandidates.isEmpty()) {
            throw new Exception("No splitting candidate.");
//            return rootNode;
        }

        System.out.println("");
         System.out.println("Finding the best split...\n");
        double minRegressionError = splittingCandidates.get(0).getSplitRegressionError();
        int bestSplit = 0;
        for (int i = 0; i < splittingCandidates.size(); i++) {
            System.out.println(" - Split by column " + i + ". has minimum variance of " + splittingCandidates.get(i).getSplitRegressionError());
            if (splittingCandidates.get(i).getSplitRegressionError() < minRegressionError) {
                minRegressionError = splittingCandidates.get(i).getSplitRegressionError();
                bestSplit = i;
            }
        }
        System.out.println(" * The best split chosen for root node is by " + bestSplit + ". column. The condition value there is " + splittingCandidates.get(bestSplit).getSplittingRuneValue());
        SplittingCandidate bestSplittingCandidate = splittingCandidates.get(bestSplit);

        System.out.println("Creating children of Predictive Clustering Tree's root node...");
        rootNode.setSplittingRuleColumn(bestSplittingCandidate.getSplittingRuleColumn());
        rootNode.setSplittingRuleValue(bestSplittingCandidate.getSplittingRuneValue());
        System.out.println("Doing the binary splitting...");
        doSplit(bestSplittingCandidate);

        splittingCandidates.clear();
        splittingCandidatesInColumns.clear();
        return rootNode;
    }

    @Deprecated
    public void splitDataset(ArrayList<Integer> indicies, SplittingCandidate splittingCandidate) {
        double[][] dataset1 = new double[dataset[0].length][indicies.size()];
        double[][] dataset2 = new double[dataset[0].length][dataset.length - indicies.size()];

        double[][] targetVariables1 = new double[targetVariables[0].length][indicies.size()];
        double[][] targetVariables2 = new double[targetVariables[0].length][targetVariables.length - indicies.size()];

        for (int i = 0; i < dataset.length; i++) {
            if (indicies.contains(i)) {
                dataset1[i] = dataset[i];
            } else {
                dataset2[i] = dataset[i];
            }
        }

        for (int i = 0; i < targetVariables.length; i++) {
            if (indicies.contains(i)) {
                targetVariables1[i] = targetVariables[i];
            } else {
                targetVariables2[i] = targetVariables[i];
            }
        }
    }

    /**
     *
     * Splits at <splittingCriteria and >=splittingCriteria
     *
     * @param columnIndex
     * @param splittingCriteria
     * @return
     */
    public SplittingCandidate splitDataset(int columnIndex, double splittingCriteria) {
        int examplesForDataset1 = 0;
        int examplesForDataset2 = 0;

        for (double[] dataset1 : dataset) {
            if (dataset1[columnIndex] > splittingCriteria) {
                examplesForDataset2++;
            } else {
                examplesForDataset1++;
            }
        }
//        double[][] dataset1 = new double[examplesForDataset1][dataset[0].length];
//        double[][] dataset2 = new double[examplesForDataset2][dataset[0].length];
//
        double[][] targetVariables1 = new double[examplesForDataset1][targetVariables[0].length];
        double[][] targetVariables2 = new double[examplesForDataset2][targetVariables[0].length];

        int filledRowOfDataset1 = 0;
        int filledRowOfDataset2 = 0;
        for (int i = 0; i < dataset.length; i++) {
            if (dataset[i][columnIndex] > splittingCriteria) {
//                dataset2[filledRowOfDataset2] = dataset[i];
                targetVariables2[filledRowOfDataset2] = targetVariables[i];
                filledRowOfDataset2++;
            } else {
//                dataset1[filledRowOfDataset1] = dataset[i];
                targetVariables1[filledRowOfDataset1] = targetVariables[i];
                filledRowOfDataset1++;
            }
        }

        double[][] globalPrototype = FindPrototype.findOptimalPrototype(targetVariables);
        return new SplittingCandidate(targetVariables1, targetVariables2, globalPrototype, columnIndex, splittingCriteria);
    }

    private SplittingCandidate figureOutSplittingCriterium(int columnNo) throws Exception {
        double splittingValue;
        double[] column = Gets.getColumnFromMatrix(columnNo, dataset);
        Arrays.sort(column);

        ArrayList<Integer> indexesOfNewVals = new ArrayList<>();
        double val = column[0];
        for (int i = 1; i < column.length; i++) {
            if (val < column[i]) {
                val = column[i];
                indexesOfNewVals.add(i);
            }
        }

        System.out.println("        - There are " + indexesOfNewVals.size() + " of unique values in " + columnNo + ". column.");
        System.out.println("");

        SplittingCandidate splittingCandidate = null;

        System.out.println("        - Attempting various splits for " + columnNo + ". column.");
        for (int j = 0; j < indexesOfNewVals.size(); j++) {
            splittingValue = 0.5 * (column[j] + column[j + 1]);
//            System.out.println("        - Attempt to split by value: " + splittingValue);
            splittingCandidate = splitDataset(columnNo, splittingValue);
            splittingCandidatesInColumns.add(splittingCandidate);
        }

        if (splittingCandidatesInColumns.isEmpty()) {
            throw new Exception("### There is no splitting candidates here, go for next column.");
        }

        double minRegressionError = splittingCandidatesInColumns.get(0).getSplitRegressionError();
        int bestSplit = 0;
        System.out.println("        Finding the best split for" + columnNo + ". by it's regression error.");
        for (int i = 0; i < splittingCandidatesInColumns.size(); i++) {
//            System.out.println("                Regression error for " + i + ". split is: " + splittingCandidatesInColumns.get(i).getSplitRegressionError());
            if (splittingCandidatesInColumns.get(i).getSplitRegressionError() < minRegressionError) {
                minRegressionError = splittingCandidatesInColumns.get(i).getSplitRegressionError();
                bestSplit = i;
            }
        }
        System.out.println("        The chosen split is by " + bestSplit + ". split.");
        return splittingCandidatesInColumns.get(bestSplit);

    }

    @Deprecated
    private void doHalfSplit(SplittingCandidate bestSplittingCandidate) {
        PCTNode leftChild = new PCTNode();
        if (bestSplittingCandidate.getDataset1Variance() <= bestSplittingCandidate.getDataset2Variance()) {
            leftChild.setDataset(bestSplittingCandidate.getDataset1());
            leftChild.setTargetVariables(bestSplittingCandidate.getTargetVariables1());
            leftChild.setPredecessor(rootNode);
            leftChild.setRegressionError(bestSplittingCandidate.getDataset1RegressionError());
            leftChild.setTargetVariance(bestSplittingCandidate.getDataset1Variance());
        } else {
            PCTNode rightChild = new PCTNode();
            leftChild.setDataset(bestSplittingCandidate.getDataset2());
            leftChild.setTargetVariables(bestSplittingCandidate.getTargetVariables2());
            leftChild.setPredecessor(rootNode);
            leftChild.setRegressionError(bestSplittingCandidate.getDataset2RegressionError());
            leftChild.setTargetVariance(bestSplittingCandidate.getDataset2Variance());
        }
        ArrayList<PCTNode> children = new ArrayList<>();
        children.add(leftChild);

        rootNode.setSuccessors(children);
        currentNumberOfClusters += 1;
    }

    private void doSplit(SplittingCandidate bestSplittingCandidate) {
        PCTNode leftChild = new PCTNode();

        int examplesForDataset2 = 0;
        int examplesForDataset1 = 0;

        for (double[] dataset1 : dataset) {
            if (dataset1[bestSplittingCandidate.getSplittingRuleColumn()] > bestSplittingCandidate.getSplittingRuneValue()) {
                examplesForDataset2++;
            } else {
                examplesForDataset1++;
            }
        }

        double[][] dataset1 = new double[examplesForDataset1][dataset[0].length];
        double[][] dataset2 = new double[examplesForDataset2][dataset[0].length];

        double[][] targetVariables1 = new double[examplesForDataset1][targetVariables[0].length];
        double[][] targetVariables2 = new double[examplesForDataset2][targetVariables[0].length];

        int filledRowOfDataset1 = 0;
        int filledRowOfDataset2 = 0;
        for (int i = 0; i < dataset.length; i++) {
            if (dataset[i][bestSplittingCandidate.getSplittingRuleColumn()] > bestSplittingCandidate.getSplittingRuneValue()) {
                dataset2[filledRowOfDataset2] = dataset[i];
                targetVariables2[filledRowOfDataset2] = targetVariables[i];
                filledRowOfDataset2++;
            } else {
                dataset1[filledRowOfDataset1] = dataset[i];
                targetVariables1[filledRowOfDataset1] = targetVariables[i];
                filledRowOfDataset1++;
            }
        }

        leftChild.setDataset(dataset1);
        leftChild.setTargetVariables(targetVariables1);
        leftChild.setPredecessor(rootNode);
        leftChild.setRegressionError(bestSplittingCandidate.getDataset1RegressionError());
        leftChild.setTargetVariance(bestSplittingCandidate.getDataset1Variance());

        PCTNode rightChild = new PCTNode();
        rightChild.setDataset(dataset2);
        rightChild.setTargetVariables(targetVariables2);
        rightChild.setPredecessor(rootNode);
        rightChild.setRegressionError(bestSplittingCandidate.getDataset2RegressionError());
        rightChild.setTargetVariance(bestSplittingCandidate.getDataset2Variance());

        ArrayList<PCTNode> children = new ArrayList<>();
        children.add(leftChild);
        children.add(rightChild);

        rootNode.setSuccessors(children);
        currentNumberOfClusters += 2;
    }
}
