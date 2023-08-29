package com.example.adminapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadImageActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription, editTextCategory;
    private ImageView imageViewPreview;
    private DatabaseReference itemsRef;
    private Uri selectedImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextCategory = findViewById(R.id.editTextCategory);
        imageViewPreview = findViewById(R.id.imageViewPreview);

        itemsRef = FirebaseDatabase.getInstance().getReference("items");

        Button buttonSelectImage = findViewById(R.id.buttonSelectImage);
        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        Button buttonAddItem = findViewById(R.id.buttonAddItem);
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }

        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imageViewPreview.setImageURI(selectedImageUri);
        }
    }

    private void addItem() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String category = editTextCategory.getText().toString().trim();

        if (!title.isEmpty() && !description.isEmpty() && selectedImageUri != null) {
            // Generate a unique key for the item
            String itemId = itemsRef.push().getKey();

            // Upload image to Firebase Storage
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference imageRef = storageRef.child("images/" + itemId);
            UploadTask uploadTask = imageRef.putFile(selectedImageUri);

            // Create an Item object with image URL
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    Item item = new Item(itemId, title, description, category, imageUrl);
                    itemsRef.child(itemId).setValue(item);
                    clearFields();
                });

            });
        }
    }

    private void clearFields() {
        editTextTitle.setText("");
        editTextDescription.setText("");
        imageViewPreview.setImageDrawable(null);
        selectedImageUri = null;
    }
}
