package com.social420.social420;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;

import static com.facebook.AccessToken.getCurrentAccessToken;

public class facebook_login extends AppCompatActivity {

    private CallbackManager mCallbackManager;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mAuthlistener;

    private ImageView fb;
    private LoginButton loginButton;

    private FirebaseDatabase mdatabase;
    private DatabaseReference newRef;
    private ImageView loginimage;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_facebook_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loginimage = (ImageView)findViewById(R.id.imageView);
        loginimage.setVisibility(android.view.View.GONE);
        signup = (TextView)findViewById(R.id.signup);



        SharedPreferences sp1= getSharedPreferences("Login",0);

        String signedup=sp1.getString("signedup", null);

        if (signedup != null){

            Intent intent = new Intent(facebook_login.this,multiple_permissions.class);
            startActivity(intent);

        }


        signup.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                Intent intent = new Intent(facebook_login.this,signup.class);
                startActivity(intent);

            }
        });



        if(getIntent().hasExtra("logout")){

            LoginManager.getInstance().logOut();
        }

        mauth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();

        fb = (ImageView) findViewById(R.id.fb);

        fb.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                loginButton.performClick();
                loginimage.setVisibility(android.view.View.VISIBLE);



            }
        });

        loginButton = (LoginButton)findViewById(R.id.loginbutton);
        loginButton.setReadPermissions("email","public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        mAuthlistener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(facebook_login.this,multiple_permissions.class);
                    String email = user.getEmail();
                    String displayname = user.getDisplayName();
                    String profilephoto = user.getPhotoUrl().toString();
                    String userid = mauth.getCurrentUser().getUid();




                    String profilepicbig = ("https://graph.facebook.com/" + getCurrentAccessToken().getUserId() + "/picture?type=large").toString();

                    mdatabase = FirebaseDatabase.getInstance();
                    newRef = mdatabase.getReference("Users");

                    newRef.child(userid).child("name").setValue(displayname);
                    newRef.child(userid).child("profilepic").setValue(profilepicbig);





                    SharedPreferences sp=getSharedPreferences("Login", 0);
                    SharedPreferences.Editor Ed=sp.edit();
                    Ed.putString("Unm",displayname );
                    Ed.putString("email",email);
                    Ed.putString("propic",profilephoto);
                    Ed.putString("propicbig",profilepicbig);
                    Ed.putString("id",userid);

                    Ed.commit();




                    //Toast.makeText(getBaseContext(), email+displayname+profilepicbig, Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }

            }
        };



    }
    @Override
    public void onStart(){
        super.onStart();

        mauth.addAuthStateListener(mAuthlistener);
    }

    @Override
    public void onStop(){
        super.onStop();

        if (mAuthlistener != null){
            mauth.removeAuthStateListener(mAuthlistener);
        }
    }

    private void handleFacebookAccessToken(AccessToken token){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mauth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){

                    Toast.makeText(getBaseContext(), "Authentication Failed", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
    }




}
