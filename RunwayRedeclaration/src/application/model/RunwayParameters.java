package application.model;

import javafx.beans.property.DoubleProperty;

public class RunwayParameters {

	private final DoubleProperty TORA;
	private final DoubleProperty TODA;
	private final DoubleProperty ASDA;
	private final DoubleProperty LDA;

	public RunwayParameters(final DoubleProperty TORA, final DoubleProperty TODA, final DoubleProperty ASDA,
			final DoubleProperty LDA) {
		this.TORA = TORA;
		this.TODA = TODA;
		this.ASDA = ASDA;
		this.LDA = LDA;
	}

	public final double getTORA() {
		return TORA.getValue();
	}

	public final double getTODA() {
		return TODA.getValue();
	}

	public final double getASDA() {
		return ASDA.getValue();
	}

	public final double getLDA() {
		return LDA.getValue();
	}

}