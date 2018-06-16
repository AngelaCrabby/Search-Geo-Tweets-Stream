package demo.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data  // for getter & setter member variables
@NoArgsConstructor(access = AccessLevel.PUBLIC)  // for json serialization & deserialization
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entities {
    private List<Hashtag> hashtags = new ArrayList<>();
}
