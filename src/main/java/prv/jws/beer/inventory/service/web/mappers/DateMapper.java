package prv.jws.beer.inventory.service.web.mappers;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class DateMapper {
    public OffsetDateTime timestampToOffsetDateTime(Timestamp timestamp) {
        if (nonNull(timestamp)) {
            var localDatetime = timestamp.toLocalDateTime();
            return OffsetDateTime.of(localDatetime.getYear(), localDatetime.getMonthValue(), localDatetime.getDayOfMonth(),
                    localDatetime.getHour(), localDatetime.getMinute(), localDatetime.getSecond(),
                    localDatetime.getNano(), ZoneOffset.UTC);
        }
        return null;
    }

    public Timestamp timestampToOffsetDateTime(OffsetDateTime offsetDateTime) {
        if (nonNull(offsetDateTime)) {
            return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        }
        return null;
    }
}
