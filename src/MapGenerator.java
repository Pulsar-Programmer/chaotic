import java.awt.Point;
import java.util.HashMap;

final class MapGenerator {

    /** An escape room puzzle. */
    public static final int ESCAPE_ROOM = 0;
    public static final int BABA_IS_YOU = 1;
    public static final int TIC_TAC_TOE = 2;
    public static final int LOGIC = 3;


    // public static Map generate(){

    // }

    //In each map, there is a boss room or some kind of upgrade room, at least two puzzle rooms, four enemy rooms. Completing a puzzle gives you a devils coin which is a collectible. There are doors that need to be unlocked.
    //The corrdors in each map can change

    public static Map standard_corridor(int length, boolean is_horizontal, int tiling){
        HashMap<Point, Tile> map = new HashMap<Point, Tile>();
        if(is_horizontal){
            map.put(new Point(0, 0), Tile.with_collision(9));
            map.put(new Point(0, 1), Tile.with_collision(2));
            map.put(new Point(0, 2), Tile.with_collision(2));
            map.put(new Point(0, 3), Tile.with_collision(2));
            map.put(new Point(0, 4), Tile.with_collision(7));
            for(int i = 1; i < length+1; i++){
                Tile tile = new Tile(i % tiling == 0 ? 1 : 0);
                map.put(new Point(i, 0), Tile.with_collision(5));
                map.put(new Point(i, 1), tile);
                map.put(new Point(i, 2), new Tile(0));
                map.put(new Point(i, 3), tile);
                map.put(new Point(i, 4), Tile.with_collision(4));
            }
            map.put(new Point(length + 1, 0), Tile.with_collision(8));
            map.put(new Point(length + 1, 1), Tile.with_collision(3));
            map.put(new Point(length + 1, 2), Tile.with_collision(3));
            map.put(new Point(length + 1, 3), Tile.with_collision(3));
            map.put(new Point(length + 1, 4), Tile.with_collision(6));
        } else {
            map.put(new Point(0, 0), Tile.with_collision(9));
            map.put(new Point(1, 0), Tile.with_collision(5));
            map.put(new Point(2, 0), Tile.with_collision(5));
            map.put(new Point(3, 0), Tile.with_collision(5));
            map.put(new Point(4, 0), Tile.with_collision(8));
            for(int i = 1; i < length+1; i++){
                Tile tile = new Tile(i % tiling == 0 ? 1 : 0);
                map.put(new Point(0, i), Tile.with_collision(2));
                map.put(new Point(1, i), tile);
                map.put(new Point(2, i), new Tile(0));
                map.put(new Point(3, i), tile);
                map.put(new Point(4, i), Tile.with_collision(3));
            }
            map.put(new Point(0, length+1), Tile.with_collision(7));
            map.put(new Point(1, length+1), Tile.with_collision(4));
            map.put(new Point(2, length+1), Tile.with_collision(4));
            map.put(new Point(3, length+1), Tile.with_collision(4));
            map.put(new Point(4, length+1), Tile.with_collision(6));
            //do the above if you want to end it in some places -> we need a .branch
        }
        return Map.from_tiles(map);
    }
    
    public static Map tight_corridor(int length, boolean is_horizontal, int tiling){
        HashMap<Point, Tile> map = new HashMap<Point, Tile>();
        if(is_horizontal){
            map.put(new Point(0, 0), Tile.with_collision(9));
            map.put(new Point(0, 1), Tile.with_collision(2));
            map.put(new Point(0, 2), Tile.with_collision(7));
            for(int i = 1; i < length+1; i++){
                Tile tile = new Tile(i % tiling == 0 ? 1 : 0);
                map.put(new Point(i, 0), Tile.with_collision(5));
                map.put(new Point(i, 1), tile);
                map.put(new Point(i, 2), Tile.with_collision(4));
            }
            map.put(new Point(length + 1, 0), Tile.with_collision(8));
            map.put(new Point(length + 1, 1), Tile.with_collision(3));
            map.put(new Point(length + 1, 2), Tile.with_collision(6));
        } else {
            map.put(new Point(0, 0), Tile.with_collision(9));
            map.put(new Point(1, 0), Tile.with_collision(5));
            map.put(new Point(2, 0), Tile.with_collision(8));
            for(int i = 1; i < length+1; i++){
                Tile tile = new Tile(i % tiling == 0 ? 1 : 0);
                map.put(new Point(0, i), Tile.with_collision(2));
                map.put(new Point(1, i), tile);
                map.put(new Point(2, i), Tile.with_collision(3));
            }
            map.put(new Point(0, length+1), Tile.with_collision(7));
            map.put(new Point(1, length+1), Tile.with_collision(4));
            map.put(new Point(2, length+1), Tile.with_collision(6));
            //do the above if you want to end it in some places -> we need a .branch
        }
        return Map.from_tiles(map);
    }
    
