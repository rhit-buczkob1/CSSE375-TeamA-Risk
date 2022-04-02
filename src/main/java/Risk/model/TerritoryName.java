package Risk.model;

import java.util.HashSet;
import java.util.Set;

public class TerritoryName {
    private String name;
    private Set<String> validTerritorryNames = new HashSet<>();

    public TerritoryName(String newName){
        populateValidTerritoryNames();
//        if(!isValidTerritoryName(newName)) throw new IllegalArgumentException(newName + " is not a valid territory.");
        name = newName;
    }

    public String getName(){
        return name;
    }



    private boolean isValidTerritoryName(String terriory){
        return validTerritorryNames.contains(terriory);
    }

    private void populateValidTerritoryNames(){
        validTerritorryNames.add("Eastern Australia");
        validTerritorryNames.add("Western Australia");
        validTerritorryNames.add("New Guinea");
        validTerritorryNames.add("Indonesia");
        validTerritorryNames.add("Southeast Asia");
        validTerritorryNames.add("India");
        validTerritorryNames.add("China");
        validTerritorryNames.add("Middle East");
        validTerritorryNames.add("Afghanistan");
        validTerritorryNames.add("Japan");
        validTerritorryNames.add("Mongolia");
        validTerritorryNames.add("Siberia");
        validTerritorryNames.add("Ural");
        validTerritorryNames.add("Irkutsk");
        validTerritorryNames.add("Yakutsk");
        validTerritorryNames.add("Kamchatka");
        validTerritorryNames.add("Madagascar");
        validTerritorryNames.add("South Africa");
        validTerritorryNames.add("Central Africa");
        validTerritorryNames.add("East Africa");
        validTerritorryNames.add("Egypt");
        validTerritorryNames.add("North Africa");
        validTerritorryNames.add("Argentina");
        validTerritorryNames.add("Peru");
        validTerritorryNames.add("Brazil");
        validTerritorryNames.add("Venezuela");
        validTerritorryNames.add("Central America");
        validTerritorryNames.add("Eastern United States");
        validTerritorryNames.add("Western United States");
        validTerritorryNames.add("Eastern Canada");
        validTerritorryNames.add("Ontario");
        validTerritorryNames.add("Alberta");
        validTerritorryNames.add("Alaska");
        validTerritorryNames.add("Northwest Territory");
        validTerritorryNames.add("Greenland");
        validTerritorryNames.add("Iceland");
        validTerritorryNames.add("Great Britain");
        validTerritorryNames.add("Scandinavia");
        validTerritorryNames.add("Russia");
        validTerritorryNames.add("Northern Europe");
        validTerritorryNames.add("Southern Europe");
        validTerritorryNames.add("Western Europe");
    }

}