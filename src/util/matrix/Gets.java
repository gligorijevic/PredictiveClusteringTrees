/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.matrix;

/**
 *
 * @author djordje
 */
public class Gets {
 
    /**
     * 
     * Works only for two dimensional target Variables
     * 
     * @param rowId
     * @param matrix
     * @return 
     */
    public static double[][] getRowFromMatix(int rowId, double[][] matrix){
        double[][] row = new double[1][matrix[0].length];
        row[0][0] = matrix[rowId][0]; 
        row[0][1] = matrix[rowId][1];
        
        return row;
    }
    
    /**
     * 
     * Works only for two dimensional target Variables
     * 
     * @param colId
     * @param matrix
     * @return 
     */
    public static double[] getColumnFromMatrix(int colId, double[][] matrix){
        double[] column = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            column[i] = matrix[i][colId];
        }
        
        return column;
    }
    
    
}
