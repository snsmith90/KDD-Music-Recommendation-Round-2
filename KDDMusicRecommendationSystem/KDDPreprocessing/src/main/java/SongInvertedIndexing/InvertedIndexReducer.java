package SongInvertedIndexing;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import CoreTypes.Song;
import CoreTypes.User;
import CoreTypes.Users;



/**
 * The reducer takes in: KEY:&ltsongID&gt, VALUE: [&ltuser&gt]+ <br>
 * and output: KEY:&ltsongID&gt, VALUE: &ltusers&gt <br>
 * where users is a concatenation of the shuffled &ltuser&gts that rated the
 * same &ltsongID&gt.
 * 
 * @author Maha
 * 
 */
public class InvertedIndexReducer extends MapReduceBase implements Reducer<Song, User, Song, Users> {

	public void reduce(Song song, Iterator<User> nextUser, OutputCollector<Song, Users> output,
			Reporter reporter) throws IOException {
		Users users = new Users();

		while (nextUser.hasNext())
			users.addUser(nextUser.next());
		output.collect(song, users);
	}
}
