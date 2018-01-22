package jupiterpa.weather.domain.model;

public class Led {
	
	int row;
	int column;		
	int red = 0;
	int green = 0;
	int blue = 0;

	public Led() {}
	
	public Led(Location loc, Color color) {
		this.row = loc.getRow();
		this.column = loc.getColumn();
		this.red = color.getRed();
		this.green = color.getGreen();
		this.blue = color.getBlue();
	}

	@Override
	public String toString() {
		return "LED " + row + "/" + column + ": " + red + "/" + green + "/" + blue;
	}
	
	public Location getLocation() {
		return new Location(row,column);
	}
	
	public Color getColor() {
		return new Color(red,green,blue);
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

}
