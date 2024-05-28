import java.awt.Rectangle;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public boolean checkUp(Entity entity){
        int left_x = entity.world_x + entity.solidArea.x;
        int right_x = entity.world_x + entity.solidArea.x + entity.solidArea.width;
        int top_y = entity.world_y + entity.solidArea.y - entity.speed;
        
        int left_tile = left_x/GamePanel.TILE_SIZE;
        int right_tile = right_x/GamePanel.TILE_SIZE;
        int top_tile = top_y/GamePanel.TILE_SIZE;

        var tile1 = gp.tileManager.find(left_tile, top_tile);
        var tile2 = gp.tileManager.find(right_tile, top_tile);
        // System.out.println("Up: " + tile1.has_collision + " " + tile2.has_collision);
        return tile1.has_collision || tile2.has_collision;
    }

    public boolean checkDown(Entity entity){
        int left_x = entity.world_x + entity.solidArea.x;
        int right_x = entity.world_x + entity.solidArea.x + entity.solidArea.width;
        int bottom_y = entity.world_y + entity.solidArea.y + entity.solidArea.height + entity.speed;
        
        int left_tile = left_x/GamePanel.TILE_SIZE;
        int right_tile = right_x/GamePanel.TILE_SIZE;
        int bottom_tile = bottom_y/GamePanel.TILE_SIZE;

        var tile3 = gp.tileManager.find(left_tile, bottom_tile);
        var tile4 = gp.tileManager.find(right_tile, bottom_tile);
        // System.out.println("Down: " + tile3.has_collision + " " + tile4.has_collision);
        return tile3.has_collision || tile4.has_collision;
    }

    public boolean checkLeft(Entity entity){
        int left_x = entity.world_x + entity.solidArea.x - entity.speed;
        int top_y = entity.world_y + entity.solidArea.y;
        int bottom_y = entity.world_y + entity.solidArea.y + entity.solidArea.height;
        
        int left_tile = left_x/GamePanel.TILE_SIZE;
        int top_tile = top_y/GamePanel.TILE_SIZE;
        int bottom_tile = bottom_y/GamePanel.TILE_SIZE;

        var tile1 = gp.tileManager.find(left_tile, top_tile);
        var tile3 = gp.tileManager.find(left_tile, bottom_tile);
        // System.out.println("Left: " + tile1.has_collision + " " + tile3.has_collision);
        return tile1.has_collision || tile3.has_collision;
    }

    public boolean checkRight(Entity entity){
        int right_x = entity.world_x + entity.solidArea.x + entity.solidArea.width + entity.speed;
        int top_y = entity.world_y + entity.solidArea.y;
        int bottom_y = entity.world_y + entity.solidArea.y + entity.solidArea.height;
        
        int right_tile = right_x/GamePanel.TILE_SIZE;
        int top_tile = top_y/GamePanel.TILE_SIZE;
        int bottom_tile = bottom_y/GamePanel.TILE_SIZE;

        var tile2 = gp.tileManager.find(right_tile, top_tile);
        var tile4 = gp.tileManager.find(right_tile, bottom_tile);
        // System.out.println("Right: " + tile2.has_collision + " " + tile4.has_collision);
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

    public int checkObject(Entity entity, boolean is_player){
        int index = -1;

        for(var i = 0; i < gp.objectManager.objects.size(); i++){

            var entity_rect = new Rectangle();
            var object_rect = new Rectangle();

            entity_rect.x = entity.world_x + entity.solidArea.x;
            entity_rect.y = entity.world_y + entity.solidArea.y;
            entity_rect.width = entity.solidArea.width;
            entity_rect.height = entity.solidArea.height;

            var object = gp.objectManager.objects.get(i);
            object_rect.x = object.world_x + object.solidArea.x;
            object_rect.y = object.world_y + object.solidArea.y;
            object_rect.width = object.solidArea.width;
            object_rect.height = object.solidArea.height;

            if(entity_rect.intersects(object_rect)){
                index = i;
            }

        }

        return index;
    }
}
