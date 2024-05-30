
public class Monster extends Entity {
    
    public String name;
    public int sprite;
    public int maxSpriteNum = 0;
    public int vel_x, vel_y;
    public int invicibility_counter = 60;
    public boolean invincible = false;
    // public int behavior = 0;

    public Monster(){
        world_x = 0;
        world_y = 0;
        spriteNum = 0;
        sprite = 0;
        name = "";
    }

    public static Monster skeleton(){
        var mon = new Monster();
        mon.name = "Skeleton";
        mon.sprite = 0;
        mon.speed = 1;
        mon.maxHealth = 4;
        mon.health = mon.maxHealth;
        mon.maxSpriteNum = 1;
        return mon;
    }
    //we need a patrol behavior
    public void skeleton_behavior(){
        if(world_x >= 100){
            vel_x -= speed;
            // vel_y -= speed;
            direction = LEFT;
        }
        else if(world_x <= 0){
            vel_x += speed;
            // vel_y += speed;
            direction = RIGHT;
        }
        else if(world_y >= 100){
            vel_y -= speed;
            direction = UP;
        }
        else if(world_y <= 0){
            vel_y += speed;
            direction = DOWN;
        }
    }

    public void update(){

        if(name.equals("Skeleton")){
            skeleton_behavior();
        }

        world_x += vel_x;
        world_y += vel_y;
        

        if(vel_x != 0 || vel_y != 0){
            spriteCounter += 1;
            if(spriteCounter > 10){
                spriteNum += 1;
                spriteNum %= (maxSpriteNum + 1);
                spriteCounter = 0;
            }
        }

        if(invincible){
            invicibility_counter -= 1;
            if(invicibility_counter <= 0){
                invincible = false;
                invicibility_counter = 60;
            }
        }
    }

    public void damageMonster(){
        if(!invincible){
            health -= 1;
            invincible = true;
        }
    }

}
