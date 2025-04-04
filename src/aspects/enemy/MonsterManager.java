package aspects.enemy;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import aspects.map.MapGenerator;
import aspects.object.Object;
import main.GamePanel;

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
            
            ghost.add(ImageIO.read(new File("res/monsters/ghost/up.png")));
            ghost.add(ImageIO.read(new File("res/monsters/ghost/down.png")));
            ghost.add(ImageIO.read(new File("res/monsters/ghost/left.png")));
            ghost.add(ImageIO.read(new File("res/monsters/ghost/right.png")));

            monster_sprites.add(ghost);

            var skeleton = new ArrayList<BufferedImage>();
            setup_images(skeleton, "skeleton", 2);
            monster_sprites.add(skeleton);

            var knight = new ArrayList<BufferedImage>();
            setup_images(knight, "knight/walk", 2);
            setup_images(knight, "knight/atk", 4);
            monster_sprites.add(knight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setup_images(ArrayList<BufferedImage> to_add, String path, int spriteNum){
        try {
            for(var i = 1; i <= spriteNum; i++){
                to_add.add(ImageIO.read(new File("res/monsters/" + path + "/up_" + i + ".png")));
            }
            for(var i = 1; i <= spriteNum; i++){
                to_add.add(ImageIO.read(new File("res/monsters/" + path + "/down_" + i + ".png")));
            }
            for(var i = 1; i <= spriteNum; i++){
                to_add.add(ImageIO.read(new File("res/monsters/" + path + "/left_" + i + ".png")));
            }
            for(var i = 1; i <= spriteNum; i++){
                to_add.add(ImageIO.read(new File("res/monsters/" + path + "/right_" + i + ".png")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(GamePanel gp){
        for(var i = 0; i < monsters.size(); i++){
            monsters.get(i).update(gp);
            if(!monsters.get(i).alive){
                var dead = monsters.remove(i);
                if(MapGenerator.gen_range(10) >= 5){
                    gp.objectManager.objects.add(Object.heart(dead.world_x, dead.world_y, dead.offense/2 + 1));
                } else {
                    gp.objectManager.objects.add(Object.coin(dead.world_x, dead.world_y));
                }
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
            // System.out.println(monsta.sprite + ":" + monsta.direction + ":" + monsta.walking.max_sprite_num);
            var sprite = monster_sprites.get(monsta.sprite).get(monsta.direction * monsta.walking.max_sprite_num + monsta.walking.sprite_num);

            var world_x = monsta.world_x;
            var world_y = monsta.world_y;

            int screen_x = world_x - gp.player.world_x + gp.player.screen_x;
            int screen_y = world_y - gp.player.world_y + gp.player.screen_y;
            
            if(world_x + GamePanel.TILE_SIZE > gp.player.world_x - gp.player.screen_x &&
                world_x - GamePanel.TILE_SIZE < gp.player.world_x + gp.player.screen_x &&
                world_y + GamePanel.TILE_SIZE > gp.player.world_y - gp.player.screen_y &&
                world_y - GamePanel.TILE_SIZE < gp.player.world_y + gp.player.screen_y
            ){
                if(monsta.name.equals("Boss")){
                    g2d.drawImage(sprite, screen_x, screen_y, GamePanel.TILE_SIZE*4, GamePanel.TILE_SIZE*4, null);
                } else {
                    g2d.drawImage(sprite, screen_x, screen_y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
                }
                
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
