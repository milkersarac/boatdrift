/**
 * @author Mustafa Ilker Saraç
 */

package GameComponents;
import java.awt.Image;

import java.util.Random;

import javax.swing.ImageIcon;
/**This class simulates wind--singleton*/
public class WindManager {
	private static final WindManager Instance = new WindManager();//Singleton
	private String arrow = "resources/arrow.png";
	//private String arrow = "arrow.png";

	private double speedX, speedY;
	private int direction;//rotation between 0-360
	private Image image;
	private Random r;

	private int initialTime = GameManager.INITAL_TIME;

	private WindManager(){
		r = new Random();
		//ImageIcon ii = new ImageIcon(this.getClass().getResource(arrow));
		ImageIcon ii = new ImageIcon(arrow);
		image = ii.getImage();

		direction = 0;//default direction
		speedX = GameManager.WIND_FORCE_FACTOR * (Math.cos(direction*Math.PI/180));
		speedY = GameManager.WIND_FORCE_FACTOR * (Math.sin(direction*Math.PI/180));


	}

	public static WindManager getInstance(){//Singleton getInstance() method.
		return Instance;
	}

	public void setDirection(int newDirection){
		direction = newDirection;
	}

	public int getDirection(){
		return direction;
	}

	public void generateWindDirection(){
		int currentTime = (int)System.currentTimeMillis()/1000;//Current time in seconds
		//System.out.println("cur time: " + currentTime);

		if((currentTime - initialTime) % GameManager.WIND_CHANGE_FACTOR	== 0){
			direction = r.nextInt(360);
			generateWindSpeeds();
			currentTime = (int)System.currentTimeMillis()/1000;
		}

	}
	/**Generates wind speeds from wind direction*/
	private void generateWindSpeeds(){
		speedX = GameManager.WIND_FORCE_FACTOR * (Math.cos(direction*Math.PI/180));
		speedY = GameManager.WIND_FORCE_FACTOR * (Math.sin(direction*Math.PI/180));
	}

	public int getSpeedX(){
		return (int)speedX;
	}

	public int getSpeedY(){
		return (int)speedY;
	}

	public Image getImage(){
		return image;
	}

}
