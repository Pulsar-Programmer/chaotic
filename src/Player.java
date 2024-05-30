import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    
    public ArrayList<BufferedImage> player_sprites = new ArrayList<>();
    
    // private int defense;
    // private int offense;
    // public int health;

    public final int screen_x, screen_y;
    public int invicibility_counter = 60;
    public boolean invincible = false;
    public boolean attacking = false;
    public Animation attack_animation = new Animation();

    public int shot_counter = 0;

    public Player(GamePanel gp, KeyHandler kh){
        this.gp = gp;
        keyH = kh;
        screen_x = GamePanel.screenWidth / 2 - (GamePanel.TILE_SIZE / 2);
        screen_y = GamePanel.screenHeight / 2 - (GamePanel.TILE_SIZE / 2);

        solidArea = new Rectangle(8, 16, 32, 32);
        attackArea.width = 36;
        attackArea.height = 36;

        walking = new Animation();

        world_x = 100;
        world_y = 100;
        speed = 4;
        direction = DOWN;
        maxHealth = 10;
        health = maxHealth;

        getPlayerImage();
    }

    public void getPlayerImage(){
        try {
            player_sprites.add(ImageIO.read(new File("res/player/walk/boy_up_1.png")));
            player_sprites.add(ImageIO.read(new File("res/player/walk/boy_up_2.png")));
            player_sprites.add(ImageIO.read(new File("res/player/walk/boy_down_1.png")));
            player_sprites.add(ImageIO.read(new File("res/player/walk/boy_down_2.png")));
            player_sprites.add(ImageIO.read(new File("res/player/walk/boy_left_1.png")));
            player_sprites.add(ImageIO.read(new File("res/player/walk/boy_left_2.png")));
            player_sprites.add(ImageIO.read(new File("res/player/walk/boy_right_1.png")));
            player_sprites.add(ImageIO.read(new File("res/player/walk/boy_right_2.png")));

            player_sprites.add(App.res("res/player/attack/up_1.png"));
            player_sprites.add(ImageIO.read(new File("res/player/attack/up_2.png")));
            player_sprites.add(ImageIO.read(new File("res/player/attack/down_1.png")));
            player_sprites.add(ImageIO.read(new File("res/player/attack/down_2.png")));
            player_sprites.add(ImageIO.read(new File("res/player/attack/left_1.png")));
            player_sprites.add(ImageIO.read(new File("res/player/attack/left_2.png")));
            player_sprites.add(ImageIO.read(new File("res/player/attack/right_1.png")));
            player_sprites.add(ImageIO.read(new File("res/player/attack/right_2.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(){
        int timesKeyPressed = 0;
        double vel_x = 0;
        double vel_y = 0;

        if(keyH.attackHit){
            attacking = true;
            keyH.attackHit = false;
            return;
        }
        if(attacking) attack_animation();


        if(keyH.upPressed && !gp.collisionChecker.checkUp(this)){
            direction = UP;
            vel_y -= speed;
            timesKeyPressed++;
        }
        if(keyH.downPressed && !gp.collisionChecker.checkDown(this)){
            direction = DOWN;
            vel_y += speed;
            timesKeyPressed++;

        }
        if(keyH.leftPressed && !gp.collisionChecker.checkLeft(this)){
            direction = LEFT;
            vel_x -= speed;
            timesKeyPressed++;

        }
        if(keyH.rightPressed && !gp.collisionChecker.checkRight(this)){
            direction = RIGHT;
            vel_x += speed;
            timesKeyPressed++;

        }

        int obj_index = gp.collisionChecker.checkObject(this);
        if(obj_index != -1){
            var obj = gp.objectManager.objects.get(obj_index);
            evaluate_object(obj);
        }

        int ent_index = CollisionChecker.check_monsters((Entity)this, gp.monsterManager.monsters);
        if(ent_index != -1){
            var ent = gp.monsterManager.monsters.get(ent_index);
            evaluate_monster(ent);
        }

        if(keyH.specialHit){
            shoot_projectile();
            keyH.specialHit = false;
        }


        if(vel_x != 0 || vel_y != 0){
            walking.frame_counter += 1;
            if(walking.frame_counter > 10){
                walking.sprite_num += 1;
                walking.sprite_num %= 2;
                walking.frame_counter = 0;
            }
        }
        if(timesKeyPressed<=1){
            world_x += vel_x;
            world_y += vel_y;
        }
        else if(timesKeyPressed>1)
        {
            world_x += (vel_x*Math.sqrt(2)/2);
            world_y += vel_y*Math.sqrt(2)/2;
        }

        if(invincible){
            invicibility_counter -= 1;
            if(invicibility_counter <= 0){
                invincible = false;
                invicibility_counter = 60;
            }
        }

        if(shot_counter < 30){
            shot_counter += 1;
            
        }
    }
    public void draw(Graphics2D g2){
        int num = attacking ? 8 + attack_animation.sprite_num : walking.sprite_num;
        BufferedImage image = player_sprites.get(direction + num);
        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        }
        if(attacking){
            if(direction == UP){
                g2.drawImage(image, screen_x, screen_y - GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 2, null);
            }
            if(direction == LEFT){
                g2.drawImage(image, screen_x - GamePanel.TILE_SIZE, screen_y, GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE, null);
            }
            if(direction == RIGHT){
                g2.drawImage(image, screen_x, screen_y, GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE, null);
            }
            if(direction == DOWN){
                g2.drawImage(image, screen_x, screen_y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 2, null);
            }
        }
        else{
            g2.drawImage(image, screen_x, screen_y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
        }
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void evaluate_object(Object obj){
        if(obj.name.equals("Key")){
            gp.objectManager.objects.remove(obj);
        }
    }

    public void teleport_player(int to_x, int to_y){
        world_x = to_x * GamePanel.TILE_SIZE;
        world_y = to_y * GamePanel.TILE_SIZE;
    }

    public void evaluate_monster(Monster monster){
        if(monster.name.equals("Skeleton") && !invincible){
            health = Math.max(0, health - 1);
            if(health<=0){
         gp.gameState = GamePanel.DEATH;
            }
            invincible = true;
        }
    }
    
    public void attack_animation(){
        attack_animation.frame_counter += 1;
        if(attack_animation.frame_counter <= 10){
            attack_animation.sprite_num = 0;
        }
        else if(attack_animation.frame_counter <= 25){
            attack_animation.sprite_num = 1;

            int current_world_x = world_x;
            int current_world_y = world_y;
            int current_solid_area_width = solidArea.width;
            int current_solid_area_height = solidArea.height;

            if(direction == UP){
                world_y -= attackArea.height;
            } else if(direction == DOWN){
                world_y += attackArea.height;
            } else if(direction == LEFT){
                world_x -= attackArea.width;
            } else if(direction == RIGHT){
                world_x += attackArea.width;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int m_index = CollisionChecker.check_monsters((Entity)this, gp.monsterManager.monsters);
            if(m_index != -1){
                gp.monsterManager.monsters.get(m_index).damage_monster(direction);
            }

            world_x = current_world_x;
            world_y = current_world_y;
            solidArea.width = current_solid_area_width;
            solidArea.height = current_solid_area_height;
        }
        if(attack_animation.frame_counter > 25){
            attack_animation.sprite_num = 0;
            attack_animation.frame_counter = 0;
            attacking = false;
        }
    }

    public void shoot_projectile(){
        if(shot_counter >= 30){
            var fire = Projectile.fireball(world_x, world_y, direction);
            fire.origin_player = true;
            gp.projectileManager.projectiles.add(fire);
            shot_counter = 0;
        }
    }
}
