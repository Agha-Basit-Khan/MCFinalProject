package com.aghabasit.finalprojectei;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountFragment extends Fragment {
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView= inflater.inflate(R.layout.fragment_account, container, false);
        EditText emailUser=myView.findViewById(R.id.email_account);
        EditText dateofCreation=myView.findViewById(R.id.dateofCreation);
        EditText timeOfCreation=myView.findViewById(R.id.timeOfCreation);
        EditText signInAt=myView.findViewById(R.id.lastSignInAt);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        emailUser.setText(user.getEmail());
        Long timestampCreate=user.getMetadata().getCreationTimestamp();
        Date date1 = new Date(timestampCreate);
        SimpleDateFormat jdf = new SimpleDateFormat("dd MMM yyyy");
        String java_date = jdf.format(date1);
        