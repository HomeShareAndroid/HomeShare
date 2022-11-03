package com.example.homeshare.ui.roommates;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoommatesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RoommatesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Roommates fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}