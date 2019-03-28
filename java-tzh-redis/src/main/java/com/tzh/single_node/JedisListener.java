package com.tzh.single_node;

import redis.clients.jedis.JedisPubSub;

/**
 * 重写监听器的一些重要方法，JedisPubSub里面的这些回调方法都是空的，不重写就什么事都不会发生
 *
 * @author Kazz
 */
public class JedisListener extends JedisPubSub {

    /**
     * 收到消息后的回调
     */
    @Override
    public void onMessage(String channel, String message) {
        System.out.println("onMessage: 收到频道[" + channel + "]的消息[" + message + "]");
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println("onPMessage: channel[" + channel + "], message[" + message + "]");
    }

    /**
     * 成功订阅频道后的回调
     */
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out
                .println("onSubscribe: 成功订阅[" + channel + "]," + "subscribedChannels[" + subscribedChannels + "]");
    }

    /**
     * 取消订阅频道的回调
     */
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println(
                "onUnsubscribe: 成功取消订阅[" + channel + "], " + "subscribedChannels[" + subscribedChannels + "]");
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println(
                "onPUnsubscribe: pattern[" + pattern + "]," + "subscribedChannels[" + subscribedChannels + "]");
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println(
                "onPSubscribe: pattern[" + pattern + "], " + "subscribedChannels[" + subscribedChannels + "]");
    }

}