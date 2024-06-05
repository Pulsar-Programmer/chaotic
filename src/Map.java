import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;

public class Map {
    
    /** Represents the tiles in the Map. */
    private HashMap<Point, Tile> tiles;
    /** Represents the objects in the Map. */
    private ArrayList<Object> objects;
    /** Represents the monsters in the Map. */
    private ArrayList<Monster> monsters;
    /** Represents the player spawn in the Map. */
    private Point player_spawn;

    //FACTORY FUNCTIONS

    private Map(){
        tiles = new HashMap<>();
        objects = new ArrayList<>();
        monsters = new ArrayList<>();
        player_spawn = new Point(0, 0);
    }

    /** Creates a new map safely. */
    public static Map new_map(){
        return new Map();
    }

    /** Creates a map from a given tileset. */
    public static Map from_tiles(HashMap<Point, Tile> tiles){
        var map = new Map();
        map.tiles = tiles;
        return map;
    }

    //SETTERS & GETTERS

    
    
    /** Sets the tiles of the map. */
    public void setTiles(HashMap<Point, Tile> tiles) {
        this.tiles = tiles;
    }

    /** Sets the objects of the map. */
    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }

    /** Sets the monsters of the map. */
    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    /** Sets the player spawn of the map. */
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

    /** Moves the map by a given Point distance in Tile lengths and makes a clone. */
    public static Map translate(Map map, Point by){
        var translated = new_map();

        map.tiles.forEach((p, t)->{
            var p_2 = new Point(p.x + by.x, p.y + by.y);
            translated.tiles.put(p_2, t);
        });

        map.objects.forEach((obj) -> {
            obj.world_x += by.x * GamePanel.TILE_SIZE;
            obj.world_y += by.y * GamePanel.TILE_SIZE;
            translated.objects.add(obj);
            translated.objects.add(obj);
        });

        map.monsters.forEach((mon) -> {
            mon.world_x += by.x * GamePanel.TILE_SIZE;
            mon.world_y += by.y * GamePanel.TILE_SIZE;

            mon.patrol_start.x += by.x * GamePanel.TILE_SIZE;
            mon.patrol_start.y += by.y * GamePanel.TILE_SIZE;
            mon.patrol_end.x += by.x * GamePanel.TILE_SIZE;
            mon.patrol_end.y += by.y * GamePanel.TILE_SIZE;
            
            translated.monsters.add(mon);
        });

        map.player_spawn.translate(by.x, by.y);
        translated.player_spawn = map.player_spawn;
        translated.player_spawn = map.player_spawn;

        return translated;
    }

    /** Moves the map by a given Point distance in Tile lengths. */
    public void translate_map(final int x, final int y){
        var gen = new HashMap<Point, Tile>();
        tiles.forEach((p, t) -> {
            var shifted = new Point(p.x + x, p.y + y);
            gen.put(shifted, t);
        });
        tiles = gen;

        for(var i = 0; i < objects.size(); i++){
            var obj = objects.get(i);
            obj.world_x += x * GamePanel.TILE_SIZE;
            obj.world_y += y * GamePanel.TILE_SIZE;
        }
        for(var i = 0; i < monsters.size(); i++){
            var mon = monsters.get(i);
            mon.world_x += x * GamePanel.TILE_SIZE;
            mon.world_y += y * GamePanel.TILE_SIZE;

            mon.patrol_start.x += x * GamePanel.TILE_SIZE;
            mon.patrol_start.y += y * GamePanel.TILE_SIZE;
            mon.patrol_end.x += x * GamePanel.TILE_SIZE;
            mon.patrol_end.y += y * GamePanel.TILE_SIZE;
        }
        player_spawn.x += x;
        player_spawn.y += y;
    }

    /** Layers a map on top of another map, removing underlying tiles, retaining the player spawn, and overlaying the objects and monsters. */
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

    // public void boolean_layer_stitch(Map layer){
    //     layer.tiles.forEach((p, t) ->{
    //         if(!tiles.containsKey(p)){
    //             tiles.put(p, t);
    //         } else {
    //             var prev = tiles.get(p).sprite;
    //             if(t.sprite == prev || t.sprite == 0 || t.sprite == 1 || t.sprite == 2){
    //                 return;
    //             }
    //             System.out.println(prev + ":" + t.sprite);
    //             tiles.remove(p);
    //         }
    //     });
    //     objects.addAll(layer.objects);
    //     monsters.addAll(layer.monsters);
    // }

    /** Branches the map at a certain location, allowing one to chain map creating operations effectively. 
     * This is a combination of the translate and boolean layer function.
    */
    public Map branch(Map branch, Point cut){
        var appendable_branch = Map.translate(branch, cut);
        this.boolean_layer(appendable_branch);
        return this;
    }

    public Map direct_branch(Map branch, Point cut){
        var appendable_branch = Map.translate(branch, cut);
        this.layer(appendable_branch);
        return this;
    }

    /** Moves the map such that, when drawn, it expands in the regular (bottom-right) quadrant. The drawing point can be thought of being moved to the top left of the map. */
    public void rebase_origin(){
        int min_x = Integer.MAX_VALUE;
        int min_y = Integer.MAX_VALUE;
        for (Point p : tiles.keySet()) {
            min_x = Math.min(min_x, p.x);
            min_y = Math.min(min_y, p.y);
        }
        for (Monster monster : monsters) {
            min_x = Math.min(min_x, monster.world_x / GamePanel.TILE_SIZE);
            min_y = Math.min(min_y, monster.world_y / GamePanel.TILE_SIZE);
        }
        for (Object object : objects) {
            min_x = Math.min(min_x, object.world_x / GamePanel.TILE_SIZE);
            min_y = Math.min(min_y, object.world_y / GamePanel.TILE_SIZE);
        }
        min_x = Math.min(min_x, player_spawn.x);
        min_y = Math.min(min_y, player_spawn.y);

        translate_map(-min_x, -min_y);
    }

    /** Moves the map such that, when drawn, it expands in the bottom left quadrant. The drawing point can be thought of being moved to the top right of the map. */
    public void rebase_x(){
        int max_x = Integer.MIN_VALUE;
        int min_y = Integer.MAX_VALUE;
        for (Point p : tiles.keySet()) {
            max_x = Math.max(max_x, p.x);
            min_y = Math.min(min_y, p.y);
        }
        for (Monster monster : monsters) {
            max_x = Math.max(max_x, monster.world_x / GamePanel.TILE_SIZE);
            min_y = Math.min(min_y, monster.world_y / GamePanel.TILE_SIZE);
        }
        for (Object object : objects) {
            max_x = Math.max(max_x, object.world_x / GamePanel.TILE_SIZE);
            min_y = Math.min(min_y, object.world_y / GamePanel.TILE_SIZE);
        }
        max_x = Math.max(max_x, player_spawn.x);
        min_y = Math.min(min_y, player_spawn.y);

        translate_map(-max_x, -min_y);
    }

    /** Moves the map such that, when drawn, it expands in the top right quadrant. The drawing point can be thought of being moved to the bottom left of the map. */
    public void rebase_y(){
        int min_x = Integer.MAX_VALUE;
        int max_y = Integer.MIN_VALUE;
        for (Point p : tiles.keySet()) {
            min_x = Math.min(min_x, p.x);
            max_y = Math.max(max_y, p.y);
        }
        for (Monster monster : monsters) {
            min_x = Math.min(min_x, monster.world_x / GamePanel.TILE_SIZE);
            max_y = Math.max(max_y, monster.world_y / GamePanel.TILE_SIZE);
        }
        for (Object object : objects) {
            min_x = Math.min(min_x, object.world_x / GamePanel.TILE_SIZE);
            max_y = Math.max(max_y, object.world_y / GamePanel.TILE_SIZE);
        }
        min_x = Math.min(min_x, player_spawn.x);
        max_y = Math.max(max_y, player_spawn.y);

        translate_map(-min_x, -max_y);
    }

    /** Moves the map such that, when drawn, it expands in the top left quadrant. The drawing point can be thought of being moved to the bottom right of the map. */
    public void rebase_xy(){
        int max_x = Integer.MIN_VALUE;
        int max_y = Integer.MIN_VALUE;
        for (Point p : tiles.keySet()) {
            max_x = Math.max(max_x, p.x);
            max_y = Math.max(max_y, p.y);
        }
        for (Monster monster : monsters) {
            max_x = Math.max(max_x, monster.world_x / GamePanel.TILE_SIZE);
            max_y = Math.max(max_y, monster.world_y / GamePanel.TILE_SIZE);
        }
        for (Object object : objects) {
            max_x = Math.max(max_x, object.world_x / GamePanel.TILE_SIZE);
            max_y = Math.max(max_y, object.world_y / GamePanel.TILE_SIZE);
        }
        max_x = Math.max(max_x, player_spawn.x);
        max_y = Math.max(max_y, player_spawn.y);

        translate_map(-max_x, -max_y);
    }

    public Map branch_up(Map branch, Point cut){
        branch.rebase_y();
        var branched = branch.branch(branch, cut);
        branched.five_stitch(Entity.UP, cut);
        return branched;
    }
    public Map branch_down(Map branch, Point cut){
        var branched = branch.branch(branch, cut);
        branched.five_stitch(Entity.DOWN, cut);
        return branched;
    }
    public Map branch_down_checked(Map branch, Point cut){
        branch.rebase_origin();
        var branched = branch.branch(branch, cut);
        branched.five_stitch(Entity.DOWN, cut);
        return branched;
    }
    public Map branch_left(Map branch, Point cut){
        branch.rebase_x();
        var branched = branch.branch(branch, cut);
        branched.five_stitch(Entity.LEFT, cut);
        return branched;
    }
    public Map branch_right(Map branch, Point cut){
        var branched = branch.branch(branch, cut);
        branched.five_stitch(Entity.RIGHT, cut);
        return branched;
    }
    public Map branch_right_checked(Map branch, Point cut){
        branch.rebase_origin();
        var branched = branch.branch(branch, cut);
        branched.five_stitch(Entity.RIGHT, cut);
        return branched;
    }

    public void five_stitch(int direction, Point loc){
        if(direction == Entity.UP){
            tiles.put(new Point(loc.x, loc.y), Tile.with_collision(15));
            tiles.put(new Point(loc.x + 1, loc.y), new Tile(0));
            tiles.put(new Point(loc.x + 2, loc.y), new Tile(0));
            tiles.put(new Point(loc.x + 3, loc.y), new Tile(0));
            tiles.put(new Point(loc.x + 4, loc.y), Tile.with_collision(16));
        } else
        if(direction == Entity.DOWN){
            tiles.put(new Point(loc.x, loc.y), Tile.with_collision(13));
            tiles.put(new Point(loc.x + 1, loc.y), new Tile(0));
            tiles.put(new Point(loc.x + 2, loc.y), new Tile(0));
            tiles.put(new Point(loc.x + 3, loc.y), new Tile(0));
            tiles.put(new Point(loc.x + 4, loc.y), Tile.with_collision(14));
        } else 
        if(direction == Entity.LEFT){
            tiles.put(new Point(loc.x, loc.y), Tile.with_collision(15));
            tiles.put(new Point(loc.x, loc.y + 1), new Tile(0));
            tiles.put(new Point(loc.x, loc.y + 2), new Tile(0));
            tiles.put(new Point(loc.x, loc.y + 3), new Tile(0));
            tiles.put(new Point(loc.x, loc.y + 4), Tile.with_collision(13));
        } else 
        if(direction == Entity.RIGHT){
            tiles.put(new Point(loc.x, loc.y), Tile.with_collision(16));
            tiles.put(new Point(loc.x, loc.y + 1), new Tile(0));
            tiles.put(new Point(loc.x, loc.y + 2), new Tile(0));
            tiles.put(new Point(loc.x, loc.y + 3), new Tile(0));
            tiles.put(new Point(loc.x, loc.y + 4), Tile.with_collision(14));
        }
    }
    
}
