package com.example.cotizacionesvisenar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cotizacionesvisenar.DateUserActivity;
import com.example.cotizacionesvisenar.Model.Movie;
import com.example.cotizacionesvisenar.R;
import com.example.cotizacionesvisenar.PDF.viewPDFActivity;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends PagerAdapter {

    Context context;
    List<Movie> movieList;
    LayoutInflater inflater;

    //double hora = 0, costo = 0, valor = 0;

    //String[] horas = {"Hora","4","8","12","16","24","48"};

    public MyAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // inflete view
        View view = inflater.inflate(R.layout.view_pager_item,container,false);

        //View
        ImageView movie_image = (ImageView)view.findViewById(R.id.movie_image);
        TextView movie_title = (TextView)view.findViewById(R.id.movie_title);
        TextView movie_description = (TextView)view.findViewById(R.id.movie_description);
        final TextView movie_costo = (TextView)view.findViewById(R.id.movie_costo);
        //final Spinner movie_hora = (Spinner) view.findViewById(R.id.spinner);
        //movie_hora.setAdapter(new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item,horas));
        final FloatingActionButton btn_email = (FloatingActionButton)view.findViewById(R.id.btn_email);

        //Set Data
        Picasso.get().load(movieList.get(position).getImage()).into(movie_image);
        movie_title.setText(movieList.get(position).getName());
        movie_description.setText(movieList.get(position).getDescription());
        movie_costo.setText(movieList.get(position).getCostoSS());

        //Event
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = new Locale("es","CO");
                NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
                Intent intent = new Intent(context, DateUserActivity.class);
                intent.putExtra("valor",movie_costo.getText().toString());
                context.startActivity(intent);

                /*
                Locale locale = new Locale("es","CO");
                NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
                if (movie_hora.getSelectedItem() != "Hora") {
                    //conversion
                    hora = Double.parseDouble(movie_hora.getSelectedItem().toString());
                    costo = Double.parseDouble(movie_costo.getText().toString());
                    if (hora <= 24){
                        valor = (((hora * costo)/48)*1.2);
                        //Toast.makeText(context, "Valor total: " + (nf.format(valor).replace(",", ".")), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,viewPDFActivity.class);
                        intent.putExtra("hora",movie_hora.getSelectedItem().toString());
                        intent.putExtra("valor",(nf.format(valor).replace(",", ".")));
                        context.startActivity(intent);
                    }
                    if (hora > 24){
                        valor = ((hora * costo)/48);
                        //Toast.makeText(context, "Valor total: " + (nf.format(valor).replace(",",".")), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,viewPDFActivity.class);
                        intent.putExtra("hora",movie_hora.getSelectedItem().toString());
                        intent.putExtra("valor",(nf.format(valor).replace(",", ".")));
                        context.startActivity(intent);
                    }
                }else{
                    Toast.makeText(context, "Campo vacio, por favor intentelo nuevamente", Toast.LENGTH_SHORT).show();
                }
                */
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Film clicked", Toast.LENGTH_SHORT).show();
            }
        });

        container.addView(view);
        return view;
    }




}
