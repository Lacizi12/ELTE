
void main() {
    var p = new Person("Anonymous", 0);
    IO.println(p.toString()); // a fenti toString nélkül: Person@<zaj>
    IO.println(p.toString()); // a fenti toStringgel    : Anonymous (0)
    IO.println(p);            // mivel p típusa nem String, áthív a toStringre
}
