package pddl4gui.gui.tools;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class FileTools {

    private static File staticLastPath;

    public static boolean checkFile(File file) {
        return (file == null);
    }

    public static String removeExtension(String fileName) {
        if (fileName.indexOf(".") > 0) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }

    public static File getFile(Component component, int integer) {
        final JFileChooser fileChooser = new JFileChooser();
        if (staticLastPath != null) {
            fileChooser.setCurrentDirectory(staticLastPath);
        } else {
            fileChooser.setCurrentDirectory(new File("."));
        }

        File openFile = null;
        if (integer == 0) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("pddl file", "pddl"));
        } else if (integer == 1) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("text file", "txt"));
        }
        int option = fileChooser.showOpenDialog(component);

        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                openFile = fileChooser.getSelectedFile();
                staticLastPath = openFile;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return openFile;
    }

    public static Vector<File> getFiles(Component component) {
        final JFileChooser fileChooser = new JFileChooser();
        if (staticLastPath != null) {
            fileChooser.setCurrentDirectory(staticLastPath);
        } else {
            fileChooser.setCurrentDirectory(new File("."));
        }

        Vector<File> openFiles = new Vector<>();
        fileChooser.setFileFilter(new FileNameExtensionFilter("pddl file", "pddl"));
        fileChooser.setMultiSelectionEnabled(true);

        int option = fileChooser.showOpenDialog(component);
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                openFiles.addAll(Arrays.asList(fileChooser.getSelectedFiles()));
                staticLastPath = openFiles.firstElement();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return openFiles;
    }

    public static String readFileToString(File file) {
        final StringBuilder fileContent = new StringBuilder();
        try {
            Scanner scan = FileTools.readFileToScanner(file);
            if (scan != null) {
                while (scan.hasNext())
                    fileContent.append(scan.nextLine() + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fileContent.toString();
    }

    private static Scanner readFileToScanner(File file) {
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File saveFile(Component component, int integer) {
        final JFileChooser fileChooser = new JFileChooser();
        if (staticLastPath != null) {
            fileChooser.setCurrentDirectory(staticLastPath);
        } else {
            fileChooser.setCurrentDirectory(new File("."));
        }

        File saveFile = null;

        if (integer == 0) {
            fileChooser.setSelectedFile(new File("file.pddl"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("pddl file", "pddl"));
        } else if (integer == 1) {
            fileChooser.setSelectedFile(new File("file.txt"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("text file", "txt"));
        } else if (integer == 2) {
            fileChooser.setSelectedFile(new File("file.tex"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("tex file", "tex"));
        } else if (integer == 3) {
            fileChooser.setSelectedFile(new File("file.png"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("png file", "png"));
        } else if (integer == 4) {
            fileChooser.setSelectedFile(new File("file.svg"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("svg file", "svg"));
        } else if (integer == 5) {
            fileChooser.setSelectedFile(new File("file.json"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("JSON file", "json"));
        }

        int option = fileChooser.showSaveDialog(component);

        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                saveFile = fileChooser.getSelectedFile();
                staticLastPath = saveFile;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return saveFile;
    }

    public static void writeInFile(File file, String string) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));

            writer.write(string);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
