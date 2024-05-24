import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.awt.Graphics2D;

public class TileManager {
    GamePanel gp;
    BufferedImage[] tile_sprites;
    ArrayList<Tile> tiles;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tiles = new ArrayList<Tile>();
        getTileImage();
        loadMap();
    }
    public void draw(Graphics2D g2d){
        for(var elem : tiles){
            elem.draw(g2d, gp.tileSize, tile_sprites);
        }
    }
    public void getTileImage(){
        tile_sprites = new BufferedImage[5];//variable to change depending on number tile sprites added.
        try {
            tile_sprites[2] = ImageIO.read(new File("res/tiles/Black.png"));
            tile_sprites[1] = ImageIO.read(new File("res/tiles/blue_bricks.jpg"));
            tile_sprites[0] = ImageIO.read(new File("res/tiles/brown_bricks.jpg"));
            tile_sprites[3] = ImageIO.read(new File("res/tiles/GrassWF.png"));
            tile_sprites[4] = ImageIO.read(new File("res/tiles/Light.png"));
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
        
                for (String part : parts) {
                    var num = Integer.parseInt(part);
                    tiles.add(new Tile(num, gp.tileSize * x, gp.tileSize * y));
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
}
