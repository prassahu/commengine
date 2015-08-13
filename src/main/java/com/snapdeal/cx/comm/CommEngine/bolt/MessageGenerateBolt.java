package com.snapdeal.cx.comm.CommEngine.bolt;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class MessageGenerateBolt implements IRichBolt {

	private static final long serialVersionUID = -429817312458957761L;
	private OutputCollector collector;

	private static VelocityEngine ve;

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		ve = new VelocityEngine();
		ve.init();
		this.collector = collector;
	}

	public void execute(Tuple input) {
		byte[] filebin = input.getBinary(0);
		// File file = FileUtils.
		@SuppressWarnings("unchecked")
		Map<String, String> values = (HashMap<String, String>) input
				.getValue(1);
		String content = formatMessage(values);
		collector.emit(new Values(content));
		collector.ack(input);
	}

	public void cleanup() {
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("messageContent"));
	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

	private static String formatMessage(Map<String, String> values) {
		
		Template t = ve.getTemplate("msg.vm");
		VelocityContext context = new VelocityContext();
		for (String key : values.keySet()) {
			context.put(key, values.get(key));
		}
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		return writer.toString();
	}
}
