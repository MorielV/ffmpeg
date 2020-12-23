package pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author MorielV
 * @since 12/23/20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @JsonProperty("longest_valid_period")
    private double longestValidPeriod;
    @JsonProperty("valid_video_percentage")
    private double validVideoPercentage;
    @JsonProperty("valid_periods")
    private List<List<Double>> validPeriods;
}
