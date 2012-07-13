package CoreTypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

import edu.umd.cloud9.io.map.HashMapWritable;

public class Songs implements Writable {

	private HashMapWritable<IntWritable, Song> songs = new HashMapWritable<IntWritable, Song>();
	private int count = 0;

	public Songs() {
	}

	public Songs(Song song) {
		this.addSong(song);
	}

	public void addSong(Song song) {
		try {
			if (songs.containsKey(song.getID())) {
				songs.get(song.getID()).joinRating(song.getRating(), song.getRatingCount());
			} else {
				songs.put(new IntWritable(song.getID()), song);
			}
			count++;
		} catch (Exception e) {
			System.out.println("");
		}
	}

	public int getCount() {
		return count;
	}

	public Song getSong(int id) {
		return songs.get(id);
	}

	public boolean containsSong(int id) {
		return songs.containsKey(id);
	}

	public Iterator<Song> iterator() {
		return songs.values().iterator();
	}

	public void write(DataOutput out) throws IOException {
		songs.write(out);
		out.writeInt(count);
	}

	public void readFields(DataInput in) throws IOException {
		songs.readFields(in);
		count = in.readInt();
	}
}
