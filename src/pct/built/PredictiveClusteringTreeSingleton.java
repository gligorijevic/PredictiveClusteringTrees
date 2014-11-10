/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pct.built;

import pct.PCTNode;

/**
 *
 * @author djordje
 */
public class PredictiveClusteringTreeSingleton {
    
    private PCTNode predictiveClusteringTree;
    
    private PredictiveClusteringTreeSingleton() {
    }
    
    public static PredictiveClusteringTreeSingleton getInstance() {
        return PredictiveClusteringTreeSingletonHolder.INSTANCE;
    }

    /**
     * @return the predictiveClusteringTree
     */
    public PCTNode getPredictiveClusteringTree() {
        return predictiveClusteringTree;
    }

    /**
     * @param predictiveClusteringTree the predictiveClusteringTree to set
     */
    public void setPredictiveClusteringTree(PCTNode predictiveClusteringTree) {
        this.predictiveClusteringTree = predictiveClusteringTree;
    }
    
    private static class PredictiveClusteringTreeSingletonHolder {

        private static final PredictiveClusteringTreeSingleton INSTANCE = new PredictiveClusteringTreeSingleton();
    }
    
    
    
}
