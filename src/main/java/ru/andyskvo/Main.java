package ru.andyskvo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    //list for no valid nodes
    private static List<Long> blackIdList = new ArrayList<>();

    static {
        blackIdList.add(3816414884L);
        blackIdList.add(3805728883L);
        blackIdList.add(27072632L);
        blackIdList.add(1249401878L);
        blackIdList.add(3768691783L);
    }

    public static void main(String[] args) {
        try {
            String fileName = getFileName();

            JSONObject jsonFileObject = getJsonFileObject(fileName);

            JSONArray jsonFeaturesArray = (JSONArray) jsonFileObject.get("features");
            Iterator featuresIterator = jsonFeaturesArray.iterator();

            DataBase.connect();

            while (featuresIterator.hasNext()) {
                Town currentTown = getTown(featuresIterator);
                if (!blackIdList.contains(currentTown.getId())) {
                    DataBase.insertTown(currentTown);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return geojson file name
     */
    public static String getFileName() throws IOException{
        String fileName = "";

        System.out.println("Enter geojson file name");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        fileName = bufferedReader.readLine();
        bufferedReader.close();

        return fileName;
    }

    private static JSONObject getJsonFileObject(String fileName) throws IOException {
        JSONObject jsonObject = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            fileReader.close();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static Town getTown(Iterator featuresIterator) {
        JSONObject featuresInner = (JSONObject) featuresIterator.next();

        //get node id
        String nodeId = (String) featuresInner.get("id");
        Long id = Long.parseLong(nodeId.substring(nodeId.indexOf("/") + 1));

        //get coordinates
        JSONObject jsonGeometryObj = (JSONObject) featuresInner.get("geometry");
        JSONArray jsonCoordinatesArr = (JSONArray) jsonGeometryObj.get("coordinates");
        double longitude = Double.parseDouble(jsonCoordinatesArr.get(0).toString());
        double latitude = Double.parseDouble(jsonCoordinatesArr.get(1).toString());

        //get name, region and country
        JSONObject jsonPropertiesObj = (JSONObject) featuresInner.get("properties");
        String nameRu = (String) jsonPropertiesObj.get("name:ru");
        String nameEn = (String) jsonPropertiesObj.get("name:en");
        String nameUk = (String) jsonPropertiesObj.get("name:uk");
        String nameBe = (String) jsonPropertiesObj.get("name:be");

        return new Town(id, latitude, longitude, nameRu, nameEn, nameUk, nameBe);
    }
}
