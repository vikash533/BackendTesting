package Testing.LearnObjectMapperConcept;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class First {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = new Car("yellow", "renault");
//        objectMapper.writeValue(new File("outputdata/car.json"), car);
        String carAsString = objectMapper.writeValueAsString(car);


    }
}
