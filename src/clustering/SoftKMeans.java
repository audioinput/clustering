package clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class SoftKMeans {
    private final Point[] data;
    private Point[] centers;
    private final int amtOfClusters;
    private final int dimension;
    private final int dataSize;
    private final double stiffness;
    private double[][] matrix;
    private final int iterations;
    
    /*
    Initializes all fields.
    */
    public SoftKMeans(ArrayList<Point> data, int amtOfClusters, double stiffness, int iterations) {
        System.out.println("Stiffness= " + stiffness);
        System.out.println("it= " + iterations);
        this.data = Arrays.copyOf(data.toArray(), data.toArray().length, Point[].class);
        centers = new Point[amtOfClusters];
        for (int i = 0; i < amtOfClusters; i++) {
            centers[i] = this.data[i];
        }
        this.iterations = iterations;
        this.amtOfClusters = amtOfClusters;
        this.stiffness = stiffness;
        dimension = data.get(0).getCoords().length;
        dataSize = data.size();
        matrix = new double[amtOfClusters][data.size()];
    }
    
    /*
    Updates the "hidden matrix".
    */
    public void updateMatrix() {
        double[] numerators = new double[amtOfClusters];
        double denominator;
        for (int j = 0; j < dataSize; j++){
            denominator = 0;
            for (int i = 0; i < amtOfClusters; i++) {
                numerators[i] = Math.pow(Math.E, -stiffness*data[j].dist(centers[i]));
                denominator += numerators[i];
            }
            for (int i = 0; i < amtOfClusters; i++){
                matrix[i][j] = numerators[i]/denominator;
            }
        }
    }
    
    /*
    Updates the centers using the "hidden matrix".
    */
    public void findCenters() {
        double[] unitVector = unitVector(dataSize);
        for (int i = 0; i < amtOfClusters; i++) {
            for (int j = 0; j < dimension; j++) {
                double coord = dotProduct(matrix[i], coordVector(j))/dotProduct(matrix[i], unitVector);
                centers[i].setCoord(j, coord);
            }
        }
    }
    
    /*
    Calculates the dot-product from vectors (as arrays) a and b.
    */
    public double dotProduct(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];    
        }
        return sum;
    }
    
    /*
    Returns the n-dimensional vector with the j-th component of each datapoint.
    Where n is the amount of datapoints.
    */
    public double[] coordVector(int j) {
        double[] a = new double[dataSize];
        for (int i = 0; i < dataSize; i++) {
            a[i] = data[i].getCoord(j);
        }
        return a;
    }
    
    /*
    Returns a unit vector with dimension = size.
    */
    public double[] unitVector(int size) {
        double[] a = new double[size];
        for (int i = 0; i < size; i++) {
            a[i] = 1;
        }
        return a;
    }
    
    /*
    Runs the actual algorithm.
    */
    public Point[] cluster(){
        if (data.length > amtOfClusters)
        {
            for (int i = 0; i < amtOfClusters; i++){
                centers[i] = data[i];
            }
            for(int i = 0; i < iterations; i++) {
                updateMatrix();
                findCenters();
            }
            
        }
        return centers;
    }
}