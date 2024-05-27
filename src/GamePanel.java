import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    final static int FPS = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);
    CollisionChecker collisionChecker = new CollisionChecker(this);
    GUIManager guiManager = new GUIManager();
    ObjectManager objectManager = new ObjectManager();
    AssetSetter assetSetter = new AssetSetter(this);

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
        player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tileManager.draw(g2);
        objectManager.draw(g2, this);
        player.draw(g2);
        g2.dispose();
    }
}
