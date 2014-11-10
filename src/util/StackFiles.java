/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 * @author djordje
 */
public class StackFiles {

    static String path = "c:\\Users\\Djordje\\Dropbox\\eksperimenti\\GCRFdjole_final\\pctdata\\rain_non_missing\\";
    static int numberOfTimesteps = 4;
    static int numberOfPredictors=3;

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        ArrayList<ArrayList<String>> files = new ArrayList<>();
        for (int pred = 1; pred <= numberOfPredictors; pred++) {
            ArrayList<String> ithPredictor = new ArrayList<>();
            for (int ts = 1; ts <= numberOfTimesteps; ts++) {
                ithPredictor.add(path + "\\\\podaci_predictor_"+pred+"_ts" + ts);
            }
            files.add(ithPredictor);
        }
        
        BufferedReader br = null;

        for (int i = 0; i < files.size(); i++) {
            PrintWriter writer = new PrintWriter(path + "/podaci_predictor_" + (i + 1) + "_tsall.csv", "UTF-8");
            System.out.println("Starting to write to file: " + path + "/podaci_predictor_" + (i + 1) + "_tsall.csv");
            for (int j = 0; j < files.get(i).size(); j++) {
                System.out.println("    + Reading data from file: " + files.get(i).get(j));
                br = new BufferedReader(new FileReader(files.get(i).get(j)));

                String line = br.readLine();
                while (line != null) {
                    if (!line.equals("") && line != null) {
                        writer.println(line);
                        writer.flush();
                    }
                    line = br.readLine();
                }
                System.out.println("    - Finished reading data from file" + files.get(i).get(j));
                br.close();
            }
            writer.close();
        }
    }
}
