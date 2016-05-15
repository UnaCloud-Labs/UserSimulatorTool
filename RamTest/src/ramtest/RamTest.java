package ramtest;

/**
 * Occupies an amount of RAM for a specified time.
 * @author Antonio de la Vega
 */
public class RamTest {

    /**
     * @param args the command line arguments
     * Each element in args has the form: time,load
     * The load is specified in KiB.
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        for (String arg : args) {
            String data[] = arg.split(",");
            long duration = Long.parseLong(data[0]);
            int load = Integer.parseInt(data[1]);
            byte[] x = new byte[1024*load];
            Thread.sleep(1000*duration);
        }
    }
    
}
