package net.wavem.rcljava.rosdds.handle.topic;

import net.wavem.rcljava.rosdds.infra.DDSQoS;
import net.wavem.rcljava.rosdds.infra.DDSSupport;
import net.wavem.rcljava.rosidl.infra.RCLMessage;
import pinorobotics.rtpstalk.RtpsTalkClient;
import pinorobotics.rtpstalk.RtpsTalkConfiguration;
import pinorobotics.rtpstalk.messages.RtpsTalkDataMessage;

import java.util.concurrent.SubmissionPublisher;

public class RCLPublisher<T extends RCLMessage> {
    private final RtpsTalkClient ddsClient = new RtpsTalkClient(
            new RtpsTalkConfiguration.Builder()
                    .networkInterface(DDSSupport.DDS_NETWORK_INTERFACE_TYPE)
                    .build()
    );

    private final SubmissionPublisher<RtpsTalkDataMessage> ddsPublisher = new SubmissionPublisher<>();

    public void registerPublisher(String topic, String messageType) {
        String ddsTopic = DDSSupport.qualifyTopic(topic);
        String ddsMessageType = DDSSupport.qualifyMessageType(messageType);

        ddsClient.publish(ddsTopic, ddsMessageType, DDSQoS.DEFAULT_PUBLISHER_QOS, ddsPublisher);
    }

    public void publish(byte[] data) {
        RtpsTalkDataMessage ddsMessage = new RtpsTalkDataMessage(data);
        ddsPublisher.submit(ddsMessage);
    }
}