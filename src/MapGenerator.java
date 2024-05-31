import java.awt.Point;
import java.util.HashMap;

final class MapGenerator {
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

    public static Map generic_room(int dim_x, int dim_y){
        //TODO
        return Map.new_map();
    }

    public static Map puzzle_room(int dim_x, int dim_y){
        var room = generic_room(dim_x, dim_y);
        //TODO
        return Map.new_map();
    }

    public static Map enemy_room(){
        //TODO
        return Map.new_map();
    }

    public static Map boss_room(){
        //TODO
        return Map.new_map();
    }

    public static Map procedure(){

        boolean placement = gen_range(2) == 1 ? true : false;
        var first_corridor = MapGenerator.standard_corridor(gen_range(100, 200), placement, gen_range(3, 20));
        var second_corridor = MapGenerator.standard_corridor(gen_range(30, 70), !placement, gen_range(3, 10));
        first_corridor.branch(second_corridor, new Point(4, 0));
        //TODO
        return first_corridor;
    }



    public static int gen_range(int max){
        return (int)(Math.random() * max);
    }
    public static int gen_range(int low, int high){
        return (int)(Math.random() * (high - low)) + low;
    }
}
