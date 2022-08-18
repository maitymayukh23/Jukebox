package wavPlayer;

import entity.Record;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class PlayerConsole implements LineListener {
    public JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel DURATION;
    private JLabel timeLabel;
    private JTextField minTF;
    private JTextField secTF;
    private JTextField statusTF;
    private JButton PLAYButton;
    private JButton STOPButton;
    private JButton LOOPOFFButton;
    private JButton RESUMEButton;
    private JButton LOOPONButton;
    private JButton PAUSEButton;
    private JButton RESTARTButton;
    private JButton JUMPButton;
    private JButton PREVButton;
    private JButton NEXTButton;
    private JButton SHUFFLEButton;
    private JComboBox<String> comboBox;
    private JProgressBar progressBar;

    Clip clip;
    static boolean playCompleted;
    static String status="";
    static Long currentFrame;
    static String filepath="";
    AudioInputStream ais;
    java.util.Timer timer;
    private static int index=0;

    public PlayerConsole(List<Record> recordList) {
        minTF.setText("0");//initially min and sec text box for jump operation is set to 0.
        secTF.setText("0");//otherwise if user press jump button without the entering the duration, it will throw exception
        for (Record r : recordList) {
            comboBox.addItem(r.getRecordName());
        }
        setPlayerPlay(recordList);

        timer= new Timer();
        timer.schedule(new Progress(),0, 1000);//Timer will call Progress() every second.

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==comboBox){
                    index=comboBox.getSelectedIndex();
                    setPlayerPlay(recordList);
                }
            }
        });

        PLAYButton.addActionListener(e -> play());

        PAUSEButton.addActionListener(e -> pause());

        RESUMEButton.addActionListener(e -> {
            try {
                resume();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });

        STOPButton.addActionListener(e -> {
            stop();
            //System.exit(0);
        });

        RESTARTButton.addActionListener(e -> {
            try {
                restart();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });

        LOOPONButton.addActionListener(e -> {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            statusTF.setText("CONTINUOUS LOOP");
        });

        LOOPOFFButton.addActionListener(e -> {
            try {
                loopOff();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
            statusTF.setText("LOOP OFF");
        });

        JUMPButton.addActionListener(e -> {
            long minL=Long.parseLong(minTF.getText());
            long secL=Long.parseLong(secTF.getText());
            long micro=TimeUnit.MINUTES.toMicros(minL)+TimeUnit.SECONDS.toMicros(secL);
            try {
                jump(micro);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        NEXTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(index+1<recordList.size())
                    index++;
                else
                   index=0;//directing to first record
                setPlayerPlay(recordList);
            }
        });

        PREVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(index-1>=0)
                    index--;
                else
                    index=recordList.size()-1;//directing to last record
                setPlayerPlay(recordList);
            }
        });

        SHUFFLEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newIndex;
                do{
                    newIndex=(int)(Math.random()*(recordList.size())+0);
                }while (newIndex==index);
                index=newIndex;

                setPlayerPlay(recordList);
            }
        });
    }
    private void setPlayerPlay(List<Record> recordList){
        comboBox.setSelectedIndex(index);
        if(clip!=null)//since when the console is opened for the first time clip will be null
            clip.close();
        status = "off";
        filepath = recordList.get(index).getLocation();
        File file = new File(filepath);
        try {
            ais = AudioSystem.getAudioInputStream(file);
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(this);
            clip.open(ais);
            titleLabel.setText(recordList.get(index).getRecordName());
            long minute= TimeUnit.MICROSECONDS.toMinutes(clip.getMicrosecondLength());
            long seconds=(clip.getMicrosecondLength()/1000000)%60;
            DURATION.setText("DURATION : "+minute+":"+seconds);
            progressBar.setMaximum((int)ais.getFrameLength());
            play();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }
    }
    private void play(){
        if(status.equals("play")){
            statusTF.setText("Audio is already being played");
            return;
        }
        //start the clip
        clip.start();
        statusTF.setText("PLAYING SONG");
        status = "play";

    }
    private void pause(){
        if (status.equals("paused"))
        {
            statusTF.setText("SONG ALREADY PAUSED");
            return;
        }
        currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
        statusTF.setText("SONG PAUSED");
    }
    private void resume() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (status.equals("play"))
        {
            statusTF.setText("Audio is already being played");
            return;
        }
        clip.close();
        status="stop";
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
        status="play";
    }
    private void restart() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        clip.stop();
        status="stop";
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
        status="play";
    }
    private void stop(){
        currentFrame = 0L;
        clip.stop();
        statusTF.setText("SONG STOPPED");
        clip.close();
        status="stop";
    }
    private void resetAudioStream() throws UnsupportedAudioFileException, IOException,LineUnavailableException
    {
        ais = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
        clip.open(ais);

    }
    private void loopOff() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        currentFrame = this.clip.getMicrosecondPosition();
        clip.close();
        status="stop";
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
        status="play";
    }

    //method to jump to a specific time(accepts time in microseconds)
    private void jump(long c) throws UnsupportedAudioFileException, IOException,LineUnavailableException
    {
        if (c > 0 && c < clip.getMicrosecondLength())
        {
            clip.stop();
            status="stop";
            clip.close();
            resetAudioStream();
            currentFrame = c;
            clip.setMicrosecondPosition(c);
            this.play();
            status="play";
        }
    }

    //method to display the time elapsed
    class Progress extends TimerTask
    {
        @Override
        public void run() {
            long minute= TimeUnit.MICROSECONDS.toMinutes(clip.getMicrosecondPosition());
            long seconds=(clip.getMicrosecondPosition()/1000000)%60;
            timeLabel.setText("TIME ELAPSED :- "+minute+" : "+seconds);
            progressBar.setValue(clip.getFramePosition());
        }
    }

    //method to check when the clip is over.
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
         if (type == LineEvent.Type.STOP) //Playback completed
         {
             playCompleted = true;
             clip.close();
             status = "stop";
             //statusTF.setText("PLAYING COMPLETED");
         }
    }

    //method to execute the JFrame
    public static void player(List<Record> recordList){
        JFrame frame=new JFrame("Music PlayerConsole App");
        PlayerConsole pc=new PlayerConsole(recordList);
        frame.setContentPane(pc.mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosing(e);
                pc.clip.close();
                pc.clip.stop();
                e.getWindow().dispose();//dispose() closes only the console, it does not terminate the program.
            }
        });
        frame.pack();
        frame.setVisible(true);
    }
}
