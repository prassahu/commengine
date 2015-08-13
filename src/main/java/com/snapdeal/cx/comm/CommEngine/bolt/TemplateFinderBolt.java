package com.snapdeal.cx.comm.CommEngine.bolt;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class TemplateFinderBolt implements IRichBolt {

	private static final long serialVersionUID = -6458700984640022900L;
	private OutputCollector collector;
	private File file;
	private Map<String, String> valueMap;

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		valueMap = new HashMap<String, String>();
	}

	public void execute(Tuple input) {
		String fileName = input.getString(0);
		file = new File(fileName);
		try {
			byte[] filebin = FileUtils.readFileToByteArray(file);
			valueMap.put("name", "User");
			collector.emit(new Values(filebin, valueMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
		collector.ack(input);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("fileData", "placeHolderValue"));
	}

	public void cleanup() {
		valueMap.clear();
		file = null;
	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
