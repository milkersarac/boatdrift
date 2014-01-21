/**
 * @author ilker sarac
 */

package GameComponents;

import java.awt.Image;
import java.awt.Rectangle;


public abstract class Collidable {

        /**An abstract class for all collidable game objects*/
	public Collidable(){}

	abstract public Image getImage();
	abstract public int getX();
	abstract public int getY();
	abstract public Rectangle getBounds();

}
