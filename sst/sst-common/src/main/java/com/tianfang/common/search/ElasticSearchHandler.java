package com.tianfang.common.search;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ElasticSearchHandler {

		@Getter
	 	private Client client;
	 	
		private TransportClient transportClient;
		
		@Getter
		@Setter
		private String ipAddress;
		
		@Getter
		@Setter
		private int port;
		
	    public ElasticSearchHandler(){    
	    }
		
		public ElasticSearchHandler(String ipAddress, int port){
			//Settings settings = ImmutableSettings.settingsBuilder().put("client.transport.sniff", true).put("cluster.name", "name of node").build();  
	        transportClient = new TransportClient();
			client = transportClient.addTransportAddress(new InetSocketTransportAddress(ipAddress, port));
		}
		   
	    
	    /**
	     * 创建索引
	     * @param client
	     * @param jsondata
	     * @return
	     */
	    public IndexResponse createIndexResponse(String indexname, String type,String jsondata){
	        IndexResponse response = client.prepareIndex(indexname, type)
	            .setSource(jsondata)
	            .execute()
	            .actionGet();
	        return response;
	    }
	    
	    public IndexRequestBuilder createIndexResponse(String indexname, String type){
	    	IndexRequestBuilder requestBuilder = client.prepareIndex(indexname, type).setRefresh(true);
	        return requestBuilder;
	    }
	    
	    public void setSource(String data, IndexRequestBuilder requestBuilder){	    	
	    	requestBuilder.setSource(data).execute().actionGet();
	    }
	    
	    public void setSource(String id, String data,  IndexRequestBuilder requestBuilder) {
	    	requestBuilder.setSource(data).setId(id).setSource(data).execute().actionGet();
	    }
}
