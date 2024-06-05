import java.awt.Point;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;
import java.io.File;
final class MapGenerator {

    /** An escape room puzzle. */
    public static final int ESCAPE_ROOM = 0; //has a key and some other stuff
    public static final int BABA_IS_YOU = 1; //baba words to move around
    public static final int TIC_TAC_TOE = 2; //tic tac toe against computer
    public static final int LOGIC = 3; //electrical components, maybe redstone
    public static final int MAZE = 4; //cool maze
    //some of these rooms will be mandatory
    

    // public static Map generate(){

    // }

    //In each map, there is a boss room or some kind of upgrade room, at least two puzzle rooms, four enemy rooms. Completing a puzzle gives you a devils coin which is a collectible. There are doors that need to be unlocked.
    //The corrdors in each map can change

    public static Map standard_corridor(int length, boolean is_horizontal, int tiling){
        HashMap<Point, Tile> map = new HashMap<Point, Tile>();
        if(is_horizontal){
            map.put(new Point(0, 0), Tile.with_collision(10));
            map.put(new Point(0, 1), Tile.with_collision(3));
            map.put(new Point(0, 2), Tile.with_collision(3));
            map.put(new Point(0, 3), Tile.with_collision(3));
            map.put(new Point(0, 4), Tile.with_collision(8));
            for(int i = 1; i < length+1; i++){
                Tile tile = new Tile(i % tiling == 0 ? 1 : 0);
                map.put(new Point(i, 0), Tile.with_collision(6));
                map.put(new Point(i, 1), tile);
                map.put(new Point(i, 2), new Tile(0));
                map.put(new Point(i, 3), tile);
                map.put(new Point(i, 4), Tile.with_collision(5));
            }
            map.put(new Point(length + 1, 0), Tile.with_collision(9));
            map.put(new Point(length + 1, 1), Tile.with_collision(4));
            map.put(new Point(length + 1, 2), Tile.with_collision(4));
            map.put(new Point(length + 1, 3), Tile.with_collision(4));
            map.put(new Point(length + 1, 4), Tile.with_collision(7));
        } else {
            map.put(new Point(0, 0), Tile.with_collision(10));
            map.put(new Point(1, 0), Tile.with_collision(6));
            map.put(new Point(2, 0), Tile.with_collision(6));
            map.put(new Point(3, 0), Tile.with_collision(6));
            map.put(new Point(4, 0), Tile.with_collision(9));
            for(int i = 1; i < length+1; i++){
                Tile tile = new Tile(i % tiling == 0 ? 1 : 0);
                map.put(new Point(0, i), Tile.with_collision(3));
                map.put(new Point(1, i), tile);
                map.put(new Point(2, i), new Tile(0));
                map.put(new Point(3, i), tile);
                map.put(new Point(4, i), Tile.with_collision(4));
            }
            map.put(new Point(0, length+1), Tile.with_collision(8));
            map.put(new Point(1, length+1), Tile.with_collision(5));
            map.put(new Point(2, length+1), Tile.with_collision(5));
            map.put(new Point(3, length+1), Tile.with_collision(5));
            map.put(new Point(4, length+1), Tile.with_collision(7));
            //do the above if you want to end it in some places -> we need a .branch
        }
        return Map.from_tiles(map);
    }
    
