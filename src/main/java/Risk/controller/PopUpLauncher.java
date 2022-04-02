package Risk.controller;

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

public class PopUpLauncher implements EventHandler<MouseEvent> {
    
	GameFlowController gfc;
	
	public PopUpLauncher(GameFlowController gfc) {
		this.gfc = gfc;
	}
	
	@Override
	public void handle(MouseEvent event) {
			Stage panel = new Stage();
			Text prompt = new Text(gfc.messages.getString("changeLangPrompt"));
			ComboBox<String> box = new ComboBox<String>();
			box.getItems().add(gfc.messages.getString("eng"));
			box.getItems().add(gfc.messages.getString("ger"));
			
			Button confirm = new Button(gfc.messages.getString("confirm"));
			Button cancel = new Button(gfc.messages.getString("cancel"));
			
			confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					String lang = box.getValue();
					Locale l;
					if (lang == gfc.messages.getString("eng")) {
						l = new Locale("en", "US");
					} else {
						l = new Locale("de", "DE");
					}
					gfc.messages = ResourceBundle.getBundle("MessagesBundle", l);
					gfc.gui.setLanguage(gfc.messages, gfc.getPhase());
					gfc.updateCardsOnGui();
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
