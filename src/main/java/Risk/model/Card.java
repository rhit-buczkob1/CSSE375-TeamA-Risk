package Risk.model;

import java.util.HashSet;
import java.util.Set;

public class Card {

	public String territory;
	public String troopType;
	public Card(String territory) {
		this.territory = territory;
	}

	public Card(String territory, String troopType) {
		this.territory = territory;
		this.troopType = troopType;
	}

	public String getTerritory() {
		return this.territory;
	}

	public String getTroopType() {
		return this.troopType;
	}

	private boolean isValidTroopType(String inputString){
		return inputString.equals("Infantry") || inputString.equals("Cavalry") || inputString.equals("Artillery");
	}


}
