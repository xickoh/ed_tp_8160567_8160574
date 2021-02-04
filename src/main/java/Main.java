import Exceptions.EmptyCollectionException;
import org.json.simple.parser.ParseException;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {

        //playMusic("MissionImpossible.wav");

        Agent p = new Agent("Kim");
        try {

            Simulation s = new Simulation("mission.json", p);

            Iterator i = s.getAutomaticSimulation();

            //s.getManualSimulation();

            Iterator h = IO.missionResults();

            while(h.hasNext()){

                System.out.print(h.next());
            }

/*            while(i.hasNext()){

                System.out.print(i.next()+" -> ");
            }*/
            System.out.print("Hotel ->");
            System.out.print(" Trivago ->");
            System.out.print(" Papar gajas, because it's TÓ fucking CRUZ");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void  playMusic(String filepath){

        try{

            File musicPath = new File(filepath);

            if(musicPath.exists()){

                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();

                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else {

                System.out.println("Can't find music");
            }
        }catch (Exception ex){

            ex.printStackTrace();
        }
    }
}