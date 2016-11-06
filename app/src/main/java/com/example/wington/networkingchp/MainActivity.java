package com.example.wington.networkingchp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private static final String IMG_URL = "http://static5.gamespot.com/uploads/original/1557/15576725/3153546-cwdfniaukaaxebz.jpg";
    private ImageView iBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iBitmap = (ImageView) findViewById(R.id.iBitmap);
        new ImageDownloadTask().execute(IMG_URL);
   }

    private class ImageDownloadTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            //Convert String to URL
            URL url = getUrlFromString(params[0]);

            //get input stream
            InputStream in = getInputStream(url);

            Bitmap bm = decodeBitMap(in);

            return bm;

        }

        private URL getUrlFromString(String imgUrl) {
            URL url;
            try {
                url = new URL(imgUrl);
            } catch (MalformedURLException e) {
                url = null;
            }
            return url;

        }

        private InputStream getInputStream(URL url) {
            InputStream in;
            URLConnection conn;
            try {
                conn = url.openConnection();
                conn.connect();
                in = conn.getInputStream();

            } catch (IOException e) {
                in = null;
            }
            return in;
        }

        private Bitmap decodeBitMap(InputStream in) {
            Bitmap bm;
            try{
                bm = BitmapFactory.decodeStream(in);
                in.close();

            }catch(IOException e){
                in = null;
                bm = null;
            }
            return bm;

        }

        //Do not access the View in the onbackground method
        //Because that happens outside of the UIThread
        //OnPostExecute has access to the UIs
        @Override
        protected void onPostExecute(Bitmap result) {
            iBitmap.setImageBitmap(result);

        }
    }

}
