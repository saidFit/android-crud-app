package com.example.myapplication1;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floating = findViewById(R.id.floating);
        recyclerView =findViewById(R.id.recyclerView);
        floating.setOnClickListener(v -> {
            startActivity(new Intent(this,UploadData.class));
        });


//        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
//        recyclerView.setLayoutManager(gridLayoutManager);
//
//       List<DataClass> dataList = new ArrayList<>();
//       NoteAdapter adapter = new NoteAdapter(MainActivity.this, dataList);
//        recyclerView.setAdapter(adapter);
//
//       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Android database");
//
//         databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                dataList.clear();
//                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
//                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
//                    Log.d("key", itemSnapshot.getKey());
//                    dataClass.setKey(itemSnapshot.getKey());
//                    dataList.add(dataClass);
//                }
//                adapter.notifyDataSetChanged();
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });









        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Android database");
        Toast.makeText(this, "loading data...", Toast.LENGTH_SHORT).show();
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  List<DataClass> dataList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataClass data = snapshot.getValue(DataClass.class);
                    data.setKey(snapshot.getKey());
                    dataList.add(data);
                }
                // Now you have the data in dataList, you can pass it to your adapter.
//                NoteAdapter adapter = new NoteAdapter(dataList);
//                Log.d("title", dataList.get(0).getDataTitle());
//                recyclerView.setAdapter(adapter);

       // TODO--GridLayoutManager is being created and configured to display items in a grid layout with 1 column.
       // TODO--recyclerView.setLayoutManager(gridLayoutManager) sets this layout manager to your RecyclerView. It tells the RecyclerView how to arrange its items.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

               NoteAdapter adapter = new NoteAdapter(MainActivity.this, dataList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
        Toast.makeText(this, "finding successfully", Toast.LENGTH_SHORT).show();

    }

}