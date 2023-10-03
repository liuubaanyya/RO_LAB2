import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Bee implements Runnable {
    private final int id;
    private final List<Integer> forest;
    private static final Object winnieThePooh = new Object();

    public Bee(int id, List<Integer> forest) {
        this.id = id;
        this.forest = forest;
    }

    @Override
    public void run() {
        try {
            System.out.println("Bee " + id + " is searching for Winnie the Pooh");
            Thread.sleep(new Random().nextInt(2000)); // Simulate search

            synchronized (winnieThePooh) {
                if (forest.contains(id)) {
                    System.out.println("Bee " + id + " found Winnie the Pooh and punished him");
                    forest.remove(Integer.valueOf(id));
                }
            }

            System.out.println("Bee " + id + " returns to the hive");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class WinnieThePoohSearch {
    public static void main(String[] args) {
        int numBees = 10;
        int forestSize = 5;
        List<Integer> forest = new ArrayList<>();

        // Populate the forest with random locations of Winnie the Pooh
        Random random = new Random();
        for (int i = 0; i < forestSize; i++) {
            forest.add(random.nextInt(numBees) + 1);
        }

        ExecutorService executor = Executors.newFixedThreadPool(numBees);

        for (int i = 1; i <= numBees; i++) {
            executor.execute(new Bee(i, forest));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All bees have returned to the hive. Search is complete.");
    }
}