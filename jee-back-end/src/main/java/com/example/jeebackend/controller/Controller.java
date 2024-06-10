package com.example.jeebackend.controller;

import com.example.jeebackend.business.HandelUser;
import com.example.jeebackend.exception.MyException;
import com.example.jeebackend.model.Offre;
import com.example.jeebackend.model.Universite;
import com.example.jeebackend.model.User;
import com.example.jeebackend.repository.OffreRepository;
import com.example.jeebackend.repository.UniversiteRepository;
import com.example.jeebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@CrossOrigin("http://localhost:3000")
public class Controller {
    @Autowired
    HandelUser handelUser;
    @Autowired
    OffreRepository offreRepository;
    @Autowired
    UniversiteRepository universiteRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/signUp")
    User newUser(@RequestBody User newUser) {
        return handelUser.handleSignUp(newUser);
    }

    @PostMapping("/login")
    User login(@RequestBody User user) {
        return handelUser.handleLogin(user.getLogin(), user.getPassword());
    }

    @PostMapping("/addOffre")
    public User createOffre(@RequestParam Long userId,
                            @RequestParam String universite,
                            @RequestParam String prix,
                            @RequestParam String logement,
                            @RequestParam String genre,
                            @RequestParam String localisation,
                            @RequestParam String contact,
                            @RequestParam("images") MultipartFile[] images) {
        try {
            return handelUser.handelAddOffre(userId, universite, prix, logement, genre, localisation, contact, images);
        } catch (IOException e) {
            throw new MyException("Could not add this offres");
        }
    }
    @GetMapping("/offres")
    public List<Offre> getOffres() {
        return offreRepository.findAll();
    }
    @GetMapping("/usersWithHand/{id}")
    public List<User> getUsersWithHand(@PathVariable Long id) {
        return handelUser.getUsersWithHand(id);
    }
    @PutMapping("/addHand/{id_user}/{id_offre}")
    public User addHand(@PathVariable Long id_user, @PathVariable Long id_offre){
        return handelUser.addHand(id_user, id_offre);
    }
    @PutMapping("/removeHand/{id_user}/{id_offre}")
    public User removeHand(@PathVariable Long id_user, @PathVariable Long id_offre){
        return handelUser.removeHand(id_user, id_offre);
    }
    @PutMapping("/addLike/{id_user}/{id_offre}")
    public User addLike(@PathVariable Long id_user, @PathVariable Long id_offre){
        return handelUser.addLike(id_user, id_offre);
    }
    @PutMapping("/removeLike/{id_user}/{id_offre}")
    public User removeLike(@PathVariable Long id_user, @PathVariable Long id_offre){
        return handelUser.removeLike(id_user, id_offre);
    }
    @PutMapping("/removeAllHands/{id_user}")
    public User removeAllHnads(@PathVariable Long id_user){
        return handelUser.removeAllHands(id_user);
    }
    @PostMapping("/addUniversite")
    public Universite addUniversite(@RequestBody Universite universite){
        return universiteRepository.save(universite);
    }
    @GetMapping("/getUniversites")
    public List<Universite> getUniversite(){
        return universiteRepository.findAll();
    }
    @PutMapping("/editOffre")
    public Offre editOffre(@RequestParam Long offreId,
                            @RequestParam String universite,
                            @RequestParam String prix,
                            @RequestParam String logement,
                            @RequestParam String genre,
                            @RequestParam String localisation,
                            @RequestParam String contact,
                            @RequestParam("images") MultipartFile[] images) {
        try {
            return handelUser.handelEditOffre(offreId, universite, prix, logement, genre, localisation, contact, images);
        } catch (IOException e) {
            throw new MyException("Could not edit this offres");
        }
    }
    @DeleteMapping("/deleteOffre/{id}")
    public ResponseEntity deleteOffre(@PathVariable Long id) {
        return handelUser.handleDeleteOffre(id);
    }
    @GetMapping("/likesNumber/{id}")
    public int getLikesNumber(@PathVariable Long id) {
        return userRepository.countUsersWithOfferInLike(id);
    }
    @GetMapping("/handsNumber/{id}")
    public int getHandsNumber(@PathVariable Long id) {
        return userRepository.countUsersWithOfferInHands(id);
    }
    @GetMapping("/verifiedOffers")
    public List<Offre> getVerifiedOffers() {
        return offreRepository.findVerifiedOffres();
    }
    @PutMapping("/verifieOffers/{id}")
    public Offre addVerifiedOffer(@PathVariable Long id){
        Offre offre = offreRepository.findById(id).get();
        offre.setVerified(true);
        return offreRepository.save(offre);
    }
}