
void main() {
    String ageTxt = IO.readln("What is your age? ");
    // now it is a proper number
    int age = Integer.parseInt(ageTxt);
    var oldAge = age + 1;

    IO.println("You will be " + oldAge + " next year, I know");
}
