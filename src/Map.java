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
}