    public static Map tight_corridor(int length, boolean is_horizontal, int tiling){
        HashMap<Point, Tile> map = new HashMap<Point, Tile>();
        if(is_horizontal){
            map.put(new Point(0, 0), Tile.with_collision(10));
            map.put(new Point(0, 1), Tile.with_collision(3));
            map.put(new Point(0, 2), Tile.with_collision(8));
            for(int i = 1; i < length+1; i++){
                Tile tile = new Tile(i % tiling == 0 ? 1 : 0);
                map.put(new Point(i, 0), Tile.with_collision(6));
                map.put(new Point(i, 1), tile);
                map.put(new Point(i, 2), Tile.with_collision(5));
            }
            map.put(new Point(length + 1, 0), Tile.with_collision(9));
            map.put(new Point(length + 1, 1), Tile.with_collision(4));
            map.put(new Point(length + 1, 2), Tile.with_collision(7));
        } else {
            map.put(new Point(0, 0), Tile.with_collision(10));
            map.put(new Point(1, 0), Tile.with_collision(6));
            map.put(new Point(2, 0), Tile.with_collision(9));
            for(int i = 1; i < length+1; i++){
                Tile tile = new Tile(i % tiling == 0 ? 1 : 0);
                map.put(new Point(0, i), Tile.with_collision(3));
                map.put(new Point(1, i), tile);
                map.put(new Point(2, i), Tile.with_collision(4));
            }
            map.put(new Point(0, length+1), Tile.with_collision(8));
            map.put(new Point(1, length+1), Tile.with_collision(5));
            map.put(new Point(2, length+1), Tile.with_collision(7));
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
        tiles.put(new Point(0, 0), Tile.with_collision(10));
        tiles.put(new Point(dim_x, 0), Tile.with_collision(9));
        tiles.put(new Point(0, dim_y), Tile.with_collision(8));
        tiles.put(new Point(dim_x, dim_y), Tile.with_collision(7));
        for(var i = 1; i < dim_x; i++){
            tiles.put(new Point(i, 0), Tile.with_collision(6));
        }
        for(var j = 1; j < dim_y; j++){
            tiles.put(new Point(0, j), Tile.with_collision(3));
        }
        for(var i = 1; i < dim_x; i++){
            tiles.put(new Point(i, dim_y), Tile.with_collision(5));
        }
        for(var j = 1; j < dim_y; j++){
            tiles.put(new Point(dim_x, j), Tile.with_collision(4));
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
        var tiles = room.getTiles();
        for(var i = 2; i < dim_x - 1; i++){
            for(var j = 2; j < dim_y - 1; j++){
                var p = new Point(i, j);
                if(gen_range(100) >= 50){
                    tiles.put(p, new Tile(2));
                    continue;
                }
                tiles.put(p, new Tile(1));
            }
        }
        //TODO
        return room;
    }

    public static Map enemy_room(Point patrol_start, Point patrol_end, Monster enemy, int dim_x, int dim_y){
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
        enemy.world_x = patrol_start.x * GamePanel.TILE_SIZE;
        enemy.world_y = patrol_start.y * GamePanel.TILE_SIZE;
        enemy.patrol_start.x = patrol_start.x * GamePanel.TILE_SIZE;
        enemy.patrol_end.x = patrol_end.x * GamePanel.TILE_SIZE;
        enemy.patrol_start.y = patrol_start.y * GamePanel.TILE_SIZE;
        enemy.patrol_end.y = patrol_end.y * GamePanel.TILE_SIZE;
        room.getMonsters().add(enemy);
        //TODO
        return room;
    }

    public static Map arkin_enemy_room(Point patrol_start, Point patrol_end, int dim_x, int dim_y){
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

        var enemy = Monster.skeleton();
        enemy.world_x = patrol_start.x * GamePanel.TILE_SIZE;
        enemy.world_y = patrol_start.y * GamePanel.TILE_SIZE;
        var enemy2 = gen_range(2) == 0 ? Monster.turret() : Monster.ghost();
        enemy2.world_x = patrol_end.x * GamePanel.TILE_SIZE;
        enemy2.world_y = patrol_end.y * GamePanel.TILE_SIZE;
        Monster[] array = {Monster.skeleton(), enemy2};
        for(var i = 0; i < array.length; i++){
            var enem = array[i];
            enem.patrol_start.x = patrol_start.x * GamePanel.TILE_SIZE;
            enem.patrol_end.x = patrol_end.x * GamePanel.TILE_SIZE;
            enem.patrol_start.y = patrol_start.y * GamePanel.TILE_SIZE;
            enem.patrol_end.y = patrol_end.y * GamePanel.TILE_SIZE;
            room.getMonsters().add(enem);
        }
        //TODO
        return room;
    }

    public static Map boss_room(int dim_x, int dim_y, int boss){
        var room = generic_room(dim_x, dim_y);
        var tiles = room.getTiles();
        for(var i = 1; i < dim_x; i++){
            for(var j = 1; j < dim_y; j++){
                var val = gen_range(100);
                if(val >= 10){
                    continue;
                }
                if(val >= 5){
                    tiles.put(new Point(i, j), new Tile(2));
                    continue;
                }
                tiles.put(new Point(i, j), new Tile(1));
            }
        }
        //TODO
        return room;
    }

    public static Map procedure(){

        // boolean orientation = gen_range(2) == 1 ? true : false; //determines if the first chorodor is up or down
        // var first_corridor = MapGenerator.standard_corridor(gen_range(100, 200), orientation, gen_range(3, 20));
        // var second_corridor = MapGenerator.tight_corridor(gen_range(30, 70), !orientation, gen_range(3, 10));
        // second_corridor.rebase_x();
        // first_corridor.branch(second_corridor, new Point(0, 0)); //bramch connects two sections, based on a point
        // first_corridor.rebase_origin(); //reseting

        //------------------------------------------------𝓯𝓻𝓮𝓪𝓴𝔂 section--------------------------------------------------------------------------------
        var room = MapGenerator.generic_room(10, 10); //spawn room
        room.setPlayer_spawn(new Point(3, 3));

        var top = MapGenerator.standard_corridor(5, false, 3); //top cut premath
        top.rebase_y();

        var bottom = MapGenerator.standard_corridor(5, false, 3); //bottom cut premath
        
        var left = MapGenerator.standard_corridor(5, true, 3); //left cut premath
        left.rebase_x();

        var right = MapGenerator.standard_corridor(5, true, 3); //right cut premath
        
        room.branch(top, new Point(5, 0)); //creating the top cut
        room.five_stitch(Player.UP, new Point(5, 0));
        room.branch(bottom, new Point(5, 10)); //creating the bottom cut
        room.five_stitch(Player.DOWN, new Point(5, 10));
        room.branch(left, new Point(0, 5)); //creating the left cut
        room.five_stitch(Player.LEFT, new Point(0, 5));
        room.branch(right, new Point(10, 5)); //creating the right cut
        room.five_stitch(Player.RIGHT, new Point(10, 5));
        room.rebase_origin();

        //------------------------------------------------𝓯𝓻𝓮𝓪𝓴𝔂 section--------------------------------------------------------------------------------


        //TODO



        return room;
    }

    public static Map one(){
        Map map = MapGenerator.generic_room(15, 15);
        map.setPlayer_spawn(new Point(10, 10));
        // int counterOfY =0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                var generic_room = MapGenerator.arkin_enemy_room(new Point(7, 7), new Point(10, 10), 15, 15);
                map.direct_branch(generic_room, new Point(j * 25, i * 25));
                var generic_hor_corridor = MapGenerator.standard_corridor(10, true, 3);
                map.direct_branch(generic_hor_corridor, new Point(15 + j * 25, i * 15 + 10));
                var generic_ver_corridor = MapGenerator.standard_corridor(10, false, 3);
                map.direct_branch(generic_ver_corridor, new Point(i * 15 + 10, j * 25 + 15));
                // var ghost = Monster.skeleton();
                // ghost.world_x = (5 + 25 * j) * GamePanel.TILE_SIZE;
                // ghost.world_y = 5 + i * 25 * GamePanel.TILE_SIZE;
                // map.getMonsters().add(ghost);
            }
        }
        var maze = MapGenerator.maze();
        // maze.rebase_y();
        // map.branch(maze, new Point(-20, 10));

        map.rebase_origin();

        map.getObjects().add(Object.coin(5 * GamePanel.TILE_SIZE, 8 * GamePanel.TILE_SIZE));
        var knight = Monster.knight();
        knight.world_x = 10 * GamePanel.TILE_SIZE;
        knight.world_y = 9 * GamePanel.TILE_SIZE;

        map.getMonsters().add(knight);

        map.rebase_origin();

        map = maze();

        map.setPlayer_spawn(new Point(6, 5));

        return map;
    }

