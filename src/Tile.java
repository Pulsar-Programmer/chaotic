import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class Tile {
    public Optional<Point> teleporter;
    public Optional<Integer> map_to_load; 

    int sprite;
    public boolean has_collision;

    public void draw(Graphics2D g2d, int tileSize, BufferedImage[] tiles, int x, int y){
        g2d.drawImage(tiles[sprite], x, y, tileSize, tileSize, null);
    }

    public Tile(int sprite){
        this.sprite = sprite;
        has_collision = false;
        teleporter = Optional.empty();
    }
    public static Tile with_collision(int sprite){
        var tile = new Tile(sprite);
        tile.has_collision = true;
        return tile;
    }
}
