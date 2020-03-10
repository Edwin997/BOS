package com.example.bca_bos;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ApplicationContainer extends AppCompatActivity  {

    public static final String KEY_OPEN_APPS = "open_apps";

    public static final int ID_BERANDA = 0;
    public static final int ID_TEMPLATE = 1;
    public static final int ID_PRODUK = 2;
    public static final int ID_TRANSAKSI = 3;
    public static final int ID_PROFILE = 4;

    private BottomNavigationView g_navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_application_container);

        disableSertifikatSSL();

        int tmpTypeOpen = ID_TEMPLATE;
        if(getIntent().hasExtra(KEY_OPEN_APPS)){
            tmpTypeOpen = getIntent().getExtras().getInt(KEY_OPEN_APPS);
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_beranda, R.id.navigation_template, R.id.navigation_produk,
                R.id.navigation_transaksi, R.id.navigation_profile)
                .build();
        g_navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(g_navView, navController);

        switch (tmpTypeOpen){
            case ID_BERANDA:
                navController.navigate(R.id.navigation_beranda);
                break;
            case ID_TEMPLATE:
                navController.navigate(R.id.navigation_template);
                break;
            case ID_PRODUK:
                navController.navigate(R.id.navigation_produk);
                break;
            case ID_TRANSAKSI:
                navController.navigate(R.id.navigation_transaksi);
                break;
            case ID_PROFILE:
                navController.navigate(R.id.navigation_profile);
                break;
            default:
                navController.navigate(R.id.navigation_beranda);
        }
    }

    public static void disableSertifikatSSL() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }
}
