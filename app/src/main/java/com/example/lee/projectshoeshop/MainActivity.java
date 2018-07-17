package com.example.lee.projectshoeshop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lee.projectshoeshop.Activity.AndressActivity;
import com.example.lee.projectshoeshop.Activity.LoginActivity;
import com.example.lee.projectshoeshop.Activity.MapsActivity;

public class MainActivity extends Activity {
    private Button btnfacebook;
    private Button btnContinue;
    private Button btnweb;
    private Button btnAndress;
    private Button btnSD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSD = (Button) findViewById(R.id.btnshutdown);
        btnweb = (Button) findViewById(R.id.btnweb);
        btnContinue = (Button) findViewById(R.id.btnContinues);
        btnfacebook = (Button) findViewById(R.id.btnfb);
        btnAndress = (Button) findViewById(R.id.btnAndress);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToLogin();
            }
        });
        btnAndress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToAndroid();
            }
        });
        btnSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        btnweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToWeb();
            }
        });
        btnfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTofb();
            }
        });
    }

    protected void sendToWeb() {
        String url = "https://drake.vn/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    protected void sendTofb() {
        String url = "https://facebook.com/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    protected void sendToAndroid() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    protected void sendToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
