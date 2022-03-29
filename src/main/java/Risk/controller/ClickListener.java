package Risk.controller;

import Risk.model.Territory;
import Risk.view.GraphicalUserInterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickListener implements MouseListener {

    private Point lastClick = null;
    private GraphicalUserInterface.GuiComponent guiComponent;
    private GameFlowController gfController;

    public ClickListener(GameFlowController gfController) {
        this.gfController = gfController;
    }

    /*
     * Returns the point that was most recently clicked, and then sets lastClick to
     * null.
     */
    public Point getLastClick() {
        if (lastClick == null) {
            return null;
        }
        Point toReturn = new Point(lastClick.x, lastClick.y);
        lastClick = null;

        return toReturn;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        lastClick = new Point(x, y);
        String lastclickedstring = gfController.gui.checkForPointOnTerritory();
        lastclickedstring = lastclickedstring.replace("_", " ");
        Territory toupdate = gfController.gbcontroller.getTerritory(lastclickedstring);
        gfController.gui.currentTerritoryArmyCount = toupdate.getArmyCount();
        gfController.gui.currentTerritoryPlayer = toupdate.getPlayer();
        if (this.guiComponent != null) {
            this.guiComponent.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {}

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}

    @Override
    public void mouseReleased(MouseEvent event) {}

    public void addComponent(GraphicalUserInterface.GuiComponent component) {
        this.guiComponent = component;
    }
}
