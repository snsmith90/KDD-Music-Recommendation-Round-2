package UserInvertedIndexing.copy;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import CoreTypes.Song;
import CoreTypes.User;



/**
 * The map class reads in an input of the following format:
 * <p>
 * &ltuserID&gt | &ltnumber-of-ratings&gt <br>
 * &ltsongID&gt \t &ltrate&gt <br>
 * &ltsongID&gt \t &ltrate&gt <br>
 * </p>
 * <p>
 * then produce for each user the songs that he has rated as: &ltUserID&gt \t
 * &ltsong&gt<br>
 * 
 * @author Maha
 * 
 */
public class InvertedIndexMapper extends MapReduceBase implements Mapper<Object, Text, Song, User> {

	private User user;
	private Song song;

	public void map(Object unused, Text line, OutputCollector<Song, User> output, Reporter reporter)
			throws IOException {

		Boolean barExists = line.toString().contains("|");
		StringTokenizer str = new StringTokenizer(line.toString(), " |\t");

		if (barExists)
			/* Read userID and number of ratings for that user */
			user = new User(Integer.parseInt(str.nextToken()), Integer.parseInt(str.nextToken()));
		else {
			song = new Song(Integer.parseInt(str.nextToken()), Integer.parseInt(str.nextToken()));
			user.addSong(song);
			output.collect(song, user);
		}
	}
}
