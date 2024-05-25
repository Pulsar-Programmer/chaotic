import java.awt.Rectangle;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public boolean checkTile(Entity entity){

        int entityLeftWorldX = entity.world_x + entity.solidArea.x;
        int entityRightWorldX = entity.world_x + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.world_y + entity.solidArea.y;
        int entityBottomWorldY = entity.world_y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        Tile tile1, tile2;

        //I spent so much time here so I'm just gonna copy down verbatim

        switch (entity.direction) {
            case Entity.up:{
                entityTopRow = (entityTopWorldY - entity.speed) /gp.tileSize;
                tile1 = gp.tileManager.find(entityLeftCol, entityTopRow);
                tile2 = gp.tileManager.find(entityRightCol, entityTopRow);
                if (tile1.has_collision || tile2.has_collision){
                    return true;
                }
            } break;
            case Entity.down:{
                entityBottomRow = (entityBottomWorldY + entity.speed) /gp.tileSize;
                tile1 = gp.tileManager.find(entityLeftCol, entityBottomRow);
                tile2 = gp.tileManager.find(entityRightCol, entityBottomRow);
                if (tile1.has_collision || tile2.has_collision){
                    return true;
                }
            } break;
            case Entity.left:{
                entityLeftCol = (entityLeftWorldX - entity.speed) /gp.tileSize;
                tile1 = gp.tileManager.find(entityLeftCol, entityTopRow);
                tile2 = gp.tileManager.find(entityLeftCol, entityBottomRow);
                if (tile1.has_collision || tile2.has_collision){
                    return true;
                }
            } break;
            case Entity.right:{
                entityRightCol = (entityRightWorldX + entity.speed) /gp.tileSize;
                tile1 = gp.tileManager.find(entityRightCol, entityTopRow);
                tile2 = gp.tileManager.find(entityRightCol, entityBottomRow);
                if (tile1.has_collision || tile2.has_collision){
                    return true;
                }
            } break;
            default: break;
        }
        return false;
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
