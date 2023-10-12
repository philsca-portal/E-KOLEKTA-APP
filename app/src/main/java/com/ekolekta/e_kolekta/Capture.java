package com.ekolekta.e_kolekta;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.e_kalat.R;
import com.example.e_kalat.ml.ModelUnquant;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Capture extends AppCompatActivity {

    private static final int requestcamera_code = 12;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION= 1;
    private static final int CAMERA_PERM_CODE = 101;
    ImageView capture;
    int imageSize = 224;
    String currentPhotoPath;
    Button sendbtn;
    ProgressBar progressBar;
    TextView loc,datee,link, result;
    Spinner tagg,typee;
    private FusedLocationProviderClient mFusedLocationClient;
    DatabaseReference reference,reference1,reference2,reference3,reference4,reference5,reference6;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    Data data;
    Points points;
    long maxId = 0;
    long maxId1 = 0;
    long maxId2 = 0;
    long maxId3 = 0;
    long maxId4 = 0;
    String text = "DATA";
    String text1 ="ALL DATA";
    String urllink = "https://live.ekolekta-app.website/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        sendbtn = findViewById(R.id.sendBtn);
        progressBar = findViewById(R.id.progress_capture);
        progressBar.setVisibility(View.INVISIBLE);
        capture = findViewById(R.id.captureImage);
        data = new Data();
        points = new Points();

        result = findViewById(R.id.result);
        datee = findViewById(R.id.date);
        link = findViewById(R.id.link);
        loc = findViewById(R.id.location);
        tagg = findViewById(R.id.spinner);
        typee = findViewById(R.id.spinner1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(text);
        reference1 = FirebaseDatabase.getInstance().getReference().child(text1);
        reference2 = FirebaseDatabase.getInstance().getReference();
        reference3 = FirebaseDatabase.getInstance().getReference().child("Biodegradable");
        reference4 = FirebaseDatabase.getInstance().getReference().child("Non-Biodegradable");
        reference5 = FirebaseDatabase.getInstance().getReference().child("Recyclable");
        reference6 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxId = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxId1 = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxId2 = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxId3 = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxId4 = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = spinner.getSelectedItem().toString();
                String type = spinner1.getSelectedItem().toString();
                String location = loc.getText().toString();
                String value = result.getText().toString();
                String bottle = "Bottle";
                String cardboard = "CardBoard";
                String chalkboarderaser = "Chalkboard Eraser";
                String cigarettebutt = "Cigarette Butt";
                String facemask = "Facemask";
                String fireextinguisher = "Fire Extinguisher";
                String garbagepile = "Garbage Pile";
                String human = "Human";
                String id = "ID";
                String keyboard = "Keyboard";
                String leaf = "Leaf";
                String monitor = "Monitor";
                String mouse = "Mouse";
                String paper = "Paper";
                String papercup = "Paper Cup";
                String paperpackage = "Paper Package";
                String plasticchair = "Plastic Chair";
                String plasticcup = "Plastic Cup";
                String plasticmold = "Plastic Mold";
                String plasticstraw = "Plastic Straw";
                String plasticutensils = "Plastic Utensils";
                String printer = "Printer";
                String stick = "Stick";
                String styrofoam = "Styrofoam";
                String wallswitch = "Wall Switch";
                String watch = "Watch";
                String wrapper = "Wrapper";
                String paperplate = "Paper Plate";
                String plastic = "Plastic";

                if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(type) && !TextUtils.isEmpty(location)) {
                    if(value != bottle && value != cigarettebutt && value != facemask && value != garbagepile && value != leaf &&
                            value != paper && value != papercup && value != paperpackage && value != plasticcup && value != plasticstraw &&
                            value != plasticutensils && value != stick && value != facemask && value != styrofoam && value != wrapper &&
                            value != paperplate && value != plastic) {
                        Toast.makeText(Capture.this, "This is not a litter..", Toast.LENGTH_SHORT).show();
                    }else{
                        progressBar.setVisibility(View.VISIBLE);
                        File f = new File(currentPhotoPath);
                        capture.setImageURI(Uri.fromFile(f));
                        Log.d("tag", "Absolute Url of Image is " + Uri.fromFile(f));
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri contentUri = Uri.fromFile(f);
                        mediaScanIntent.setData(contentUri);
                        Capture.this.sendBroadcast(mediaScanIntent);

                        uploadImageToFirebase(f.getName(), contentUri);

                        data.setTag(tag);
                        data.setType(type);
                        data.setLoc(location);

                    }

                } else {
                    Toast.makeText(Capture.this, "FIll all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
                fetchLocation();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Capture.this,R.layout.my_selected_item,
                getResources().getStringArray(R.array.tag));
        adapter.setDropDownViewResource(R.layout.my_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Capture.this,R.layout.my_selected_item,
                getResources().getStringArray(R.array.type));
        adapter1.setDropDownViewResource(R.layout.my_dropdown_item);
        spinner1.setAdapter(adapter1);
    }

    private void askCameraPermission() {

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else{
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else{
                Toast.makeText(this, "Camera permission is required to use the Camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchLocation() {
        if(ContextCompat.checkSelfPermission(Capture.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(Capture.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){

                new AlertDialog.Builder(this).setTitle("Required Location Permission")
                        .setMessage("You have to give this permission to access the feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(Capture.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();

            }else{
                ActivityCompat.requestPermissions(Capture.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }else{
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object

                                try {
                                    Geocoder geocoder = new Geocoder(Capture.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
                                    String address = addresses.get(0).getAddressLine(0);

                                    loc.setText(address);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
    }


    private void uploadImageToFirebase(String name, Uri contentUri) {
        StorageReference image = storageReference.child("images/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("tag", "onSuccess: Uploaded Image URL is " + uri.toString());
                        String url = uri.toString();
                        data.setImage(url);
                        link.setText(url);

                        String date = String.valueOf(DateFormat.format("MMM dd, yyyy h:mm a", new Date()));
                        data.setDate(date);
                        datee.setText(date);

                        reference2.child("ALL DATA").child(String.valueOf((maxId1 + 1))).setValue(data);
                        reference.child(String.valueOf(maxId + 1)).setValue(data);
                        reference6.child("points").setValue(String.valueOf(maxId + 1));

                        String tYpe = typee.getSelectedItem().toString();

                        switch (tYpe) {
                            case "Biodegradable":
                                reference3.child(String.valueOf((maxId2 + 1))).setValue(data);
                                break;
                            case "Non-Biodegradable":
                                reference4.child(String.valueOf((maxId3 + 1))).setValue(data);
                                break;
                            case "Recyclable":
                                reference5.child(String.valueOf((maxId4 + 1))).setValue(data);
                                break;
                        }

                        process();
                        Intent intent = new Intent(Capture.this,Dashboard.class);
                        startActivity(intent);
                        finish();
                    }

                    private void process() {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(urllink)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        myapi api = retrofit.create(myapi.class);

                        Call<Data> call = api.adddata(tagg.getSelectedItem().toString(), typee.getSelectedItem().toString(),
                                datee.getText().toString(),link.getText().toString(),loc.getText().toString());

                        call.enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Data> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Capture.this, "Data sent successfully!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Capture.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager()) != null){

            File photoFile = null;
            try{
                photoFile = createImageFile();
            }catch (IOException ex){

            }

            if(photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.ekolekta.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent,requestcamera_code);
            }
        }
    }
    public void classifyImage(Bitmap image){
        try {
            ModelUnquant model = ModelUnquant.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int [] values = new int [imageSize * imageSize];
            image.getPixels(values, 0, image.getWidth(), 0, 0, image.getWidth(),image.getHeight());
            int pixel = 0;
            for(int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = values[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val >> 0xFF)* (1.f / 255.f));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            ModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for(int i = 0; i < confidences.length; i++){
                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String [] classes = {"Bottle","CardBoard","Chalkboard Eraser","Cigarette Butt","Facemask",
            "Fire Extinguisher","Garbage Pile","Human","ID","Keyboard","Leaf","Monitor","Mouse","Paper",
            "Paper Cup","Paper Package","Plastic Chair","Plastic Cup","Plastic Mold","Plastic Straw",
            "Plastic Utensils","Printer","Stick","Styrofoam","Wall Switch","Watch","Wrapper","Paper Plate",
            "Plastic"};
            result.setText(classes[maxPos]);
            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            File f = new File (currentPhotoPath);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap image = BitmapFactory.decodeFile(f.getAbsolutePath(),bmOptions);
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image,dimension,dimension);
            capture.setImageBitmap(image);

            image = Bitmap.createScaledBitmap(image, imageSize,imageSize, false);
            classifyImage(image);

        super.onActivityResult(requestCode, resultCode, data);
    }
}