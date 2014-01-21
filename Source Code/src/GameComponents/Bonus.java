/**
 * @author Mustafa Ilker Saraç
 */

package GameComponents;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class Bonus extends Collidable{
	private String boxImage = "resources/box.png";//Default
	private String lifeImage = "resources/lifebonus.png";//type1
	private String speedImage = "resources/speedbonus.png";//type2
	private String bulletImage = "resources/bulletbonus.png";//type3

	private double x, y;
	private int width, height;
	private boolean visible;
	private boolean cracked;
	private String bonusType;
	private Image image;
	private ImageIcon ii;
	private int point;


	public Bonus(int x, int y, String type){
		//ii = new ImageIcon(this.getClass().getResource(boxImage));//Bonus representation is a box in default case
		ii = new ImageIcon(boxImage);
		image = ii.getImage();
		visible = true;
		this.x = x;
		this.y = y;
		width = image.getWidth(null);
		height = image.getHeight(null);
		cracked = false;//default case bonus is not cracked, bonus is box
		bonusType = type;
		point = 0;//default point
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return image;
	}
	/**Random set bonus method*/
	public void setRandomBonusImage(){
		Random r = new Random();
		int i = r.nextInt(3);
		if(i == 0){
			//ii = new ImageIcon(this.getClass().getResource(lifeImage));
			ii = new ImageIcon(lifeImage);
			image = ii.getImage();
			width = image.getWidth(null);
			height = image.getHeight(null);
			bonusType = "lifebonus";
			point = 10;
		}
		else if(i == 1){
			//ii = new ImageIcon(this.getClass().getResource(speedImage));
			ii = new ImageIcon(speedImage);
			image = ii.getImage();
			width = image.getWidth(null);
			height = image.getHeight(null);
			bonusType = "speedbonus";
			point = 25;
		}
		else if(i == 2){
			//ii = new ImageIcon(this.getClass().getResource(bulletImage));
			ii = new ImageIcon(bulletImage);
			image = ii.getImage();
			width = image.getWidth(null);
			height = image.getHeight(null);
			bonusType = "bulletbonus";
			point = 5;
		}
		else
			System.out.println("undefined image");
	}
        /**This initializes new types of bonuses*/
	public void setBonusImage(String bonusType){
		if(bonusType.equalsIgnoreCase("lifebonus")){
			ii = new ImageIcon(this.getClass().getResource(lifeImage));
			image = ii.getImage();
			width = image.getWidth(null);
			height = image.getHeight(null);
			point = 10;
		}
		else if(bonusType.equalsIgnoreCase("speedbonus")){
			ii = new ImageIcon(this.getClass().getResource(speedImage));
			image = ii.getImage();
			width = image.getWidth(null);
			height = image.getHeight(null);
			point = 25;
		}
		else if(bonusType.equalsIgnoreCase("bulletbonus")){
			ii = new ImageIcon(this.getClass().getResource(bulletImage));
			image = ii.getImage();
			width = image.getWidth(null);
			height = image.getHeight(null);
			point = 5;
		}
		else
			System.out.println("undefined image");
	}

	//setBonusImage(so bonus type) randomly!!

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

	public boolean isVisible(){
		return visible;
	}

	public void setVisible(boolean visible){
		this.visible = visible;
	}

	public boolean isCracked(){
		return cracked;
	}

	public void setCracked(boolean cracked){
		this.cracked = cracked;
	}

	public String getBonusType(){
		return bonusType;
	}

	public void setBonusType(String type){
		bonusType = type;
	}

	public int getPoint(){
		return point;
	}

	public void setPoint(int newPoint){
		point = newPoint;
	}

	public void setPosition(int x, int y){
		this.x = (double)x;
		this.y = (double)y;
	}

	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, width, height);
	}


}
