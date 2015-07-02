package cardStuff;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class TriPeaksRunner {
	private static final int[][] l4i = {{0,1},{2,3},{4,5}};
	private static final int[][] l3i = {{0,1},{1,2},{3,4},{4,5},{6,7},{7,8}};
	private static final int[][] l2i = {{0,1},{1,2},{2,3},{3,4},{4,5},{5,6},{6,7},{7,8},{8,9}};
	
	private static final SecureRandom sr = new SecureRandom();
	
	private static final int maxSims = 1000000;
	
	private TreeSet<Card> available;
	private final Card[] deck,l1,l2,l3,l4;
	
	private List<Card> al1;
	private ArrayList<Card> used;
	private Card currentCard;
	
	
	public static void main(final String[] args) {
		String deck = "jd,qd,7c,ks,4c,8h,2h,7h,ad,6d,qc,jh,9c,qs,js,7d,as,9s,ac,th,2s,tc,3h,qh";
		String l1 = "kh,4s,6h,5c,5s,7s,3d,5d,jc,ah";
		String l2 = "6c,kd,kc,2d,5h,td,ts,9h,9d";
		String l3 = "4h,8d,8s,4d,3c,8c";
		String l4 = "6s,3s,2c";
		
		
		TriPeaksRunner tpr = new TriPeaksRunner(deck,l1,l2,l3,l4);
		
		int most = 0;
		
		boolean found = false;
		for (int s = 0; (s < maxSims) && !found; s++) {
			System.out.printf("[%6d] Testing...", s);
			ArrayList<Card> result = tpr.runSim();
			
			most = (result.size() > most ? result.size() : most);
			
			if ((result.size() == 52) || s == maxSims-1) {
				found = true;
				System.out.println("\tSUCCESS!!");
				for (Card rc : result) {
					System.out.println(rc.toString());
				}
			} else {
				System.out.println("\tFAIL: " + result.size());
			}
			
		}
		
		System.out.println("Most matches: " + most);
	}
	
	
	public TriPeaksRunner(final String deckStr, final String l1s, final String l2s, final String l3s, final String l4s) {
		deck = cardParser(deckStr).toArray(new Card[0]);
		l1 = cardParser(l1s).toArray(new Card[0]);	l2 = cardParser(l2s).toArray(new Card[0]);
		l3 = cardParser(l3s).toArray(new Card[0]);	l4 = cardParser(l4s).toArray(new Card[0]);
	}
	
	private void initSim() {
		al1 = Arrays.asList(l1);
		available = new TreeSet<>();
		available.addAll(al1);
		
		used = new ArrayList<>();
		
		currentCard = null;
	}
	
	private void setCurrentCard(final Card c) {
		currentCard = c;
		if (used.contains(c)) {
			throw new IllegalArgumentException("Unable to use a card multiple times!");
		}
		used.add(c);
		if (available.contains(c)) {
			available.remove(c);
		}
	}
	
	public ArrayList<Card> runSim() {
		initSim();
		
		for (int i = 0; i < deck.length; i++) {
			setCurrentCard(deck[i]);
			
			boolean scanning = true;
			while (scanning) {
				ArrayList<Card> matches = new ArrayList<>();
				for (Card a : available) {
					if (currentCard.matchesTP(a)) {
						matches.add(a);
					}
				}
				
				if ((matches.size() < 1) || ((matches.size() <= 3) && (sr.nextInt(100) < 40))) {
					scanning = false;
				} else {
					int pi = sr.nextInt(matches.size());
					Card use = matches.get(pi);
					setCurrentCard(use);
					updateUsable();
				}
			}
			
		}
		
		return used;
	}
	
	
	private void updateUsable() {
		for (int l4t = 0; l4t < 3; l4t++) {
			if (used.contains(l3[l4i[l4t][0]]) && used.contains(l3[l4i[l4t][1]]) && !used.contains(l4[l4t])) {
				available.add(l4[l4t]);
			}
		}
		
		for (int l3t = 0; l3t < 6; l3t++) {
			if (used.contains(l2[l3i[l3t][0]]) && used.contains(l2[l3i[l3t][1]]) && !used.contains(l3[l3t])) {
				available.add(l3[l3t]);
			}
		}
		
		for (int l2t = 0; l2t < 9; l2t++) {
			if (used.contains(l1[l2i[l2t][0]]) && used.contains(l1[l2i[l2t][1]]) && !used.contains(l2[l2t])) {
				available.add(l2[l2t]);
			}
		}
	}
	
	private static List<Card> cardParser(final String cardStr) {
		String[] cardArr = cardStr.split("\\s*,");
		List<Card> cards = new ArrayList<>();
		for (int c = 0; c < cardArr.length; c++) {
			cards.add(new Card(cardArr[c]));
		}
		return cards;
	}
}