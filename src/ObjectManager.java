import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ObjectManager {
    BufferedImage[][] sprites;
    ArrayList<Object> objects;

    public ObjectManager(){
        setupSprites();
        objects = new ArrayList<Object>();
    }

    public void setupSprites(){
        sprites = new BufferedImage[3][3];
        try {
            //key
            sprites[0][0] = ImageIO.read(new File("res/player/walk/boy_down_1.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d, GamePanel gp){
        for(var elem : objects){
            elem.draw(g2d, gp);
        }
    }

    public void setupObjects(){
        // objects.add(Object.key(32*5, 32*12));
        // objects.add(Object.door(32*10, 32*10));
    }
}
