package CoreTypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

import org.apache.hadoop.io.Writable;

import edu.umd.cloud9.io.array.ArrayListWritable;

public class Similarities implements Writable {

	private int k;
	private ArrayListWritable<Similarity> neighbors = new ArrayListWritable<Similarity>();

	public Similarities() {
		k = 10;// Main.getOptions().getK();
	}

	public int getK() {
		return k;
	}

	public void insert(Similarity is) {
		int index = Collections.binarySearch(neighbors, is);
		if (index < 0) {
			neighbors.add(-index - 1, is);
		}
		while (neighbors.size() > k) {
			neighbors.remove(0);
		}
	}

	public void print() {
		for (Similarity is : neighbors) {
			System.out.println("-" + "\t" + is.getNeighborSong().getID() + "\t"
					+ is.getSimilarity());
		}
	}

	public double getSimilarity(Song song) {
		for (Similarity sim : neighbors) {
			if (song.getID() == sim.getNeighborSong().getID()) {
				return sim.getSimilarity();
			}
		}
		return 0;
	}

	public boolean contains(Song song) { // sequential search
		for (Similarity sim : neighbors) {
			if (song.getID() == sim.getNeighborSong().getID()) {
				return true;
			}
		}
		return false;
	}

	public Iterator<Similarity> iterator() {
		return neighbors.iterator();
	}

	public void write(DataOutput out) throws IOException {
		out.writeInt(k);
		neighbors.write(out);
	}

	public void readFields(DataInput in) throws IOException {
		k = in.readInt();
		neighbors.readFields(in);
	}
}
