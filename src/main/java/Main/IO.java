package Main;

import Exceptions.EmptyCollectionException;
import Structs.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
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
        File file = new File("data/maps/" + path);

        if (file.length() != 0) {
            Graph<Room> ng = new Graph();

            Object obj = parser.parse(new FileReader(file));
            JSONObject map = (JSONObject) obj;

            //Mission info
            String missionCode = (String) map.get("cod-missao");
            int version = ((Long) map.get("versao")).intValue();


            //Zones
            JSONArray jsonZones = (JSONArray) map.get("edificio");

            if(jsonZones!=null) {
                for (int i = 0; i < jsonZones.size(); i++) {
                    Room newRoom = new Room((String) jsonZones.get(i));
                    ng.addVertex(newRoom);
                }
            }

            //Target info
            Target target = null;
            JSONObject jsonTarget = (JSONObject) map.get("alvo");

            if( jsonTarget != null) {
                Room searchRoom = new Room((String) jsonTarget.get("divisao"));
                target = new Target((String) jsonTarget.get("tipo"));
                if (target.getType() != null && ng.getIndex(searchRoom) != -1) {
                    ng.getVertex(searchRoom).setTarget(target);
                }
            }

            //Entries
            JSONArray jsonEntry = (JSONArray) map.get("entradas");
            LinkedList<Room> entries = new LinkedList<>();

            if (jsonEntry!=null) {
                for (int i = 0; i < jsonEntry.size(); i++) {
                    Room entry = new Room((String) jsonEntry.get(i));
                    if (ng.getIndex(entry) != -1) {
                        entries.add(ng.getVertex(entry));
                    }
                }
            }

            //Exits
            JSONArray jsonExits = (JSONArray) map.get("saidas");
            LinkedList<Room> exits = new LinkedList<>();

            if (jsonExits!=null) {
                for (int i = 0; i < jsonExits.size(); i++) {
                    Room exit = new Room((String) jsonExits.get(i));
                    if (ng.getIndex(exit) != -1) {
                        exits.add(ng.getVertex(exit));
                    }
                }
            }

            //Edges
            JSONArray jsonEdgesArray = (JSONArray) map.get("ligacoes");

            for (Object o : jsonEdgesArray){
                JSONArray edgesArray = (JSONArray) o;
                int index1 = ng.getIndex(new Room((String)edgesArray.get(0)));
                int index2 = ng.getIndex(new Room((String)edgesArray.get(1)));
                if (index1 != -1 && index2 != -1) {
                    ng.addEdge(ng.getVertex(index1), ng.getVertex(index2));
                }
            }

            //Enemies
            JSONArray jsonEnemies = (JSONArray) map.get("inimigos");

            for (Object enemyObj : jsonEnemies){
                JSONObject e = (JSONObject) enemyObj;
                String eName = (String) e.get("nome");
                Double ePower = ((Number) e.get("poder")).doubleValue();
                Room room = new Room((String) e.get("divisao"));

                //Checks if vertix exists
                if (ng.getIndex(room) != -1) {

                    ng.getVertex(room).getEnemies().add(new Enemy(eName, ePower));
                }
            }

            if (missionCode == null || target == null || ng.size() == 0 || entries.isEmpty() || exits.isEmpty()){
                return null;
            }
            return new Mission(ng ,entries, exits, missionCode, version);
        }

        return null;
    }

    /**
     * Exports the results of the mission to the JSON file missionCorrupted.json
     *
     * @param path Path followed by the agent
     * @param health Health of the agent
     * @param missionCode Code of the mission
     * @param version Version of the mission
     * @throws IOException
     * @throws ParseException
     * @throws EmptyCollectionException
     */
    public static void exportMission(LinkedQueue<Room> path, double health, String missionCode, int version)
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
        JSONArray jsonEventsArray = new JSONArray();

        while (!path.isEmpty()){
            Room room = path.dequeue();

            jsonPath.add(room.getRoom());

            if (room.getEnemiesPower()>0){
                JSONObject event = new JSONObject();
                Iterator<Enemy> enemiesItr = room.getEnemies().iterator();
                while (enemiesItr.hasNext()){
                    Enemy e = enemiesItr.next();
                    event.put("description", e.getName() + " inflicted " + e.getPower() + " hitpoints");
                }
                event.put("room", room.getRoom());
                jsonEventsArray.add(event);
            }

            if (room.getTarget()!=null){
                JSONObject event = new JSONObject();
                event.put("description", "Target acquired");
                event.put("room", room.getRoom());
                jsonEventsArray.add(event);
            }
        }

        newResult.put("path", jsonPath);
        resultsArray.add(newResult);

        newResult.put("events", jsonEventsArray);

        JSONObject results = new JSONObject();
        results.put("results", resultsArray);

        FileWriter fileWriter = new FileWriter("data/missionResults.json");
        fileWriter.write(results.toJSONString());
        fileWriter.flush();
    }

    /**
     * This method is used to load the results into the program
     *
     * @return Returns a instance of iterator
     * @throws IOException
     * @throws ParseException
     * @throws EmptyCollectionException
     */
    public static Iterator missionResults() throws IOException, ParseException, EmptyCollectionException{

        JSONParser parser = new JSONParser();
        File file = new File("data/missionResults.json");
        PriorityQueue<MissionResult> listResults = new PriorityQueue<MissionResult>();
        ArrayUnorderedList<MissionResult> list = new ArrayUnorderedList<>();

        if (file.length() != 0) {

            Object obj = parser.parse(new FileReader(file));
            JSONObject allResults = (JSONObject) obj;
            JSONArray results = (JSONArray) allResults.get("results");

            for(Object o : results){

                ArrayUnorderedList<String> path = new ArrayUnorderedList<>();
                ArrayUnorderedList<Event> events = new ArrayUnorderedList<>();
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

                JSONArray jsonEvents = (JSONArray) result.get("events");


                for(Object i : jsonEvents){
                    JSONObject event = (JSONObject) i;
                    Event newEvent = new Event((String) event.get("description"),(String) event.get("room"));

                    events.addToRear(newEvent);
                }

                listResults.addElement((new MissionResult(health, path, events, version, missionCode, date)), (int)health);
            }

            while(!listResults.isEmpty()){
                list.addToRear(listResults.removeNext());
            }

            return list.iterator();
        }

        return null;
    }

    /**
     * This method is used to load the mission information 
     *
     * @return Returns a instance of iterator
     * @throws IOException
     * @throws ParseException
     * @throws EmptyCollectionException
     */
    public static Iterator readMission() throws IOException, ParseException, EmptyCollectionException{

        JSONParser parser = new JSONParser();
        File file = new File("data/missionResults.json");
        ArrayUnorderedList<String> listMissions = new ArrayUnorderedList<>();

        if (file.length() != 0) {

            Object obj = parser.parse(new FileReader(file));
            JSONObject allResults = (JSONObject) obj;
            JSONArray results = (JSONArray) allResults.get("results");

            for (Object o : results) {

                JSONObject result = (JSONObject) o;
                String missionCode = (String) result.get("missionCode");

                if(!listMissions.contains(missionCode)){

                    listMissions.addToRear(missionCode);

                }
            }
        }

        return listMissions.iterator();
    }

    public static Iterator readVersion(String codMission) throws IOException, ParseException, EmptyCollectionException{

        JSONParser parser = new JSONParser();
        File file = new File("data/missionResults.json");
        ArrayUnorderedList<Integer> listVersions = new ArrayUnorderedList<>();

        if (file.length() != 0) {

            Object obj = parser.parse(new FileReader(file));
            JSONObject allResults = (JSONObject) obj;
            JSONArray results = (JSONArray) allResults.get("results");

            for (Object o : results) {

                JSONObject result = (JSONObject) o;
                int missionVersion = ((Long) result.get("version")).intValue();
                String missionCod = (String) result.get("missionCode");

                if(!listVersions.contains(missionVersion) && missionCod.equals(codMission)){

                    listVersions.addToRear(missionVersion);

                }
            }
        }

        return listVersions.iterator();
    }
}