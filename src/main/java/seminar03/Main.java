package seminar03;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        Post post1 = new Post(1, new Date(), "I learned how to use jackson!",
                10, Arrays.asList("Well done!", "Great job!"), new Person("Ivan", 20));
        Post post2 = new Post(2, new Date(), "I learned java!",
                22, Arrays.asList("Well done!", "Great job!"), new Person("Anna", 30));

        ObjectMapper objectMapper = new ObjectMapper();

        String postAsString1 = objectMapper.writeValueAsString(post1);
        System.out.println(postAsString1);
        String postAsString2 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(post2);
        System.out.println(postAsString2);

        String inputJson = "{\"id\":3,\"createdDate\":1654027200000,\"content\":\"I learned how to use jackson!\",\"likes\":10,\"comments\":[\"Well done!\",\"Great job!\"],\"person\":{\"name\":\"Alice\",\"age\":20}}";
        Post post = objectMapper.readValue(inputJson, Post.class);
        System.out.println(post);

        File file = new File("post.json");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(postAsString2);
            bw.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Post deserializedPost = null;
        try {
            deserializedPost = objectMapper.readValue(file, Post.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
        System.out.println(deserializedPost);
    }
}
