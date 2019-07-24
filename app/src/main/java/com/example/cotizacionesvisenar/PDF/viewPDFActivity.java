package com.example.cotizacionesvisenar.PDF;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cotizacionesvisenar.DateUserActivity;
import com.example.cotizacionesvisenar.HomeActivity;
import com.example.cotizacionesvisenar.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class viewPDFActivity extends AppCompatActivity {

    private TextView textViewHora, textViewValor, textViewNombre, textViewCorreo, textViewCelular;
    /*private Button buttonAtras,buttonPDF;*/
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        textViewNombre = findViewById(R.id.textViewNombre);
        textViewCorreo = findViewById(R.id.textViewCorreo);
        textViewCelular = findViewById(R.id.textViewCelular);
        textViewHora = findViewById(R.id.textViewHora);
        textViewValor = findViewById(R.id.textViewValor);
        /*buttonAtras = findViewById(R.id.btnAtras);
        buttonPDF = findViewById(R.id.btnPDF);*/

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if(b != null){
            String n = (String) b.get("nombre");
            textViewNombre.setText(n);
            String e = (String) b.get("correo");
            textViewCorreo.setText(e);
            String c = (String) b.get("celular");
            textViewCelular.setText(c);
            String h = (String) b.get("hora");
            textViewHora.setText(h);
            String v = (String) b.get("valor");
            textViewValor.setText(v);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Verifica permisos para Android 6.0+
            isStoragePermissionGranted();
        }

        metodos();

        /*buttonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewPDFActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutToImage();
                //convertCertViewToImage();
                try {
                    imageToPDF();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }


    public void metodos(){
        layoutToImage();

    }

    String dirpath;
    public void layoutToImage() {
        // get view group using reference
        constraintLayout = findViewById(R.id.print);
        // convert view group to bitmap
        constraintLayout.setDrawingCacheEnabled(true);
        constraintLayout.buildDrawingCache();
        Bitmap bm = constraintLayout.getDrawingCache();
        //Bitmap bm = BitmapFactory.decodeFile(constraintLayout.getDrawingCache().toString());
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

            imageToPDF();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void convertCertViewToImage(){
        // get view group using reference
        constraintLayout = findViewById(R.id.print);
        // convert view group to bitmap
        constraintLayout.setDrawingCacheEnabled(true);
        constraintLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        constraintLayout.layout(0, 0, constraintLayout.getMeasuredWidth(), constraintLayout.getMeasuredHeight());
        constraintLayout.buildDrawingCache();
        Bitmap bm = Bitmap.createBitmap(constraintLayout.getDrawingCache());
        constraintLayout.setDrawingCacheEnabled(false); //clear drawing cache
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpg");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File f = new File(getExternalFilesDir(null).getAbsolutePath() + File.separator + "Certificate" + File.separator + "myCertificate.jpg");
        try{
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        }catch (IOException e){
            e.printStackTrace();
        }
        Toast.makeText(this, "Metodo ConvertCertViewToImage", Toast.LENGTH_SHORT).show();
    }

    public void imageToPDF() throws FileNotFoundException {
        try {
            Document document = new Document();
            dirpath = android.os.Environment.getExternalStorageDirectory().toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/NewPDF.pdf")); //Change pdf's name.
            document.open();
            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }


}
