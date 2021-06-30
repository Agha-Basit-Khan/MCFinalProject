package com.aghabasit.finalprojectei;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                values[1] = 0;

                for (DataSnapshot mysnap : snapshot.getChildren()) {

                    Data data = mysnap.getValue(Data.class);

                    values[1] += data.getAmount();
                    DateFormat format = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
                    Date date = null;
                    try {
                        date = format.parse(data.getDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    DateWiseExpense.put(date,DateWiseExpense.getOrDefault(date,0)+data.getAmount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Pie Chart
        PieChart pieChart = myView.findViewById(R.id.piechart);
        ArrayList<PieEntry> data=new ArrayList<PieEntry>() ;
        for(int i=0;i<values.length;i++){
            data.add(new PieEntry(values[i], type[i]));
        }

        int[] colorClassArray=new int[]{0xFF669900, 0xFFCC0000};
        PieDataSet pieDataSet = new PieDataSet(data,"");
        pieDataSet.setColors(colorClassArray);
        PieData pieData=new PieData(pieDataSet);
        pieData.setValueTextSize(25);
        pieChart.setData(pieData);
        pieChart.animateXY(2000, 2000);

        pieChart.setDrawHoleEnabled(false);
        Legend l = pieChart.getLegend();
        l.setTextSize(18);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setTextColor(Color.CYAN);
        l.setEnabled(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();


        //Line Chart 1
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        String[] xAxisValues = new String[DateWiseIncome.size()];
        ArrayList<Entry> incomeEntries = new ArrayList<>();
        int i=0;
        for (Map.Entry<Date, Integer> entry : DateWiseIncome.entrySet()){
            Format formatter = new SimpleDateFormat("MMM d, yyyy");
            String s = formatter.format(entry.getKey());
            xAxisValues[i]=s;
            incomeEntries.add(new Entry(i,entry.getValue()));
            i++;
            Log.i("DATE", xAxisValues[i-1]);
            Log.i("AMOUNT", String.valueOf(entry.getValue()));
        }