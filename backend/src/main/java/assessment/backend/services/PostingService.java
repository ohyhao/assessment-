package assessment.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import assessment.backend.models.Posting;
import assessment.backend.repositories.PostingRepository;

@Service
public class PostingService {

    @Autowired
    private PostingRepository postingRepo;

    public void cachePosting(Posting p) {
        postingRepo.cachePosting(p);
    }

    public Posting getPostingFromCache(String id) {
        return postingRepo.getPostingFromCache(id);
    }

    public void deletePostingFromCache(String id) {
        postingRepo.deletePostingFromCache(id);
    }

    public boolean insertPosting(String id, String date, String name, String email, 
    String phone, String title, String description, String image) {
        return postingRepo.insertPosting(id, date, name, email, phone, title, description, image);
    }
    
}
