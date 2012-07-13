package knn1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Reader;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import CoreTypes.Song;
import CoreTypes.Songs;
import CoreTypes.User;
import CoreTypes.Users;
import additional.pairRate;
import additional.pairSongs;

public class KNNMapper extends MapReduceBase implements Mapper<Song, Users, pairSongs, pairRate> {

	// private HashMap<Song, Users> SongUsersIndex = new HashMap<Song, Users>();
	private HashMap<User, Songs> UserSongsIndex = new HashMap<User, Songs>();
	// private HashSet<User> nextUsers = new HashSet<User>();

	private JobConf job;
	private FileStatus[] fstat = null;
	private int numInputFiles = 0;
	private FileSystem hdfs = null;

	// private OutputCollector<pairSongs, pairRate> collector = null;

	@Override
	public void configure(JobConf job) {
		// this.job = job;
		// String inputFile = job.get("map.input.file");
		// Path inputDir = (new Path(inputFile)).getParent();
		// numInputFiles = setupHDFS(inputDir);
		// System.out.println("Num of files is " + numInputFiles);
	}

	public void map(Song song, Users users, OutputCollector<pairSongs, pairRate> output,
			Reporter reporter) throws IOException {
		if (true) {
			output.collect(new pairSongs(), new pairRate()); // remove
			return;
		}
		// collector = output; // think about later
		// SongUsersIndex.put(song, users);

		Iterator<User> usrs = users.iterator();
		while (usrs.hasNext()) {
			User nextUser = usrs.next();
			if (UserSongsIndex.containsKey(nextUser))
				UserSongsIndex.get(nextUser).addSong(song);
			else
				UserSongsIndex.put(nextUser, new Songs(song));
		}
		// collector.collect(new pairSongs(), new pairRate()); // remove
		// addUsersToSet(users);
	}

	// public void addUsersToSet(Users users) {
	// Iterator<User> usrs = users.iterator();
	// while (usrs.hasNext())
	// nextUsers.add(usrs.next());
	// }

	@Override
	public void close() {

		if (true)
			return;
		Reader reader = null;

		for (int i = 0; i < numInputFiles; i++) {
			if ((reader = getReader(i)) == null)
				continue;
			else
				processFile(reader, i);
		}
	}

	public int setupHDFS(Path inputDir) {

		try {
			hdfs = FileSystem.get(job);
			fstat = hdfs.listStatus(inputDir);
			return (fstat.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Reader getReader(int fileNo) {
		Reader reader = null;
		try {
			Path otherPath = fstat[fileNo].getPath();
			System.err.println("Reader path is " + otherPath.getName());
			if (!otherPath.getName().contains("_"))
				reader = new SequenceFile.Reader(hdfs, otherPath, job);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reader;
	}

	/**
	 * @param reader
	 *            : pointer to the current file to be read
	 * @throws IOException
	 */
	public void processFile(Reader reader, int i) {

		Song otherSong = new Song();
		Users value = new Users();
		Iterator<User> otherSongUsers = null;
		Iterator<Song> songItr = null;

		reader = getReader(i);
		if (reader == null)
			return;// remove
		try {
			/* Read other songID:user list */
			while (reader.next(otherSong, value)) {
				otherSongUsers = value.iterator();
				/* Traverse users for the other song */
				while (otherSongUsers.hasNext()) {
					User otherUser = otherSongUsers.next();
					if (UserSongsIndex.containsKey(otherUser)) {
						songItr = UserSongsIndex.get(otherUser).iterator();
						while (songItr.hasNext()) {
							Song myUserSong = songItr.next();
							if (myUserSong.compareTo(otherSong) == -1)
								;
							// collector.collect(
							// new pairSongs(myUserSong, otherSong),
							// new pairRate((otherUser.getRating(myUserSong) -
							// otherUser
							// .getAvgRating()),
							// (otherUser.getRating(otherSong) - otherUser
							// .getAvgRating())));
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
