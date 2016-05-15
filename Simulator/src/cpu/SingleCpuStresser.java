package cpu;

/**
 * Tries to mantain a single cpu processor with a load defined by percent.
 * @author Antonio de la Vega
 */
public class SingleCpuStresser extends Thread {

    private int percent;
    private final StopWatch watch;
    private boolean keepRunning;

    /**
     * Initializes all values for the cpu load to begin.
     * @param percent, the desired initial load on a cpu.
     */
    public SingleCpuStresser(int percent) {
        this.percent = percent;
        watch = new StopWatch();
        keepRunning = true;
    }

    /**
     * Keeps the cpu active for a number of miliseconds equivalent to percent.
     * Afterwards, the cpu is released of the load for 100-percent seconds.
     * As a result, the cpu would be active on the targeted percent value.
     */
    @Override
    public void run() {
        watch.start();
        while (keepRunning) {
            if (watch.getElapsedTime() > percent) {
                try {
                    Thread.sleep(100 - percent);
                } catch (InterruptedException e) {}
                watch.start();
            }
        }
    }
    
    /**
     * Unloads the cpu.
     * After this method call, the thread would soon die.
     */
    public void stopLoad() {
        keepRunning = false;
    }

    /**
     * Changes the load that the thread loads in the CPU.
     * @param percent 
     */
    public void changeLoad(int percent) {
        this.percent = percent;
    }

}
