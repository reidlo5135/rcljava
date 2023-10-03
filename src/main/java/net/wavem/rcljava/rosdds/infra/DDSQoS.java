package net.wavem.rcljava.rosdds.infra;

import pinorobotics.rtpstalk.qos.DurabilityType;
import pinorobotics.rtpstalk.qos.PublisherQosPolicy;
import pinorobotics.rtpstalk.qos.ReliabilityType;
import pinorobotics.rtpstalk.qos.SubscriberQosPolicy;

public interface DDSQoS {
    PublisherQosPolicy DEFAULT_PUBLISHER_QOS = new PublisherQosPolicy(ReliabilityType.RELIABLE, DurabilityType.VOLATILE_DURABILITY_QOS);
    SubscriberQosPolicy DEFAULT_SUBSCRIBER_QOS = new SubscriberQosPolicy(ReliabilityType.RELIABLE, DurabilityType.VOLATILE_DURABILITY_QOS);
}
