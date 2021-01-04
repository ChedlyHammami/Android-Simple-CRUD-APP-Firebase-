package com.example.films;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    ActionBar actionBar;
    EditText mTitleEt, mDescrtiptionEt;
    Button mSaveBtn,mListBtn;
    ProgressDialog pd;
    FirebaseFirestore db;
    String pId,pTitle,pDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitleEt = findViewById(R.id.titleEt);
        actionBar = getSupportActionBar();
        mDescrtiptionEt = findViewById(R.id.descreptionEt);
        pd = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        mSaveBtn = findViewById(R.id.saveBtn);
        mListBtn = findViewById(R.id.listBtn);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            actionBar.setTitle("Update Film");
            mSaveBtn.setText("Update");
            pId = bundle.getString("pId");
            pTitle = bundle.getString("pTitle");
            pDescription = bundle.getString("pDescription");
            mTitleEt.setText(pTitle);
            mDescrtiptionEt.setText(pDescription);

        }
        else {
            actionBar.setTitle("Add Film");
            mSaveBtn.setText("Save");
        }

        mListBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ListActivity.class));
                finish();
            }
        });
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle != null){
                    String id = pId;
                    String title = mTitleEt.getText().toString().trim();
                    String description = mDescrtiptionEt.getText().toString().trim();
                    updateData(id, title, description);
                }
                else{
                    String title = mTitleEt.getText().toString().trim();
                    String description = mDescrtiptionEt.getText().toString().trim();
                    uploadData(title,description);
                }

            }
        });


// Add a new document with a generated ID

    }

    private void updateData(String id, String title, String description) {
        pd.setTitle("Updating Film...");
        pd.show();
        db.collection("Documents").document(id)
                .update("title",title,"description",description)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast toast =Toast.makeText(MainActivity.this,"Film Updated Successfully",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast toast =Toast.makeText(MainActivity.this,"Error On Updating Film",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }

    private void uploadData(String title, String description) {
        pd.setTitle("Adding Film...");
        pd.show();
        String id = UUID.randomUUID().toString();
        Map<String, Object> doc = new HashMap<>();
        doc.put("id",id);
        doc.put("title",title);
        doc.put("description",description);

        db.collection("Documents").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast toast =Toast.makeText(MainActivity.this,"Film Added Successfully",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast toast =Toast.makeText(MainActivity.this,"Error On Adding Film",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }
}