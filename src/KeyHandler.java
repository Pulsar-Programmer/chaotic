import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean pausePressed, attackPressed, specialPressed;
    
    public boolean upHit, downHit;
    public boolean pauseHit, startHit, attackHit;
    

    public KeyHandler() {}

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upHit = true;
            upPressed = true;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downHit = true;
            downPressed = true;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P || code == KeyEvent.VK_ESCAPE){
            if(!pausePressed){
                pauseHit = true;
            }
            pausePressed = true;
        }
        if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE){
            startHit = true;
        }
        if(code == KeyEvent.VK_Q){
            if(!attackPressed){
                attackHit = true;
            }
            attackPressed = true;
        }
        if(code == KeyEvent.VK_E){
            specialPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_P){
            pausePressed = false;
        }
        if(code == KeyEvent.VK_Q){
            attackPressed = false;
        }
        if(code == KeyEvent.VK_E){
            specialPressed = false;
        }
    }

}