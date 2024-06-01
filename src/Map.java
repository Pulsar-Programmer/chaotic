import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;

public class Map {
    
    private HashMap<Point, Tile> tiles;
    private ArrayList<Object> objects;
    private ArrayList<Monster> monsters;
    private Point player_spawn;

    //FACTORY FUNCTIONS

    private Map(){
        tiles = new HashMap<>();
        objects = new ArrayList<>();
        monsters = new ArrayList<>();
        player_spawn = new Point(1, 1);
    }

    public static Map new_map(){
        return new Map();
    }

    public static Map from_tiles(HashMap<Point, Tile> tiles){
        var map = new Map();
        map.tiles = tiles;
        return map;
    }

    //SETTERS & GETTERS

    
    
    
    public void setTiles(HashMap<Point, Tile> tiles) {
        this.tiles = tiles;
    }

    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public void setPlayer_spawn(Point player_spawn) {
        this.player_spawn = player_spawn;
    }

    public HashMap<Point, Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public Point getPlayer_spawn() {
        return player_spawn;
    }


    //ETC.

    public static Map translate(Map map, Point by){
        var translated = new_map();

        map.tiles.forEach((p, t)->{
            var p_2 = new Point(p.x + by.x, p.y + by.y);
            translated.tiles.put(p_2, t);
        });

        map.objects.forEach((obj) -> {
            obj.world_x += by.x * GamePanel.TILE_SIZE;
            obj.world_y += by.y * GamePanel.TILE_SIZE;
        });

        map.monsters.forEach((mon) -> {
            mon.world_x += by.x * GamePanel.TILE_SIZE;
            mon.world_y += by.y * GamePanel.TILE_SIZE;
        });

        map.player_spawn.translate(by.x, by.y);

        return translated;
    }

    public void layer(Map layer){
        tiles.putAll(layer.tiles);
        objects.addAll(layer.objects);
        monsters.addAll(layer.monsters);
    }

    /**
     * This works just like the `layer` function but eliminates tiles that collide at the same point unless they are the same tile.
     */
    public void boolean_layer(Map layer){
        layer.tiles.forEach((p, t) ->{
            if(!tiles.containsKey(p)){
                tiles.put(p, t);
            } else {
                if(t.sprite == tiles.get(p).sprite){
                    return;
                }
                tiles.remove(p);
            }
        });
        objects.addAll(layer.objects);
        monsters.addAll(layer.monsters);
    }

    public Map branch(Map branch, Point cut){
        var appendable_branch = Map.translate(branch, cut);
        this.boolean_layer(appendable_branch);
        return this;
    }

    // public void invert_x(){
    //     var gen = new HashMap<Point, Tile>();
    //     tiles.forEach((p, t) -> {
    //         gen.put(new Point(-p.x, p.y), t);
    //     });
    //     tiles = gen;
    // }

    // public void invert_y(){
    //     var gen = new HashMap<Point, Tile>();
    //     tiles.forEach((p, t) -> {
    //         gen.put(new Point(p.x, -p.y), t);
    //     });
    //     tiles = gen;
    // }

    // public void invert_xy(){
    //     var gen = new HashMap<Point, Tile>();
    //     tiles.forEach((p, t) -> {
    //         gen.put(new Point(-p.x, -p.y), t);
    //     });
    //     tiles = gen;
    // }

    public void rebase_origin(){
        var gen = new HashMap<Point, Tile>();
        int min_x = Integer.MAX_VALUE;
        int min_y = Integer.MAX_VALUE;
        for (Point p : tiles.keySet()) {
            min_x = Math.min(min_x, p.x);
            min_y = Math.min(min_y, p.y);
        }
        final int min_x_safe = min_x;
        final int min_y_safe = min_y;
        tiles.forEach((p, t) -> {
            var shifted = new Point(p.x - min_x_safe, p.y - min_y_safe);
            gen.put(shifted, t);
        });
        tiles = gen;
    }

    public void rebase_x(){
        var gen = new HashMap<Point, Tile>();
        int max_x = Integer.MIN_VALUE;
        int min_y = Integer.MAX_VALUE;
        for (Point p : tiles.keySet()) {
            max_x = Math.max(max_x, p.x);
            min_y = Math.min(min_y, p.y);
        }
        final int max_x_safe = max_x;
        final int min_y_safe = min_y;
        tiles.forEach((p, t) -> {
            var shifted = new Point(p.x - max_x_safe, p.y - min_y_safe);
            gen.put(shifted, t);
        });
        tiles = gen;
    }

    public void rebase_y(){
        var gen = new HashMap<Point, Tile>();
        int min_x = Integer.MAX_VALUE;
        int max_y = Integer.MIN_VALUE;
        for (Point p : tiles.keySet()) {
            min_x = Math.min(min_x, p.x);
            max_y = Math.max(max_y, p.y);
        }
        final int min_x_safe = min_x;
        final int max_y_safe = max_y;
        tiles.forEach((p, t) -> {
            var shifted = new Point(p.x - min_x_safe, p.y - max_y_safe);
            gen.put(shifted, t);
        });
        tiles = gen;
    }

    public void rebase_xy(){
        var gen = new HashMap<Point, Tile>();
        int max_x = Integer.MIN_VALUE;
        int max_y = Integer.MIN_VALUE;
        for (Point p : tiles.keySet()) {
            max_x = Math.max(max_x, p.x);
            max_y = Math.max(max_y, p.y);
        }
        final int max_x_safe = max_x;
        final int max_y_safe = max_y;
        tiles.forEach((p, t) -> {
            var shifted = new Point(p.x - max_x_safe, p.y - max_y_safe);
            gen.put(shifted, t);
        });
        tiles = gen;
    }

    
}
