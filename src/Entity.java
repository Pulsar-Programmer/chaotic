import java.awt.Rectangle;

public abstract class Entity {
   // public Entity linkedEntity;
    public int world_x, world_y;
    public int speed;
    public int maxSpeed = 5;
    public Rectangle solidArea = new Rectangle(0, 0, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
   
    public int direction;
    public final static int UP = 0;
    public final static int DOWN = 1;
    public final static int LEFT = 2;
    public final static int RIGHT = 3;

    public Animation walking;

    // /** Max Health must always be a multiple of four. */
    public int maxHealth = 10;
    public int health = 0;

    public int defense = 0;
    public int offense = 1;

    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter = 0;

    // public Entity(){
    //     // assign default values in here TODO
    // }

}
