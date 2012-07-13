package knn1;

import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.SequenceFileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import CoreTypes.Song;
import additional.pairRate;
import additional.pairSongs;

public class KNNDriver extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new KNNDriver(), args);
		System.exit(res);
	}

	public int run(String[] args) throws Exception {
		JobConf job = new JobConf();
		job.setJarByClass(KNNDriver.class);

		job.setMapperClass(KNNMapper.class);
		job.setMapOutputKeyClass(pairSongs.class);
		job.setMapOutputValueClass(pairRate.class);
		job.setReducerClass(KNNReducer.class);
		job.setOutputKeyClass(Song.class);
		job.setOutputValueClass(Song.class);

		job.setInputFormat(SequenceFileInputFormat.class);
		SequenceFileInputFormat.addInputPath(job, new Path("sinvertedindex"));
		String outputPathDir = "knnresults";
		Path outputPath = new Path(outputPathDir);
		FileSystem.get(job).delete(outputPath, true);
		FileOutputFormat.setOutputPath(job, outputPath);

		Date startTime = new Date();
		JobClient.runJob(job); // submits job to hadoop
		Date end_time = new Date();
		System.out.println("KNN calculations took " + (end_time.getTime() - startTime.getTime())
				/ (float) 1000.0 + " seconds.");
		return 0;
	}

	public void produceTopK() {
	}

	public void computeSimilarities(String[] Args) throws Exception {

		JobConf job = new JobConf();
		job.setJarByClass(KNNDriver.class);

		job.setMapperClass(KNNMapper.class);
		job.setMapOutputKeyClass(pairSongs.class);
		job.setMapOutputValueClass(pairRate.class);
		job.setReducerClass(KNNReducer.class);
		job.setOutputKeyClass(Song.class);
		job.setOutputValueClass(Song.class);

		job.setInputFormat(SequenceFileInputFormat.class);
		SequenceFileInputFormat.addInputPath(job, new Path("sinvertedindex"));
		String outputPathDir = "knnresults";
		Path outputPath = new Path(outputPathDir);
		FileSystem.get(job).delete(outputPath, true);
		FileOutputFormat.setOutputPath(job, outputPath);

		Date startTime = new Date();
		JobClient.runJob(job); // submits job to hadoop
		Date end_time = new Date();
		System.out.println("KNN calculations took " + (end_time.getTime() - startTime.getTime())
				/ (float) 1000.0 + " seconds.");
		return;
	}
}