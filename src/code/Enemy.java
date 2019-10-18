package code;

import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;

/**
 * 	Represents an enemy
 */
public class Enemy extends Sprite{
    
    public Enemy(int initialX, int initialY) {
	super(null, initialX, initialY);
	this.setSprite((new ImageIcon(this.getClass().
		getResource("/enemy_sprite.png"))).getImage());
	// initializes a random speed for the enemy
	this.setYSpeed(ThreadLocalRandom.current().nextInt(4, 10));
    }
    
    
}
