package assessment.backend.repositories;

public interface Queries {
    
    public static final String SQL_INSERT_POSTING = "insert into postings (posting_id, posting_date, name, email, phone, title, description, image) values (?, ?, ?, ?, ?, ?, ?, ?)";
}
