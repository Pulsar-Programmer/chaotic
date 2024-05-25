public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public boolean checkTile(Entity entity){
        int left = entity.world_x + entity.solidArea.x;
        int right = entity.world_x + entity.solidArea.x + entity.solidArea.width;
        int top = entity.world_y + entity.solidArea. y;
        int bottom = entity.world_y + entity.solidArea.y + entity.solidArea.height;

        int e_left = (left) / gp.tileSize;
        int e_right = (right) / gp.tileSize;
        int e_top = (top) / gp.tileSize;
        int e_bottom = (bottom) / gp.tileSize;

        int[] pick = {e_top, e_bottom, e_left, e_right};
        int idx = entity.direction / 2;
        int idx_1 = (((idx / 2) + 1) % 2) * 2;
        int idx_2 = idx_1 + 1;
        pick[idx] = (pick[idx] * gp.tileSize - entity.speed) / gp.tileSize;
        //i wrote this during a fever dream
        Tile[] tilepick1 = {gp.tileManager.find(pick[idx], pick[idx_1]), gp.tileManager.find(pick[idx_1], pick[idx])};
        Tile[] tilepick2 = {gp.tileManager.find(pick[idx], pick[idx_2]), gp.tileManager.find(pick[idx_2], pick[idx])};
        //find is super inefficient and needs help. Plus, this is kind of a sucky way to do my goal.
        Tile tile1 = tilepick1[(idx / 2 + 1) % 2];
        Tile tile2 = tilepick2[(idx / 2 + 1) % 2];
        return tile1.has_collision || tile2.has_collision;
    }
}
