package msa.pseudo.lotto.lottoservice.kafka.deserializer;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

@Component
public class CustomJsonDeserializer extends JsonDeserializer {

    public CustomJsonDeserializer() {
        super();
        this.addTrustedPackages("*");
    }
}
