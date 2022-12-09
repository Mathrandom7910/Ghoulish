package me.mathrandom7910.ghoulish.client.misc;

public class ThreadHolder implements MCInst {
    private Thread thread = null;
    private boolean canceled = false;
    private int interval;
    private final Runnable runnable;

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public ThreadHolder(Runnable r, int interval) {
        runnable = r;
        this.interval = interval;
    }

    public void start() {
//        System.out.println("STARTING THREAD " + thread + " " + (thread == null ? "null" : thread.isAlive()));
        if (thread == null || !thread.isAlive()) {

            thread = new Thread(() -> {
//                System.out.println("BEGINNING THREAD");
                while (!canceled) {
                    if (mc.world == null || mc.player == null) continue;

//                    System.out.println("running");
                    runnable.run();

                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

//                System.out.println("END THREAD");
                thread = null;
            });
        thread.start();
        }
    }

    public void setInt(int interval) {
        this.interval = interval;
    }
}
