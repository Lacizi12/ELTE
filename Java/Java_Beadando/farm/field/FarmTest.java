package farm.field;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FarmTest {

    @Test
    void testFarmInitialization() {
        Farm farm = new Farm(3, 5);

        assertNotNull(farm.getPlot(0, 0));
        assertNotNull(farm.getPlot(2, 4));

        assertThrows(IllegalArgumentException.class, () -> farm.getPlot(3, 0));
    }
}