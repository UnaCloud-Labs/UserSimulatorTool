package ramload;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Loads the machine with the desired amount of RAM.
 * Note: Only works for Windows OS at the moment.
 */
public class RamLoad {

    /**
     * Number of bytes in 500Mb. Determines the number of memory that would be
     * occupied per process. Note that the Maximum heap size of JVM tends to go 
     * around 1Gb on machines running Windows.This value gives some flexibility.
     */
    private static final int MAX_LOAD = 512000;
    
    /**
     * Total memory in the system.
     */
    private final int totalmem;

    /**
     * Total number of memory that is currently being occupied.
     */
    private final int occupiedmem;

    /**
     * Number of processes that must be executed in order to reach the desired
     * load.
     */
    private final int totalProcesses;
    
    /**
     * Determines where the RamTest.jar is located.
     */
    private final String jarPath;

    /**
     * Buffer that reads from the file.
     */
    private final BufferedReader fileReader;

    /**
     * All the parameters that must be passed to the process builder.
     */
    private final ArrayList<String> parameters;
    
    /**
     * 
     * @param logPath, path that contains the file that specifies the different
     * RAM values that must be mantained for the specified time.
     * @param jarPath, path that defines where the RamTest.jar is located at.
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public RamLoad(String logPath, String jarPath) throws IOException, InterruptedException {

        this.jarPath = jarPath;

        totalmem = totalRam();

        occupiedmem = totalmem - freeRam();

        totalProcesses = maxProcessesNeeded(freeRam());

        fileReader = new BufferedReader(new FileReader(logPath));

        parameters = arguments();

    }

    /**
     * Runs a command to the windows platform to know the available amount of
     * RAM in the system.
     * @return availableRam
     * @throws IOException 
     */
    public static int freeRam() throws IOException {
        // In KiBytes, available RAM of the system.
        Process memavailable = Runtime.getRuntime().exec("wmic OS get FreePhysicalMemory");
        BufferedReader br = new BufferedReader(new InputStreamReader(memavailable.getInputStream()));
        br.readLine();
        br.readLine();
        int availableRam = Integer.parseInt(br.readLine().trim());
        return availableRam;
    }
    
    /**
     * Runs a command to the windows platform to know the total amount of RAM.
     * @return totalRam
     * @throws IOException 
     */
    public static int totalRam() throws IOException {
        // In KiBytes, Total RAM of the system.
        Process memtot = Runtime.getRuntime().exec("wmic OS Get TotalVisibleMemorySize");

        BufferedReader br = new BufferedReader(new InputStreamReader(memtot.getInputStream()));
        br.readLine();
        br.readLine();
        int totalRam = Integer.parseInt(br.readLine().trim());
        return totalRam;
    }

    /**
     * Determines the number of processes that are needed in order to
     * occupy the specified amount of RAM.
     * @param ram, the amount of RAM that wants to be used in the machine.
     * @return 
     */
    private int maxProcessesNeeded(int ram) {
        return (ram % MAX_LOAD == 0) ? ram / MAX_LOAD : ram / MAX_LOAD + 1;
    }

    /**
     * Returns the number of memory that needs to be occupied per process.
     * @param percentage
     * @return 
     */
    private int memNeeded(int percentage) {
        return (totalmem * percentage / 100 - occupiedmem) / totalProcesses;
    }

    /**
     * Builds a command that is used to execute one process with RamTest.jar
     * @return
     * @throws IOException 
     */
    private ArrayList<String> arguments() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("java");
        args.add("-Xms550m");
        args.add("-Xmx800m");
        args.add("-jar");
        args.add("\"" + jarPath + "\"");
        String data = "";
        while ((data = fileReader.readLine()) != null) {
            String parse[] = data.split(",");
            args.add(parse[0] + "," + memNeeded(Integer.parseInt(parse[1])));
        }
        return args;
    }

    /**
     * Starts to load the RAM with the values specified in the logFile.
     * @throws IOException 
     */
    public void start() throws IOException {

        ProcessBuilder pb = new ProcessBuilder(parameters);

        for (int i = 0; i < totalProcesses; i++) {
            pb.start();
        }
    }

}
