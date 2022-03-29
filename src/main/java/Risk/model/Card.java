package Risk.model;

public class Card {

	public String territory;
	public String troopType;

	public Card(String territory) {
		this.territory = territory;
	}

	public Card(String territory, String troopType) {
		if(!isValidTroopType(troopType)) throw new IllegalArgumentException(troopType + "is not a valid troop type.");
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
