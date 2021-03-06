package bases;

import java.util.LinkedList;
import java.util.List;

public class PairingsManager {

	private List<Pairing> pairings;
	
	public PairingsManager() {
		pairings = new LinkedList<Pairing>();
	}

	public void addPairing(Pairing pairing) {
		pairings.add(pairing);
	}

	public boolean contains(char base) {
		for (final Pairing pairing : pairings) {
			if (pairing.contains(base))
				return true;
		}
		return false;
	}

	public char getComplementaryOf(char base) {
		for (final Pairing pairing : pairings) {
			if (base == pairing.getFirstBase())
				return pairing.getSecondBase();
			if (base == pairing.getSecondBase())
				return pairing.getFirstBase();
		}
		return (char) -1;
	}

	public int nbPairings() {
		return pairings.size();
	}
	
	@Override
	public String toString() {
		return pairings.toString();
	}

}
