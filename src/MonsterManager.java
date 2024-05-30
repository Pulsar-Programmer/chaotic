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

    public void update(GamePanel gp){
        Monster dead = null;
        for(var elem : monsters){
            if(!elem.alive) dead = elem;
            elem.update(gp);
        }
        monsters.remove(dead);
    }

    public void draw(Graphics2D g2d, GamePanel gp){
        for(var monsta : monsters){
            // System.out.println(monsta.world_x);
            // System.out.println(monsta.direction + monsta.spriteNum);
            if(monsta.invincible){
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            }

            //DRAW MONSTA
            var sprite = monster_sprites.get(monsta.sprite).get(monsta.direction + monsta.walking.sprite_num);

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

    public void setup_monsters(){
        monsters.add(Monster.skeleton());
        var monsta = Monster.skeleton();
        monsta.name = "Turret";
        monsters.add(monsta);
    }

}