import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Monster extends Entity implements Collider {
    
    public String name;
    public int sprite;
    public int maxSpriteNum = 2;
    public int vel_x, vel_y;
    public int invicibility_counter = 60;
    public boolean invincible = false;
    public int waiting = 0;
    public boolean is_waiting = false;
    // public 
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

    public static Monster ghost(){
        var mon = new Monster();
        mon.name = "Ghost";
        mon.sprite = 0;
        mon.speed = 4;
        mon.health = mon.maxHealth;
        return mon;
    }

    public static Monster skeleton(){
        var mon = new Monster();
        mon.name = "Skeleton";
        mon.sprite = 1;
        mon.speed = 4;
        mon.health = mon.maxHealth;
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

    public void update(GamePanel gp){
        if(dying) return;
        vel_x = 0;
        vel_y = 0;

        if(name.equals("Ghost")){
            patrol_behavior(100, 100, 400, 200, 20);
        }
        if(name.equals("Turret")){
            patrol_behavior(200, 200, 300, 300, 60);
            if(waiting == 1){
                shoot_projectile(gp);
            }
            // shoot_projectile(gp);
        }
        if(name.equals("Skeleton")){
            var p1 = new Point(gp.player.world_x, gp.player.world_y);
            var p2 = new Point(world_x, world_y);
            if(p1.distance(p2) <= GamePanel.TILE_SIZE * 5){
                patrol_behavior(gp.player.world_x, gp.player.world_y, gp.player.world_x, gp.player.world_y, 2);
            } else {
                patrol_behavior(100, 100, 400, 200, 20);
            }
        }

        world_x += vel_x;
        world_y += vel_y;
        

        if(vel_x != 0 || vel_y != 0){
            walking.frame_counter += 1;
            if(walking.frame_counter > 10){
                walking.sprite_num += 1;
                walking.sprite_num %= maxSpriteNum;
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

    public void damage_monster(int player_direction, int atk, boolean is_healer){
        if(!invincible){
            health = Math.max(health - Math.max(atk - defense, 1), 0);
            invincible = true;
            hp_bar_on = true;
            hp_counter = 0;
            if(health == 0){
                dying = true;
            }
            damage_reaction(player_direction, is_healer);
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

    public void damage_reaction(int player_direction, boolean is_healer){
        // speed += 2;
        direction = player_direction;
        if(name.equals("Skeleton")){
            waiting = 30;
            is_waiting = true;
        }
        if(is_healer){
            waiting = 100;
            is_waiting = true;
        }
    }

    public void shoot_projectile(GamePanel gp){
        if(name.equals("Turret")){
            var turret = Projectile.turret(world_x, world_y, direction);
            turret.offense = offense;
            gp.projectileManager.projectiles.add(turret);
        }
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
        return speed;
    }
}
