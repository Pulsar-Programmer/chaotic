package aspects.enemy;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import aspects.Animation;
import aspects.map.MapGenerator;
import aspects.projectile.Projectile;
import collision.Collider;
import main.GamePanel;
import main.Player;

public class Monster extends Entity implements Collider {
    public String name;
    public int sprite;
    // public int maxSpriteNum = 2;
    public int vel_x, vel_y;
    public int invicibility_counter = 60;
    public boolean invincible = false;
    public int waiting = 0;
    public boolean is_waiting = false;
    // public
    // public int behavior = 0;\

    public EnemyChoice choice;
    public Player player;

    public long lastCall;

    public boolean hp_bar_on = false;
    public int hp_counter = 0;
    public int shot_counter = 0;

    public Point patrol_start = new Point(1, 1);
    public Point patrol_end = new Point(2, 2);

    public Monster(Player player) {
        world_x = 100;
        world_y = 100;
        sprite = 0;
        name = "";
        walking = new Animation();
        this.player = player;
        choice = new EnemyChoice(player, this);

        lastCall = System.currentTimeMillis();
    }

    public static Monster ghost(Player player) {
        var mon = new Monster(player);
        mon.name = "Ghost";
        mon.sprite = 0;
        mon.walking.max_sprite_num = 1;
        mon.speed = 1;
        mon.offense = 100;
        mon.health = mon.maxHealth;
        return mon;

    }

    public static Monster turret(Player player){
        var mon = new Monster(player);
        mon.name = "Turret";
        mon.sprite = 1;
        mon.speed = 4;
        mon.offense = 10;
        mon.maxHealth = 20;
        mon.health = mon.maxHealth;
        return mon;
    }

    public static Monster skeleton(Player player) {
        var mon = new Monster(player);
        mon.name = "Skeleton";
        mon.sprite = 1;
        mon.speed = 4;
        mon.health = mon.maxHealth;
        return mon;
    }

    public static Monster minion(Player player){
        var s4 = Monster.skeleton(player);
        s4.name = "Minion";
        s4.maxHealth = 2;
        s4.health = s4.maxHealth;
        return s4;
    }

    public static Monster knight(Player player) {
        var mon = new Monster(player);
        mon.name = "Knight";
        mon.sprite = 2;
        mon.speed = 6;
        mon.maxHealth = 40;
        mon.health = mon.maxHealth;
        return mon;
    }
    public static Monster boss(Player player) {
        var mon = new Monster(player);
        mon.name = "Boss";
        mon.sprite = 1;
        mon.speed = 3;
        mon.solidArea = new Rectangle(0,0,GamePanel.TILE_SIZE*4,GamePanel.TILE_SIZE*4);
        mon.maxHealth = 100;
        mon.health = mon.maxHealth;
        return mon;
    }

    // we need a patrol behavior
    public void patrol_behavior(int low_x, int low_y, int high_x, int high_y, int wait_time) {

        if (is_waiting) {
            waiting -= 1;
            if (waiting <= 0) {
                waiting = wait_time;
                is_waiting = false;
            }
            return;
        }

        if (direction == UP) {
            vel_y -= speed;
        }
        if (direction == DOWN) {
            vel_y += speed;
        }
        if (direction == LEFT) {
            vel_x -= speed;
        }
        if (direction == RIGHT) {
            vel_x += speed;
        }

        if (direction == UP && world_y <= low_y) {
            direction = RIGHT;
            is_waiting = true;
        }
        if (direction == RIGHT && world_x >= high_x) {
            direction = DOWN;
            is_waiting = true;
        }
        if (direction == DOWN && world_y >= high_y) {
            direction = LEFT;
            is_waiting = true;
        }
        if (direction == LEFT && world_x <= low_x) {
            direction = UP;
            is_waiting = true;
        }
    }

