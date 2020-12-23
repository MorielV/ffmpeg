package response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pojos.Video;

import java.util.List;

/**
 * @author MorielV
 * @since 12/23/20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FreezeDetectResponse {
    @JsonProperty("all_videos_freeze_frame_synced")
    private boolean allVideosFreezeFrameSynced;

    private List<Video> videos;
}
