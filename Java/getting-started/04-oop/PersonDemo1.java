
void main() {
    new Person("John", 99);         // példány (instance)

    var p = new Person("John", 99); // változó nélkül nehéz használni 😀
    IO.println(p.getAge());
    p.ageUp();
    IO.println(p.getAge());
}
