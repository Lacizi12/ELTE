package farm.field;

import farm.crop.Crop;

public class Farm {

    private final Plot[][] field;

    public Farm(int rows, int cols) {
        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException("Farm rows and cols must be at least 1.");
        }

        this.field = new Plot[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.field[i][j] = new Plot();
            }
        }
    }

    public Plot[][] getField() {
        return field;
    }

    public void simulateDay() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                Plot currentPlot = field[i][j];
                if (!currentPlot.isEmpty()) {
                    currentPlot.getCrop().simulateDay();
                }
            }
        }
    }

    public Crop findMostThirstyCrop() {
        Crop thirstiest = null;
        int minWater = Integer.MAX_VALUE;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                Plot currentPlot = field[i][j];

                if (!currentPlot.isEmpty()) {
                    Crop currentCrop = currentPlot.getCrop();
                    if (thirstiest == null || currentCrop.getWaterLevel() < minWater) {
                        thirstiest = currentCrop;
                        minWater = currentCrop.getWaterLevel();
                    }
                }
            }
        }
        return thirstiest;
    }

    public Plot getPlot(int row, int col) {
        if (row < 0 || row >= field.length || col < 0 || col >= field[0].length) {
             throw new IllegalArgumentException("Invalid plot coordinates.");
        }
        return field[row][col];
    }
    
    public int getRows() {
        return field.length;
    }

    public int getCols() {
        return field[0].length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                sb.append(field[i][j].toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}