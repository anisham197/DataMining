import java.io.*;
import java.util.*;

public class KMeans {

    int k, maxIters, dimensions;
    ArrayList<Double[]> centroids, data;
    HashMap<Integer, ArrayList<Double[]>> finalClusters;

    public KMeans(int k, int maxIters, String filename) {
        this.k = k ;
        this.maxIters = maxIters;
        centroids = new ArrayList<>();
        data = new ArrayList<>();
        parseCSV(filename);
        this.dimensions = data.get(0).length;

    }

    public void parseCSV(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while( (line = reader.readLine()) != null ){
                String row[] = line.split(",");
                int i = 0;
                Double row_double[] = new Double[3];
                for(String attr : row){
                    row_double[i] = Double.parseDouble(attr);
                    i++;
                } 
                data.add(row_double);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void initCentroids() {
        for(int i = 0; i  < k; i++) {
            centroids.add(data.get(i));
        }
    }

    public void findCentroids() {
        for (int i = 0; i < maxIters; i++) {
            HashMap<Integer, ArrayList<Double[]> > clusterData = new HashMap<>();
            for (int c = 0; c < k; c++){
                clusterData.put(c, new ArrayList<Double[]>() );
            }

            for (Double[] point : data){
                ArrayList<Double> distanceFromAllCentroids = findDistanceFromAllCentroids(point);
                int cluster_index = findCluster(distanceFromAllCentroids);
                ArrayList<Double[]> cluster = clusterData.get(cluster_index);
                cluster.add(point);
                clusterData.put(cluster_index, cluster);
            }

            recomputeCentroid(clusterData);
            finalClusters = clusterData;
        }
    }

    public ArrayList<Double> findDistanceFromAllCentroids(Double[] point) {
        ArrayList<Double> distance = new ArrayList<>();
        for(Double[] c : centroids) {
            Double dist = EuclideanDistance(point, c);           
            // Double dist = ManhattanDistance(point, c);
            distance.add(dist);
        }
        return distance;
    }

    public Double EuclideanDistance(Double[] first, Double[] second){
        Double sum = 0.0;
        for( int i = 0; i < first.length; i++ ){
            sum += Math.pow( (first[i]-second[i]) , 2);
        }
        return Math.sqrt(sum);
    }

    public Double ManhattanDistance(Double[] first, Double[] second){
        Double sum = 0.0;
        for( int i = 0; i < first.length; i++ ){
            sum += Math.abs( (first[i]-second[i]) );
        }
        return sum;
    }
   
    public int findCluster(ArrayList<Double> distanceFromAllCentroids) {
        int index = -1;
        Double min = Double.MAX_VALUE;
        for (Double distance: distanceFromAllCentroids){
            if (distance < min){
                min = distance;
                index = distanceFromAllCentroids.indexOf(distance);
            }
        }
        return index;
    }

    public void recomputeCentroid(HashMap<Integer,ArrayList<Double[]>> clusterData){        
        for(Map.Entry<Integer, ArrayList<Double[]>> entry : clusterData.entrySet()) {
            ArrayList<Double[]> clusterPoints = entry.getValue();
            int clusterIndex = entry.getKey();

            Double[] newCentroid = new Double[dimensions];
            for(Double[] point : clusterPoints){
                for(int d = 0; d < dimensions; d++){
                    newCentroid[d] = newCentroid[d] + point[d];
                }
            }

            for(int d = 0; d < dimensions; d++){
                newCentroid[d] /= clusterPoints.size();
            }

            centroids.add(clusterIndex, newCentroid);
        }
    }

    public void printClusters() {
        for(Map.Entry<Integer, ArrayList<Double[]>> entry : finalClusters.entrySet()) {
            System.out.print( entry.getKey() + " : " );
            ArrayList<Double[]> clusterPoints = entry.getValue();
            for(Double[] point : clusterPoints){
                System.out.print(Arrays.toString(point) + " , ");
            }
            System.out.println();
        }
    }
    
    public void predict(Double[] test) {
        ArrayList<Double> distanceFromAllCentroids = findDistanceFromAllCentroids(test);
        int cluster_index = findCluster(distanceFromAllCentroids);
        System.out.println("Datapoint belongs to cluster: " + cluster_index);
    }

    public static void main(String[] args)throws IOException {
        String filename = "data.csv";
        KMeans km = new KMeans(3, 100, filename);
        km.initCentroids();
        km.findCentroids();
        km.printClusters();
        Double[] test = {2.0, 3.0, 4.5};
        km.predict(test);
    }
    
}
