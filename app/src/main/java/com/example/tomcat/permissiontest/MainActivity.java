package com.example.tomcat.permissiontest;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{
    private final static String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_CONTACTS = 1;
    String[] PERMISSIONS = {Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN,
                            Manifest.permission.VIBRATE
                        }; // List of permissions required


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate() ...");

        ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CONTACTS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        Log.i(TAG, "onRequestPermissionsResult(), grantResults[]: " + Arrays.toString(grantResults) +
                "\n permissions[]: " + Arrays.toString(permissions));

        switch (requestCode)
        {
            case REQUEST_CONTACTS:
                if ((grantResults.length > 0) &&
                        (grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[1] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[2] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[3] == PackageManager.PERMISSION_GRANTED)) {
                    //Do your work.
                    for (String permission : permissions) {
                        askDialog(permission);
                    }
                }
                else
                {
                    Toast.makeText(this, "Until you grant the permission, we cannot proceed further",
                            Toast.LENGTH_LONG).show();
                }
                return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public void askPermission()
    {
        for (String permission : PERMISSIONS)
        {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
            {
                askDialog(permission);
            }
            else
            {

            }
        }
    }

    private void askDialog(final String permission)
    {
        Log.i(TAG, "askDialog(), permission: " + permission);
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle(permission + " Request !!");
        builder.setMessage("This App requests " + permission + ", are you OK ? ");
        builder.setPositiveButton(" OK ", new DialogInterface.OnClickListener()
        {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(getBaseContext(), permission + " is enable !!", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
        });

        builder.setNegativeButton(" No ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create();
        builder.show();
    }
}