package com.odenktools.netzmeodenktools.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.odenktools.netzmeodenktools.activity.customer.AddCustomerActivity;
import com.odenktools.netzmeodenktools.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        fireButtonAction();
    }

    private void fireButtonAction() {
        // Scan QR
        binding.btnProfileCustomer.setOnClickListener(v -> {
            //goto
            gotoActivity(AddCustomerActivity.class);
        });
        // Pay Invoice
        binding.btnMerchantProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add
                gotoActivity(AddCustomerActivity.class);
            }
        });
    }

    private void gotoActivity(Class<?> cls) {
        Intent intent = new Intent(ProfileActivity.this, cls);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        startActivity(intent);
    }
}
