package Utilities;

import javafx.scene.control.ComboBox;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class TimeConversion {

    private static ZoneId businessZoneID = ZoneId.of("America/New_York");
    private static ZoneOffset businessZoneOffset = businessZoneID.getRules().getOffset(Instant.now());
    private static ZoneId userZoneID = ZoneId.systemDefault();
    private static ZoneOffset userZoneOffset = userZoneID.getRules().getOffset(Instant.now());

    private static ZoneId databaseZoneID = ZoneId.of("UTC");
    private static ZoneOffset databaseZoneOffset = databaseZoneID.getRules().getOffset(Instant.now());

    private LocalTime userLocalTime = LocalTime.now(businessZoneID);
    private LocalTime businessLocalTime = LocalTime.now(businessZoneID);

    private long hoursBetween = ChronoUnit.HOURS.between(userLocalTime, businessLocalTime);

    /**
     * Returns date in from local date time to readable format for user.
     * @param localDateTime
     * @return
     */
    public static String formatLocalDate(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * Returns time from local date time to readable format for user.
     * Time is displayed in the 12-hour format with am/pm.
     * @param localDateTime
     * @return timeFormatter
     */
    public static String formatLocalTimeFromDate(LocalDateTime localDateTime){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return timeFormatter.format((localDateTime));
    }

    /**
     * Transforms LocalDateTime object into a LocalTime object format
     * to use in a readable 12-hour format
     * @param localTime
     * @return timeFormatter
     */
    public static String formatLocalTime (LocalTime localTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return timeFormatter.format(localTime);
    }

    public static String formatZonedDateTime(ZonedDateTime zonedDateTime){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return timeFormatter.format(zonedDateTime);
    }

    /**
     * Populates combo boxes on the add appointment and update appointment screens with selectable times.
     * Selectable times are in 15 minute intervals.
     * @param localTimeComboBox
     * @param startTime
     * @param endTime
     * @return localTimeComboBox
     */
    public static ComboBox<LocalTime> populateTimeSelectionComboBox(ComboBox<LocalTime> localTimeComboBox, LocalTime startTime, LocalTime endTime){
        LocalTime convertedStartTime = (startTime);
        LocalTime convertedEndTime = (endTime);

        while(convertedStartTime.isBefore(convertedEndTime.plusSeconds(1))){
            int timeIntervals = 15;
            localTimeComboBox.getItems().add(convertedStartTime);
            convertedStartTime = convertedStartTime.plusMinutes(timeIntervals);
        }
        return localTimeComboBox;
    }

    public static ComboBox<ZonedDateTime> populateTimeSelectionComboBoxZDT(ComboBox<ZonedDateTime> zonedDateTimeComboBox, ZonedDateTime startTime, ZonedDateTime endTime){
        ZonedDateTime convertedStartTime = startTime;
        ZonedDateTime convertedEndTime = endTime;

        while(convertedStartTime.isBefore(convertedEndTime.plusSeconds(1))){
            int timeIntervals = 15;
            zonedDateTimeComboBox.getItems().add(convertedStartTime);
            convertedStartTime = convertedStartTime.plusMinutes(timeIntervals);
        }
        return zonedDateTimeComboBox;
    }

    private static Map<ZoneId, Integer> getAllZoneIDAndItsOffset(){
        Map<ZoneId, Integer> timeZoneResults = new HashMap<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        for (String zoneID : ZoneId.getAvailableZoneIds()){
            ZoneId id = ZoneId.of(zoneID);
            ZonedDateTime zonedDateTime = localDateTime.atZone(id);
            ZoneOffset zoneOffset = zonedDateTime.getOffset();
            String offset = zoneOffset.getId().replaceAll("Z", "+00:00");
            timeZoneResults.put(id, Integer.valueOf((offset)));
        }
        System.out.println(timeZoneResults);
        return timeZoneResults;
    }

    /**
     * Converts the local time to eastern time.
     * @param timeConversionToEST
     * @return currentEasternTime
     */
    public static LocalDateTime compareLocalToEasternTime(LocalDateTime timeConversionToEST){
        LocalDateTime localTime = timeConversionToEST;

        ZonedDateTime currentLocalTime = localTime.atZone(userZoneID);
        ZonedDateTime currentEasternTime = currentLocalTime.withZoneSameInstant(businessZoneID);

        return currentEasternTime.toLocalDateTime();
    }

    /**
     * Compares local time to the company's business hours, which is 8am to 10pm EST.
     * Returns true if hours are between business hours.
     * Returns false if hours are outside business hours.
     * @param timeToCompare
     * @return easternTimeToCompare
     */
    public static boolean compareLocalToBusinessHours(LocalDateTime timeToCompare){
        int timeZoneDifference = ((businessZoneOffset.getTotalSeconds()/3600) - databaseZoneOffset.getTotalSeconds()/3600);
        System.out.println("compareLocalToBusinessHours time zone difference between business and UTC is: "+timeZoneDifference);

        LocalTime startTime = LocalTime.of(7,59);
        LocalTime endTime = LocalTime.of(22,1);

        ZonedDateTime easternTimeStart = ZonedDateTime.of(timeToCompare.toLocalDate(), startTime, businessZoneID);
        ZonedDateTime easternEndTime = ZonedDateTime.of(timeToCompare.toLocalDate(), endTime, businessZoneID);

        ZonedDateTime userTimeStart = ZonedDateTime.of(timeToCompare.toLocalDate(), startTime, userZoneID);
        ZonedDateTime userTimeEnd = ZonedDateTime.of(timeToCompare.toLocalDate(), endTime, userZoneID);

        LocalDateTime compareLocalToEST = compareLocalToEasternTime(timeToCompare);

        return compareLocalToEST.isAfter(ChronoLocalDateTime.from(easternTimeStart))
                && compareLocalToEST.isBefore(ChronoLocalDateTime.from(easternEndTime));
    }

    public static String convertLocalToUTC(LocalTime localTime){
        LocalDateTime LocalDT = LocalDateTime.now();
        ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.systemDefault());
        ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of(String.valueOf(databaseZoneID)));
        LocalTime utcOUT = utcDT.toLocalTime();
        String utcString = utcOUT.format(DateTimeFormatter.ofPattern("HH:mm"));
        return utcString;
    }

    public static LocalDateTime convertLocalToEST(LocalDateTime localTimeToBusinessTime){
        LocalDateTime localDateTime = localTimeToBusinessTime;

        ZonedDateTime currentLocal = localDateTime.atZone(userZoneID);
        ZonedDateTime currentBusiness = currentLocal.withZoneSameInstant(businessZoneID);

        return currentBusiness.toLocalDateTime();
    }

    public static LocalTime convertLocalToESTTime(LocalTime localTimeToBusinessTime){
        return localTimeToBusinessTime;
    }

    public static LocalTime convertESTToLocal(LocalTime localTime){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        //LocalTime estOUT = zonedDateTime
        return localTime;
    }

    //public static LocalTime convert
}