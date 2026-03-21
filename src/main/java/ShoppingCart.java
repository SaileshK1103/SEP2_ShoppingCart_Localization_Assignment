import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ShoppingCart {
    // Calculate the total cost of each items (price  * quamtity)
    public double calculateItemTotal(double price, int quantity) {
        return price * quantity;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        // Language selection
        System.out.println("Select language / Valitse Kieli / Välj spåk:");
        System.out.println("1. English");
        System.out.println("2. Finnish(Suomi)");
        System.out.println("3. Swedish (Svenska)");
        System.out.println("4. Japanese (日本語)");

        int choice = scanner.hasNextInt() ? scanner.nextInt() : 1;

        Locale locale = switch (choice) {
            case 2 -> new Locale("fi", "FI");
            case 3 -> new Locale("sv", "SE");
            case 4 -> new Locale("ja", "JP");
            default -> new Locale("en", "US");
        };

        // Load the corresponding properties file
        ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", locale);

        // Prompt for number of items
        System.out.println(bundle.getString("msg.items") + " ");
        int numItems = scanner.nextInt();

        double cartTotal = 0;

        // Loop through items
        for (int i=1; i <= numItems; i++) {
            System.out.println("\n--- Item #" + i + " ---");

            System.out.print(bundle.getString("msg.price") + " ");
            double price = scanner.nextDouble();

            System.out.print(bundle.getString("msg.quantity") + " ");
            int quantity = scanner.nextInt();

            // Calculate item total using the logic method
            double itemTotal = cart.calculateItemTotal(price, quantity);
            cartTotal += itemTotal;
        }
        // Display final total
        System.out.println("\n-------------------------");
        String formattedTotal = String.format("%.2f", cartTotal);
        System.out.println(bundle.getString("msg.total") + " " + formattedTotal);
        System.out.println("---------------------------");

        scanner.close();
    }
}
