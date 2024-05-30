import java.awt.Graphics2D;

public class Monster extends Entity {
    
    public String name;
    public int sprite;
    public int maxSpriteNum = 0;
    public int vel_x, vel_y;
    public int invicibility_counter = 60;
    public boolean invincible = false;
    public int waiting = 0;
    public boolean is_waiting = false;
    // public int behavior = 0;

    public boolean hp_bar_on = false;
    public int hp_counter = 0;

    public Monster(){
        world_x = 100;
        world_y = 100;
        sprite = 0;
        name = "";
        walking = new Animation();
    }

    public static Monster skeleton(){
        var mon = new Monster();
        mon.name = "Skeleton";
        mon.sprite = 0;
        mon.speed = 4;
        mon.maxHealth = 4;
        mon.health = mon.maxHealth;
        mon.maxSpriteNum = 1;
        return mon;
    }
    //we need a patrol behavior
    public void patrol_behavior(int low_x, int low_y, int high_x, int high_y, int wait_time){
        
        if(is_waiting){
            waiting -= 1;
            if(waiting <= 0){
                waiting = wait_time;
                is_waiting = false;
            }
            return;
        }

        if(direction == UP){
            vel_y -= speed;
        }
        if(direction == DOWN){
            vel_y += speed;
        }
        if(direction == LEFT){
            vel_x -= speed;
        }
        if(direction == RIGHT){
            vel_x += speed;
        }

        if(direction == UP && world_y <= low_y){
            direction = RIGHT;
            is_waiting = true;
        }
        if(direction == RIGHT && world_x >= high_x){
            direction = DOWN;
            is_waiting = true;
        }
        if(direction == DOWN && world_y >= high_y){
            direction = LEFT;
            is_waiting = true;
        }
        if(direction == LEFT && world_x <= low_x){
            direction = UP;
            is_waiting = true;
        }
    }

    public void update(){
        vel_x = 0;
        vel_y = 0;

        if(name.equals("Skeleton")){
            patrol_behavior(100, 100, 400, 200, 20);
        }

        world_x += vel_x;
        world_y += vel_y;
        

        if(vel_x != 0 || vel_y != 0){
            walking.frame_counter += 1;
            if(walking.frame_counter > 10){
                walking.sprite_num += 1;
                walking.sprite_num %= (maxSpriteNum + 1);
                walking.frame_counter = 0;
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

    public void damage_monster(int player_direction){
        if(!invincible){
            health = Math.max(health - 1, 0);
            invincible = true;
            hp_bar_on = true;
            hp_counter = 0;
            if(health == 0){
                dying = true;
            }
            damage_reaction(player_direction);
        }
    }

    public void dying_animation(Graphics2D g2d){
        dyingCounter += 1;
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - (dyingCounter)/100f));
        //enter implementation here
        if(dyingCounter == 100){
            dying = false;
            alive = false;
        }
    }

    public void damage_reaction(int player_direction){
        // speed += 2;
        direction = player_direction;
    }

}
