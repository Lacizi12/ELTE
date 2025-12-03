package farm.crop;

public class Crop {

    private final CropType type;
    private int waterLevel;
    private int growthLevel;
    private int dryDays;
    private boolean isCropDead;

    public Crop(CropType type) {
        if (type == null) {
            throw new IllegalArgumentException("CropType cannot be null");
        }
        this.type = type;
        this.waterLevel = 0;
        this.growthLevel = 0;
        this.dryDays = 0;
        this.isCropDead = false;
    }

    public boolean isCropDead() {
        return isCropDead;
    }

    public boolean getIsCropDead() {
        return isCropDead;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public int getGrowthLevel() {
        return growthLevel;
    }

    public void water() {
        if (getIsCropDead()) {
            return;
        }

        this.waterLevel += 2;
        
        if (this.waterLevel > 10) {
            this.waterLevel = 10;
        }

        this.dryDays = 0;
    }

    public void simulateDay() {
        if (waterLevel > 0) {
            this.growthLevel += type.getGrowthRate();
            this.waterLevel--;
        } else {
            this.dryDays++;
        }
        
        if (dryDays > type.getMaxDryDays()) {
            this.isCropDead = true;
        }
    }

    public boolean isMature() {
        return growthLevel >= type.getPossibleMaturity();
    }

    public boolean harvest() {
        if (isMature() && !getIsCropDead()) {
            this.waterLevel = 0;
            this.growthLevel = 0;
            this.dryDays = 0;
            this.isCropDead = false;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if (getIsCropDead()) {
            return "D";
        }
        switch (type) {
            case LETTUCE: return "L";
            case CORN: return "C";
            case STRAWBERRY: return "S";
            default: return "?";
        }
    }
    
    // Régi getterek kompatibilitás miatt
    public int getWaterAmount() { return waterLevel; }
    public CropType getType() { return type; }
}