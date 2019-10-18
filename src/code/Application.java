package code;

import javax.swing.*;

/***
 *	Represents the Application (Window of the game)
 */
public class Application extends JFrame{
    
    public Application() {
	initWindow();
    }
    
    private void initWindow() {
	add(new Game());
	
	setTitle("Dodge!");
	setSize(450, 635);
	setLocation(500,100);
	setResizable(false);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
	Application dodgeGame = new Application();
	dodgeGame.setVisible(true);
    }
}
