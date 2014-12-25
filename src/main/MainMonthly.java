package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import pct.BuildPCT;
import pct.PCTNode;
import pct.built.PredictiveClusteringTreeSingleton;
import readdata.ReadTestData;
import static util.tree.TreeUtils.GetAllLeafs;
import static util.tree.TreeUtils.numberOfLeafs;

/**
 *
 * @author Djordje
 */
public class MainMonthly {

    static String dataPath;
    static String testDataPath;
    static String outputFilePathTest;
    static String splitter;
    static String variancesOutputTest;
    static String outputFilePathTraining;
    static String variancesOutputTraining;
    static int numberOfClusters;
    static int M;
    static int Mpred;
    static int N;
    static int N_pred_all;
    static int N_malo;
    static int Npred_malo;
    static String path;
    static int numberOfPredictors;

    public static void main(String[] args) throws IOException {

        path = "F:\\MATLAB\\GCRF for heterogeneous data\\H-GCRF_HCUP_v2.1\\pctdata\\grid_prec_synth\\";

        loadProperties(path + "\\size.properties");

        Runtime rt = Runtime.getRuntime();
        long totalMem = rt.totalMemory();
        long maxMem = rt.maxMemory();
        long freeMem = rt.freeMemory();
        double megs = 1048576.0;

        System.err.println("Total Memory: " + totalMem + " (" + (totalMem / megs) + " MiB)");
        System.err.println("Max Memory:   " + maxMem + " (" + (maxMem / megs) + " MiB)");
        System.err.println("Free Memory:  " + freeMem + " (" + (freeMem / megs) + " MiB)");

        //za edge
        dataPath = path + "/podaci_ts1";
        int Mrd = M;
        int Nrd = N_malo;
        int Mtest = M;
        int Ntest = N;

        testDataPath = path + "/podaci_tsall.csv";
        outputFilePathTest = path + "/podaci_tsall_clusters.csv";
        variancesOutputTest = path + "/podaci_ts1_variances.csv";
        splitter = " ";
        numberOfClusters = 2;
        boolean treeIsBuilt = false;

        for (int t = 0; t < 12; t++) {

//       za prediktore zakomentarisi 
            ArrayList<ArrayList<String>> predictors = new ArrayList<>();
            for (int i = 1; i <= numberOfPredictors; i++) {
                ArrayList<String> ithPredoctor = new ArrayList<>();
                ithPredoctor.add(path + "/podaci_predictor_" + i + "_ts"+(t+1));
                ithPredoctor.add(path + "/podaci_predictor_" + i + "_ts"+(t+13)+".csv");
                predictors.add(ithPredoctor);
            }

            Mtest = Mpred;
            Ntest = N_malo; // nije: N_pred_all;
            
            
            for (int p = 0; p < predictors.size(); p++) {
                System.out.println("Training data on: " + predictors.get(p).get(0));
                dataPath = predictors.get(p).get(0);
                testDataPath = predictors.get(p).get(1);
                Mrd = Mpred;
                Nrd = Npred_malo;
                outputFilePathTraining = path + "/podaci_ts_training_precitor_" + (p + 1) + "_clusters.csv";
                variancesOutputTraining = path + "/podaci_ts_training_precitor_" + (p + 1) + "_variances.csv";
                outputFilePathTest = path + "/podaci_ts_test_precitor_" + (p + 1) + "_clusters.csv";
                variancesOutputTest = path + "/podaci_ts_test_precitor_" + (p + 1) + "_variances.csv";
                
//      za prediktore zakomentarisi iznad      
                BuildPCT buildPCT = new BuildPCT(numberOfClusters);

                System.out.println("Training data is being loaded...");

                readdata.ReadData.readData(dataPath, splitter, buildPCT, Mrd, Nrd);

                buildPCT.setRootNode(null);
                PCTNode rootnode;
                try {
                    rootnode = buildPCT.inducePredictiveClusteringTree();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    return;
                }

                PredictiveClusteringTreeSingleton.getInstance().setPredictiveClusteringTree(rootnode);

                System.out.println("We are now building the tree to fit the wanted number of clusters...");
                ArrayList<PCTNode> leafs = new ArrayList<>();

                int currentNumberOfClusters = numberOfLeafs(PredictiveClusteringTreeSingleton.getInstance().getPredictiveClusteringTree());
                GetAllLeafs(leafs, PredictiveClusteringTreeSingleton.getInstance().getPredictiveClusteringTree());

                System.out.println("Current number of clusters is " + currentNumberOfClusters);
                while (currentNumberOfClusters < numberOfClusters) {

                    double minRegressionError = leafs.get(0).getRegressionError();
                    int position = 0;
                    for (int i = 0; i < leafs.size(); i++) {
                        if (minRegressionError > leafs.get(i).getRegressionError()) {
                            if (leafs.get(i).getDataset().length >= 2) {
                                minRegressionError = leafs.get(i).getRegressionError();
                                position = i;
                            }
                        }
                    }

                    if (position == 0 && leafs.get(position).getDataset().length < 2) {
                        System.out.println("Tree cannot generate more clusters with this data!");
                        leafs.clear();
                        GetAllLeafs(leafs, PredictiveClusteringTreeSingleton.getInstance().getPredictiveClusteringTree());
                        break;
                    }

                    System.err.println(leafs.get(position));

                    buildPCT.setDataset(leafs.get(position).getDataset());
                    buildPCT.setTargetVariables(leafs.get(position).getTargetVariables());
                    buildPCT.setRootNode(leafs.get(position));
                    try {
                        buildPCT.inducePredictiveClusteringTree();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        break;
                    }

                    leafs.clear();
                    GetAllLeafs(leafs, PredictiveClusteringTreeSingleton.getInstance().getPredictiveClusteringTree());
                    currentNumberOfClusters = numberOfLeafs(PredictiveClusteringTreeSingleton.getInstance().getPredictiveClusteringTree());
                    System.out.println("Current number of clusters is " + currentNumberOfClusters);

                    ////// i odavde
                    if (numberOfClusters % numberOfLeafs(PredictiveClusteringTreeSingleton.getInstance().getPredictiveClusteringTree()) == 2) {
                        for (PCTNode pCTNode : leafs) {
                            buildPCT.setRootNode(pCTNode);
                            try {
                                buildPCT.inducePredictiveClusteringTree();
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                                break;
                            }
                        }
                    } else if (numberOfClusters % numberOfLeafs(PredictiveClusteringTreeSingleton.getInstance().getPredictiveClusteringTree()) == 2) {
                        if (numberOfLeafs(PredictiveClusteringTreeSingleton.getInstance().getPredictiveClusteringTree()) == numberOfClusters) {
                            treeIsBuilt = true;
                        }
                    }
//                //////do ovde
                }

                PrintWriter writer = new PrintWriter(variancesOutputTest, "UTF-8");
                for (int i = 0; i < leafs.size(); i++) {
                    leafs.get(i).setClusterName(i + 1);
                    writer.println(leafs.get(i).getClusterName() + ", " + leafs.get(i).getTargetVariance());
                }
                writer.close();

                System.out.println("Predictive Clustering Tree has been built.");

                System.out.println("\n INFERENCE TIME \n");

                ReadTestData.readDataAndInfer(dataPath, splitter, outputFilePathTraining, Mtest, Ntest); //Klasteri za trening
                ReadTestData.readDataAndInfer(testDataPath, splitter, outputFilePathTest, Mtest, Ntest); //Klasteri za predikciju
            }
        }
    } /////////ovu zakomentarisi

    public void buildPredictiveClusteringTree(PCTNode node) {
    }

    public static void loadProperties(String filePath) {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream(filePath);
            prop.load(input);

            // get the property value and print it out
            N = Integer.parseInt(prop.getProperty("tsall"));
            N_pred_all = Integer.parseInt(prop.getProperty("predictors_tsall"));
            M = Integer.parseInt(prop.getProperty("M"));
            Mpred = Integer.parseInt(prop.getProperty("Mpred"));
            N_malo = Integer.parseInt(prop.getProperty("Nmalo"));
            Npred_malo = Integer.parseInt(prop.getProperty("Npred_malo"));
            //path=prop.getProperty("filepath");
            numberOfPredictors = Integer.parseInt(prop.getProperty("numPred"));

//N_pred_all=prop.getProperty("Npred_all");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
