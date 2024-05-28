public class GUIManager {

    ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
    Font font;
    private GamePanel gp;

    public GUIManager(GamePanel gp){
        this.gp = gp;
        InputStream = new File("res/fonts/some_font.ttf");
        

    }
    
    public void setupSprites(){
        try {

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2){
        if(gp.gameState==0){
       drawTitleScreen();
        }
        else if(gp.gameState==1){

        }   
             else if (gp.gameState==2){

        }
        else{

        }
        public void drawTitleScreen(){
            
        }
    }
    
    
    
}
