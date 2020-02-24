package com.boot.kaizen.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * elasticsearch 客户端配置
 * 
 * @author weichengz
 * @date 2019年10月16日 上午10:18:27
 */
@Configuration
public class EsConfig {

	/**
	 * 这里据我了解到 es只需要配置一个节点（集群环境下）然后他会自动的嗅觉到主节点，如果主节点挂了，他还是可以继续使用，链接其他的主节点的。 待测试
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月25日 下午5:33:35
	 */
	@Bean
	public TransportClient transportClient() throws UnknownHostException {
		// Settings settings = Settings.builder().put("cluster.name","ihandle-logs").put("client.transport.sniff", true).build();
		Settings settings = Settings.builder().put("cluster.name", "ihandle-logs").build();
		TransportClient transportClient = new PreBuiltTransportClient(settings);
		TransportAddress transportAddress = new TransportAddress(InetAddress.getByName("10.255.255.133"), 9300);
		// TransportAddress transportAddress = new TransportAddress(InetAddress.getByName("172.16.207.85"), 9300);
		transportClient.addTransportAddress(transportAddress);
		return transportClient;
	}
}
