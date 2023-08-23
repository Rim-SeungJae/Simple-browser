package com.example.map_browser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    WebView webView;
    SwipeRefreshLayout swipe;
    EditText webAddressView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Menu menu;
    MenuItem item1,item2,item3,item4;
    String url="https://www.google.com";
    ObjectAnimator animation,animation2;
    boolean animation_flag;

    private void gethashkey(){
        PackageInfo packageInfo=null;
        try{
            packageInfo=getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        if(packageInfo==null)
        {
            Log.e("KeyHash","KeyHash:null");
        }

        for(Signature signature:packageInfo.signatures){
            try{
                MessageDigest md=MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }catch (NoSuchAlgorithmException e){
                Log.e("KeyHash","Unable to get MessageDigest. signatures"+signature,e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animation_flag=false;


        webAddressView=findViewById(R.id.et_web_address);

        webAddressView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    String url = webAddressView.getText().toString();
                    if (!url.isEmpty())
                        if(url.equals("zerg rush")){
                            animation_flag=true;
                            final ImageView imageView=findViewById(R.id.imageView);
                            imageView.setVisibility(View.VISIBLE);
                            animation=ObjectAnimator.ofFloat(webView,"translationY",-2000f);
                            animation.setDuration(5000);
                            animation2=ObjectAnimator.ofFloat(imageView,"translationY",-2000f);
                            animation2.setDuration(5000);
                            animation.start();
                            animation2.start();
                        }
                    else{
                            webView.loadUrl(url);
                        }
                    return true;
                }
                return false;
            }
        });

        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(webView.getUrl());
            }
        });

        androidx.appcompat.widget.Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.drawer);

        menu = navigationView.getMenu();

        item1=menu.findItem(R.id.Login);
        item2=menu.findItem(R.id.Logout);
        item3=menu.findItem(R.id.Bookmarks);
        item4=menu.findItem(R.id.add_bookmark);

        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("KAKAO API","Session closed"+errorResult);
            }

            @Override
            public void onSuccess(MeV2Response result) {
                View headerView=navigationView.getHeaderView(0);
                TextView username=(TextView)headerView.findViewById(R.id.drawer_username);
                Log.i("KAKAO API","user id"+result.getId());
                UserAccount kakaoAccount=result.getKakaoAccount();
                if(kakaoAccount!=null){
                    Profile profile=kakaoAccount.getProfile();
                    if(profile!=null){
                        username.setText(profile.getNickname());
                        item2.setVisible(true);
                        item3.setVisible(true);
                        item4.setVisible(true);

                    }
                    else if(kakaoAccount.profileNeedsAgreement()== OptionalBoolean.TRUE){

                    }
                    else{

                    }
                }
            }
        });


        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, tb, R.string.app_name, R.string.app_name);
        drawerToggle.syncState();

        if(getIntent().getExtras()!=null){
            Intent intent=getIntent();
            url=intent.getStringExtra("url");
        }
        WebAction();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        closeDrawer();

        switch (item.getItemId()){

            case R.id.Login:
                Intent loginintent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(loginintent);
                break;

            case R.id.Logout:
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Log.i("KAKAO API","successfully logout");
                    }
                });
                View headerView=navigationView.getHeaderView(0);
                TextView username=(TextView)headerView.findViewById(R.id.drawer_username);
                username.setText("Please Login");
                item2.setVisible(false);
                item3.setVisible(false);
                item4.setVisible(false);
                break;

            case R.id.add_bookmark:
                Intent addintent=new Intent(MainActivity.this,AddBookmark.class);
                addintent.putExtra("url",webView.getUrl().toString());
                startActivity(addintent);
                break;

            case R.id.Bookmarks:
                Intent viewintent=new Intent(MainActivity.this,ViewBookmark.class);
                startActivity(viewintent);
                break;


        }




        return false;
    }

    private void closeDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    public void WebAction(){

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl(url);
        swipe.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient(){

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                webView.loadUrl("file:///android_assets/error.html");

            }

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                webAddressView.setText(url);
                swipe.setRefreshing(false);
            }

        });

    }


    @Override
    public void onBackPressed(){

        if (webView.canGoBack()){
            webView.goBack();
        }else {
            finish();
        }
    }
}