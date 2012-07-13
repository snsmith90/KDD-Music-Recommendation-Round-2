package CoreTypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class User implements Writable {

	private int id;
	private int myRatingsNo = 0;
	private int myRatingsSum = 0;
	private double myAvgRating = -1;
	private Songs mySongs = new Songs();

	public User() {
	}

	public User(int id) {
		this.id = id;
		// ratings = new Songs();
	}

	public User(int id, int num) {
		this.id = id;
		this.myRatingsNo = num;
	}

	public void setNumRatings(int num) {
		this.myRatingsNo = num;
	}

	public void setAvgRating(double rating) {
		this.myAvgRating = rating;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public Songs getSongs() {
		return mySongs;
	}

	public int getRating(Song song) {
		return getRating(song.getID());

	}

	public int getRating(int id) {
		return mySongs.getSong(id).getRating();
	}

	public void addSong(Song song) {
		mySongs.addSong(song);
		myRatingsSum += song.getRating();
	}

	public double getAvgRating() {
		return myAvgRating;
	}

	public boolean isRated(Song song) {
		return rated(song.getID());
	}

	public boolean rated(int id) {
		return mySongs.containsSong(id);
	}

	public void write(DataOutput out) throws IOException {

		out.writeInt(this.id);
		out.writeInt(this.myRatingsNo);
		out.writeInt(myRatingsSum);
		out.writeDouble(myAvgRating);
		mySongs.write(out);
	}

	public void readFields(DataInput in) throws IOException {
		id = in.readInt();
		myRatingsNo = in.readInt();
		myRatingsSum = in.readInt();
		myAvgRating = in.readDouble();
		mySongs.readFields(in);
	}
}
