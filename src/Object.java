import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Object {
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

    public static Object key(int world_x, int world_y){
        var obj = new Object();
        obj.name = "Key";
        obj.image = 0;
        obj.world_x = world_x;
        obj.world_y = world_y;
        return obj;
    }

    public static Object coin(int world_x, int world_y){
        var obj = new Object();
        obj.name = "Coin";
        obj.image = 1;
        obj.world_x = world_x;
        obj.world_y = world_y;
        return obj;
    }

    // public static Object door(int world_x, int world_y){
    //     var obj = new Object();
    //     obj.name = "Door";
    //     obj.image = 1;
    //     obj.world_x = world_x;
    //     obj.world_y = world_y;
    //     return obj;
    // }

    // public static Object chest(int world_x, int world_y){
    //     var obj = new Object();
    //     obj.name = "Chest";
    //     obj.image = 2;
    //     obj.world_x = world_x;
    //     obj.world_y = world_y;
    //     return obj;
    // }

    public void draw(Graphics2D g2d, GamePanel gp){
        
        gp.screen_draw(gp.objectManager.sprites[image][animation_state], world_x, world_y, g2d);
        
    }
}
