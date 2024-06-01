package test.concurrency;

public class AdapterPatternDemo {
    public static void main(String[] args) {
        MediaPlayer mediaPlayer = new MediaPlayerImpl();
        mediaPlayer.play("old-song", "mp3");
        mediaPlayer.play("new-song", "avi");
    }
}

interface MediaPlayer{
    void play(String fileToPlay, String formatToPlay);
}

class DefaultMediaPlayer implements MediaPlayer{
    @Override
    public void play(String fileToPlay, String formatToPlay) {
        System.out.println("Playing " + fileToPlay + "." + formatToPlay);
    }
}

class AdvanceFormat{
    String fileToPlay;
    String formatToPlay;
    String resolution;

    public String getFileToPlay() {
        return fileToPlay;
    }

    public void setFileToPlay(String fileToPlay) {
        this.fileToPlay = fileToPlay;
    }

    public String getFormatToPlay() {
        return formatToPlay;
    }

    public void setFormatToPlay(String formatToPlay) {
        this.formatToPlay = formatToPlay;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}

interface AdvanceMediaPlayer{
    void playAdvanceFormat(AdvanceFormat advanceFormat);
}

class AVIPlayer implements AdvanceMediaPlayer{
    @Override
    public void playAdvanceFormat(AdvanceFormat advanceFormat) {
        System.out.println("Playing using Advance media player " + advanceFormat.getFileToPlay() + "." + advanceFormat.getFormatToPlay() + " in resolution " + advanceFormat.getResolution());
    }
}

class MediaPlayerAdapter implements MediaPlayer{
    AdvanceMediaPlayer advanceMediaPlayer;
    AdvanceFormat advanceFormat;

    MediaPlayerAdapter(AdvanceFormat advanceFormat){
        advanceMediaPlayer = new AVIPlayer();
        this.advanceFormat = advanceFormat;
    }

    @Override
    public void play(String fileToPlay, String formatToPlay) {
        advanceFormat.setFileToPlay(fileToPlay);
        advanceFormat.setFormatToPlay(formatToPlay);
        advanceMediaPlayer.playAdvanceFormat(advanceFormat);
    }
}

class MediaPlayerImpl implements MediaPlayer{
    MediaPlayer mediaPlayer;
    MediaPlayerAdapter mediaPlayerAdapter;

    @Override
    public void play(String fileToPlay, String formatToPlay) {
        if(formatToPlay.equalsIgnoreCase("mp3")){
            mediaPlayer = new DefaultMediaPlayer();
            mediaPlayer.play(fileToPlay, formatToPlay);
        }else {
            AdvanceFormat advanceFormat = new AdvanceFormat();
            advanceFormat.setResolution("720p");
            mediaPlayerAdapter = new MediaPlayerAdapter(advanceFormat);
            mediaPlayerAdapter.play(fileToPlay, formatToPlay);
        }
    }
}