package aspects.projectile;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ProjectileManager {
    public ArrayList<ArrayList<BufferedImage>> projectile_sprites = new ArrayList<>();
    public ArrayList<Projectile> projectiles;

    public ProjectileManager(){
        projectiles = new ArrayList<>();
        setup_projectile_sprites();
    }

    public void setup_projectile_sprites(){
        projectile_sprites = new ArrayList<>();
        try {
            var fireball = new ArrayList<BufferedImage>();
            setup_images(fireball, "fireball", 2);
            projectile_sprites.add(fireball);

            var turret = new ArrayList<BufferedImage>();
            setup_images(turret, "turret", 2);
            projectile_sprites.add(turret);

            var magic = new ArrayList<BufferedImage>();
            setup_images(magic, "magic", 2);
            projectile_sprites.add(magic);

            var power_magic = new ArrayList<BufferedImage>();
            setup_images(power_magic, "power_magic", 4);
            projectile_sprites.add(power_magic);

            var knight = new ArrayList<BufferedImage>();
            setup_images(knight, "knight", 3);
            projectile_sprites.add(knight);
            
            var arrow = new ArrayList<BufferedImage>();
            arrow.add(ImageIO.read(new File("res/projectiles/arrow/up.png")));
            arrow.add(ImageIO.read(new File("res/projectiles/arrow/down.png")));
            arrow.add(ImageIO.read(new File("res/projectiles/arrow/left.png")));
            arrow.add(ImageIO.read(new File("res/projectiles/arrow/right.png")));
            projectile_sprites.add(arrow);

            var power_arrow = new ArrayList<BufferedImage>();
            setup_images(power_arrow, "power_arrow", 3);
            projectile_sprites.add(power_arrow);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setup_images(ArrayList<BufferedImage> to_add, String path, int spriteNum){
        try {
            for(var i = 1; i <= spriteNum; i++){
                to_add.add(ImageIO.read(new File("res/projectiles/" + path + "/up_" + i + ".png")));
            }
            for(var i = 1; i <= spriteNum; i++){
                to_add.add(ImageIO.read(new File("res/projectiles/" + path + "/down_" + i + ".png")));
            }
            for(var i = 1; i <= spriteNum; i++){
                to_add.add(ImageIO.read(new File("res/projectiles/" + path + "/left_" + i + ".png")));
            }
            for(var i = 1; i <= spriteNum; i++){
                to_add.add(ImageIO.read(new File("res/projectiles/" + path + "/right_" + i + ".png")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    public void update(GamePanel gp){
        Projectile dead = null;

        for(var elem : projectiles){
            if(!elem.alive) dead = elem;
            elem.update(gp);
        }
        projectiles.remove(dead);
    }


    public void draw(Graphics2D g2d, GamePanel gp){
        for(var elem : projectiles){
            var sprite = projectile_sprites.get(elem.sprite).get(elem.direction * elem.walking.max_sprite_num + elem.walking.sprite_num);
            gp.screen_draw(sprite, elem.world_x, elem.world_y, g2d);
        }
    }

    
}
