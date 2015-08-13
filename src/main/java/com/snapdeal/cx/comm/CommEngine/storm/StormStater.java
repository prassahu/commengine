package com.snapdeal.cx.comm.CommEngine.storm;

import com.snapdeal.cx.comm.CommEngine.bolt.MessageGenerateBolt;
import com.snapdeal.cx.comm.CommEngine.bolt.TemplateFinderBolt;
import com.snapdeal.cx.comm.CommEngine.bolt.TemplatePublisherBolt;
import com.snapdeal.cx.comm.CommEngine.spout.TemplateReaderSpout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

public class StormStater {
	
	public static void main(String[] args) throws Exception {
		Config config = new Config();
		config.setDebug(true);
		config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("message-comm-spout", new TemplateReaderSpout());
		builder.setBolt("template-finder", new TemplateFinderBolt())
				.shuffleGrouping("message-comm-spout");
		builder.setBolt("template-content", new MessageGenerateBolt())
				.shuffleGrouping("template-finder");
		builder.setBolt("template-publisher", new TemplatePublisherBolt())
				.shuffleGrouping("template-content");

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("CommStorm", config, builder.createTopology());
		Thread.sleep(10000);

		cluster.shutdown();
	}

}
