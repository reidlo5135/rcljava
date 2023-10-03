package net.wavem.rcljava.rosdds.handle.topic;

import id.jros2client.impl.rmw.RmwConstants;
import net.wavem.rcljava.rosdds.infra.DDSQoS;
import net.wavem.rcljava.rosdds.infra.DDSSupport;
import net.wavem.rcljava.rosidl.infra.RCLMessage;
import pinorobotics.rtpstalk.RtpsTalkClient;
import pinorobotics.rtpstalk.RtpsTalkConfiguration;
import pinorobotics.rtpstalk.messages.RtpsTalkDataMessage;
import rx.Observable;
import rx.subjects.PublishSubject;
import java.util.concurrent.Flow;

public class RCLSubscription<T extends RCLMessage> {
    private final RtpsTalkClient ddsClient = new RtpsTalkClient(
            new RtpsTalkConfiguration.Builder()
                    .networkInterface(DDSSupport.DDS_NETWORK_INTERFACE_TYPE)
                    .build()
    );

    private final PublishSubject<byte[]> dataObservable = PublishSubject.create();

    public Observable<byte[]> getDataObservable() {
        return dataObservable;
    }

    public void registerSubscription(String topic, String messageType) {
        String ddsTopic = DDSSupport.qualifyTopic(topic);
        String ddsMessageType = DDSSupport.qualifyMessageType(messageType);

        ddsClient.subscribe(ddsTopic, ddsMessageType, DDSQoS.DEFAULT_SUBSCRIBER_QOS, new Flow.Subscriber<RtpsTalkDataMessage>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                System.out.println(ddsTopic + " subscription registered");
                subscription.request(1);
            }

            @Override
            public void onNext(RtpsTalkDataMessage message) {
                message.data().ifPresent(dataObservable::onNext);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                subscription.cancel();
            }
        });
    }
}