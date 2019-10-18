package code;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *	Represents the actual content of the game.
 */
public class Game extends JPanel implements ActionListener{
    
    // Constants that represent the game's current state
    private static final int START_SCREEN = 0;
    private static final int IN_GAME = 1;
    private static final int END_SCREEN = 2;
    
    /*
     *  Constants that represent different FPS uses, EIGHT_FPS will be used
     *  mostly for player animation where the player's animation refresh rate
     *  will be 8 FPS. ONE_FPS will be used as a counter to count in seconds.
     */
    private static final int EIGHT_FPS = 8;
    private static final int ONE_FPS = 62;
    
    // Constant that contains number of enemies in the game
    private static final int NUM_OF_ENEMIES = 8;
    
    private Image background;
    private Player player;
    private Timer timer;
    private int playerFrame; // mainly used for player animation
    private int secondCounter; // mainly used for score
    private int gameWidth;
    private int gameHeight;
    private int GAME_STATE;
    
    /*
     *  These variable's main purpose is to grab all the sprites/images for the
     *  player to allow for proper animation that corresponds to the player's
     *  current action.
     */
    private ArrayList<Image> playerStandingLeft = new ArrayList<Image>();
    private ArrayList<Image> playerStandingRight = new ArrayList<Image>();
    private ArrayList<Image> playerWalkingLeft = new ArrayList<Image>();
    private ArrayList<Image> playerWalkingRight = new ArrayList<Image>();
    private Animation pStandLeftAni;
    private Animation pStandRightAni;
    private Animation pWalkLeftAni;
    private Animation pWalkRightAni;
    
    // Container that holds all the enemies in the game
    private ArrayList<Enemy> enemies;
    
    private Score score;
    
    public Game() {
	initGame();
    }
    
    // Initializes all the variables to values that are required to start the
    // game
    public void initGame(){
	// Initializes a Key listener to listen for user key actions
	addKeyListener(new TAdapter());
	setFocusable(true);
	
	// Grabs the background image and initializes the background
	ImageIcon ii = new ImageIcon(this.getClass().getResource("/background.png"));
	background = ii.getImage();
	gameWidth = background.getWidth(this);
	gameHeight = background.getHeight(this);
	setPreferredSize(new Dimension(gameWidth, gameHeight));
	
	// Initializes the helping variables that assist in player animation
	// and scoring
	playerFrame = 0;
	secondCounter = 0;
	
	//Initialize game state to the start screen
	GAME_STATE = START_SCREEN;
	
	player = new Player();
	enemies = new ArrayList<Enemy>();
	score = new Score();
	
	loadPlayerSprites();
	
	initializeEnemies();
	
	timer = new Timer(16, this);
	timer.start();
    }
    
    private void initializeEnemies() {
	int randX;
	int randY;
	for (int i = 0; i < NUM_OF_ENEMIES; i++) {
	    randX = ThreadLocalRandom.current().nextInt(0, 375);
	    randY = ThreadLocalRandom.current().nextInt(-80, -68);
	    enemies.add(new Enemy(randX, randY));
	}
    }
    
