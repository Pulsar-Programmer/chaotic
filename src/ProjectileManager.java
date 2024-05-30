import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
            var first = new ArrayList<BufferedImage>();
            
            first.add(ImageIO.read(new File("res/projectiles/fireball_up_1.png")));
            first.add(ImageIO.read(new File("res/projectiles/fireball_up_2.png")));
            first.add(ImageIO.read(new File("res/projectiles/fireball_down_1.png")));
            first.add(ImageIO.read(new File("res/projectiles/fireball_down_2.png")));
            first.add(ImageIO.read(new File("res/projectiles/fireball_left_1.png")));
            first.add(ImageIO.read(new File("res/projectiles/fireball_left_2.png")));
            first.add(ImageIO.read(new File("res/projectiles/fireball_right_1.png")));
            first.add(ImageIO.read(new File("res/projectiles/fireball_right_2.png")));


            projectile_sprites.add(first);
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
            // System.out.println(elem.sprite + " " + elem.direction + " " + elem.walking.sprite_num);
            var sprite = projectile_sprites.get(elem.sprite).get(elem.direction + elem.walking.sprite_num);

            gp.screen_draw(sprite, elem.world_x, elem.world_y, g2d);
        }
    }

    
}
