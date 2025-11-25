public class Crop {

    private final CropType type;
    private int waterAmount;
    private int growthLevel;
    private int dryDays;

    public Crop(CropType type) {
        if (type == null) {
            throw new IllegalArgumentException("CropType cannot be null");
        }
        this.type = type;
        this.waterAmount = 0;
        this.growthLevel = 0;
        this.dryDays = 0;
    }

    public boolean isDead() {
        return dryDays > type.getMaxDryDays();
    }

    public void water() {
        if (isDead()) {
            return;
        }

        this.waterAmount += 2;
        
        if (this.waterAmount > 10) {
            this.waterAmount = 10;
        }

        this.dryDays = 0;
    }

    public void simulateDay() {
        if (waterAmount > 0) {
            this.growthLevel += type.getGrowthRate();
            this.waterAmount--;
        } else {
            this.dryDays++;
        }
    }

    public boolean isMature() {
        return growthLevel >= type.getPossibleMaturity();
    }

    public boolean harvest() {
        if (isMature() && !isDead()) {
            this.waterAmount = 0;
            this.growthLevel = 0;
            this.dryDays = 0;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if (isDead()) {
            return "D";
        }
        switch (type) {
            case LETTUCE: return "L";
            case CORN: return "C";
            case STRAWBERRY: return "S";
            default: return "?";
        }
    }
    
    public int getWaterAmount() { return waterAmount; }
    public CropType getType() { return type; }
}