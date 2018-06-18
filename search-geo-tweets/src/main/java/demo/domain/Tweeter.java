package demo.domain;//package demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"timestamp_ms", "id", "text", "place", "entities"})
@Document  // Object in this class & MongoDB mapping
public class Tweeter {

    @Id
    private String id;
    private String text;
    private Date timestamp_ms;

    @Transient  // not include into MongoDB
    private Position coordinates;   // longitude, latitude

    private Place place;    // include polygon
    private Entities entities;  // include hashtags

    @GeoSpatialIndexed   // mark GeoSpatial data index in MongoDB
    @JsonIgnore // no a field call geoPoint in JSON Sting
    private GeoPoint geoPoint = new GeoPoint(0.0, 0.0);

}
