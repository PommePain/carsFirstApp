package carsLittleApp.app;

import carsLittleApp.service.serviceCars;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;
import java.lang.reflect.Field;
import java.util.List;

public class Swing extends JFrame {
    int windowWidth = 600;
    int windowHeight = 400;
    final int windowX = 425;
    final int windowY = 225;
    JPanel panel = new JPanel();


    public Swing() {
        super("Cars DB");

        /* Listener */
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        /* Call Methods */
        displayButtonsMenu();

        // Icone
        ImageIcon appIcon = new ImageIcon("assets/icon.png", "appIcon");
        setIconImage(appIcon.getImage());

        // Settings of window
        setBackground(new Color(186, 168, 90));
        setLocation(windowX, windowY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        addWindowListener(l);
        setSize(windowWidth,windowHeight);
        setVisible(true);
    }

    /* Methods */
    public void displayButtonsMenu() {
        String[] optionsMenu = {
                "Chercher une voiture",
                "Enregistrer une voiture",
                "Voir toutes les voitures",
                "Supprimer une voiture",
                "Quitter"
        };

        JButton[] buttons = new JButton[optionsMenu.length];
        setSize(windowWidth,windowHeight);

        // Search a car
        JButton enterButton = new JButton("Valider");
        JLabel text = new JLabel("Entrez le nom de la voiture");
        enterButton.setBorder(new LineBorder(Color.red));
        JTextField textField = new JTextField(5);

        // Actions et events des boutons
        for(int i = 0; i< optionsMenu.length;i++) {
            buttons[i] = new JButton(optionsMenu[i]);
            panel.add(buttons[i]);
            switch(optionsMenu[i]) {
                case "Chercher une voiture" -> buttons[i].addActionListener(e -> {
                    clearWindow();
                    panel.revalidate();

                    panel.add(text);
                    panel.add(textField);
                    panel.add(enterButton);
                    text.setBounds(175, 150, 175, 15);
                    enterButton.setBounds(225, 210, 50, 25);
                    textField.setBounds(200,170,100,30);
                    panel.revalidate();

                    JLabel htmlContainerSearch = new JLabel();
                    panel.remove(htmlContainerSearch);

                    /* Settings of input Window */
                    enterButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent event) {
                            String userInput = textField.getText();
                            JEditorPane editorPane = new JEditorPane();
                            editorPane.setContentType("text/html");
                            JLabel title = new JLabel("<html><center>Infos de la voiture</center>");
                            Cars searchedCar = serviceCars.getCar(userInput);
                            String carInfosString = searchedCar.getInfosString().replaceAll("\n", "<br>");
                            JLabel carInfos = new JLabel(carInfosString);
                            JLabel htmlSearchCar = new JLabel(title.getText() + carInfos.getText());
                            htmlContainerSearch.setText(title.getText() + carInfos.getText());
                            panel.add(htmlContainerSearch);
                            panel.revalidate();
                        }
                    });
                });
                case "Voir toutes les voitures" -> buttons[i].addActionListener(e -> {
                    clearWindow();
                    panel.revalidate();
                    JEditorPane editorPane = new JEditorPane();
                    editorPane.setContentType("text/html"); // Définit le type de contenu
                    JLabel title = new JLabel("<html><center>Infos de toutes les voitures</center>");

                    List<Cars> allCars = serviceCars.getAllCars();
                    int amountCars = allCars.size();
                    StringBuilder data = new StringBuilder(title.getText());

                    JLabel[] carsInfos = new JLabel[amountCars];
                    for (int j = 0; j < amountCars; j++) {
                        String carName = allCars.get(j).getBrand() + " " + allCars.get(j).getName();
                        String carPrice = allCars.get(j).getPrice() + " €";
                        String carSpeed = allCars.get(j).getSpeed() + " km/h";
                        carsInfos[j] = new JLabel();
                        if (j != amountCars-1) {
                            carsInfos[j].setText("<center><br>Nom : ".concat(carName) +
                                    " | Prix : ".concat(carPrice) + " | Vitesse: ".concat(carSpeed) + "</center></br>");
                        } else {
                            carsInfos[j].setText("<center><br>Nom : ".concat(carName) +
                                    " | Prix : ".concat(carPrice) + " | Vitesse: ".concat(carSpeed) + "</br></html>");
                        }
                        data.append(carsInfos[j].getText());
                        //panel.add(carsInfos[j]);
                    }
                    JLabel htmlContent = new JLabel(data.toString());

                    panel.add(htmlContent);
                    panel.invalidate();
                    setSize(500, 350);
                });
                case "Supprimer une voiture" -> buttons[i].addActionListener(e -> {
                    clearWindow();
                    displayDeleteCarMenu();
                });
                case "Enregistrer une voiture" -> buttons[i].addActionListener(e -> {
                    clearWindow();
                    displayCreateCarMenu();
                });
                case "Quitter" -> buttons[i].addActionListener(e -> System.exit(0));
            }
        }
    }

    public void clearWindow() {
        panel.removeAll();
        displayButtonsMenu();
        panel.revalidate();
    }

    public void displayCreateCarMenu() {
        panel.revalidate();
        JLabel createCarText = new JLabel("Informations de la voiture");
        JButton createCarButton = new JButton("Enregistrer une voiture");

        final Cars car = new Cars();
        Field[] carFields = car.getClass().getDeclaredFields();
        ArrayList<JTextField> inputs = new ArrayList<>();

        createCarText.setBounds(80, 240, 175, 15);
        createCarButton.setBounds(80, 260, 50, 25);
        panel.add(createCarText);

        int i = 0;
        for (Field carField : carFields) {
            if (!carField.getName().equals("id")) {
                JTextField input = new JTextField();
                input.setText(carField.getName());
                input.setName(carField.getName());
                input.setBounds(160,270+(i*10),150,30);
                inputs.add(input);
                panel.add(input);
            }
            i++;
        }

        panel.add(createCarButton);
        panel.revalidate();
        panel.repaint();

        createCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JTextField input : inputs) {
                    try {
                        switch (input.getName()) {
                            case "name" -> { car.setName(input.getText()); }
                            case "brand" -> { car.setBrand(input.getText()); }
                            case "price" -> {
                                car.setPrice(Integer.parseInt(input.getText()));
                                if (input.getText().equals("price")) car.setPrice(0);
                            }
                            case "speed" -> {
                                car.setSpeed(Integer.parseInt(input.getText()));
                                if (input.getText().equals("speed")) car.setSpeed(0);
                            }
                            case "dateCreation" -> { car.setDateCreation(input.getText()); }
                            case "colors" -> { car.setColors(List.of(input.getText().split(","))); }
                        }
                    } catch (Exception exception) {
                        System.out.println("Error during getting input field : " + exception.getMessage());
                        //exception.printStackTrace();
                    }
                }
                JEditorPane jEditorPane = new JEditorPane();
                jEditorPane.setContentType("text/html");
                String textHtmlReturn = (serviceCars.createCar(car) == 1) ? "La voiture a bien été créée" : "Erreur durant la création";
                JLabel title = new JLabel("<html><center>"+textHtmlReturn+"</center></html>");
                panel.add(title);
                panel.revalidate();
            }
        });
    }

    public void displayDeleteCarMenu() {
        JButton delCarButton = new JButton("Supprimer");
        JLabel enterCarNameToDelText = new JLabel("Entrez le nom de la voiture");
        delCarButton.setBorder(new LineBorder(Color.red));
        JTextField textFieldEnterDelCar = new JTextField(5);
        panel.add(enterCarNameToDelText);
        panel.add(textFieldEnterDelCar);
        panel.add(delCarButton);
        enterCarNameToDelText.setBounds(175, 150, 175, 15);
        delCarButton.setBounds(225, 210, 50, 25);
        textFieldEnterDelCar.setBounds(200,170,100,30);
        panel.revalidate();

        JLabel htmlContainerSearch = new JLabel();
        panel.remove(htmlContainerSearch);

        delCarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String userInput = textFieldEnterDelCar.getText();
                JEditorPane editorPane = new JEditorPane();
                editorPane.setContentType("text/html");
                String textHtmlReturn = (serviceCars.deleteCar(userInput) == 1) ? "La voiture a bien été supprimée" : "Erreur durant la suppression";
                JLabel title = new JLabel("<html><center>"+textHtmlReturn+"</center></html>");
                panel.add(title);
                panel.revalidate();
            }
        });
    }
}
