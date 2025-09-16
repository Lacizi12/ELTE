
public enum Rank { ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING }
public enum Suit { SPADES, CLUBS, HEARTS, DIAMONDS }
public enum GirlsBestFriend { DIAMONDS }

public record Card(Rank rank, Suit suit) {}

void main() {
    var rank = Rank.valueOf(IO.readln("Enter the name of a rank: "));
    var suit = Suit.valueOf(IO.readln("Enter the name of a suit: "));

    IO.println(new Card(rank, suit));
    IO.println(rank.ordinal() + 1);     // nullától indul
    IO.println(suit.ordinal() + 1);
}
