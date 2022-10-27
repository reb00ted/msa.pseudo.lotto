package msa.pseudo.lotto.saga.kafka.deserializer;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

@Component
public class CustomJsonDeserializer extends JsonDeserializer {

    public CustomJsonDeserializer() {
        super();
        this.addTrustedPackages("*");
    }
}
