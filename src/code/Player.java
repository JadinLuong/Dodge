package code;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

/**
 * 	Represents a player
 */
public class Player extends Sprite{
    // Initial position of the player
    public static final int INITIAL_X_POS = 225;
    public static final int INITIAL_Y_POS = 475;
    
    // Checks if the player is facing right or moving
    // Used for animation purposes
    private boolean isFacingRight;
    private boolean isMoving;
    
    
    public Player() {
	super(null, INITIAL_X_POS, INITIAL_Y_POS);
	this.setSprite((new ImageIcon(this.getClass().
		getResource("/standleft0.png"))).getImage());
	this.isFacingRight = false;
	this.isMoving = false;
    }
    
    public void setPlayerFacingRight(boolean playerFacingRight) {
	this.isFacingRight = playerFacingRight;
    }
    
    public boolean isFacingRight() {
	return this.isFacingRight;
    }
    
    public boolean isMoving() {
	return this.isMoving;
    }
    
    // Represents the movement of the player
    public void keyPressed(KeyEvent e) {
	int key = e.getKeyCode();
	if(key == KeyEvent.VK_LEFT) {
	    isMoving = true;
	    isFacingRight = false;
	    this.setXSpeed(-5);
	}
	if(key == KeyEvent.VK_RIGHT) {
	    isMoving = true;
	    isFacingRight = true;
	    this.setXSpeed(5);
	}
    }
    
    public void keyReleased(KeyEvent e) {
	int key = e.getKeyCode();
	if(key == KeyEvent.VK_LEFT) {
	    isMoving = false;
	    this.setXSpeed(0);
	}
	if(key == KeyEvent.VK_RIGHT) {
	    isMoving = false;
	    this.setXSpeed(0);
	}
    }
}
