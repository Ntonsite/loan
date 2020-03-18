package abt.dev.loan;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Step3Activity extends AppCompatActivity {


    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3);



        final SignaturePad signaturePadMteja = (SignaturePad) findViewById(R.id.signaturePadMteja);
        final SignaturePad signaturePadWakala = (SignaturePad) findViewById(R.id.signaturePadWakala);

        final Button clearButtonMteja = (Button) findViewById(R.id.clearButtonMteja);
        final Button clearButtonWakala = (Button) findViewById(R.id.clearButtonWakala);

        final Button saveButton = (Button) findViewById(R.id.buttonSave);

        //initializing database instance


        //disable the buttons on the start

        saveButton.setEnabled(false);
        clearButtonMteja.setEnabled(false);
        clearButtonWakala.setEnabled(false);

        signaturePadMteja.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                saveButton.setEnabled(true);
                clearButtonMteja.setEnabled(true);
            }

            @Override
            public void onClear() {
                saveButton.setEnabled(false);
                clearButtonMteja.setEnabled(false);
            }
        });
        signaturePadWakala.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                saveButton.setEnabled(true);
                clearButtonWakala.setEnabled(true);
            }

            @Override
            public void onClear() {
                saveButton.setEnabled(false);
                clearButtonWakala.setEnabled(false);
            }
        });
        clearButtonWakala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePadWakala.clear();
            }
        });
        clearButtonMteja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePadMteja.clear();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checking network connection and send where it belong

                if(checkNetworkConnection()){
                    saveToAppServer();
                }
                else
                {
                    saveToLocalStorage();
                }

            }
        });
    }

    private void saveToAppServer() {
        db = new DBHelper(getApplicationContext());

        //now getting the user's id to be inserted as a columnID(Mtumaji wa Application)
        User user = SharedPrefManager.getInstance(this).getUser();

        final int userId = user.getId();

        final String id = Integer.toString(userId);

        //receiving data from previous activity
        Bundle data = getIntent().getExtras();

        final String ukoo = data.getString("ukoo");
        final String kwanza = data.getString("kwanza");
        final String mengine = data.getString("mengine");
        final String mail = data.getString("mail");
        final String simu = data.getString("simu");
        final String mahali = data.getString("mahali");
        final String wilaya = data.getString("wilaya");
        final String chaguo = data.getString("chaguo");
        final String biashara = data.getString("biashara");
        final String muda = data.getString("muda");
        final String eneo = data.getString("eneo");
        final String kiasi = data.getString("kiasi");
        final String ukomo = data.getString("ukomo");
        final int _status = 1;
        final int _status1 = 0;
        final String server_status = Integer.toString(_status);
        final String local_status = Integer.toString(_status1);


        //new Approach for Data to be synchronized, At first data is stored on SQLITE DB then synced on clicking

        class appRegister extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {

                //creating a request Handler Object
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("ukoo", ukoo);
                params.put("kwanza", kwanza);
                params.put("mengine", mengine);
                params.put("mail", mail);
                params.put("simu", simu);
                params.put("mahali", mahali);
                params.put("wilaya", wilaya);
                params.put("chaguo", chaguo);
                params.put("biashara", biashara);
                params.put("muda", muda);
                params.put("eneo", eneo);
                params.put("kiasi", kiasi);
                params.put("ukomo", ukomo);
                params.put("id", id);//id of the one who just sent an application

                //sending the request with parameters above
                return requestHandler.sendPostRequest(URLs.URL_POST, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user sends application to a server
                progressBar = (ProgressBar) findViewById(R.id.progressBarFinish);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        db.insertApplication(ukoo, kwanza, mengine, mail, simu, mahali, wilaya, chaguo, biashara, kiasi, muda, ukomo, id, server_status);
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();


                        //starting the profile activity after clearing also the previous top activity
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {

                        db.insertApplication(ukoo, kwanza, mengine, mail, simu, mahali, wilaya, chaguo, biashara, kiasi, muda, ukomo, id, local_status);

                        //clearing the top activities and start new activity
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        appRegister ru = new appRegister();
        ru.execute();
    }

    public boolean checkNetworkConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo!=null && networkInfo.isConnected());
    }
    public void saveToLocalStorage(){




        //receiving data from previous activity
        Bundle data = getIntent().getExtras();

        final String ukoo = data.getString("ukoo");
        final String kwanza = data.getString("kwanza");
        final String mengine = data.getString("mengine");
        final String mail = data.getString("mail");
        final String simu = data.getString("simu");
        final String mahali = data.getString("mahali");
        final String wilaya = data.getString("wilaya");
        final String chaguo = data.getString("chaguo");
        final String biashara = data.getString("biashara");
        final String muda = data.getString("muda");
        final String eneo = data.getString("eneo");
        final String kiasi = data.getString("kiasi");
        final String ukomo = data.getString("ukomo");
        final int _status = 1;
        final int _status1 = 0;
        final String local_status = Integer.toString(_status1);


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        Toast.makeText(Step3Activity.this, "Data Stored to local Database", Toast.LENGTH_SHORT).show();

    }

}