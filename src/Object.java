import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Object {
    public int image;
    public String name;b
    public boolean has_collision = false;
    public int world_x, world_y;
    public Rectangle solidArea = new Rectangle(0, 0, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);

    private Object(){
        image = 0;
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

    public static Object door(int world_x, int world_y){
        var obj = new Object();
        obj.name = "Door";
        obj.image = 1;
        obj.world_x = world_x;
        obj.world_y = world_y;
        obj.has_collision = true;
        return obj;
    }

    public static Object chest(int world_x, int world_y){
        var obj = new Object();
        obj.name = "Chest";
        obj.image = 2;
        obj.world_x = world_x;
        obj.world_y = world_y;
        return obj;
    }

    public void draw(Graphics2D g2d, GamePanel gp){
        
        int screen_x = world_x - gp.player.world_x + gp.player.screen_x;
        int screen_y = world_y - gp.player.world_y + gp.player.screen_y;
        System.out.println(screen_x + "" + screen_y);
        if(world_x + GamePanel.TILE_SIZE > gp.player.world_x - gp.player.screen_x &&
            world_x - GamePanel.TILE_SIZE < gp.player.world_x + gp.player.screen_x &&
            world_y + GamePanel.TILE_SIZE > gp.player.world_y - gp.player.screen_y &&
            world_y - GamePanel.TILE_SIZE < gp.player.world_y + gp.player.screen_y
        ){
            System.out.println("Yay!");
            g2d.drawImage(gp.objectManager.sprites[image], screen_x, screen_y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
        }
    }
}
