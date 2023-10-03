package net.wavem.rcljava.rosdds.handle;

import net.wavem.rcljava.rosdds.handle.topic.RCLPublisher;
import net.wavem.rcljava.rosdds.handle.topic.RCLSubscription;
import net.wavem.rcljava.rosidl.infra.RCLMessage;

public class RCLJava {
    public <T extends RCLMessage> RCLPublisher<T> createPublisher(String topic, String messageType) {
        RCLPublisher<T> rclPublisher = new RCLPublisher<>();
        rclPublisher.registerPublisher(topic, messageType);

        System.out.println(topic + " publisher created");

        return rclPublisher;
    }

    public <T extends RCLMessage> RCLSubscription<T> createSubscription(String topic, String messageType) {
        RCLSubscription<T> rclSubscription = new RCLSubscription<>();
        rclSubscription.registerSubscription(topic, messageType);

        System.out.println(topic + " subscription created");

        return rclSubscription;
    }

    public static void publishingTest() {
        RCLJava rclJava = new RCLJava();
        RCLPublisher<net.wavem.rcljava.rosidl.message.std_msgs.String> rclPublisher = rclJava.createPublisher("/chatter", "std_msgs/String");

        try {
            net.wavem.rcljava.rosidl.message.std_msgs.String string = new net.wavem.rcljava.rosidl.message.std_msgs.String("hi chatter");

            for (int i = 1; i <= 10; i++) {
                rclPublisher.publish(net.wavem.rcljava.rosidl.message.std_msgs.String.write(net.wavem.rcljava.rosidl.message.std_msgs.String.build(string)));
                Thread.sleep(500);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public static void subscriptionTest() {
        RCLJava rclJava = new RCLJava();
        String topic = "/chatter";
        String messageType = "std_msgs/String";
        RCLSubscription<net.wavem.rcljava.rosidl.message.std_msgs.String> rclSubscription = rclJava.createSubscription(topic, messageType);

        rclSubscription.getDataObservable().subscribe( it -> {
            if (it != null) {
                net.wavem.rcljava.rosidl.message.std_msgs.String stringCallback = net.wavem.rcljava.rosidl.message.std_msgs.String.read(it);
                System.out.println("$topic callback : " + stringCallback.getData());
            }
        });
    }

    public static void main(String[] args) {
        publishingTest();
//        subscriptionTest();
    }
}