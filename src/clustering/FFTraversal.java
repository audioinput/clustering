package clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FFTraversal {
    private ArrayList<Point> data;
    private ArrayList<Point> centers = new ArrayList<>();
    private final int amtOfClusters;

    /*
    initializes fields.
    */
    public FFTraversal(ArrayList<Point> data, int amtOfClusters) {
        this.data = data;
        this.amtOfClusters = amtOfClusters;
        centers.add(data.get(0)); //add the first datapoint to the set of centers.
        data.remove(0);
    }
    
    
    /*
    Returns index of the datapoint which is the farthest away from all centers.
    */
    public int findFarthest(){
        double maxdist = 0;
        double dist = 0;
        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            dist = data.get(i).sqDistToNearestCenter(centers);
            if (dist > maxdist){
                index = i;
                maxdist = dist;
            }
        }
        return index;
    }
    
    /*
    Clusters the data by finding the centers (returnvalue).
    */
    public List<Point> cluster() {
        int index;
        while (centers.size() < amtOfClusters) {
            index = findFarthest();
            centers.add(data.get(index));
            data.remove(index);
        }
        return centers;
    }
    
}
