package com.odenktools.netzmeodenktools.activity.customer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.odenktools.netzmeodenktools.BuildConfig;
import com.odenktools.netzmeodenktools.databinding.ActivityAddCustomerBinding;
import com.odenktools.netzmeodenktools.model.db.CustomerDb;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import io.paperdb.Paper;
import timber.log.Timber;

import static com.odenktools.netzmeodenktools.util.Constanta.*;


public class AddCustomerActivity extends AppCompatActivity {

    private ActivityAddCustomerBinding binding = null;
    private String customerUid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCustomerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        fireButtonAction();
        fillDebugData();

        /*List<String> allKeys = Paper.book().getAllKeys();
        for (int x = 0; x < allKeys.size(); x++) {
            Timber.d("ssssssss %s", allKeys.get(x));
        }*/

        //Paper.book().delete(CUSTOMER_TABLE);
        //customerDbList = Paper.book().read(Constanta.CUSTOMER_TABLE);
    }

    @SuppressLint("SetTextI18n")
    private void fillDebugData() {
        if (BuildConfig.DEBUG) {
            binding.etCustomerFullName.setText("Rahyan Buyer Tester");
            binding.etCustomerNetzmeId.setText("re3WVWvE");
            binding.etCustomerPhone.setText("+6289671000082");
            binding.etCustomerEmail.setText("rahyan@netzme.id");
            binding.etCustomerAddress.setText("Guntur bumi");
        }
    }

    private void fireButtonAction() {
        binding.btnSaveCustomer.setOnClickListener(v -> {
            //saving
            save();
        });
        binding.btnDeleteAllCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().delete(CUSTOMER_TABLE);
            }
        });

        binding.btnDeleteCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CustomerDb> fff = Paper.book().read(CUSTOMER_TABLE, new ArrayList<>());
                if (fff == null) {
                    return;
                }
                for (CustomerDb item : fff) {
                    if (StringUtils.equals(item.getId(), customerUid)) {
                        fff.remove(item);
                    }
                    Timber.d("ddddddddddddddd %s", item.getId());
                }
                Paper.book().write(CUSTOMER_TABLE, fff);
            }
        });
        binding.btnGetAllCustomer.setOnClickListener(view -> {
            List<CustomerDb> fff = Paper.book().read(CUSTOMER_TABLE, new ArrayList<>());
            if (fff == null) {
                return;
            }
            for (CustomerDb item : fff) {
                Timber.d("READ_ALL_DATA %s", item.getId());
            }
        });
    }

    private void save() {
        if (StringUtils.isEmpty(binding.etCustomerFullName.getText().toString())) {
            return;
        }
        if (StringUtils.isEmpty(binding.etCustomerNetzmeId.getText().toString())) {
            return;
        }
        if (StringUtils.isEmpty(binding.etCustomerPhone.getText().toString())) {
            return;
        }
        if (StringUtils.isEmpty(binding.etCustomerEmail.getText().toString())) {
            return;
        }
        if (StringUtils.isEmpty(binding.etCustomerAddress.getText().toString())) {
            return;
        }
        List<CustomerDb> customerDbList = new ArrayList<>();
        String customerName = binding.etCustomerFullName.getText().toString();
        String etCustomerNetzmeId = binding.etCustomerNetzmeId.getText().toString();
        String etCustomerPhone = binding.etCustomerPhone.getText().toString();
        String etCustomerEmail = binding.etCustomerEmail.getText().toString();
        String etCustomerAddress = binding.etCustomerAddress.getText().toString();
        customerUid = UUID.randomUUID().toString();
        CustomerDb customerDb = new CustomerDb();
        customerDb.setId(customerUid);
        customerDb.setCustomerAddress(etCustomerAddress);
        customerDb.setCustomerFullName(customerName);
        customerDb.setCustomerAddress(etCustomerNetzmeId);
        customerDb.setCustomerPhone(etCustomerPhone);
        customerDb.setCustomerEmail(etCustomerEmail);
        customerDb.setIsActive(1);
        customerDbList.add(customerDb);
        Paper.book().write(CUSTOMER_TABLE, customerDbList);
    }
}