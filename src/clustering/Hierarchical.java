package clustering;

import java.util.ArrayList;
import java.util.List;

public class Hierarchical {
    private double[][] distMat;
    private List<Cluster> clusters;

    public Hierarchical(double[][] distMat) {
        int n = distMat.length;
        this.distMat = distMat;
        clusters = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            clusters.add(i, new Cluster(i+1));
        }
    }
    
    public Hierarchical(ArrayList<Point> data) {
        int n = data.size();
        this.distMat = dataToDistMat(data);
        clusters = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            clusters.add(i, new Cluster(i+1));
        }
    }
    
    public double[][] dataToDistMat(ArrayList<Point> data){
        int n = data.size();
        double[][] distMat = new double[n][n];
        for(int i = 0; i < n; i++){
            for (int j = i+1; j < n; j++){
                distMat[i][j] = data.get(i).dist(data.get(j));
            }
        }
        return distMat;
    }
    
    public List<Integer> cluster() {
        if (clusters.size() <= 1) return null;
        double minDist = Double.MAX_VALUE;
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < clusters.size(); i++) {
            for (int j = i + 1; j < clusters.size(); j++) {
                double dist = clusters.get(i).distToCluster(clusters.get(j));
                if (dist < minDist) {
                    minDist = dist;
                    index1 = i;
                    index2 = j;
                }
            }
        }
        clusters.get(index1).merge(clusters.get(index2));
        clusters.remove(index2);
        return clusters.get(index1).getPoints();
    }
    
    private class Cluster{
        private List<Integer> points;

        public Cluster(List<Integer> points) {
            this.points = points;
        }
        
        public Cluster(int point) {
            this.points = new ArrayList<>();
            points.add(point);
        }
        
        public List<Integer> getPoints(){
            return points;
        }
        
        public int get(int i) {
            return points.get(i);
        }
        
        public double distToCluster(Cluster c){
            List<Integer> pts = c.getPoints();
            int ptsSize = pts.size();
            int pointsSize = points.size();
            double dist = 0;
            for (int i = 0; i < pointsSize; i++) {
                for (int j = 0; j < ptsSize; j++) {
                    dist += distMat[get(i)-1][c.get(j)-1];
                }
            }
            return dist/(pointsSize*ptsSize);
        }
        
        public void merge(Cluster c) {
            points.addAll(c.getPoints());
        }
    }
}
