package com.example.ledpanelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {

    private MainController controller;

    // UI elements
    private Button connectBtn;
    private EditText ipText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new MainController();
        findViews();
    }

    private void findViews() {
        connectBtn = (Button)findViewById(R.id.button_connect);
        ipText = (EditText)findViewById(R.id.editText_ip);
    }

    public void onClickConnect(View v) {
        if (controller.isConnected()) {
            //we are connected -> disconnect
            controller.disconnect();
            changeToDisconnected();
        } else {
            //we are disconnected -> connect
            if (!Patterns.IP_ADDRESS.matcher(ipText.getText()).matches()) {
                makeText(MainActivity.this, getResources().getString(R.string.falscheIp), LENGTH_SHORT).show();
                return;
            }
            if (controller.connect(ipText.getText().toString())) {
                changeToConnected();
            }
        }
    }

    public void changeToConnected() {
        connectBtn.setText(getResources().getString(R.string.trennen));
        ipText.setEnabled(false);
    }

    public void changeToDisconnected() {
        connectBtn.setText(getResources().getString(R.string.verbinden));
        ipText.setEnabled(true);
    }
}