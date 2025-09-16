public enum Rank { ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING }
public enum Suit { SPADES, CLUBS, HEARTS, DIAMONDS }
public enum GirlsBestFriend { DIAMONDS }

public record Card(Rank rank, Suit suit) {}

void main() {
    var rank = Rank.ACE;
    var suit = Suit.SPADES;

    IO.println(rank);
    IO.println(suit);
    // Card[rank=ACE, suit=SPADES]

    IO.println(Rank.ACE == Rank.THREE);    // false
    // IO.println(Suit.DIAMONDS == GirlsBestFriend.DIAMONDS); // does not compile
    // IO.println(Suit.DIAMONDS == GirlsBestFriend.DIAMONDS); // nem fordul le
}
