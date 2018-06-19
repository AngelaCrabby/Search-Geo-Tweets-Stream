package demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.util.ArrayList;
import java.util.List;

@Data  // for getter & setter member variables
@NoArgsConstructor(access = AccessLevel.PUBLIC)  // for json serialization & deserialization
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"text", "indices"})
public class Hashtag {

    @TextIndexed
    private String text;
    private List<Integer> indices = new ArrayList<>(2);
}
