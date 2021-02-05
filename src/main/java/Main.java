import Exceptions.EmptyCollectionException;
import Exceptions.NotComparableException;
import Structs.ArrayList;
import Structs.ArrayUnorderedList;
import org.json.simple.parser.ParseException;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    public static void clearScreen() {
        for (int i = 0; i < 50; ++i) System.out.println();
        System.out.flush();
    }

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
            System.out.println(" ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n\n");

            System.out.print("Your option: ");
            option = s.nextLine();
            clearScreen();
            try {

                switch (Integer.parseInt(option)) {
                    case 1: {
                        String path = chooseMap();
                        Simulation simulation = new Simulation(path, p);

                        Iterator i = simulation.getAutomaticSimulation();
                        while (i.hasNext()) {
                            System.out.print(i.next() + " -> ");
                        }

                    }
                    break;
                    case 2: {
                        String path = chooseMap();
                        Simulation simulation = new Simulation(path, p);

                        playMusic("MissionImpossible.wav");
                        simulation.getManualSimulation();

                        backToMenu();
                        break;
                    }
                    case 3:

                        Scanner cs = new Scanner(System.in);
                        Iterator missions = IO.readMission();
                        ArrayUnorderedList<String> listMissions = new ArrayUnorderedList<>();

                        while (missions.hasNext()){
                            listMissions.addToRear((String) missions.next());
                        }
                        System.out.println("Choose the mission you want to analyze: ");
                        for(int i = 0; i < listMissions.size(); i++){
                            System.out.println((i + 1) + " - " + listMissions.index(i));
                        }

                        int c = cs.nextInt();
                        while (c < 1 || c > listMissions.size()) {
                            System.out.println("It's not time to joke around, select a valid option");
                            c = cs.nextInt();
                        }

                        Iterator list = IO.missionResults();

                        while (list.hasNext()) {
                            MissionResult mission = (MissionResult) list.next();
                            if(mission.getCodMission().equals(listMissions.index(c-1)))
                                System.out.print(mission);
                        }

                        backToMenu();
                        break;

                    case 4: {
                        String path = chooseMap();
                        Simulation simulation = new Simulation(path, p);

                        System.out.println(simulation.getMap());
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
                e.printStackTrace();
            } catch (NotComparableException e) {
                e.printStackTrace();
            } catch (EmptyCollectionException e) {
                e.printStackTrace();
            }
        }
    }


    public static String chooseMap() {

        System.out.println("Roger that. Choose a mission to deploy");

        File folder = new File("data/maps");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println((i + 1) + " - " + listOfFiles[i].getName());
            }
        }

        Scanner s = new Scanner(System.in);
        int option = s.nextInt();
        while (option < 1 || option > listOfFiles.length) {
            System.out.println("It's not time to joke around, select a valid option");
            option = s.nextInt();
        }

        return listOfFiles[option - 1].getName();
    }

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

        System.out.println("█░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░▰░█");
        System.out.println("\n" +
                "▀█▀ █▀▄▀█  █▀▀█ █▀▀█ █▀▀█ █▀▀▄ █▀▀█ █▀▀▄ █░░ █▀▀ 　 ▒█▀▄▀█ ░▀░ █▀▀ █▀▀ ░▀░ █▀▀█ █▀▀▄ \n" +
                "▒█░ █░▀░█ █░░█ █▄▄▀ █░░█ █▀▀▄ █▄▄█ █▀▀▄ █░░ █▀▀ 　 ▒█▒█▒█ ▀█▀ ▀▀█ ▀▀█ ▀█▀ █░░█ █░░█ \n" +
                "▄█▄ ▀░░░▀ █▀▀▀ ▀░▀▀ ▀▀▀▀ ▀▀▀░ ▀░░▀ ▀▀▀░ ▀▀▀ ▀▀▀ 　 ▒█░░▒█ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀▀ ▀░░▀ \n" +
                "\n" +
                "▒█▀▀▀ █▀▀█ █▀▀█ █▀▀ █▀▀ \n" +
                "▒█▀▀▀ █░░█ █▄▄▀ █░░ █▀▀ \n" +
                "▒█░░░ ▀▀▀▀ ▀░▀▀ ▀▀▀ ▀▀▀ \n");

        System.out.println("Welcome aboard \u001B[36m" + p.getName() + "\u001B[32m");

        printMenu(p);
    }
}