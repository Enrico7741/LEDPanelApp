package com.example.ledpanelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import org.zeromq.ZMQ;

import java.util.logging.Logger;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {

    private MainController controller;

    // UI elements
    private Button connectBtn;
    private EditText ipText;
    private Menu myMenu;
    private SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new MainController();
        findViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_menu, menu);
        MenuItem switchOnOffItem = menu.findItem(R.id.switchOnOffItem);
        switchOnOffItem.setActionView(R.layout.switch_layout);

        SwitchCompat switchOnOff = switchOnOffItem.getActionView().findViewById(R.id.switchOnOff);
        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
                if(!switchOnOff.isPressed()) {
                    return;
                }
                if (switchOnOff.isChecked()) {
                    controller.turnOn();
                } else {
                    controller.turnOff();
                }
            }
        });

        myMenu = menu;
        myMenu.findItem(R.id.switchOnOffItem).setVisible(false);
        return true;
    }

    private void findViews() {
        connectBtn = (Button)findViewById(R.id.button_connect);
        ipText = (EditText)findViewById(R.id.editText_ip);
        seekbar = (SeekBar)findViewById(R.id.seekBar2);;
        seekbar.setEnabled(false);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                int ret = ((seekbar.getProgress() + 1) * 10);
                controller.setBrightness(ret);

            }
        });

    }

    public void onClickConnect(View v) {
        if (controller.isConnected()) {
            //we are connected -> disconnect
            controller.disconnect();
            changeToDisconnected();
        } else {
            //we are disconnected -> connect
            if (!Patterns.IP_ADDRESS.matcher(ipText.getText().toString()).matches()) {
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
        myMenu.findItem(R.id.switchOnOffItem).setVisible(true);
        seekbar.setEnabled(true);
    }

    public void changeToDisconnected() {
        connectBtn.setText(getResources().getString(R.string.verbinden));
        ipText.setEnabled(true);
        myMenu.findItem(R.id.switchOnOffItem).setVisible(false);
        seekbar.setEnabled(false);
    }
}