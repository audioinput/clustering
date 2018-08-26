package clustering;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class KMeans {
    private Cluster[] clusters;
    private final Point[] data;
    private Point[] centers;
    private final int amtOfClusters;

    /*
    Initializes all fields.
    */
    public KMeans(ArrayList<Point> data, int amtOfClusters) {
        clusters = new Cluster[amtOfClusters];
        this.data = Arrays.copyOf(data.toArray(), data.toArray().length, Point[].class);
        for (int i = 0; i < amtOfClusters; i++) {
            clusters[i] = new Cluster(this.data[0].getCoords().length);
        }
        centers = new Point[amtOfClusters];
        this.amtOfClusters = amtOfClusters;
    }
    
    /*
    Runs the actual algorithm.
    */
    public Point[] cluster(){
       int k=0;
        if (data.length > amtOfClusters)
        {
            centers = new Point[amtOfClusters];
            for (int i = 0; i < amtOfClusters; i++){
                centers[i] = data[i];
                    System.out.println(centers[i]); //print the initial centers
                clusters[i].add(centers[i]);
            }
            for (Point p : data) 
                addToNearestCluster(p);
            while(!done(clusters, centers)) {
                k++;
                for (int i = 0; i < clusters.length; i++){
                    centers[i]=findCenter(clusters[i]);
                    System.out.println(centers[i]); //print the centers after each iteration
                }
                System.out.println("");
                for (Point p : data) 
                    addToNearestCluster(p);
            }
        }
        System.out.println("Iterations = "+k); //print total number of iterations
        return centers;
    }
    
    /*
    Checks if the algorithm should stop
    (ie every center is the centroid of its cluster).
    */
    public boolean done(Cluster[] clusts, Point[] cents){
        int dim = cents[0].getCoords().length;
        for (int i = 0; i < clusts.length; i++) {
            for (int j = 0; j < dim; j++) {
                if (findCenter(clusters[i]).getCoords()[j]!=centers[i].getCoords()[j]) return false;
            }
        }
        return true;
    }
    
    /*
    Finds the center from a given cluster.
    */
    public Point findCenter(Cluster c){
        c.updateCenter();
        return c.getCenter();   
    }
    
    /*
    Finds the nearest cluster and adds the given point to it.
    */
    public void addToNearestCluster(Point p) {
        if (p.getCluster() != null)
            p.getCluster().remove(p);
        double d = Double.MAX_VALUE;
        int j = 0;
        for (int i = 0; i < amtOfClusters; i++) {
            double dist = centers[i].squaredDist(p);
            if (dist < d) {
                j = i;
                d = dist; 
            }
        }
        p.setCluster(clusters[j]);
        clusters[j].add(p);
        
    }

    /*
    Some getters.
    */
    public Cluster[] getClusters() {
        return clusters;
    }
    
    public Point[] getCenters() {
        return centers;
    }
    
}
