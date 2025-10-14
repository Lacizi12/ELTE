public class ComplexMain {
    public static void main(String[] args) {
        Complex alpha = new Complex(3.0, 2.0);
        Complex beta = new Complex(1.0, 2.0);

        alpha.add(beta);
        System.out.println("alpha = " + alpha); // 4 + 4i
        System.out.println("beta = " + beta);   // 1 + 2i

        alpha.sub(beta);
        System.out.println("alpha = " + alpha); // vissza 3 + 2i

        alpha.mul(beta);
        System.out.println("alpha = " + alpha); // (3+2i)*(1+2i) = -1 + 8i

        System.out.println("abs(alpha) = " + alpha.abs());
    }
}
