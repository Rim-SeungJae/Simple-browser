package com.example.map_browser;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;

import java.util.ArrayList;

public class ViewBookmark extends AppCompatActivity {

    private ListView mListView;
    long userid;
    BookmarkAdapter adapter;
    ArrayList<Bookmark> data;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_bookmark);

        mListView=(ListView)findViewById(R.id.listview);


        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("KAKAO API","Session closed"+errorResult);
            }

            @Override
            public void onSuccess(MeV2Response result) {
                userid=result.getId();
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("bookmark/"+userid);
                //reference.setValue(new Bookmark("hi","test",userid));
                Log.i("KAKAO API","id:"+userid);

                data=new ArrayList<Bookmark>();
                adapter=new BookmarkAdapter(ViewBookmark.this,data);
                mListView.setAdapter(adapter);

                ValueEventListener bookmarkListener=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        data.clear();
                        Log.i("firebase","listener online");
                        for(DataSnapshot Snapshot:dataSnapshot.getChildren()){
                            Log.i("firebase","for loop");
                            Bookmark get=Snapshot.getValue(Bookmark.class);
                            data.add(get);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("firebase","listener fail");
                    }
                };

                reference.addValueEventListener(bookmarkListener);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent tomain=new Intent(ViewBookmark.this,MainActivity.class);
                        tomain.putExtra("url",data.get(position).getUrl());
                        startActivity(tomain);
                    }
                });
            }
        });


    }
}
