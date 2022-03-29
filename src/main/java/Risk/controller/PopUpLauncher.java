package Risk.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class PopUpLauncher implements ActionListener {
    private GameFlowController gfController;

    public PopUpLauncher(GameFlowController gfController) {
        this.gfController = gfController;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }

                JPanel panel = new JPanel();
                panel.add(new JLabel(gfController.messages.getString("changeLangPrompt")));
                DefaultComboBoxModel model = new DefaultComboBoxModel();
                model.addElement(gfController.messages.getString("eng"));
                model.addElement(gfController.messages.getString("ger"));
                JComboBox comboBox = new JComboBox(model);
                panel.add(comboBox);

                int result = JOptionPane.showConfirmDialog(
                        null,
                        panel,
                        "Language",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if(result == JOptionPane.OK_OPTION) {
                    String lang = (String) comboBox.getSelectedItem();
                    Locale l;
                    if (lang == gfController.messages.getString("eng")) {
                        l = new Locale("en", "US");
                    } else {
                        l = new Locale("de", "DE");
                    }
                    gfController.messages = ResourceBundle.getBundle("MessagesBundle", l);
                    gfController.gui.setLanguage(gfController.messages, gfController.getPhase());
                    gfController.updateCardsOnGui();
                }
            }
        });
    }
}
