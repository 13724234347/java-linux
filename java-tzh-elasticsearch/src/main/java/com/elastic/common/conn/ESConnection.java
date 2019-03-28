package com.elastic.common.conn;

import java.net.InetAddress;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 
 * 获取ES集群连接
 *
 *
 */
public class ESConnection {
    private static TransportClient  client = null;
    private static final String ES_HOST[] ={"192.168.1.197","192.168.1.198"};
    private static final Integer ES_PORT = 9300;
    
    
    /**
     * 集群连接
     */
    @SuppressWarnings("resource")
    private ESConnection() {
        try {
            Settings settings = Settings.builder().put("client.transport.sniff", true)
                    .put("cluster.name", "my-application").build();

            PreBuiltTransportClient preBuiltTransportClient = new PreBuiltTransportClient(settings);
            for (String host : ES_HOST) {
                client = preBuiltTransportClient.addTransportAddress(new TransportAddress(InetAddress.getByName(host), ES_PORT));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * 获取连接
     */
    public static TransportClient getConnection() {

        if (client == null) {
            synchronized (EsClient.class) {
                if (client == null) {
                    new ESConnection();
                }
            }
        }
        return client;

    }

}
