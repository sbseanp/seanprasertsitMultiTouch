package edu.ucsb.cs.cs185.seanprasertsit.seanprasertsitmultitouch.app;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;


public class MultiTouch extends ActionBarActivity {



    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_touch);
        String root = Environment.getExternalStorageDirectory().toString();
        File imgFile = new  File(root + "/ucsbmap.png");
        if(imgFile.exists()){

            //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            Touchview myImage = (Touchview) findViewById(R.id.touchview);
            Bitmap bit = myImage.createBitmap(imgFile);
            myImage.setImageBitmap(bit);
        }
    }

    // Inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.multi_touch, menu);
        return true;
    }

    // Action bar actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            showSettingsDialog();
            return true;
        }
        if (id == R.id.picture) {
            Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
            return true;
        }
        if (id == R.id.help) {
            showHelpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Help popup
    public void showHelpDialog() {
        DialogFragment dialog = new HelpFrag();
        dialog.show(getFragmentManager(), "HelpDialog");
    }

    // Settings popup
    public void showSettingsDialog() {
        DialogFragment dialog = new SettingsFrag();
        dialog.show(getFragmentManager(), "SettingsDialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            File ftemp = new File(picturePath);
            //Touchview Touchview = (Touchview) findViewById(R.id.touchview);
            //Touchview.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            Touchview Touchview = (Touchview) findViewById(R.id.touchview);
            Bitmap bit = Touchview.createBitmap(ftemp);
            Touchview.setImageBitmap(bit);

        }


    }

}
