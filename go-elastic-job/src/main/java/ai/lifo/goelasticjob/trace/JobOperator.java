package ai.lifo.goelasticjob.trace;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.infra.pojo.JobConfigurationPOJO;
import org.apache.shardingsphere.elasticjob.lite.lifecycle.api.JobConfigurationAPI;
import org.apache.shardingsphere.elasticjob.lite.lifecycle.api.JobOperateAPI;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerUtils;
import org.quartz.impl.calendar.BaseCalendar;
import org.quartz.spi.OperableTrigger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author zhanglifeng
 * @since 2024-05-23
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JobOperator {

    private final JobConfigurationAPI jobConfigurationAPI;

    private final JobOperateAPI jobOperateAPI;

    public JobConfigurationPOJO getJobConfig(String jobName) throws ParseException {
        JobConfigurationPOJO jobConfiguration = jobConfigurationAPI.getJobConfiguration(jobName);
//        CronTriggerImpl cronTrigger = new CronTriggerImpl();
//        cronTrigger.setCronExpression(jobConfiguration.getCron());
        LocalDateTime now = LocalDateTime.now();

        Date startTime = localDateTimeToDate(now);
        Date endTime = localDateToDate(now.toLocalDate().plusDays(1));
//        CronCalendar cronCalendar = new CronCalendar(cronTrigger.getCronExpression());
//        cronTrigger.setStartTime(startTime);
//        cronTrigger.setEndTime(endTime);
//        List<Date> dateList = TriggerUtils.computeFireTimesBetween(cronTrigger, cronCalendar, startTime, endTime);

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(jobConfiguration.getCron()))
                .build();
        List<Date> dateList = TriggerUtils.computeFireTimesBetween((OperableTrigger) trigger, new BaseCalendar(), startTime, endTime);

        log.info("dateList:{}", dateList);
        return jobConfiguration;
    }


    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static LocalDate dateToLocalDate(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    public static Date localDateToDate(LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            return null;
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }
}
