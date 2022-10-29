package com.odenktools.netzmeodenktools;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.odenktools.netzmeodenktools.activity.InvoiceActivity;
import com.odenktools.netzmeodenktools.activity.McPaymentActivity;
import com.odenktools.netzmeodenktools.activity.ProfileActivity;
import com.odenktools.netzmeodenktools.activity.ScannerQrActivity;
import com.odenktools.netzmeodenktools.activity.TopUpActivity;
import com.odenktools.netzmeodenktools.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding = null;
    private View mainView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        mainView = binding.getRoot();
        setContentView(mainView);
        fireButtonAction();
        checkCustomerProfile();
    }

    private void checkCustomerProfile() {

    }

    private void fireButtonAction() {
        // Scan QR
        binding.btnGotoScanQr.setOnClickListener(v -> {
            //goto
            gotoActivity(ScannerQrActivity.class);
        });
        // Pay Invoice
        binding.btnPayInvoiceMerchant.setOnClickListener(v -> {
            //Add
            gotoActivity(InvoiceActivity.class);
        });
        // Pay Invoice
        binding.btnProfile.setOnClickListener(v -> {
            //Add
            gotoActivity(ProfileActivity.class);
        });
        // Topup User
        binding.btnTopup.setOnClickListener(v -> {
            //Add
            gotoActivity(TopUpActivity.class);
        });
        // MC Payment
        binding.btnMcPayment.setOnClickListener(v -> {
            //goto
            gotoActivity(McPaymentActivity.class);
        });
    }

    private void gotoActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        startActivity(intent);
    }

    private void showExitDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this,
                R.style.CustomAlertDialog);
        alert.setMessage(getResources().getString(R.string.exit_dialog_confirm));
        alert.setCancelable(true);

        alert.setNegativeButton("Tidak", (dialogInterface, i) ->
                dialogInterface.cancel());

        alert.setPositiveButton("Ya", (dialogInterface, i) -> {
            System.runFinalization();
            Runtime.getRuntime().gc();
            System.gc();
            System.exit(0);
            finish();
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
        Button buttonPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonPositive.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        Button buttonNegative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonNegative.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }
}