    public void update(GamePanel gp) {
        if(System.currentTimeMillis() - lastCall > 1000) {
            choice.periodic(choice);
            lastCall = System.currentTimeMillis();
        }

        System.out.println(player.world_x);

        if (dying)
            return;
        vel_x = 0;
        vel_y = 0;

        if (name.equals("Ghost")) {
            // patrol_behavior(100, 100, 400, 200, 20);
            patrol_behavior(patrol_start.x, patrol_start.y, patrol_end.x, patrol_end.y, 20);
        }
        if (name.equals("Turret")) {
            // patrol_behavior(200, 200, 300, 300, 60);
            patrol_behavior(patrol_start.x, patrol_start.y, patrol_end.x, patrol_end.y, 60);
            if (waiting == 1) {
                shoot_projectile(gp);
            }
            // shoot_projectile(gp);
        }
        if (name.equals("Skeleton")) {
            var p1 = new Point(gp.player.world_x, gp.player.world_y);
            var p2 = new Point(world_x, world_y);
            if (p1.distance(p2) <= GamePanel.TILE_SIZE * 5) {
                patrol_behavior(gp.player.world_x, gp.player.world_y, gp.player.world_x, gp.player.world_y, 2);
            } else {
                // patrol_behavior(100, 100, 400, 200, 20);
                patrol_behavior(patrol_start.x, patrol_start.y, patrol_end.x, patrol_end.y, 20);
            }
        }
        if(name.equals("Boss") || name.equals("Knight")){
            var p1 = new Point(gp.player.world_x, gp.player.world_y);
            var p2 = new Point(world_x, world_y);
            if (p1.distance(p2) <= GamePanel.TILE_SIZE * 10) {
                patrol_behavior(gp.player.world_x, gp.player.world_y, gp.player.world_x, gp.player.world_y, 2);
            }
            if(MapGenerator.gen_range(1000) == 1){
                attack3(gp.monsterManager);
            }
            shot_counter += 1;
            if(shot_counter == 40){
                shoot_projectile(gp);
                shot_counter = 0;
            }
        }
        if(name.equals("Minion")){
            patrol_behavior(gp.player.world_x, gp.player.world_y, gp.player.world_x, gp.player.world_y, 0);
        }
        var up = gp.collisionChecker.checkUp(this);
        var down = gp.collisionChecker.checkDown(this);
        var left = gp.collisionChecker.checkLeft(this);
        var right = gp.collisionChecker.checkRight(this);

        if(up){
            vel_y =  Math.max(0, vel_y);
        }
        if(down){
            vel_y = Math.min(0, vel_y);
        }
        if(left){
            vel_x = Math.max(0, vel_x);
        }
        if(right){
            vel_x = Math.min(0, vel_x);
        }
        
        

        world_x += vel_x;
        world_y += vel_y;

        if (vel_x != 0 || vel_y != 0) {
            walking.frame_counter += 1;
            if (walking.frame_counter > 10) {
                walking.sprite_num += 1;
                walking.sprite_num %= walking.max_sprite_num;
                walking.frame_counter = 0;
            }
        }

        if (invincible) {
            invicibility_counter -= 1;
            if (invicibility_counter <= 0) {
                invincible = false;
                invicibility_counter = 60;
            }
        }
    }

    public void damage_monster(int player_direction, int atk, boolean is_healer) {
        if (!invincible) {
            health = Math.max(health - Math.max(atk - defense, 1), 0);
            invincible = true;
            hp_bar_on = true;
            hp_counter = 0;
            if (health == 0) {
                dying = true;
            }
            damage_reaction(player_direction, is_healer);
        }
    }

    public void dying_animation(Graphics2D g2d) {
        dyingCounter += 1;
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f -
        // (dyingCounter)/100f));
        // enter implementation here
        if (dyingCounter == 100) {
            dying = false;
            alive = false;
        }
    }

    public void damage_reaction(int player_direction, boolean is_healer) {
        // speed += 2;
        direction = player_direction;
        if (name.equals("Skeleton")) {
            waiting = 30;
            is_waiting = true;
        }
        if (is_healer) {
            waiting = 100;
            is_waiting = true;
        }
    }

    public void shoot_projectile(GamePanel gp) {
        if (name.equals("Turret")) {
            var turret = Projectile.turret(world_x, world_y, direction);
            turret.offense = offense;
            gp.projectileManager.projectiles.add(turret);
        } else 
        if (name.equals("Boss") || name.equals("Knight")) {
            var turret = Projectile.knight(world_x, world_y, direction);
            turret.offense = offense;
            turret.speed = 12;
            gp.projectileManager.projectiles.add(turret);
        }
    }

    public void attack3(MonsterManager monsterManager) { // RAISE SOULS

        // summons 4 skeletons, slight delay between each spawn
        var x = world_x + 1;
        var y = world_y + 1;
        var s1 = Monster.minion(player);
        s1.world_x = x;
        s1.world_y = y;

        monsterManager.monsters.add(s1);

        x = world_x - 1;
        y = world_y + 1;
        var s2 = Monster.minion(player);
        s2.world_x = x;
        s2.world_y = y;

        monsterManager.monsters.add(s2);


        x = world_x + 1;
        y = world_y - 1;
        var s3 = Monster.minion(player);
        s3.world_x = x;
        s3.world_y = y;

        monsterManager.monsters.add(s3);

        x = world_x - 1;
        y = world_y - 1;
        var s4 = Monster.minion(player);
        s4.world_x = x;
        s4.world_y = y;

        monsterManager.monsters.add(s4);

        
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------------
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
