import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class GUU extends JFrame
        implements ActionListener {

    private JButton openButton;
    private JTextField nameField;
    private JTextArea textArea;
    private JPanel polar;

    public static void main(String[] args) {
        GUU frame = new GUU();
        frame.setTitle("Analise your protein");
        frame.setSize(400, 200);
        frame.setResizable(false);
        frame.createGUI();
        frame.setVisible(true);
    }

    /**
     * Creates the gui
     */
    public void createGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        openButton = new JButton("Open");
        c.add(openButton);
        openButton.addActionListener(this);

        JButton analiseButton = new JButton("Analise");
        c.add(analiseButton);
        analiseButton.addActionListener(this);

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(400, 20));
        c.add(nameField);

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(400, 50));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        c.add(textArea);

        polar = new JPanel();
        polar.setPreferredSize(new Dimension(400, 20));
        c.add(polar);

        JLabel blue = new JLabel("Polar");
        blue.setForeground(Color.blue);
        c.add(blue);

        JLabel red = new JLabel("Apolar");
        red.setForeground(Color.red);
        c.add(red);
    }

    /**
     * Reads the file in the located at the path in the textArea
     */
    public void readFile() {
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(nameField.getText()));
            textArea.setText("");
            String protein = "";
            String line;
            while ((line = inFile.readLine()) != null) {
                if (!line.startsWith(">")) {
                    protein += line;
                }
            }
            try {
                float[] returns = Backend.calculate(protein);
                float pol = returns[0];
                float apol = returns[1];
                int length = (int) returns[2];
                textArea.setText(String.format("Length protein: %s\nPercentage polar: %s\nPercentage apolar: %s",
                        length, pol, apol));
                drawPart(polar.getGraphics(), pol, apol);
            } catch (NotAnAA notAnAA) {
                textArea.setText(String.valueOf(notAnAA));
            }
            inFile.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "File Error: " + e.toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Viualises the share of polar and apolar amino acids in the fasta sequence
     * @param g the graphics of the JPanel polar
     * @param polar A float with the percentage of polar amino acids
     * @param apolar A float with the percentage of apolar amino acids
     */
    public static void drawPart(Graphics g, float polar, float apolar) {
        int pol = Math.round(polar);
        int apol = Math.round(apolar);
        g.setColor(Color.blue);
        g.fillRect(0, 0, pol * 4, 20);
        g.setColor(Color.red);
        g.fillRect(pol * 4, 0, apol * 4, 20);
    }

    /**
     * Performes actions after they get activated by clivking on one of the buttons.
     * @param event ActionEvent tied to the buttons openButton and analiseButton
     */
    public void actionPerformed(ActionEvent event) {
        File selectedFile;
        int reply;
        if (event.getSource() == openButton) {
            JFileChooser fileChooser = new JFileChooser();
            reply = fileChooser.showOpenDialog(this);
            if (reply == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                nameField.setText(selectedFile.getAbsolutePath());

            }
        }
        readFile();
    }
}