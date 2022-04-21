package Risk.controller;

import Risk.view.GraphicalUserInterface;
import com.sun.javafx.application.PlatformImpl;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.easymock.EasyMock;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import org.testfx.framework.junit.ApplicationTest;
import org.testfx.robot.Motion;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AlternatePhaseControllerTests extends ApplicationTest{

    Locale locale = new Locale("en", "US");
    ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", locale);

    @Override
    public void start(Stage stage) throws Exception {
        GraphicalUserInterface gui = new GraphicalUserInterface(msg, stage);
        Parent parent = gui.chooseGameMode;
        Scene scene = new Scene(parent, 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void test_button_existance() {
        verifyThat(".button", hasText("Choose Game Mode"));
    }
}
