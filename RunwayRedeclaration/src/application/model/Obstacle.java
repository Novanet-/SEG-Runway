package application.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public class Obstacle {

	private final StringProperty name;
	private final DoubleProperty height;
	private final DoubleProperty width;
	private final DoubleProperty length;

	public Obstacle(final StringProperty name, final DoubleProperty height, final DoubleProperty width,
			final DoubleProperty length) {
		this.name = name;
		this.height = height;
		this.width = width;
		this.length = length;
	}

	/**
	 * @return
	 */
	public final String getName() {
		return name.getValueSafe();
	}

	/**
	 * @return
	 */
	public final double getHeight() {
		return height.getValue();
	}

	/**
	 * @return
	 */
	public final double getWidth() {
		return width.getValue();
	}

	/**
	 * @return
	 */
	public final double getLength() {
		return length.getValue();
	}

}