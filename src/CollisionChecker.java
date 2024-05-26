import java.awt.Rectangle;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public boolean checkUp(Entity entity){
        int left_x = entity.world_x + entity.solidArea.x - entity.speed;
        int right_x = entity.world_x + entity.solidArea.x + entity.solidArea.width + entity.speed;
        int top_y = entity.world_y + entity.solidArea.y - entity.speed;
        
        int left_tile = left_x/gp.tileSize;
        int right_tile = right_x/gp.tileSize;
        int top_tile = top_y/gp.tileSize;

        var tile1 = gp.tileManager.find(left_tile, top_tile);
        var tile2 = gp.tileManager.find(right_tile, top_tile);
        System.out.println("Up: " + tile1.has_collision + " " + tile2.has_collision);
        return tile1.has_collision || tile2.has_collision;
    }

    public boolean checkDown(Entity entity){
        int left_x = entity.world_x + entity.solidArea.x - entity.speed;
        int right_x = entity.world_x + entity.solidArea.x + entity.solidArea.width + entity.speed;
        int bottom_y = entity.world_y + entity.solidArea.y + entity.solidArea.height + entity.speed;
        
        int left_tile = left_x/gp.tileSize;
        int right_tile = right_x/gp.tileSize;
        int bottom_tile = bottom_y/gp.tileSize;

        var tile3 = gp.tileManager.find(left_tile, bottom_tile);
        var tile4 = gp.tileManager.find(right_tile, bottom_tile);
        System.out.println("Down: " + tile3.has_collision + " " + tile4.has_collision);
        return tile3.has_collision || tile4.has_collision;
    }

    public boolean checkLeft(Entity entity){
        int left_x = entity.world_x + entity.solidArea.x - entity.speed;
        int top_y = entity.world_y + entity.solidArea.y - entity.speed;
        int bottom_y = entity.world_y + entity.solidArea.y + entity.solidArea.height + entity.speed;
        
        int left_tile = left_x/gp.tileSize;
        int top_tile = top_y/gp.tileSize;
        int bottom_tile = bottom_y/gp.tileSize;

        var tile1 = gp.tileManager.find(left_tile, top_tile);
        var tile3 = gp.tileManager.find(left_tile, bottom_tile);
        System.out.println("Left: " + tile1.has_collision + " " + tile3.has_collision);
        return tile1.has_collision || tile3.has_collision;
    }

    public boolean checkRight(Entity entity){
        int right_x = entity.world_x + entity.solidArea.x + entity.solidArea.width + entity.speed;
        int top_y = entity.world_y + entity.solidArea.y - entity.speed;
        int bottom_y = entity.world_y + entity.solidArea.y + entity.solidArea.height + entity.speed;
        
        int right_tile = right_x/gp.tileSize;
        int top_tile = top_y/gp.tileSize;
        int bottom_tile = bottom_y/gp.tileSize;

        var tile2 = gp.tileManager.find(right_tile, top_tile);
        var tile4 = gp.tileManager.find(right_tile, bottom_tile);
        System.out.println("Right: " + tile2.has_collision + " " + tile4.has_collision);
        return tile4.has_collision || tile2.has_collision;
    }

    // public static boolean aabb_collision(Rectangle rect1, Rectangle rect2){
    //     return
    //     (rect1.x < rect2.x && rect1.x + rect1.width > rect2.x ||
    //     rect1.x < rect2.x + rect2.width && rect1.x + rect1.width > rect2.x + rect2.width)
    //     &&
    //     (rect1.y < rect2.y && rect1.y + rect1.height > rect2.y ||
    //     rect1.y < rect2.y + rect2.height && rect1.y + rect1.height > rect2.y + rect2.height)
    //     ;
    // }
}
