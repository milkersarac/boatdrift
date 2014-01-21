/**
 * @author Mustafa Ilker Saraç
 */

package GameComponents;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Buoy extends Collidable{
	private String buoy = "resources/buoy.png";
	private double x, y;
	private int width, height;
	private Image image;
	private boolean visible;
	private int point;


	public Buoy(int x, int y){
		//ImageIcon ii = new ImageIcon(this.getClass().getResource("buoy.png"));
		ImageIcon ii = new ImageIcon(buoy);
		image = ii.getImage();
		visible = true;
		this.x = x;
		this.y = y;
		width = image.getWidth(null);
		height = image.getHeight(null);
		point = 2;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return image;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return (int)x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return (int)y;
	}

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

	public int getPoint(){
		return point;
	}

	public void setPoint(int newPoint){
		point = newPoint;
	}

    
	public Rectangle getBounds(){
		//System.out.println("Buoy bounds:" + x + ", " + y + ", " + width + ", " + height);
		return new Rectangle((int)x, (int)y, width, height);
	}

}
