package pddl4gui.gui.tools;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class implements the FileTools class of <code>PDDL4GUI</code>.
 * This class is a toolbox for File management (get, save, etc.).
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class FileTools implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The last file path used.
     */
    private static File staticLastPath;

    /**
     * The file extensions used in PDDL4GUI.
     */
    private static FileNameExtensionFilter pddl = new FileNameExtensionFilter("pddl file", "pddl");
    private static FileNameExtensionFilter txt = new FileNameExtensionFilter("text file", "txt");
    private static FileNameExtensionFilter tex = new FileNameExtensionFilter("tex file", "tex");
    private static FileNameExtensionFilter png = new FileNameExtensionFilter("png file", "png");
    private static FileNameExtensionFilter svg = new FileNameExtensionFilter("svg file", "svg");
    private static FileNameExtensionFilter json = new FileNameExtensionFilter("JSON file", "json");

    /**
     * Returns a boolean. True if the file is not null, false otherwise.
     *
     * @param file the file to check.
     * @return true if the file is not null, false otherwise.
     */
    public static boolean checkFile(File file) {
        return (file != null);
    }

    /**
     * Returns a String containing the name of the file without the extension.
     *
     * @param fileName the filename with the extension.
     * @return the filename without the extension.
     */
    public static String removeExtension(String fileName) {
        if (fileName.indexOf(".") > 0) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }

    /**
     * Gets a list of file from a JFileChooser.
     *
     * @param component the swing component for JFileChooser.
     * @param integer   the integer to choose the type of file (0: pddl ; 1: txt).
     * @param multiple  the possibility to choose multiple files.
     * @param button    the parent button.
     * @return a list of files.
     */
    public static Vector<File> getFiles(Component component, int integer, boolean multiple, JButton button) {
        final JFileChooser fileChooser = new JFileChooser();
        if (staticLastPath != null) {
            fileChooser.setCurrentDirectory(staticLastPath);
        } else {
            fileChooser.setCurrentDirectory(new File("."));
        }

        if (integer == 0) {
            fileChooser.setFileFilter(pddl);
        } else if (integer == 1) {
            fileChooser.setFileFilter(txt);
        }

        fileChooser.setMultiSelectionEnabled(multiple);
        Vector<File> openFiles = new Vector<>();

        int option = fileChooser.showOpenDialog(component);

        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                if (multiple) {
                    openFiles.addAll(Arrays.asList(fileChooser.getSelectedFiles()));
                    staticLastPath = openFiles.firstElement();
                } else {
                    final File file = fileChooser.getSelectedFile();
                    staticLastPath = file;
                    openFiles.add(file);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (option == JFileChooser.CANCEL_OPTION) {
            button.setText("No file(s) selected");
        }

        return openFiles;
    }

    /**
     * Returns the content of a file as a String.
     *
     * @param file the file to read.
     * @return the content of the file as a String.
     */
    public static String readFileToString(File file) {
        final StringBuilder fileContent = new StringBuilder();
        try {
            Scanner scan = FileTools.readFileToScanner(file);
            if (scan != null) {
                while (scan.hasNext()) {
                    fileContent.append(scan.nextLine());
                    fileContent.append("\n");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fileContent.toString();
    }

    /**
     * Returns a Scanner for a specified file.
     *
     * @param file the file to read.
     * @return a Scanner for a specified file.
     */
    private static Scanner readFileToScanner(File file) {
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a new file, with a JFileChooser, in a specified place with a specified extension.
     *
     * @param component the swing component for the JFileChooser.
     * @param integer   the integer to choose the type of file (0: pddl ; 1: txt ; 2: tex ; 3: png ; 4: svg ; 5: json).
     * @return a new file with a specified place and extension.
     */
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
            fileChooser.setFileFilter(pddl);
        } else if (integer == 1) {
            fileChooser.setSelectedFile(new File("file.txt"));
            fileChooser.setFileFilter(txt);
        } else if (integer == 2) {
            fileChooser.setSelectedFile(new File("file.tex"));
            fileChooser.setFileFilter(tex);
        } else if (integer == 3) {
            fileChooser.setSelectedFile(new File("file.png"));
            fileChooser.setFileFilter(png);
        } else if (integer == 4) {
            fileChooser.setSelectedFile(new File("file.svg"));
            fileChooser.setFileFilter(svg);
        } else if (integer == 5) {
            fileChooser.setSelectedFile(new File("file.json"));
            fileChooser.setFileFilter(json);
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

    /**
     * Writes a content (String) in a specified file.
     *
     * @param file   the file.
     * @param string the content to write.
     */
    public static void writeInFile(File file, String string) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));

            writer.write(string);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
