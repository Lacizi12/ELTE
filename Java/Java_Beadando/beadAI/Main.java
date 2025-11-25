// Main.java

public class Main {
    public static void main(String[] args) {
        System.out.println("--- FARM SZIMULÁCIÓ START ---");

        // 1. Létrehozunk egy 3x3-as farmot
        Farm farm = new Farm(3, 3);
        System.out.println("Farm létrehozva (3x3).");

        // 2. Létrehozunk egy gazdát
        Farmer farmer = new Farmer("Pista bá", farm);
        System.out.println("Gazda: " + "Pista bá");

        // 3. Ültetünk növényeket
        System.out.println("\n--- Ültetés ---");
        // (0,0) - Saláta (Gyorsan nő)
        farmer.plantCrop(0, 0, CropType.LETTUCE);
        // (1,1) - Kukorica (Lassan nő)
        farmer.plantCrop(1, 1, CropType.CORN);
        // (2,2) - Eper (Érzékeny a szárazságra)
        farmer.plantCrop(2, 2, CropType.STRAWBERRY);
        
        printFarmState(farm, "Kezdeti állapot");

        // --- SZIMULÁCIÓ (10 nap) ---
        for (int day = 1; day <= 10; day++) {
            System.out.println("\n=== " + day + ". NAP ===");

            // Stratégia:
            // A Salátát (0,0) minden nap locsoljuk -> Gyorsan megérik.
            farmer.waterCrop(0, 0);

            // A Kukoricát (1,1) csak minden 2. nap locsoljuk.
            if (day % 2 == 0) {
                farmer.waterCrop(1, 1);
            }

            // Az Epret (2,2) SOHA nem locsoljuk -> Megnézzük, mikor hal meg.
            // (Az eper maxDryDays értéke 1, tehát gyorsan el fog pusztulni "D")

            // Eltelik a nap (növekedés, vízcsökkenés, szárazság növekedés)
            farmer.simulateDay();

            // Megpróbálunk aratni mindent, ami érett
            // (A harvestCrops metódus végigmegy a farmon)
            farmer.harvestCrops(CropType.LETTUCE);
            farmer.harvestCrops(CropType.CORN);
            farmer.harvestCrops(CropType.STRAWBERRY);
            
            // Ha van halott növény, takarítsuk el (opcionális, most hagyjuk ott, hogy lássuk a 'D'-t)
            // farmer.cleanPlot(2, 2); 

            // Kirajzoljuk a farmot
            printFarmState(farm, "Nap vége");
        }

        System.out.println("\n--- JÁTÉK VÉGE ---");
        System.out.println("Betakarított növények száma: " + farmer.getHarvestedCrops().size());
        
        // Kiírjuk miket szedtünk le
        for (Crop crop : farmer.getHarvestedCrops()) {
            System.out.println("- " + crop.getType()); // Itt csak a típus látszik majd
        }
    }

    // Segédfüggvény a kirajzoláshoz, hogy szebb legyen a konzol
    private static void printFarmState(Farm farm, String label) {
        System.out.println("[" + label + "]");
        System.out.println(farm.toString());
        System.out.println("-----------------");
    }
}