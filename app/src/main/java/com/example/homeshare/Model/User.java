package com.example.homeshare.Model;


import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class User  {
    FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    String getEmail() {
        return fbUser.getEmail();
    }

    String getName() {
        return fbUser.getDisplayName();
    }

    Uri getPhotoUrl() {
        return fbUser.getPhotoUrl();
    }







}
