import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Object implements Collider {
    private int image;
    private int animation_state;
    public String name;
    public int world_x, world_y;
    public Rectangle solidArea = new Rectangle(0, 0, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);

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
        obj.name = "Metal Plate";
        obj.image = 1;
        obj.world_x = world_x;
        obj.world_y = world_y;
        return obj;
    }

    public void update(Player player){
        if(name.equals("Rock")){
            update_rock(player);
        } else
        if(name.equals("Metal Plate")){
            update_plate();
        }
    }

    public void update_rock(Player player){
        if (CollisionChecker.check_intersection(player, this)) {
            int direction = player.direction;
            if (direction == Entity.DOWN) {
                world_y += player.speed;
            } else if (direction == Entity.LEFT) {
                world_x -= player.speed;
            } else if (direction == Entity.RIGHT) {
                world_x += player.speed;
            } else if (direction == Entity.UP) {
                world_y -= player.speed;
            }
        }
    }

    public void update_plate(){
        
    }


    public void draw(Graphics2D g2d, GamePanel gp){
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
