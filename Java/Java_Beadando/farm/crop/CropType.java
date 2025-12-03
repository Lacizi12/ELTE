package farm.crop;

public enum CropType {

    LETTUCE(3, 30, 2),
    CORN(5, 75, 3),
    STRAWBERRY(6, 60, 1);

    private final int growthRate;
    private final int possibleMaturity;
    private final int maxDryDays;

    CropType(int growthRate, int possibleMaturity, int maxDryDays) {
        this.growthRate = growthRate;
        this.possibleMaturity = possibleMaturity;
        this.maxDryDays = maxDryDays;
    }

    public int getGrowthRate() {
        return growthRate;
    }

    public int getPossibleMaturity() {
        return possibleMaturity;
    }

    public int getMaxDryDays() {
        return maxDryDays;
    }
}