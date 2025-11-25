// Farm.java
import java.util.ArrayList;
import java.util.List;

/**
 * A Farm osztály egy Plot-okból álló 2D-s tömböt kezel.
 * [cite: 110]
 */
public class Farm {

    private Plot[][] plots; // A 2D tömb, ami a parcellákat tárolja

    /**
     * Konstruktor: Létrehozza a farmot a megadott méretben.
     * @param rows Sorok száma
     * @param cols Oszlopok száma
     */
    public Farm(int rows, int cols) {
        // Ellenőrzés: legalább 1x1-esnek kell lennie. [cite: 111]
        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException("Farm rows and cols must be at least 1.");
        }

        // Tömb inicializálása
        this.plots = new Plot[rows][cols];

        // FONTOS: Feltöltjük a tömböt üres Plot objektumokkal.
        // Ha ezt kihagynánk, minden elem null lenne!
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.plots[i][j] = new Plot();
            }
        }
    }

    /**
     * Szimulálja egy nap elteltét az összes parcella számára.
     * [cite: 114]
     */
    public void simulateDay() {
        // Végigiterálunk a 2D tömb minden elemén
        for (int i = 0; i < plots.length; i++) {
            for (int j = 0; j < plots[i].length; j++) {
                Plot currentPlot = plots[i][j];
                
                // Ha van növény a parcellán, szimuláljuk a napot
                if (!currentPlot.isEmpty()) {
                    currentPlot.getCrop().simulateDay();
                }
            }
        }
    }

    /**
     * Megkeresi a legszomjasabb (legalacsonyabb vízszintű) növényt a farmon.
     * @return A legszomjasabb Crop, vagy null, ha üres a farm.
     * [cite: 115]
     */
    public Crop findMostThirstyCrop() {
        Crop thirstiest = null;
        int minWater = Integer.MAX_VALUE; // Kezdőértéknek egy nagyon nagy számot adunk

        for (int i = 0; i < plots.length; i++) {
            for (int j = 0; j < plots[i].length; j++) {
                Plot currentPlot = plots[i][j];

                // Csak akkor vizsgálódunk, ha van ott növény
                if (!currentPlot.isEmpty()) {
                    Crop currentCrop = currentPlot.getCrop();
                    
                    // Ha ez a növény szomjasabb (kevesebb vize van), mint az eddigi rekordtartó
                    // VAGY még nem találtunk növényt (thirstiest == null)
                    if (thirstiest == null || currentCrop.getWaterAmount() < minWater) {
                        thirstiest = currentCrop;
                        minWater = currentCrop.getWaterAmount();
                    }
                }
            }
        }
        return thirstiest;
    }

    /**
     * Segédfüggvény a Farmer osztálynak: Visszaad egy adott parcellát a koordináták alapján.
     * (Erre szükség lesz, hogy a Farmer ültetni tudjon adott helyre).
     */
    public Plot getPlot(int row, int col) {
        // Ellenőrizzük, hogy létezik-e a koordináta
        if (row < 0 || row >= plots.length || col < 0 || col >= plots[0].length) {
             throw new IllegalArgumentException("Invalid plot coordinates.");
        }
        return plots[row][col];
    }
    
    /**
     * Visszaadja a farm méreteit (sorok száma).
     */
    public int getRows() {
        return plots.length;
    }

    /**
     * Visszaadja a farm méreteit (oszlopok száma).
     */
    public int getCols() {
        return plots[0].length;
    }

    /**
     * A farm szöveges kirajzolása.
     * [cite: 116-120]
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Hatékonyabb szövegösszefűzéshez
        
        for (int i = 0; i < plots.length; i++) {
            for (int j = 0; j < plots[i].length; j++) {
                // Hozzáfűzzük a parcella betűjelét (E, L, C, S, D)
                sb.append(plots[i][j].toString());
            }
            // Sor végén sortörés
            sb.append("\n");
        }
        return sb.toString();
    }
}