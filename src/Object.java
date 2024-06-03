import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

public class Object implements Collider {
    private int image;
    private int animation_state;
    public String name;
    public int world_x, world_y;
    public Rectangle solidArea = new Rectangle(0, 0, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);


    public boolean tile_activated = false;
    public int tile_activation_counter = 0;
    public int minigame_affiliation = 0; //0 means no association


    private Object(){
        image = 0;
        animation_state = 0;
        name = "";
        world_x = 0;
        world_y = 0;
    }

    // public static Object key(int world_x, int world_y){
    //     var obj = new Object();
    //     obj.name = "Key";
    //     obj.image = 0;
    //     obj.world_x = world_x;
    //     obj.world_y = world_y;
    //     return obj;
    // }

    // public static Object coin(int world_x, int world_y){
    //     var obj = new Object();
    //     obj.name = "Coin";
    //     obj.image = 1;
    //     obj.world_x = world_x;
    //     obj.world_y = world_y;
    //     return obj;
    // }

    public static Object rock(int world_x, int world_y){
        var obj = new Object();
        obj.name = "Rock";
        obj.image = 0;
        obj.world_x = world_x;
        obj.world_y = world_y;
        obj.solidArea.height = 18 * GamePanel.SCALE;
        obj.solidArea.width = 18 * GamePanel.SCALE;
        return obj;
    }

    public static Object metal_plate(int world_x, int world_y){
        var obj = new Object();
        obj.name = "Plate";
        obj.image = 1;
        obj.world_x = world_x;
        obj.world_y = world_y;
        return obj;
    }

    public static Object trophe(int world_x, int world_y){
        var obj = new Object();
        obj.name = "Trophe";
        obj.image = 2;
        obj.world_x = world_x;
        obj.world_y = world_y;
        return obj;
    }

    public void update(GamePanel gp){
        if(name.equals("Rock")){
            update_rock(gp.player);
        } else
        if(name.equals("Plate")){
            update_plate(gp.objectManager);
        } else
        if(name.equals("Trophe")){
            if(CollisionChecker.check_intersection(gp.player, this)) {
                gp.player.trophe_count += 1;
                gp.objectManager.objects.remove(this);
            }
        }
    }

    public void update_rock(Player player){
        if(CollisionChecker.check_intersection(player, this)) {
            player.speed = player.maxSpeed - 3;

            world_y += player.vel_y;
            world_x += player.vel_x;
        } else {
            player.speed = player.maxSpeed;
        }

        // if(CollisionChecker.check_intersections(this, null)
        //check against other rocks..
    }

    public void update_plate(ObjectManager objectManager){
        var on_top_list = CollisionChecker.check_intersections(this, objectManager.objects);
        if(tile_activated){
            tile_activation_counter += 1;
        } else{
            tile_activation_counter = 0;
        }
        tile_activated = false;
        for (Integer integer : on_top_list) {
            if(objectManager.objects.get(integer).name.equals("Rock")){
                tile_activated = true;
                break;
            }
        }
    }

    public void draw(Graphics2D g2d, GamePanel gp){
        if(name.equals("Plate")){
            animation_state = tile_activated ? 1 : 0;
        }
        gp.screen_draw(gp.objectManager.sprites.get(image).get(animation_state), world_x, world_y, g2d);
    }

    @Override
    public Rectangle collider_rect() {
        return solidArea;
    }

    @Override
    public int world_x() {
        return world_x;
    }

    @Override
    public int world_y() {
        return world_y;
    }
}
