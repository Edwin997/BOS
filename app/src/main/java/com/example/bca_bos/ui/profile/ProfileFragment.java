package com.example.bca_bos.ui.profile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.StartActivity;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Courier;
import com.example.bca_bos.models.Seller;
import com.example.bca_bos.models.locations.KotaKab;
import com.example.bca_bos.networks.VolleyClass;
import com.example.bca_bos.ui.produk.ChooseImageFromDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements OnCallBackListener, View.OnClickListener {

    private Context g_context;
    private Boolean IS_CHOOSE_JNE = false, IS_CHOOSE_TIKI = false, IS_CHOOSE_POS = false;
    View g_view;

    private ImageButton g_profile_ib_edit;
    private RoundedImageView g_profile_image;
    private TextView g_profile_nama_seller, g_profile_nama_toko, g_profile_rekening, g_profile_bosid,
            g_profile_phone, g_profile_kotkab, g_profile_courier;
    private Button g_profile_btn_change_password, g_profile_button_jne, g_profile_button_tiki,
            g_profile_button_pos, g_profile_btn_about, g_profile_btn_logout;

    //BottomSheet Edit
    private BottomSheetDialog g_bottomsheet_dialog_edit_profile, g_bottomsheet_dialog_change_password;
    private Button g_bottomsheet_simpan_profil, g_bottomsheet_simpan_password;
    private TextView g_bottomsheet_tv_nama_seller, g_bottomsheet_tv_error_password_lama, g_bottomsheet_tv_error_password_baru, g_bottomsheet_tv_error_konfirmasi_password_baru, g_bottomsheet_tv_error_nama_toko, g_bottomsheet_tv_error_kota;
    private EditText g_bottomsheet_et_nama_toko, g_bottomsheet_et_password_lama, g_bottomsheet_et_password_baru, g_bottomsheet_et_konfirmasi_password;
    private AutoCompleteTextView g_bottomsheet_actv_kota_asal;
    private ArrayAdapter<String> g_autocompleteadapter;
    public static List<String> g_city_name_list = new ArrayList<>();
    public List<KotaKab> g_city_list;
    private String g_asal_id_city;
    private ImageView g_bottomsheet_iv_profile;
    private ChooseImageFromDialog g_choose_dialog;
    private Bitmap g_bmp_bottom_sheet_edit_profile;
    private Uri pathh;

    //Popup Logout
    private Dialog g_profile_logout_popup;
    private Button g_profile_btn_logout_ya, g_profile_btn_logout_tidak;

    public static ProfileFragment g_instance;

    //Shared Preference
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String BOS_ID = "BOS_ID";
    private static final String SELLER_ID = "SELLER_ID";
    private static final String NAMA_TOKO = "NAMA_TOKO";
    SharedPreferences g_preference;
    int g_seller_id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Get Seller ID
        g_preference = this.getActivity().getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        g_seller_id = g_preference.getInt(SELLER_ID, -1);

        //Inisialisasi
        g_instance = this;
        g_context = container.getContext();
        g_choose_dialog = new ChooseImageFromDialog(this);
        VolleyClass.getProfile(g_context, g_seller_id);
        VolleyClass.getCityProfile(g_context);
        g_bottomsheet_dialog_edit_profile = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        g_bottomsheet_dialog_change_password = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        g_view = inflater.inflate(R.layout.fragment_profile, container, false);


        g_profile_image = g_view.findViewById(R.id.profile_img_toko);
        g_profile_nama_seller = g_view.findViewById(R.id.profile_tv_nama_penjual);
        g_profile_nama_toko = g_view.findViewById(R.id.profile_tv_nama_toko);
        g_profile_rekening = g_view.findViewById(R.id.profile_tv_rekening_toko);
        g_profile_bosid = g_view.findViewById(R.id.profile_tv_bosid_toko);
        g_profile_phone = g_view.findViewById(R.id.profile_tv_phone_toko);
        g_profile_kotkab = g_view.findViewById(R.id.profile_tv_kotakab);
        g_profile_courier = g_view.findViewById(R.id.profile_tv_courier);

        //Edit Profile
        g_profile_ib_edit = g_view.findViewById(R.id.profile_edit_button);
        g_profile_ib_edit.setOnClickListener(this);

        //Change Password
        g_profile_btn_change_password = g_view.findViewById(R.id.profile_change_password_button);
        g_profile_btn_change_password.setOnClickListener(this);

        //Logout Button
        g_profile_btn_logout = g_view.findViewById(R.id.profile_logout_button);
        g_profile_btn_logout.setOnClickListener(this);

        //Logout Popup
        g_profile_logout_popup = new Dialog(g_context);

        //About Button
        g_profile_btn_about = g_view.findViewById(R.id.profile_about_button);
        g_profile_btn_about.setOnClickListener(this);

        return g_view;
    }

    @Override
    public void onClick(View p_view) {

        switch (p_view.getId()){
            case R.id.profile_edit_button:
                buttonSheetEditProfile();
                break;
            case R.id.profile_change_password_button:
                g_bottomsheet_dialog_change_password = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
                View l_bottomsheet_view_change_password = LayoutInflater.from(g_context).inflate(
                        R.layout.layout_bottom_sheet_change_password,
                        (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_change_password)
                );

                //Edit Text
                g_bottomsheet_et_password_baru = l_bottomsheet_view_change_password.findViewById(R.id.apps_profile_bottom_sheet_et_password_baru);
                g_bottomsheet_et_password_lama = l_bottomsheet_view_change_password.findViewById(R.id.apps_profile_bottom_sheet_et_password_lama);
                g_bottomsheet_et_konfirmasi_password = l_bottomsheet_view_change_password.findViewById(R.id.apps_profile_bottom_sheet_et_konfirmasi_password);

                //Error Text View
                g_bottomsheet_tv_error_password_lama = l_bottomsheet_view_change_password.findViewById(R.id.apps_bottom_sheet_tv_password_lama_error);
                g_bottomsheet_tv_error_password_baru = l_bottomsheet_view_change_password.findViewById(R.id.apps_bottom_sheet_tv_password_baru_error);
                g_bottomsheet_tv_error_konfirmasi_password_baru = l_bottomsheet_view_change_password.findViewById(R.id.apps_bottom_sheet_tv_konfirmasi_password_baru_error);

                //Button
                g_bottomsheet_simpan_password = l_bottomsheet_view_change_password.findViewById(R.id.apps_bottom_sheet_btn_simpan_password);
                g_bottomsheet_simpan_password.setOnClickListener(this);

                g_bottomsheet_dialog_change_password.setContentView(l_bottomsheet_view_change_password);
                g_bottomsheet_dialog_change_password.show();
                break;
            case R.id.profile_jne_button:
                IS_CHOOSE_JNE = !IS_CHOOSE_JNE;
                configChooseCourierButton(p_view, IS_CHOOSE_JNE);
                break;
            case R.id.profile_tiki_button:
                IS_CHOOSE_TIKI = !IS_CHOOSE_TIKI;
                configChooseCourierButton(p_view, IS_CHOOSE_TIKI);
                break;
            case R.id.profile_pos_button:
                IS_CHOOSE_POS = !IS_CHOOSE_POS;
                configChooseCourierButton(p_view, IS_CHOOSE_POS);
                break;
            case R.id.apps_bottom_sheet_btn_simpan_profil:
                if(g_bmp_bottom_sheet_edit_profile == null){
                    g_bmp_bottom_sheet_edit_profile = ((BitmapDrawable)g_bottomsheet_iv_profile.getDrawable()).getBitmap();
                }


                Seller tmp_seller = new Seller();
                KotaKab tmp_kotakab = new KotaKab();

                tmp_seller.setId_seller(g_seller_id);
                tmp_seller.setShop_name(g_bottomsheet_et_nama_toko.getText().toString());
                tmp_seller.setBase64StringImage(imageToString(g_bmp_bottom_sheet_edit_profile));

                List<Courier> listCourier = new ArrayList<>();
                Courier tmp_courier1 = new Courier();
                tmp_courier1.setId_courier(1);
                tmp_courier1.setIs_selected(courierIsSelected(IS_CHOOSE_JNE));
                listCourier.add(tmp_courier1);

                Courier tmp_courier2 = new Courier();
                tmp_courier2.setId_courier(2);
                tmp_courier2.setIs_selected(courierIsSelected(IS_CHOOSE_TIKI));
                listCourier.add(tmp_courier2);

                Courier tmp_courier3 = new Courier();
                tmp_courier3.setId_courier(3);
                tmp_courier3.setIs_selected(courierIsSelected(IS_CHOOSE_POS));
                listCourier.add(tmp_courier3);

                tmp_seller.setSelected_courier(listCourier);

                if (isEditProfileValid()){
                    //get city id
                    getAsalCityId(g_bottomsheet_actv_kota_asal);
                    tmp_kotakab.setId_kota_kab(Integer.parseInt(g_asal_id_city));
                    tmp_seller.setKota_kab(tmp_kotakab);;

                    try {
                        VolleyClass.updateProfile(g_context, tmp_seller);
                        g_bmp_bottom_sheet_edit_profile = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    g_bottomsheet_dialog_edit_profile.dismiss();
                }else {

                }
                break;
            case R.id.apps_bottom_sheet_btn_simpan_password:
                String l_password_lama = g_bottomsheet_et_password_lama.getText().toString();
                String l_password_baru = g_bottomsheet_et_password_baru.getText().toString();
                String l_konfirmasi_password = g_bottomsheet_et_konfirmasi_password.getText().toString();

                if (isSimpanPasswordValid()){
                    VolleyClass.changePassword(g_context, String.valueOf(g_seller_id), l_password_lama, l_password_baru, l_konfirmasi_password);
                    g_bottomsheet_dialog_change_password.dismiss();
                }else {

                }

                break;
            case R.id.profile_logout_button:
                g_profile_logout_popup.setContentView(R.layout.layout_popup_profile_logout);
                g_profile_logout_popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                g_profile_logout_popup.show();

                //Button
                g_profile_btn_logout_ya = g_profile_logout_popup.findViewById(R.id.btn_popup_profile_ya);
                g_profile_btn_logout_ya.setOnClickListener(this);
                g_profile_btn_logout_tidak = g_profile_logout_popup.findViewById(R.id.btn_popup_profile_tidak);
                g_profile_btn_logout_tidak.setOnClickListener(this);

                break;
            case  R.id.btn_popup_profile_ya:
                //Delete Shared Preference
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREF_LOGIN, MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();

                //Login Intent
                Intent loginIntent = new Intent(g_context, StartActivity.class);
                startActivity(loginIntent);
                getActivity().finish();
                g_profile_logout_popup.dismiss();
                break;
            case R.id.btn_popup_profile_tidak:
                g_profile_logout_popup.dismiss();
                break;
            case R.id.apps_bottom_sheet_iv_gambar_edit_profile:
                g_choose_dialog.showChooseDialogEditProfile(g_context);
                break;
            case R.id.profile_about_button:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
                break;
        }
    }

    private int courierIsSelected(Boolean tmp_choose){
        if (tmp_choose){
            return 1;
        }else {
            return 0;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void buttonSheetEditProfile() {
        g_bottomsheet_dialog_edit_profile = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        View l_bottomsheet_view_edit_profile = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_edit_profile,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_edit_profile)
        );

        VolleyClass.getProfile(g_context, g_seller_id);

        //Round Image View
        g_bottomsheet_iv_profile = l_bottomsheet_view_edit_profile.findViewById(R.id.apps_bottom_sheet_iv_gambar_edit_profile);
        g_bottomsheet_iv_profile.setOnClickListener(this);

        //TextView dan EditText
        g_bottomsheet_tv_nama_seller = l_bottomsheet_view_edit_profile.findViewById(R.id.apps_bottom_sheet_tv_nama_edit_profile);
        g_bottomsheet_tv_error_nama_toko = l_bottomsheet_view_edit_profile.findViewById(R.id.apps_bottom_sheet_tv_nama_toko_error);
        g_bottomsheet_tv_error_kota = l_bottomsheet_view_edit_profile.findViewById(R.id.apps_bottom_sheet_tv_kota_error);
        g_bottomsheet_et_nama_toko = l_bottomsheet_view_edit_profile.findViewById(R.id.apps_bottom_sheet_et_nama_toko_edit_profile);
        g_bottomsheet_actv_kota_asal = l_bottomsheet_view_edit_profile.findViewById(R.id.apps_bottom_sheet_actv_kota_asal_edit_profile);
        g_bottomsheet_actv_kota_asal.setAdapter(g_autocompleteadapter);
        g_bottomsheet_actv_kota_asal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                g_bottomsheet_actv_kota_asal.showDropDown();
                return false;
            }
        });

        //Ongkir Pilihan
        g_profile_button_jne = l_bottomsheet_view_edit_profile.findViewById(R.id.profile_jne_button);
        configChooseCourierButton(g_profile_button_jne, IS_CHOOSE_JNE);
        g_profile_button_jne.setOnClickListener(this);

        g_profile_button_tiki = l_bottomsheet_view_edit_profile.findViewById(R.id.profile_tiki_button);
        configChooseCourierButton(g_profile_button_tiki, IS_CHOOSE_TIKI);
        g_profile_button_tiki.setOnClickListener(this);

        g_profile_button_pos = l_bottomsheet_view_edit_profile.findViewById(R.id.profile_pos_button);
        configChooseCourierButton(g_profile_button_pos, IS_CHOOSE_POS);
        g_profile_button_pos.setOnClickListener(this);

        //Set Text
        g_bottomsheet_tv_nama_seller.setText(g_profile_nama_seller.getText());
        g_bottomsheet_et_nama_toko.setText(g_profile_nama_toko.getText());
        g_bottomsheet_actv_kota_asal.setText(g_profile_kotkab.getText());

        //Simpan Profil
        g_bottomsheet_simpan_profil = l_bottomsheet_view_edit_profile.findViewById(R.id.apps_bottom_sheet_btn_simpan_profil);
        g_bottomsheet_simpan_profil.setOnClickListener(this);

        g_bottomsheet_dialog_edit_profile.setContentView(l_bottomsheet_view_edit_profile);
        g_bottomsheet_dialog_edit_profile.show();

    }

    private Boolean isSimpanPasswordValid(){
        g_bottomsheet_tv_error_password_lama.setText("");
        g_bottomsheet_tv_error_password_baru.setText("");
        g_bottomsheet_tv_error_konfirmasi_password_baru.setText("");

        String l_password_lama = g_bottomsheet_et_password_lama.getText().toString();
        String l_password_baru = g_bottomsheet_et_password_baru.getText().toString();
        String l_konfirmasi_password_baru = g_bottomsheet_et_konfirmasi_password.getText().toString();

        Boolean b_password_lama, b_password_baru, b_konfirmasi_password_baru;

        if (l_password_lama.isEmpty()){
            g_bottomsheet_tv_error_password_lama.setText("Mohon diisi terlebih dahulu");
            b_password_lama = false;
        }else if (l_password_lama.length() < 6){
            g_bottomsheet_tv_error_password_lama.setText("Password harus 6 digit");
            b_password_lama = false;
        }
        else {
            b_password_lama = true;
        }

        if (l_password_baru.isEmpty()){
            g_bottomsheet_tv_error_password_baru.setText("Mohon diisi terlebih dahulu");
            b_password_baru = false;
        }else if (l_password_baru.length() < 6){
            g_bottomsheet_tv_error_password_baru.setText("Password harus 6 digit");
            b_password_baru = false;
        }else if (l_password_baru.equals(l_password_lama)){
            g_bottomsheet_tv_error_password_baru.setText("Password baru harus berbeda dengan password lama");
            b_password_baru = false;
        }else {
            b_password_baru = true;
        }

        if (l_konfirmasi_password_baru.isEmpty()){
            g_bottomsheet_tv_error_konfirmasi_password_baru.setText("Mohon diisi terlebih dahulu");
            b_konfirmasi_password_baru = false;
        }else if (l_konfirmasi_password_baru.length() < 6){
            g_bottomsheet_tv_error_konfirmasi_password_baru.setText("Password harus 6 digit");
            b_konfirmasi_password_baru = false;
        }else if (!l_password_baru.equals(l_konfirmasi_password_baru)){
            g_bottomsheet_tv_error_konfirmasi_password_baru.setText("Konfirmasi password tidak cocok");
            b_konfirmasi_password_baru = false;
        }else {
            b_konfirmasi_password_baru = true;
        }



        if (b_password_lama.equals(true) && b_password_baru.equals(true) && b_konfirmasi_password_baru.equals(true)){
            return true;
        }else return false;
    }

    private Boolean isEditProfileValid(){
        g_bottomsheet_tv_error_kota.setText("");
        g_bottomsheet_tv_error_nama_toko.setText("");

        String l_nama_toko = g_bottomsheet_et_nama_toko.getText().toString();
        String l_kota = g_bottomsheet_actv_kota_asal.getText().toString();

        Boolean b_nama_toko = false, b_kota = false;

        if (l_nama_toko.isEmpty()){
            g_bottomsheet_tv_error_nama_toko.setText("Mohon diisi terlebih dahulu");
            b_nama_toko = false;
        }else {
            b_nama_toko = true;
        }

        if (l_kota.isEmpty()){
            g_bottomsheet_tv_error_kota.setText("Mohon diisi terlebih dahulu");
            b_kota = false;
        }else {
            b_kota = true;
        }

        if(!g_city_name_list.contains(l_kota)){
            g_bottomsheet_tv_error_kota.setText("Mohon diisi sesuai dropdown");
            b_kota = false;
        }else {
            b_kota = true;
        }

        if (b_nama_toko.equals(true) && b_kota.equals(true)){
            return true;
        }else return false;
    }

    //Mendapatkan ID dari City
    private void getAsalCityId(AutoCompleteTextView p_autocomplete){
        int l_position = g_city_name_list.indexOf(p_autocomplete.getText().toString());
        g_asal_id_city = String.valueOf(g_city_list.get(l_position).getId_kota_kab());
    }

    public void getCity(List<KotaKab> p_kotakab){
        g_city_name_list.clear();
        g_city_list = p_kotakab;
        String l_city_name;

        for (int i = 0; i < p_kotakab.size(); i++){
            l_city_name = p_kotakab.get(i).getKota_kab_name();

            g_city_name_list.add(l_city_name);
        }

        g_autocompleteadapter = new ArrayAdapter<>(g_context, android.R.layout.simple_list_item_1, g_city_name_list);
        g_autocompleteadapter.notifyDataSetChanged();
    }

    private void configChooseCourierButton(View p_view, boolean p_choose){
        if (p_choose){
            p_view.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_blue);
        }else {
            p_view.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
        }
    }

    public void refreshLayout(Seller p_seller){
        if(p_seller.getBase64StringImage().isEmpty()){
            g_profile_image.setImageResource(R.drawable.ic_bos_mascot_default_profile);
        }
        else
        {
            g_profile_image.setImageBitmap(Method.convertToBitmap(p_seller.getBase64StringImage()));
        }
        g_profile_image.setImageBitmap(Method.convertToBitmap(p_seller.getBase64StringImage()));
        g_profile_nama_seller.setText(p_seller.getName());
        g_profile_nama_toko.setText(p_seller.getShop_name());
        g_profile_rekening.setText(p_seller.getCard_number());
        g_profile_bosid.setText(p_seller.getUsername());
        g_profile_phone.setText(p_seller.getPhone());
        g_profile_kotkab.setText(p_seller.getKota_kab().getKota_kab_name());

        String l_selected_courier = "";
        if (p_seller.getSelected_courier().get(0).getIs_selected() == 1){
            l_selected_courier = l_selected_courier + p_seller.getSelected_courier().get(0).getCourier_name() + "\n";
            IS_CHOOSE_JNE = true;
        }
        else{
            IS_CHOOSE_JNE = false;
        }

        if (p_seller.getSelected_courier().get(1).getIs_selected() == 1){
            l_selected_courier = l_selected_courier + p_seller.getSelected_courier().get(1).getCourier_name() + "\n";
            IS_CHOOSE_TIKI = true;
        }
        else{
            IS_CHOOSE_TIKI = false;
        }

        if (p_seller.getSelected_courier().get(2).getIs_selected() == 1){
            l_selected_courier = l_selected_courier + p_seller.getSelected_courier().get(2).getCourier_name();
            IS_CHOOSE_POS = true;
        }
        else{
            IS_CHOOSE_POS = false;
        }

        g_profile_courier.setText(l_selected_courier);
    }

    public void refreshLayoutAfterPut(){
        VolleyClass.getProfile(g_context, g_seller_id);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null){
            if(requestCode == ChooseImageFromDialog.CODE_GALLERY_EDIT_PROFILE){
                pathh = data.getData();

                try {
                    g_bmp_bottom_sheet_edit_profile = MediaStore.Images.Media.getBitmap(g_context.getContentResolver(), pathh);
                    g_bottomsheet_iv_profile.setImageBitmap(g_bmp_bottom_sheet_edit_profile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(requestCode == ChooseImageFromDialog.CODE_CAMERA_EDIT_PROFILE){
                try {
                    Bundle extras = data.getExtras();
                    g_bmp_bottom_sheet_edit_profile = (Bitmap) extras.get("data");
                    g_bottomsheet_iv_profile.setImageBitmap(g_bmp_bottom_sheet_edit_profile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof Integer){
            int tmpCode = (int)p_obj;
            if(tmpCode == ChooseImageFromDialog.CODE_CAMERA_EDIT_PROFILE){
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, ChooseImageFromDialog.CODE_CAMERA_EDIT_PROFILE);
            }else if(tmpCode == ChooseImageFromDialog.CODE_GALLERY_EDIT_PROFILE){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, ChooseImageFromDialog.CODE_GALLERY_EDIT_PROFILE);
            }
        }
    }

    public void updateStringSharedPreference(String p_key, String p_value) {
        //Save Shared Preference
        SharedPreferences.Editor l_editor = this.getActivity().getSharedPreferences(PREF_LOGIN, MODE_PRIVATE).edit();
        l_editor.putString(p_key, p_value);
        l_editor.commit();
    }

    public String imageToString(Bitmap bitmap){
        ByteArrayOutputStream bost = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 ,bost);
        byte[] img = bost.toByteArray();
        String temp = Base64.encodeToString(img, Base64.NO_WRAP);
        return temp;
    }


}