import java.io.*;
import java.util.*;

public class Perceptron {

    int epochs, dimensions;
    double alpha;
    ArrayList<Double[]> x;
    ArrayList<Double> y;
    Double[] weights;

    public static void main(String args[]){
        String filename = "data.csv";
        int epochs = 1000;
        double alpha = 0.01;
        Perceptron p = new Perceptron(epochs, alpha, filename);
        System.out.println("Initial Weights");
        p.printWeights();
        p.learnWeights();
        System.out.println("Final Weights");
        p.printWeights();
        Double[] test = {6.2,3.0,4.8,1.5}; // Expected output = 1;
        System.out.println("Predicted class for test sample: " + p.predict(test));
    }

    public Perceptron(int epochs, double alpha, String filename){
        this.epochs = epochs;
        this.alpha = alpha;
        x = new ArrayList<>();
        y = new ArrayList<>();
        parseCSV(filename);
        dimensions = x.get(0).length;

        weights = new Double[dimensions + 1]; //for bias
        weights[0] = 1.0; //initialising bias
        Random random = new Random();
        for(int i = 1; i < dimensions + 1; i++){
            weights[i] = random.nextDouble(); // inital weights between 0 and 1(exclusive);
        }
    }

    public void parseCSV(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while( (line = reader.readLine()) != null ){
                String row[] = line.split(",");
                dimensions = row.length - 1;
                Double[] row_double = new Double[dimensions];
                for(int i = 0; i < dimensions; i++){
                    row_double[i] = Double.parseDouble(row[i]);
                } 
                x.add(row_double);
                y.add(Double.parseDouble(row[row.length - 1]));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void printWeights() {
        String w = Arrays.toString(weights);
        System.out.println(w.substring(1, w.length() - 1));
        
    }

    public void learnWeights() {
        for(int e = 0 ; e < epochs; e++) {
            for(int i = 0; i < x.size(); i++) {
                Double prediction = predict( x.get(i) );
                Double error = y.get(i) - prediction;
                weights[0] = weights[0] + alpha * error; // Updating bias
                //Updating weights
                for(int d = 1; d < dimensions + 1; d++) {
                    weights[d] = weights[d] + ( alpha * error * x.get(i)[d-1]);
                }
            }    
        }
    }

    public Double predict(Double[] x) {
        Double sum = weights[0]; // bias + weighted sum of features
        for(int i = 1;i < dimensions + 1; i++) {
            sum += weights[i] * x[i-1];
        }
        return activation(sum);
    }

    public double activation(Double x) {
        return (x >= 0) ? 1 : 0;
    }


}