package files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FilesExercise {
  public static void main(String[] args) {
    InputStream inputStream;
    try {
      inputStream = Files.newInputStream(new File("/Users/i574245/Desktop/certification.zip").toPath());
      UnZipper unZipper = new UnZipper();
      unZipper.unzip(inputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

}
