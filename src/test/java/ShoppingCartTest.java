import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    @Test
    public void testCalculateItemTotal() {
        ShoppingCart cart = new ShoppingCart();

        // Test basic calculation
        assertEquals(50.0, cart.calculateItemTotal(10.0, 5), 0.001);

        // Test with zero quantity
        assertEquals(0.0, cart.calculateItemTotal(100.0, 0), 0.001);

        // Test with decimal prices
        assertEquals(15.75, cart.calculateItemTotal(5.25, 3), 0.001);
    }
}