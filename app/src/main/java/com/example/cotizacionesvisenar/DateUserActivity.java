package com.example.cotizacionesvisenar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cotizacionesvisenar.PDF.viewPDFActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class DateUserActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner_hora;
    private EditText name, email, phone;
    private Button cotizar;
    private TextView textViewValor;
    double hora = 0, costo = 0, valor = 0;
    String name1,email1,phone1,hora1;

    String[] horas = {"Hora","4","8","12","16","24","48"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_user);

        spinner_hora = findViewById(R.id.spinner);
        spinner_hora.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, horas));

        name = findViewById(R.id.editText_name);
        email = findViewById(R.id.editText_email);
        phone = findViewById(R.id.editText_phone);

        cotizar = findViewById(R.id.button_cotizar);
        cotizar.setOnClickListener(this);

        textViewValor = findViewById(R.id.costo);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if(b != null){
            String k = (String) b.get("valor");
            textViewValor.setText(k);
        }
    }

    @Override
    public void onClick(View v) {
        validaciones();
    }

    public void calculo(){
        Locale locale = new Locale("es","CO");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        if (spinner_hora.getSelectedItem() != "Hora") {
            //conversion
            hora = Double.parseDouble(spinner_hora.getSelectedItem().toString());
            costo = Double.parseDouble(textViewValor.getText().toString());
            name1 = name.getText().toString();
            email1 = email.getText().toString();
            phone1 = phone.getText().toString();
            hora1 = spinner_hora.getSelectedItem().toString();
            if (hora <= 24){
                valor = (((hora * costo)/48)*1.2);
                /*Intent intent = new Intent(this,viewPDFActivity.class);
                intent.putExtra("nombre",name1);
                intent.putExtra("correo",email1);
                intent.putExtra("celular",phone1);
                intent.putExtra("hora",hora1);
                intent.putExtra("valor",(nf.format(valor).replace(",", ".")));
                startActivity(intent);*/
                Toast.makeText(getApplicationContext(), "Valor total: " + (nf.format(valor).replace(",", ".")), Toast.LENGTH_SHORT).show();
                //pdf();
            }
            if (hora > 24){
                valor = ((hora * costo)/48);
                /*Intent intent = new Intent(this, viewPDFActivity.class);
                intent.putExtra("nombre",name.getText());
                intent.putExtra("correo",email.getText());
                intent.putExtra("celular",phone.getText());
                intent.putExtra("hora",hora);
                startActivity(intent);*/
                Toast.makeText(getApplicationContext(), "Valor total: " + (nf.format(valor).replace(",",".")), Toast.LENGTH_SHORT).show();
                //pdf();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Campo vacio, por favor intentelo nuevamente", Toast.LENGTH_SHORT).show();
        }
    }


    private void validaciones() {
        String eName = name.getText().toString();
        String eEmail = email.getText().toString();
        String ePhone = phone.getText().toString();

        if (!eName.isEmpty() && !eEmail.isEmpty() && !ePhone.isEmpty()){
            calculo();
        }else{
            Toast.makeText(this, "Campos vacios, por favor intentelo nuevamente", Toast.LENGTH_LONG).show();

            if (eName.isEmpty()){
                name.setError("Nombre no válido");
                name.requestFocus();
            }
            if (eEmail.isEmpty()){
                email.setError("Correo no válido");
                email.requestFocus();
            }
            if (ePhone.isEmpty()){
                phone.setError("Número no válido");
                phone.requestFocus();
            }

        }
    }
}
