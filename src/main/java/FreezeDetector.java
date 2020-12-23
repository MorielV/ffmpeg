import exceptions.FfmpegCallException;
import exceptions.ParsingException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojos.FreezeFrame;
import pojos.Video;
import response.FreezeDetectResponse;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author MorielV
 * @since 12/23/20
 */
public class FreezeDetector {
    private final static Logger LOG = LoggerFactory.getLogger(FreezeDetector.class);


    public static void main(String[] args) {
        FreezeDetectResponse response = new FreezeDetectResponse(false, new ArrayList<>());
        Set<String> urls = new HashSet<>(Arrays.asList(args));
        Process process;
        for (String url : urls) {
            try {
                String[] command = createCommand("ffmpeg",
                        "-i", url,
                        "-vf", "freezedetect=n=0.003,metadata=mode=print:file=- ",
                        "-map", "0:v:0",
                        "-f", "null", "-"
                );
                process = Runtime.getRuntime().exec(command);
                try (InputStream stdout = process.getInputStream();
                        InputStream error = process.getErrorStream()) {

                    String responseString = IOUtils.toString(stdout, StandardCharsets.UTF_8);
                    String errorResponseString = IOUtils.toString(error, StandardCharsets.UTF_8);

                    if (StringUtils.isBlank(responseString)) {
                        throw new FfmpegCallException("Got Blank response");
                    }
                    List<FreezeFrame> freezeFrames = convertToFreezeFrames(responseString);

                    Video video = prepareVideo(freezeFrames);
                    response.getVideos().add(video);
                }
            } catch (Exception e) {
                LOG.warn("Got Error", e);
            }
        }

    }

    private static Video prepareVideo(List<FreezeFrame> freezeFrames) {
        double currentEnd = 0;
        double longestValidPeriod = 0;
        List<List<Double>> validPeriods = new ArrayList<>();
        for (FreezeFrame freezeFrame : freezeFrames) {
            //valid period
            List<Double> validPeriod = new ArrayList<>();
            validPeriod.add(0, currentEnd);
            validPeriod.add(1, freezeFrame.getStart());
            validPeriods.add(validPeriod);
            currentEnd = freezeFrame.getEnd();

            //longest valid period update
            double currentValidPeriod = freezeFrame.getStart() - currentEnd;
            if (currentValidPeriod > longestValidPeriod) {
                longestValidPeriod = currentValidPeriod;
            }
        }
        double validVideoPercentage = longestValidPeriod / currentEnd;
        return new Video(longestValidPeriod, validVideoPercentage, validPeriods);
    }

    private static String[] createCommand(String... args) {
        return args;
    }

    private static List<FreezeFrame> convertToFreezeFrames(String responseString) {
        String[] splitResponse = responseString.split("\n");
        List<String> strings = Arrays.asList(splitResponse);
        List<FreezeFrame> freezeFrames = new ArrayList<>();
        final FreezeFrame[] freezeFrame = {new FreezeFrame()};
        strings.forEach(s -> {
            if (s.startsWith("lavfi.freezedetect")) {
                String[] split = s.split("=");
                if (split.length < 2) {
                    throw new ParsingException(String.format("Freeze frame line %s has wrong format", s));
                }
                double seconds = Double.parseDouble(split[1]);

                if (s.startsWith("lavfi.freezedetect.freeze_start")) {
                    freezeFrame[0].setStart(seconds);
                }
                if (s.startsWith("lavfi.freezedetect.freeze_duration")) {
                    freezeFrame[0].setDuration(seconds);
                }
                if (s.startsWith("lavfi.freezedetect.freeze_end")) {
                    freezeFrame[0].setEnd(seconds);
                    freezeFrames.add(new FreezeFrame(freezeFrame[0].getStart(), freezeFrame[0].getDuration(), freezeFrame[0].getEnd()));
                    freezeFrame[0] = new FreezeFrame();
                }
            }


        });
        return freezeFrames;
    }
}
