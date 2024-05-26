import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Tile {
    
    int sprite;
    public boolean has_collision;
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
        has_collision = false;
    }
    public static Tile with_collision(int sprite, int x, int y){
        var tile = new Tile(sprite, x, y);
        tile.has_collision = true;
        return tile;
    }
}
