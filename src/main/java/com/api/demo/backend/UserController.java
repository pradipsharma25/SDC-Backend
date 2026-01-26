package com.api.demo.backend;

import com.api.demo.config.JwtUtil;
import com.api.demo.model.Artist;
import com.api.demo.model.Event;
import com.api.demo.model.Hiphop;
import com.api.demo.model.Jazz;
import com.api.demo.model.Test;
import com.api.demo.model.Ticketbooking;
import com.api.demo.model.Merch;
import com.api.demo.model.Music;
import com.api.demo.model.Pop;
import com.api.demo.model.Recommended;
import com.api.demo.model.Rock;
import com.api.demo.repository.ArtistRepository;
import com.api.demo.repository.EventRepository;
import com.api.demo.repository.HiphopRepository;
import com.api.demo.repository.JazzRepository;
import com.api.demo.repository.MerchRepository;
import com.api.demo.repository.MusicRepository;
import com.api.demo.repository.PopRepository;
import com.api.demo.repository.RecommendedRepository;
import com.api.demo.repository.RockRepository;
import com.api.demo.repository.TestRepository;
import com.api.demo.repository.TicketRepository;

import io.jsonwebtoken.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TestRepository tRepo;
    
    @Autowired
    private EventRepository rRepo;
    
    @Autowired
    private MerchRepository mRepo;
    
    @Autowired
    private TicketRepository tikRepo;
    
    @Autowired
    private ArtistRepository artistRepo;
    
    @Autowired
    private MusicRepository musicRepo;
    
    @Autowired
    private HiphopRepository hRepo;
    
    @Autowired
    private JazzRepository jazzRepo;
    
    @Autowired
    private PopRepository popRepo;
    
    @Autowired
    private RecommendedRepository recomRepo;
    
    @Autowired
    private RockRepository rockRepo;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Test user) {
        if (tRepo.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (tRepo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        tRepo.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
    
    @GetMapping("/pradip")
    public String getPradip() {
    
    	return "Hello I am pradip Sharma";
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String loginId = (request.getUsername() != null && !request.getUsername().isEmpty())
                    ? request.getUsername()
                    : request.getEmail();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginId, request.getPassword())
            );
            
            
            // To fetch the role of the user
            Test user = request.getUsername() != null && !request.getUsername().isEmpty()
                    ? tRepo.findByUsername(request.getUsername()).orElse(null)
                    : tRepo.findByEmail(request.getEmail()).orElse(null);
            
            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }

            // ✅ Generate JWT if login successful
            String token = jwtUtil.generateToken(loginId);

            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "token", token, "role",user.getRole()!=null ? user.getRole() : "test"
            ));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username/email or password");
        }
    }
    
    
    
    @PostMapping("/event")
    public ResponseEntity<String> createEvent(@RequestBody Event event) {
        rRepo.save(event);
        return ResponseEntity.ok("Entered successfully");
    }
    
    @GetMapping("/event")
    public List<Event> getEvent() {
    	return rRepo.findAll();
    }
    
    @GetMapping("/merch")
    public List<Merch> getMerch() {
    	return mRepo.findAll();
    }
    
    @PostMapping("/merch")
    public ResponseEntity<String> createEvent(@RequestBody Merch merch) {
        mRepo.save(merch);
        return ResponseEntity.ok("Entered successfully");
    }

    // Example of a protected API endpoint
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        if (jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            return ResponseEntity.ok("Welcome, " + username + "!");
        }
        return ResponseEntity.status(401).body("Invalid or expired token");
    }
    
    @PostMapping("/ticketbooking")
    public ResponseEntity<String> createTicket(@RequestBody Ticketbooking ticket) {
        tikRepo.save(ticket);
        return ResponseEntity.ok("Entered successfully");
    }
    
    @GetMapping("/ticketbooking")
    public List<Ticketbooking> getTicket() {
    	
    	return tikRepo.findAll();
    }
    
    
    @PostMapping("/artist")
    public ResponseEntity<String> postArtist(@RequestBody Artist artist) {
        artistRepo.save(artist);
        return ResponseEntity.ok("Entered successfully");
    }
    
    @GetMapping("/artist")
    public List<Artist> getartist() {
    	
    	return artistRepo.findAll();
    }
    
    @PostMapping(value = "/music", consumes = "multipart/form-data")
    public ResponseEntity<Music> postMusic(
            @RequestParam("title") String title,
            @RequestParam("artistName") String artistName,
            @RequestParam("album") String album,
            @RequestParam("genre") String genre,
            @RequestParam("audioFile") MultipartFile audioFile,
            @RequestParam("imageFile") MultipartFile imageFile
    ) throws IllegalStateException, java.io.IOException {
        try {
            // Create uploads directory if not exists
            File uploadDir = new File("uploads");
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // Save audio file
            String audioFilePath = "uploads/" + System.currentTimeMillis() + "_" + audioFile.getOriginalFilename();
            audioFile.transferTo(new File(audioFilePath));

            // Save image file
            String imageFilePath = "uploads/" + System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            imageFile.transferTo(new File(imageFilePath));

            // Save entity
            Music music = new Music();
            music.setTitle(title);
            music.setArtistName(artistName);
            music.setAlbum(album);
            music.setGenre(genre);
            music.setAudioFilePath(audioFilePath);
            music.setImageFilePath(imageFilePath);

            Music savedMusic = musicRepo.save(music);

            // Return saved entity as JSON
            return ResponseEntity.ok(savedMusic);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // GET all music
    @GetMapping("/music")
    public ResponseEntity<List<Music>> getAllMusic() {
        List<Music> musicList = musicRepo.findAll();
        return ResponseEntity.ok(musicList);
    }
    
    @GetMapping("/hiphop")
    public List<Hiphop> getHiphop(){
    	
    	return hRepo.findAll();
    }
    
    @PostMapping("/hiphop")
    public ResponseEntity<String> postHiphop(@RequestBody Hiphop hiphop) {
        hRepo.save(hiphop);
        return ResponseEntity.ok("Entered successfully");
    }
    
    @GetMapping("/jazz")
    public List<Jazz> getJazz(){
    	return jazzRepo.findAll();
    }
    
    @PostMapping("/jazz")
    public ResponseEntity<String> postJazz(@RequestBody Jazz jazz){
    	jazzRepo.save(jazz);
    	return ResponseEntity.ok("Entered Successfully");
    }
    
    @GetMapping("/pop")
    public List<Pop> getPop(){
    	return popRepo.findAll();
    }
    
    @PostMapping("/pop")
    public ResponseEntity<String> postPop(@RequestBody Pop pop){
    	popRepo.save(pop);
    	return ResponseEntity.ok("Entered Successfully");
    }
    
    @GetMapping("/recommended")
    public List<Recommended> getRecom(){
    	return recomRepo.findAll();
    }
    
    @PostMapping("/recommended")
    public ResponseEntity<String> postRecom(@RequestBody Recommended recommended){
    	recomRepo.save(recommended);
    	return ResponseEntity.ok("Entered Successfully");
    }
    
    @GetMapping("/rock")
    public List<Rock> getRock(){
    	return rockRepo.findAll();
    }
    
    @PostMapping("/rock")
    public ResponseEntity<String> postRock (@RequestBody Rock rock ){
    	rockRepo.save(rock);
    	return ResponseEntity.ok("Entered Successfully");
    }
}
