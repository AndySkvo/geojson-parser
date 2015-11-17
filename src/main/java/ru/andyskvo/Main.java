package ru.andyskvo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;

/**
 * Created by Skvorcov on 17.11.2015.
 */
public class Main {
    public static void main(String[] args) {
        try {
            String fileName = getFileName();
            JSONObject jsonFileObject = getJsonFileObject(fileName);

            JSONArray jsonFeaturesArray = (JSONArray) jsonFileObject.get("features");
            Iterator featuresIterator = jsonFeaturesArray.iterator();
            while (featuresIterator.hasNext()) {
                JSONObject featuresInner = (JSONObject) featuresIterator.next();

                //get node id
                String nodeId = (String) featuresInner.get("id");
                Long id = Long.parseLong(nodeId.substring(nodeId.indexOf("/") + 1));

                //get coordinates
                JSONObject jsonGeometryObj = (JSONObject) featuresInner.get("geometry");
                JSONArray jsonCoordinatesArr = (JSONArray) jsonGeometryObj.get("coordinates");
                double latitude = Double.parseDouble(jsonCoordinatesArr.get(0).toString());
                double longitude = Double.parseDouble(jsonCoordinatesArr.get(1).toString());

                //get name, region and country
                JSONObject jsonPropertiesObj = (JSONObject) featuresInner.get("properties");
                String name = (String) jsonPropertiesObj.get("name");
                System.out.println(id + " " + latitude + " " + longitude + " " + name);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getJsonFileObject(String fileName) {
        JSONObject jsonObject = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static String getFileName() {
        String fileName = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            fileName = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
