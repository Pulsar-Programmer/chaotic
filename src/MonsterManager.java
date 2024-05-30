import java.awt.AlphaComposite;
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
        Monster dead = null;
        for(var elem : monsters){
            if(elem.health == 0){
                dead = elem;
            }
            elem.update();
        }
        if(dead != null){
            monsters.remove(dead);
        }
    }

    public void draw(Graphics2D g2d, GamePanel gp){
        for(var monsta : monsters){
            // System.out.println(monsta.world_x);
            // System.out.println(monsta.direction + monsta.spriteNum);
            if(monsta.invincible){
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            }
            gp.screen_draw(monster_sprites.get(monsta.sprite).get(monsta.direction + monsta.walking.sprite_num), monsta.world_x, monsta.world_y, g2d);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void setup_monsters(){
        monsters.add(Monster.skeleton());
    }

}