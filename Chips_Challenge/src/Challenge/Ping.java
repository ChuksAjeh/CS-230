package Challenge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class Ping {

    private final String puzzleURL = "http://cswebcat.swan.ac.uk/puzzle";
    private final String messageURL = "http://cswebcat.swan.ac.uk/message?solution=";

    String getPing() {

        try {
            return pingAPI();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private String pingAPI() throws Exception {

        String inputString = makeConnection(puzzleURL);
        char[] inputArray = inputString.toCharArray();

        StringBuilder out = new StringBuilder();

        for (int i = 0 ; i < inputArray.length ; i++ ) {

            if (0 == i % 2) {
                out.append(shiftUp(inputArray[i]));
            } else {
                out.append(shiftDown(inputArray[i]));
            }

        }

        return makeConnection(messageURL + out);

    }

    private String makeConnection(String URL) throws Exception {

        java.net.URL url = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        return br.readLine();
    }

    private char shiftUp(char c) {
        return 'Z' == c ? 'A' : (char) (c + 1);
    }

    private char shiftDown(char c) {
        return 'A' == c ? 'Z' : (char) (c - 1);
    }

}
