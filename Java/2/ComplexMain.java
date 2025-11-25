public class ComplexMain {
    public static void main(String[] args) {
        // 1. Létrehozunk egy számot: 4 + 2i
        Complex c = new Complex(4.0, 2.0);
        
        System.out.print("Alapállapot: ");
        c.print(); // Kiírja: 4,0+2,0i

        // 2. Összeadás: Hozzáadunk 1 - 13i -t
        Complex c2 = new Complex(1.0, -13.0);
        c.add(c2);
        
        // Kiírás a bővített módszerrel
        // "after adding..."
        c.print("after adding", c2);

        // 3. Kivonás
        Complex c3 = new Complex(11.1, 2.4);
        c.sub(c3);
        c.print("after subtracting", c3);
        
        // 4. Szorzás (Bónusz teszt)
        Complex c4 = new Complex(2.0, 0.0); // Sima 2-vel szorzás
        c.mul(c4);
        c.print("after multiplying by", c4);
    }
}