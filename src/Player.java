import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    
    public ArrayList<BufferedImage> entity_sprites = new ArrayList<>();
    
    // private int defense;
    // private int offense;
    // public int health;

    public final int screen_x, screen_y;

    public Player(GamePanel gp, KeyHandler kh){
        this.gp = gp;
        keyH = kh;
        screen_x = GamePanel.screenWidth / 2 - (GamePanel.TILE_SIZE / 2);
        screen_y = GamePanel.screenHeight / 2 - (GamePanel.TILE_SIZE / 2);

        solidArea = new Rectangle(8, 16, 32, 32);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        world_x = 100;
        world_y = 100;
        speed = 4;
        direction = DOWN;
        maxHealth = 10;
        health = maxHealth;
    }

    public void update(){
        int timesKeyPressed = 0;
        double vel_x = 0;
        double vel_y = 0;

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

        int obj_index = gp.collisionChecker.checkObject(this, true);
        if(obj_index != -1){
            var obj = gp.objectManager.objects.get(obj_index);
            if(obj.has_collision){
                return;
            }
            evaluate_object(obj);
        }


        if(vel_x != 0 || vel_y != 0){
            spriteCounter += 1;
            if(spriteCounter > 10){
                spriteNum += 1;
                spriteNum %= 2;
                spriteCounter = 0;
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
        
    }
    public void draw(Graphics2D g2){
        BufferedImage image = entity_sprites.get(direction + spriteNum);
        g2.drawImage(image, screen_x, screen_y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }

    public void getPlayerImage(){
        try {
            entity_sprites.add(ImageIO.read(new File("res/player/walk/boy_up_1.png")));
            entity_sprites.add(ImageIO.read(new File("res/player/walk/boy_up_2.png")));
            entity_sprites.add(ImageIO.read(new File("res/player/walk/boy_down_1.png")));
            entity_sprites.add(ImageIO.read(new File("res/player/walk/boy_down_2.png")));
            entity_sprites.add(ImageIO.read(new File("res/player/walk/boy_left_1.png")));
            entity_sprites.add(ImageIO.read(new File("res/player/walk/boy_left_2.png")));
            entity_sprites.add(ImageIO.read(new File("res/player/walk/boy_right_1.png")));
            entity_sprites.add(ImageIO.read(new File("res/player/walk/boy_right_2.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void evaluate_object(Object obj){
        if(obj.name == "Key"){
            gp.objectManager.objects.remove(obj);
        }
    }

    public void teleport_player(int to_x, int to_y){
        world_x = to_x * GamePanel.TILE_SIZE;
        world_y = to_y * GamePanel.TILE_SIZE;
    }
}
