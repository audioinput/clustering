package clustering;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Clustering {

    private static int iterations;
    private static String algorithm;
    
    
    /*
    arguments format: filepath algorithm iterations
    algorithms:
    fft - Farthest First Traversal
    km - k-means (Lloyd)
    skm - Soft k-means
    h - Hierarchical (distance matrix)
    hd - Hierarchical (datapoints)
    */
    public static void main(String[] args) {
        File file = null;
        if (args.length > 1) {
            file = new File(args[0]);
            algorithm = args[1];
        }
        if (args.length > 2) {
            iterations = Integer.parseInt(args[2]);
        }
        if (algorithm.equals("skm")){
            System.out.println("Algorithm: Soft k-means");
            softKMeans(file);
        }
        if (algorithm.equals("km")){
            System.out.println("Algorithm: K-means");
            kMeans(file);
        }
        if (algorithm.equals("fft")){
            System.out.println("Algorithm: Farthest-First-Traversal");
            fFTraversal(file);
        }
        if (algorithm.equals("h")){
            System.out.println("Algorithm: Hierarchical clustering (from distance matrix)");
            hierarchical(file);
        }
        if (algorithm.equals("hd")){
            System.out.println("Algorithm: Hierarchical clustering (from data)");
            hierarchicalD(file);
        }
    }
    

    /*
    Converts data from scanner to a List of n-dimensional points.
    */
    private static ArrayList<Point> readData(Scanner sc, int n) {
        ArrayList<Point> data = new ArrayList<>();
        while (sc.hasNextLine()) {
                Point p = new Point(n);
                String[] coords = sc.nextLine().split(" ");
                for (int i = 0; i < n; i++) {
                    p.setCoord(i,Double.parseDouble(coords[i]));
                }
                data.add(p);
            }
        return data;
    }
    
    
    /*
    Correctly reads input for soft k-means and runs the algorithm.
    */
    private static void softKMeans(File f) {
        ArrayList<Point> data;
        try {
            Scanner sc = new Scanner(f);
            String[] ints = sc.nextLine().split(" ");
            int amtOfClusters = Integer.parseInt(ints[0]);
            int dimension = Integer.parseInt(ints[1]);
            double stiffness = Double.parseDouble(sc.nextLine());
            data = readData(sc, dimension);
            SoftKMeans skm = new SoftKMeans(data, amtOfClusters, stiffness, iterations);
            for (Point p : skm.cluster()){
                System.out.println(p.toString()); //prints all centers
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    /*
    Correctly reads input for k-means (Lloyd) and runs the algorithm.
    */
    private static void kMeans(File f) {
        ArrayList<Point> data;
        try {
            Scanner sc = new Scanner(f);
            String[] ints = sc.nextLine().split(" ");
            int amtOfClusters = Integer.parseInt(ints[0]);
            int dimension = Integer.parseInt(ints[1]);
            data = readData(sc, dimension);
            KMeans km = new KMeans(data, amtOfClusters);
            for (Point p : km.cluster()){
                System.out.println(p.toString()); //prints all centers
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    /*
    Correctly reads input for farthestfirst-traversal and runs the algorithm.
    */
    private static void fFTraversal(File f) {
        ArrayList<Point> data;
        try {
            Scanner sc = new Scanner(f);
            String[] ints = sc.nextLine().split(" ");
            int amtOfClusters = Integer.parseInt(ints[0]);
            int dimension = Integer.parseInt(ints[1]);
            data = readData(sc, dimension);
            FFTraversal fft = new FFTraversal(data, amtOfClusters);
            for (Point p : fft.cluster()){
                System.out.println(p.toString()); //prints all centers
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void hierarchical(File f) {
        int size;
        try {
            Scanner sc = new Scanner(f);
            size = Integer.parseInt(sc.nextLine());
            double[][] matrix = new double[size][size];
            int row = 0;
            while (sc.hasNextLine()) {
                String[] dists = sc.nextLine().split(" ");
                for (int i = 0; i < size; i++) {
                    matrix[row][i] = Double.parseDouble(dists[i]);
                }
                row++;
            }
            Hierarchical h = new Hierarchical(matrix);
            for (int i = 0; i < size - 1; i++) {
                List<Integer> cluster = h.cluster();
                cluster.sort(Comparator.naturalOrder());
                System.out.print(cluster.get(0));
                for (int j = 1; j < cluster.size(); j++) {
                    System.out.print(" " + cluster.get(j));
                }
                System.out.println("");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Clustering.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void hierarchicalD(File f) {
        ArrayList<Point> data;
        int dimension;
        try {
            Scanner sc = new Scanner(f);
            dimension = Integer.parseInt(sc.nextLine());
            data = readData(sc, dimension);
            Hierarchical h = new Hierarchical(data);
            for (int i = 0; i < data.size() - 1; i++) {
                List<Integer> cluster = h.cluster();
                cluster.sort(Comparator.naturalOrder());
                System.out.print(cluster.get(0));
                for (int j = 1; j < cluster.size(); j++) {
                    System.out.print(" " + cluster.get(j));
                }
                System.out.println("");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Clustering.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
