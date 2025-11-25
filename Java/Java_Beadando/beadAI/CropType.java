public enum CropType {
    
    // LETTUCE (Saláta): growthRate: 3, maturity: 30, maxDryDays: 2
    LETTUCE(3, 30, 2),
    
    // CORN (Kukorica): growthRate: 5, maturity: 75, maxDryDays: 3
    CORN(5, 75, 3),
    
    // STRAWBERRY (Eper): growthRate: 6, maturity: 60, maxDryDays: 1
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