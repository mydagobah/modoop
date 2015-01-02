/*
 * Mongo-Hadoop Demo
 */
package main;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.hadoop.io.MongoUpdateWritable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.bson.BasicBSONObject;
import java.io.IOException;
import java.util.Date;
import main.io.IntArrayWritable;

/**
 * The treasury yield reducer.
 */
public class CountUpdateReducer extends Reducer<Text, IntArrayWritable, NullWritable, MongoUpdateWritable> {

    private static final Log LOG = LogFactory.getLog(CountUpdateReducer.class);

    @Override
    public void reduce(final Text pKey,
                       final Iterable<IntArrayWritable> pValues,
                       final Context pContext) throws IOException, InterruptedException {

        int opens = 0, clicks = 0;

        for (final IntArrayWritable value : pValues) {
            Writable[] iw = value.get();

            opens += ((IntWritable)iw[0]).get();
            clicks += ((IntWritable)iw[1]).get();
        }

        //LOG.debug("Opens for list " + pKey.toString() + " is " + opens);

        BasicBSONObject query = new BasicBSONObject("list", pKey.toString());
        BasicBSONObject modifiers = new BasicBSONObject();
        modifiers.put("$set", BasicDBObjectBuilder.start().add("opens", opens).add("clicks", clicks).get());
        modifiers.put("$push", new BasicBSONObject("calculatedAt", new Date()));
        modifiers.put("$inc", new BasicBSONObject("numCalculations", 1));

        pContext.write(null, new MongoUpdateWritable(query, modifiers, true, false));
    }

}

