package com.example.foodorderingapp.Data.Repository.Authentication;

import com.example.foodorderingapp.Data.Model.Entity.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpRepository {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference_user = firebaseFirestore.collection("USER");
    private CollectionReference reference_login = firebaseFirestore.collection("LOGIN");

    public void createUser(User user){

    }

}
