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
            var fireball = new ArrayList<BufferedImage>();
            setup_images(fireball, "fireball");
            projectile_sprites.add(fireball);

            var turret = new ArrayList<BufferedImage>();
            setup_images(turret, "turret");
            projectile_sprites.add(turret);

            var magic = new ArrayList<BufferedImage>();
            setup_images(magic, "magic");
            projectile_sprites.add(magic);

            var power_magic = new ArrayList<BufferedImage>();
            setup_images(power_magic, "power_magic");
            power_magic.add(ImageIO.read(new File("res/projectiles/power_magic/up_3.png")));
            power_magic.add(ImageIO.read(new File("res/projectiles/power_magic/up_4.png")));
            power_magic.add(ImageIO.read(new File("res/projectiles/power_magic/down_3.png")));
            power_magic.add(ImageIO.read(new File("res/projectiles/power_magic/down_4.png")));
            power_magic.add(ImageIO.read(new File("res/projectiles/power_magic/left_3.png")));
            power_magic.add(ImageIO.read(new File("res/projectiles/power_magic/left_4.png")));
            power_magic.add(ImageIO.read(new File("res/projectiles/power_magic/right_3.png")));
            power_magic.add(ImageIO.read(new File("res/projectiles/power_magic/right_4.png")));
            projectile_sprites.add(power_magic);

            var knight = new ArrayList<BufferedImage>();
            setup_images(knight, "knight");
            knight.add(ImageIO.read(new File("res/projectiles/knight/up_3.png")));
            knight.add(ImageIO.read(new File("res/projectiles/knight/down_3.png")));
            knight.add(ImageIO.read(new File("res/projectiles/knight/left_3.png")));
            knight.add(ImageIO.read(new File("res/projectiles/knight/right_3.png")));
            projectile_sprites.add(knight);
            
            var arrow = new ArrayList<BufferedImage>();
            arrow.add(ImageIO.read(new File("res/projectiles/arrow/up.png")));
            arrow.add(ImageIO.read(new File("res/projectiles/arrow/down.png")));
            arrow.add(ImageIO.read(new File("res/projectiles/arrow/left.png")));
            arrow.add(ImageIO.read(new File("res/projectiles/arrow/right.png")));
            projectile_sprites.add(arrow);

            var power_arrow = new ArrayList<BufferedImage>();
            setup_images(power_arrow, "power_arrow");
            power_arrow.add(ImageIO.read(new File("res/projectiles/power_arrow/up_3.png")));
            power_arrow.add(ImageIO.read(new File("res/projectiles/power_arrow/down_3.png")));
            power_arrow.add(ImageIO.read(new File("res/projectiles/power_arrow/left_3.png")));
            power_arrow.add(ImageIO.read(new File("res/projectiles/power_arrow/right_3.png")));
            projectile_sprites.add(power_arrow);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setup_images(ArrayList<BufferedImage> to_add, String path){
        try {
            to_add.add(ImageIO.read(new File("res/projectiles/" + path + "/up_1.png")));
            to_add.add(ImageIO.read(new File("res/projectiles/" + path + "/up_2.png")));
            to_add.add(ImageIO.read(new File("res/projectiles/" + path + "/down_1.png")));
            to_add.add(ImageIO.read(new File("res/projectiles/" + path + "/down_2.png")));
            to_add.add(ImageIO.read(new File("res/projectiles/" + path + "/left_1.png")));
            to_add.add(ImageIO.read(new File("res/projectiles/" + path + "/left_2.png")));
            to_add.add(ImageIO.read(new File("res/projectiles/" + path + "/right_1.png")));
            to_add.add(ImageIO.read(new File("res/projectiles/" + path + "/right_2.png")));
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
