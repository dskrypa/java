package cardStuff;

/**
 * Chapter 7 - Problem 30: Card Shuffling and Dealing
 * @author Douglas Skrypa
 * @version 2015.02.02
 * https://github.com/dskrypa/Java_Spring15
 */
public enum CardSuit {
	HEARTS("Hearts"),DIAMONDS("Diamonds"),CLUBS("Clubs"),SPADES("Spades");
	
	private final String suitName;
	
	CardSuit(final String suitName){
		this.suitName = suitName;
	}
	
	public static CardSuit getSuit(final String suitName) {
		return getSuit(suitName.charAt(0));
	}
	
	public static CardSuit getSuit(final char suitAbbrev) {
		switch (Character.toLowerCase(suitAbbrev)) {
			case 'h':	return HEARTS;
			case 'd':	return DIAMONDS;
			case 'c':	return CLUBS;
			case 's':	return SPADES;
			default:	throw new IllegalArgumentException();
		}
	}
	
	public String toString() {
		return suitName;
	}
}
