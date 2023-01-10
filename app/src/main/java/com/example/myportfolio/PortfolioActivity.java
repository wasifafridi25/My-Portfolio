package com.example.myportfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

//import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static com.itextpdf.layout.property.Property.MAX_HEIGHT;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PortfolioActivity extends AppCompatActivity {
    private TextView portfolioName;
    private TextView portfolioEmail;
    private TextView portfolioProfession;
    private TextView portfolioWork;
    private TextView portfolioEducation;
    private TextView portfolioVolunteer;
    private TextView portfolioActivity;
    private TextView portfolioSkills;
    private TextView portfoliolanguage;
    private LinearLayout root;
    private Button sharePortfolio;
    private Button editPortfolio;
    private ScrollView portfolioScrollView;


    final DatabaseHelper helper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portfolio);

        portfolioName = (TextView)findViewById(R.id.portfolioName);
        portfolioEmail = (TextView)findViewById(R.id.portfolioEmail);
        portfolioProfession = (TextView)findViewById(R.id.portfolioProfessionalSummaryParagraph);
        portfolioWork = (TextView)findViewById(R.id.portfolioWorkExperienceParagraph);
        portfolioVolunteer = (TextView)findViewById(R.id.portfolioVolunteerParagraph);
        portfolioEducation = (TextView)findViewById(R.id.portfolioEducationParagraph);
        portfolioActivity = (TextView)findViewById(R.id.portfolioActivityParagraph);
        portfolioSkills = (TextView)findViewById(R.id.portfolioSkillsParagraph);
        portfoliolanguage = (TextView)findViewById(R.id.portfolioLanguageParagraph);

        Cursor cursor = helper.fetch();
        cursor.moveToLast();

        portfolioName.setText(cursor.getString(1));
        portfolioEmail.setText(cursor.getString(2));
        portfolioProfession.setText(cursor.getString(3));
        portfolioWork.setText(cursor.getString(4));
        portfolioEducation.setText(cursor.getString(5));
        portfolioVolunteer.setText(cursor.getString(6));
        portfolioActivity.setText(cursor.getString(7));
        portfolioSkills.setText(cursor.getString(8));
        portfoliolanguage.setText(cursor.getString(9));

        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());



        sharePortfolio = (Button)findViewById(R.id.share);
        //editPortfolio = (Button)findViewById(R.id.edit);




        sharePortfolio.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                screenShot();
            }
        });


    }
    public void screenShot(){
        //View view1 = getWindow().getDecorView().getRootView();//findViewById(R.id.parentLayout);//getWindow().getDecorView().getRootView();
        View u = findViewById(R.id.portfolioScrollView);
        ScrollView s = (ScrollView)findViewById(R.id.portfolioScrollView);
        int totalHeight = s.getChildAt(0).getHeight();
        int totalWidth = s.getChildAt(0).getWidth();

        Bitmap b = getBitmapFromView(u,totalHeight,totalWidth);
        s.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(s.getDrawingCache());
        s.setDrawingCacheEnabled(false);

        String filePath = Environment.getExternalStorageDirectory()+"/Download/"+"portfolio.jpg";
        File fileScreenshot = new File(filePath);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileScreenshot);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(fileScreenshot);
        intent.setDataAndType(uri,"image/jpeg");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }
    public static Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        int height = Math.min(MAX_HEIGHT, totalHeight);
        float percent = height / (float)totalHeight;

        Bitmap bitmap = Bitmap.createBitmap((int)(totalWidth*percent),(int)(totalHeight*percent), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);

        Drawable drawable = view.getBackground();
        if (drawable != null)
            drawable.draw(c);
        else
            c.drawColor(Color.WHITE);

        c.save();
        c.scale(percent, percent);
        view.draw(c);
        c.restore();

        return bitmap;
    }





    /*private void takeScreenShot() {

        try {


            View u = ((Activity) this).findViewById(R.id.portfolioScrollView);

            ScrollView z = (ScrollView) ((Activity) this).findViewById(R.id.portfolioScrollView);
            int totalHeight = z.getChildAt(0).getHeight();
            int totalWidth = z.getChildAt(0).getWidth();

            Bitmap bitmap = getBitmapFromView(u,totalHeight,totalWidth);

            Image image;

            //Save bitmap
            String path = Environment.getExternalStorageDirectory()+"/Folder/";
            String fileName = "report1.pdf";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            Log.v("PDFCreator", "PDF Path: " + path);

            File myPath = new File(path, fileName);



            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
            image = Image.getInstance(stream.toByteArray());
            image.setAbsolutePosition(0, 0);
            Document document = new Document(image);
            PdfWriter.getInstance(document, new FileOutputStream(myPath));
            document.open();
            document.add(image);
            document.close();


        } catch (Exception i1) {
            i1.printStackTrace();
        }
    }*/
    /*private void screenShot()
    {
        View u = findViewById(R.id.portfolioScrollView);

        ScrollView s = (ScrollView)findViewById(R.id.portfolioScrollView);
        int totalHeight = s.getChildAt(0).getHeight();
        int totalWidth = s.getChildAt(0).getWidth();

        Bitmap b = getBitmapFromView(u,totalHeight,totalWidth);

        //Save bitmap
        String extr = Environment.getExternalStorageDirectory()+"/Folder/";
        String fileName = "portfolio.jpg";
        File myPath = new File(extr, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(this.getContentResolver(), b, "Screen", "screen");
        }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }*/

    /*public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap bitmap = Bitmap.createBitmap(totalWidth,totalHeight , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }*/
    /*public static Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        int height = Math.min(MAX_HEIGHT, totalHeight);
        float percent = height / (float)totalHeight;

        Bitmap canvasBitmap = Bitmap.createBitmap((int)(totalWidth*percent),(int)(totalHeight*percent), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);

        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);

        canvas.save();
        canvas.scale(percent, percent);
        view.draw(canvas);
        canvas.restore();

        return canvasBitmap;
    }*/
}