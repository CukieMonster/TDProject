package main;

import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {

    private static GamePanel instance;
    private BufferedImage img;

    private GamePanel() {
        setPanelSize();
        addMouseListener(MouseInputs.getInstance());
        addMouseMotionListener(MouseInputs.getInstance());
    }

    public static GamePanel getInstance() {
        if (instance == null) {
            instance = new GamePanel();
        }
        return instance;
    }

    private void setPanelSize() {
        setPreferredSize(new Dimension(1920, 1080));
        //setPreferredSize(new Dimension(1280, 720));
    }

    public void updateGame() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Game.getInstance().render(g);

        //g.drawImage(img, 100, 100, null);

    }
}
