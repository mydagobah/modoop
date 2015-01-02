/*
 * Mongo-Hadoop Demo
 */
package main;

import org.apache.hadoop.util.ToolRunner;

/**
 * Hadoop entry point
 * hadoop jar <jar.file> main.Entry
 */
public class Entry {

    public static void main(final String[] args) throws Exception {

        System.exit(ToolRunner.run(new Config(), args));
    }
}