package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author MorielV
 * @since 12/23/20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FreezeFrame {
    private double start;
    private double duration;
    private double end;
}
