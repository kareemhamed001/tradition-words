package com.barmej.culturalwords;

import static com.barmej.culturalwords.R.drawable.icon_1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int[] images={R.drawable.icon_1,R.drawable.icon_2,R.drawable.icon_3,R.drawable.icon_4,R.drawable.icon_5,R.drawable.icon_6,R.drawable.icon_7,R.drawable.icon_8,R.drawable.icon_9,R.drawable.icon_10,R.drawable.icon_11,R.drawable.icon_12,R.drawable.icon_13};
    private String[]Answers;
    private  String[]Answer_Descriptions;
    private String mAnswers,mAnswersDescription;
    private ImageButton ShareButton;
    private Random random;
    private ImageView imageView;
    private int mImages;
    int index=0;


    

    private static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Answers=getResources().getStringArray(R.array.answers);
        Answer_Descriptions=getResources().getStringArray(R.array.answer_description);
        ShareButton=findViewById(R.id.button_share_question);
        imageView=findViewById(R.id.image_view_question);
        showImage();


    }

    public void onclicknext(View view)  {
        if (index <images.length){
            showImage();
        }else {
          index=0;
          showImage();
        }
    }
    public void showImage(){

        Drawable image=ContextCompat.getDrawable(this,images[index]);
        imageView.setImageDrawable(image);
        mImages=images[index];
        index++;
    }
    public void onclickAnswerButton(View view){
        Intent intent=new Intent(MainActivity.this,AnswerActivity.class);
        intent.putExtra("answer_key",Answers[index-1]);
        startActivity(intent);


    }
    /**
     * يجب عليك كتابة الكود الذي يقوم بمشاركة الصورة في هذه الدالة
     */
    public void shareImage() {
        // كود مشاركة الصورة هنا
        Intent intent=new Intent(MainActivity.this, ShareActivity.class);
        intent.putExtra("key",  mImages);
        startActivity(intent);
    }
    public void shareView(View view) {
        // كود مشاركة الصورة هنا
        Intent intent=new Intent(MainActivity.this, ShareActivity.class);
        intent.putExtra("qustion text extra",  mImages);
        startActivity(intent);
    }

    /**
     *  هذه الدالة تقوم بطلب صلاحية الكتابة على ال external storage حتى يمكن حفظ ملف الصورة
     * <p>
     * وفي حال الحصول على الصلاحية تقوم باستدعاء دالة shareImage لمشاركة الصورة
     **/
    private void checkPermissionAndShare() {
        // insertImage في النسخ من آندرويد 6 إلى آندرويد 9 يجب طلب الصلاحية لكي نتمكن من استخدام الدالة
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // هنا لا يوجد صلاحية للتطبيق ويجب علينا طلب الصلاحية منه عن طريك الكود التالي
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // بسبب عدم منح المستخدم الصلاحية للتطبيق فمن الأفضل شرح فائدتها له عن طريق عرض رسالة تشرح ذلك
                // هنا نقوم بإنشاء AlertDialog لعرض رسالة تشرح للمستخدم فائدة منح الصلاحية
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.permission_title)
                        .setMessage(R.string.permission_explanation)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // requestPermissions عند الضغط على زر منح نقوم بطلب الصلاحية عن طريق الدالة
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //  عند الضغط على زر منع نقوم بإخفاء الرسالة وكأن شيء لم يكن
                                dialogInterface.dismiss();
                            }
                        }).create();

                // نقوم بإظهار الرسالة بعد إنشاء alertDialog
                alertDialog.show();

            } else {
                // لا داعي لشرح فائدة الصلاحية للمستخدم ويمكننا طلب الصلاحية منه
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }

        } else {
            // الصلاحية ممنوحه مسبقا لذلك يمكننا مشاركة الصورة
            shareImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // يتم استدعاء هذه الدالة بعد اختيار المستخدم احد الخيارين من رسالة طلب الصلاحية
        if (requestCode == PERMISSIONS_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // تم منح الصلاحية من قبل المستخدم لذلك يمكننا مشاركة الصورة الآن
                shareImage();
            } else {
                // لم يتم منح الصلاحية من المستخدم لذلك لن نقوم بمشاركة الصورة، طبعا يمكننا تنبيه المستخدم بأنه لن يتم مشاركة الصورة لسبب عدم منح الصلاحية للتطبيق
                Toast.makeText(MainActivity.this, R.string.permission_explanation, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    public void showLanguageDialoge(View view){
        AlertDialog alertDialog=new AlertDialog.Builder(this).setTitle(R.string.menuName).setItems(R.array.languages, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String language = "ar";
                switch (which) {
                    case 0:
                        language = "ar";
                        break;
                    case 1:
                        language = "en";
                        break;

                }

                LocaleHelper.setLocale(MainActivity.this, language);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        }).create();
        alertDialog.show();


    }
}
