/*
 * Mongo-Hadoop Demo
 */
package main.io;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.ArrayWritable;

public class IntArrayWritable extends ArrayWritable {
	public IntArrayWritable() {
		super(IntWritable.class);
	}
	
}