package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {

    private BufferedImage img;
    private Game game;

    public GamePanel(Game game) {
        importImg();
        setPanelSize();
        this.game = game;
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/enemy_1.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPanelSize() {
        setPreferredSize(new Dimension(1920, 1080));
    }

    public void updateGame() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);

        //g.drawImage(img, 100, 100, null);

    }
}