    // public static Map two(){
    //     //TODO
    // }

    // public static Map three(){
    //     //TODO
    // }

    public static Map maze(){
        var map = Map.new_map();
        var tiles = map.getTiles();
        try {
            var scanner = new Scanner(new File("res/mazeTwo.txt"));
            int x = 0;
            int y = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] parts = line.split(" ");
                
                for (String part : parts) {
                    var num = Integer.parseInt(part);
                    var p = new Point(x, y);
                    if(num >= 3 && num != 12){
                        tiles.put(p, Tile.with_collision(num));
                    }
                    else {
                        tiles.put(p, new Tile(num));
                    }
                    x += 1;
                }
                x = 0;
                y += 1;
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO
        return map;
    }


    public static int gen_range(int max){
        return (int)(Math.random() * max);
    }
    public static int gen_range(int low, int high){
        return (int)(Math.random() * (high - low)) + low;
    }


    public static Map sample_map(){
        var map = Map.new_map();
        var monsters = map.getMonsters();
        monsters.add(Monster.ghost());
        monsters.add(Monster.turret());
        var othermonsta = Monster.skeleton();
        monsters.add(othermonsta);
        var boss = Monster.boss();
        monsters.add(boss);
        // var boss = Monster.knight();
        // monsters.add(boss);


        var objects = map.getObjects();
        var plate = Object.metal_plate(32*10, 32*10);
        var rock = Object.rock(32*5, 32*12);
        var plate_2 = Object.metal_plate(32*8, 32*6);
        var rock_2 = Object.rock(32*2, 32*15);
        var door = Object.door(GamePanel.TILE_SIZE * 4, GamePanel.TILE_SIZE);
        plate.minigame_affiliation = 1;
        // rock.minigame_affiliation = 1;
        plate_2.minigame_affiliation = 1;
        // rock_2.minigame_affiliation = 1;
        door.minigame_affiliation = 1;
        door.teleporter = Optional.of(new Point(10, 10));
        var key = Object.key(GamePanel.TILE_SIZE * 4, GamePanel.TILE_SIZE * 15);
        key.minigame_affiliation = 1;
        var toll = Object.toll(GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 5, 1);
        toll.minigame_affiliation = 1;
        var rock_o = Object.rock(GamePanel.TILE_SIZE * 3, GamePanel.TILE_SIZE * 11);
        rock_o.minigame_affiliation = 2;
        rock_o.painted_rock = Optional.of(false);
        var rock_x = Object.rock(GamePanel.TILE_SIZE * 3, GamePanel.TILE_SIZE * 13);
        rock_x.minigame_affiliation = 2;
        rock_x.painted_rock = Optional.of(true);
        objects.add(plate);
        objects.add(plate_2);
        objects.add(rock);
        objects.add(rock_2);
        objects.add(door);
        objects.add(key);
        objects.add(toll);
        objects.add(rock_o);
        objects.add(rock_x);
        var chimer = Object.chimer(GamePanel.TILE_SIZE * 20, GamePanel.TILE_SIZE * 10);
        chimer.minigame_affiliation = 2;
        var link = Object.wire(GamePanel.TILE_SIZE * 25, GamePanel.TILE_SIZE * 10, true);
        var link2 = Object.wire(GamePanel.TILE_SIZE * 30, GamePanel.TILE_SIZE * 10, false);
        link.movable = true;
        link.minigame_affiliation = 2;
        link2.movable = true;
        link2.minigame_affiliation = 2;
        objects.add(chimer);
        objects.add(link);
        objects.add(link2);
        return map;
    }

    // public void spawnskeletons(int x, int y) {
    //     var map = Map.new_map();
    //     var monsters = map.getMonsters();
    //     monsters.add(Monster.skeleton());
    // }
}
