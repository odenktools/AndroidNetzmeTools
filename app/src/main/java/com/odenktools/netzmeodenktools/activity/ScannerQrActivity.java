package com.odenktools.netzmeodenktools.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.android.Intents;
import com.google.zxing.common.HybridBinarizer;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.odenktools.netzmeodenktools.R;
import com.odenktools.netzmeodenktools.databinding.ActivityScannerQrBinding;
import com.odenktools.netzmeodenktools.model.qr.EMVco;
import com.odenktools.netzmeodenktools.model.qr.QRis;
import com.odenktools.netzmeodenktools.util.AppUtils;
import com.odenktools.netzmeodenktools.util.Bitmaps;
import com.odenktools.netzmeodenktools.util.FileUtil;
import com.odenktools.netzmeodenktools.util.MoneyHelper;
import com.odenktools.netzmeodenktools.util.QRISParser;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import timber.log.Timber;

public class ScannerQrActivity extends AppCompatActivity {

    private DecoratedBarcodeView scannerView = null;
    private ActivityScannerQrBinding binding = null;
    private static final int PERMISSION_CAMERA = 1100;
    public static Integer SELECT_IMG = 115;
    public static Integer PERMISSION_READ_GALLERY = 1;
    private String imagePath = "";
    private final Collection<BarcodeFormat> formats
            = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
    private String jsonResult = "";
    private String additionalData = "";
    private String dataResult = "";
    private BeepManager beepManager;
    private View mainView = null;
    private Boolean emvCoDecode = true;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher
            = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Intent originalIntent = result.getOriginalIntent();
                    if (originalIntent == null) {
                        AppUtils.showFailedSnackBar(mainView, ScannerQrActivity.this,
                                "Cancelled");
                    } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                        AppUtils.showFailedSnackBar(mainView, ScannerQrActivity.this,
                                "Cancelled due to missing camera permission");
                    }
                } else {
                    AppUtils.showSnackBar(mainView, ScannerQrActivity.this,
                            result.getContents());
                }
            });

    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    private void setEnableButton(boolean disableBUtton) {
        binding.chkAddtional.setEnabled(disableBUtton);
        binding.btnGoto.setEnabled(disableBUtton);
        binding.btnCopyText.setEnabled(disableBUtton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScannerQrBinding.inflate(getLayoutInflater());
        mainView = binding.getRoot();
        setContentView(mainView);
        initCamera();
        doRequestPermissionCamera();
        setEnableButton(false);
        beepManager = new BeepManager(this);
        fireButton();
    }

    private void fireButton() {
        binding.chkEmvcoDecode.setChecked(true);
        binding.chkEmvcoDecode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            emvCoDecode = isChecked;
            binding.chkAddtional.setEnabled(isChecked);
        });

        binding.btnRescan.setOnClickListener(v -> {
            if (scannerView != null) {
                stopHideCamera();
                scannerView = null;
                relaunchScanner();
                doRequestPermissionCamera();
                binding.tvResult.setText("");
                binding.tvResult.setVisibility(View.GONE);
                binding.zxScan.setVisibility(View.VISIBLE);
            }
        });

        binding.btnCopyText.setOnClickListener(v -> {
            setClipboard(ScannerQrActivity.this, binding.tvResult.getText().toString());
            AppUtils.showFailedSnackBar(mainView, ScannerQrActivity.this,
                    "Text to clipboard");
        });

        binding.btnUploadQR.setOnClickListener(v -> {
            pickImage();
        });

        binding.btnGoto.setOnClickListener(v -> {
            Intent intent = new Intent(ScannerQrActivity.this, InfoQrActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("jsonResult", jsonResult);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        binding.chkAddtional.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (StringUtils.isNotEmpty(dataResult)) {
                        binding.tvResult.setText(additionalData);
                    }
                } else {
                    if (StringUtils.isNotEmpty(dataResult)) {
                        binding.tvResult.setText(dataResult);
                    }
                }
            }
        });
    }

    private void initCamera() {
        scannerView = binding.zxScan;
        scannerView.setDecoderFactory(new DefaultDecoderFactory(formats));
        scannerView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        scannerView.initializeFromIntent(getIntent());
        //scannerView.decodeContinuous(callback);
    }

    private void launchFirstScanner() {
        scannerView.decodeContinuous(callback);
        scannerView.resume();
    }

    private void relaunchScanner() {
        if (scannerView != null) {
            scannerView.destroyDrawingCache();
            scannerView.getBarcodeView().stopDecoding();
            scannerView.decodeContinuous(null);
            scannerView.pause();
            scannerView = null;
        }
        scannerView = binding.zxScan;
        scannerView.resume();
        scannerView.decodeContinuous(callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ScannerQrActivity.SELECT_IMG) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    AppUtils.showFailedSnackBar(mainView, ScannerQrActivity.this,
                            "Tidak dapat akses gallery.");
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSION_READ_GALLERY);
                    return;
                }
                resizeImageFromIntent(resultCode, data);
            }
        }
    }

    private Bitmap bitmapDecode = null;

    private void resizeImageFromIntent(final int resultCode, final Intent data) {
        if (resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();
            AppUtils.runOnUIThread(() -> {
                try {
                    File file = FileUtil.fromIntent(ScannerQrActivity.this, uri);
                    final Uri phototUri = Uri.fromFile(file);
                    imagePath = phototUri.getPath();
                    String mCurrentPhoto = file.getAbsolutePath();
                    bitmapDecode = Bitmaps.getResizedBitmap(mCurrentPhoto,
                            200, 200, false);
                    FileOutputStream out = new FileOutputStream(imagePath);
                    bitmapDecode.compress(Bitmap.CompressFormat.JPEG, 100, out);


                    int width = bitmapDecode.getWidth(), height = bitmapDecode.getHeight();
                    int[] pixels = new int[width * height];
                    bitmapDecode.getPixels(pixels, 0, width, 0, 0, width, height);
                    bitmapDecode.recycle();
                    bitmapDecode = null;
                    RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                    BinaryBitmap bBitmap = new BinaryBitmap(new HybridBinarizer(source));
                    MultiFormatReader reader = new MultiFormatReader();
                    try {
                        Result result = reader.decode(bBitmap);
                        String dataId = result.getText();
                        binding.tvResult.setText(dataId);
                        binding.tvResult.setVisibility(View.VISIBLE);
                        dataResult = dataId;
                        scannerView.setVisibility(View.GONE);
                        try {
                            QRis qRis = QRISParser.getQRis(MoneyHelper.IDR(15000), dataId);
                            EMVco emVco = qRis;
                            jsonResult = new Gson().toJson(emVco);
                            setEnableButton(true);
                            Timber.d("sssssssssssssssssssssss %s", jsonResult);
                            Timber.d("sssssssssssssssssssssss %s", qRis.getMerchantName());
                            Timber.d("sssssssssssssssssssssss %s", qRis.getPostalCode());
                            Timber.d("sssssssssssssssssssssss %s", qRis.getMerchantCity());
                            Timber.d("sssssssssssssssssssssss %s", qRis.getAdditionalData().getOriginalAdditionalData());
                            Timber.d("sssssssssssssssssssssss %s", qRis.getTransactionAmount());
                            if (qRis.getAdditionalData() != null && qRis.getAdditionalData().getProprietaryData() != null) {
                                additionalData = qRis.getAdditionalData().getOriginalAdditionalData();
                            } else {
                                AppUtils.showFailedSnackBar(mainView, ScannerQrActivity.this,
                                        "No additional data");
                            }
                        } catch (Exception e) {
                            setEnableButton(false);
                            AppUtils.showFailedSnackBar(mainView, ScannerQrActivity.this,
                                    e.getMessage());
                        }
                    } catch (NotFoundException e) {
                        //Log.e("TAG", "decode exception", e);
                    }
                } catch (IOException e) {
                    imagePath = "";
                    Timber.e(e);
                }
            });
        }
    }

    /**
     * Meminta permission CAMERA.
     */
    private void doRequestPermissionCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{
                        Manifest.permission.CAMERA
                }, PERMISSION_CAMERA);
            } else {
                launchFirstScanner();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchFirstScanner();
            } else {
                showPermissionDeniedCamera();
            }
        }
    }

    private void showPermissionDeniedCamera() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_camera_permission_title));
        builder.setMessage(getResources().getString(R.string.dialog_camera_permission_desc));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.dialog_btn_positive), (dialog, which) -> {
            //no-op
            doRequestPermissionCamera();
        });
        builder.setNegativeButton(getResources().getString(R.string.dialog_btn_negative), (dialog, which) -> {
            //no-op
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        if (scannerView != null) {
            scannerView.destroyDrawingCache();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (scannerView != null) {
            scannerView.pause();
        }
    }

    private void pickImage() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_READ_GALLERY);
            return;
        }
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startDiscoverImage(this, Intent.createChooser(intent, "Pilih foto..."));
    }

    private void startDiscoverImage(final Activity activity, final Intent intent) {
        activity.startActivityForResult(intent, ScannerQrActivity.SELECT_IMG);
    }

    private void stopHideCamera() {
        scannerView.destroyDrawingCache();
        scannerView.pause();
        scannerView.setVisibility(View.GONE);
    }

    private void scanEmvco(String dataId) {
        try {
            QRis qRis = QRISParser.getQRis(MoneyHelper.IDR(15000), dataId);
            jsonResult = new Gson().toJson(qRis);
            setEnableButton(true);
            Timber.d("sssssssssssssssssssssss %s", jsonResult);
            Timber.d("sssssssssssssssssssssss %s", qRis.getMerchantName());
            Timber.d("sssssssssssssssssssssss %s", qRis.getPostalCode());
            Timber.d("sssssssssssssssssssssss %s", qRis.getMerchantCity());
            Timber.d("sssssssssssssssssssssss %s", qRis.getAdditionalData().getOriginalAdditionalData());
            Timber.d("sssssssssssssssssssssss %s", qRis.getTransactionAmount());
            if (qRis.getAdditionalData() != null && qRis.getAdditionalData().getProprietaryData() != null) {
                additionalData = qRis.getAdditionalData().getOriginalAdditionalData();
            } else {
                AppUtils.showSnackBar(mainView, ScannerQrActivity.this,
                        "No additional data");
            }
            scannerView.pause();
            scannerView.getBarcodeView().stopDecoding();
        } catch (Exception e) {
            setEnableButton(false);
            AppUtils.showFailedSnackBar(mainView, ScannerQrActivity.this,
                    e.getMessage());
        }
    }

    private final BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            String dataId = result.getText();
            scannerView.setVisibility(View.GONE);
            binding.tvResult.setText(dataId);
            binding.tvResult.setVisibility(View.VISIBLE);
            dataResult = dataId;
            beepManager.playBeepSoundAndVibrate();
            if (emvCoDecode) {
                scanEmvco(dataResult);
            } else {
                jsonResult = "";
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };
}
