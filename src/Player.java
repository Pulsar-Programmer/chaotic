import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler kh){
        this.gp = gp;
        keyH = kh;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
        direction = down;
    }

    public void update(){
        if(keyH.upPressed){
            direction = up;
            y -= speed;
        }
        if(keyH.downPressed){
            direction = down;
            y += speed;
        }
        if(keyH.leftPressed){
            direction = left;
            x -= speed;
        }
        if(keyH.rightPressed){
            direction = right;
            x += speed;
        }
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            spriteCounter += 1;
            if(spriteCounter > 10){
                spriteNum += 1;
                spriteNum %= 2;
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2){
        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = e_sprites.get(direction + spriteNum);
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }

    public void getPlayerImage(){
        try {
            // String classpath = System.getProperty("java.class.path");
            // System.out.println(classpath);
            e_sprites.add(ImageIO.read(new File("res/player/walk/boy_up_1.png")));
            e_sprites.add(ImageIO.read(new File("res/player/walk/boy_up_2.png")));
            e_sprites.add(ImageIO.read(new File("res/player/walk/boy_down_1.png")));
            e_sprites.add(ImageIO.read(new File("res/player/walk/boy_down_2.png")));
            e_sprites.add(ImageIO.read(new File("res/player/walk/boy_left_1.png")));
            e_sprites.add(ImageIO.read(new File("res/player/walk/boy_left_2.png")));
            e_sprites.add(ImageIO.read(new File("res/player/walk/boy_right_1.png")));
            e_sprites.add(ImageIO.read(new File("res/player/walk/boy_right_2.png")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
