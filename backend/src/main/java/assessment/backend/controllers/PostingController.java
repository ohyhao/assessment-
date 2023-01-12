package assessment.backend.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import assessment.backend.models.Posting;
import assessment.backend.models.Response;
import assessment.backend.services.PostingService;

@RestController
@RequestMapping(path="/api")
public class PostingController {

    @Autowired
    private AmazonS3 s3;

    @Autowired
    private PostingService postingSvc;

    @PostMapping(path="/posting", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> postPosting(
    @RequestPart MultipartFile myfile, 
    @RequestPart String name, 
    @RequestPart String email,
    @RequestPart String phone,
    @RequestPart String title,
    @RequestPart String description) {

        System.out.println(name + " " + email +" "+phone+" "+title+" "+description );
        
        String postingId = UUID.randomUUID().toString().substring(0, 8);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        

        Map<String, String> myData = new HashMap<>();
        myData.put("createdOn", (new Date().toString()));

        //MetaData for the object
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(myfile.getContentType());
        metadata.setContentLength(myfile.getSize());
        metadata.setUserMetadata(myData);

        try {
            PutObjectRequest putReq = new PutObjectRequest("vttp-final-project", "assessment/%s".formatted(postingId), myfile.getInputStream(), metadata);
            putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
            s3.putObject(putReq);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Posting posting = new Posting();
        posting.setPostingId(postingId);
        posting.setPostingDate(sdf.format(new Date()));
        posting.setName(name);
        posting.setEmail(email);
        posting.setPhone(phone);
        posting.setTitle(title);
        posting.setDescription(description);
        posting.setImage("https://vttp-final-project.sgp1.digitaloceanspaces.com/assessment/%s".formatted(postingId));

        postingSvc.cachePosting(posting);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Posting.toJson(posting).toString());
    }

    @PutMapping(path="/posting/{id}")
    public ResponseEntity<String> confirmPost(@PathVariable String id) {

        Posting p;
        Response resp;

        p = postingSvc.getPostingFromCache(id);
        
        if (p == null) {
            resp = new Response();
            resp.setCode(404);
            resp.setMessage("Posting ID %s not found".formatted(id));
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(resp.toJson().toString());
        } else {
            postingSvc.deletePostingFromCache(id);
            try {
                boolean insert = postingSvc.insertPosting(p.getPostingId(), p.getPostingDate().toString(), p.getName()
                    , p.getEmail(), p.getPhone(), p.getTitle(), p.getDescription(), p.getImage());
                
                if (insert != true) 
                throw new IllegalArgumentException("Unable to add posting!");
                } catch (Exception ex) {
                    ex.getStackTrace();
                    throw ex;
                }
            resp = new Response();
            resp.setCode(200);
            resp.setMessage("Accepted %s".formatted(p.getPostingId()));
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(resp.toJson().toString());
        }

    }


    

}
