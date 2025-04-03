import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class GUIManager {

    ArrayList<BufferedImage> sprites;
    private Font font;
    public GamePanel gp;
    public int commandNum = 0;
    public int gui_counter = 0;

    public GUIManager(GamePanel gp) {

        this.gp = gp;
        try {
            InputStream inputStream = new FileInputStream(new File("res/fonts/Jacquard12.ttf"));
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupSprites();
    }

    public void setupSprites() {
        sprites = new ArrayList<BufferedImage>();
        try {
            sprites.add(App.res("res/objects/awards/ultimate.png"));
            sprites.add(App.res("res/objects/coin.png"));
            sprites.add(App.res("res/gui/health/zero.png"));
            sprites.add(App.res("res/gui/health/one.png"));
            sprites.add(App.res("res/gui/health/two.png"));
            sprites.add(App.res("res/gui/health/three.png"));
            sprites.add(App.res("res/gui/health/four.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        g2.setFont(font);
        if (gp.gameState == GamePanel.DEATH) {
            deathScreen(g2);
        } else if (gp.gameState == GamePanel.END) {
            drawEndScreen(g2);
        }
        if (gp.gameState == GamePanel.TITLE) {
            drawTitleScreen(g2);
            // System.out.println("gagins");
        } else if (gp.gameState == GamePanel.PLAY) {
         
            drawGameUI(g2, gp.player);
            gui_counter = 0;
            // System.out.println("aggin");
        } else if (gp.gameState == GamePanel.PAUSE) {
            drawPauseScreen(g2);
            // System.out.println("Paused");
        } else if (gp.gameState == GamePanel.CLASS_SELECTION) {
            drawClassSelectionScreen(g2);
            // System.out.println("Up-Town Funk");
        }
    }

    /** Draws the UI for the game while it is currently in PLAY mode. */
    public void drawGameUI(Graphics2D g2d, Player entity) {
        // g2d.setColor(Color.red);
        // g2d.fillRect(0, 0, 32 * entity.health, 48);
        draw_health(g2d, entity.health, entity.maxHealth);

        g2d.setColor(Color.gray);
        g2d.fillRect(0, 48, 5 * 30, 16);
        g2d.setColor(Color.white);
        g2d.fillRect(0, 48, 5 * 30 * entity.special_counter/entity.special_counter_max, 16);

        g2d.drawImage(sprites.get(0), 10, 96, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
        g2d.setFont(g2d.getFont().deriveFont(32F));
        g2d.setColor(Color.white);
        g2d.drawString("x " + entity.trophe_count, 60, 96 + 3 * GamePanel.TILE_SIZE / 4);

        g2d.drawImage(sprites.get(1), 120, 96, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
        g2d.setFont(g2d.getFont().deriveFont(32F));
        g2d.setColor(Color.white);
        g2d.drawString("x " + entity.coin_count, 170, 96 + 3 * GamePanel.TILE_SIZE / 4);
    }
    
    // public static int determine_heart_sprite(Player player){
    //     int currentHealth = player.health;
    //     int maxHealth = player.maxHealth;
    //     int total_hearts = player.maxHealth/4;
    //     int pct_of_last_heart = currentHealth % 4;

    //     if(pct_of_last_heart >= 4){
    //         return 4;
    //     } else if(pct_of_last_heart >= 3){
    //         return 3;
    //     } else if(pct_of_last_heart >= 2){
    //         return 2;
    //     } else if(pct_of_last_heart >= 1){
    //         return 1;
    //     } else if(pct_of_last_heart <=.1){
    //         return 0;
    //     }
    // }

    public void draw_health(Graphics2D g2d, int health, int maxHealth){
        //amt can be 8 -> 2 full hearts 10 -> 
        int numOfHearts = maxHealth/4;
        int remainderOfHearts = health%4;
        int heartsRemain = (health-remainderOfHearts)/4;
        for(var i = 0; i < numOfHearts; i++){
            if(heartsRemain>0){
                heartsRemain--;   
                g2d.drawImage(sprites.get(6), 10 + i * GamePanel.TILE_SIZE, 10, GamePanel.TILE_SIZE / 2, GamePanel.TILE_SIZE / 2, null);
            }
            else if(remainderOfHearts>0){
                g2d.drawImage(sprites.get(remainderOfHearts + 2), 10 + i * GamePanel.TILE_SIZE, 10, GamePanel.TILE_SIZE / 2, GamePanel.TILE_SIZE / 2, null);
                remainderOfHearts = 0;
            }
            else{
                g2d.drawImage(sprites.get(2), 10 + i * GamePanel.TILE_SIZE, 10, GamePanel.TILE_SIZE / 2, GamePanel.TILE_SIZE / 2, null);
            }
        
        }
    }

    public void drawPauseScreen(Graphics2D g2d) {
        String text = "PAUSED";
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 96F));
        int x = getXforCT(text, g2d);
        int y = GamePanel.screenHeight / 2;
        g2d.setColor(Color.white);
        g2d.drawString(text, x, y);
    }

    public void drawClassSelectionScreen(Graphics2D g2d) {
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Class Selection";
        g2d.setColor(Color.PINK);
        int x = getXforCT(text, g2d);
        int y = GamePanel.TILE_SIZE * 3;

        g2d.drawString(text, x + 10, y + 10);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 48F));
        text = "Wizard";
        y += GamePanel.TILE_SIZE * 4;
        g2d.drawString(text, x, y);
        if (commandNum == 0) {
            g2d.drawString("->", x - 50, y);
        }
        text = "Knight";
        y += GamePanel.TILE_SIZE;
        g2d.drawString(text, x, y);

        if (commandNum == 1) {
            g2d.drawString("->", x - 50, y);
        }
        text = "Archer";
        y += GamePanel.TILE_SIZE;
        g2d.drawString(text, x, y);

        if (commandNum == 2) {
            g2d.drawString("->", x - 50, y);
        }
        text = "Healer";
        y += GamePanel.TILE_SIZE;
        g2d.drawString(text, x, y);

        if (commandNum == 3) {
            g2d.drawString("->", x - 50, y);
        }
    }

    public void drawEndScreen(Graphics2D g2d) {
        if (gui_counter < 120) {
            gui_counter += 1;
            transition(g2d);
            return;
        }   
        
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 192F));
        String text = "Chaotic";
        g2d.setColor(Color.BLUE);
        int x = getXforCT(text, g2d);
        int y = GamePanel.TILE_SIZE * 5;
        g2d.drawString(text, x + 10, y + 10);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);

    }

    public void transition(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (1f - gui_counter / 120f)));
        g2d.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void deathScreen(Graphics2D g2d) {
        if (gui_counter < 120) {
            gui_counter += 1;
            transition(g2d);
            return;
        }

        g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 96F));
        String text = "Death Unto Soul";

        int x = getXforCT(text, g2d);
        int y = GamePanel.TILE_SIZE * 5;
        g2d.drawString(text, x, y);
        g2d.setColor(Color.GRAY);
        g2d.drawString(text, x + 10, y + 10);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 48F));
        g2d.setColor(Color.GREEN);
        text = "Thou Shalt Resuscitate";
        y += GamePanel.TILE_SIZE * 4;
        g2d.drawString(text, x, y);

        if (commandNum == 0) {
            // g2d.setColor(Color.WHITE);
            g2d.drawString("->", x - 50, y);
        }
        g2d.setColor(Color.RED);
        text = "Embrace Darkness";
        y += GamePanel.TILE_SIZE;
        g2d.drawString(text, x, y);

        if (commandNum == 1) {
            // g2d.setColor(Color.WHITE);
            g2d.drawString("->", x - 50, y);
        }
    }

    public void drawTitleScreen(Graphics2D g2d) {
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Chaotic";
        g2d.setColor(Color.BLUE);
        int x = getXforCT(text, g2d);
        int y = GamePanel.TILE_SIZE * 3;

        g2d.drawString(text, x + 10, y + 10);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 48F));
        text = "New Game";
        y += GamePanel.TILE_SIZE * 4;
        g2d.drawString(text, x, y);

        if (commandNum == 0) {
            g2d.drawString("->", x - 50, y);
        }

        text = "Quit Game";
        y += GamePanel.TILE_SIZE;
        g2d.drawString(text, x, y);

        if (commandNum == 1) {
            g2d.drawString("->", x - 50, y);
        }
    }

    private int getXforCT(String text, Graphics2D g2d) {
        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = (GamePanel.screenWidth / 2) - length / 2;
        return x;
    }

}
