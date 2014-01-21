/**
 * @author Mustafa Ilker Saraç
 */

package GameComponents;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Island extends Collidable{

	//private String island = "island.png";

	private double x, y;
	private int width, height;
	private Image image;

	/**IslandType: large, medium, small.*/
	public Island(int x, int y, String islandType){
		//ImageIcon ii = new ImageIcon(this.getClass().getResource(islandType + "island.png"));
		//ImageIcon ii = new ImageIcon(this.getClass().getResource("mediumisland.png"));
		ImageIcon ii = new ImageIcon("resources/" + islandType + "island.png");
		image = ii.getImage();
		this.x = x;
		this.y = y;
		width = image.getWidth(null);
		height = image.getHeight(null);
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

	public Rectangle getBounds(){
		//System.out.println("Island bounds: " + x + ", " + y + ", " + width + ", " + height);
		//return new Rectangle((int)x+40, (int)y+70, width-90, height-90);
		int rx, ry, rw, rh;
		int d = (int)(0.2 * width);
		rx = (int)x + (int)(0.8 * d);
		ry = (int)y + (int)(1.3 * d);
		rw = width - (int)(1.8*d);
		rh = height - 2*d;
		Rectangle temp = new Rectangle(rx, ry, rw, rh);

		return temp;
		//return new Rectangle((int)x, (int)y, width, height);
	}

}
