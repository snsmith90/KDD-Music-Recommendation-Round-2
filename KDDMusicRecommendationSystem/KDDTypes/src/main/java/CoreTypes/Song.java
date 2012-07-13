package CoreTypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class Song implements WritableComparable<Song> {

	private int id;
	private int ratingsSum; // dum of ratings
	private int ratingsCount; // count users rating this song
	private Similarities similarities = new Similarities();

	public Song() {
	}

	public Song(int id, int rating) {
		this.id = id;
		this.ratingsSum = rating;
		ratingsCount = 1;
	}

	public Song(int id) {
		this.id = id;
		this.ratingsSum = 0;
		ratingsCount = 0;
	}

	@Override
	public String toString() {
		return (id + " ");
	}

	public void setID(int num) {
		this.id = num;
	}

	// public void setTotalRating(int num) {
	// this.ratingsSum = num;
	// }

	public void addRating(int rating) {
		ratingsSum += rating;
		ratingsCount++;
	}

	public void joinRating(int rating, int count) {
		ratingsSum += rating;
		ratingsCount += count;
	}

	public int getRatingCount() {
		return ratingsCount;
	}

	public int getRating() {
		return ratingsSum;
	}

	public int getID() {
		return id;
	}

	public Similarities getNeighborhood() {
		if (similarities == null) {
			similarities = new Similarities();
		}
		return similarities;
	}

	public void addToNeighborhood(Similarity sim) {
		if (similarities == null) {
			similarities = new Similarities();
		}
		similarities.insert(sim);
	}

	public double getSimilarity(Song s) {
		if (similarities == null) {
			similarities = new Similarities();
		}
		return similarities.getSimilarity(s);
	}

	public void print() {
		if (similarities == null) {
			similarities = new Similarities();
		}
		System.out.println(id);
		similarities.print();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Song) {
			Song other = (Song) o;
			return (id == other.id);
		}
		return false;
	}

	// @Override
	// public int hashCode() {
	// int hash = 7;
	// hash = 97 * hash + this.id;
	// return hash;
	// }

	public void write(DataOutput out) throws IOException {
		out.writeInt(id);
		out.writeInt(ratingsSum);
		out.writeInt(ratingsCount);
		similarities.write(out);
	}

	public void readFields(DataInput in) throws IOException {
		id = in.readInt();
		ratingsSum = in.readInt();
		ratingsCount = in.readInt();
		similarities.readFields(in);
	}

	public int compareTo(Song other) {
		if (this.id < other.id)
			return -1;
		else if (this.id > other.id)
			return 1;
		else
			return 0;
	}
}
