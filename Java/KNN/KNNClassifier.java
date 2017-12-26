import java.io.*;
import java.util.*;

public class KNNClassifier {
    int k, dimensions;
    ArrayList<Double[]> data;
    ArrayList<DistanceID> distances;
    Integer neighbours[];

    public KNNClassifier(String filename, int k){
        data = new ArrayList<>();
        distances = new ArrayList<>();
        neighbours = new Integer[k];
        this.k = k;
        parseCSV(filename);
        dimensions = data.get(0).length - 1;
    }

    public void parseCSV(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while( (line = reader.readLine()) != null ){
                String row[] = line.split(",");
                int i = 0;
                Double row_double[] = new Double[row.length];
                for(String attr : row){
                    row_double[i] = Double.parseDouble(attr);
                    i++;
                } 
                data.add(row_double);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void calculateDistance(Double new_row[] ){
        for ( int i = 0; i < data.size(); i++){
            Double point[] = data.get(i);
            Double dist = EuclideanDistance(point, new_row);
            distances.add(new DistanceID(dist, i));           
        }
    }

    public double EuclideanDistance(Double[] first, Double[] second){
        double sum = 0;
        for( int i = 0; i < dimensions; i++ ){
            sum += Math.pow( (first[i]-second[i]) , 2);
        }
        return Math.sqrt(sum);
    }

    public void nearestNeighbours() {
        Collections.sort(distances);
        for(int i = 0; i < k; i++){
            neighbours[i] = distances.get(i).id;
        }
    }

    public Double predict() {
        HashMap<Double, Integer> classCount = new HashMap<>();
        int label_index = data.get(0).length - 1;
        for(int i = 0; i < k; i++){
            Double label = data.get(neighbours[i])[label_index];
            if ( ! classCount.containsKey(label))
                classCount.put(label, 1);
            else 
                classCount.put(label, classCount.get(label) + 1);
        }

        Integer max = -1;
        Double class_label = -1.0;
        for (Double key : classCount.keySet()) {
            if ( classCount.get(key) > max) {
                max = classCount.get(key);
                class_label = key;
            }
        }
        return class_label;
    }
    
    public static void main(String[] args) {
        String filename = "data.csv";
        int k = 3; 
        KNNClassifier knn = new KNNClassifier(filename, k);

        Double[] new_row = {6.0,3.5,4.1};
        knn.calculateDistance(new_row);
        knn.nearestNeighbours();
        Double label = knn.predict();

        System.out.println("Class of given point is: " + label);
    }
}