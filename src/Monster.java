import java.awt.Graphics2D;

public class Monster extends Entity {
    
    public String name;
    public int sprite;
    // public int behavior = 0;

    public Monster(){
        world_x = 0;
        world_y = 0;
        spriteNum = 0;
        sprite = 0;
        name = "";
    }

    public static Monster slime(){
        var mon = new Monster();
        mon.name = "Green Slime";
        mon.sprite = 0;
        mon.speed = 1;
        mon.maxHealth = 4;
        mon.health = mon.maxHealth;

        return mon;
    }

    public void update(){
        var vel_x = 0;
        var vel_y = 0;
        if(vel_x != 0 || vel_y != 0){
            spriteCounter += 1;
            if(spriteCounter > 10){
                spriteNum += 1;
                spriteNum %= 2;
                spriteCounter = 0;
            }
        }
    }

}
