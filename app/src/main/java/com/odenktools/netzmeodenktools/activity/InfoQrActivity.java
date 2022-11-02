package com.odenktools.netzmeodenktools.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.odenktools.netzmeodenktools.databinding.ActivityInfoQrBinding;

import timber.log.Timber;

public class InfoQrActivity extends AppCompatActivity {

    private String jsonResult = "";
    private ActivityInfoQrBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        jsonResult = bundle.getString("jsonResult", "");
        binding = ActivityInfoQrBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        try {
            JsonObject json = new Gson().fromJson(jsonResult, JsonObject.class);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(json);
            //Toast.makeText(InfoQrActivity.this, jsonResult, Toast.LENGTH_SHORT).show();
            //QRis qRis = new Gson().fromJson(jsonResult, QRis.class);
            //binding.textView.setText(prettyJson);
            binding.editTextTextMultiLine.setText(prettyJson);
        } catch (Exception e) {
            Toast.makeText(InfoQrActivity.this, "sssss", Toast.LENGTH_SHORT).show();
            Timber.e("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR %s", e.getMessage());
        }
    }
}