import java.util.ArrayList;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.awt.Color;

public class GUIManager {

    ArrayList<BufferedImage> sprites;
    private Font font;
    public GamePanel gp;
    public int commandNum = 0;

    public GUIManager(GamePanel gp){
        
        this.gp = gp;
        try {
            InputStream inputStream = new FileInputStream(new File("res/fonts/Jacquard12.ttf"));
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupSprites();
    }
    
    public void setupSprites(){
        sprites = new ArrayList<BufferedImage>();
        try {
            // sprites.add(ImageIO.read(new File("res/gui/health/zero.png")));
            // sprites.add(ImageIO.read(new File("res/gui/health/one.png")));
            // sprites.add(ImageIO.read(new File("res/gui/health/two.png")));
            // sprites.add(ImageIO.read(new File("res/gui/health/three.png")));
            // sprites.add(ImageIO.read(new File("res/gui/health/four.png")));
            // sprites.add(ImageIO.read(new File("res/gui/health/five.png")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2){

        g2.setFont(font);
        if(gp.gameState==GamePanel.DEATH){
            deathScreen(g2);
        }
        else if(gp.gameState==GamePanel.END){
            drawEndScreen(g2);
        }
        if(gp.gameState == GamePanel.TITLE){
            drawTitleScreen(g2);
            // System.out.println("gagins");
        }
        else if(gp.gameState == GamePanel.PLAY){
            drawGameUI(g2, gp.player);
            // System.out.println("aggin");
        }   
        else if (gp.gameState == GamePanel.PAUSE){
            drawPauseScreen(g2);
            // System.out.println("Paused");
        }
        else if(gp.gameState==GamePanel.CLASS_SELECTION){
            drawClassSelectionScreen(g2);
            // System.out.println("Up-Town Funk");
        }
    }

    public void drawGameUI(Graphics2D g2d, Player entity){
        g2d.setColor(Color.red);
        g2d.fillRect(0, 0, 32 * entity.health, 48);
    }
    
    public void drawPauseScreen(Graphics2D g2d){
        String text = "PAUSED";
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 96F));
        int x = getXforCT(text, g2d);
        int y = GamePanel.screenHeight / 2;
        g2d.setColor(Color.white);
        g2d.drawString(text, x, y);
    }


    public void drawClassSelectionScreen(Graphics2D g2d){
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Class Selection";
        g2d.setColor(Color.PINK);
        int x = getXforCT(text, g2d);
        int y = GamePanel.TILE_SIZE*3;
        
        g2d.drawString(text, x+10,y+10);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text,x,y);

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 48F));
        text = "Necromancer";
        y += GamePanel.TILE_SIZE*4;
        g2d.drawString(text,x,y);
        if(commandNum==0){
            g2d.drawString("->",x-50,y);
        }
        text = "Knight";
        y += GamePanel.TILE_SIZE;
        g2d.drawString(text,x,y);
        
        if(commandNum==1){
            g2d.drawString("->",x-50,y);
        }
        text = "Archer";
        y += GamePanel.TILE_SIZE;
        g2d.drawString(text,x,y);

        if(commandNum==2){
            g2d.drawString("->",x-50,y);
        }
        text = "Human";
        y += GamePanel.TILE_SIZE;
        g2d.drawString(text,x,y);
        
        if(commandNum==3){
            g2d.drawString("->",x-50,y);
        }
    }
public void drawEndScreen(Graphics2D g2d){
    g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 192F));
    String text = "Chaotic";
      g2d.setColor(Color.BLUE);
    int x = getXforCT(text, g2d);
    int y = GamePanel.TILE_SIZE * 5;
    g2d.drawString(text, x+10,y+10);
    g2d.setColor(Color.WHITE);
    g2d.drawString(text,x,y);
    

    
}
public void deathScreen(Graphics2D g2d){
    g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 96F));
    String text = "Death Unto Soul.";
      g2d.setColor(Color.BLUE);
    int x = getXforCT(text, g2d);
    int y = GamePanel.TILE_SIZE * 5;
    g2d.drawString(text, x+10,y+10);
    g2d.setColor(Color.WHITE);
    g2d.drawString(text,x,y);
    g2d.drawString(text, x+10,y+10);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text,x,y);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 48F));
        text = "A Voice Calls Out...";
        y += GamePanel.TILE_SIZE*4;
        g2d.drawString(text,x,y);

        if(commandNum==0){
            g2d.drawString("->",x-50,y);
        }

        text = "Give Up.";
        y += GamePanel.TILE_SIZE;
        g2d.drawString(text,x,y);

        if(commandNum==1){
            g2d.drawString("->",x-50,y);
        }
    }
    

    public void drawTitleScreen(Graphics2D g2d){
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Chaotic";
          g2d.setColor(Color.BLUE);
        int x = getXforCT(text, g2d);
        int y = GamePanel.TILE_SIZE * 3;
        
        g2d.drawString(text, x+10,y+10);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text,x,y);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 48F));
        text = "New Game";
        y += GamePanel.TILE_SIZE*4;
        g2d.drawString(text,x,y);

        if(commandNum==0){
            g2d.drawString("->",x-50,y);
        }

        text = "Give Up";
        y += GamePanel.TILE_SIZE;
        g2d.drawString(text,x,y);

        if(commandNum==1){
            g2d.drawString("->",x-50,y);
        }
    }
    
    private int getXforCT(String text, Graphics2D g2d){
        int length = (int)g2d.getFontMetrics().getStringBounds(text,g2d).getWidth();
        int x = (GamePanel.screenWidth/2)-length/2;
        return x; 
    }
    
}
