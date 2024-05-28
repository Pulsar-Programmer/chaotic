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
    private GamePanel gp;
    public int commandNum=0;
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
            
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2){

        g2.setFont(font);
        
        if(gp.gameState == GamePanel.TITLE){
            drawTitleScreen(g2);
            System.out.println("gagins");
           
        }
        else if(gp.gameState == GamePanel.PLAY){
            System.out.println("aggin");
        }   
        else if (gp.gameState == GamePanel.PAUSE){
            drawPauseScreen(g2);
            System.out.println("Paused");
        }
        else if(gp.gameState==GamePanel.CLASS_SELECTION){
            drawClassSelectionScreen(g2);
            System.out.println("Up-Town Funk");
            
        }
    }
    
    public void drawPauseScreen(Graphics2D g2d){
        String text = "PAUSED";
        int x = getXforCT(text, g2d);
        int y = GamePanel.screenHeight / 2;
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 96F));
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
        y +=GamePanel.TILE_SIZE;
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
        y +=GamePanel.TILE_SIZE;
        g2d.drawString(text,x,y);
        
        if(commandNum==3){
            g2d.drawString("->",x-50,y);
        }
    }

    public void drawTitleScreen(Graphics2D g2d){
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Chaotic";
          g2d.setColor(Color.BLUE);
        int x = getXforCT(text, g2d);
        int y = GamePanel.TILE_SIZE*3;
        
        g2d.drawString(text, x+10,y+10);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text,x,y);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 48F));
        text = "New Game";
        y +=GamePanel.TILE_SIZE*4;
        g2d.drawString(text,x,y);
        if(commandNum==0){
            g2d.drawString("->",x-50,y);
        }

        text = "Give Up";
        y +=GamePanel.TILE_SIZE;
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
