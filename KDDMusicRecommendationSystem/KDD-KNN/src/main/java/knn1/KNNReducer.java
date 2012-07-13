package knn1;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import CoreTypes.Song;
import additional.pairRate;
import additional.pairSongs;

public class KNNReducer extends MapReduceBase implements Reducer<pairSongs, pairRate, Song, Song> {

	public void reduce(pairSongs pairSongs, Iterator<pairRate> nextRatings,
			OutputCollector<Song, Song> output, Reporter reporter) throws IOException {
		double num = 0;
		double din1 = 0;
		double din2 = 0;
		while (nextRatings.hasNext()) {
			pairRate pr = nextRatings.next();
			num += pr.getx() * pr.gety();
			din1 += (pr.getx() * pr.getx());
			din2 += (pr.gety() * pr.gety());
		}
		double sim = num / Math.sqrt(din1 * din2);
		if (sim > 0)
			output.collect(pairSongs.song1, pairSongs.song2);
	}
}
