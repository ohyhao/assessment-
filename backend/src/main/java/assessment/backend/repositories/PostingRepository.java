package assessment.backend.repositories;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import assessment.backend.models.Posting;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import static assessment.backend.repositories.Queries.*;

@Repository
public class PostingRepository {

    Logger logger = Logger.getLogger(PostingRepository.class.getName());

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JdbcTemplate template;

    public void cachePosting(Posting p) {

        logger.log(Level.INFO, "Storing posting " + p.getPostingId() + "in cache");

        redisTemplate.opsForHash()
            .put("postings", p.getPostingId(), Posting.toJson(p).toString());

        Duration expireTime = Duration.parse("PT15M");
        redisTemplate.expire(p.getPostingId(), expireTime);
    }

    public Posting getPostingFromCache(String id) {

        String postString = (String)redisTemplate.opsForHash().get("postings", id);

        try (InputStream is = new ByteArrayInputStream(postString.getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject data = reader.readObject();
            logger.log(Level.INFO, "JSON object of posting created");
            Posting posting = Posting.create(data);
            return posting;
        } catch (Exception e) {
            logger.log(Level.INFO, "failed to retrieve book from cache");
            return null;
        }
    }

    public void deletePostingFromCache(String id) {
        redisTemplate.opsForHash().delete("entry", id);
    }

    public boolean insertPosting(String id, String date, String name, String email, 
        String phone, String title, String description, String image) {

            int count = template.update(SQL_INSERT_POSTING, id, date, name, email, 
                phone, title, description, image);
            
            return 1 == count;
        }
    
}
