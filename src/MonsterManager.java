import java.awt.AlphaComposite;
import java.awt.Color;
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
            var ghost = new ArrayList<BufferedImage>();
            
            //add first (slime) stuff here
            ghost.add(ImageIO.read(new File("res/monsters/ghost/up.png")));
            ghost.add(ImageIO.read(new File("res/monsters/ghost/up.png")));
            ghost.add(ImageIO.read(new File("res/monsters/ghost/down.png")));
            ghost.add(ImageIO.read(new File("res/monsters/ghost/down.png")));
            ghost.add(ImageIO.read(new File("res/monsters/ghost/left.png")));
            ghost.add(ImageIO.read(new File("res/monsters/ghost/left.png")));
            ghost.add(ImageIO.read(new File("res/monsters/ghost/right.png")));
            ghost.add(ImageIO.read(new File("res/monsters/ghost/right.png")));

            monster_sprites.add(ghost);

            var skeleton = new ArrayList<BufferedImage>();

            skeleton.add(ImageIO.read(new File("res/monsters/skeleton/up_1.png")));
            skeleton.add(ImageIO.read(new File("res/monsters/skeleton/up_2.png")));
            skeleton.add(ImageIO.read(new File("res/monsters/skeleton/down_1.png")));
            skeleton.add(ImageIO.read(new File("res/monsters/skeleton/down_2.png")));
            skeleton.add(ImageIO.read(new File("res/monsters/skeleton/left_1.png")));
            skeleton.add(ImageIO.read(new File("res/monsters/skeleton/left_2.png")));
            skeleton.add(ImageIO.read(new File("res/monsters/skeleton/right_1.png")));
            skeleton.add(ImageIO.read(new File("res/monsters/skeleton/right_2.png")));

            monster_sprites.add(skeleton);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(GamePanel gp){
        Monster dead = null;
        for(var elem : monsters){
            if(!elem.alive) dead = elem;
            elem.update(gp);
        }
        if(dead != null){
            monsters.remove(dead);
            if(MapGenerator.gen_range(10) >= 5){
                gp.objectManager.objects.add(Object.heart(dead.world_x, dead.world_y, dead.offense/2 + 1));
            } else {
                gp.objectManager.objects.add(Object.coin(dead.world_x, dead.world_y));
            }
        }
    }

    public void draw(Graphics2D g2d, GamePanel gp){
        for(var monsta : monsters){
            // System.out.println(monsta.world_x);
            // System.out.println(monsta.direction + monsta.spriteNum);
            if(monsta.invincible){
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            }

            //DRAW MONSTA
            var sprite = monster_sprites.get(monsta.sprite).get(monsta.direction * monsta.maxSpriteNum + monsta.walking.sprite_num);

            var world_x = monsta.world_x;
            var world_y = monsta.world_y;

            int screen_x = world_x - gp.player.world_x + gp.player.screen_x;
            int screen_y = world_y - gp.player.world_y + gp.player.screen_y;
            
            if(world_x + GamePanel.TILE_SIZE > gp.player.world_x - gp.player.screen_x &&
                world_x - GamePanel.TILE_SIZE < gp.player.world_x + gp.player.screen_x &&
                world_y + GamePanel.TILE_SIZE > gp.player.world_y - gp.player.screen_y &&
                world_y - GamePanel.TILE_SIZE < gp.player.world_y + gp.player.screen_y
            ){
                g2d.drawImage(sprite, screen_x, screen_y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
            }
            


            //DRAW HEALTHBAR
            if(monsta.hp_bar_on){
                var progression = monsta.health * (GamePanel.TILE_SIZE) / (double)(monsta.maxHealth);
    
                g2d.setColor(new Color(35, 35, 35));
                g2d.fillRect(screen_x, screen_y - 15, GamePanel.TILE_SIZE, 6);
                g2d.setColor(new Color(255, 0, 30));
                g2d.fillRect(screen_x, screen_y - 15, (int)progression, 6);

                monsta.hp_counter += 1;
                if(monsta.hp_counter >= 300){
                    monsta.hp_bar_on = false;
                    monsta.hp_counter = 0;
                }
            }

            if(monsta.dying){
                monsta.dying_animation(g2d);
            }
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

}