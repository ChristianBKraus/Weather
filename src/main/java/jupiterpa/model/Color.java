package jupiterpa.model;

public class Color {
	public static final Color Black = new Color(0,0,0);
	public static final Color White = new Color(255,255,255);
	public static final Color Red = new Color(255,0,0);
	public static final Color Green = new Color(0,255,0);
	public static final Color Blue = new Color(0,0,255);
	public static final Color Yellow = new Color(0,255,255);

	
	int red;
	int green;
	int blue;
	
	public Color(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	@Override
	public String toString() {
		return "Color: " + red + "/" + green + "/" + blue;
	}
	
	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}
	
}
