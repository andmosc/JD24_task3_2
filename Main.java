package netology.task3_2;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Создать три экземпляра класса GameProgress.
 * Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
 * Созданные файлы сохранений из папки savegames запаковать в архив zip.
 * Удалить файлы сохранений, лежащие вне архива.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        FileSystemView f = FileSystemView.getFileSystemView();
        File homePathDirectory = new File((f.getHomeDirectory()) + "\\Games");
        File saveGamesPath = new File(homePathDirectory + "\\savegames");

        GameProgress saveGameProgress = new GameProgress(73, 3, 1, 125.5);
        GameProgress save1GameProgress = new GameProgress(62, 7, 1, 165.5);
        GameProgress save2GameProgress = new GameProgress(100, 1, 1, 15.5);

        File dir = new File(saveGamesPath, "save.dat");
        File dir1 = new File(saveGamesPath, "save1.dat");
        File dir2 = new File(saveGamesPath, "save2.dat");

        saveGame(saveGameProgress, dir);
        saveGame(save1GameProgress, dir1);
        saveGame(save2GameProgress, dir2);

        File dirZip = new File(homePathDirectory, "save.zip");
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(dirZip));

        doZip(saveGamesPath, outZip);
        outZip.close();
    }

    private static void doZip(File dir, ZipOutputStream out) {

        for (File item : dir.listFiles()) {
            if (item.isFile()) {
                try {
                    out.putNextEntry(new ZipEntry(item.getPath()));
                    write(new FileInputStream(item), out);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void write(FileInputStream in, ZipOutputStream out) {
        try {
            byte[] buffer = new byte[in.available()];
            int len;
            while ((len = in.read(buffer)) >= 0) {
                out.write(buffer);
            }
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void saveGame(GameProgress gameProgress, File dir) {

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dir))) {
            outputStream.writeObject(gameProgress);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
