import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {
    public int x, y;
    public int speed;

    // public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public ArrayList<BufferedImage> e_sprites = new ArrayList<>();
    public final static int up = 0;
    public final static int down = 2; 
    public final static int left = 4;
    public final static int right = 6; 


    public int direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
}
