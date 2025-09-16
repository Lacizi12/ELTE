
void main() {
    for (int i = 0; i < 10; i++) {
        helloWorld(i);
    }
}

// segédmetódus (segédfüggvény)
// helper method (helper function)
void helloWorld(int idx) {
    IO.println("Helló világ #" + idx + "! 👍");
}
