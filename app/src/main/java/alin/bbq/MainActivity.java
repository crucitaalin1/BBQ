package alin.bbq;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DataProgress {
    TextView tv1,tv2,tv3,tv4,tv5;
    String[] weather;
    Boolean ok;
    String[] goodDays;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ok = false;

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);

        Button b1 = (Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message;
                if(goodDays[0] == null)
                {
                    message = "Bad weather these days but hey, don't give up!";
                }
                else
                {
                    if(ok == true)
                    {
                        message = "Great days for a BBQ: \nToday \n";
                        int i=1;
                            while(i<goodDays.length && goodDays[i]!=null)
                            {
                                message = message + goodDays[i] + "\n";
                                i++;
                            }


                    }
                    else
                    {
                        message = "Great days for a BBQ: \n";
                        int i=1;
                        while(goodDays[i]!=null && i<goodDays.length)
                        {
                            message = message + goodDays[i] + "\n";
                            i++;
                        }
                    }

                }
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("BBQ");
                alertDialog.setMessage(message);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        //request location permission
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    99);
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    99);
        }

        CurrentLocation currentLocation = new CurrentLocation(this);
        Location location = currentLocation.getLocation();

        double Lat = location.getLatitude();
        double Lon = location.getLongitude();

        String APIkey = "1dcd2a35b4167a29d79fa56dcb4f4c62";//"9e69688b117d83a2210fef821a1a3215";
       // String weather_url = "http://api.openweathermap.org/data/2.5/weather?lat="+Lat+"&lon="+Lon+"&apikey="+APIkey;
        String weather_url = "https://api.darksky.net/forecast/1dcd2a35b4167a29d79fa56dcb4f4c62/"+Lat+","+Lon;
        requestWeather(weather_url);
    }

    private void requestWeather(String url)
    {
        try {
            new GetWeather(this,url).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGet() {
        progressDialog = ProgressDialog.show(this, "",
                "Getting the weather...", true);
    }

    @Override
    public void onSuccess(String weather_list) {
        progressDialog.dismiss();

        Dates dates = new Dates();
        String days = dates.getDates();

        String[] aux = days.split(" ");
        String[] weather_days = weather_list.split(" ");

        //...
        goodDays = new String[4];
        int cont = 0;
        //...

        for(int i=0;i<weather_days.length;i++)
        {
            double w = (Double.parseDouble(weather_days[i]) - 32)*5/9;
            weather_days[i] = String.valueOf(w);

         if(w > 23 && i == 0)
         {
             ok = true;
         }
        }

        tv1.setText("Today's weather: "+ weather_days[0]);
        tv2.setText(aux[0] + ": MIN " + weather_days[1] + " MAX " + weather_days[2]);
        tv3.setText(aux[1] + ": MIN " + weather_days[3] + " MAX " + weather_days[4]);
        tv4.setText(aux[2] + ": MIN " + weather_days[5] + " MAX " + weather_days[6]);
        tv5.setText(aux[3] + ": MIN " + weather_days[7] + " MAX " + weather_days[8]);

        //...

            if(Double.parseDouble(weather_days[2])> 22)
            {
                goodDays[cont++] = aux[0];
            }
            if(Double.parseDouble(weather_days[4])> 22)
            {
                goodDays[cont++] = aux[1];
            }
            if(Double.parseDouble(weather_days[6])> 22)
            {
                goodDays[cont++] = aux[2];
            }
            if(Double.parseDouble(weather_days[8])> 22)
            {
                goodDays[cont++] = aux[3];
            }
    }

    @Override
    public void onFailed() {

    }

}
