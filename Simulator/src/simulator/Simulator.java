package simulator;

import cpu.CpuLoad;
import java.io.FileNotFoundException;
import java.io.IOException;
import ramload.RamLoad;

/**
 * Loads CPU and RAM at desired levels for a specific amount of time.
 * @author Antonio de la Vega
 */
public class Simulator {
    /** 
     * @param args
     * args[0] cpu load filepath.
     * args[1] ram load filepath.
     * args[2] RamTest.jar filepath.
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException{
        if(args.length < 3){
            throw new IllegalArgumentException("You must specify the filepath "
                    + "for the following files in the order in which they are"
                    + " presented: cpu, ram, and jar");
        }
        try{
            RamLoad ramLoader = new RamLoad(args[1], args[2]);
            CpuLoad cpuLoader = new CpuLoad(args[0]);
            ramLoader.start();
            cpuLoader.start();
        }
        catch(IOException | InterruptedException e){System.out.println(e.getMessage());}
    }
}
