import java.io.*;
import java.util.*;

public class ConfusionMatrix {

    double tp, tn, fp, fn;

    public void enterMatrix() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter No. of True Positives");
        tp = sc.nextDouble();
        System.out.println("Enter No. of False Positives");
        fp = sc.nextDouble();
        System.out.println("Enter No. of True Negatives");
        tn = sc.nextDouble();
        System.out.println("Enter No. of False Negatives");
        fn = sc.nextDouble();
    }

    public void printMatrix() {
        System.out.println("\tPredicted");
        System.out.println("\tPos\tNeg");
        System.out.println("Pos\t" + tp + "\t" + fn + "\tActual");      
        System.out.println("Neg\t" + fp + "\t" + tn);
    }

    public void calculate(){
        double sensitivity = tp / (tp + fn );     
        double specificity = tn / (tn + fp) ;

        double recall = sensitivity;
        double precision = tp / (tp + fp) ;

        double accuracy = (tp + tn) / ( tp + fp + tn + fn ); 

        System.out.println("Sensitivity: " + sensitivity);
        System.out.println("Specificity: " + specificity);
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);
        System.out.println("Accuracy: " + accuracy);
    }

    public static void main(String args[]){
        ConfusionMatrix cm = new ConfusionMatrix();
        cm.enterMatrix();
        cm.printMatrix();
        cm.calculate();
    }
}