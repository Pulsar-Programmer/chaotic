import java.awt.Rectangle;
import java.util.ArrayList;

public class Projectile extends Entity implements Collider {
    String name;
    int sprite;
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

    public static Projectile turret(int world_x, int world_y, int direction){
        var ball = new Projectile();
        ball.name = "Turret";
        ball.sprite = 1;
        ball.world_x = world_x;
        ball.world_y = world_y;
        ball.direction = direction;
        return ball;
    }

    public static Projectile knight(int world_x, int world_y, int direction){
        var ball = new Projectile();
        ball.name = "Knight";
        ball.sprite = 4;
        ball.walking.max_sprite_num = 3;
        ball.world_x = world_x;
        ball.world_y = world_y;
        ball.direction = direction;
        return ball;
    }

    public static Projectile magic(int world_x, int world_y, int direction){
        var ball = new Projectile();
        ball.name = "Magic";
        ball.sprite = 2;
        ball.world_x = world_x;
        ball.world_y = world_y;
        ball.direction = direction;
        return ball;
    }

    public static Projectile power_magic(int world_x, int world_y, int direction){
        var ball = new Projectile();
        ball.name = "Power Magic";
        ball.sprite = 3;
        ball.walking.max_sprite_num = 4;
        ball.world_x = world_x;
        ball.world_y = world_y;
        ball.direction = direction;
        return ball;
    }

    public static Projectile arrow(int world_x, int world_y, int direction){
        var ball = new Projectile();
        ball.name = "Arrow";
        ball.sprite = 5;
        ball.walking.max_sprite_num = 1;
        ball.world_x = world_x;
        ball.world_y = world_y;
        ball.direction = direction;
        return ball;
    }

    public static Projectile power_arrow(int world_x, int world_y, int direction){
        var ball = new Projectile();
        ball.name = "Power Arrow";
        ball.sprite = 6;
        ball.walking.max_sprite_num = 3;
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

        var ms = CollisionChecker.check_intersections(this, gp.monsterManager.monsters);
        if(!ms.isEmpty() && origin_player){
            gp.monsterManager.monsters.get(ms.getFirst()).damage_monster(direction, offense, false);
            alive = false;
        }

        if(CollisionChecker.check_intersection(this, gp.player) && !origin_player){
            gp.player.damage_player(offense);
            alive = false;
        }

        if(gp.collisionChecker.checkUp(this) || gp.collisionChecker.checkDown(this) || gp.collisionChecker.checkLeft(this) || gp.collisionChecker.checkRight(this) ){
            alive = false;
        }

        // int player = gp.collisionChecker.check_monsters(null, null);

        walking.frame_counter += 1;
        if(walking.frame_counter > 10){
            walking.sprite_num += 1;
            walking.sprite_num %= (walking.max_sprite_num);
            walking.frame_counter = 0;
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
