import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Projectile extends Entity {
    String name;
    int sprite;
    int maxSpriteNum = 1;
    boolean origin_player;
    
    public Projectile(){
        name = "";
        speed = 10;
        maxHealth = 40;
        health = maxHealth;
        sprite = 0;
        alive = true;
        walking = new Animation();
        origin_player = false;
    }

    public static Projectile fireball(int world_x, int world_y, int direction){
        var ball = new Projectile();
        ball.name = "Fireball";
        ball.sprite = 0;
        ball.world_x = world_x;
        ball.world_y = world_y;
        ball.direction = direction;
        return ball;
    }

    public void update(GamePanel gp){
        var vel_x = 0;
        var vel_y = 0;

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

        world_x += vel_x;
        world_y += vel_y;



        health -= 1;
        if(health <= 0){
            alive = false;
        }

        int monster = CollisionChecker.check_monsters(this, gp.monsterManager.monsters);
        if(monster != -1){
            gp.monsterManager.monsters.get(monster).damage_monster(direction);
            alive = false;
        }

        

        // int player = gp.collisionChecker.check_monsters(null, null);

        walking.frame_counter += 1;
        if(walking.frame_counter > 10){
            walking.sprite_num += 1;
            walking.sprite_num %= (maxSpriteNum + 1);
            walking.frame_counter = 0;
        }
    }





}
