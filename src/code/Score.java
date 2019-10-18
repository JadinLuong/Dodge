package code;

/**
 * 	Represents the scoring of the game
 */
public class Score {
    
    private int score;
    
    public Score() {
	this.score = 0;
    }
    
    public void setScore(int newScore) {
	this.score = newScore;
    }
    
    public int getScore() {
	return this.score;
    }
    
    public void incrementScore() {
	this.score++;
    }
    
    public void resetScore() {
	this.score = 0;
    }
}
