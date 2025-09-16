
void main() {
    String name = IO.readln("What is your name? ");
    String friendName = IO.readln("What is your friend's name? ");

    if (name == friendName) {
        // elérhetetlen kód
        // unreachable code
        IO.println("You hacked the program to get here, right?");
    } else {
        IO.println("""
        Wow, == is a bad choice when you are using Strings in Java!
        The next demo shows you how to fix this.
        """);
    }
}
