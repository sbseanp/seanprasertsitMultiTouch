package edu.ucsb.cs.cs185.seanprasertsit.seanprasertsitmultitouch.app;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SilverWolf on 5/15/14.
 */
public class SettingsFrag extends DialogFragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        getDialog().setTitle(R.string.haha);
        return view;
    }
}
