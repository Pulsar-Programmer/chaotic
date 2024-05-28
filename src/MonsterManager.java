import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
            gp.screen_draw(monster_sprites.get(monsta.sprite).get(monsta.spriteNum), monsta.world_x, monsta.world_y, g2d);
        }
    }

}