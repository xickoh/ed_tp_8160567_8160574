package Main;

import Exceptions.EmptyCollectionException;
import Exceptions.NotComparableException;
import Structs.*;
import org.json.simple.parser.ParseException;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    /**
     * Clears the screen adding empty lines
     */
    public static void clearScreen() {
        for (int i = 0; i < 50; ++i) System.out.println();
        System.out.flush();
    }

    /**
     * Asks the player continuously for a valid option
     * @param number
     * @return
     */
    public static int readValidInput(int number) {
        Scanner sc = new Scanner(System.in);
        int position = 0;

        if (sc.hasNextInt()) {
            position = sc.nextInt();
        } else sc.nextLine();

        while (position < 1 || position > number) {
            System.out.println("It's not time to joke around, select a valid option");

            System.out.println("\nInsert a valid position: ");
            if (sc.hasNextInt()) {
                position = sc.nextInt();
            } else {
                sc.nextLine();
            }
        }

        return position;
    }

    public static void printAutomaticSimulation(Iterator<Room> i, Agent p){
        Boolean success = false;

        if (i.hasNext()) {
            //Starting Room
            Room startingRoom = i.next();
            System.out.print("\n" + startingRoom.getRoom());

            if (startingRoom.getEnemiesPower()>0){
                p.setHealth(p.getHealth() - startingRoom.getEnemiesPower());
                System.out.print(" - Damage points: " + startingRoom.getEnemiesPower() + " - Current health: " + p.getHealth());
            }
            System.out.print("\n");

            while (i.hasNext()) {
                Room room = i.next();
                System.out.print("     |\n     v\n" + i.next().getRoom());
                if (room.getEnemiesPower()>0){
                    p.setHealth(p.getHealth() - room.getEnemiesPower());
                    System.out.print(" - Damage points: " + room.getEnemiesPower() + " - Current health: " + p.getHealth());
                }
                if (room.getTarget()!=null){
                    System.out.print(" - Target acquired, current health: " + p.getHealth());
                    success = (p.getHealth() == 100) ? true:false;
                }
                System.out.print("\n");
            }
        }

        System.out.println("\nFinal health: " + p.getHealth());
        if (p.getHealth()>0 && success){
            System.out.println("Mission accomplished!");
        }
        else{
            System.out.println("Mission failed, I need to perform better next time");
        }

    }

    /**
     * Plays the theme music
     * @param filepath path to the music file
     */
    public static void  playMusic(String filepath) {
        try {
            File musicPath = new File(filepath);

            if (musicPath.exists()) {

                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {

                System.out.println("Can't find music");
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    /**
     * Prints the options menu to the console
     * @param p The agent that will perform the simulations
     */
    public static void printMenu(Agent p){
        Scanner s = new Scanner(System.in);
        String option = "0";
        while (Integer.parseInt(option)!=5) {

            System.out.println("\u001B[0m _______________________________________");
            System.out.println("│ Select an option:                     │");
            System.out.println("│   ─────────────────────────────────   │▒");
            System.out.println("│  │ 1- Automatic Simulation         │  │▒");
            System.out.println("│  │ 2- Manual Simulation            │  │▒");
            System.out.println("│  │ 3- History                      │  │▒");
            System.out.println("│  │ 4- Map View                     │  │▒");
            System.out.println("│   ─────────────────────────────────   │▒");
            System.out.println("│_______________________________________│▒");
            System.out.println("│ 5- Exit                               │▒");
            System.out.println("|_______________________________________|▒");
            System.out.println("   ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n\n");

            System.out.print("Your option: ");
            option = s.nextLine();

            clearScreen();

            try {

                switch (Integer.parseInt(option)) {
                    case 1: {
                        String path = chooseMap();
                        Simulation simulation = new Simulation(path, p);

                        if (simulation.missionValid()) {

                            Iterator i = simulation.getAutomaticSimulation();

                            printAutomaticSimulation(i,p);
                        } else {
                            System.out.println("\u001B[32mThis mission is corrupted. I refuse to set foot in that building\u001B[0m");
                        }
                        backToMenu();
                    }
                    break;
                    case 2: {
                        String path = chooseMap();
                        Simulation simulation = new Simulation(path, p);

                        if (simulation.missionValid()) {
//                            playMusic("MissionImpossible.wav");
                            simulation.getManualSimulation();
                        }
                        else {
                            System.out.println("\u001B[32mThis mission is corrupted. I refuse to set foot in that building\u001B[0m");
                        }

                        backToMenu();
                        break;
                    }
                    case 3:

                        Scanner cs = new Scanner(System.in);
                        Iterator missions = IO.readMission();
                        ArrayUnorderedList<String> listMissions = new ArrayUnorderedList<>();
                        ArrayUnorderedList<Integer> listVersions = new ArrayUnorderedList<>();

                        while (missions.hasNext()){
                            listMissions.addToRear((String) missions.next());
                        }
                        System.out.println("Choose the mission you want to analyze: ");
                        for(int i = 0; i < listMissions.size(); i++){
                            System.out.println((i + 1) + " - " + listMissions.index(i));
                        }

                        int c = readValidInput(listMissions.size());

                        Iterator<Integer> versions = IO.readVersion(listMissions.index(c-1));

                        while (versions.hasNext()){
                            listVersions.addToRear(versions.next());
                        }

                        System.out.println("Choose the version you want to analyze: ");
                        for(int i = 0; i < listVersions.size(); i++){
                            System.out.println((i + 1) + " - " + listVersions.index(i));
                        }

                        int b = cs.nextInt();
                        while (b < 1 || b > listVersions.size()) {
                            System.out.println("It's not time to joke around, select a valid option");
                            b = cs.nextInt();
                        }

                        Iterator list = IO.missionResults();

                        while (list.hasNext()) {

                            MissionResult mission = (MissionResult) list.next();
                            if(mission.getCodMission().equals(listMissions.index(c-1)) &&
                                    mission.getVersion() == listVersions.index(b-1)){

                                System.out.print(mission);
                            }
                        }

                        backToMenu();
                        break;

                    case 4: {
                        String path = chooseMap();
                        Simulation simulation = new Simulation(path, p);


                        if (simulation.missionValid()) {
                            System.out.println(simulation.getMap());
                        } else {
                            System.out.println("\u001B[32mThis mission is corrupted. I refuse to set foot in that building\u001B[0m");
                        }

                        backToMenu();
                        break;
                    }
                    case 5:
                        System.out.println("\u001B[32mUsually I never quit, but this is too much\u001B[0m");
                        return;
                    default:
                        System.out.println("\u001B[32mOption " + option + " is not an option, that would kill me\u001B[0m");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                System.out.println("\u001B[32mOption " + option + " is not an option, that would kill me\u001B[0m");
            } catch (NumberFormatException e){
                System.out.println("\u001B[32mOption " + option + " is not an option, that would kill me\u001B[0m");
                option = "0";
            } catch (NotComparableException e) {
                e.printStackTrace();
            } catch (EmptyCollectionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Displays which maps are avaiable to play the simulations
     * @return the name of the map file
     */
    public static String chooseMap() {

        System.out.println("Roger that. Choose a mission to deploy");

        File folder = new File("data/maps");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println((i + 1) + " - " + listOfFiles[i].getName());
            }
        }

        int option = readValidInput(listOfFiles.length);

        return listOfFiles[option - 1].getName();
    }

    /**
     * Waits for the user input to return to the menu
     */
    public static void backToMenu(){

        Scanner sc = new Scanner(System.in);
        System.out.println("\n\nPress any key to go back to the menu");
        sc.nextLine();

    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        System.out.println("\n" +
                "██▓  ███▄ ▄███▓  ██████▒\n" +
                "██▒  ██▒▀█▀ ██▒  ██░     \n" +
                "██▒  ██░    ██░  ████░   \n" +
                "██░  ██░    ██░  ██▒    \n" +
                "██░  ██▒    ██▒  ██░    \n");


        System.out.println("The agency Improbable Mission Force wants to know your name.");
        System.out.print("\u001B[32mI never tell my real name, but you can call me... ");
        Agent p = new Agent(s.nextLine());

        clearScreen();

        System.out.println("█░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░█");
        System.out.println("\n" +
                "▀█▀ █▀▄▀█  █▀▀█ █▀▀█ █▀▀█ █▀▀▄ █▀▀█ █▀▀▄ █░░ █▀▀ 　 ▒█▀▄▀█ ░▀░ █▀▀ █▀▀ ░▀░ █▀▀█ █▀▀▄ \n" +
                "▒█░ █░▀░█ █░░█ █▄▄▀ █░░█ █▀▀▄ █▄▄█ █▀▀▄ █░░ █▀▀ 　 ▒█▒█▒█ ▀█▀ ▀▀█ ▀▀█ ▀█▀ █░░█ █░░█ \n" +
                "▄█▄ ▀░░░▀ █▀▀▀ ▀░▀▀ ▀▀▀▀ ▀▀▀░ ▀░░▀ ▀▀▀░ ▀▀▀ ▀▀▀ 　 ▒█░░▒█ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀▀ ▀░░▀ \n" +
                "\n" +
                "▒█▀▀▀ █▀▀█ █▀▀█ █▀▀ █▀▀ \n" +
                "▒█▀▀▀ █░░█ █▄▄▀ █░░ █▀▀ \n" +
                "▒█░░░ ▀▀▀▀ ▀░▀▀ ▀▀▀ ▀▀▀ \n");
        System.out.println("█░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░█");
        System.out.println("Welcome aboard \u001B[36m" + p.getName() + "\u001B[32m");

        printMenu(p);
    }
}