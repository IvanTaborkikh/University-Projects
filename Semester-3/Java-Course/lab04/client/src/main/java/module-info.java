module client {
    requires json.simple;
    requires com.fasterxml.jackson.databind;
    requires java.base;

    opens org.padadak.client.objects to com.fasterxml.jackson.databind;
    exports org.padadak.client.classes;
    exports org.padadak.client.objects;
}
