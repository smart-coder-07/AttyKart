package com.asa;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
 
public class CalculateASA {
 
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("C:\\Users\\ATULTIWA\\Desktop\\AttyCart\\Backend\\JsonToExcel\\src\\main\\java\\com\\asa\\input20.json"));
 
        if (!root.isArray()) {
            System.out.println("❌ JSON should be an array of objects.");
            return;
        }
 
        Duration totalWait = Duration.ZERO;
        int count = 0;
 
        for (JsonNode obj : root) {
            if (obj.has("assigned_at") && obj.has("queued_at")) {
                OffsetDateTime assigned = OffsetDateTime.parse(obj.get("assigned_at").asText());
                OffsetDateTime queued = OffsetDateTime.parse(obj.get("queued_at").asText());
 
                Duration diff = Duration.between(queued, assigned);
totalWait = totalWait.plus(diff);
                count++;
            }
        }
 
        System.out.println("20th \nTotal objects: " + count);
 
        String totalFormatted = formatDuration(totalWait);
        System.out.println("🟢 Total wait: " + totalFormatted);
 
        if (count > 0) {
            Duration averageWait = totalWait.dividedBy(count);
            String averageFormatted = formatDuration(averageWait);
            System.out.println("🟢 ASA: " + averageFormatted);
        }
    }
 
    private static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        return String.format("%02d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
    }
}