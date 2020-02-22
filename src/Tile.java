import java.awt.*;
import javax.swing.*;

public class Tile {

	final JButton tile;

	int tile_x_position;
	int tile_y_position;

	Tile(final int xPosition, final int yPosition) {

		this.tile_x_position = xPosition;
		this.tile_y_position = yPosition;
		
		tile = new JButton();

		Insets buttonMargin = new Insets(0, 0, 0, 0);
		tile.setMargin(buttonMargin);
	}

	/**
	 * [getXPosition description]
	 * @return this x-position
	 */
	public int getXPosition() {
		return this.tile_x_position;
	}

	/**
	 * [getYPosition description]
	 * @return this y position
	 */
	public int getYPosition() {
		return this.tile_y_position;
	}
}