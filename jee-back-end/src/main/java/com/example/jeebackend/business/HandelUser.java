package com.example.jeebackend.business;

import com.example.jeebackend.exception.MyException;
import com.example.jeebackend.model.Image;
import com.example.jeebackend.model.Offre;
import com.example.jeebackend.model.User;
import com.example.jeebackend.repository.OffreRepository;
import com.example.jeebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HandelUser {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OffreRepository offreRepository;
    public User handleSignUp(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new MyException("l’email existe deja");
        }
        if(!user.getPhone().replaceAll("\\D", "").matches("^(06|07)\\d{8}$")){
            throw new MyException("le numero de telephone n'est pas valide");
        }
        if(!user.getPassword().matches("^(?=.*[A-Z])(?=.*\\d).{8,}$")){
            throw new MyException("le mot de passe doit contenir 8 caracteres dont une majuscule et un chiffre");
        }

        return userRepository.save(user);
    }
    public User handleLogin(String login, String password){
        return userRepository.findByLoginAndPassword(login, password)
                .orElseThrow(()-> new MyException("le Login ou le mot de passe n'est pas correct"));
    }
    public User handelAddOffre(Long userId,
                                      String universite,
                                      String prix,
                                      String logement,
                                      String genre,
                                      String localisation,
                                      String contact,
                                      MultipartFile[] images)
            throws IOException {
        Offre offre = new Offre();
        offre.setContact(contact);
        offre.setGenre(genre);
        offre.setUniversite(universite);
        offre.setLogement(logement);
        offre.setLocalisation(localisation);
        offre.setPrix(prix);
        offre.setImages(new ArrayList<>());
        for (MultipartFile image : images) {
            Image imageEntity = new Image();
            imageEntity.setData(image.getBytes());
            offre.getImages().add(imageEntity);
        }
        User user = userRepository.findById(userId).get();
        user.getMyOffres().add(offre);
        return userRepository.save(user);
    }
    public List<User> getUsersWithHand(Long id) {
        return userRepository.findUsersWithOfferInHands(id);
    }
    public User addHand(Long idUser, Long idOffer){
        User user = userRepository.findById(idUser).get();
        Offre offre = offreRepository.findById(idOffer).get();
        user.getHands().add(offre);
        return userRepository.save(user);
    }
    public User addLike(Long idUser, Long idOffer){
        User user = userRepository.findById(idUser).get();
        Offre offre = offreRepository.findById(idOffer).get();
        user.getLikes().add(offre);
        return userRepository.save(user);
    }
    public User removeHand(Long idUser, Long idOffer){
        User user = userRepository.findById(idUser).get();
        Offre offre = offreRepository.findById(idOffer).get();
        user.getHands().remove(offre);
        return userRepository.save(user);
    }
    public User removeLike(Long idUser, Long idOffer){
        User user = userRepository.findById(idUser).get();
        Offre offre = offreRepository.findById(idOffer).get();
        user.getLikes().remove(offre);
        return userRepository.save(user);
    }
    public User removeAllHands(Long idUser){
        User user = userRepository.findById(idUser).get();
        user.getHands().clear();
        return userRepository.save(user);
    }




    public Offre handelEditOffre(Long offreId,
                               String universite,
                               String prix,
                               String logement,
                               String genre,
                               String localisation,
                               String contact,
                               MultipartFile[] images)
            throws IOException {
        Offre offre = offreRepository.findById(offreId).get();
        offre.setContact(contact);
        offre.setGenre(genre);
        offre.setUniversite(universite);
        offre.setLogement(logement);
        offre.setLocalisation(localisation);
        offre.setPrix(prix);
        offre.setImages(new ArrayList<>());
        for (MultipartFile image : images) {
            Image imageEntity = new Image();
            imageEntity.setData(image.getBytes());
            offre.getImages().add(imageEntity);
        }
        return offreRepository.save(offre);
    }

    public ResponseEntity handleDeleteOffre(Long id){
        User user = userRepository.findUserbyMyOffre(id).get();
        Offre offre = offreRepository.findById(id).get();
        List<User> likes = userRepository.findUsersWithOfferInLike(id);
        List<User> hands = userRepository.findUsersWithOfferInHands(id);
        for (User userL : likes
             ) {
            userL.getLikes().remove(offre);
            userRepository.save(userL);
        }
        for (User userH : hands
        ) {
            userH.getHands().remove(offre);
            userRepository.save(userH);
        }
        user.getMyOffres().remove(offre);
        userRepository.save(user);
        offreRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
