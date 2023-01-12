package assessment.backend.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Posting {
    
    private String postingId;
    private String postingDate;
    private String name;
    private String email;
    private String phone;
    private String title;
    private String description;
    private String image;
    
    public String getPostingId() { return postingId; }
    public void setPostingId(String postingId) { this.postingId = postingId; }

    public String getPostingDate() { return postingDate; }
    public void setPostingDate(String postingDate) { this.postingDate = postingDate; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public static JsonObject toJson(Posting p) {
        return Json.createObjectBuilder()
            .add("postingId", p.getPostingId())
            .add("postingDate", p.getPostingDate().toString())
            .add("name", p.getName())
            .add("email", p.getEmail())
            .add("phone", p.getPhone())
            .add("title", p.getTitle())
            .add("description", p.getDescription())
            .add("image", p.getImage())
            .build();
    }

    public static Posting create(JsonObject data) {
        Posting posting = new Posting();

        

        posting.setPostingId(data.getString("postingId"));
        posting.setPostingDate(data.getString("postingDate"));
        posting.setName(data.getString("name"));
        posting.setEmail(data.getString("email"));
        posting.setPhone(data.getString("phone"));
        posting.setTitle(data.getString("title"));
        posting.setDescription(data.getString("description"));
        posting.setImage(data.getString("image"));

        return posting;

    }

}
    