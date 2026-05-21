package lap_1.university;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import java.rmi.Naming;

public class stud_input extends Application {
    private MyRemoteInterface stub;

    @Override
    public void start(Stage primaryStage) {
        try {
            stub = (MyRemoteInterface) Naming.lookup("rmi://localhost/student");

            primaryStage.setTitle("Student Registration");
            primaryStage.setMinWidth(400);
            primaryStage.setMinHeight(400);

            TextField stud_id = new TextField();
            stud_id.setPromptText("Enter Student ID");

            TextField stud_name = new TextField();
            stud_name.setPromptText("Enter Student Name");

            TextField stud_department = new TextField();
            stud_department.setPromptText("Enter Department");

            TextField stud_section = new TextField();
            stud_section.setPromptText("Enter Section");

            TextField stud_year = new TextField();
            stud_year.setPromptText("Enter Year");

            Button submit = new Button("Submit to Database");
            Label statusLabel = new Label("Status: Ready");

            submit.setOnAction(e -> {
                try {
                    student s = new student(
                        Integer.parseInt(stud_id.getText()),
                        stud_name.getText(),
                        stud_department.getText(),
                        stud_section.getText(),
                        Integer.parseInt(stud_year.getText())
                    );

                    
                    stub.addtoDb(s);
                    
                    statusLabel.setText("Status: Student added successfully!");
                    
                    stud_id.clear();
                    stud_name.clear();
                    stud_department.clear();
                    stud_section.clear();
                    stud_year.clear();

                } catch (Exception ex) {
                    statusLabel.setText("Status: " + ex.getMessage());

                }
            });

            VBox root = new VBox(15);
            root.setStyle("-fx-padding: 20; -fx-alignment: center;");
            root.getChildren().addAll(
                new Label("Student Management System"),
                stud_id, stud_name, stud_department, stud_section, stud_year, 
                submit, statusLabel
            );

            Scene scene = new Scene(root, 450, 450);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Could not connect to RMI Server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}