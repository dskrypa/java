package cardStuff;

/**
 * Chapter 7 - Problem 30: Card Shuffling and Dealing
 * @author Douglas Skrypa
 * @version 2015.02.02
 * https://github.com/dskrypa/Java_Spring15
 */
public enum CardFace {
	ACE("Ace"),DEUCE("Deuce"),THREE("Three"),FOUR("Four"),FIVE("Five"),
	SIX("Six"),SEVEN("Seven"),EIGHT("Eight"),NINE("Nine"),
	TEN("Ten"),JACK("Jack"),QUEEN("Queen"),KING("King");
	
	private final String faceName;
	
	CardFace(final String faceName) {
		this.faceName = faceName;
	}
	
	public static CardFace getFace(final String faceName) {
		if (faceName.equals("10")) {
			return getFace('t');
		}
		return getFace(faceName.charAt(0));
	}
	
	public static CardFace getFace(final char faceAbbrev) {
		switch (Character.toLowerCase(faceAbbrev)) {
			case 'a':	case '1':	return ACE;
			case '2':	return DEUCE;
			case '3':	return THREE;
			case '4':	return FOUR;
			case '5':	return FIVE;
			case '6':	return SIX;
			case '7':	return SEVEN;
			case '8':	return EIGHT;
			case '9':	return NINE;
			case 't':	return TEN;
			case 'j':	return JACK;
			case 'q':	return QUEEN;
			case 'k':	return KING;
			default:	throw new IllegalArgumentException();
		}
	}
	
	public String toString() {
		return faceName;
	}
}
