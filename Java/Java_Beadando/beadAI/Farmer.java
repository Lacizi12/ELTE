import java.util.ArrayList;
import java.util.List;

public class Farmer {

    private String name;
    private Farm farm;
    private List<Crop> harvestedCrops;

    public Farmer(String name, Farm farm) {
        if (name == null || name.isEmpty() || farm == null) {
            throw new IllegalArgumentException("Farmer name and farm cannot be null/empty.");
        }
        this.name = name;
        this.farm = farm;
        this.harvestedCrops = new ArrayList<>();
    }

    public void plantCrop(int x, int y, CropType type) {
        Plot plot = farm.getPlot(x, y);
        plot.plant(new Crop(type));
    }

    public void waterCrop(int x, int y) {
        Plot plot = farm.getPlot(x, y);
        
        if (!plot.isEmpty()) {
            Crop crop = plot.getCrop();
            crop.water();
        }
    }

    public void waterCrops(CropType type) {
        for (int i = 0; i < farm.getRows(); i++) {
            for (int j = 0; j < farm.getCols(); j++) {
                Plot plot = farm.getPlot(i, j);
                
                if (!plot.isEmpty()) {
                    Crop crop = plot.getCrop();
                    if (crop.getType() == type && !crop.isDead()) {
                        crop.water();
                    }
                }
            }
        }
    }

    // Segédfüggvény a kódismétlés elkerülésére
    private void harvestCropLogic(Plot plot) {
        if (!plot.isEmpty()) {
            Crop crop = plot.getCrop();
            
            if (crop.harvest()) {
                harvestedCrops.add(crop);
                plot.removeCrop();
            }
        }
    }
    
    public void harvestCrop(int x, int y) {
         Plot plot = farm.getPlot(x, y);
         harvestCropLogic(plot);
    }

    public void harvestCrops(CropType type) {
        for (int i = 0; i < farm.getRows(); i++) {
            for (int j = 0; j < farm.getCols(); j++) {
                Plot plot = farm.getPlot(i, j);
                if (!plot.isEmpty()) {
                    Crop crop = plot.getCrop();
                    if (crop.getType() == type) {
                        harvestCropLogic(plot);
                    }
                }
            }
        }
    }

    public void cleanPlot(int x, int y) {
        Plot plot = farm.getPlot(x, y);
        if (plot.hasDeadCrop()) {
            plot.removeCrop();
        }
    }

    public void simulateDay() {
        farm.simulateDay();
    }

    public void waterMostThirstyCrop() {
        Crop thirsty = farm.findMostThirstyCrop();
        if (thirsty != null) {
            thirsty.water();
        }
    }

    public List<Crop> getHarvestedCrops() {
        return harvestedCrops;
    }
}