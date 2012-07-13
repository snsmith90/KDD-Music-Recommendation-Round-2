package UserInvertedIndexing.copy;

import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


import CoreTypes.Song;
import CoreTypes.User;
import CoreTypes.Users;
import SongInvertedIndexing.NonSplitableTextInputFormat;

public class UserInvertedIndexDriver extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new UserInvertedIndexDriver(), args);
		System.exit(res);
	}

	public int run(String[] args) throws Exception {

		JobConf job = new JobConf();
		job.setJarByClass(UserInvertedIndexDriver.class);

		job.setMapperClass(InvertedIndexMapper.class);
		job.setMapOutputKeyClass(Song.class);
		job.setMapOutputValueClass(User.class);

		job.setReducerClass(InvertedIndexReducer.class);
		job.setOutputKeyClass(Song.class);
		job.setOutputValueClass(Users.class);

		// try MultiFileInputFormat
		job.setInputFormat(NonSplitableTextInputFormat.class);
		NonSplitableTextInputFormat.addInputPath(job, new Path(args[0]));
		String outputPathDir = "sinvertedindex";
		Path outputPath = new Path(outputPathDir);
		FileSystem.get(job).delete(outputPath, true);
		// Change to FileOutputFormat to see output
		job.setOutputFormat(SequenceFileOutputFormat.class);
		SequenceFileOutputFormat.setOutputPath(job, outputPath);

		Date startTime = new Date();
		JobClient.runJob(job); // submits job to hadoop
		Date end_time = new Date();
		System.out.println("Preprocessing took " + (end_time.getTime() - startTime.getTime())
				/ (float) 1000.0 + " seconds.");
		return 0;
	}
}