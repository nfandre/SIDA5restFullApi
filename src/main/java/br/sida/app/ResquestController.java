package br.sida.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping(value = "/response", produces="application/json")
public class ResquestController {

    @GetMapping
    public Response getResponse() {
        Response res = new Response();
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/todos/1");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Falha : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            ObjectMapper objectMapper = new ObjectMapper();
            StringBuilder content = new StringBuilder();
            while ((output = br.readLine()) != null) {
                content.append(output);
            }
            res = objectMapper.readValue( content.toString(), Response.class);
            System.out.println(res.toString());
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  res;
    }
}
