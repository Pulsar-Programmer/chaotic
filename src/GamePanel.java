import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    static final int originalTileSize = 16;
    static final int scale = 3;

    public static final int TILE_SIZE = originalTileSize * scale;
    public static final int maxScreenCol = 16;
    public static final int maxScreenRow = 12;
    public static final int screenWidth = TILE_SIZE * maxScreenCol;
    public static final int screenHeight = TILE_SIZE * maxScreenRow;

    final static int FPS = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);
    CollisionChecker collisionChecker = new CollisionChecker(this);
    GUIManager guiManager = new GUIManager(this);
    ObjectManager objectManager = new ObjectManager();
    AssetSetter assetSetter = new AssetSetter(this);

    public int gameState = TITLE;
    public final static int TITLE = -1;
    public final static int PLAY = 0;
    public final static int PAUSE = 1;
    public final static int CLASS_SELECTION = -2;


    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.black);
        setDoubleBuffered(true);
        addKeyListener(keyH);
        setFocusable(true);
    }

    public void startGameThread() {
        setupGame();
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setupGame(){
        assetSetter.setObject();
    }

    @Override
    public void run() {

        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta -= 1;
            }
        } 
    }

    public void update(){

        if(gameState == PLAY){
            player.update();
        }
        else if(gameState == PAUSE){
            
        } else if(gameState == TITLE){
            if(keyH.upHit){
                guiManager.commandNum = (guiManager.commandNum + 1) % 2;
                keyH.upHit = false;
            }
            if(keyH.downHit){
                guiManager.commandNum = (guiManager.commandNum + 1) % 2;
                keyH.downHit = false;
            }
            if(keyH.startHit){
                if(guiManager.commandNum==0){
                    gameState = CLASS_SELECTION;
                    guiManager.commandNum=0;
                }
                else if(guiManager.commandNum==1){
                    System.exit(0);
                }
                keyH.startHit = false;
            }
        }
        else if(gameState == CLASS_SELECTION){
            if(keyH.upHit){
                guiManager.commandNum = (guiManager.commandNum + 3) % 4;
                keyH.upHit = false;
            }
            if(keyH.downHit){
                guiManager.commandNum = (guiManager.commandNum + 1) % 4;
                keyH.downHit = false;
            }
            if(keyH.startHit){
                if(guiManager.commandNum==0){
                    gameState = PLAY;
                    guiManager.commandNum=0;
                }
                  if(guiManager.commandNum==1){
                    gameState = PLAY;
                    guiManager.commandNum=0;
                }
                if(guiManager.commandNum==3){
                    gameState = PLAY;
                    guiManager.commandNum=4;
                }

                else if(guiManager.commandNum==1){
                    System.exit(0);
                }
                keyH.startHit = false;
            }
        }

        if((gameState == PAUSE || gameState == PLAY) && keyH.pauseHit){
            gameState = (gameState + 1) % 2;
            System.out.println("State!");
            keyH.pauseHit = false;
        }


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        guiManager.draw(g2);

        if(gameState == PLAY){
            tileManager.draw(g2);
            objectManager.draw(g2, this);
            player.draw(g2);
        }
        else if(gameState == PAUSE){
            // guiManager.drawPauseScreen(g2);
        }
        else if(gameState == TITLE){
            // guiManager.drawTitleScreen(g2);
        }
        g2.dispose();
    }

}
