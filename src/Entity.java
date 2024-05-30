import java.awt.Rectangle;

public abstract class Entity {
    public int world_x, world_y;
    public int speed;
    public Rectangle solidArea = new Rectangle(0, 0, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    public int direction;
    public final static int UP = 0;
    public final static int DOWN = 2; 
    public final static int LEFT = 4;
    public final static int RIGHT = 6;

    public Animation walking;

    public int maxHealth = 10;
    public int health = 0;
}
