package cardStuff;

import java.io.IOException;
import java.util.ArrayList;

import common.BFWriter;

/**
 * Chapter 7 - Problem 30: Card Shuffling and Dealing
 * @author Douglas Skrypa
 * @version 2015.02.05
 * https://github.com/dskrypa/Java_Spring15
 */
public class CardTester {
	public static void main(final String[] args) throws IOException {
		DeckOfCards doc = new DeckOfCards();
		testToConsole(doc);
		//testToFile(doc);
	}
	
	public static void testToConsole(final DeckOfCards doc) {
		for (String line : processDeck(doc)) {
			System.out.println(line);
		}
	}
	
	public static void testToFile(final DeckOfCards doc) throws IOException {
		int numShuffles = 10000;
		BFWriter bfw = new BFWriter("./temp.txt");
		for (int x = 0; x < numShuffles; x++) {
			for (String line : processDeck(doc)) {
				bfw.write(line + "\n");
			}
		}
		bfw.close();
	}
	
	public static ArrayList<String> processDeck(final DeckOfCards doc) {
		ArrayList<String> output = new ArrayList<>();
		doc.shuffle();
		while (doc.cardsRemaining() >= 5) {	
			Card[] hand = doc.dealHand(5);
			String analysis = DeckOfCards.analyzeHand(hand);
			String contents = getHandString(hand);
			output.add(contents + "\t:\t" + analysis);
		}
		return output;
	}
	
	public static String getHandString(final Card[] hand) {
		String toPrint = "";
		for (Card c : hand) {
			if (toPrint.length() > 0) {
				toPrint += ", ";
			}
			toPrint += c.toString();
		}
		return toPrint;
	}
}
