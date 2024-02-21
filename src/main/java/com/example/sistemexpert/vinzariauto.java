package com.example.sistemexpert;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Comparator;

class Autoturism implements Serializable{
    private String marca;
    private String model;
    private double suma;
    private String tip;
    private int an;
    private String imagine;
    private double capacitateMotor;
    private String tipCombustibil;
    private double kilometraj;
    private String transmisie;
    private String stareatehnica;
    private int proprietari;

    public Autoturism(String marca, String model, double suma, String tip,int an, String imagine, double capacitateMotor, String tipCombustibil,
                      double kilometraj, String transmisie, String stareTehnica, int proprietari) {
        this.marca = marca;
        this.model = model;
        this.suma = suma;
        this.tip = tip;
        this.an = an;
        this.imagine = imagine;
        this.capacitateMotor = capacitateMotor;
        this.tipCombustibil = tipCombustibil;
        this.kilometraj = kilometraj;
        this.transmisie = transmisie;
        this.stareatehnica = stareTehnica;
        this.proprietari = proprietari;

    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public void setTip(String tip){this.tip = tip;}
    public String getTip(){return tip;}

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public String getImagine() {
        return imagine;
    }

    public void setImagine(String imagine) {
        this.imagine = imagine;
    }

    public double getCapacitateMotor() {
        return capacitateMotor;
    }

    public void setCapacitateMotor(double capacitateMotor) {
        this.capacitateMotor = capacitateMotor;
    }

    public String getTipCombustibil() {
        return tipCombustibil;
    }

    public void setTipCombustibil(String tipCombustibil) {
        this.tipCombustibil = tipCombustibil;
    }
    public double getkilometraj(){return kilometraj;}
    public void setKilometraj(double kilometraj){this.kilometraj = kilometraj;}
    public String getTransmisie(){return transmisie;}
    public void setTransmisie(String transmisie){this.transmisie = transmisie;}
    public String getStareatehnica(){return stareatehnica;}
    public void setstareatehnica(String stareatehnica){this.stareatehnica = stareatehnica;}
    public int getProprietari() {
        return proprietari;
    }

    public void setproprietari(int proprietari) {
        this.proprietari = proprietari;
    }


    public String getInfo() {
        return "Marca: " + marca +
                "\nModel: " + model +
                "\nSuma: " + suma +
                "\nTip caroserie:" + tip +
                "\nAn: " + an +
                "\nImagine: " + imagine +
                "\nCapacitate Motor: " + capacitateMotor +
                "\nTip Combustibil: " + tipCombustibil +
                "\nKilometraj:" + kilometraj +
                "\nTransmisie:" + transmisie +
                "\nStare Tehnica:" + stareatehnica +
                "\nNr de proprietari:" + proprietari;
    }

    @Override
    public String toString() {
        return marca + " " + model;
    }
}

class SalonAuto {
    private List<Autoturism> autoturisme;

    public SalonAuto() {
        autoturisme = new ArrayList<>();
    }

    public void adaugaAutoturism(Autoturism autoturism) {
        autoturisme.add(autoturism);
    }

    public void stergeAutoturism(Autoturism autoturism) {
        autoturisme.remove(autoturism);
    }

    public void sortareDupaPret() {
        autoturisme.sort(Comparator.comparing(Autoturism::getSuma));
    }


    public List<Autoturism> getAutoturisme() {
        return autoturisme;
    }

    public void salvareDate(String numeFisier) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(numeFisier))) {
            outputStream.writeObject(autoturisme);
            System.out.println("Datele au fost salvate în fișierul " + numeFisier);
        } catch (IOException e) {
            System.out.println("Eroare la salvarea datelor în fișier: " + e.getMessage());
        }
    }

    public void incarcareDate(String numeFisier) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(numeFisier))) {
            autoturisme = (List<Autoturism>) inputStream.readObject();
            System.out.println("Datele au fost încărcate din fișierul " + numeFisier);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Eroare la încărcarea datelor din fișier: " + e.getMessage());
        }
    }

}

public class vinzariauto extends Application {
    private SalonAuto salonAuto;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        salonAuto = new SalonAuto();
        salonAuto.incarcareDate("date_salon_auto.dat");

