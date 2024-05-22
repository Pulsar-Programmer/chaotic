import java.awt.Graphics2D;
import java.io.File;

import javax.imageio.ImageIO;

public class Tile {
    
    public java.awt.image.BufferedImage image;
    public boolean collision = false;
    int x, y;

    public void draw(Graphics2D g2d, int tileSize){
        g2d.drawImage(image, x, y, tileSize, tileSize, null);
    }

    public Tile(String filepath, int x, int y){
        this.x = x;
        this.y = y;
        try {
            image = ImageIO.read(new File(filepath));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
