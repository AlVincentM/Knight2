import java.awt.*;
import javax.swing.*;

public class Tile {

	final JButton tile;

	int tile_x_position = 0;
	int tile_y_position = 0;

	Tile(final int tile_x_position, final int tile_y_position) {

		this.tile_x_position = tile_x_position;
		this.tile_y_position = tile_y_position;

		tile = new JButton();

		Insets buttonMargin = new Insets(0, 0, 0, 0);
		tile.setMargin(buttonMargin);
	}

	public int getXPosition() {
		return this.tile_x_position;
	}

	public int getYPosition() {
		return this.tile_y_position;
	}
}