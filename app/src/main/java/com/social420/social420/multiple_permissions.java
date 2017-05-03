package com.social420.social420;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by pradeeppj on 07/04/17.
 */

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;



public class multiple_permissions extends AppCompatActivity {

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    private TextView txtPermissions;
    private Button btnCheckPermissions;
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_permissions);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);



        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);

        //txtPermissions = (TextView) findViewById(R.id.txtPermissions);
        //btnCheckPermissions = (Button) findViewById(R.id.btnCheckPermissions);

        if(ActivityCompat.checkSelfPermission(multiple_permissions.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(multiple_permissions.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(multiple_permissions.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(multiple_permissions.this, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(multiple_permissions.this, permissionsRequired[4]) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(multiple_permissions.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(multiple_permissions.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(multiple_permissions.this,permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(multiple_permissions.this,permissionsRequired[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(multiple_permissions.this,permissionsRequired[4])){
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(multiple_permissions.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(multiple_permissions.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(multiple_permissions.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        ActivityCompat.requestPermissions(multiple_permissions.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                        //sentToSettings = true;
                        //Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        //Uri uri = Uri.fromParts("package", getPackageName(), null);
                        //intent.setData(uri);
                        //startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }  else {
                //just request the permission
                ActivityCompat.requestPermissions(multiple_permissions.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
            }

            //txtPermissions.setText("Permissions Required");

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0],true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT){
            //check if all permissions are granted
            boolean allgranted = false;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if(allgranted){
                proceedAfterPermission();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(multiple_permissions.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(multiple_permissions.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(multiple_permissions.this,permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(multiple_permissions.this,permissionsRequired[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(multiple_permissions.this,permissionsRequired[4])){
                //txtPermissions.setText("Permissions Required");
                AlertDialog.Builder builder = new AlertDialog.Builder(multiple_permissions.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(multiple_permissions.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(multiple_permissions.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    private void proceedAfterPermission() {
        //txtPermissions.setText("We've got all permissions");
        //Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();


        Intent in = new Intent(multiple_permissions.this, social.class);
        startActivity(in);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(multiple_permissions.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }
}

