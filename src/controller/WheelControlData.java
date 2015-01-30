package controller;

public class WheelControlData {
	private double degX;
	private double degY;
	private double sizeX;
	private double sizeY;
	public WheelControlData(double degX, double degY, double sizeX, double sizeY) {
		this.degX = degX;
		this.degY = degY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	public double getDegX() {
		return degX;
	}
	public void setDegX(double degX) {
		this.degX = degX;
	}
	public double getDegY() {
		return degY;
	}
	public void setDegY(double degY) {
		this.degY = degY;
	}
	public double getSizeX() {
		return sizeX;
	}
	public void setSizeX(double sizeX) {
		this.sizeX = sizeX;
	}
	public double getSizeY() {
		return sizeY;
	}
	public void setSizeY(double sizeY) {
		this.sizeY = sizeY;
	}
}
