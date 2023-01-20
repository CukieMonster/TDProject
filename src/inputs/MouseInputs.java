package inputs;

import main.Game;
import main.GamePanel;
import towers.TowerManager;
import ui.ButtonManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private static MouseInputs instance;

    private MouseInputs() {

    }

    public static MouseInputs getInstance() {
        if (instance == null) {
            instance = new MouseInputs();
        }
        return instance;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        ButtonManager.getInstance().mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ButtonManager.getInstance().mouseReleased(e);
        TowerManager.getInstance().mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        ButtonManager.getInstance().mouseMoved(e);
        TowerManager.getInstance().mouseMoved(e);
    }
}
