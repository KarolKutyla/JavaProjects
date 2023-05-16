package GameView;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicBoard {

    static String filePath = "Resources/Sounds/";

    public static void playClip(String clipName)
    {
        Thread t = new Thread(()->{
            try {
                AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filePath+clipName+".wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                clip.start();
                do {
                    Thread.sleep(100);
                }
                while (clip.isActive());
                clip.stop();
                clip.close();
                stream.close();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        t.start();
    }
}
