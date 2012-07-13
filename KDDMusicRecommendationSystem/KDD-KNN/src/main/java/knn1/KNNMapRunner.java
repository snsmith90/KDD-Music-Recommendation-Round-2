package knn1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapRunner;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;

import CoreTypes.Similarity;
import CoreTypes.Song;
import CoreTypes.Users;

public class KNNMapRunner extends MapRunner<Song, Users, Song, Similarity> {

	private KNNMapper mapper = new KNNMapper();
	private HashMap<Song, Users> SongUsersIndex = new HashMap<Song, Users>();
	private JobConf job;
	private FileSystem hdfs = null;
	private int inputFilesNo = 0;

	public int numFiles;
	public JobConf j;
	public float totalTerms = 0;
	public int ids = 0;
	public ArrayList<Long> dummyIndexMap = new ArrayList<Long>();
	public boolean log;

	@Override
	public void run(RecordReader input, OutputCollector output, Reporter reporter)
			throws IOException {

		/* Build index <song,users> */
		Song song = new Song();
		Users users = new Users();
		while (input.next(song, users))
			SongUsersIndex.put(song, users);

		/* Read other files and compare with my index */

	}
}
