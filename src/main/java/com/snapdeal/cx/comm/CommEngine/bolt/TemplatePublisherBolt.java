package com.snapdeal.cx.comm.CommEngine.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class TemplatePublisherBolt implements IRichBolt {

  /**
   * 
   */
  private static final long serialVersionUID = 5655848633109581358L;

  public void cleanup() {
    // TODO Auto-generated method stub

  }

  public void execute(Tuple input) {
    System.out.println(input.getString(0));

  }

  public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {

  }

  public void declareOutputFields(OutputFieldsDeclarer arg0) {
    // TODO Auto-generated method stub

  }

  public Map<String, Object> getComponentConfiguration() {
    // TODO Auto-generated method stub
    return null;
  }

}
