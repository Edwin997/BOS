package com.example.bca_bos.ui.produk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProdukViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProdukViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Produk fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}