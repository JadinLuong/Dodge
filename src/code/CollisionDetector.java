package code;

import java.awt.Rectangle;

/**
 * 	Helper class to help detect collision between two sprites
 */
public class CollisionDetector {
    
    public static boolean spriteCollision(Sprite firstSprite, Sprite secondSprite) {
	Rectangle firstSpriteRect = firstSprite.getBounds();
	Rectangle secondSpriteRect = secondSprite.getBounds();
	return firstSpriteRect.intersects(secondSpriteRect);
    }
}
