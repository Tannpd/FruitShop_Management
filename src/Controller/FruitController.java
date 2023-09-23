package controller;

import common.Library;
import java.util.ArrayList;
import java.util.Hashtable;
import model.Fruit;
import model.Order;
import view.Menu;

public class FruitController extends Menu {

    Library l;
    ArrayList<Fruit> list_F;
    Hashtable<String, ArrayList<Order>> ht;

    public FruitController(String td, String[] mc, String exit) {
        super(td, mc, exit);
        l = new Library();
        list_F = new ArrayList<>();
        ht = new Hashtable<>();
    }

    @Override
    public void execute(int n) {
        switch (n) {
            case 1:
                createFruit();
                break;
            case 2:
                viewListOrder();
                break;
            case 3:
                shopping();
                break;
        }
    }

    public void viewListOrder() {
        if (ht.isEmpty()) {
            System.out.println("No Order");
            return;
        }
        for (String name : ht.keySet()) {
            System.out.println("Customer: " + name);
            ArrayList<Order> array_o = ht.get(name);
            displayListOrder(array_o);
        }
    }

    public void createFruit() {
        while (true) {
            int id = generateID();
            String name = l.getString("Enter fruit name: ");
            double price = l.getDouble("Enter fruit price ");
            int quantity = l.getInt("Enter fruit quantity (1-100) ", 1, 100);
            String origin = l.getString("Enter fruit origin: ");
            list_F.add(new Fruit(id, name, price, quantity, origin));

            // Ask the user if they want to add another fruit
            String continueInput = l.getString("Do you want to continue (Y/N)? ");
            if (!continueInput.equalsIgnoreCase("Y")) {
                break;
            }
        }
    }

    public void displayFruit() {
        System.out.println("---------------------------------------------------------------");
        System.out.println("| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ | ++ Quantity ++ |");
        System.out.println("---------------------------------------------------------------");

        for (Fruit f : list_F) {
            System.out.printf(" %4d\t\t%-20s\t%-15s\t%.2f$\t\t%3d\t\n", f.getId(), f.getName(), f.getOrigin(), f.getPrice(), f.getQuantity());
        }

        System.out.println("---------------------------------------------------------------");
    }

    public void shopping() {
        while (true) {
            if (list_F.isEmpty()) {
                System.out.println("No products available for shopping.");
                return; // Exit the shopping method as there are no products.
            }

            displayFruit();
            int selectedItem = l.getInt("To order, customer selects Item (1 to " + list_F.size() + ") ", 1, list_F.size());
            Fruit selectedFruit = list_F.get(selectedItem - 1);

            System.out.println("You selected: " + selectedFruit.getName());

            int quantityOrder = l.getInt("Please input quantity", 1, 10);
            ArrayList<Order> list_o = new ArrayList<>();

            int id = selectedFruit.getId();
            double price = selectedFruit.getPrice();
            int availableQuantity = selectedFruit.getQuantity();

            if (quantityOrder > availableQuantity) {
                System.out.println("Quantity Ordered more than available quantity");
                continue; // Don't proceed with the order if the quantity is insufficient.
            }

            list_o.add(new Order(id, selectedFruit.getName(), quantityOrder, price));

            String orderNow = l.getString("Do you want to order now (Y/N): ");
            if (orderNow.equalsIgnoreCase("N")) {
                continue; // Continue ordering
            } else if (orderNow.equalsIgnoreCase("Y")) {
                displayListOrder(list_o);
                String customer = l.getString("Input your name: ");
                ht.put(customer, list_o);
                System.out.println("Add Successful");

                // Update the quantity of the product in the list_F
                selectedFruit.setQuantity(availableQuantity - quantityOrder);
                return; // Exit the shopping method after completing the order.
            } else {
                System.out.println("Invalid input. Please enter 'Y' to order now or 'N' to continue ordering.");
            }
        }
    }

    private void displayListOrder(ArrayList<Order> list_o) {
        double total = 0;
        System.out.println("Product  |  Quantity  |  Price  |  Amount");

        for (int i = 0; i < list_o.size(); i++) {
            Order o = list_o.get(i);
            double amount = o.getPrice() * o.getQuanlity();
            System.out.printf("%d. %s\t%d\t%.2f$ \t%.2f$%n", (i + 1), o.getName(), o.getQuanlity(), o.getPrice(), amount);
            total += amount;
        }
        System.out.printf("Total: $%.2f%n", total);
    }

    public int generateID() {
        int id = 0;
        if (list_F.size() == 0) {
            return 1;
        } else {

            for (Fruit s : list_F) {
                if (s.getId() == list_F.size()) {
                    id = s.getId() + 1;
                }
            }
        }
        return id;
    }
}
