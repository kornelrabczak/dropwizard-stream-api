package com.thecookiezen.streamapi;

import akka.actor.ActorSystem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.softwaremill.react.kafka.ConsumerProperties;
import com.softwaremill.react.kafka.PropertiesBuilder;
import io.dropwizard.Configuration;
import kafka.serializer.StringDecoder;
import org.hibernate.validator.constraints.NotEmpty;

public class ApplicationConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String zooKeeperHost;

    @NotEmpty
    @JsonProperty
    private String brokerList;

    @JsonIgnore
    private ActorSystem system = ActorSystem.create("ReactiveKafka");

    @NotEmpty
    @JsonProperty
    private String topic;

    @NotEmpty
    @JsonProperty
    private String groupId;

    public String getZooKeeperHost() {
        return zooKeeperHost;
    }

    public void setZooKeeperHost(String zooKeeperHost) {
        this.zooKeeperHost = zooKeeperHost;
    }

    public String getBrokerList() {
        return brokerList;
    }

    public void setBrokerList(String brokerList) {
        this.brokerList = brokerList;
    }

    public ActorSystem getSystem() {
        return system;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public ConsumerProperties<String> build() {
        return new PropertiesBuilder.Consumer(brokerList, zooKeeperHost, topic, groupId, new StringDecoder(null))
                .build();
    }
}
