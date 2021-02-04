import Exceptions.EmptyCollectionException;
import Structs.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class IO<T> {

    /**
     * Reads from .json file in order to populate the graph with the
     * data contained in the mission
     *
     * @param path name of the .json file
     * @return Mission populated
     * @throws IOException
     * @throws ParseException
     */
    public static Mission importMission(String path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
            File file = new File("data/" + path);

            if (file.length() != 0) {

                NetworkGraph ng = new NetworkGraph();

                Object obj = parser.parse(new FileReader(file));
                JSONObject map = (JSONObject) obj;

                //Mission info
                String missionCode = (String) map.get("cod-missao");
                int version = ((Long) map.get("versao")).intValue();

                //Target info
                JSONObject jsonTarget = (JSONObject) map.get("alvo");
                String tZone = (String) jsonTarget.get("divisao");
                String tType = (String) jsonTarget.get("tipo");
                Target target = new Target(tType, tZone);

                //Zones
                JSONArray jsonZones = (JSONArray) map.get("edificio");

                for (int i = 0; i<jsonZones.size(); i++){
                    String zone = (String) jsonZones.get(i);
                    ng.addVertex(zone);
                }

                //Entries and exits
                JSONArray jsonEntryExit = (JSONArray) map.get("entradas-saidas");
                ArrayUnorderedList<String> entryExit = new ArrayUnorderedList<>();

                for (int i = 0; i<jsonEntryExit.size(); i++){
                    entryExit.addToRear((String) jsonEntryExit.get(i));
                }

                //Edges
                JSONArray jsonEdgesArray = (JSONArray) map.get("ligacoes");

                for (Object o : jsonEdgesArray){
                    JSONArray edgesArray = (JSONArray) o;
                    ng.addEdge(edgesArray.get(0), edgesArray.get(1));
                }

                //Enemies
                JSONArray jsonEnemies = (JSONArray) map.get("inimigos");
                ArrayUnorderedList<Enemy> enemies = new ArrayUnorderedList<>();

                for (int i = 0; i<jsonEnemies.size(); i++){
                    JSONObject e = (JSONObject) jsonEnemies.get(i);
                    String eName = (String) e.get("nome");
                    Double ePower = ((Number) e.get("poder")).doubleValue();
                    String eZone = (String) e.get("divisao");
                    enemies.addToRear(new Enemy(eName, ePower, eZone));
                    for (int j = 0; j<ng.size(); j++) {
                        if (ng.getVertices()[j].equals(eZone)){
                            ng.addEdge(ng.getVertices()[j], eZone, ePower);
                        }
                        if (ng.getAdjMatrix()[j][ng.getIndex(eZone)]) {
                            ng.addEdge(ng.getVertices()[j], eZone, ePower);
                        }
                    }
                }

                return new Mission(ng, entryExit, target, enemies, missionCode, version);
            }

        return null;
    }

    public static void exportMission(LinkedQueue path, double health, String missionCode, int version)
            throws IOException, ParseException, EmptyCollectionException {

        JSONParser parser = new JSONParser();
        File file = new File("data/missionResults.json");
        JSONArray resultsArray = new JSONArray();

        if (file.length() != 0) {
            Object obj = parser.parse(new FileReader(file));
            JSONObject jsonResults = (JSONObject) obj;
            resultsArray = (JSONArray) jsonResults.get("results");
        }

        JSONObject newResult = new JSONObject();

        newResult.put("health", health);
        newResult.put("missionCode", missionCode);
        newResult.put("version", version);

        Date date = new Date();
        SimpleDateFormat formattedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        newResult.put("date", formattedDate.format(date));

        JSONArray jsonPath = new JSONArray();
        while (!path.isEmpty()){
            jsonPath.add(path.dequeue());
        }

        newResult.put("path", jsonPath);
        resultsArray.add(newResult);

        JSONObject results = new JSONObject();
        results.put("results", resultsArray);

            FileWriter fileWriter = new FileWriter("data/missionResults.json");
            fileWriter.write(results.toJSONString());
            fileWriter.flush();
    }

    public static Iterator missionResults() throws IOException, ParseException{

        JSONParser parser = new JSONParser();
        File file = new File("data/missionResults.json");
        LinkedHeap<MissionResult> listResults = new LinkedHeap<>();

        if (file.length() != 0) {

            Object obj = parser.parse(new FileReader(file));
            JSONObject allResults = (JSONObject) obj;
            JSONArray results = (JSONArray) allResults.get("results");

            for(Object o : results){

                ArrayUnorderedList<String> path = new ArrayUnorderedList<>();
                JSONObject result = (JSONObject) o;

                String missionCode = (String) result.get("missionCode");
                int version = ((Long) result.get("version")).intValue();
                double health = (double) result.get("health");
                String date = (String) result.get("date");

                JSONArray jsonPath = (JSONArray) result.get("path");

                for(Object i : jsonPath){

                    String zone = (String) i;
                    path.addToRear(zone);
                }

                listResults.addElement(new MissionResult(health, path, version, missionCode, date));

            }

            return listResults.iteratorLevelOrder();
        }

        return null;
    }

}
