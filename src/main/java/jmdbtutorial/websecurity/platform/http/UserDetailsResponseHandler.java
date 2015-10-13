package jmdbtutorial.websecurity.platform.http;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class UserDetailsResponseHandler implements ResponseHandler {

    public static String[] EMPTY_ARR = new String[0];

    public static Map<String, String[]> parseUserDetails(String userDetails) {
        String[] items = userDetails.split("userdetails.");
        Map<String, String[]> data = new HashMap<String, String[]>();
        AttributeBuilder attributeBuilder = null;
        for (String item : items) {
            if (item.length() == 0) {
                continue;
            }


            if (item.startsWith("role")) {
                addRole(data, item);
            } else {
                String[] strings = item.split("=");
                if ("attribute.name".equals(strings[0])) {
                    if (attributeBuilder != null) {
                        attributeBuilder.addTo(data);
                    }
                    attributeBuilder = new AttributeBuilder();
                    attributeBuilder.name(strings[1]);
                } else if ("attribute.value".equals(strings[0])) {
                    attributeBuilder.value(strings[1]);
                } else {
                    data.put(strings[0], new String[]{strings[1]});
                }
            }

        }

        return data;
    }

    private static void addRole(Map<String, String[]> data, String item) {
        String toParse = item.substring("role=".length());
        String[] strings = toParse.split(",");

        String role = strings[0].split("=")[1];
        if (!data.containsKey("roles")) {
            data.put("roles", new String[]{role});
        } else {
            String[] values = data.get("roles");
            ArrayList<String> strings1 = new ArrayList<String>(asList(values));
            strings1.add(role);
            data.put("roles", strings1.toArray(EMPTY_ARR));
        }
    }

    static String debugArray(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for (String item : arr) {
            sb.append(item).append(", ");
        }
        String result = sb.toString();
        return result.substring(0, result.length() - 2);
    }

    @Override
    public Map processResponseBody(CloseableHttpResponse response, StringBuffer responseBody) throws IOException {
        return parseUserDetails(responseBody.toString());
    }

    public static class AttributeBuilder {


        private List<String> values = new ArrayList<String>();
        private String name;

        public void name(String name) {
            this.name = name;
        }

        public void addTo(Map<String, String[]> data) {
            data.put(name, values.toArray(EMPTY_ARR));
        }

        public void value(String value) {
            this.values.add(value);
        }
    }


}
