package application.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public class Obstacle {

	private final StringProperty name;
	private final DoubleProperty height;
	private final DoubleProperty width;
	private final DoubleProperty length;
	private final DoubleProperty blastProtection;

	public Obstacle(final StringProperty name, final DoubleProperty height, final DoubleProperty width,
			final DoubleProperty length, final DoubleProperty blastProtection) {
		this.name = name;
		this.height = height;
		this.width = width;
		this.length = length;
		this.blastProtection = blastProtection;
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

	public final double getBlastProtection() {
		return blastProtection.get();
	}

}