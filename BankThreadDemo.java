import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BankThreadDemo {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        String inputFileName = "bank_data.csv";
        String outputFileName = "bank_data_corrected.csv";

        try {
            List<String> outputLines = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(inputFileName));
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");

            if (!lines.isEmpty()) {
                lines.remove(0);
            }

            for (String line : lines) {
                String[] fields = line.split(",");

                // a. Remove extra symbol at the end of the account number
                fields[0] = fields[0].replaceAll("'$", "");

                // b. Change date format
                try {
                    fields[1] = outputDateFormat.format(inputDateFormat.parse(fields[1]));
                } catch (ParseException e) {
                    //System.err.println("Error parsing date: " + fields[1]);
                }

                try {
                    fields[4] = outputDateFormat.format(inputDateFormat.parse(fields[4]));
                } catch (ParseException e) {
                    //System.err.println("Error parsing date: " + fields[4]);
                }

                // c. Correct transaction type
                fields[2] = fields[2].replace("FDRL/INTERNAL FUND TRANSFE", "FDRL/EXTERNAL FUND TRANSFE");

                // d. Clean the amount field
                fields[5] = fields[5].replaceAll("[^\\d.]", "");
                fields[6] = fields[6].replaceAll("[^\\d.]", "");
                fields[7] = fields[7].replaceAll("[^\\d.]", "");

                String correctedLine = String.join(",", fields);
                outputLines.add(correctedLine);
            }

            Files.write(Paths.get(outputFileName), outputLines);
            System.out.println("CSV file processed and saved as " + outputFileName);
        } catch (IOException e) {
            System.err.println("Error processing the file: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }
}