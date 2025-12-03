package farm.field;

import farm.crop.Crop;
import farm.crop.CropType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlotTest {

    @Test
    void testPlantAndRemove() {
        Plot plot = new Plot();
        assertTrue(plot.isEmpty());

        plot.plant(new Crop(CropType.CORN));
        assertFalse(plot.isEmpty());

        plot.removeCrop();
        assertTrue(plot.isEmpty());
    }

    @Test
    void testPlantOnOccupiedPlotFails() {
        Plot plot = new Plot();
        plot.plant(new Crop(CropType.LETTUCE));

        assertThrows(IllegalStateException.class, () -> {
            plot.plant(new Crop(CropType.CORN));
        });
    }
}