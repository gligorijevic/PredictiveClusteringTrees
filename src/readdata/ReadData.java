package readdata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import pct.BuildPCT;

/**
 *
 * @author Djordje
 */
public class ReadData {

    public static void readData(String dataurl, String splitter, BuildPCT buildPCT,int  M, int N) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(dataurl));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            //String[] firstrow = line.trim().split(splitter);
            // [N M] yi yj
            //int M = Integer.parseInt(firstrow[1]);
            //int N = Integer.parseInt(firstrow[0]);
            System.out.println("This dataset contains: " + M + " columns - " + N + " rows.");

            double[][] data = new double[N][M];
            double[][] output = new double[N][2];

            int datarow = 0;

            //line = br.readLine();
            while (line != null) {

                String[] row = line.trim().split(" ");
                for (int i = 0; i < M; i++) {
                    data[datarow][i] = Double.parseDouble(row[i]);
                }
                output[datarow][0] = Double.parseDouble(row[M]);
                output[datarow][1] = Double.parseDouble(row[M + 1]);
                datarow++;

                line = br.readLine();
            }

            buildPCT.setDataset(data);
            buildPCT.setTargetVariables(output);

        } finally {
            br.close();
        }
    }

}
