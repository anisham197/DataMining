public class Record {
    
	Double att1;
	String att2;
	Integer label;
	Record(Double att1, String att2, Integer label)	{
		this.att1 = att1;
		this.att2 = att2;
		this.label = label;
	}

	void printRecord(){
		System.out.println(this.att1 + "\t" + this.att2 + "\t" + this.label);
	}
}