    private void loadPlayerSprites() {
	
	for(int i = 0; i < 4; i++) {
	    playerStandingLeft.add((new ImageIcon(this.getClass().
		    getResource("/standleft" + i + ".png")).getImage()));
	    playerStandingRight.add((new ImageIcon(this.getClass().
		    getResource("/standright" + i + ".png")).getImage()));
	    playerWalkingLeft.add((new ImageIcon(this.getClass().
		    getResource("/walkleft" + i + ".png")).getImage()));
	    playerWalkingRight.add((new ImageIcon(this.getClass().
		    getResource("/walkright" + i + ".png")).getImage()));
	}
	
	pStandLeftAni = new Animation(player, playerStandingLeft);
	pStandRightAni = new Animation(player, playerStandingRight);
	pWalkLeftAni = new Animation(player, playerWalkingLeft);
	pWalkRightAni = new Animation(player, playerWalkingRight);
    }
    
    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	run(g);
	
    }
    
    /*
     * Main method that keeps the game running
     * Take note that this method is essentially called every 16ms since we had
     * our timer refresh every 16ms.
     */
    private void run(Graphics g) {
	playerFrame++;
	secondCounter++;
	
	// Main logic that allows the player to animate in 8 FPS
	if(playerFrame > EIGHT_FPS) {
	    playerFrame = 0;
	}
	
	Graphics2D g2d = (Graphics2D) g;
	
	// Acts as the game's state machine
	if(GAME_STATE == START_SCREEN) {
	    startScreen(g2d);  
	} else if(GAME_STATE == IN_GAME) {
	    inGame(g2d);
	} else if(GAME_STATE == END_SCREEN) {
	    endScreen(g2d);
	}
    }
    
    // Represents the result screen which essentially shows the player's score
    // and prompts them to press space bar to play again
    private void endScreen(Graphics2D g2d) {
	this.setBackground(new Color(73, 105, 209));
	
	Font font = new Font("Arial", Font.PLAIN, 15);
	g2d.setColor(Color.WHITE);
	g2d.setFont(font);
	g2d.drawString("Press spacebar to restart", 145, 380);
	
	font = new Font("Arial", Font.PLAIN, 15);
	g2d.setFont(font);
	g2d.drawString("Your Score:", 185, 220);
	
	font = new Font("Arial", Font.BOLD, 150);
	g2d.setFont(font);
	
	// Center the score
	FontMetrics fm = g2d.getFontMetrics();
	int x = (gameWidth - fm.stringWidth(String.valueOf(score.getScore())))/2;
	int y = (fm.getAscent() + (gameHeight - (fm.getAscent() + fm.getDescent()))/2);
	g2d.drawString(String.valueOf(score.getScore()), x, y);
    }
    
    // Represents the start screen, prompts the user to press spacebar to start
    // the game
    private void startScreen(Graphics2D g2d) {
	g2d.drawImage(background, 0, 0, this);
	
	// A fancy way to outline text
	AffineTransform transform = g2d.getTransform();
	transform.translate(100, 300);
	g2d.transform(transform);
	g2d.setColor(Color.BLACK);
	FontRenderContext frc = g2d.getFontRenderContext();
	TextLayout textLayout = new TextLayout("START", 
		g2d.getFont().deriveFont(80F), frc);
	Shape shape = textLayout.getOutline(null);
	g2d.setStroke(new BasicStroke(5f));
	g2d.draw(shape);
	g2d.setColor(Color.WHITE);
	g2d.fill(shape);
	
	Font font = new Font("Arial", Font.BOLD, 20);
	g2d.setColor(Color.BLACK);
	g2d.setFont(font);
	g2d.drawString("Press spacebar to start", 15, 20);
	
    }
    
    /* 
     * Represents the game when it is active, constantly updates player,
     * enemies and score's properties.
     * Always checks collision between the player and any enemy.
     */
    private void inGame(Graphics2D g2d) {
	g2d.drawImage(background, 0, 0, this);
	
	updatePlayer(g2d);
	updateEnemies(g2d);
	updateScore(g2d);
	checkCollision();
    }
    
    // Updates score, and displays it at the top of the game screen
    private void updateScore(Graphics2D g2d) {
	if(secondCounter > ONE_FPS) {
	    score.incrementScore();
	    secondCounter = 0;
	}
	
	// A way to make the score text look fancy and outlined
	AffineTransform transform = g2d.getTransform();
	transform.translate(190, 80);
	g2d.transform(transform);
	g2d.setColor(Color.BLACK);
	FontRenderContext frc = g2d.getFontRenderContext();
	TextLayout textLayout = new TextLayout(String.valueOf(score.getScore()), 
		g2d.getFont().deriveFont(80F), frc);
	Shape shape = textLayout.getOutline(null);
	g2d.setStroke(new BasicStroke(5f));
	g2d.draw(shape);
	g2d.setColor(Color.WHITE);
	g2d.fill(shape);
    }
    
    // checks collision between the player and any of the enemies
    private void checkCollision() {
	int index = 0;
	boolean collisionDetected = false;
	// loops through enemies and call collision detection helper method
	while(index < NUM_OF_ENEMIES && collisionDetected == false) {
	    collisionDetected = CollisionDetector.spriteCollision(player, 
		    enemies.get(index));
	    index++;
	}
	// player loses of collision detected between player and an enemy5
	if(collisionDetected == true) {
	    GAME_STATE = END_SCREEN;
	}
    }
    
    // updates the Enemies' properties and location
    private void updateEnemies(Graphics2D g2d) {
	// represents random x and y coordinates for the enemies
	int randX;
	int randY;
	
	// loops through each enemy
	for(int i = 0; i < NUM_OF_ENEMIES; i++) {
	    // move the enemies accordingly and draw the enemy in their new
	    // position
	    enemies.get(i).move();
	    g2d.drawImage(enemies.get(i).getSprite(), enemies.get(i).getX(),
		    enemies.get(i).getY(), this);
	    /*
	     * If enemies fall out of the map, respawn a new one at the top of
	     * the map at a random coordinate.
	     */
	    if(enemies.get(i).getY() > 600) {
		randX = ThreadLocalRandom.current().nextInt(0, 375);
                randY = ThreadLocalRandom.current().nextInt(-80, -68);
                enemies.get(i).setX(randX);
                enemies.get(i).setY(randY);
                enemies.get(i).setYSpeed(ThreadLocalRandom.current().nextInt(4, 10));
	    }
	}
    }
    
    // updates player's properties and location
    private void updatePlayer(Graphics2D g2d) {
	// Ensures the player can not move out of the map
	if((player.getX() > 0 && player.getXSpeed() < 0) ||
		player.getX() < 400 && player.getXSpeed() > 0) {
	    player.move();
	}
	// Constantly updates the player's corresponding animation and draws it
	updatePlayerAnimation();
	g2d.drawImage(player.getSprite(), player.getX(), player.getY(), this);
    }
    
    // updates the player's animation
    private void updatePlayerAnimation() {
	if(player.isMoving() == false && player.isFacingRight() == false) {
	    pStandLeftAni.animate(playerFrame);
	}else if(player.isMoving() == false && player.isFacingRight() == true) {
	    pStandRightAni.animate(playerFrame);
	}else if(player.isMoving() == true && player.isFacingRight() == false) {
	    pWalkLeftAni.animate(playerFrame);
	}else if(player.isMoving() == true && player.isFacingRight() == true) {
	    pWalkRightAni.animate(playerFrame);
	}
    }
    
    // resets all the required variables to it's default values to restart
    // the game
    private void restartGame() {
	score.resetScore();
	secondCounter = 0;
	enemies = new ArrayList<Enemy>();
	initializeEnemies();
    }
    
    private class TAdapter extends KeyAdapter {
	
	@Override
	public void keyPressed(KeyEvent e) {
	    if(e.getKeyCode() == KeyEvent.VK_SPACE) {
		if(GAME_STATE == START_SCREEN) {
		    GAME_STATE = IN_GAME;
		} else if(GAME_STATE == END_SCREEN) {
		    restartGame();
		    GAME_STATE = IN_GAME;
		}
	    } else {
		player.keyPressed(e);
	    }
	}	
	
	@Override
	public void keyReleased(KeyEvent e) {
	    player.keyReleased(e);
	    // resets the player's animation frame back to 0 when a new action
	    // is performed
	    playerFrame = 0;
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	repaint();
    }

}
