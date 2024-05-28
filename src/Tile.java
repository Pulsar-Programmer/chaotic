import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class Tile {
    //I know, i wish i brought earbuds??? its ok 
    //what i did not see what you wrote?? explain the thing you just did rq...
    //sure inside collision manager i checked to see if the tile is teleporter. 
    //How are you designationg that the tile is a teleort tile? By a certain sprite or what? just set it to like sprite 11. Yeah something like that 
    //If it is, I teleport the player. That's it i think we can put this in collision then right?
    //Yeah it already is I just put it in yeah, but it needs some work. Dude I wish we could vc .
    //yeah actually we can do that if we do what we did for the collision basically
    //wait we need to set something to it first
    //arkin lets test it ???
    //Arkin???????
    public Optional<Point> teleporter; //set it to
    
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
        teleporter = Optional.empty();
    }
    public static Tile with_collision(int sprite, int x, int y){
        var tile = new Tile(sprite, x, y);
        tile.has_collision = true;
        return tile;
    }
}
