package com.example.map_browser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;

public class AddBookmark extends AppCompatActivity {

    String url;
    EditText urlet,titleet;
    private DatabaseReference UrlReference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bookmark);

        if(getIntent().getExtras()!=null){
            Intent intent=getIntent();
            url=intent.getStringExtra("url");
        }
        urlet=(EditText)findViewById(R.id.add_url);
        titleet=findViewById(R.id.add_title);
        urlet.setText(url);
        Button btn=(Button)findViewById(R.id.add_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleet.getText().toString().length()*urlet.getText().toString().length()==0){
                    Toast.makeText(AddBookmark.this,"Please fill all blanks",Toast.LENGTH_SHORT).show();
                }
                else{
                    UserManagement.getInstance().me(new MeV2ResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Log.e("KAKAO API","Session closed"+errorResult);
                        }

                        @Override
                        public void onSuccess(MeV2Response result) {
                            UrlReference= FirebaseDatabase.getInstance().getReference("bookmark/"+result.getId()).push();
                            UrlReference.setValue(new Bookmark(titleet.getText().toString(),urlet.getText().toString(),result.getId()));
                        }
                    });
                }
                Intent mainIntent=new Intent(AddBookmark.this,MainActivity.class);
                mainIntent.putExtra("url",url);
                startActivity(mainIntent);
            }
        });
    }
}
