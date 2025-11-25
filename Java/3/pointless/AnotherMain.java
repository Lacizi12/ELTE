package pointless;

import point2d.Point;

public class AnotherMain {
    public static void main(String[] args) {
        Point point = new Point(1.2, 2.3);

        System.out.println("Eredeti pont: " + point.toString());

        point.move(1.0, 2.0);

        System.out.println("A pont eltolva (1.0, 2.0)-val: " + point.toString());

        point.mirror(3.0, 5.0);

        System.out.println("A kapott pont tukrozve (3.0, 5.0)-re: " + point.toString());
    } 
}