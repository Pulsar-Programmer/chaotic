import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Sound {
    File[] arr;
    Clip clip;

    // nah we good.
    public Sound() {
        arr = new File[30];
        
        arr[0] = new File("res/sounds/death_screen.wav");
        arr[1] = new File("res/sounds/battle.wav");
        arr[2] = new File("res/sounds/ambience_air_conditioner.wav");
        arr[3] = new File("res/sounds/bat_hit.wav");
        arr[4] = new File("res/sounds/body_fall.wav");
        arr[5] = new File("res/sounds/button_assorted.wav");
        arr[6] = new File("res/sounds/chute_open.wav");
        arr[7] = new File("res/sounds/fencing_hit.wav");
        arr[8] = new File("res/sounds/melody_hit.wav");
        arr[9] = new File("res/sounds/punch.wav");
        arr[10] = new File("res/sounds/stab.wav");
        arr[11] = new File("res/sounds/swoosh.wav");
        arr[12] = new File("res/sounds/untitled.wav");
        arr[13] = new File("res/sounds/water.wav");
        arr[14] = new File("res/sounds/woosh.wav");
        arr[15] = new File("res/sounds/8bitdungeonboss.wav");
        arr[16] = new File("res/sounds/streets.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(arr[i]);
            clip = AudioSystem.getClip();
            clip.open(a);
            FloatControl gainControl = 
            (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            if(i==1){
                gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-20.0f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY); //"this.clip" is null
    }

    public void stop() {
        clip.stop();
    }

    public void start() {
        clip.start();
    }
}