        primaryStage.setTitle("Salon Auto");

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(230, 238, 255)),
                new Stop(1, Color.rgb(185, 210, 245)));

        BackgroundFill fill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(fill);
        root.setBackground(background);

        Label lista = new Label("Lista Autoturisme");
        lista.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

        ListView<Autoturism> autoturismeListView = new ListView<>();
        autoturismeListView.setPrefHeight(400);
        autoturismeListView.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitWidth(135);
                imageView.setFitHeight(110);
                setGraphic(imageView);
            }

            @Override
            protected void updateItem(Autoturism autoturism, boolean empty) {
                super.updateItem(autoturism, empty);
                if (empty || autoturism == null) {
                    imageView.setImage(null);
                    setText(null);
                } else {
                    imageView.setImage(new Image(new File(autoturism.getImagine()).toURI().toString()));
                    setText(autoturism.toString());
                }
            }
        });

        Button adaugaButton = new Button("Adauga");
        Button stergeButton = new Button("Sterge");
        Button modificaButton = new Button("Modifica");

        adaugaButton.setOnAction(e -> {
            Dialog<Autoturism> dialog = new Dialog<>();
            dialog.setTitle("Adauga Autoturism");
            dialog.setHeaderText("Introduceti detaliile autoturismului:");

            TextField marcaTextField = new TextField();
            marcaTextField.setPromptText("Marca");
            TextField modelTextField = new TextField();
            modelTextField.setPromptText("Model");
            TextField sumaTextField = new TextField();
            sumaTextField.setPromptText("Suma");
            TextField anTextField = new TextField();
            anTextField.setPromptText("An");
            TextField tipTextField = new TextField();
            tipTextField.setPromptText("Tip caroserie");
            TextField imagineTextField = new TextField();
            imagineTextField.setPromptText("Calea catre imagine");
            TextField capacitateMotorTextField = new TextField();
            capacitateMotorTextField.setPromptText("Capacitate Motor");
            TextField tipCombustibilTextField = new TextField();
            tipCombustibilTextField.setPromptText("Tip Combustibil");
            TextField kilometrajTextField = new TextField();
            kilometrajTextField.setPromptText("Kilometraj");
            TextField transmisieTextField = new TextField();
            transmisieTextField.setPromptText("Transmisie");
            TextField stareTehnicaTextField = new TextField();
            stareTehnicaTextField.setPromptText("Starea Tehnica");
            TextField proprietariTextField = new TextField();
            proprietariTextField.setPromptText("Proprietari");

            TextField denumireaTextField = new TextField();
            denumireaTextField.setPromptText("Denumirea");

            Button chooseImageButton = new Button("Selecteaza Imagine");
            chooseImageButton.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Selecteaza Imagine");
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    imagineTextField.setText(file.getAbsolutePath());
                }
            });

            GridPane grid = new GridPane();
            grid.add(new Label("Marca:"), 0, 0);
            grid.add(marcaTextField, 1, 0);
            grid.add(new Label("Model:"), 0, 1);
            grid.add(modelTextField, 1, 1);
            grid.add(new Label("Suma:"), 0, 2);
            grid.add(sumaTextField, 1, 2);
            grid.add(new Label("An:"), 0, 3);
            grid.add(anTextField, 1, 3);
            grid.add(new Label("Tip caroserie:"), 0, 4);
            grid.add(tipTextField, 1, 4);
            grid.add(new Label("Imagine:"), 0, 5);
            grid.add(imagineTextField, 1, 5);
            grid.add(new Label("Capacitate Motor:"), 0, 6);
            grid.add(capacitateMotorTextField, 1, 6);
            grid.add(new Label("Tip Combustibil:"), 0, 7);
            grid.add(tipCombustibilTextField, 1, 7);
            grid.add(new Label("Kilometraj:"), 0, 8);
            grid.add(kilometrajTextField, 1, 8);
            grid.add(new Label("Transmisie:"), 0, 9);
            grid.add(transmisieTextField, 1, 9);
            grid.add(new Label("Stare Tehnica:"), 0, 10);
            grid.add(stareTehnicaTextField, 1, 10);
            grid.add(new Label("Proprietari:"), 0, 11);
            grid.add(proprietariTextField, 1, 11);
            grid.add(chooseImageButton, 1, 12);

            dialog.getDialogPane().setContent(grid);

            ButtonType addButton = new ButtonType("Adauga", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButton) {
                    try {
                        String marca = marcaTextField.getText();
                        String model = modelTextField.getText();
                        double suma = Double.parseDouble(sumaTextField.getText());
                        String tip = tipTextField.getText();
                        int an = Integer.parseInt(anTextField.getText());
                        String imagine = imagineTextField.getText();
                        double capacitateMotor = Double.parseDouble(capacitateMotorTextField.getText());
                        String tipCombustibil = tipCombustibilTextField.getText();
                        double kilometraj = Double.parseDouble(kilometrajTextField.getText());
                        String transmisie = transmisieTextField.getText();
                        String stareTehnica = stareTehnicaTextField.getText();
                        int proprietari = Integer.parseInt(proprietariTextField.getText());

                        if (suma < 0) {
                            throw new IllegalArgumentException("Suma nu poate fi negativa!");
                        }
                        if (an < 1800) {
                            throw new IllegalArgumentException("Anul nu poate fi mai mic decât anul în care a apărut vehiculele cu motoare cu ardere internă!");
                        }
                        if (capacitateMotor < 1){
                            throw new IllegalArgumentException("Capacitatea la motor nu poate fi mai mică decât 1");
                        }
                        if (kilometraj <30){
                            throw new IllegalArgumentException("Kilometrajul nu poate fi mai mic decât 30");
                        }
                            if (proprietari < 0){
                            throw new IllegalArgumentException("Nr de proprietari nu poate fi mai mica decât 0");
                        }

                        return new Autoturism(marca, model, suma,tip ,an, imagine, capacitateMotor, tipCombustibil, kilometraj, transmisie, stareTehnica, proprietari);
                    } catch (NumberFormatException ex) {
                        showAlert("Valori invalide introduse!");
                    } catch (IllegalArgumentException ex) {
                        showAlert(ex.getMessage());
                    }
                }
                return null;
            });

            dialog.showAndWait().ifPresent(autoturism -> {
                salonAuto.adaugaAutoturism(autoturism);
                autoturismeListView.getItems().setAll(salonAuto.getAutoturisme());
            });
        });

        stergeButton.setOnAction(e -> {
            Autoturism selectedAutoturism = autoturismeListView.getSelectionModel().getSelectedItem();
            if (selectedAutoturism != null) {
                salonAuto.stergeAutoturism(selectedAutoturism);
                autoturismeListView.getItems().setAll(salonAuto.getAutoturisme());
            }
        });

        modificaButton.setOnAction(e -> {
            Autoturism selectedAutoturism = autoturismeListView.getSelectionModel().getSelectedItem();
            if (selectedAutoturism != null) {
                Dialog<Autoturism> dialog = new Dialog<>();
                dialog.setTitle("Modifica Autoturism");
                dialog.setHeaderText("Introduceti noile detalii ale autoturismului:");

                TextField marcaTextField = new TextField(selectedAutoturism.getMarca());
                TextField modelTextField = new TextField(selectedAutoturism.getModel());
                TextField sumaTextField = new TextField(String.valueOf(selectedAutoturism.getSuma()));
                TextField anTextField = new TextField(String.valueOf(selectedAutoturism.getAn()));
                TextField imagineTextField = new TextField(selectedAutoturism.getImagine());
                TextField capacitateMotorTextField = new TextField(String.valueOf(selectedAutoturism.getCapacitateMotor()));
                TextField tipCombustibilTextField = new TextField(selectedAutoturism.getTipCombustibil());
                TextField tipTextField = new TextField(selectedAutoturism.getTip());
                TextField kilometrajTextField = new TextField(String.valueOf(selectedAutoturism.getkilometraj()));
                TextField transmisieTextField = new TextField(selectedAutoturism.getTransmisie());
                TextField stareTehnicaTextField = new TextField(selectedAutoturism.getStareatehnica());
                TextField proprietariTextField = new TextField(String.valueOf(selectedAutoturism.getProprietari()));


                Button chooseImageButton = new Button("Selecteaza Imagine");
                chooseImageButton.setOnAction(event -> {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Selecteaza Imagine");
                    File file = fileChooser.showOpenDialog(primaryStage);
                    if (file != null) {
                        imagineTextField.setText(file.getAbsolutePath());
                    }
                });

                GridPane grid = new GridPane();
                grid.add(new Label("Marca:"), 0, 0);
                grid.add(marcaTextField, 1, 0);
                grid.add(new Label("Model:"), 0, 1);
                grid.add(modelTextField, 1, 1);
                grid.add(new Label("Suma:"), 0, 2);
                grid.add(sumaTextField, 1, 2);
                grid.add(new Label("An:"), 0, 3);
                grid.add(anTextField, 1, 3);
                grid.add(new Label("Tip caroserie:"), 0, 4);
                grid.add(tipTextField, 1, 4);
                grid.add(new Label("Imagine:"), 0, 5);
                grid.add(imagineTextField, 1, 5);
                grid.add(new Label("Capacitate Motor:"), 0, 6);
                grid.add(capacitateMotorTextField, 1, 6);
                grid.add(new Label("Tip Combustibil:"), 0, 7);
                grid.add(tipCombustibilTextField, 1, 7);
                grid.add(new Label("Kilometraj:"), 0, 8);
                grid.add(kilometrajTextField, 1, 8);
                grid.add(new Label("Transmisie:"), 0, 9);
                grid.add(transmisieTextField, 1, 9);
                grid.add(new Label("Stare Tehnica:"), 0, 10);
                grid.add(stareTehnicaTextField, 1, 10);
                grid.add(new Label("Proprietari:"), 0, 11);
                grid.add(proprietariTextField, 1, 11);
                grid.add(chooseImageButton, 1, 12);

                dialog.getDialogPane().setContent(grid);

                ButtonType modifyButton = new ButtonType("Modifica", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(modifyButton, ButtonType.CANCEL);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == modifyButton) {
                        try {
                            String marca = marcaTextField.getText();
                            String model = modelTextField.getText();
                            double suma = Double.parseDouble(sumaTextField.getText());
                            String tip = tipTextField.getText();
                            int an = Integer.parseInt(anTextField.getText());
                            String imagine = imagineTextField.getText();
                            double capacitateMotor = Double.parseDouble(capacitateMotorTextField.getText());
                            String tipCombustibil = tipCombustibilTextField.getText();
                            double kilometraj = Double.parseDouble(kilometrajTextField.getText());
                            String transmisie = transmisieTextField.getText();
                            String stareTehnica = stareTehnicaTextField.getText();
                            int proprietari = Integer.parseInt(proprietariTextField.getText());

                            if (suma < 0) {
                                throw new IllegalArgumentException("Suma nu poate fi negativa!");
                            }
                            if (an < 1800) {
                                throw new IllegalArgumentException("Anul nu poate fi mai mic decât anul în care a apărut vehiculele cu motoare cu ardere internă!");
                            }
                            if (capacitateMotor < 1){
                                throw new IllegalArgumentException("Capacitatea la motor nu poate fi mai mică decât 1");
                            }
                            if (kilometraj <30){
                                throw new IllegalArgumentException("Kilometrajul nu poate fi mai mic decât 30");
                            }
                            if (proprietari < 0){
                                throw new IllegalArgumentException("Nr de proprietari nu poate fi mai mica decât 0");
                            }

                            selectedAutoturism.setMarca(marca);
                            selectedAutoturism.setModel(model);
                            selectedAutoturism.setSuma(suma);
                            selectedAutoturism.setTip(tip);
                            selectedAutoturism.setAn(an);
                            selectedAutoturism.setImagine(imagine);
                            selectedAutoturism.setCapacitateMotor(capacitateMotor);
                            selectedAutoturism.setTipCombustibil(tipCombustibil);
                            selectedAutoturism.setKilometraj(kilometraj);
                            selectedAutoturism.setTransmisie(transmisie);
                            selectedAutoturism.setstareatehnica(stareTehnica);
                            selectedAutoturism.setproprietari(proprietari);

                            return selectedAutoturism;
                        } catch (NumberFormatException ex) {
                            showAlert("Valori invalide introduse!");
                        } catch (IllegalArgumentException ex) {
                            showAlert(ex.getMessage());
                        }
                    }
                    return null;
                });

                dialog.showAndWait().ifPresent(autoturism -> {
                    autoturismeListView.getItems().setAll(salonAuto.getAutoturisme());
                });
            }
        });

        Button infoButton = new Button("Info");
        infoButton.setOnAction(e -> {
            Autoturism selectedAutoturism = autoturismeListView.getSelectionModel().getSelectedItem();
            if (selectedAutoturism != null) {
                showAlertWithImage(selectedAutoturism.getInfo(), selectedAutoturism.getImagine());
            }
        });
        Button sortareButton = new Button("Sorteaza dupa pret");
        sortareButton.setOnAction(e -> {
            salonAuto.sortareDupaPret();
            autoturismeListView.getItems().setAll(salonAuto.getAutoturisme());
        });
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Cauta...");

        ComboBox<String> searchCriteriaComboBox = new ComboBox<>();
        searchCriteriaComboBox.getItems().addAll("Denumire", "Model", "Pret", "An", "Combustibil");
        searchCriteriaComboBox.setValue("Denumire");

        Button searchButton = new Button("Cauta");
        searchButton.setOnAction(e -> {
            String searchKeyword = searchTextField.getText().toLowerCase();
            String selectedCriteria = searchCriteriaComboBox.getValue().toLowerCase();

            List<Autoturism> filteredAutoturisme = new ArrayList<>();

            switch (selectedCriteria) {
                case "denumire":
                    filteredAutoturisme = salonAuto.getAutoturisme().stream()
                            .filter(autoturism -> autoturism.getMarca().toLowerCase().contains(searchKeyword))
                            .toList();
                    break;
                case "model":
                    filteredAutoturisme = salonAuto.getAutoturisme().stream()
                            .filter(autoturism -> autoturism.getModel().toLowerCase().contains(searchKeyword))
                            .toList();
                    break;
                case "pret":
                    double searchPrice;
                    try {
                        searchPrice = Double.parseDouble(searchKeyword);
                    } catch (NumberFormatException ex) {
                        showAlert("Introduceti un pret valid!");
                        return;
                    }
                    filteredAutoturisme = salonAuto.getAutoturisme().stream()
                            .filter(autoturism -> autoturism.getSuma() == searchPrice)
                            .toList();
                    break;
                case "an":
                    int searchYear;
                    try {
                        searchYear = Integer.parseInt(searchKeyword);
                    } catch (NumberFormatException ex) {
                        showAlert("Introduceti un an valid!");
                        return;
                    }

                    filteredAutoturisme = salonAuto.getAutoturisme().stream()
                            .filter(autoturism -> autoturism.getAn() == searchYear)
                            .toList();
                    break;
                case "combustibil":
                    filteredAutoturisme = salonAuto.getAutoturisme().stream()
                            .filter(autoturism -> autoturism.getTipCombustibil().toLowerCase().contains(searchKeyword))
                            .toList();
                    break;
                default:
                    break;
            }

            autoturismeListView.getItems().setAll(filteredAutoturisme);
        });

        Button updateButton = new Button("Actualizeaza lista");
        updateButton.setOnAction(e -> {
            autoturismeListView.getItems().setAll(salonAuto.getAutoturisme());
        });


        HBox buttonBox = new HBox(10, searchTextField,searchCriteriaComboBox, searchButton, adaugaButton, stergeButton, modificaButton, infoButton, sortareButton, updateButton);

        buttonBox.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lista, autoturismeListView, buttonBox);
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> salonAuto.salvareDate("date_salon_auto.dat"));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);

        Label headerLabel = new Label("Eroare");
        headerLabel.setFont(Font.font(16));

        alert.setGraphic(headerLabel);

        alert.setContentText(message);

        alert.showAndWait();
    }

    private void showAlertWithImage(String message, String imagePath) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalii Autoturism");
        alert.setHeaderText(null);

        ImageView imageView = new ImageView(new Image("file:" + imagePath));
        imageView.setFitWidth(400);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);
        alert.setGraphic(imageView);

        Label contentLabel = new Label(message);
        contentLabel.setFont(Font.font(16));

        alert.getDialogPane().setContent(contentLabel);

        alert.showAndWait();
    }
    @Override
    public void stop() {
        salonAuto.salvareDate("date_salon_auto.dat");
    }
}