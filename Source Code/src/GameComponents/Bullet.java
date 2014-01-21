/**
 * @author ilker sarac
 */

package GameComponents;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;



public class Bullet extends Collidable{
	private String bullet = "resources/bullet.png";
	private double x, y;
	private int width, height;
	private Image image;
	boolean visible;

	private int direction;

    public Bullet(int x, int y){
        //ImageIcon ii = new ImageIcon(this.getClass().getResource("bullet.png"));
    	ImageIcon ii = new ImageIcon(bullet);
        image = ii.getImage();
        visible = true;
        this.x = x;
        this.y = y;
        direction = 0;
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public void move() {
    	//when bullet is not visible it will not move
    	if(isVisible()){
            x += GameManager.BULLET_SPEED_FACTOR*Math.cos(GameManager.TIGHT_TURN_FACTOR*this.direction*Math.PI/180);
            y += GameManager.BULLET_SPEED_FACTOR*Math.sin(GameManager.TIGHT_TURN_FACTOR*this.direction*Math.PI/180);
            if (x > GameManager.GAME_WIDTH || x < 0 || y > GameManager.GAME_HEIGHT || y < 0)
                visible = false;
    	}
    }

    public int getDirection(){
    	return direction;
    }

    public void setDirection(int newDirection){
    	direction = newDirection;
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

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible(){
    	return visible;
    }

	public Rectangle getBounds(){
		System.out.println("Bullet bounds:" + x + ", " + y + ", " + width + ", " + height);
		return new Rectangle((int)x, (int)y, width, height);
	}

}
