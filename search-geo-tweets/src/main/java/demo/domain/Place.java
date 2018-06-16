package demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"name", "full_name", "country", "bounding_box"})
public class Place {
    private String id;
    private String place_type;
    private String name;
    private String full_name;
    private String country_code;
    private String country;
    private BoundingBox bounding_box;

}
