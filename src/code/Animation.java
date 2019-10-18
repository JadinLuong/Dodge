package code;

import java.awt.Image;
import java.util.ArrayList;

/**
 * 	Represents the Animation class that helps animate any sprite that is
 * 	required to be animated.
 */
public class Animation {
    
    ArrayList<Image> spriteImages;
    Player player;
    int currentFrame;
    
    public Animation(Sprite newPlayer, ArrayList<Image> newSpriteImages) {
	this.spriteImages = newSpriteImages;
	this.player = (Player) newPlayer;
	this.currentFrame = 0;
	player.setSprite(this.spriteImages.get(0));
    }
    
    public Image animate(int spriteFrame) {
	// Goes to the next frame of the sprite
	player.setSprite(this.spriteImages.get(currentFrame));
	// Recall that the spriteFrame works with the timer, each time it resets
	// to 0, we can get the next frame of the sprite
	if(spriteFrame == 0) {
	    currentFrame++;
	}
	// If the animation has reached the last frame, restart the animation
	if(currentFrame >= this.spriteImages.size()) {
	    this.currentFrame = 0;
	}
	return player.getSprite();
    }
    
    public void resetFrames() {
	this.currentFrame = 0;
    }
}
