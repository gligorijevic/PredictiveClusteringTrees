/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readdata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import pct.PCTNode;
import pct.built.PredictiveClusteringTreeSingleton;

/**
 *
 * @author Djordje
 */
public class ReadTestData {

    static double[][] data;

    public static void readDataAndInfer(String testDataPath, String separator, String outputFile,int  M, int N) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(testDataPath));
        PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
//        writer.println("The first line");
//        writer.println("The second line");

        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
//            String[] firstrow = line.trim().split(separator);
            // [N M] yi yj
//            int M = Integer.parseInt(firstrow[1]);
//            int N = Integer.parseInt(firstrow[0]);
            System.out.println("This test set contains: " + M + " columns - " + N + " rows.");
            data = new double[1][M];
//            double[][] output = new double[N][2];

//            line = br.readLine();
            while (line != null) {

                String[] row = line.trim().split(" ");
                for (int i = 0; i < M; i++) {
                    data[0][i] = Double.parseDouble(row[i]);
                }
                writer.println(predictCluster(data));

                line = br.readLine();
            }
        } finally {
            writer.close();
            br.close();
        }
    }

    private static String predictCluster(double[][] data) {
        StringBuilder builder = new StringBuilder();

        PCTNode rootNode = PredictiveClusteringTreeSingleton.getInstance().getPredictiveClusteringTree();
        while (!rootNode.getSuccessors().isEmpty()) {
            if (data[0][rootNode.getSplittingRuleColumn()] > rootNode.getSplittingRuleValue()) {
                rootNode = rootNode.getSuccessors().get(1);
            } else {
                rootNode = rootNode.getSuccessors().get(0);
            }
        }
        builder.append(rootNode.getClusterName());

        return builder.toString();
    }

}
