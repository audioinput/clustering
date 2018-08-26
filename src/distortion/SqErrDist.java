package distortion;

import clustering.Point;
import clustering.SoftKMeans;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SqErrDist {

    
    public static void main(String[] args) {
        File f = new File(args[0]);
        ArrayList<Point> data = new ArrayList<>();
        ArrayList<Point> centers = new ArrayList<>();
        try {
            Scanner sc = new Scanner(f);
            String[] ints = sc.nextLine().split(" ");
            int amtOfClusters = Integer.parseInt(ints[0]);
            int dimension = Integer.parseInt(ints[1]);
            String cCoords = sc.nextLine();
            while (!cCoords.startsWith("-")) {
                Point p = new Point(dimension);
                String[] coords = cCoords.split(" ");
                for (int i = 0; i < dimension; i++) {
                    p.setCoord(i, Double.parseDouble(coords[i]));
                }
                centers.add(p);
                cCoords = sc.nextLine();
            }
            while (sc.hasNextLine()) {
                Point p = new Point(dimension);
                String[] coords = sc.nextLine().split(" ");
                for (int i = 0; i < dimension; i++) {
                    p.setCoord(i, Double.parseDouble(coords[i]));
                }
                data.add(p);
            }
            System.out.println(distortion(data, centers));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /*
    Returns the Squared Error Distortion(data, centers).
    */
    public static double distortion(ArrayList<Point> data, ArrayList<Point> centers) {
        double result = 0;
        for (Point p : data) {
            result += p.sqDistToNearestCenter(centers);
        }
        return result / data.size();

    }

    

}