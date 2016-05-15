/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Antonio de la Vega
 */
public class DataProcessor {

    private final BufferedReader fileReader;

    // Sirve para computadores con 4 cores fisicos y tiempo entre mediciones de 1 segundo.
    public void ohm(String ramPath, String cpuPath) throws IOException {
        try (PrintWriter pwRam = new PrintWriter(new File(ramPath));
                PrintWriter pwCpu = new PrintWriter(new File(cpuPath))) {
            fileReader.readLine();
            String head[] = fileReader.readLine().split(",");
            System.out.println(head[5] + " " + head[20]);
            String line = "";
            int cpu = -1;
            int cpuTime = 1;
            int ram = -1;
            int ramTime = 1;
            while ((line = fileReader.readLine()) != null) {
                String[] data = line.split(",");
                int tempCpu = (int) (Double.parseDouble(data[5]) + 0.5);
                int tempRam = (int) (Double.parseDouble(data[20]) + 0.5);
                if (cpu == tempCpu) {
                    cpuTime++;
                } else if (cpu != -1 && cpu != tempCpu) {
                    pwCpu.println(cpuTime + "," + cpu + "," + cpu + "," + cpu + "," + cpu + "," + cpu + "," + cpu + "," + cpu + "," + cpu);
                    pwCpu.flush();
                    cpuTime = 1;
                }

                if (ram == tempRam) {
                    ramTime++;
                } else if (ram != -1 && ram != tempRam) {
                    pwRam.println(ramTime + "," + ram);
                    pwRam.flush();
                    ramTime = 1;
                }
                ram = tempRam;
                cpu = tempCpu;
            }
        }
    }

    public DataProcessor(String path) throws FileNotFoundException {
        fileReader = new BufferedReader(new FileReader(new File(path)));
    }
}
