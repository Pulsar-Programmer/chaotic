import java.awt.Graphics2D;
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
        if(keyH.upPressed){
            direction = up;
            vel_y -= speed;
            timesKeyPressed++;
        }
        if(keyH.downPressed){
            direction = down;
            vel_y += speed;
            timesKeyPressed++;

        }
        if(keyH.leftPressed){
            direction = left;
            vel_x -= speed;
            timesKeyPressed++;

        }
        if(keyH.rightPressed){
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
        // var l = Math.sqrt(Math.pow(x, 2) + Math.pow(x, 2));
        // vel_x = vel_x / l;
        // vel_y = vel_y / l;
        // 
        if(timesKeyPressed<=1){
        world_x += vel_x;
        world_y += vel_y;}
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
    }

    public void getPlayerImage(){
        try {
            // String classpath = System.getProperty("java.class.path");
            // System.out.println(classpath);
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
