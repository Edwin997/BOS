package com.example.bca_bos.ui.produk;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddProdukActivity extends AppCompatActivity {

    ImageView g_apps_produk_image;
    TextInputLayout g_apps_produk_nama, g_apps_produk_harga, g_apps_produk_stok;
    ImageButton g_apps_from_camera, g_apps_from_gallery;
    Button g_apps_simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produk);
        Method.callback(this);

        initLayout();

    }

    public void initLayout(){
        g_apps_produk_nama = findViewById(R.id.apps_produk_add_nama_et);
        g_apps_produk_harga = findViewById(R.id.apps_produk_add_harga_et);
        g_apps_produk_stok = findViewById(R.id.apps_produk_add_stok_et);
        g_apps_from_camera = findViewById(R.id.apps_produk_add_camera_btn);
        g_apps_from_gallery = findViewById(R.id.apps_produk_add_gallery_btn);
        g_apps_produk_image = findViewById(R.id.apps_produk_add_iv);
        g_apps_simpan = findViewById(R.id.apps_produk_add_simpan_btn);
    }

//    @Override
//    public void onClick(View v) {
//        if(v == g_apps_simpan){
//            uploadImage();
//
//        }else if(v == g_apps_from_gallery){
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(intent,gallery_code);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == gallery_code && resultCode == RESULT_OK && data != null){
//            Uri path = data.getData();
//
//            try {
//                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//                g_apps_produk_image.setImageBitmap(bmp);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void uploadImage(){
//        RequestQueue rq = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                "http://192.168.43.228:80/server/server.php",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            String res = obj.getString("response");
//                            Toast.makeText(AddProdukActivity.this, res, Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("name", "coba");
//                params.put("image", imageToString(bmp));
//                return params;
//            }
//        };
//
//        rq.add(stringRequest);
//    }
//
//    public String imageToString(Bitmap bitmap){
//        ByteArrayOutputStream bost = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 ,bost);
//        byte[] img = bost.toByteArray();
//        return Base64.encodeToString(img, Base64.DEFAULT);
//    }
}
