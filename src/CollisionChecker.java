import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    //round_divide fn is dead

    public boolean checkUp(Collider entity){
        int left_x = entity.world_x() + entity.collider_rect().x;
        int right_x = entity.world_x() + entity.collider_rect().x + entity.collider_rect().width;
        int top_y = entity.world_y() + entity.collider_rect().y - entity.speed();

        int left_tile = left_x/GamePanel.TILE_SIZE;
        int right_tile = right_x/GamePanel.TILE_SIZE;
        int top_tile = top_y/GamePanel.TILE_SIZE;

        var tile1 = gp.tileManager.find(left_tile, top_tile);
        var tile2 = gp.tileManager.find(right_tile, top_tile);

        // check_teleporter(tile1);
        // check_teleporter(tile2);
    
        return tile1.has_collision || tile2.has_collision;
    }

    public boolean checkDown(Collider entity){
        int left_x = entity.world_x() + entity.collider_rect().x;
        int right_x = entity.world_x() + entity.collider_rect().x + entity.collider_rect().width;
        int bottom_y = entity.world_y() + entity.collider_rect().y + entity.collider_rect().height + entity.speed();
        
        int left_tile = left_x/GamePanel.TILE_SIZE;
        int right_tile = right_x/GamePanel.TILE_SIZE;
        int bottom_tile = bottom_y/GamePanel.TILE_SIZE;

        var tile3 = gp.tileManager.find(left_tile, bottom_tile);
        var tile4 = gp.tileManager.find(right_tile, bottom_tile);

        // check_teleporter(tile3);
        // check_teleporter(tile4);

        return tile3.has_collision || tile4.has_collision;
    }

    public boolean checkLeft(Collider entity){
        int left_x = entity.world_x() + entity.collider_rect().x - entity.speed();
        int top_y = entity.world_y() + entity.collider_rect().y;
        int bottom_y = entity.world_y() + entity.collider_rect().y + entity.collider_rect().height;
        
        int left_tile = left_x/GamePanel.TILE_SIZE;
        int top_tile = top_y/GamePanel.TILE_SIZE;
        int bottom_tile = bottom_y/GamePanel.TILE_SIZE;

        var tile1 = gp.tileManager.find(left_tile, top_tile);
        var tile3 = gp.tileManager.find(left_tile, bottom_tile);

        // check_teleporter(tile1);
        // check_teleporter(tile3);

        return tile1.has_collision || tile3.has_collision;
    }

    public boolean checkRight(Collider entity){
        int right_x = entity.world_x() + entity.collider_rect().x + entity.collider_rect().width + entity.speed();
        int top_y = entity.world_y() + entity.collider_rect().y;
        int bottom_y = entity.world_y() + entity.collider_rect().y + entity.collider_rect().height;
        
        int right_tile = right_x/GamePanel.TILE_SIZE;
        int top_tile = top_y/GamePanel.TILE_SIZE;
        int bottom_tile = bottom_y/GamePanel.TILE_SIZE;

        var tile2 = gp.tileManager.find(right_tile, top_tile);
        var tile4 = gp.tileManager.find(right_tile, bottom_tile);

        // check_teleporter(tile2);
        // check_teleporter(tile4);

        return tile4.has_collision || tile2.has_collision;
    }

    // private void check_teleporter(Tile tile){
        
    // }

    public static ArrayList<Integer> check_intersections(Collider entity, ArrayList<? extends Collider> entities){
        var list = new ArrayList<Integer>();

        for(var i = 0; i < entities.size(); i++){

            var entity_rect = new Rectangle();
            var creature_rect = new Rectangle();

            entity_rect.x = entity.world_x() + entity.collider_rect().x;
            entity_rect.y = entity.world_y() + entity.collider_rect().y;
            entity_rect.width = entity.collider_rect().width;
            entity_rect.height = entity.collider_rect().height;

            var creature = entities.get(i);
            creature_rect.x = creature.world_x() + creature.collider_rect().x;
            creature_rect.y = creature.world_y() + creature.collider_rect().y;
            creature_rect.width = creature.collider_rect().width;
            creature_rect.height = creature.collider_rect().height;

            if(entity_rect.intersects(creature_rect)){
                list.add(i);
            }

        }

        return list;
    }

    public static boolean check_intersection(Collider entity, Collider creature){
        var entity_rect = new Rectangle();
        var creature_rect = new Rectangle();

        entity_rect.x = entity.world_x() + entity.collider_rect().x;
        entity_rect.y = entity.world_y() + entity.collider_rect().y;
        entity_rect.width = entity.collider_rect().width;
        entity_rect.height = entity.collider_rect().height;

        creature_rect.x = creature.world_x() + creature.collider_rect().x;
        creature_rect.y = creature.world_y() + creature.collider_rect().y;
        creature_rect.width = creature.collider_rect().width;
        creature_rect.height = creature.collider_rect().height;

        return entity_rect.intersects(creature_rect);
    }
}
