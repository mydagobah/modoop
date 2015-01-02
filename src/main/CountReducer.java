/*
 * Mongo-Hadoop Demo
 */
package main;

import com.mongodb.hadoop.io.BSONWritable;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Reducer;
import org.bson.BasicBSONObject;

import java.io.IOException;
import java.util.Iterator;

/**
 * The treasury yield reducer.
 */
public class CountReducer extends Reducer<Text, IntWritable, Text, BSONWritable> 
    implements org.apache.hadoop.mapred.Reducer<Text, IntWritable, Text, BSONWritable> {

   // private static final Log LOG = LogFactory.getLog(CountReducer.class);

    @Override
    public void reduce(final Text pKey, final Iterable<IntWritable> pValues, final Context pContext)
        throws IOException, InterruptedException {

        int sum = 0;
        for (final IntWritable value : pValues) {
            sum += value.get();
        }

        //LOG.debug("Opens for list " + pKey.get() + " is " + sum);

        BasicBSONObject output = new BasicBSONObject();
        output.put("sum", sum);
        pContext.write(pKey, new BSONWritable(output));
    }

    @Override
    public void reduce(final Text key, final Iterator<IntWritable> values, final OutputCollector<Text, BSONWritable> output,
                       final Reporter reporter) throws IOException {

        int sum = 0;
        while (values.hasNext()) {
            sum += values.next().get();
        }

        //LOG.debug("Opens for list " + pKey.get() + " is " + sum);

        BasicBSONObject bsonObject = new BasicBSONObject();
        bsonObject.put("sum", sum);
        output.collect(key, new BSONWritable(bsonObject));
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void configure(final JobConf job) {
    }
}