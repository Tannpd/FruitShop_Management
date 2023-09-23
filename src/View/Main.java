
package view;

import controller.FruitController;

public class Main {
    public static void main(String[] args) {
        String mChon[] = {"Create Fruit", "View Orders", "Shopping (for buyer)"};
        FruitController fruitController = new FruitController("Main Menu", mChon, "exit");
        fruitController.run();
    }
}
