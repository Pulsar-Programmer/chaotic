import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Graphics2D;

public class TileManager {
    GamePanel gp;
    ArrayList<Tile> tiles;

    public TileManager(GamePanel gp){
        this.gp = gp;
        loadMap();
    }
    public void draw(Graphics2D g2d){
        for(var elem : tiles){
            elem.draw(g2d, gp.tileSize);
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
                    tiles.add(new Tile("res/tiles/" + num + ".png", gp.tileSize * x, gp.tileSize * y));
                    x += 1;
                }
                y += 1;
            }

            scanner.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
