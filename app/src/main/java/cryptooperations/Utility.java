package cryptooperations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    protected static String extractTxHashFromErrorMessage(String message) {
        // Regex to match transaction hash (length of 66 characters including "0x")
        String regex = "(0x[a-fA-F0-9]{64})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    // Single function to process the error message and format the result
    protected static String processTransactionFailure(String errorMessage) {
        // Use regex to extract the JSON part from the error message
        Pattern pattern = Pattern.compile("\\{.*\\}");
        Matcher matcher = pattern.matcher(errorMessage);
        String x = "Error parsing JSON or missing fields.";

        if (matcher.find()) {
            String jsonPart = matcher.group(0);

            // Extract the "message" and "reason" fields from the JSON string
            String message = extractFieldFromJson(jsonPart, "message");
            String reason = extractFieldFromJson(jsonPart, "reason");

            System.out.println("------------------------Inside error-----------" + message);

            // Return the formatted output in "message::reason" format
            if (message == null && reason != null) {
                x = reason;
            } else if (message != null && reason == null) {
                x = message;
            } else if (message != null && reason != null) {
                x = message + "::" + reason;
            } else if (message != null) {
                x = message;
            } else if (reason != null) {
                x = reason;
            }
        } else {
            String g = extractMessage(errorMessage);
            if(g != null) {
                x = g;
            }
        }
        return x;
    }

    // Helper method to extract a field's value from the JSON string
    private static String extractFieldFromJson(String json, String field) {
        // Regex to extract the value of a field in the JSON
        Pattern pattern = Pattern.compile("\"" + field + "\":\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static String extractMessageValue(String message) {
        // Regex pattern to extract the message
        String regex = "Message: (.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            // Extracted message
            String extractedMessage = matcher.group(1);
            return extractedMessage;
        }

        return null;
    }

    private static String extractMessage(String message) {
        String[] x = message.split("Message:");
        if(x.length >= 2) {
            return x[1].trim();
        } else {
            return message;
        }
    }
}
