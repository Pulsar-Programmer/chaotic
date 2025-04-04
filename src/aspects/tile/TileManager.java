package aspects.tile;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

import main.App;
import main.GamePanel;
import main.Player;

public class TileManager {
    public ArrayList<BufferedImage> tile_sprites;
    public HashMap<Point, Tile> tiles;

    public TileManager(){
        tiles = new HashMap<Point, Tile>();
        getTileImage();
        loadMap();
    }
    public void draw(Graphics2D g2d, Player player){
        tiles.forEach((Point p, Tile elem) -> {
            int world_x = p.x * GamePanel.TILE_SIZE;
            int world_y = p.y * GamePanel.TILE_SIZE;
            
            int x = world_x - player.world_x + player.screen_x;
            int y = world_y - player.world_y + player.screen_y;
            if(world_x + GamePanel.TILE_SIZE > player.world_x - player.screen_x &&
                world_x - GamePanel.TILE_SIZE < player.world_x + player.screen_x &&
                world_y + GamePanel.TILE_SIZE > player.world_y - player.screen_y &&
                world_y - GamePanel.TILE_SIZE < player.world_y + player.screen_y
            ){
                elem.draw(g2d, GamePanel.TILE_SIZE, tile_sprites, x, y);
            }
        });
    }
    public void getTileImage(){
        tile_sprites = new ArrayList<BufferedImage>(); //variable to change depending on number tile sprites added.
        try {
            tile_sprites.add(App.res("res/tiles/blue/bricks.png")); //0
            tile_sprites.add(App.res("res/tiles/blue/paver.png")); //1
            tile_sprites.add(App.res("res/tiles/blue/mossy_bricks.png")); //2
            tile_sprites.add(App.res("res/tiles/blue/wall/west.png")); //3
            tile_sprites.add(App.res("res/tiles/blue/wall/east.png")); //4
            tile_sprites.add(App.res("res/tiles/blue/wall/south.png")); //5
            tile_sprites.add(App.res("res/tiles/blue/wall/north.png")); //6
            tile_sprites.add(App.res("res/tiles/blue/wall/southeast.png")); //7
            tile_sprites.add(App.res("res/tiles/blue/wall/southwest.png")); //8
            tile_sprites.add(App.res("res/tiles/blue/wall/northeast.png")); //9
            tile_sprites.add(App.res("res/tiles/blue/wall/northwest.png")); //10
            tile_sprites.add(App.res("res/tiles/blue/door/north_closed.png")); //11
            tile_sprites.add(App.res("res/tiles/blue/door/north_open.png")); //12
            tile_sprites.add(App.res("res/tiles/blue/wall/southeast_inner.png")); //13
            tile_sprites.add(App.res("res/tiles/blue/wall/southwest_inner.png")); //14
            tile_sprites.add(App.res("res/tiles/blue/wall/northeast_inner.png")); //15
            tile_sprites.add(App.res("res/tiles/blue/wall/northwest_inner.png")); //16
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadMap(){
        try {
            var scanner = new Scanner(new File("res/map.txt"));
            int x = 0;
            int y = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] parts = line.split(" ");
                var xx = 0;
                for (String part : parts) {
                    if(part.isEmpty()){
                        xx += 1;
                        xx %= 2;
                        if(xx == 1){
                            x += 1;
                        }
                        continue;
                    }
                    var num = Integer.parseInt(part);
                    var p = new Point(x, y);
                    if(num >= 3 && num != 12){
                        tiles.put(p, Tile.with_collision(num));
                    } else if(num==12){
                        var tile = new Tile(num);

                        tile.teleporter = Optional.of(new Point(10, 10));

                        tiles.put(p, tile);
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
    }
    public Tile find(int x, int y){
        var val = tiles.get(new Point(x, y));
        if(val == null){
            return new Tile(0);
        }
        return val;
    }
}
