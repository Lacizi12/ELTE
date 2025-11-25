package math.operation.safe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class IncrementTest {

    @Test
    void testIncrement() {
        // 1. Normál esetek
        assertEquals(1, Increment.increment(0));
        assertEquals(101, Increment.increment(100));
        assertEquals(0, Increment.increment(-1)); // -1 + 1 = 0
        
        // 2. Szélsőértékek (A lényeg!)
        
        // Minimum növelése (ez biztonságos)
        assertEquals(Integer.MIN_VALUE + 1, Increment.increment(Integer.MIN_VALUE));
        
        // Maximum növelése (ez a "biztonságos" megállás)
        // Ha nem lenne az if, itt negatív számot kapnánk!
        assertEquals(Integer.MAX_VALUE, Increment.increment(Integer.MAX_VALUE));
    }
}