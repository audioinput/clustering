package clustering;
import java.util.HashSet;
import java.util.Set;

/*
 * test git on intelliJ
 */
public class Cluster {
    private Set<Point> points;
    private Point center;
    private int dim;

    /*
    Creates a cluster (ie set of points) in a Euclidean space with dimension dim.
    */
    public Cluster(int dim) {
        points = new HashSet<Point>();
        this.dim = dim;
    }

    /*
    Calculates the new center of a cluster by calculating the centroid.
    */
    public void updateCenter() {
        Point point = new Point(dim);
        for (int i = 0; i < dim; i++){
            double coord = 0;
            for (Point p : points) {
                coord += p.getCoords()[i]/points.size();
            }
            point.getCoords()[i]= coord;
        }
        point.setCluster(this);
        center = point;
    }
    
    public void add(Point p) {
        points.add(p);
    }
    
    public void clear(){
        points.clear();
    }
    
    public void remove(Point p) {
        points.remove(p);
    }
    
    public Set<Point> getPoints() {
        return points;
    }

    public void setPoints(Set<Point> points) {
        this.points = points;
    }
    
    public int getDim() {
        return dim;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    
    
}
