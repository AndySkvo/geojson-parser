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
    private static List<Long> blackIdList = new ArrayList<Long>();

    static {
        blackIdList.add(3816414884L);
        blackIdList.add(3805728883L);
        blackIdList.add(27025689L);
        blackIdList.add(27072632L);
        blackIdList.add(27017589L);
        blackIdList.add(1249401878L);
        blackIdList.add(3768691783L);
    }

    public static void main(String[] args) {
        String fileName = getFileName();
        try {
            JSONObject jsonFileObject = getJsonFileObject(fileName);

            JSONArray jsonFeaturesArray = (JSONArray) jsonFileObject.get("features");
            Iterator featuresIterator = jsonFeaturesArray.iterator();

            DataBase.Connect();

            while (featuresIterator.hasNext()) {
                Town currentTown = getTown(featuresIterator);
                int kp = getKp(currentTown.getLatitude());
                if (kp != 0 && !blackIdList.contains(currentTown.getId())) {
                    DataBase.insertTown(currentTown, kp);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return geojson file name
     */
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

    /**
     *  The calculation of the coefficient in longitude
     * @param latitude
     * @return kp index of solar activity
     */
    private static int getKp(double latitude) {
        int kp = 0;
        if (latitude  >= 70) {
            kp = 1;
        }
        else if (latitude < 70 && latitude >= 60) {
            kp = 3;
        }
        else if (latitude < 60 && latitude >= 57){
            kp = 5;
        }
        else if (latitude < 57 && latitude >= 54) {
            kp = 7;
        }
        else if (latitude < 54 && latitude >= 42) {
            kp = 9;
        }
        return kp;
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
        String name = (String) jsonPropertiesObj.get("name");

        return new Town(id, latitude, longitude, name);
    }
}
