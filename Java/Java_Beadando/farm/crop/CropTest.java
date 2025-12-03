package farm.crop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CropTest {

    @Test
    void testWaterDecayAfterSimulateDay() { 
        Crop crop = new Crop(CropType.CORN);
        crop.water(); 
        
        crop.simulateDay(); 
        
        assertEquals(1, crop.getWaterAmount());
    }

    @Test
    void testGrowthOccursWhenWatered() { 
        Crop crop = new Crop(CropType.LETTUCE);
        crop.water(); 
        
        crop.simulateDay(); 

        assertFalse(crop.getIsCropDead());
    }

    @Test
    void testDifferentDeathThresholds() { 

        Crop strawberry = new Crop(CropType.STRAWBERRY);
        
        strawberry.simulateDay(); 
        assertFalse(strawberry.getIsCropDead());

        strawberry.simulateDay(); 
        assertTrue(strawberry.getIsCropDead());
        Crop corn = new Crop(CropType.CORN);
        corn.simulateDay(); 
        corn.simulateDay(); 
        corn.simulateDay(); 
        assertFalse(corn.getIsCropDead());
        
        corn.simulateDay(); 
        assertTrue(corn.getIsCropDead());
    }

    @Test
    void testIsMature() { 
        Crop crop = new Crop(CropType.LETTUCE);
        
        for (int i = 0; i < 9; i++) {
            crop.water();
            crop.simulateDay();
        }
        assertFalse(crop.isMature()); 

        crop.water();
        crop.simulateDay();
        assertTrue(crop.isMature()); 
    }

    @Test
    void testHarvestFailsWhenNotMatureOrDead() { 
        Crop youngCrop = new Crop(CropType.CORN);
        assertFalse(youngCrop.harvest());

        Crop deadCrop = new Crop(CropType.STRAWBERRY);
        deadCrop.simulateDay();
        deadCrop.simulateDay(); 
        assertFalse(deadCrop.harvest());
    }

    @Test
    void testHarvestSuccess() { 
        Crop crop = new Crop(CropType.LETTUCE);
        for (int i = 0; i < 10; i++) {
            crop.water();
            crop.simulateDay();
        }
        
        assertTrue(crop.harvest()); 
        assertEquals(0, crop.getWaterAmount()); 
    }
}