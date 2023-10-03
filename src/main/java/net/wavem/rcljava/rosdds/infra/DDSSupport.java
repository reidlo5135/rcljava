package net.wavem.rcljava.rosdds.infra;

public class DDSSupport {
    public static final String DDS_NETWORK_INTERFACE_TYPE = "lo";
    private static final String DDS_TOPIC_FORMAT = "rt";
    private static final String DDS_MESSAGE_TYPE_FORMAT = "::msg::dds_::";

    public static String qualifyTopic(String topic) {
        String ddsQualifiedTopic = "";

        if (!topic.contains("/")) {
            ddsQualifiedTopic = DDS_TOPIC_FORMAT + "/" + topic;
        } else {
            ddsQualifiedTopic = DDS_TOPIC_FORMAT + topic;
        }

        return ddsQualifiedTopic;
    }

    public static String qualifyMessageType(String messageType) {
        if (!messageType.contains("/")) {
            throw new RuntimeException("RCLMessageType must contain '/'");
        }

        String[] messageTypeSplit = messageType.split("/");
        String messagePackage = messageTypeSplit[0];
        String messageClass = messageTypeSplit[1] + "_";

        String ddsQualifiedMessageType = messagePackage + DDS_MESSAGE_TYPE_FORMAT + messageClass;

        System.out.println("qualifyMessage : " + ddsQualifiedMessageType);

        return ddsQualifiedMessageType;
    }
}
