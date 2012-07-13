package CoreTypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class Similarity implements WritableComparable<Similarity> {

	private Song neighbor;
	private double similarity;

	public Similarity(Song neighbor, double s) {
		if (neighbor == null)
			System.out.println("Setting the neighbor as null");
		this.neighbor = neighbor;
		similarity = s;
	}

	public Similarity(String idAndValue) {
		String[] split = idAndValue.split(" ");
		this.neighbor = new Song(Integer.valueOf(split[0]));
		similarity = Double.valueOf(split[1]);
	}

	public double getSimilarity() {
		return similarity;
	}

	public Song getNeighborSong() {
		return neighbor;
	}

	public int compareTo(Similarity s) {
		double sim_other = s.getSimilarity();
		if (similarity > sim_other) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public String toString() {
		return neighbor.getID() + " " + similarity;
	}

	public void write(DataOutput out) throws IOException {
		neighbor.write(out);
		out.writeDouble(similarity);
	}

	public void readFields(DataInput in) throws IOException {
		neighbor.readFields(in);
		similarity = in.readDouble();
	}
}
