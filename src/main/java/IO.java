import Structs.ArrayUnorderedList;
import Structs.NetworkGraph;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class IO {

    /**
     * Reads from .json file in order to populate the graph with the
     * data contained in the mission
     *
     * @param path name of the .json file
     * @return Mission populated
     * @throws IOException
     * @throws ParseException
     */
    public Mission importMission(String path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
            File file = new File("data/" + path);

            if (file.length() != 0) {

                NetworkGraph ng = new NetworkGraph();

                Object obj = parser.parse(new FileReader(file));
                JSONObject map = (JSONObject) obj;

                //Mission info
                String missionCode = (String) map.get("cod-missao");
                int version = ((Double) map.get("versao")).intValue();

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
                    Double ePower = (Double) e.get("poder");
                    String eZone = (String) e.get("divisao");
                    enemies.addToRear(new Enemy(eName, ePower, eZone));
                    for (int j = 0; j<ng.size(); j++) {
                        ng.addEdge(ng.getVertices()[i], eZone, ePower);
                    }
                }

                return new Mission(ng, entryExit, target, enemies, missionCode, version);
            }

        return null;
    }

}
