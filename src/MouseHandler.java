import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {

    public boolean leftClicked, rightClicked, middleClicked;
    public boolean leftDragged, rightDragged, middleDragged;
    public boolean leftPressed, rightPressed, middlePressed;
    public int mouseX, mouseY; // Stores the last known mouse position

    public MouseHandler() {}

    @Override
    public void mouseClicked(MouseEvent e) {
        int button = e.getButton();
        if (button == MouseEvent.BUTTON1) { // Left click
            leftClicked = true;
        } else if (button == MouseEvent.BUTTON3) { // Right click
            rightClicked = true;
        } else if (button == MouseEvent.BUTTON2) { // Middle click
            middleClicked = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int button = e.getButton();
        if (button == MouseEvent.BUTTON1) { // Left click
            leftPressed = true;
            mouseX = e.getX();
            mouseY = e.getY();
        } else if (button == MouseEvent.BUTTON3) { // Right click
            rightPressed = true;
        } else if (button == MouseEvent.BUTTON2) { // Middle click
            middlePressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        if (button == MouseEvent.BUTTON1) { // Left click
            leftPressed = false;
        } else if (button == MouseEvent.BUTTON3) { // Right click
            rightPressed = false;
        } else if (button == MouseEvent.BUTTON2) { // Middle click
            middlePressed = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int button = e.getButton();
        if (button == MouseEvent.BUTTON1) { // Left drag
            leftDragged = true;
        } else if (button == MouseEvent.BUTTON3) { // Right drag
            rightDragged = true;
        } else if (button == MouseEvent.BUTTON2) { // Middle drag
            middleDragged = true;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
