package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import com.example.bca_bos.networks.NetworkUtil;
import com.example.bca_bos.networks.VolleyClass;

public class cobaImage extends AppCompatActivity {

    public static cobaImage g_instance;
    ImageView g_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coba_image);

        g_instance = this;

        NetworkUtil.disableSSL();

        VolleyClass.getImage(getApplicationContext());

        g_image = findViewById(R.id.imageViewCoba);
    }

    public void settt(String p_string){
        byte[] decodedString = Base64.decode(p_string, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        g_image.setImageBitmap(decodedByte);
    }
}
