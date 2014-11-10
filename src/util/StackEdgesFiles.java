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
public class StackEdgesFiles {
    //static String path = "C:\\Users\\djordje\\Dropbox\\Matlab\\GCRFuncertainty_final\\pctdata\\grid400_same_dist";
    static String path = "c:\\Users\\Djordje\\Dropbox\\eksperimenti\\GCRFdjole_final\\pctdata\\rain_non_missing\\";
    static int numberOfTimesteps = 4;
    static int numberOfPredictors=3;
    
    
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        ArrayList<ArrayList<String>> files = new ArrayList<>();
        
        for (int pred = 1; pred <= numberOfPredictors; pred++) {
            ArrayList<String> edges = new ArrayList<>();
            for (int ts = 1; ts <= numberOfTimesteps; ts++) {
                edges.add(path + "\\\\podaci_ts" + ts);
            }
            files.add(edges);
        }
        

        BufferedReader br = null;
        PrintWriter writer = new PrintWriter(path+"/podaci_tsall.csv", "UTF-8");
        for (int i = 0; i < files.size(); i++) {
            
            System.out.println("Starting to write to file: " + path + "/podaci_tsall.csv");
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
        }
                    writer.close();

    }
}
