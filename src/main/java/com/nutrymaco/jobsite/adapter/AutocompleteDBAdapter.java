package com.nutrymaco.jobsite.adapter;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AutocompleteDBAdapter {
    public static List<String> getWordsByQuery(String query) {
        if ("".equals(query)) {
            return List.of();
        }
        URL url = null;
        List<String> words = new ArrayList<>(10);
        try {
            url = new URL(String.format("http://localhost:7299/words?query=%s",
                    URLEncoder.encode(query, StandardCharsets.UTF_8)));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            int wsIndex = query.lastIndexOf(" ");
            String cutQuery;
            if (wsIndex == -1) {
                cutQuery = "";
            } else {
                cutQuery = query.substring(0, wsIndex + 1);
            }

            words = Arrays.stream(content.toString()
                    .substring(1, content.length() - 1)
                    .split(","))
                    .filter(l -> l.length() > 0)
                    .map(l -> cutQuery + l.substring(1, l.length() - 1))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Can't request to autocomplete-db");
        }

        return words;
    }

    static class ParameterStringBuilder {
        public static String getParamsString(Map<String, String> params)
                throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        }
    }
}
