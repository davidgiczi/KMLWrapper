package hu.david.giczi.mvmxpert.wrapper.service;


import hu.david.giczi.mvmxpert.wrapper.domain.Point;

public class AzimuthAndDistance {
	
	private final Point pointA;
	private final Point pointB;
	
	public AzimuthAndDistance(Point pointA, Point pointB) {
		this.pointA = pointA;
		this.pointB = pointB;
	}
	
	public double calcAzimuth() {
		
		double deltaX = pointB.getY_EOV() - pointA.getY_EOV();
		double deltaY = pointB.getX_EOV() - pointA.getX_EOV();
		
		if( deltaX >= 0 && deltaY > 0 ) {
			return Math.atan(deltaX / deltaY);
		}
		else if( deltaX >= 0 &&  0 > deltaY ) {
			return Math.PI - Math.atan(deltaX / Math.abs(deltaY));
		}
		else if( 0 >= deltaX && 0 > deltaY ) {
			return Math.PI + Math.atan(Math.abs(deltaX) / Math.abs(deltaY));
		}
		else if( 0 >= deltaX && deltaY > 0 ) {
			return 2 * Math.PI - Math.atan(Math.abs(deltaX) / deltaY);
		}
		else if(deltaX > 0) {
			return Math.PI / 2;
		}
		else if(0 > deltaX) {
			return 3 * Math.PI / 2;
		}
		
		return Double.NaN;
	}
	 
	public double calcDistance() {
		return Math.sqrt(Math.pow(pointA.getX_EOV() - pointB.getX_EOV(), 2)
				+ Math.pow(pointA.getY_EOV() - pointB.getY_EOV(), 2));
	}


}
