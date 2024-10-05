package com.example.morgue_module;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class BodiesFragment extends Fragment {

    private ImageView BodyPicture;
    private Uri photoUri;
    public Context context;

    private final int CAMERA_REQ_CODE = 100;
    private final int GALLERY_REQ_CODE = 200;


    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImageUri);
                            BodyPicture.setImageBitmap(bitmap);  // Display selected image
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    } else if (photoUri != null) {  // Handle camera capture
                        BodyPicture.setImageURI(photoUri);  // Display captured image
                        Toast.makeText(context, "Image Captured", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(context, "Image Does not Got", Toast.LENGTH_SHORT).show();
                }
            });

    private void showImagePickerDialog() {
        // Inflate the custom layout
        View view = LayoutInflater.from(getContext()).inflate(R.layout.uploadorclick_layout, null); // Inflate your custom layout

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();


        // Get references to the buttons inside the custom layout
        LinearLayout btnClick = view.findViewById(R.id.btnClick);
        LinearLayout btnUpload = view.findViewById(R.id.btnUpload);

        // Create and show the dialog

        // Set onClick listeners for both buttons
        btnClick.setOnClickListener(v -> {
            // Handle camera capture button click

            Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(iCamera,CAMERA_REQ_CODE);
            bottomSheetDialog.dismiss();
            // Add your code to open the camera here
        });

        btnUpload.setOnClickListener(v -> {
            // Handle upload button click
            Intent iPick = new Intent(Intent.ACTION_PICK);
            iPick.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iPick,GALLERY_REQ_CODE);
            bottomSheetDialog.dismiss();// Add your code to open the gallery picker here
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bodies, container, false);
        BodyPicture = view.findViewById(R.id.BodyPicture);
        Button selectImageButton = view.findViewById(R.id.select_image_button);
        context = getContext();

        selectImageButton.setOnClickListener(v -> {
            if(checkPermissions())
            {
                showImagePickerDialog();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode  == RESULT_OK)
        {
            if(requestCode == CAMERA_REQ_CODE)
            {
                assert data != null;
                Bitmap img = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                BodyPicture.setImageBitmap(img);

            }
            else if(requestCode == GALLERY_REQ_CODE)
            {
                assert data != null;
                BodyPicture.setImageURI(data.getData());
            }
        }
    }

    // Check necessary permissions for camera and storage access
    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            return false;
        }
        return true;
    }

}
