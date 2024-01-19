
package ohmyquiz.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ohmyquiz.App;
import ohmyquiz.bussinesses.UserBussiness;
import ohmyquiz.dataAccesses.QuizDataAccess;

import javafx.scene.Node;
public class CreateQuizController {

    // @FXML
    // private Button addMore;
    private QuizDataAccess qz;
@FXML
    private Label sectionGuidLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private TableView<DataItem> tableView;

    @FXML
    private TableView<DataItem> questionTable;

    @FXML
    private TableView<DataItem> aswerTable;
    @FXML
    private TextField sectionTitleTextField;


    @FXML
    private TableColumn<DataItem, String> column1;

    @FXML
    private TableColumn<DataItem, String> column2;
    @FXML
    private TableColumn<DataItem, String> column3;


    @FXML
    private TableColumn<DataItem, String> column4;

     @FXML
    private TableColumn<DataItem, String> column5;
    @FXML
    private TableColumn<DataItem, String> column6;
    @FXML
    private TableColumn<DataItem, String> columnDele;


    // @FXML
    // private Button button;

    // @FXML
    // private Button addSection;

    // @FXML
    // private VBox section;

    // @FXML
    // void display(ActionEvent event) {
    //     section.setVisible(true);
    // }

//     @FXML
//     void addMoreSection(ActionEvent event) {
// VBox newSection = new VBox();

//         VBox innerVBox = new VBox();
//         Button innerButton = new Button("Button");
//         innerVBox.getChildren().add(innerButton);

//         TextField textField = new TextField();

//         newSection.getChildren().addAll(textField, innerVBox);

//         section.getChildren().add(newSection);

      
//     }

//     private VBox cloneVBox(VBox originalVBox) {
//         VBox clonedVBox = new VBox();
//         clonedVBox.setStyle(originalVBox.getStyle());
//         clonedVBox.getStyleClass().addAll(originalVBox.getStyleClass());
//         clonedVBox.setSpacing(originalVBox.getSpacing());
//         clonedVBox.setPadding(originalVBox.getPadding());

//         return clonedVBox;
//     }

@FXML
    public void initialize() {
        qz = new QuizDataAccess();
        // Gọi phương thức để lấy giá trị sectionGuid và title từ QuizDataAccess

        List<Document> sections = qz.getSections();

  
        // // Tạo một đối tượng mới chứa sectionGuidsText và titlesText
        // DataItem dataItem = new DataItem(sectionGuidsText, titlesText);

        // // Thêm đối tượng vào danh sách tableView
        // tableView.getItems().add(dataItem);
        
        for(var i = 0; i < sections.size(); i++)
        {
            var section = sections.get(i);
            DataItem dataItem = new DataItem(section.getString("sectionGuid"), section.getString("title"));
            tableView.getItems().add(dataItem);
        }

        

        // Liên kết dữ liệu với các cột
        column1.setCellValueFactory(cellData -> cellData.getValue().sectionGuidsTextProperty());
        column2.setCellValueFactory(cellData -> cellData.getValue().titlesTextProperty());
       //tao context menu cho chuot phai
        ContextMenu smallMenu = new ContextMenu();
                    MenuItem UpdateSC = new MenuItem("UpdateSC");
                    MenuItem DeleteSC = new MenuItem("DeleteSC");
                    MenuItem AddSC = new MenuItem("AddSC");

                    smallMenu.getItems().add(AddSC);
                    smallMenu.getItems().add(UpdateSC);
                    smallMenu.getItems().add(DeleteSC);
                    
            



        // Bat su kien khi click vao bang
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override

            
            public void handle(MouseEvent event) {

                //ẩn menu nếu nhấn 
                if (smallMenu.isShowing()){
                    smallMenu.hide();

                }

                if(event.getButton() == javafx.scene.input.MouseButton.SECONDARY){
                   
                    smallMenu.show(tableView, event.getScreenX(),event.getScreenY());

                }
                // Lấy item được chọn
                DataItem selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    String sectionGuid = selectedItem.sectionGuidsTextProperty().getValue();
                    System.out.println(sectionGuid);
                    var questions = qz.getQuestion(sectionGuid);
                    //Xoa toan bo cac question cu di
                    questionTable.getItems().clear();
                    
                    for(var q : questions) {
                        DataItem questionItem = new DataItem(q.getString("content"), q.getString("questionGuid"));
                        questionTable.getItems().add(questionItem);
                    }
                    System.out.println("Selected item: " + selectedItem);

                    
                    column4.setCellValueFactory(cellData -> cellData.getValue().sectionGuidsTextProperty());
                    column3.setCellValueFactory(cellData -> cellData.getValue().titlesTextProperty());
        
                }
            }
            
        }





        


    );


    questionTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Lấy item được chọn
                DataItem selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    String answerGuid = selectedItem.sectionGuidsTextProperty().getValue();
                    System.out.println(answerGuid);
                    var questions = qz.getQuestion(answerGuid);
                    //Xoa toan bo cac question cu di
                    aswerTable.getItems().clear();
                    for(var q : questions) {
                        DataItem questionItem = new DataItem(q.getString("content"), q.getString("answerGuid"));
                        aswerTable.getItems().add(questionItem);
                    }
                    System.out.println("Selected item: " + selectedItem);
                    column6.setCellValueFactory(cellData -> cellData.getValue().sectionGuidsTextProperty());
                    column5.setCellValueFactory(cellData -> cellData.getValue().titlesTextProperty());
        
                }
            }
            
        }


        


    );

        
        
    }

    // Class DataItem để chứa sectionGuidsText và titlesText
    public class DataItem {
        private StringProperty sectionGuidsText;
        private StringProperty titlesText;



        public DataItem(String sectionGuidsText, String titlesText) {
            this.sectionGuidsText = new SimpleStringProperty(sectionGuidsText);
            this.titlesText = new SimpleStringProperty(titlesText);
        }

        // Getters và setters cho sectionGuidsText và titlesText
        public StringProperty sectionGuidsTextProperty() {
            return sectionGuidsText;
        }

        public StringProperty titlesTextProperty() {
            return titlesText;
        }
    }

    public class DataItemAnswers {
        private StringProperty content;
        private StringProperty answerGuid;



        public DataItemAnswers(String content, String answerGuid) {
            this.content = new SimpleStringProperty(content);
            this.answerGuid = new SimpleStringProperty(answerGuid);
        }

        // Getters và setters cho sectionGuidsText và titlesText
        public StringProperty answerContent() {
            return content;
        }

        public StringProperty answer() {
            return answerGuid;
        }
    }




