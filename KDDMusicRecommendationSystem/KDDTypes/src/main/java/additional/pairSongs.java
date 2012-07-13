package additional;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

import CoreTypes.Song;


public class pairSongs implements WritableComparable<pairSongs> {

	public Song song1;
	public Song song2;

	public pairSongs() {
	}

	public pairSongs(Song s1, Song s2) {
		this.song1 = s1;
		this.song2 = s2;
	}

	public pairSongs(int id1, int rating1, int id2, int rating2) {
		this.song1 = new Song(id1, rating1);
		this.song2 = new Song(id2, rating2);
	}

	@Override
	public String toString() {
		return (song1.getID() + " " + song2.getID() + " ");
	}

	public void setID(int id1, int id2) {
		this.song1.setID(id1);
		this.song2.setID(id2);
	}

	public void write(DataOutput out) throws IOException {
		this.song1.write(out);
		this.song2.write(out);
	}

	public void readFields(DataInput in) throws IOException {
		this.song1.readFields(in);
		this.song2.readFields(in);
	}

	public int compareTo(pairSongs other) {
		if (this.song1.getID() < other.song1.getID())
			return -1;
		else if (this.song1.getID() > other.song1.getID())
			return 1;
		else {
			if (this.song2.getID() < other.song2.getID())
				return -1;
			else if (this.song2.getID() > other.song2.getID())
				return 1;
			return 0;
		}
	}
}
