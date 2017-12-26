import java.io.*;
import java.util.*;

public class KMeans {

    int k, maxIters, dimensions;    
    ArrayList<Double[]> centroids, data;
    HashMap<Integer, ArrayList<Double[]>> finalClusters;

    public static void main (String[] args){
        String filename = "data.csv";
        int clusters = 3, max_iterations = 100;
        KMeans km = new KMeans(clusters, max_iterations, filename);

        System.out.println("Data Length: " + km.data.size());
        System.out.println("Dimensions: " + km.dimensions); 
        System.out.println("No. of clusters: " + km.k);

        km.initCentroids();
        System.out.println("Initial centroids are: ");
        km.printData(km.centroids);

        km.findCentroids();
        System.out.println("Final centroids are: ");
        km.printData(km.centroids);

        km.printClusters();
        Double[] test = {2.0, 3.0, 4.5};
        km.predict(test);
    }

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
                Double[] row_double = new Double[row.length];
                int i = 0;
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

    public void printData(ArrayList<Double[]> data) {     
        for(Double[] row : data){
            String row_d = Arrays.toString(row);
            System.out.println(row_d.substring(1, row_d.length() - 1));
        }
    }

    public void findCentroids() {
        HashMap<Integer, ArrayList<Double[]>> clusterData = new HashMap<>();
        for (int i = 0; i < maxIters; i++) {
            clusterData = new HashMap<>();
            for (int c = 0; c < k; c++){
                clusterData.put(c, new ArrayList<Double[]>() );
            }

            for (Double[] point : data){
                ArrayList<Double> distance = findDistanceFromCentroids(point);
                int cluster_index = distance.indexOf(Collections.min(distance));
                ArrayList<Double[]> cluster = clusterData.get(cluster_index);
                cluster.add(point);
                clusterData.put(cluster_index, cluster);
            }
            recomputeCentroids(clusterData);
        }
        finalClusters = clusterData;
    }

    public ArrayList<Double> findDistanceFromCentroids(Double[] point) {
        ArrayList<Double> distance = new ArrayList<>();
        for(Double[] c : centroids) {  
            distance.add( EuclideanDistance(point, c) );
        }
        return distance;
    }

    public Double EuclideanDistance(Double[] first, Double[] second){
        Double sum = 0.0;
        for( int i = 0; i < dimensions; i++ ){
            sum += Math.pow( ( first[i] - second[i] ) , 2);
        }
        return Math.sqrt(sum);
    }

    public void printClusters() {
        for(int i = 0; i < k; i++){
            System.out.println("Points in Cluster " + i);
            ArrayList<Double[]> data = finalClusters.get(i);
            printData(data);
        }
    }

    public void recomputeCentroids(HashMap<Integer, ArrayList<Double[]>> clusterData){        
        centroids = new ArrayList<>();
        for(int i = 0; i < k; i++){
            ArrayList<Double[]> clusterPoints = clusterData.get(i);
            
            Double[] newCentroid = new Double[dimensions];
            Arrays.fill(newCentroid, 0.0);
            for(Double[] point : clusterPoints){
                for(int d = 0; d < dimensions; d++) {
                    newCentroid[d] = newCentroid[d] + point[d];
                }
            }

            for(int d = 0; d < dimensions; d++){
                newCentroid[d] = newCentroid[d] / clusterPoints.size();
            }

            centroids.add(newCentroid);
        }
    }

    public void predict(Double[] test) {
        ArrayList<Double> distance = findDistanceFromCentroids(test);
        int cluster_index = distance.indexOf(Collections.min(distance));
        System.out.println("Datapoint belongs to cluster: " + cluster_index);
    }
}