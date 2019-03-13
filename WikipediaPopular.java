
/**
 * CMPT 431 Distributed Systems - Assignment 04
 * 
 * GROUP:
 * Ahsan Naveed (anaveed)
 * Amandeep Sindhar (asindhar)
 */
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * WikipediaPopular
 */
public class WikipediaPopular {

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

        private Text popularPageTimeStamp = new Text();
        private IntWritable maxVisitCount = new IntWritable(0);

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String data = value.toString();
            String[] field = data.split(" ");
            String enPage = field[1];
            String pageName = field[2];
            if (enPage.startsWith("en") && !pageName.startsWith("Main_Page") && !pageName.startsWith("Special:")) {
                if (maxVisitCount.get() < Integer.parseInt(field[3])) {
                    popularPageTimeStamp.set(field[0]);
                    maxVisitCount.set(Integer.parseInt(field[3]));
                }
            }
        }

        @Override
        public void cleanup(Context context) throws IOException, InterruptedException {
            context.write(popularPageTimeStamp, maxVisitCount);
        }
    }

    public static class MostVisitedReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            for (IntWritable val : values) {
                context.write(key, val);
            }
        }
    }

    public static void printResults(String name, long timeInNanos) {
        System.out.println(String.format("%s completed in %8.3f milliseconds.", name, timeInNanos / 1e6));
    }

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "wikipedia popular");
        job.setJarByClass(WikipediaPopular.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setReducerClass(MostVisitedReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        if (job.waitForCompletion(true)) {
            printResults(job.getJobName(), System.nanoTime() - startTime);
            System.exit(0);
        } else {
            System.exit(1);
        }
    }
}