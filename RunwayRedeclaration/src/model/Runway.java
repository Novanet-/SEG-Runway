package model;

public class Runway {
	private String name;
	
	/**
	 * Take-Off Run Available
	 */
	double TORA;
	
	/**
	 * Take-Off Distance Available
	 */
	double TODA;
	
	/**
	 * Accelerate-Stop Distance Available
	 */
	double ASDA;
	
	/**
	 * Landing Distance Available
	 */
	double LDA;
	
	public double getTORA() {
		return TORA;
	}

	public void setTORA(double TORA) {
		this.TORA = TORA;
	}

	public double getTODA() {
		return TODA;
	}

	public void setTODA(double TODA) {
		this.TODA = TODA;
	}

	public double getASDA() {
		return ASDA;
	}

	public void setASDA(double ASDA) {
		this.ASDA = ASDA;
	}

	public double getLDA() {
		return LDA;
	}

	public void setLDA(double LDA) {
		this.LDA = LDA;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
