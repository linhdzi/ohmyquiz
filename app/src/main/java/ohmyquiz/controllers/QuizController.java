// package ohmyquiz.controllers;

// import java.io.IOException;

// import javafx.beans.property.SimpleStringProperty;
// import javafx.beans.property.StringProperty;
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Label;
// import javafx.scene.control.Alert.AlertType;
// import javafx.scene.control.Button;
// import javafx.scene.control.PasswordField;
// import javafx.scene.control.TableColumn;
// import javafx.scene.control.TableView;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.BorderPane;
// import javafx.stage.Stage;
// import ohmyquiz.App;
// import ohmyquiz.bussinesses.UserBussiness;
// import ohmyquiz.dataAccesses.QuizDataAccess;
// import ohmyquiz.models.Section;
// import javafx.scene.Node;


// import java.util.ArrayList;
// import java.util.List;

// public class QuizController {
//     @FXML
//     private Label sectionGuidLabel;

//     @FXML
//     private Label titleLabel;

//     @FXML
//     private TableView<DataItem> tableView;

//     @FXML
//     private TableColumn<DataItem, String> column1;

//     @FXML
//     private TableColumn<DataItem, String> column2;

//     @FXML
//     private Button button;

//     private QuizDataAccess qz;

//     public QuizController() {
        
//     }

//     @FXML
//     public void initialize() {
//         qz = new QuizDataAccess();
//         // Gọi phương thức để lấy giá trị sectionGuid và title từ QuizDataAccess
//         List<String> sectionGuids = qz.getTitles();
//         List<String> titles = qz.getSectionGuids();
//         for(var s : titles){
//             System.out.println(s);
//         }
//         // Chuyển đổi danh sách thành chuỗi
//         String sectionGuidsText = String.join(", ", sectionGuids);
//         String titlesText = String.join(", ", titles);
  
//         // Tạo một đối tượng mới chứa sectionGuidsText và titlesText
//         DataItem dataItem = new DataItem(sectionGuidsText, titlesText);

//         // Thêm đối tượng vào danh sách tableView
//         tableView.getItems().add(dataItem);
//         button.setText("Click me!");

//         // Liên kết dữ liệu với các cột
//         column1.setCellValueFactory(cellData -> cellData.getValue().sectionGuidsTextProperty());
//         column2.setCellValueFactory(cellData -> cellData.getValue().titlesTextProperty());
//     }

//     // Class DataItem để chứa sectionGuidsText và titlesText
//     public class DataItem {
//         private StringProperty sectionGuidsText;
//         private StringProperty titlesText;

//         public DataItem(String sectionGuidsText, String titlesText) {
//             this.sectionGuidsText = new SimpleStringProperty(sectionGuidsText);
//             this.titlesText = new SimpleStringProperty(titlesText);
//         }

//         // Getters và setters cho sectionGuidsText và titlesText
//         public StringProperty sectionGuidsTextProperty() {
//             return sectionGuidsText;
//         }

//         public StringProperty titlesTextProperty() {
//             return titlesText;
//         }
//     }
// }
