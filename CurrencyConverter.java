import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class CurrencyConverter {
    private static final String API_KEY = "4e561cc0b3e063b9053989fd"; // Replace with your API key
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static double getExchangeRate(String fromCurrency, String toCurrency) throws IOException {
        String urlString = BASE_URL + API_KEY + "/latest/" + fromCurrency;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HTTP Response Code: " + responseCode);
        }

        Scanner scanner = new Scanner(url.openStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        JSONObject json = new JSONObject(response.toString());
        return json.getJSONObject("conversion_rates").getDouble(toCurrency);
    }

    public static void main(String[] args) {
        try {
            String fromCurrency = "USD"; // Change as needed
            String toCurrency = "INR";   // Change as needed
            double amount = 100.0;       // Amount to convert

            double rate = getExchangeRate(fromCurrency, toCurrency);
            double convertedAmount = amount * rate;

            System.out.println(amount + " " + fromCurrency + " = " + convertedAmount + " " + toCurrency);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

