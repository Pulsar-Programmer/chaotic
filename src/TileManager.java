import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.awt.Graphics2D;
import java.awt.Point;

public class TileManager {
    GamePanel gp;
    BufferedImage[] tile_sprites;
    ArrayList<Tile> tiles;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tiles = new ArrayList<Tile>();
        getTileImage();
        loadMap();
        // tiles.get(2).has_collision = true;
        // tiles.get(15).has_collision = true;
    }
    public void draw(Graphics2D g2d){
        for(var elem : tiles){
            int world_x = elem.x;
            int world_y = elem.y;
            int x = world_x - gp.player.world_x + gp.player.screen_x;
            int y = world_y - gp.player.world_y + gp.player.screen_y;
            if(world_x + GamePanel.TILE_SIZE > gp.player.world_x - gp.player.screen_x &&
                world_x - GamePanel.TILE_SIZE < gp.player.world_x + gp.player.screen_x &&
                world_y + GamePanel.TILE_SIZE > gp.player.world_y - gp.player.screen_y &&
                world_y - GamePanel.TILE_SIZE < gp.player.world_y + gp.player.screen_y
            ){
                elem.draw(g2d, GamePanel.TILE_SIZE, tile_sprites, x, y);
            }
        }
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
                    if(num >= 2 && num != 11){
                        tiles.add(Tile.with_collision(num, GamePanel.TILE_SIZE * x, GamePanel.TILE_SIZE * y));
                    } else if(num==11){
                        var tile = new Tile(num, GamePanel.TILE_SIZE * x, GamePanel.TILE_SIZE * y);
                        // System.out.println("Setting!!");
                        tile.teleporter = Optional.of(new Point(10, 10));
                        // System.out.println(tile.teleporter);
                        tiles.add(tile);
                    }
                    else {
                        tiles.add(new Tile(num, GamePanel.TILE_SIZE * x, GamePanel.TILE_SIZE * y));
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
        for (Tile elem : tiles) {
            if(elem.x/GamePanel.TILE_SIZE == x && elem.y/GamePanel.TILE_SIZE == y) {
                return elem;
            }
        }
        return new Tile(0, 0, 0);
    }
}
