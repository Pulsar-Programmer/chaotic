import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ObjectManager {
    BufferedImage[] sprites;
    ArrayList<Object> objects;

    public ObjectManager(){
        setupSprites();
        objects = new ArrayList<Object>();
    }

    public void setupSprites(){
        sprites = new BufferedImage[3];
        try {
            sprites[0] = ImageIO.read(new File("res/player/walk/boy_down_1.png"));
            sprites[1] = ImageIO.read(new File("res/tiles/blue/door/north_closed.png"));
            sprites[2] = ImageIO.read(new File("res/tiles/placeholder/Light.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d, GamePanel gp){
        for(var elem : objects){
            elem.draw(g2d, gp);
        }
    }
}
