package Main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class ImagePanel extends JPanel {

    Image image;
    ImageObserver imageObserver;

    ImagePanel(String filename) {
        ImageIcon icon = new ImageIcon(filename);
        image = icon.getImage();
        imageObserver = icon.getImageObserver();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), imageObserver);
    }
}
