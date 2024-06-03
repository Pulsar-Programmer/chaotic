import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

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
            rock.add(App.res("res/objects/escape_room/rock_1.png"));
            sprites.add(rock);

            var plate = new ArrayList<BufferedImage>();
            plate.add(App.res("res/objects/escape_room/plate.png"));
            plate.add(App.res("res/objects/escape_room/plate_activated.png"));
            sprites.add(plate);

            var trophe = new ArrayList<BufferedImage>();
            trophe.add(App.res("res/objects/awards/ultimate.png"));
            sprites.add(trophe);

            var door = new ArrayList<BufferedImage>();
            door.add(App.res("res/tiles/blue/door/north_closed.png"));
            door.add(App.res("res/tiles/blue/door/north_open.png"));
            sprites.add(door);

            var key = new ArrayList<BufferedImage>();
            key.add(App.res("res/objects/key.png"));
            sprites.add(key);

            var heart = new ArrayList<BufferedImage>();
            heart.add(App.res("res/objects/heart.png"));
            sprites.add(heart);

            var coin = new ArrayList<BufferedImage>();
            coin.add(App.res("res/objects/coin.png"));
            sprites.add(coin);

            var toll = new ArrayList<BufferedImage>();
            toll.add(App.res("res/objects/toll.png"));
            sprites.add(toll);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(GamePanel gp){
        for(var i = 0; i < objects.size(); i++){
            update_minigame_checker(gp);
            objects.get(i).update(gp);
        }
    }

    public void update_minigame_checker(GamePanel gp){
        var all_activated_hashmap = new HashMap<Integer, Boolean>();
        for (var object : objects) {
            if(object.minigame_affiliation != 0 && !object.name.equals("Door")){
                all_activated_hashmap.putIfAbsent(object.minigame_affiliation, true);
                all_activated_hashmap.put(object.minigame_affiliation, all_activated_hashmap.get(object.minigame_affiliation) && object.tile_activation_counter >= 120);
            }
        }
        all_activated_hashmap.forEach((a, b) -> {
            if(b){
                for(var i = 0; i < objects.size(); i++){
                    if(objects.get(i).minigame_affiliation == a){
                        if(objects.get(i).name.equals("Door")){
                            objects.get(i).tile_activated = true;
                            continue;
                        }
                        objects.remove(i);
                        i--;
                    }
                }
                objects.add(Object.trophe(gp.player.world_x + GamePanel.TILE_SIZE, gp.player.world_y));
            }
        });
    }

    public void draw(Graphics2D g2d, GamePanel gp){
        for(var elem : objects){
            elem.draw(g2d, gp);
        }
    }
}
