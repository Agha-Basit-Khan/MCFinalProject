package com.aghabasit.finalprojectei;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StatsFragment extends Fragment {

    // Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;
    private String[] type={"Income", "Expense"};
    private int[] values={0,0};
    private Map<Date, Integer> DateWiseIncome = new TreeMap<Date, Integer>();
    private Map<Date, Integer> DateWiseExpense = new TreeMap<Date, Integer>();
    private static Set<Pair<Integer,Integer>> DateWiseIncomeSorter= new HashSet<Pair<Integer,Integer>>();;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myView = inflater.inflate(R.layout.fragment_stats, container, false);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);
        mIncomeDatabase.keepSynced(true);
        mExpenseDatabase.keepSynced(true);


        mIncomeDatabase.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                values[0] = 0;
                DateWiseIncome.clear();
                for (DataSnapshot mysnap : snapshot.getChildren()) {
                    Data data = mysnap.getValue(Data.class);

                    values[0] += data.getAmount();
                    DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                    Date date = null;
                    try {
                        date = format.parse(data.getDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    DateWiseIncome.put(date,DateWiseIncome.getOrDefault(date,0)+data.getAmount());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });