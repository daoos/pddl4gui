package solver.gui.tools;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileTools {

    private static File staticLastPath;
    private static File tempFile;

    public static File getTempFile() {
        return tempFile;
    }

    public static boolean checkFile(File file) {
        return (file == null);
    }

    private static String removeExtension(String fileName) {
        if (fileName.indexOf(".") > 0) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }

    public static File getFile(Component component, int integer) {
        JFileChooser fileChooser = new JFileChooser();
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
                System.out.println(ex.getMessage());
            }
        }
        return openFile;
    }

    public static String readFileToString(File file) {
        StringBuilder fileContent = new StringBuilder();
        try {
            Scanner scan = FileTools.readFileToScanner(file);
            if (scan != null) {
                while (scan.hasNext())
                    fileContent.append(scan.nextLine() + "\n");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return fileContent.toString();
    }

    public static Scanner readFileToScanner(File file) {
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static File saveFile(Component component, int integer) {
        JFileChooser fileChooser = new JFileChooser();
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
                System.out.println(ex.getMessage());
            }
        }
        return saveFile;
    }

    public static void writeResultFile(File file, String string, int integer) {
        BufferedWriter writer = null;
        try {
            File resultFile = new File("temp");
            if (integer == 0) {
                resultFile = new File(file.getParent(), "output_" + FileTools.removeExtension(file.getName()) + ".txt");
            } else if (integer == 1) {
                resultFile = new File(file.getParent(), "output_" + FileTools.removeExtension(file.getName()) + ".json");
            }

            writer = new BufferedWriter(new FileWriter(resultFile));

            writer.write(string);

            if (integer == 0) {
                tempFile = resultFile;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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
                writer.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean compareStringInList(String search, ArrayList<String> list) {
        for (String str : list) {
            if (str.trim().contains(search))
                return true;
        }
        return false;
    }

}
