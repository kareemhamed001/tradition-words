package com.barmej.culturalwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.barmej.culturalwords.R;

import org.w3c.dom.Text;

public class ShareActivity extends AppCompatActivity {
    private String questionText;
    private EditText editText;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        imageView=findViewById(R.id.image_view_question);
        int extras=getIntent().getIntExtra("qustion text extra",0);
        imageView.setImageResource(extras);
        questionText=getIntent().getStringExtra("qustion text extra");
        editText=findViewById(R.id.edit_text_share_title);
        SharedPreferences sharedPreferences =getSharedPreferences("app_pref",MODE_PRIVATE);
        String questionTitle=sharedPreferences.getString("share_title","");
        editText.setText(questionTitle);

    }
    public void shareQuestion(View view){
        String questionTitle=editText.getText().toString();
        Intent shareintent=new Intent();
        shareintent.setAction(Intent.ACTION_SEND);
        shareintent.putExtra(Intent.EXTRA_TEXT,questionTitle+"\n"+questionText);
        shareintent.setType("text/plain");
        startActivity(shareintent);

        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("share_title", questionTitle);
        editor.apply();


    }
}