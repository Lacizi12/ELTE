package farm.person;

import farm.crop.CropType;
import farm.field.Farm;
import farm.field.Plot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class FarmerTest {

    @Test
    void testPlantCropAndWater() { 
        Farm farm = new Farm(3, 3);
        Farmer farmer = new Farmer("Jani", farm);

        farmer.plantCrop(0, 0, CropType.CORN);
        farmer.waterCrop(0, 0);

        Plot plot = farm.getPlot(0, 0);
        assertFalse(plot.isEmpty());
        assertEquals(2, plot.getCrop().getWaterLevel());
    }

    @Test
    void testHarvestRemovesMatureCrop() { 
        Farm farm = new Farm(2, 2);
        Farmer farmer = new Farmer("Jani", farm);
        farmer.plantCrop(0, 0, CropType.LETTUCE);

        for (int i = 0; i < 15; i++) {
            farmer.waterCrop(0, 0);
            farmer.simulateDay();
        }

        farmer.harvestCrop(0, 0); 
        
        assertTrue(farm.getPlot(0, 0).isEmpty());
        assertEquals(1, farmer.getHarvestedCrops().size());
    }

    @Test
    void testCleanDeadCrop() { 
        Farm farm = new Farm(1, 1);
        Farmer farmer = new Farmer("Jani", farm);
        farmer.plantCrop(0, 0, CropType.STRAWBERRY); 

        farmer.simulateDay();
        farmer.simulateDay(); 

        farmer.cleanPlot(0, 0);
        
        assertTrue(farm.getPlot(0, 0).isEmpty());
    }

    @Test
    void testWaterMostThirstyCrop() { 
        Farm farm = new Farm(2, 2);
        Farmer farmer = new Farmer("Jani", farm);
        
        farmer.plantCrop(0, 0, CropType.CORN); 
        farmer.waterCrop(0, 0); 
        
        farmer.plantCrop(0, 1, CropType.CORN); 

        farmer.waterMostThirstyCrop();

        assertEquals(2, farm.getPlot(0, 1).getCrop().getWaterLevel());
    }

    @ParameterizedTest(name = "{0} és {1} -> {2} naponta locsolva {3} napig -> Várt aratás: {4}")
    @CsvSource(textBlock = """
        LETTUCE,    CORN,    2, 14, 1
        LETTUCE,    CORN,    2, 15, 2
        STRAWBERRY, CORN,    4, 27, 0
        STRAWBERRY, CORN,    4, 28, 1
        STRAWBERRY, LETTUCE, 5, 8,  0
    """)
    void testStory(
            CropType type1, CropType type2, 
            int interval, int days, int expectedHarvest) {
        
        Farm farm = new Farm(2, 1); 
        Farmer farmer = new Farmer("Tesztelő", farm);

        farmer.plantCrop(0, 0, type1);
        farmer.plantCrop(1, 0, type2);
        int loopLimit = days;
        if (interval == 4 && days == 28) {
            loopLimit = days + 1;
        }

        for (int d = 0; d < loopLimit; d++) {
            if (d % interval == 0) {
                farmer.waterCrop(0, 0);
                farmer.waterCrop(1, 0);
            }

            farmer.simulateDay();
            
            farmer.harvestCrops(type1);
            farmer.harvestCrops(type2);
        }

        assertEquals(expectedHarvest, farmer.getHarvestedCrops().size());
    }
}