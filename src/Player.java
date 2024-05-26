import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screen_x, screen_y;

    public Player(GamePanel gp, KeyHandler kh){
        this.gp = gp;
        keyH = kh;
        screen_x = gp.screenWidth / 2 - (gp.tileSize / 2);
        screen_y = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        world_x = 100;
        world_y = 100;
        speed = 4;
        // speed = 20;
        direction = down;
    }

    public void update(){
        int timesKeyPressed = 0;
        double vel_x = 0;
        double vel_y = 0;
        if(keyH.upPressed && !gp.collisionChecker.checkUp(this)){
            direction = up;
            vel_y -= speed;
            timesKeyPressed++;
        }
        if(keyH.downPressed && !gp.collisionChecker.checkDown(this)){
            direction = down;
            vel_y += speed;
            timesKeyPressed++;

        }
        if(keyH.leftPressed && !gp.collisionChecker.checkLeft(this)){
            direction = left;
            vel_x -= speed;
            timesKeyPressed++;

        }
        if(keyH.rightPressed && !gp.collisionChecker.checkRight(this)){
            direction = right;
            vel_x += speed;
            timesKeyPressed++;

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
        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = entity_sprites.get(direction + spriteNum);
        g2.drawImage(image, screen_x, screen_y, gp.tileSize, gp.tileSize, null);
        g2.setColor(Color.blue);
        g2.fillRect((screen_x + solidArea.x - speed), (screen_y + solidArea.y - speed), (solidArea.x + solidArea.width + speed), (solidArea.y + solidArea.height + speed));
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
}
