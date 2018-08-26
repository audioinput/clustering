package clustering;

import java.util.ArrayList;

public class Point {
    private double[] coords;
    private Cluster cluster;

    /*
    Creates a datapoint (origin) in a Euclidean space with dimension dim.
    */
    public Point(int dim) {
        coords = new double[dim];
    }
    
    /*
    Creates a datapoint in a Euclidean space with given coordinates.
    */
    public Point(double[] coords) {
       this.coords = coords;    
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(coords[0]);
        for (int i = 1; i < coords.length; i++) {
            builder.append(" " + coords[i]);
        }
        return builder.toString();
    }
    
    /*
    Calculates the squared Euclidean distance to a datapoint p.
    */
    public double squaredDist(Point p) {
        double d = 0;
        for (int i = 0; i < this.getCoords().length; i++){
            d+=Math.pow((this.getCoords()[i]-p.getCoords()[i]),2);
        }
        return d;
    }
    
    /*
    Calculates the Euclidean distance to a datapoint p.
    */
    public double dist(Point p) {
        return Math.sqrt(squaredDist(p));
    }
    
    /*
    Returns the nearest point from the given list of points.
    */
    public Point findNearestCenter(ArrayList<Point> centers) {
        int k = 0;
        double minDist = Double.MAX_VALUE;
        double dist;
        for (int i = 0; i < centers.size(); i++) {
            dist = dist(centers.get(i));
            if (dist < minDist) {
                k = i;
                minDist = dist;
            }
        }
        return centers.get(k);
    }

    /*
    Returns the squared distance to the nearest point from the given list of points.
    */
    public double sqDistToNearestCenter(ArrayList<Point> centers) {
        double minDist = Double.MAX_VALUE;
        double dist;
        for (int i = 0; i < centers.size(); i++) {
            dist = squaredDist(centers.get(i));
            if (dist < minDist) {
                minDist = dist;
            }
        }
        return minDist;
    }
    
    /*
    Returns the distance to the nearest point from the given list of points.
    */
    public double distToNearestCenter(ArrayList<Point> centers) {
        return Math.sqrt(sqDistToNearestCenter(centers));
    }
    
    /*
    Some getters and setters.
    */
    
    public double[] getCoords() {
        return coords;
    }

    public void setCoords(double[] coords) {
        this.coords = coords;
    }
    
    public void setCoord(int j, double coord) {
        coords[j] = coord;
    }
    
    public double getCoord(int j) {
        return coords[j];
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
}
