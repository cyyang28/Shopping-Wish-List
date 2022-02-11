// Referenced code in AccountNotRobust-TellerApp class

package ui;

import model.Product;
import model.ShoppingWishList;

import java.util.Scanner;

// Shopping wish list application
public class WishListApp {
    private ShoppingWishList shoppingWishList;
    private Scanner input;

    // EFFECTS: runs the wish list application
    public WishListApp() {
        runWishList();
    }

    // MODIFIES: this
    // EFFECTS: processes use input
    private void runWishList() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddProduct();
        } else if (command.equals("i")) {
            doIncreaseQuantity();
        } else if (command.equals("d")) {
            doDecreaseQuantity();
        } else if (command.equals("r")) {
            doRateProduct();
        } else if (command.equals("v")) {
            printShoppingWishList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes shopping wish list
    private void init() {
        shoppingWishList = new ShoppingWishList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add product");
        System.out.println("\ti -> increase quantity");
        System.out.println("\td -> decrease quantity");
        System.out.println("\tr -> rate product");
        System.out.println("\tv -> view shopping list");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this, product
    // EFFECTS: conducts an add product operation
    private void doAddProduct() {
        System.out.print("Enter product title: ");
        String title = input.next();
        System.out.print("Enter product price: $");
        double price = input.nextDouble();
        System.out.print("Enter product quantity: ");
        int quantity = input.nextInt();

        if (price < 1.0 || quantity < 1) {
            System.out.println("Cannot have input less than 1...\n");
        } else {
            shoppingWishList.addProduct(title, price, quantity);
        }

        System.out.println("Shopping Wish List ");
        printShoppingWishList();
    }

    // MODIFIES: this, product
    // EFFECTS: conducts an increase quantity operation
    private void doIncreaseQuantity() {
        Product selected = selectProduct();
        System.out.print("Enter amount to increase quantity: ");
        int amount = input.nextInt();

        if (amount >= 1) {
            shoppingWishList.increaseQuantity(selected, amount);
            System.out.println(selected.getTitle() + " quantity: " + selected.getQuantity());
        } else {
            System.out.println("Cannot have amount less than one...");
        }
    }

    // MODIFIES: this, product
    // EFFECTS: conducts a decrease quantity operation
    private void doDecreaseQuantity() {
        Product selected = selectProduct();
        System.out.print("Enter amount to decrease quantity: ");
        int amount = input.nextInt();

        if (amount > 0 && selected.getQuantity() < amount) {
            System.out.println("Please enter amount between 1 and " + selected.getQuantity());
        } else if (amount > 0) {
            shoppingWishList.decreaseQuantity(selected, amount);
            if (selected.getQuantity() == amount) {
                System.out.println("Product removed from shopping wish list...");
            } else {
                System.out.println(selected.getTitle() + " quantity: " + selected.getQuantity());
            }
        } else {
            System.out.println("Cannot have an amount less than one...");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts a rate product operation
    private void doRateProduct() {
        Product selected = selectProduct();
        System.out.print("Enter number of star to rate this product: ");
        int star = input.nextInt();

        if (0 <= star && star <= 5) {
            selected.rateProduct(star);
            System.out.println(selected.getTitle() + " have " + selected.getStar() + " stars");
        } else {
            System.out.println("Number of starts must be between 0 and 5...");
        }
    }

    // EFFECTS: prompts user to select product in shopping wish list and returns it
    private Product selectProduct() {
        String selection = "";

        System.out.println("Select product from one of the following: ");
        for (Product next : shoppingWishList.getShoppingWishList()) {
            System.out.println(next.getTitle());
        }
        selection = input.next();
        selection = selection.toLowerCase();

        for (Product next : shoppingWishList.getShoppingWishList()) {
            if (selection.equals(next.getTitle())) {
                return next;
            }
        }
        return null;
    }

    // EFFECTS: prints the shopping wish list to the screen
    private void printShoppingWishList() {
        for (Product next : shoppingWishList.getShoppingWishList()) {
            System.out.println(next.getTitle() + " $" + next.getPrice() + " quantity: " + next.getQuantity()
                    + ", rating of " + next.getStar() + " stars");
        }
    }
}
