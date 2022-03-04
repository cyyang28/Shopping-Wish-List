// Referenced code in AccountNotRobust-TellerApp class
// https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

// Referenced code in JsonSerializationDemo-WorkRoomApp class
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package ui;

import model.Product;
import model.ShoppingWishList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Represents the shopping wish list application
public class WishListApp {
    private static final String JSON_STORE = "./data/shoppingWishList.json";
    private ShoppingWishList shoppingWishList;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs wish list and runs application
    public WishListApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        shoppingWishList = new ShoppingWishList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runWishList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runWishList() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);

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

        System.out.println("\nExiting shopping wish list app!");
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
        } else if (command.equals("s")) {
            saveWishList();
        } else if (command.equals("l")) {
            loadWishList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add product");
        System.out.println("\ti -> increase quantity");
        System.out.println("\td -> decrease quantity");
        System.out.println("\tr -> rate product");
        System.out.println("\tv -> view shopping list");
        System.out.println("\ts -> save wish list to file");
        System.out.println("\tl -> load wish list from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: conducts an add product operation
    private void doAddProduct() {
        System.out.print("Enter product title: ");
        String title = input.next();
        System.out.print("Enter product price: $");
        double price = input.nextDouble();
        System.out.print("Enter product quantity: ");
        int quantity = input.nextInt();

        if (price < 1.0 || quantity < 1) {
            System.out.println("Cannot have an input less than 1...\n");
        } else {
            shoppingWishList.addProduct(title, price, quantity);
        }

        System.out.println("Shopping Wish List ");
        printShoppingWishList();
    }

    // MODIFIES: this
    // EFFECTS: conducts an increase quantity operation
    private void doIncreaseQuantity() {
        Product selected = selectProduct();
        System.out.print("Enter amount to increase quantity: ");
        int amount = input.nextInt();

        if (amount >= 1) {
            shoppingWishList.increaseQuantity(selected, amount);
            System.out.println(selected.getTitle() + " quantity: " + selected.getQuantity());
        } else {
            System.out.println("Cannot have an amount less than one...");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts a decrease quantity operation
    private void doDecreaseQuantity() {
        Product selected = selectProduct();
        System.out.print("Enter amount to decrease quantity: ");
        int amount = input.nextInt();

        if (amount > 0 && selected.getQuantity() < amount) {
            System.out.println("Please enter amount between 1 and " + selected.getQuantity());
        } else if (amount > 0) {
            if (selected.getQuantity() == amount) {
                shoppingWishList.decreaseQuantity(selected, amount);
                System.out.println("Product removed from shopping wish list...");
            } else {
                shoppingWishList.decreaseQuantity(selected, amount);
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
        System.out.print("Enter number of stars to rate this product: ");
        int star = input.nextInt();

        if (0 <= star && star <= 5) {
            selected.rateProduct(star);
            System.out.println(selected.getTitle() + " have a rating of " + selected.getStar() + " stars");
        } else {
            System.out.println("Number of stars must be between 0 and 5...");
        }
    }

    // EFFECTS: prompts user to select product in shopping wish list and returns it
    private Product selectProduct() {
        String selection;

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
        if (shoppingWishList.getShoppingWishList().isEmpty()) {
            System.out.println("Shopping wish list is empty...");
        }

        for (Product next : shoppingWishList.getShoppingWishList()) {
            System.out.println(next.getTitle() + " $" + next.getPrice() + " quantity: " + next.getQuantity()
                    + ", rating of " + next.getStar() + " stars");
        }
    }

    // EFFECTS: saves the shopping wish list to file
    private void saveWishList() {
        try {
            jsonWriter.open();
            jsonWriter.write(shoppingWishList);
            jsonWriter.close();
            System.out.println("Saved shopping wish list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads shopping wish list from file
    private void loadWishList() {
        try {
            shoppingWishList = jsonReader.read();
            System.out.println("Loaded shopping wish list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
