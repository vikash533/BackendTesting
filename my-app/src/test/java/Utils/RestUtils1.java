package Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.File;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestUtils1 {
	public static ObjectMapper objectMapper = new ObjectMapper();
    public static  String readAndParseFile(String templateFilePath,String dataFilePath) throws IOException {
        objectMapper = new ObjectMapper();
        String templateJson = new String(Files.readAllBytes(Paths.get(templateFilePath)));
        Map<String,String> dataMap = objectMapper.readValue(new File(dataFilePath), new TypeReference<Map<String, String>>() {
        });
        for(Map.Entry<String,String> entry:dataMap.entrySet()){
            templateJson=templateJson.replace("{{"+entry.getKey()+"}}",entry.getValue());
        }
        JsonNode formattedJson = objectMapper.readTree(templateJson);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(formattedJson);
    }
    public static String readAndParseFileWithJson(String templateFilePath, Map<String, Object> jsonData) throws IOException {
        String templateJson = new String(Files.readAllBytes(Paths.get(templateFilePath)));
            for (Map.Entry<String, Object> entry : jsonData.entrySet()) {
                templateJson = templateJson.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
            }

        JsonNode formattedJson = objectMapper.readTree(templateJson);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(formattedJson);
    }
    public static String readAndParserFileWithList(String templateFilePath,String dataFilePath) throws IOException {
        objectMapper= new ObjectMapper();
        String templateJson = new String(Files.readAllBytes(Paths.get(templateFilePath)));
        List<Map<String, Object>> dataList = objectMapper.readValue(new File(dataFilePath), new TypeReference<List<Map<String, Object>>>() {
        });
        for(var Singledata:dataList){
            for(Map.Entry<String,Object> entry:Singledata.entrySet()){
                templateJson=templateJson.replace("{{"+entry.getKey()+"}}",entry.getValue().toString());
            }
        }
        JsonNode formattedJson = objectMapper.readTree(templateJson);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(formattedJson);
    }
    public static void Compare(String expected,String actual) throws JSONException{
        JSONAssert.assertEquals(expected,actual, JSONCompareMode.NON_EXTENSIBLE);
    }

}
