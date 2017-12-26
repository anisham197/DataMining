import java.io.*;
import java.util.*;

public class LinearRegression {
    double M, C;
    ArrayList<Double> x, y;

    public LinearRegression (String filename) {
        M = 0;
        C = 0;
        x = new ArrayList<>();
        y = new ArrayList<>();
        parseCSV(filename);
    }

    public void parseCSV(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while( (line = reader.readLine()) != null ) {
                String row[] = line.split(",");
                x.add(Double.parseDouble( row[0] ));
                y.add(Double.parseDouble( row[1] ));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Double covariance() {
        Double xmean = mean(x);
        Double ymean = mean(y);
        Double result = 0.0;
        for (int i = 0; i < x.size(); i++) {
            result += ( (x.get(i) - xmean) * (y.get(i) - ymean) );
        }
        return result;
    }

    public Double mean(ArrayList<Double> data){
        Double sum = 0.0;
        for (Double point : data) {
            sum += point;
        }
        return sum / data.size();
    }

    public Double variance() {
        Double xmean = mean(x);
        Double result = 0.0;
        for (Double point : x) {
            result += Math.pow(point - xmean, 2);
        }
        return result;
    }

    public void coefficients() {
        M = covariance() / variance(); // covariance(x, y) / variance(x);
        C = mean(y) - M * mean(x); 
    } 

    public double predict(Double x) {
        return ( M * x + C);
    }

    public static void main(String[] args) {
        String filename = "data.csv";
        Double point = 9.0;
        LinearRegression lr = new LinearRegression (filename);
        lr.coefficients();
        System.out.println("For x = " + point + ", y = " + lr.predict(point) );
    }   
}