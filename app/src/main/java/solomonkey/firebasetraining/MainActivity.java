package solomonkey.firebasetraining;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String key = null;
    String msg;
    EditText txtEmail,txtAge,txtName;
    Button btnAdd,btnUpdate, btnDelete;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtAge = findViewById(R.id.txtAge);

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);



        mDatabase = FirebaseDatabase.getInstance().getReference();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
    }


    protected void add(){
        key = null;

        mDatabase.child("User").orderByChild("Email").equalTo(txtEmail.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                msg = "Updated User";

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    //finds the key of the child with the email equal to the txtemail
                    key = childSnapshot.getKey();
                    Log.wtf("loop: key",key);
                }

                if(key==null) {
                    //does not exist, generate new key
                    msg = "Created New User";
                    key = mDatabase.child("Users").push().getKey();

                    ObjectUsers objUser = new ObjectUsers(txtEmail.getText().toString(),txtAge.getText().toString(),txtName.getText().toString());
                    Map<String, Object> objectValues = objUser.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/User/" + key, objectValues);

                    mDatabase.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed to process", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "Email already Exist", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.wtf("onCanceled",databaseError.getMessage());
            }
        });
    }

    protected void update(){
        key = null;

        mDatabase.child("User").orderByChild("Email").equalTo(txtEmail.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                msg = "Updated User";

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    //finds the key of the child with the email equal to the txtemail
                    key = childSnapshot.getKey();
                    Log.wtf("loop: key",key);
                }

                if(key!=null) {
                    // key found, update child of the key

                    //creates an object
                    ObjectUsers objUser = new ObjectUsers(txtEmail.getText().toString(), txtAge.getText().toString(), txtName.getText().toString());
                    Map<String, Object> objectValues = objUser.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/User/" + key, objectValues);

                    mDatabase.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed to process", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "No email matched, no changes made", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.wtf("onCanceled",databaseError.getMessage());
            }
        });
    }

    protected  void delete(){
        key = null;

        mDatabase.child("User").orderByChild("Email").equalTo(txtEmail.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                msg = "Updated User";

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    //finds the key of the child with the email equal to the txtemail
                    key = childSnapshot.getKey();
                    Log.wtf("loop: key",key);
                }

                if(key!=null) {
                    //does not exist, generate new key
                    mDatabase.child("User").child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.wtf("onCanceled",databaseError.getMessage());
            }
        });
    }



}
