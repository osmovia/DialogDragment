package com.example.fragment

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore


val Context.firebaseStore
get() = FirebaseFirestore.getInstance().collection("Word")