import java.io.File;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.awt.Graphics2D;
import java.awt.Point;

public class TileManager {
    BufferedImage[] tile_sprites;
    HashMap<Point, Tile> tiles;

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
        tile_sprites = new BufferedImage[12]; //variable to change depending on number tile sprites added.
        try {
            tile_sprites[0] = ImageIO.read(new File("res/tiles/blue/bricks.png"));
            tile_sprites[1] = ImageIO.read(new File("res/tiles/blue/paver.png"));
            tile_sprites[2] = ImageIO.read(new File("res/tiles/blue/wall/west.png"));
            tile_sprites[3] = ImageIO.read(new File("res/tiles/blue/wall/east.png"));
            tile_sprites[4] = ImageIO.read(new File("res/tiles/blue/wall/south.png"));
            tile_sprites[5] = ImageIO.read(new File("res/tiles/blue/wall/north.png"));
            tile_sprites[6] = ImageIO.read(new File("res/tiles/blue/wall/southeast.png"));
            tile_sprites[7] = ImageIO.read(new File("res/tiles/blue/wall/southwest.png"));
            tile_sprites[8] = ImageIO.read(new File("res/tiles/blue/wall/northeast.png"));
            tile_sprites[9] = ImageIO.read(new File("res/tiles/blue/wall/northwest.png"));
            tile_sprites[10] = ImageIO.read(new File("res/tiles/blue/door/north_closed.png"));
            tile_sprites[11] = ImageIO.read(new File("res/tiles/blue/door/north_open.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadMap(){
        try {
            var scanner = new Scanner(new File("res/arkinmap.txt"));
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
                    if(num >= 2 && num != 11){
                        tiles.put(p, Tile.with_collision(num));
                    } else if(num==11){
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
    public void generate(){
        // tiles = MapGenerator.procedure().getTiles();
        tiles = MapGenerator.puzzle_room(20, 20).getTiles();
    }
}
