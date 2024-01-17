package ohmyquiz.dataAccesses;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import java.util.Arrays;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import org.bson.BsonTimestamp;

import ohmyquiz.models.Answer;
import ohmyquiz.models.Question;
import ohmyquiz.models.Section;
import ohmyquiz.models.User;

public class QuizDataAccess {
    private MongoCollection<Document> collection;

    public List<Document> getSections() {
        // Kết nối tới MongoDB
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");

        List<String> sectionGuids = new ArrayList<>();

        // Lấy tất cả các documents từ collection
        FindIterable<Document> documents = collection.find();

        // Sử dụng con trỏ để duyệt qua các document
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                // Lấy giá trị của sections từ document
                List<Document> sections = doc.getList("sections", Document.class);
                return sections;
            }
        }

        return null;
    }

    public List<Document> getQuestion(String sectionGuid) {
        // Kết nối tới MongoDB
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");

        List<String> sectionGuids = new ArrayList<>();

        // Lấy tất cả các documents từ collection
        FindIterable<Document> documents = collection.find();

        // Sử dụng con trỏ để duyệt qua các document
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                // Lấy giá trị của sections từ document
                List<Document> sections = doc.getList("sections", Document.class);
                for (Document section : sections) {
                    if (section.getString("sectionGuid").equals(sectionGuid)) {
                        try {
                            var query = section.getList("questions", Document.class);
                            if (query != null) {
                                List<Document> questions = query;
                                return questions;
                            }
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }

                    }

                }

            }
        }

        return new ArrayList<Document>();
    }

    public List<Document> getAswer(String questionGuid) {
        // Kết nối tới MongoDB
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");

        List<String> sectionGuids = new ArrayList<>();

        // Lấy tất cả các documents từ collection
        FindIterable<Document> documents = collection.find();

        // Sử dụng con trỏ để duyệt qua các document
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                // Lấy giá trị của sections từ document
                List<Document> sections = doc.getList("questions", Document.class);
                for (Document section : sections) {
                    if (section.getString("questionGuid").equals(questionGuid)) {
                        List<Document> questions = section.getList("content", Document.class);
                        return questions;
                    }

                }

            }
        }

        return null;
    }

    public List<String> getSectionGuids() {
        // Kết nối tới MongoDB
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");

        List<String> sectionGuids = new ArrayList<>();

        // Lấy tất cả các documents từ collection
        FindIterable<Document> documents = collection.find();

        // Sử dụng con trỏ để duyệt qua các document
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                // Lấy giá trị của sections từ document
                List<Document> sections = doc.getList("sections", Document.class);
                if (sections != null) {
                    for (Document section : sections) {
                        // Lấy giá trị của sectionGuid từ section
                        String sectionGuid = section.getString("sectionGuid");
                        sectionGuids.add(sectionGuid);
                    }
                }
            }
        }

        return sectionGuids;
    }

    public List<String> getTitles() {
        // Kết nối tới MongoDB
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");

        List<String> sectionGuids = new ArrayList<>();

        // Lấy tất cả các documents từ collection
        FindIterable<Document> documents = collection.find();

        // Sử dụng con trỏ để duyệt qua các document
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                // Lấy giá trị của sections từ document
                List<Document> sections = doc.getList("sections", Document.class);
                if (sections != null) {
                    for (Document section : sections) {
                        // Lấy giá trị của sectionGuid từ section
                        String sectionGuid = section.getString("title");
                        sectionGuids.add(sectionGuid);
                    }
                }
            }
        }

        return sectionGuids;
    }

    public List<Question> getQuestionsBySectionGuid(List<Question> questions, String sectionGuid) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");
        List<Question> result = new ArrayList<>();
        for (Question question : questions) {
            if (question.getQuestionGuid().equals(sectionGuid)) {
                result.add(question);
            }
        }
        return result;
    }

    public List<Answer> getAswerByQuestion(List<Answer> answers, String questionGuid) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");
        List<Answer> result = new ArrayList<>();
        for (Answer answer : answers) {
            if (answer.getAnswerGuid().equals(questionGuid)) {
                result.add(answer);
            }
        }
        return result;
    }

    public void AddSection(String newSectionTitle) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");

        // Tạo đối tượng section mới
        Document sectionDocument = new Document();
        sectionDocument.append("sectionGuid", UUID.randomUUID().toString());
        sectionDocument.append("title", newSectionTitle);
        sectionDocument.append("questions", new ArrayList<>());

        // Thêm section vào mảng "sections"
        Document updateDocument = new Document("$push", new Document("sections", sectionDocument));
        collection.updateOne(new Document(), updateDocument);

        return;
    }

    public void DeleteSection(String sectionGuid) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");
        Document filter = new Document("sections.sectionGuid", sectionGuid);

        // Tạo một Document để xóa phần từ mảng "sections"
        Document update = new Document("$pull", new Document("sections", new Document("sectionGuid", sectionGuid)));

        // Thực hiện xóa phần
        collection.updateOne(filter, update);

        return;
    }

    public void UpdateSection(String sectionGuid, String newSectionTitle) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");

        // Tạo điều kiện để tìm phần cần cập nhật
        Document filter = new Document("sections.sectionGuid", sectionGuid);

        // Tạo các phép cập nhật
        Document update = new Document("$set", new Document("sections.$.title", newSectionTitle));

        // Thực hiện cập nhật phần
        collection.updateOne(filter, update);

        return;
    }

    public void AddQuestionToSection(String sectionGuid, String title, String content, String difficulty) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");

        // Tạo document cho câu hỏi
        Document questionDocument = new Document();
        String questionGuid = UUID.randomUUID().toString();
        questionDocument.append("questionGuid", questionGuid);
        questionDocument.append("content", content);
        questionDocument.append("title", title);
        questionDocument.append("difficulty", difficulty);
        questionDocument.append("answers", new ArrayList<>());
        questionDocument.append("correctAnswer", new ArrayList<>());

        // Tìm section trong tài liệu
        Bson sectionFilter = Filters.eq("sections.sectionGuid", sectionGuid);
        Document sectionUpdate = new Document("$push", new Document("sections.$.questions", questionDocument));
        collection.updateOne(sectionFilter, sectionUpdate);
    }

    public void UpdateQuestion( String questionGuid, String newTitle, String newContent, String newDifficulty) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");
    
        // Tạo document chứa thông tin câu hỏi cần cập nhật
        Document updatedQuestion = new Document();
        updatedQuestion.append("title", newTitle);
        updatedQuestion.append("content", newContent);
        updatedQuestion.append("difficulty", newDifficulty);
    
        // Tìm section và câu hỏi cần cập nhật
        Bson sectionFilter = Filters.eq("sections.questions.questionGuid", questionGuid);
        System.out.println(sectionFilter);
   
        Document update = new Document("$set", new Document("sections.$.questions", updatedQuestion));
    
        // Thực hiện cập nhật câu hỏi
        collection.updateOne(sectionFilter, update);
    }

    public void DeleteQuestion(String sectionGuid, String questionGuid) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");
    
        // Tìm section và câu hỏi cần xóa
        Bson sectionFilter = Filters.eq("sections.sectionGuid", sectionGuid);
        Document update = new Document("$pull", new Document("sections.$.questions", new Document("questionGuid", questionGuid)));
    
        // Thực hiện xóa câu hỏi
        collection.updateOne(sectionFilter, update);
        
    }


    public void addAnswer(String questionGuid, String answerContent) {
        var connection = Connection.createConnection();
        MongoDatabase database = connection.getDatabase("OhMyQuiz");
        MongoCollection<Document> collection = database.getCollection("Quiz");
    
        // Tạo document cho câu trả lời
        Document answerDocument = new Document();
        String answerGuid = UUID.randomUUID().toString();
        answerDocument.append("answerGuid", answerGuid);
        answerDocument.append("content", answerContent);
    
        // Tìm câu hỏi trong tài liệu
        Bson sectionFilter = Filters.eq("sections.questions.questionGuid", questionGuid);
        Document sectionUpdate = new Document("$push", new Document("sections.$.questions.$[question].answers", answerDocument));
        UpdateOptions options = new UpdateOptions().arrayFilters(Arrays.asList(Filters.eq("question.questionGuid", questionGuid)));
        collection.updateOne(sectionFilter, sectionUpdate, options);
    }

   


   
}