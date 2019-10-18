package code;

import java.awt.Image;
import java.awt.Rectangle;

/**
 * 	Represents a sprite
 */
public class Sprite {
    
    private int x;
    private int y;
    private int xspeed;
    private int yspeed;
    private Image spriteImage;
    
    
    public Sprite(Image initialImage, int xPos, int yPos) {
	this.xspeed = 0;
	this.yspeed = 0;
	this.x = xPos;
	this.y = yPos;
	this.spriteImage = initialImage;
    }
    
    public void move() {
	x += xspeed;
	y += yspeed;
    }
    
    public void setX(int newX) {
	this.x = newX;
    }
    
    public int getX() {
	return this.x;
    }
    
    public void setY(int newY) {
	this.y = newY;
    }
    
    public int getY() {
	return this.y;
    }
    
    public void setXSpeed(int xSpeed) {
	this.xspeed = xSpeed;
    }
    
    public void setYSpeed(int ySpeed) {
   	this.yspeed = ySpeed;
    }
    
    public int getXSpeed() {
	return this.xspeed;
    }
    
    public int getYSpeed() {
   	return this.yspeed;
    }
    
    public void setSprite(Image newSprite) {
	this.spriteImage = newSprite;
    }
    
    public Image getSprite() {
	return this.spriteImage;
    }
    
    // A method that will assist in collision detection
    public Rectangle getBounds() {
	// Make the rectangle smaller than the original sprite dimensions to
	// make the collision look more sensible
	return new Rectangle(this.x+20, this.y+20, this.getSprite().getWidth(null)-20,
		this.getSprite().getHeight(null)-20);
    }
    
}
