
void main() {
    String name = IO.readln("What is your name? ");
    String friendName = IO.readln("What is your friend's name? ");

    if (name.equals(friendName)) {
        IO.println("Wow, both you and your friend are called " + name);
    } else {
        IO.println("You are %s and your friend is %s%n".formatted(name, friendName));
    }
}
