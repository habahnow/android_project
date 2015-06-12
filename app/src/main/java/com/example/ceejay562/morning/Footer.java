package com.example.ceejay562.morning;

import android.app.Activity;
import android.widget.ArrayAdapter;

/**
 * Created by ceejay562 on 6/10/2015.
 */
public class Footer extends ArrayAdapter<String> {
    private Activity context;
    private int imageId;

    public Footer(Activity context, int imageId){
        super(context,R.layout.footer, imageId );
        this.imageId = imageId;
    }


}