// neu anh muon test dele voi add thi anh cu mo ra
//     @FXML
// private void addSectionButtonClicked() {
//     String sectionTitle = sectionTitleTextField.getText();
//     if (!sectionTitle.isEmpty()) {
//         qz.DeleteSection(sectionTitle);
//     }
// }


//  update 
// @FXML
// private void addSectionButtonClicked() {
//     String sectionTitle = "linhd";
//     String sectionGuid = "ss1";
//         qz.UpdateSection(sectionGuid, sectionTitle);;
    
//add cau hoi vao section
// @FXML
// private void addSectionButtonClicked() {
  
//         qz.AddQuestionToSection("don", "dead", "name of the girl sun love 2", "hard");
            
// }

// dele question
// @FXML
// private void addSectionButtonClicked() {
  
//         qz.DeleteQuestion("don", "sun");
            
// }

// add aswer
@FXML
private void addSectionButtonClicked() {
 //qz.updateQuestion("moon", "ma code java", "name the girl sun love1111", "easy");
           // qz.deleteAnswer("73ec161c-82c1-49b6-9eaf-cbfeedb10baa");
           qz.updateAnswer("31e1c2e4-a70d-4fa8-bccb-bc12dfbed35f", "chay di ma set dung", true);
          //qz.UpdateQuestion("73ec161c-82c1-49b6-9eaf-gfdgfgdgfdguj", "sun", "name the girl sun love", "easy");

         // qz.DeleteQuestion("73ec161c-82c1-49b6-9eaf-gfdgfgdgfdguj");
}

}