    public static Map locked_corridor(int length, boolean is_horizontal, int tiling){
        var corridor = tight_corridor(length, is_horizontal, tiling);
        //TODO
        return Map.new_map();
    }

    public static Map generic_room(int dim_x, int dim_y){
        var tiles = new HashMap<Point, Tile>();
        tiles.put(new Point(0, 0), Tile.with_collision(9));
        tiles.put(new Point(dim_x, 0), Tile.with_collision(8));
        tiles.put(new Point(0, dim_y), Tile.with_collision(7));
        tiles.put(new Point(dim_x, dim_y), Tile.with_collision(6));
        for(var i = 1; i < dim_x; i++){
            tiles.put(new Point(i, 0), Tile.with_collision(5));
        }
        for(var j = 1; j < dim_y; j++){
            tiles.put(new Point(0, j), Tile.with_collision(2));
        }
        for(var i = 1; i < dim_x; i++){
            tiles.put(new Point(i, dim_y), Tile.with_collision(4));
        }
        for(var j = 1; j < dim_y; j++){
            tiles.put(new Point(dim_x, j), Tile.with_collision(3));
        }
        for(var i = 1; i < dim_x; i++){
            for(var j = 1; j < dim_y; j++){
                var p = new Point(i, j);
                tiles.put(p, new Tile(0));
            }
        }
        return Map.from_tiles(tiles);
    }

    public static Map puzzle_room(int dim_x, int dim_y){
        var room = generic_room(dim_x, dim_y);
        //TODO
        return Map.new_map();
    }

    public static Map enemy_room(Point patrol_start, Point patrol_end, int enemy, int dim_x, int dim_y){
        var room = generic_room(dim_x, dim_y);
        var tiles = room.getTiles();
        for(var i = patrol_start.x; i <= patrol_end.x; i++){
            tiles.put(new Point(i, patrol_start.y), new Tile(1));
        }
        for(var i = patrol_start.x; i <= patrol_end.x; i++){
            tiles.put(new Point(i, patrol_end.y), new Tile(1));
        }
        for(var j = patrol_start.y; j <= patrol_end.y; j++){
            tiles.put(new Point(patrol_start.x, j), new Tile(1));
        }
        for(var j = patrol_start.y; j <= patrol_end.y; j++){
            tiles.put(new Point(patrol_end.x, j), new Tile(1));
        }
        //TODO
        return room;
    }

    public static Map boss_room(){
        //TODO
        return Map.new_map();
    }

    public static Map procedure(){

        boolean placement = gen_range(2) == 1 ? true : false;
        var first_corridor = MapGenerator.standard_corridor(gen_range(100, 200), placement, gen_range(3, 20));
        var second_corridor = MapGenerator.tight_corridor(gen_range(30, 70), !placement, gen_range(3, 10));
        first_corridor.branch(second_corridor, new Point(4, 0));
        //TODO
        return first_corridor;
    }

    //puzzles: escape room, tic tac toe, baba is you



    public static int gen_range(int max){
        return (int)(Math.random() * max);
    }
    public static int gen_range(int low, int high){
        return (int)(Math.random() * (high - low)) + low;
    }
}
