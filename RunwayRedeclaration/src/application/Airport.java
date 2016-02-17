package application;

public class Airport {
	private String name;
	private Runway[] runways;
	
	public Runway[] getRunways() {
		return runways;
	}
	
	public void setRunways(Runway[] runways) {
		this.runways = runways;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
