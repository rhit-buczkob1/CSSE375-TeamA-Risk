package Risk.model;

import java.util.HashSet;
import java.util.Set;

public class Card {

	public String territory;
	public String troopType;
	private Set<String> validTerritories = new HashSet<>();
	public Card(String territory) {
		this.territory = territory;
	}

	public Card(String territory, String troopType) {
		populateValidTerritoreis();
		if(!isValidTroopType(troopType)) throw new IllegalArgumentException(troopType + " is not a valid troop type.");
		else if(!isValidTerritory(territory)) throw new IllegalArgumentException(territory + " is not a valid territory.");

		else {
			this.territory = territory;
			this.troopType = troopType;
		}
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

	private boolean isValidTerritory(String terriory){
		return validTerritories.contains(terriory);
	}

	private void populateValidTerritoreis(){
		validTerritories.add("Eastern Australia");
		validTerritories.add("Western Australia");
		validTerritories.add("New Guinea");
		validTerritories.add("Indonesia");
		validTerritories.add("Southeast Asia");
		validTerritories.add("India");
		validTerritories.add("China");
		validTerritories.add("Middle East");
		validTerritories.add("Afghanistan");
		validTerritories.add("Japan");
		validTerritories.add("Mongolia");
		validTerritories.add("Siberia");
		validTerritories.add("Ural");
		validTerritories.add("Irkutsk");
		validTerritories.add("Yakutsk");
		validTerritories.add("Kamchatka");
		validTerritories.add("Madagascar");
		validTerritories.add("South Africa");
		validTerritories.add("Central Africa");
		validTerritories.add("East Africa");
		validTerritories.add("Egypt");
		validTerritories.add("North Africa");
		validTerritories.add("Argentina");
		validTerritories.add("Peru");
		validTerritories.add("Brazil");
		validTerritories.add("Venezuela");
		validTerritories.add("Central America");
		validTerritories.add("Eastern United States");
		validTerritories.add("Western United States");
		validTerritories.add("Eastern Canada");
		validTerritories.add("Ontario");
		validTerritories.add("Alberta");
		validTerritories.add("Alaska");
		validTerritories.add("Northwest Territory");
		validTerritories.add("Greenland");
		validTerritories.add("Iceland");
		validTerritories.add("Great Britain");
		validTerritories.add("Scandinavia");
		validTerritories.add("Russia");
		validTerritories.add("Northern Europe");
		validTerritories.add("Southern Europe");
		validTerritories.add("Western Europe");
	}
}
