package Risk.model;

import java.util.HashSet;
import java.util.Set;

public class Card {

	public TerritoryName territory;
	public String troopType;
	public Card(String territory) {
		this.territory = new TerritoryName(territory);
	}

	public Card(String territory, String troopType) {
		if(!isValidTroopType(troopType)) throw new IllegalArgumentException(troopType + " is not a valid troop type.");

		else {
			this.territory = new TerritoryName(territory);
			this.troopType = troopType;
		}
	}

	public String getTerritory() {
		return this.territory.getName();
	}

	public String getTroopType() {
		return this.troopType;
	}

	private boolean isValidTroopType(String inputString){
		return inputString.equals("Infantry") || inputString.equals("Cavalry") || inputString.equals("Artillery");
	}


}
