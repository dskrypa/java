package cardStuff;

/**
 * Chapter 7 - Problem 30: Card Shuffling and Dealing
 * @author Douglas Skrypa
 * @version 2015.02.02
 * https://github.com/dskrypa/Java_Spring15
 */
public class Card implements Comparable<Card> 
{
	private final CardFace face;
	private final CardSuit suit;
	
	public Card (final CardFace cardFace, final CardSuit cardSuit) {
		face = cardFace;
		suit = cardSuit;
	}
	
	public Card (final String strRep) {
		String lcsr = strRep.toLowerCase();
		if (lcsr.length() == 3) {
			face = CardFace.getFace(lcsr.substring(0,2));
			suit = CardSuit.getSuit(lcsr.charAt(2));
		} else if (lcsr.length() == 2) {
			face = CardFace.getFace(lcsr.charAt(0));
			suit = CardSuit.getSuit(lcsr.charAt(1));
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public String toString() {
		return face.toString() + " of " + suit.toString();
	}

	public CardFace getFace() {
		return face;
	}

	public CardSuit getSuit() {
		return suit;
	}

	public boolean matchesTP(Card o) {
		int mfo = this.getFace().ordinal();
		int ofo = o.getFace().ordinal();
		return (mfo == 0 && ofo == 12) || (mfo == 12 && ofo == 0) || (Math.abs(mfo-ofo) == 1);
	}
	
	@Override
	public int compareTo(Card o) {
		if (this.getSuit() != o.getSuit()) {
			return this.getSuit().compareTo(o.getSuit());
		} else {
			return this.getFace().compareTo(o.getFace());
		}
	}
}
