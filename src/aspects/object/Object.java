package aspects.object;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Optional;

import aspects.Animation;
import collision.Collider;
import collision.CollisionChecker;
import main.GamePanel;

public class Object implements Collider {
    private int image;
    private int animation_state;
    public String name;
    public int world_x, world_y;
    public Rectangle solidArea = new Rectangle(0, 0, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);

    public boolean tile_activated = false;
    public int tile_activation_counter = 0;
    public int minigame_affiliation = 0; //0 means no association
    public int healing_power = 0;
    public int toll_amount = 0;
    public Optional<Boolean> painted_rock = Optional.empty();
    
    public Optional<Point> teleporter = Optional.empty();

    public boolean is_vertical = false;
    public Animation electric = new Animation();
    public boolean movable = false;

    private Object(){
        image = 0;
        animation_state = 0;
        name = "";
        world_x = 0;
        world_y = 0;
    }

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

    public static Object door(int world_x, int world_y){
        var obj = new Object();
        obj.name = "Door";
        obj.image = 3;
        obj.world_x = world_x;
        obj.world_y = world_y;
        return obj;
    }

    public static Object key(int world_x, int world_y){
        var obj = new Object();
        obj.name = "Key";
        obj.image = 4;
        obj.world_x = world_x;
        obj.world_y = world_y;
        return obj;
    }

    public static Object heart(int world_x, int world_y, int healing_power){
        var obj = new Object();
        obj.name = "Heart";
        obj.image = 5;
        obj.world_x = world_x;
        obj.world_y = world_y;
        obj.healing_power = healing_power;
        return obj;
    }

    public static Object coin(int world_x, int world_y){
        var obj = new Object();
        obj.name = "Coin";
        obj.image = 6;
        obj.world_x = world_x;
        obj.world_y = world_y;
        // obj.healing_power = healing_power; rename to worth and use it for coin, too lol
        return obj;
    }

    public static Object toll(int world_x, int world_y, int toll_amount){
        var obj = new Object();
        obj.name = "Toll";
        obj.image = 7;
        obj.world_x = world_x;
        obj.world_y = world_y;
        obj.toll_amount = toll_amount;
        return obj;
    }

    public static Object wire(int world_x, int world_y, boolean is_vertical){
        var obj = new Object();
        obj.is_vertical = is_vertical;
        if(is_vertical){
            obj.solidArea = new Rectangle(8 * GamePanel.SCALE, 0, 4 *  GamePanel.SCALE, GamePanel.TILE_SIZE);
        } else{
            obj.solidArea = new Rectangle(0, 8 * GamePanel.SCALE, GamePanel.TILE_SIZE, 4 *  GamePanel.SCALE);
        }
        obj.name = "Wire";
        obj.image = 8;
        obj.world_x = world_x;
        obj.world_y = world_y;
        return obj;
    }

    public static Object chimer(int world_x, int world_y){
        var obj = new Object();
        obj.name = "Chimer";
        obj.image = 9;
        obj.tile_activated = true;
        obj.world_x = world_x;
        obj.world_y = world_y;
        return obj;
    }

    

    public void update(GamePanel gp){
        if(name.equals("Rock") || (name.equals("Wire") && movable)){
            update_rock(gp);
        } else
        if(name.equals("Plate")){
            update_plate(gp.objectManager);
        } else
        if(name.equals("Door")){

        }
        if(name.equals("Trophe")){
            if(CollisionChecker.check_intersection(gp.player, this)) {
                gp.player.trophe_count += 1;
                gp.objectManager.objects.remove(this);
            }
            // if(gp.objectManager.objects.get(CollisionChecker.check_intersections(this, gp.objectManager.objects).getFirst()).name.equals("Rock")){
                
            // }
        }
        if(name.equals("Wire")){
            tile_activated = false;
            for(var elem : CollisionChecker.check_intersections(this, gp.objectManager.objects)){
                var obj = gp.objectManager.objects.get(elem);
                if(obj.name.equals("Wire") || obj.name.equals("Chimer") || obj.name.equals("Plate")){
                    tile_activated = obj.tile_activated;
                }
            }
            electric.frame_counter += 1;
            if(electric.frame_counter >= 5){
                electric.frame_counter = 0;
                electric.sprite_num += 1;
                electric.sprite_num %= electric.max_sprite_num;
            }
        }
        // if(name.equals("Toll")){
            
        // }
    }

    public void update_rock(GamePanel gp){
        if(CollisionChecker.check_intersection(gp.player, this)) {
            gp.player.pushing_rock = true;

            var up = gp.collisionChecker.checkUp(this);
            var down = gp.collisionChecker.checkDown(this);
            var left = gp.collisionChecker.checkLeft(this);
            var right = gp.collisionChecker.checkRight(this);
            gp.player.rock_up = up;
            gp.player.rock_down = down;
            gp.player.rock_left = left;
            gp.player.rock_right = right;

            if(up){
                gp.player.vel_y =  Math.max(0, gp.player.vel_y);
            }
            if(down){
                gp.player.vel_y = Math.min(0, gp.player.vel_y);
            }
            if(left){
                gp.player.vel_x = Math.max(0, gp.player.vel_x);
            }
            if(right){
                gp.player.vel_x = Math.min(0, gp.player.vel_x);
            }
            
            world_x += gp.player.vel_x;
            world_y += gp.player.vel_y;
        }
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
        if(name.equals("Plate") || name.equals("Door")){
            animation_state = tile_activated ? 1 : 0;
        }
        if(name.equals("Rock")){
            animation_state = painted_rock.isPresent() ? painted_rock.get() ? 1 : 2 : 0;
        }
        if(name.equals("Wire")){
            animation_state = 
            (is_vertical ? 3 : 0) +
            (tile_activated ? electric.sprite_num + 1 : 0);
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

    @Override
    public int speed() {
        return 0;
    }
}
