import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * MonsterManager < Remember how to do doc comments in Java.
 */
public class MonsterManager {
    public ArrayList<ArrayList<BufferedImage>> monster_sprites = new ArrayList<>();
    public ArrayList<Monster> monsters;

    public MonsterManager(){
        monsters = new ArrayList<>();
        setupMonsterSprites();
    }

    public void setupMonsterSprites(){
        monster_sprites = new ArrayList<>();
        try {
            var first = new ArrayList<BufferedImage>();
            
            //add first (slime) stuff here
            first.add(ImageIO.read(new File("res/player/walk/boy_up_1.png")));
            first.add(ImageIO.read(new File("res/player/walk/boy_up_2.png")));
            first.add(ImageIO.read(new File("res/player/walk/boy_down_1.png")));
            first.add(ImageIO.read(new File("res/player/walk/boy_down_2.png")));
            first.add(ImageIO.read(new File("res/player/walk/boy_left_1.png")));
            first.add(ImageIO.read(new File("res/player/walk/boy_left_2.png")));
            first.add(ImageIO.read(new File("res/player/walk/boy_right_1.png")));
            first.add(ImageIO.read(new File("res/player/walk/boy_right_2.png")));



            monster_sprites.add(first);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void update(){
        for(var elem : monsters){
            elem.update();
        }
    }

    public void draw(Graphics2D g2d, GamePanel gp){
        for(var monsta : monsters){
            // System.out.println(monsta.world_x);
            // System.out.println(monsta.direction + monsta.spriteNum);
            gp.screen_draw(monster_sprites.get(monsta.sprite).get(monsta.direction + monsta.spriteNum), monsta.world_x, monsta.world_y, g2d);
        }
    }

    public void setup_monsters(){
        monsters.add(Monster.skeleton());
    }

}