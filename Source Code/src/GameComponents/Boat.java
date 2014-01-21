/**
 * @author Mustafa Ilker Saraç
 */

package GameComponents;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Boat extends Collidable{

    //private String boat = "boat.png";
    private String boat = "resources/boat_green.png";
    //private String boat = "poolboat.png";




    private double x, y;
    private int width, height;
    private Image image;
    private int direction;//rotation between 0-360
    private boolean visible;
    private int numOfLives;
    private int numOfUsedBullets;
    private boolean flashForward;
    //bonus durumuna göre (can)-hız-bullets
    private boolean directionKeysOn;

    private ArrayList<Bullet> bullets;

    private final int BOAT_SIZE = 0;//collisionda sorun çıkarabilir, incele!!

    private WindManager wind = WindManager.getInstance();

    public Boat(){
    	//ImageIcon ii = new ImageIcon(this.getClass().getResource(boat));
    	ImageIcon ii = new ImageIcon(boat);
    	image = ii.getImage();
    	bullets = new ArrayList<Bullet>();
    	x = 0;
    	y = 0;
    	//width = image.getWidth(null);
    	width = 30;
    	//height = image.getHeight(null);
    	height = 10;
    	direction = 0;
    	visible = true;
    	numOfLives = GameManager.INITIAL_BOAT_LIFE_AMOUNT;
    	numOfUsedBullets = 0;
    	flashForward = false;
    	directionKeysOn = true;//??
    }

    public Boat(int x, int y){
    	//ImageIcon ii = new ImageIcon(this.getClass().getResource(boat));
    	ImageIcon ii = new ImageIcon(boat);
    	image = ii.getImage();
    	bullets = new ArrayList<Bullet>();
    	this.x = x;
    	this.y = y;
    	width = image.getWidth(null);
    	height = image.getHeight(null);
    	direction = 0;
    	visible = true;
    	numOfLives = GameManager.INITIAL_BOAT_LIFE_AMOUNT;
    	//maxNumOfBullets = GameManager.INITIAL_BOAT_BULLET_AMOUNT;
    	numOfUsedBullets = 0;
    	flashForward = false;
    	directionKeysOn = true;//??
    }
    public void setDirectionKeysOn(boolean newDierction){
    	directionKeysOn = newDierction;
    }

    public void move(){
    	//when boat is not visible it will not move
    	if(isVisible()){
    		//For smooth shifts between borders
        	if((x >= -48) && (x <= 710))
        		x += wind.getSpeedX()+GameManager.BOAT_SPEED_FACTOR*Math.cos(GameManager.TIGHT_TURN_FACTOR*direction*Math.PI/180)* increaseBoatSpeed();
        	else if(x < -48)
        		x = 710;
        	else
        		x = -48;

        	if((y >= -48) && (y <= 710))
    			y += wind.getSpeedY()+GameManager.BOAT_SPEED_FACTOR*Math.sin(GameManager.TIGHT_TURN_FACTOR*direction*Math.PI/180) * increaseBoatSpeed();
        	else if(y < - 48)
        		y = 710;
        	else
        		y = -48;
    	}

		//System.out.println("Direction: " + GameManager.TIGHT_TURN_FACTOR*direction%360 + "  x: " + (int)x + "  y: " + (int)y);
    }

    /**Live Bonus Operations*/
    public int getNumOfLives(){
    	return numOfLives;
    }
    /**Live Bonus Operations*/
    public void incrementNumOfLives(){
    	numOfLives++;
    }
    /**Live Bonus Operations*/
    public void decrementNumOfLives(){
    	numOfLives--;
    }
    /**Live Bonus Operations*/
    public void setNumOfLives(int lifeAmount){
    	numOfLives = lifeAmount;
    }
    /**Bullet Bonus Operations*/
    public int getRemainingNumOfBullets(){
    	return GameManager.INITIAL_BOAT_BULLET_AMOUNT - numOfUsedBullets;
    }
    /**Bullet Bonus Operations*/
    public void replenishUsedNumOfBullets(){
    	numOfUsedBullets = 0;
    }
    /**Bullet Bonus Operations*/
    public void addBonusBullets(){//bullet bonusu bunu çağırır.
    	numOfUsedBullets -= GameManager.BULLET_BONUS_AMOUNT_FACTOR;
    }
    /**Speed Bonus Operations*/
    public boolean isFlashForward(){
    	return flashForward;
    }
    /**Speed Bonus Operations*/
    public void setFlashForward(boolean flash){
    	flashForward = flash;
    }
    /**Speed Bonus Operations*/
	private int increaseBoatSpeed(){
		if(flashForward){
			return GameManager.SPEED_BONUS_FACTOR;
		}
		return 1;
	}
    /**Direction of the boat*/
    public int getDirection(){
    	return direction;
    }
    /**Direction of the boat*/
	public void setDirection(int newDirection) {
		direction = newDirection;
	}
	/**Set boat image from the GUI*/
	public void setBoatImage(String newImage){
		boat = newImage;
    	//ImageIcon ii = new ImageIcon(this.getClass().getResource(boat));
		ImageIcon ii = new ImageIcon(boat);
    	image = ii.getImage();
    	width = image.getWidth(null);
    	height = image.getHeight(null);
	}

	@Override
    public Image getImage(){
    	return image;
    }

	@Override
    public int getX(){
    	return (int)x;
    }

	@Override
    public int getY(){
    	return (int)y;
    }

	public void setPosition(int newX, int newY){
		x = newX;
		y = newY;
	}

	public Rectangle getBounds(){
		//System.out.println("Boat bounds:" + x + ", " + y + ", " + width + ", " + height);
		return new Rectangle((int)x, (int)y, width, height);
	}

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

	public ArrayList<Bullet> getBulletsList(){
		return bullets;
	}

	public void fire(){
		Bullet b = new Bullet((int)x + BOAT_SIZE/2, (int)y + BOAT_SIZE/2);
		b.setDirection(direction);
		if(numOfUsedBullets < GameManager.INITIAL_BOAT_BULLET_AMOUNT){//eğer kullanılmış bullet sayısı maximumdan küçükse yeni bulleta izin ver
			bullets.add(b);
			numOfUsedBullets++;
		}
	}

	public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            fire();
        }

        if(!directionKeysOn){
            if (key == KeyEvent.VK_LEFT) {
            	direction--;
            }

            if (key == KeyEvent.VK_RIGHT) {
            	direction++;
            }
        }

        if(directionKeysOn){
            //determine direction
            if(key == KeyEvent.VK_A){
            	direction--;
            }

            if(key == KeyEvent.VK_D){
            	direction++;
            }
        }

    }

	public void keyReleased(KeyEvent e) {
    }
//	 public static void main(String[] args){
//
//	 }

}
