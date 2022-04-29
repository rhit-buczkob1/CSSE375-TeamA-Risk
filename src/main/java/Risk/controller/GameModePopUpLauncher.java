package Risk.controller;

import Risk.view.GraphicalUserInterface;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class GameModePopUpLauncher implements EventHandler<MouseEvent> {

    GameFlowController gfc;

    public GameModePopUpLauncher(GameFlowController gfc) {
        this.gfc = gfc;
    }

    @Override
    public void handle(MouseEvent event) {
        Stage panel = new Stage();
        Text prompt = new Text(gfc.messages.getString("changeLangPrompt"));
        ComboBox<String> box = new ComboBox<String>();
        box.getItems().add("Normal");
        box.getItems().add("Headquarters");
        box.setId("gameModeSelectionBox");

        Button confirm = new Button(gfc.messages.getString("confirm"));
        confirm.setId("confirmModeChange");
        Button cancel = new Button(gfc.messages.getString("cancel"));
        cancel.setId("cancelModeChange");

        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                String mode = box.getValue();
                if (mode == "Headquarters") {
                    gfc.swapToAlternatePhaseController("HEADQUARTERS");
                } else {
                    gfc.swapToAlternatePhaseController("NORMAL");
                }
                panel.close();
            }

        });

        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                panel.close();
            }

        });

        GridPane grid = new GridPane();
        grid.add(prompt, 0, 0);
        grid.add(box, 1, 0);
        grid.add(confirm, 0, 1);
        grid.add(cancel, 1, 1);

        panel.setScene(new Scene(grid));
        panel.show();
    }
}

