import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Optional;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int maxScreenCol = 16;
    public static final int maxScreenRow = 12;
    public static final int screenWidth = TILE_SIZE * maxScreenCol;
    public static final int screenHeight = TILE_SIZE * maxScreenRow;
    
    public final static int FPS = 60;

    TileManager tileManager = new TileManager();
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this);
    CollisionChecker collisionChecker = new CollisionChecker(this);
    GUIManager guiManager = new GUIManager(this);
    ObjectManager objectManager = new ObjectManager();
    MonsterManager monsterManager = new MonsterManager();
    ProjectileManager projectileManager = new ProjectileManager();
    Map[] maps = {Map.new_map(), Map.new_map(), Map.new_map()};
    Sound sounds = new Sound();
    public int gameState = TITLE;
    public final static int TITLE = -1;
    public final static int PLAY = 0;
    public final static int PAUSE = 1;
    public final static int CLASS_SELECTION = -2;
    public final static int DEATH = 2;
    public final static int END = 3;

    public int mapNum = 0;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.black);
        setDoubleBuffered(true);
        addKeyListener(keyH);
        setFocusable(true);
    }

    public void startGameThread() {
        sounds.clear();
        sounds.addFile(12);
        sounds.loop();
        setupGame();
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setupGame(){
        // tiles = MapGenerator.procedure().getTiles();
        var door = Object.door(0, 0);
        door.teleporter = Optional.of(new Point(10, 10));
        var map = MapGenerator.generate(player);
        map.getMonsters().add(Monster.ghost(player));
        assert map.getMonsters() != null;
        // var map = MapGenerator.random_puzzle_room(1, door);
        // var map = MapGenerator.key_puzzle(1);
        // var map = MapGenerator.sample_map();
        map.rebase_origin();
        // map.setTiles(tileManager.tiles);

        
        // map.layer(MapGenerator.boss_room(10, 10, 0));
        // map.branch(MapGenerator.standard_corridor(20, true, 3), new Point(10, 5)); 
        // map = MapGenerator.generic_room(10, 10);
        map.setPlayer_spawn(new Point(5, 5));
        maps[0] = map;
        load_map(0);
    }

    public void load_map(int map){
        Map tempMap = maps[map];
        tileManager.tiles = tempMap.getTiles();
        objectManager.objects = tempMap.getObjects();
        monsterManager.monsters = tempMap.getMonsters();
        player.world_x = tempMap.getPlayer_spawn().x * GamePanel.TILE_SIZE;
        player.world_y = tempMap.getPlayer_spawn().y * GamePanel.TILE_SIZE;
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
        // sounds.sweep();

        if(gameState == PLAY){
            player.update();
            monsterManager.update(this);
            projectileManager.update(this);
            objectManager.update(this);
        }
        else if(gameState == PAUSE){
            
        } else if (gameState == END){
        
        }
        else if(gameState==DEATH){
            
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
                    gameState = PLAY;
                    
                    if(player.class_type == Player.WIZARD){
                        player = Player.wizard(this);
                    } else if(player.class_type == Player.KNIGHT){
                        player = Player.knight(this);
                    } else if(player.class_type == Player.ARCHER){
                        player = Player.archer(this);
                    } else if(player.class_type == Player.HEALER){
                        player = Player.healer(this);
                    }
                    setupGame();
                    sounds.clear();
                    sounds.addFile(1);
                    sounds.loop();          
                }
                else if(guiManager.commandNum==1){
                    System.exit(0);
                }
                keyH.startHit = false;
            }
        }
        else if(gameState == TITLE){
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
                    player = Player.wizard(this);
                    player.world_x = maps[mapNum].getPlayer_spawn().x * GamePanel.TILE_SIZE;
                    player.world_y = maps[mapNum].getPlayer_spawn().y * GamePanel.TILE_SIZE;
                }
                if(guiManager.commandNum==1){
                    gameState = PLAY;
                    guiManager.commandNum=0;
                    player = Player.knight(this);
                    player.world_x = maps[mapNum].getPlayer_spawn().x * GamePanel.TILE_SIZE;
                    player.world_y = maps[mapNum].getPlayer_spawn().y * GamePanel.TILE_SIZE;
                }
                if(guiManager.commandNum==2){
                    gameState = PLAY;
                    guiManager.commandNum=0;
                    player = Player.archer(this);
                    player.world_x = maps[mapNum].getPlayer_spawn().x * GamePanel.TILE_SIZE;
                    player.world_y = maps[mapNum].getPlayer_spawn().y * GamePanel.TILE_SIZE;
                }
                if(guiManager.commandNum==3){
                    gameState = PLAY;
                    guiManager.commandNum=0;
                    player = Player.healer(this);
                    player.world_x = maps[mapNum].getPlayer_spawn().x * GamePanel.TILE_SIZE;
                    player.world_y = maps[mapNum].getPlayer_spawn().y * GamePanel.TILE_SIZE;
                }
                
                keyH.startHit = false;
                sounds.clear();
                sounds.addFile(1);
                sounds.loop();
            }
        }

        if(keyH.pauseHit){
            if(gameState == PAUSE){
                sounds.clear();
                sounds.addFile(1);
                sounds.loop();
            }
            if(gameState == PLAY){
                sounds.clear();
                sounds.addFile(15);
                sounds.loop();
            }
            gameState = (gameState + 1) % 2;
            keyH.pauseHit = false;
        }

       

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if(gameState == PLAY){
            tileManager.draw(g2, player);
            objectManager.draw(g2, this);
            projectileManager.draw(g2, this);
            monsterManager.draw(g2, this);
            player.draw(g2);
        }
        else if(gameState == PAUSE){
            // guiManager.drawPauseScreen(g2);
        }
        else if(gameState == TITLE){
            // guiManager.drawTitleScreen(g2);
        }
        guiManager.draw(g2);
        g2.dispose();
    }

    public void screen_draw(BufferedImage sprite, int world_x, int world_y, Graphics2D g2d){
        int screen_x = world_x - player.world_x + player.screen_x;
        int screen_y = world_y - player.world_y + player.screen_y;
        
        if(world_x + GamePanel.TILE_SIZE > player.world_x - player.screen_x &&
            world_x - GamePanel.TILE_SIZE < player.world_x + player.screen_x &&
            world_y + GamePanel.TILE_SIZE > player.world_y - player.screen_y &&
            world_y - GamePanel.TILE_SIZE < player.world_y + player.screen_y
        ){
            g2d.drawImage(sprite, screen_x, screen_y, sprite.getWidth() * SCALE, sprite.getHeight() * SCALE, null);
        }
    }

}
