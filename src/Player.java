import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity implements Collider {
    GamePanel gp;
    
    public ArrayList<ArrayList<BufferedImage>> player_sprites = new ArrayList<>();
    
    // private int defense;
    // private int offense;
    // public int health;
    // public int special_offense = 2 * offense;

    public final int screen_x, screen_y;
    public int invicibility_counter = 60;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean special_attacking = false;
    public Animation attack_animation = new Animation();

    public int special_counter = 0;
    public int special_counter_max = 30;

    double vel_x = 0;
    double vel_y = 0;

    public int class_type = 0;
    public static final int WIZARD = 1;
    public static final int KNIGHT = 2;
    public static final int ARCHER = 3;
    public static final int HEALER = 4;

    public int trophe_count = 0;
    public boolean pushing_rock = false;
    public int coin_count = 0;

    public boolean touching_electricity = false;
    public int electricity_counter = 0;

    public boolean rock_up = false;
    public boolean rock_down = false;
    public boolean rock_left = false;
    public boolean rock_right = false;

    //TODO: make sure you assign all default values in here and not initially allocated
    public Player(GamePanel gp){
        this.gp = gp;
        screen_x = GamePanel.screenWidth / 2 - (GamePanel.TILE_SIZE / 2);
        screen_y = GamePanel.screenHeight / 2 - (GamePanel.TILE_SIZE / 2);

        solidArea = new Rectangle(8, 16, 32, 32);
        attackArea.width = 60;
        attackArea.height = 60;

        walking = new Animation();

        world_x = 100;
        world_y = 100;
        speed = 4;
        direction = DOWN;
        maxHealth = 10;
        health = maxHealth;

        getPlayerImage();
    }

    public void getPlayerImage(){
        player_sprites = new ArrayList<ArrayList<BufferedImage>>();
        try {
            // var player = new ArrayList<BufferedImage>();
            // setup_class(player, "walk");
            // setup_class(player, "attack");
            // player_sprites.add(player);

            var mage = new ArrayList<BufferedImage>();
            setup_images(mage, "wizard/walk", 2);
            setup_images(mage, "wizard/atk", 2);
            player_sprites.add(mage);

            var knight = new ArrayList<BufferedImage>();
            setup_images(knight, "knight/walk", 2);
            setup_images(knight, "knight/atk", 3);
            player_sprites.add(knight);

            var archer = new ArrayList<BufferedImage>();
            setup_images(archer, "archer/walk", 2);
            setup_images(archer, "archer/atk", 2);
            // setup_class(archer, "archer/special");
            player_sprites.add(archer);

            var healer = new ArrayList<BufferedImage>();
            setup_images(healer, "healer/walk", 2);
            setup_images(healer, "healer/atk", 2);
            player_sprites.add(healer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setup_images(ArrayList<BufferedImage> to_add, String path, int spriteNum){
        try {
            for(var i = 1; i <= spriteNum; i++){
                to_add.add(App.res("res/player/" + path + "/up_" + i + ".png"));
            }
            for(var i = 1; i <= spriteNum; i++){
                to_add.add(App.res("res/player/" + path + "/down_" + i + ".png"));
            }
            for(var i = 1; i <= spriteNum; i++){
                to_add.add(App.res("res/player/" + path + "/left_" + i + ".png"));
            }
            for(var i = 1; i <= spriteNum; i++){
                to_add.add(App.res("res/player/" + path + "/right_" + i + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }


    public static Player wizard(GamePanel gp){
        var player = new Player(gp);
        player.maxHealth = 12;
        player.health = player.maxHealth;
        player.offense = 30;
        player.defense = 5;
        player.special_counter_max = 300;
        //TODO
        player.class_type = WIZARD;
        return player;
    }

    public static Player knight(GamePanel gp){
        var player = new Player(gp);
        player.maxHealth = 12;
        player.offense = 6;
        player.defense = 10;
        player.class_type = KNIGHT;
        player.attack_animation.max_sprite_num = 3;
        //TODO @arkinsriva
        return player;
    }

    public static Player archer(GamePanel gp){
        var player = new Player(gp);
        player.maxHealth = 12;
        player.offense = 1;
        player.defense = 1;
        player.special_counter_max = 300;
        player.class_type = ARCHER;
        //TODO
        return player;
    }

    public static Player healer(GamePanel gp){
        var player = new Player(gp);
        player.maxHealth = 20;
        player.offense = 1;
        player.defense = 5;
        player.class_type = HEALER;
        player.special_counter_max = 200;
        //TODO
        return player;
    }

















    public void update(){
        int timesKeyPressed = 0;
        vel_x = 0;
        vel_y = 0;
        
        if(gp.keyH.attackHit){
            attacking = true;
            gp.keyH.attackHit = false;
            return;
        }
        if(attacking) engage_attack();

        if(pushing_rock){
            speed = maxSpeed - 3;
        } else {
            speed = maxSpeed;
            // vel_x = 0;
            // vel_y = 0;
        }

        if(gp.keyH.upPressed){
            direction = UP;
            if(!gp.collisionChecker.checkUp(this) && !rock_up){
                vel_y -= speed;
                timesKeyPressed++;
            }
        }
        if(gp.keyH.downPressed){
            direction = DOWN;
            if(!gp.collisionChecker.checkDown(this) && !rock_down){
                vel_y += speed;
                timesKeyPressed++;
            }
        }
        if(gp.keyH.leftPressed){
            direction = LEFT;
            if(!gp.collisionChecker.checkLeft(this) && !rock_left){
                vel_x -= speed;
                timesKeyPressed++;
            }
        }
        if(gp.keyH.rightPressed){
            direction = RIGHT;
            if(!gp.collisionChecker.checkRight(this) && !rock_right){
                vel_x += speed;
                timesKeyPressed++;
            }
        }
        // rock_up = false;
        // rock_down = false;
        // rock_left = false;
        // rock_right = false;

        // if(pushing_rock){
        //     gp.player.vel_x = 0;
        //     gp.player.vel_y = 0;
        // }

        var objs = CollisionChecker.check_intersections(this, gp.objectManager.objects);
        if(!objs.isEmpty()){
            var obj = gp.objectManager.objects.get(objs.getFirst());
            evaluate_object(obj);
        }

        var ents = CollisionChecker.check_intersections(this, gp.monsterManager.monsters);
        if(!ents.isEmpty()){
            var ent = gp.monsterManager.monsters.get(ents.getFirst());
            evaluate_monster(ent);
        }

        if(gp.keyH.specialHit){
            engage_special();
            
            gp.keyH.specialHit = false;
        }
        if(special_attacking){
            special_attack_animation();
        }

        if(vel_x != 0 || vel_y != 0){
            walking.frame_counter += 1;
            if(walking.frame_counter > 10){
                walking.sprite_num += 1;
                walking.sprite_num %= 2;
                walking.frame_counter = 0;
            }
        }
        if(timesKeyPressed<=1){
            world_x += vel_x;
            world_y += vel_y;
        }
        else if(timesKeyPressed>1)
        {
            world_x += (vel_x*Math.sqrt(2)/2);
            world_y += vel_y*Math.sqrt(2)/2;
        }

        if(invincible){
            invicibility_counter -= 1;
            if(invicibility_counter <= 0){
                invincible = false;
                invicibility_counter = 60;
            }
        }

        if(special_counter < special_counter_max){
            special_counter += 1;
        }

        if(health<=0){
            gp.gameState = GamePanel.DEATH;
            gp.sounds.clear();
            gp.sounds.addFile(0);
            gp.sounds.loop();
        }
        pushing_rock = false;

        if(touching_electricity){
            electricity_counter += 1;
            if(electricity_counter >= 240){
                health = Math.max(health - 4, 0);
                electricity_counter = 0;
            }
        } else {
            electricity_counter = 0;
        }
        touching_electricity = false;
    }

    public void draw(Graphics2D g2){
        int num = attacking || special_attacking ? 8 + attack_animation.sprite_num + direction *  attack_animation.max_sprite_num : walking.sprite_num + direction * walking.max_sprite_num;
        BufferedImage image = player_sprites.get(class_type - 1).get(num);
        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        }
        // if(attacking){
        //     if(direction == UP){
        //         g2.drawImage(image, screen_x, screen_y - GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 2, null);
        //     }
        //     if(direction == LEFT){
        //         g2.drawImage(image, screen_x - GamePanel.TILE_SIZE, screen_y, GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE, null);
        //     }
        //     if(direction == RIGHT){
        //         g2.drawImage(image, screen_x, screen_y, GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE, null);
        //     }
        //     if(direction == DOWN){
        //         g2.drawImage(image, screen_x, screen_y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 2, null);
        //     }
        // }
        // else{
            g2.drawImage(image, screen_x, screen_y, image.getWidth() * GamePanel.SCALE, image.getHeight() * GamePanel.SCALE, null);
        // }
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void evaluate_object(Object obj){
        if(obj.name.equals("Key")){
            gp.objectManager.objects.remove(obj);
        } else
        if(obj.name.equals("Door") && obj.tile_activated){
            if(obj.teleporter.isPresent()){
                var p = obj.teleporter.get();
                gp.player.teleport_player(p.x, p.y);
            }
        } else
        if(obj.name.equals("Heart")){
            gp.objectManager.objects.remove(obj);
            health = Math.min(maxHealth, health + obj.healing_power);
        } else
        if(obj.name.equals("Coin")){
            gp.objectManager.objects.remove(obj);
            coin_count += 1;
        } else
        if(obj.name.equals("Toll") && obj.toll_amount <= coin_count){
            gp.objectManager.objects.remove(obj);
            coin_count -= obj.toll_amount;
        } else
        if(obj.name.equals("Wire") && obj.tile_activated){
            touching_electricity = true;
        } else
        if(obj.name.equals("Chimer")){
            touching_electricity = true;
            electricity_counter += 5;
        }
    }

    public void teleport_player(int to_x, int to_y){
        world_x = to_x * GamePanel.TILE_SIZE;
        world_y = to_y * GamePanel.TILE_SIZE;
    }

    public void evaluate_monster(Monster monster){
        damage_player(monster.offense);
    }

    public void damage_player(int atk){
        if(!invincible){
            health = Math.max(health - Math.max(atk - defense, 1), 0);
            invincible = true;
        }
    }

    public void engage_attack(){
        attack_animation.frame_counter += 1;
        if(class_type == KNIGHT){
            if(attack_animation.frame_counter <= 10){
                attack_animation.sprite_num = 0;
            } else 
            if(attack_animation.frame_counter <= 20){
                attack_animation.sprite_num = 1;
                if(attack_animation.frame_counter == 15){
                    gp.sounds.addFile(7);
                    gp.sounds.start();
                }
                standard_attack();
            } else 
            if(attack_animation.frame_counter <= 30){
                attack_animation.sprite_num = 2;
            } else{
                attack_animation.sprite_num = 0;
                attack_animation.frame_counter = 0;
                attacking = false;
            }
            return;
        }
        if(attack_animation.frame_counter <= 10){
            attack_animation.sprite_num = 0;
        } else if(attack_animation.frame_counter <= 25){
            attack_animation.sprite_num = 1;
            
            if(class_type == WIZARD){
                if(attack_animation.frame_counter % 8 == 0){
                    var fire = Projectile.magic(world_x, world_y, direction);
                    fire.origin_player = true;
                    fire.offense = offense;
                    gp.projectileManager.projectiles.add(fire);
                    gp.sounds.addFile(11);
                    gp.sounds.start();
                }
                
            } else
            if(class_type == ARCHER && attack_animation.frame_counter == 15){
                var fire = Projectile.arrow(world_x, world_y, direction);
                fire.origin_player = true;
                fire.offense = offense;
                gp.projectileManager.projectiles.add(fire);
                gp.sounds.addFile(14);
                gp.sounds.start();
            } else
            if(class_type == HEALER){
                if(attack_animation.frame_counter == 15){
                    gp.sounds.addFile(3);
                    gp.sounds.start();
                }
                standard_attack();
            }
        } else{
            attack_animation.sprite_num = 0;
            attack_animation.frame_counter = 0;
            attacking = false;
        }
    }

    public void special_attack_animation(){
        attack_animation.frame_counter += 1;
        if(attack_animation.frame_counter <= 10){
            attack_animation.sprite_num = 0;
        } else if(attack_animation.frame_counter <= 25){
            attack_animation.sprite_num = 1;
        } else if(attack_animation.max_sprite_num == 3 && attack_animation.frame_counter <= 35){
            attack_animation.sprite_num = 2;
        } else{
            attack_animation.sprite_num = 0;
            attack_animation.frame_counter = 0;
            special_attacking = false;
        }
    }
    
    public void standard_attack(){
        int current_world_x = world_x;
        int current_world_y = world_y;
        int current_solid_area_width = solidArea.width;
        int current_solid_area_height = solidArea.height;

        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;

        var ms = CollisionChecker.check_intersections(this, gp.monsterManager.monsters);
        if(!ms.isEmpty()){
            gp.monsterManager.monsters.get(ms.getFirst()).damage_monster(direction, offense, class_type == HEALER);
        }

        world_x = current_world_x;
        world_y = current_world_y;
        solidArea.width = current_solid_area_width;
        solidArea.height = current_solid_area_height;
    }

    public void engage_special(){
        if(special_counter >= special_counter_max){
            special_attacking = true; // I will abandon this feature?
            if(class_type == WIZARD){
                for(var i = 0; i < 4; i++){
                    var fire = Projectile.power_magic(world_x, world_y, i);
                    fire.origin_player = true;
                    fire.offense = offense;
                    gp.projectileManager.projectiles.add(fire);
                }
                gp.sounds.addFile(11);
                gp.sounds.start();
            } else
            if(class_type == KNIGHT){
                var fire = Projectile.knight(world_x, world_y, direction);
                fire.origin_player = true;
                fire.offense = offense;
                gp.projectileManager.projectiles.add(fire);
                gp.sounds.addFile(8);
                gp.sounds.start();
            } else
            if(class_type == ARCHER){
                var fire = Projectile.power_arrow(world_x, world_y, direction);
                fire.origin_player = true;
                fire.offense = offense;
                gp.projectileManager.projectiles.add(fire);
                gp.sounds.addFile(4);
                gp.sounds.start();
            } else
            if(class_type == HEALER){
                health = Math.min(health + 3, maxHealth);
                gp.sounds.addFile(9);
                gp.sounds.start();
            }

            special_counter = 0;
        }
    }

    @Override
    public Rectangle collider_rect() {
        return solidArea;
    }

    @Override
    public int world_x() {
        return world_x;
    }

    @Override
    public int world_y() {
        return world_y;
    }

    @Override
    public int speed() {
        return speed;
    }
}
