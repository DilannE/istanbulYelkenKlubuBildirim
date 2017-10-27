package com.example.nigre.appiyk;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    TextView mTxtViewhaberBasligi;
    ProgressDialog progressDialog;
    Button mBtnHaberBasligi;
    Button mBtnTest;
    String ilkHaberBasligi;
    Database myDb;




    private class FetchTitle extends AsyncTask<Object, Object, String> {




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog= new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("BAŞLIK");
            progressDialog.setMessage("Başlık Çekiliyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Object... voids) {

            try {
                Document doc=Jsoup.connect("http://www.istanbulyelken.org.tr/").get();
                Elements element= doc.getElementsByClass("s_three_columns");
                Elements baslik=element.select("h4.mt16");


                int i=0;
                String baslikDizisi[]= new String[baslik.size()];

                if(baslik!=null){
                    for(Element b: baslik){
                        baslikDizisi[i++]=b.text();
                    }
                    ilkHaberBasligi=baslikDizisi[0];
                }



            } catch (IOException e) {
                e.printStackTrace();
            }


            return ilkHaberBasligi;
        }


        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            mTxtViewhaberBasligi = (TextView) findViewById(R.id.txt_haber_basligi);
            mTxtViewhaberBasligi.setText(ilkHaberBasligi);
            progressDialog.dismiss();
        }
    }

    private class FetchLink extends AsyncTask<Object, Object, String>{
        String ilkLinkBasligi;
        @Override
        protected String doInBackground(Object... objects) {

            try {
               Document doc = Jsoup.connect("http://www.istanbulyelken.org.tr/").get();
                Elements element= doc.getElementsByClass("s_three_columns");
                Elements link=element.select("a[href]");
                int j=0;
                String linkDizisi[]=new String[link.size()];

                if(link!=null){
                    for(Element l:link){
                        linkDizisi[j++]=l.attr("abs:href");
                    }
                    ilkLinkBasligi=linkDizisi[0];
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }




    public void Kiyasla(){
        Database d= new Database(MainActivity.this);

       if(d.ROW_TITLE== null){
           d.veriEkle(ilkHaberBasligi);
       }
        else if(d.ROW_TITLE!=null){

       }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       myDb=new Database(this);

        mBtnHaberBasligi=(Button)findViewById(R.id.btn_haber_basligi);
        mBtnHaberBasligi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new FetchTitle().execute();
                //new FetchLink().execute();


            }
        });


        mBtnTest=(Button)findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Database d = new Database(MainActivity.this);
                d.veriEkle(ilkHaberBasligi);

            }
        });


    }


}
