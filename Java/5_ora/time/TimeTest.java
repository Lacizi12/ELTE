package time;

// You may copy these imports verbatim into your own tester code.
import static org.junit.jupiter.api.Assertions.*;
import module org.junit.jupiter;

public class TimeTest {
    @DisplayName("The comment in Time.java shows how to fix the demo mistake in it")
    @Test
    void testGetHour() {
        assertEquals(12, new Time(12, 34).getHour());
    }

    @Test
    void testGetMin() {
        assertEquals(34, new Time(12, 34).getMin());
    }

    @DisplayName("The comment in Time.java shows how to fix the demo mistake in it")
    @Test
    void testSetHour() {
        Time time = new Time(0, 0);
        time.setHour(12);
        assertEquals(12, time.getHour());
    }

    @Test
    void testSetMin() {
        Time time = new Time(0, 0);
        time.setMin(34);
        assertEquals(34, time.getMin());
    }

    // These make the output prettier.
//    @ParameterizedTest(name = "{2}:{3} vs {4}:{5} ⟹ {0}:{1}")
    @ParameterizedTest
    @CsvSource(textBlock = """
        01, 02,   01, 02, 12, 34
        01, 59,   01, 59, 12, 34
        12, 34,   23, 59, 12, 34
    """)
    void testEarlier(int expectedHour, int expectedMin, int hour1, int min1, int hour2, int min2) {
        Time time1 = new Time(hour1, min1);
        Time time2 = new Time(hour2, min2);

        assertEquals(expectedHour, time1.getEarlier(time2).getHour());
        assertEquals(expectedMin, time1.getEarlier(time2).getMin());

        assertEquals(expectedHour, time2.getEarlier(time1).getHour());
        assertEquals(expectedMin, time2.getEarlier(time1).getMin());
    }
}
