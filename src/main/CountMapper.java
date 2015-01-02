/*
 * Mongo-Hadoop Demo
 */
package main;

import com.mongodb.hadoop.io.BSONWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper;
import org.bson.BSONObject;
import java.io.IOException;
import java.util.Date;
import main.io.IntArrayWritable;

/**
 * The count mapper.
 *  In: mongo id => doc(Mongo Obj)
 * Out: list(Text) => count(IntWriteable)
 */
public class CountMapper extends Mapper<Object, BSONObject, Text, IntArrayWritable>
    implements org.apache.hadoop.mapred.Mapper<Object, BSONWritable, Text, IntArrayWritable> {

    @Override
    @SuppressWarnings("deprecation")
    public void map(final Object pKey, final BSONObject pValue, final Context pContext) throws IOException, InterruptedException {

        String list = pValue.get("list").toString();
        int opens = ((Number)pValue.get("open")).intValue();
        int clicks = ((Number)pValue.get("click")).intValue();

        IntArrayWritable ia = new IntArrayWritable();
        IntWritable[] iw = new IntWritable[2];
        iw[0] = new IntWritable(opens);
        iw[1] = new IntWritable(clicks);
        ia.set(iw);

        pContext.write(new Text(list), ia);
    }


    @Override
    @SuppressWarnings("deprecation")
    public void map(final Object key, final BSONWritable value, final OutputCollector<Text, IntArrayWritable> output,
                    final Reporter reporter) throws IOException {

        BSONObject pValue = value.getDoc();
        String list = pValue.get("list").toString();
        int opens = ((Number)pValue.get("open")).intValue();
        int clicks = ((Number)pValue.get("click")).intValue();

        IntArrayWritable ia = new IntArrayWritable();
        IntWritable[] iw = new IntWritable[2];
        iw[0] = new IntWritable(opens);
        iw[1] = new IntWritable(clicks);
        ia.set(iw);

        output.collect(new Text(list), ia);
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void configure(final JobConf job) {
    }
}

