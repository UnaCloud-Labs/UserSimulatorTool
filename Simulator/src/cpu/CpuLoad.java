package cpu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Stresses all the CPUs on a machine to a given percent.
 * @author Antonio de la Vega
 */
public class CpuLoad {
    
    /**
     * Number of logical cores available on the machine.
     */
    private static final int CORES = Runtime.getRuntime().availableProcessors();

    /**
     * Path where the cpu stress file is located in.
     */
    private final String path;
    
    /**
     * Initializes all the fields before the cpu stressing starts.
     * @param path, path where the data file is located in.
     */
    public CpuLoad(String path){
        this.path = path;
    }

    /**
     * Loads the CPU to the desired value specified in the file for the 
     * specified number of seconds.
     * @throws InterruptedException
     * @throws IOException 
     */
    public void start() throws InterruptedException, IOException {
        
        try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {

            String data = "";

            SingleCpuStresser[] loads = new SingleCpuStresser[CORES];

            for (int i = 0; i < CORES; i++) {
                loads[i] = new SingleCpuStresser(0);
                loads[i].start();
            }

            while ((data = br.readLine()) != null) {
                String[] values = data.split(",");
                long delay = Long.parseLong(values[0]);
                int percent = Integer.parseInt(values[1]);
                for (int i = 0; i < CORES; i++) {
                    loads[i].changeLoad(percent);
                }
                Thread.sleep(delay * 1000);
            }

            for (int i = 0; i < CORES; i++) {
                loads[i].stopLoad();
            }
        }
    }
}