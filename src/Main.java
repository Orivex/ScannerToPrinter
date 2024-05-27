import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        // Add padding between components
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 2, 10, 10));

        List<String> savedSettings = getSavedSettings();

        if(savedSettings.isEmpty()) {
            saveSettings(" ", " ", " ", " ", " ", " ", " ", " ");
            savedSettings = getSavedSettings();
        }

        JTextField bashPathInputField = new JTextField(savedSettings.get(0));
        JTextField dirPathInputField = new JTextField(savedSettings.get(1));
        JTextField naps2PathInputField = new JTextField(savedSettings.get(2));
        JTextField sumatraPathInputField = new JTextField(savedSettings.get(3));
        JTextField scannerNameInputField = new JTextField(savedSettings.get(5));
        JTextField printerNameInputField = new JTextField(savedSettings.get(6));
        JTextField copyNumberInputField = new JTextField(savedSettings.get(7));

        String[] options = {"wia", "twain", "escl"};
        JComboBox<String> driverOptions = new JComboBox<>(options);
        driverOptions.setSelectedItem(savedSettings.get(4));

        JButton searchDevicesButton = new JButton("Search Devices");
        searchDevicesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findScanners(bashPathInputField.getText(), dirPathInputField.getText(), naps2PathInputField.getText());
                findPrinters(bashPathInputField.getText(), dirPathInputField.getText());
            }
        });

        JButton saveSettingsButton = new JButton("Save Settings");
        saveSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveSettings(bashPathInputField.getText(), dirPathInputField.getText(), naps2PathInputField.getText(), sumatraPathInputField.getText(), driverOptions.getSelectedItem().toString(), scannerNameInputField.getText(), printerNameInputField.getText(), copyNumberInputField.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton startButton = new JButton("START");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start(scannerNameInputField.getText(), printerNameInputField.getText(), driverOptions.getSelectedItem().toString(), copyNumberInputField.getText(), bashPathInputField.getText(), dirPathInputField.getText(), naps2PathInputField.getText(), sumatraPathInputField.getText());
            }
        });

        // Add labels and input fields with adjusted spacing
        panel.add(new JLabel("Bash Path:"));
        panel.add(bashPathInputField);
        panel.add(new JLabel("Working Directory Path:"));
        panel.add(dirPathInputField);
        panel.add(new JLabel("NAPS2 Path:"));
        panel.add(naps2PathInputField);
        panel.add(new JLabel("SumatraPDF Path:"));
        panel.add(sumatraPathInputField);
        panel.add(new JLabel("Search Scanners and Printers:"));
        panel.add(searchDevicesButton);
        panel.add(new JLabel("Driver:"));
        panel.add(driverOptions);
        panel.add(new JLabel("Scanner Name:"));
        panel.add(scannerNameInputField);
        panel.add(new JLabel("Printer Name:"));
        panel.add(printerNameInputField);
        panel.add(new JLabel("Copy Number:"));
        panel.add(copyNumberInputField);
        panel.add(new JLabel(""));
        panel.add(saveSettingsButton);
        panel.add(new JLabel(""));
        panel.add(startButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Scanner To Printer");
        frame.pack();
        frame.setVisible(true);
    }

    static List<String> getSavedSettings() throws IOException {
        List<String> parameters = new ArrayList<>();

        File file = new File(System.getProperty("user.dir") + "/savedSettings.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String nextLine = reader.readLine();
        while (nextLine != null) {
            parameters.add(nextLine);
            nextLine = reader.readLine();
        }

        return parameters;
    }

    static void saveSettings(String bashPath, String dirPath, String naps2Path, String sumatraPath, String driver, String scanner, String printer, String copies) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/savedSettings.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        writer.write(bashPath);
        writer.write("\n");
        writer.write(dirPath);
        writer.write("\n");
        writer.write(naps2Path);
        writer.write("\n");
        writer.write(sumatraPath);
        writer.write("\n");
        writer.write(driver);
        writer.write("\n");
        writer.write(scanner);
        writer.write("\n");
        writer.write(printer);
        writer.write("\n");
        writer.write(copies);

        writer.close();
    }
    static void findScanners(String bashPath, String dirPath, String naps2Path) {
        try {

            String[] command = {
                    bashPath,
                    dirPath + "/findScanners.sh",
                    naps2Path
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command);

            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static void findPrinters(String bashPath, String dirPath) {
        try {

            String[] command = {
                    bashPath,
                    dirPath + "/findPrinters.sh"
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command);

            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    static void start(String scannerName, String printerName, String driverName, String copyNumber, String bashPath, String dirPath, String naps2Path, String sumatraPath) {
        try {
            String[] command = {
                    bashPath,
                    dirPath + "/print.sh",
                    scannerName,
                    printerName,
                    driverName,
                    copyNumber,
                    dirPath,
                    naps2Path,
                    sumatraPath
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command);

            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}