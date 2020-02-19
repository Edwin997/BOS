package com.example.bca_bos.ui.template;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TemplateViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TemplateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Template fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}