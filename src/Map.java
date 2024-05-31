import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;

public class Map {
    
    private HashMap<Point, Tile> tiles;
    private ArrayList<Object> objects;
    private ArrayList<Monster> monsters;

    //FACTORY FUNCTIONS

    private Map(){
        tiles = new HashMap<>();
        objects = new ArrayList<>();
        monsters = new ArrayList<>();
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

    public HashMap<Point, Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
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
}
