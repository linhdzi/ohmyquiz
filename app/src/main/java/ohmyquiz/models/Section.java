package ohmyquiz.models;

import java.util.List;

import javafx.scene.control.Button;



public class Section {
    private String sectionGuid;
    private String title;
    private List<Question> questions;
    private Button button ;
    // Phương thức Getter và Setter

    public Button getButton() {
        return this.button = new Button("delete");
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getSectionGuid() {
        return this.sectionGuid;
    }

    public void setSectionGuid(String sectionGuid) {
        this.sectionGuid = sectionGuid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }


}