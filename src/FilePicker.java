import java.io.File;
import java.io.FilenameFilter;
import java.util.Random;

public class FilePicker {
    private File rootFolder;

    public FilePicker(File musicFolder) {
        this.rootFolder = musicFolder;
    }

    public FilePicker(String musicFolderPath) {
        File musicFolder = new File(musicFolderPath);
        this.rootFolder = musicFolder;
    }

    public File rootFolder() {
        return this.rootFolder;
    }

    public void setRootFolder(File newRoot) {
        this.rootFolder = newRoot;
    }

    public File pickRandom() {
        return pickRandomMP3(this.rootFolder);
    }

    private static File pickRandomMP3(File root) {
        if (root.isDirectory()) {
            File[] files = root.listFiles(new MP3Filter());
            int numberOfFiles = files.length;
            Random generator = new Random();
            int randomIndex = generator.nextInt(numberOfFiles);
            root = pickRandomMP3(files[randomIndex]);
        }

        return root;
    }

    static public class MP3Filter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            boolean result;

            File file = new File(dir, name);
            if (file.isDirectory()) {
                result = true;
            } else {
                int extensionLocation = name.length() - 4;
                String fileExtension = name.substring(extensionLocation);
                result = fileExtension.equals(".mp3");
            }

            return result;
        }
    }

    public static void main(String[] args) {
        File musicFolder = new File("C:\\Users\\Ken\\Music");
        System.out.println(musicFolder.isDirectory());
        FilePicker picker = new FilePicker(musicFolder);
        File randomTest = picker.pickRandom();
        System.out.println(randomTest);
    }
}
