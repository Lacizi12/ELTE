
void main() {
    try {
        new Person("Mr. Right", 29);
        new Person("Ms. Left", -29);
        IO.println("Elérhetetlen utasítás");
    } catch (IllegalArgumentException e) {
        IO.println(e.getMessage()); // csak az üzenet

//        e.printStackTrace();        // részletes kiírás
    }
}
