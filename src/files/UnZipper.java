package files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZipper {
  private static final int BUFFER = 512;
  private static final long TOOBIG = 200*1024*1024; // Max size of unzipped data, 200MB
  private static final int TOOMANY = 1024; // Max number of files
  private static final String MASTER_DATA_PATH = "/Users/i574245/Documents/study/project/JavaSamples/resources/static/strings/";
  private static final String MASTER_DATA_I18N_PATH = MASTER_DATA_PATH + "i18n/";

  public void unzip(InputStream in) throws java.io.IOException {
    ZipInputStream zis = new ZipInputStream(new BufferedInputStream(in));
    ZipEntry entry;
    int entries = 0;
    long total = 0;
    String name = null;
    try {
      while ((entry = zis.getNextEntry()) != null) {
        System.out.println("Extracting: " + entry);
        int count;
        byte data[] = new byte[BUFFER];
        // Write the files to the disk, but ensure that the filename is valid,
        // and that the file is not insanely big
        String intendedDir = entry.getName().lastIndexOf("_") > 0
            ? MASTER_DATA_I18N_PATH + entry.getName().substring(0, entry.getName().lastIndexOf("_")) + "/"
            : MASTER_DATA_PATH;
        name = intendedDir + entry.getName().substring(entry.getName().lastIndexOf("_") + 1);
        //validateFilename(entry.getName(), intendedDir);
        if (entry.isDirectory()) {
          System.out.println("Creating directory " + name);
          continue;
        }
        FileOutputStream fos = new FileOutputStream(name);
        BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
        while (total + BUFFER <= TOOBIG && (count = zis.read(data, 0, BUFFER)) != -1) {
          dest.write(data, 0, count);
          total += count;
        }
        dest.flush();
        dest.close();
        zis.closeEntry();
        entries++;
        if (entries > TOOMANY) {
          throw new IllegalStateException("Too many files to unzip.");
        }
        if (total + BUFFER > TOOBIG) {
          throw new IllegalStateException("File being unzipped is too big.");
        }
      }
    } catch (IOException e) {
      System.out.println("Failed to extract file " + name);
    }finally {
      zis.close();
    }
  }

  private String validateFilename(String filename, String intendedDir)
      throws java.io.IOException {
    File f = new File(filename);
    String canonicalPath = f.getCanonicalPath();

    File iD = new File(intendedDir);
    String canonicalID = iD.getCanonicalPath();

    if (canonicalPath.startsWith(canonicalID)) {
      return canonicalPath;
    } else {
      throw new IllegalStateException("File is outside extraction target directory.");
    }
  }
}
