import java.util.*;

public class Label {
	ArrayList<Record> data;
	HashMap<String, Double> probability;
	double mean, variance, prior, posteriorProb;

	public Label() {
		data = new ArrayList<>();
		probability = new HashMap<>();
		mean = 0.0;
		variance = 0.0;
		prior = 0.0;
		posteriorProb = 0.0;
	}

	void addRecord(Record record) {
		data.add(record);
	}

	void calculateMean() {
		double sum = 0;
		for (Record record : data ){
			sum += record.att1;
		}
		mean = sum / (double)data.size();
	}

	void calculateVariance() {
		double sum = 0;
		for(Record record : data){
			sum += Math.pow(record.att1 - mean, 2);
		}
		variance = sum /(double)( data.size() * (data.size() - 1) );
	}

	void calculateProbablity()	{
		HashMap<String, Integer> frequency = new HashMap<>();
        int count;

       	for (Record record : data) {
            if( frequency.containsKey(record.att2))
                count = frequency.get(record.att2) + 1;
            else
                count = 1;
			frequency.put(record.att2, count);
		}

		for(String attValue: frequency.keySet()){
			probability.put(attValue, ( (double)frequency.get(attValue) / (double)data.size() )  );
		}
	}

	double getProbability(String attValue)	{
		if(probability.containsKey(attValue))	{
			return probability.get(attValue);
		}
		return 0.0;
	}
}