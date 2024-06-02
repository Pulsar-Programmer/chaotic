import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ObjectManager {
    ArrayList<ArrayList<BufferedImage>> sprites;
    ArrayList<Object> objects;

    public ObjectManager(){
        setupSprites();
        objects = new ArrayList<Object>();
    }

    public void setupSprites(){
        sprites = new ArrayList<ArrayList<BufferedImage>>();
        try {
            var rock = new ArrayList<BufferedImage>();
            rock.add(App.res("res/objects/escape_room/2.png"));
            sprites.add(rock);

            var plate = new ArrayList<BufferedImage>();
            plate.add(App.res("res/objects/escape_room/3.png"));
            sprites.add(plate);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(GamePanel gp){
        for(var elem : objects){
            elem.update(gp.player);
        }
    }

    public void draw(Graphics2D g2d, GamePanel gp){
        for(var elem : objects){
            elem.draw(g2d, gp);
        }
    }
}
