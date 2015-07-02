package cardStuff;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * Chapter 7 - Problem 30: Card Shuffling and Dealing
 * Poker hand rankings obtained from: http://www.wsop.com/poker-hands/
 * @author Douglas Skrypa
 * @version 2015.02.02
 * https://github.com/dskrypa/Java_Spring15
 */
public class DeckOfCards {
	private Card[] deck;
	private int currentCard;
	private static final int NUMBER_OF_CARDS = 52;
	private static final SecureRandom sr = new SecureRandom();
	
	public DeckOfCards() {
		deck = new Card[NUMBER_OF_CARDS];
		currentCard = 0;
		int c = 0;
		for (CardSuit cs : CardSuit.values()) {
			for (CardFace cf : CardFace.values()) {
				deck[c++] = new Card(cf, cs);
			}
		}
	}
	
	public int cardsRemaining() {
		return NUMBER_OF_CARDS - currentCard;
	}
	
	public void shuffle() {
		currentCard = 0;
		for (int a = 0; a < deck.length; a++) {
			int b = sr.nextInt(NUMBER_OF_CARDS);
			Card temp = deck[a];
			deck[a] = deck[b];
			deck[b] = temp;
		}
	}
	
	public Card dealCard() {
		if (currentCard < deck.length) {
			return deck[currentCard++];
		} else {
			return null;
		}
	}
	
	public Card[] dealHand(final int cards) {
		if (cards <= (deck.length-currentCard)) {
			Card[] hand = new Card[cards];
			for (int x = 0; x < cards; x++) {
				hand[x] = dealCard();
			}
			return hand;
		}
		return null;
	}
	
	/**
	 * Analyzes the given hand of cards for poker hands
	 * @param hand an array of cards representing a player's hand
	 * @return a String containing the name of the best poker hand for the given hand of cards
	 */
	public static String analyzeHand(final Card[] hand) {
		Arrays.sort(hand);
		Hashtable<CardFace, Integer> faceCount = new Hashtable<>();
		Hashtable<CardSuit, Integer> suitCount = new Hashtable<>();
		
		//Iterate through the cards in the given hand
		for (Card c : hand) {
			CardFace face = c.getFace();
			CardSuit suit = c.getSuit();
			
			//Keep count of the number of cards that have the same face
			if (faceCount.containsKey(face)) {
				faceCount.replace(face, faceCount.get(face)+1);
			} else {
				faceCount.put(face, 1);
			}
			
			//Keep count of the number of cards that have the same suit
			if (suitCount.containsKey(suit)) {
				suitCount.replace(suit, suitCount.get(suit)+1);
			} else {
				suitCount.put(suit, 1);
			}
		}
		
		//Determine whether or not the given hand contains 2-4 of a kind
		boolean has4f = false, has3f = false, has2f = false;
		for (Integer faceCounter : faceCount.values()) {
			switch (faceCounter) {
				case 4:	has4f = true;	break;
				case 3:	has3f = true;	break;
				case 2: has2f = true;	break;
			}
		}
		
		//Prepare to check for a straight
		boolean expand = false;
		int[] faceVals = new int[hand.length];
		for (int i = 0; i < hand.length; i++) {
			faceVals[i] = hand[i].getFace().ordinal();
			if (faceVals[i] == 0) {
				expand = true;
			}
		}
		if (expand) {
			faceVals = Arrays.copyOf(faceVals, faceVals.length+1);
			faceVals[faceVals.length-1] = 13;
		}
		Arrays.sort(faceVals);
		
		//Determine whether or not the given hand contains a straight
		int straight = 1;
		int lastVal = faceVals[0];
		for (int x = 1; x < faceVals.length; x++) {
			if ((faceVals[x] - lastVal) == 1) {
				straight++;
				lastVal = faceVals[x];
			} else if ((x == 1) && (faceVals.length > hand.length)) {
				lastVal = faceVals[x];
			} else {
				break;
			}
		}

		int numSuits = suitCount.size();
		
		//Return the name of the highest ranking Poker hand in the given set of cards
		if ((straight == hand.length) && (numSuits == 1)) {
			if ((faceVals.length > hand.length) && (faceVals[1] == 9)) {
				return "Royal Flush";
			}
			return "Straight Flush";
		} else if (has4f) {
			return "Four of a Kind";
		} else if (has3f && has2f) {
			return "Full House";
		} else if (numSuits == 1) {
			return "Flush";
		} else if (straight == hand.length) {
			return "Straight";
		} else if (has3f) {
			return "Three of a Kind";
		} else if (has2f && (faceCount.size() == 3)) {
			return "Two pairs";
		} else if (has2f) {
			return "Pair";
		}
		return "Nothing";
	}
	
	public static void printDeck(final Card[] deck) {
		for (int x = 0; x < deck.length; x++) {
			System.out.print(deck[x].toString());
			if (x < deck.length-1) {
				System.out.print(", ");
			}
		}
		System.out.println();
	}
}