// Referenced code in JsonSerializationDemo-Main class
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new WishListApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
