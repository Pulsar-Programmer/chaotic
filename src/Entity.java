import java.awt.Rectangle;

public class Entity {
    public int world_x, world_y;
    public int speed;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;

    public final static int up = 0;
    public final static int down = 2; 
    public final static int left = 4;
    public final static int right = 6;

    public int direction;

    public int spriteCounter = 0;
    public int spriteNum = 0;

    public int maxHealth = 0;
    public int health = 0;
}
