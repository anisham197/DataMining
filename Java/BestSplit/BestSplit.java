import java.io.*;
import java.util.*;

public class BestSplit {

    ArrayList<String[]> data;
    HashMap<String, Integer> parent;
    HashMap<String, HashMap<String, Integer>> a1, a2;
    final Integer A1 = 0, A2 = 1, CLASS = 2;

    public BestSplit(String filename) {
        data = new ArrayList<>();
        parent = new HashMap<>();
        a1 = new HashMap<>();
        a2 = new HashMap<>();
        parseCSV(filename);
    }

    public void parseCSV(String filename){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while( (line = reader.readLine()) != null ){
                String row[] = line.split(",");
                data.add(row);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]){
        String filename = "data.csv";
        BestSplit bs = new BestSplit(filename);
        bs.chooseAttribute();
    }

    public void chooseAttribute(){
        for (String[] row : data){
            parentTable(row);
            attrTable(a1, row, A1);
            attrTable(a2, row, A2);
        }

        Double giniParent = gini(parent);        
        Double gain_gini_A1 = gain(giniParent, a1, true);
        Double gain_gini_A2 = gain(giniParent, a2, true);
        
        Double entropyParent = entropy(parent);
        Double gain_entropy_A1 = gain(entropyParent, a1, false);
        Double gain_entropy_A2 = gain(entropyParent, a2, false);

        System.out.println("Parent Count:");
        System.out.println(parent);

        System.out.println("Attribute 1 Table:");
        System.out.println(a1);
        System.out.println("Attribute 2 Table:");
        System.out.println(a2);

        System.out.println("I(Parent) - Gini:" + giniParent);        
        System.out.println("Gain - Gini");
        System.out.println("Attribute 1: " + gain_gini_A1 );        
        System.out.println("Attribute 2: " + gain_gini_A2 );

        System.out.println("I(Parent) - Entropy: " + entropyParent);
        System.out.println("Gain - Entropy");
        System.out.println("Attribute 1: " + gain_entropy_A1 );        
        System.out.println("Attribute 2: " + gain_entropy_A2 );

        System.out.println("Using Gini Index, Best Attribute to Split By: " + ( (gain_gini_A1 > gain_gini_A2) ? "A1" : "A2") );        
        System.out.println("Using Entropy, Best Attribute to Split By: " + ( (gain_entropy_A1 > gain_entropy_A2) ? "A1" : "A2") );
    }

    public void parentTable(String[] row){
        Integer count = parent.get(row[CLASS]);
        if(count == null){
            count = 0;
        }
        count ++;
        parent.put(row[CLASS],count);
    }

    public void attrTable(HashMap<String, HashMap<String,Integer>> table, String[] row, Integer index){
        HashMap<String,Integer> map = table.get(row[index]);
        if (map == null){
            map = new HashMap<>();
        }
        Integer count = map.get(row[CLASS]);
        if(count == null){
            count = 0;
        }
        count ++;
        map.put(row[CLASS], count);
        table.put(row[index], map);
    }

    public Double gini(HashMap<String,Integer> map) {
        double total = 0;
        double gini = 1;
        for (String s : map.keySet()){
            total += map.get(s);
        }
        for (String s : map.keySet()){
            gini -= Math.pow((map.get(s)/total), 2);
        }
        return gini;
    }

    public Double entropy(HashMap<String,Integer> map) {
        double total = 0;
        double entropy = 0;
        for (String s : map.keySet()){
            total += map.get(s);
        }
        for (String s : map.keySet()){
            Double temp = map.get(s)/total;
            entropy -= temp*(Math.log10(temp)/Math.log10(2.0));
        }
        return entropy;
    }

    public Double gain(Double parentI, HashMap<String,HashMap<String,Integer>> table, Boolean isGini){
        Double gain = parentI;
        Double total = (double)data.size();
        Double gainSuffix = 0.0;
        HashMap<String,Integer> map;

        for (String node : table.keySet()){
            map = table.get(node);
            Double innerI = isGini ? gini(map) : entropy(map);
            Double node_total = 0.0;
            for(Integer i : map.values())
                node_total += i;
            gainSuffix += (node_total/total)*innerI;
        }
        gain -= gainSuffix;
        return gain;
    }
}