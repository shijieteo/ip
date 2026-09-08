package squirtlebot;

import java.io.Serializable;
import java.time.temporal.Temporal;

public record TemporalPair(Temporal startDate, Temporal endDate) implements Serializable {}
