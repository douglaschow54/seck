package com.pcwerk.seck;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseTest {

  private String hbaseHost;
  
  public HBaseTest(String hostname) { 
    hbaseHost = hostname;
  }
  
  public HBaseTest() {
    hbaseHost = "localhost";
  }

  public void run() throws IOException {
    Configuration conf = HBaseConfiguration.create();
    conf.set("hbase.zookeeper.quorum", hbaseHost);
    
    HTable table = new HTable(conf, "testtable");
    Put put = new Put(Bytes.toBytes("row22"));
    // Get get = new Get();
    
    put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"),
        Bytes.toBytes("val1"));
    put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual2"),
        Bytes.toBytes("val2"));
    
    table.put(put);
  }
  
  public static void main(String[] args) {
    try {
      new HBaseTest().run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
