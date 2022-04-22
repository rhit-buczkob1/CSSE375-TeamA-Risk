package Risk.controller;

import Risk.model.Territory;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerSetListener implements EventHandler<MouseEvent> {

    GameFlowController gfc;

    public PlayerSetListener(GameFlowController gfc) {
        this.gfc = gfc;
    }


    @Override
    public void handle(MouseEvent event) {
        int numPlayers = (int)gfc.gui.numPlayersSlider.getValue();
        String map = "";
        if(gfc.gui.maps.getValue().equals("Europe")) {
            map = "-europe";
        } else map = "-globe";

            try{
            gfc.gbcontroller.map = map;
            gfc.gbcontroller.territoryController.map = map;
            gfc.gbcontroller.initGame();
            int armiesPerPlayer = gfc.playercontroller.setNumberOfPlayers(numPlayers);
            gfc.gui.setNumPlayers(gfc.playercontroller.getNumberOfPlayers() + "");
            gfc.gui.setCurrentPlayerArmies(armiesPerPlayer + "");
            gfc.gui.changeBackground();

        } catch (Exception e){
            System.out.println(e);
        }



    }

}