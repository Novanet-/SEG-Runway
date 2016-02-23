package application.model;

import javafx.beans.property.DoubleProperty;

public class RunwayParameters {

	private final DoubleProperty TORA;
	private final DoubleProperty TODA;
	private final DoubleProperty ASDA;
	private final DoubleProperty LDA;
	private final DoubleProperty displacedThreshold;

	/**
	 * @param TORA
	 * @param TODA
	 * @param ASDA
	 * @param LDA
	 */
	public RunwayParameters(final DoubleProperty TORA, final DoubleProperty TODA, final DoubleProperty ASDA,
			final DoubleProperty LDA, final DoubleProperty displacedThreshold) {
		this.TORA = TORA;
		this.TODA = TODA;
		this.ASDA = ASDA;
		this.LDA = LDA;
		this.displacedThreshold = displacedThreshold;
	}

	/**
	 * @return
	 */
	public final double getTORA() {
		return TORA.getValue();
	}

	/**
	 * @return
	 */
	public final double getTODA() {
		return TODA.getValue();
	}

	/**
	 * @return
	 */
	public final double getASDA() {
		return ASDA.getValue();
	}

	/**
	 * @return
	 */
	public final double getLDA() {
		return LDA.getValue();
	}

	public final double getDisplacedThreshold() {
		return displacedThreshold.getValue();
	}

}