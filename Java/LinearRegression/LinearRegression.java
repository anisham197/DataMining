import java.io.*;
import java.util.*;

public class LinearRegression
{
    double M, C;

    public LinearRegression(){
        M = 0;
        C = 0;
    }

    public double covariance(double[] x, double[] y) {
        double xmean = mean(x);
        double ymean = mean(y);
        double result = 0;
        for (int i = 0; i < x.length; i++) {
            result += ( (x[i] - xmean) * (y[i] - ymean) );
        }
        return result;
    }

    public double mean(double[] data){
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        return sum / data.length;
    }

    public double variance(double[] x)
    {
        double xmean = mean(x);
        double result = 0;
        for (int i = 0; i < x.length; i++) {
            result += Math.pow(x[i] - xmean, 2);
        }
        return result;
    }

    public void coefficients(double[] x, double[] y)
    {
        M = covariance(x,y) / variance(x);
        C = mean(y) - M * mean(x);
    } 

    public double predict(double x){
        return ( x*M + C);
    }

    public static void main(String[] args) {
        double[] x = { 2, 3, 4, 5, 6, 8, 10, 11 };
        double[] y = { 5, 7, 9, 11, 13, 17, 21, 23};

        LinearRegression lr = new LinearRegression();
        lr.coefficients(x,y);
        System.out.println(lr.predict(9));
    }   
}