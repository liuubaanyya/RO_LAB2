import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Item {
    private final String name;
    private final int value;

    public Item(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}

class Store {
    private final BlockingQueue<Item> storage;

    public Store(int capacity) {
        storage = new ArrayBlockingQueue<>(capacity);
    }

    public void addItem(Item item) throws InterruptedException {
        storage.put(item);
    }

    public Item getItem() throws InterruptedException {
        return storage.take();
    }
}

class Ivanov implements Runnable {
    private final Store store;

    public Ivanov(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Item item = new Item("Item", (int) (Math.random() * 100));
                store.addItem(item);
                System.out.println("Ivanov added " + item.getName() + " with value " + item.getValue());
                Thread.sleep(1000); // Час на винос майна
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Petrov implements Runnable {
    private final Store store;

    public Petrov(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Item item = store.getItem();
                System.out.println("Petrov loaded " + item.getName() + " with value " + item.getValue() + " into the truck");
                Thread.sleep(1500); // Час на завантаження вантажівки
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Nechiporchuk implements Runnable {
    private final Store store;

    public Nechiporchuk(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Item item = store.getItem();
                System.out.println("Nechiporchuk counted the value of " + item.getName() + " as " + item.getValue());
                Thread.sleep(2000); // Час на підрахунок вартості майна
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class MilitaryOperation {
    public static void main(String[] args) {
        Store store = new Store(10);

        Thread ivanovThread = new Thread(new Ivanov(store));
        Thread petrovThread = new Thread(new Petrov(store));
        Thread nechiporchukThread = new Thread(new Nechiporchuk(store));

        ivanovThread.start();
        petrovThread.start();
        nechiporchukThread.start();
    }
}
