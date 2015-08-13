package com.snapdeal.cx.comm.CommEngine.spout;

import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class TemplateReaderSpout implements IRichSpout {

	private static final long serialVersionUID = 2167565823193542018L;
	private SpoutOutputCollector collector;
	private boolean completed = false;
	private TopologyContext context;

	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.context = context;
		this.collector = collector;
	}

	public void nextTuple() {
		if (completed) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
		}
		this.collector.emit(new Values("msg.vm"));
		completed = true;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("fileName"));
	}

	public void close() {
	}

	public boolean isDistributed() {
		return false;
	}

	public void activate() {
	}

	public void deactivate() {
	}

	public void ack(Object msgId) {
	}

	public void fail(Object msgId) {
	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}