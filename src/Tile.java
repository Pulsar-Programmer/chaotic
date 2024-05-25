import java.awt.Graphics2D;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Tile {
    
    int sprite;
    public boolean has_collision = false;
    int x, y;

    public void draw(Graphics2D g2d, int tileSize, BufferedImage[] tiles){
        g2d.drawImage(tiles[sprite], x, y, tileSize, tileSize, null);
    }

    public void draw(Graphics2D g2d, int tileSize, BufferedImage[] tiles, int x, int y){
        g2d.drawImage(tiles[sprite], x, y, tileSize, tileSize, null);
    }

    public Tile(int sprite, int x, int y){
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }
}
