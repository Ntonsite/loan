package abt.dev.loan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class Step2Activity extends AppCompatActivity {

    //Declating Variables
    private EditText editTextBiashara;
    private EditText editTextMuda;
    private EditText editTextEneo;
    private EditText editTextKiasi;
    private EditText editTextUkomo;
    private Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);
        //obtaining data from the previous activity
        Bundle data = getIntent().getExtras();

        final String ukoo = data.getString("ukoo");
        final String kwanza = data.getString("kwanza");
        final String mengine = data.getString("mengine");
        final String mail = data.getString("mail");
        final String simu = data.getString("simu");
        final String mahali = data.getString("mahali");
        final String wilaya = data.getString("wilaya");
        final String chaguo = data.getString("chaguo");

        //declaring progress bar now
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarStep2);

        //casting the variables
        editTextBiashara = (EditText) findViewById(R.id.editTextBiashara);
        editTextMuda = (EditText) findViewById(R.id.editTextMuda);
        editTextEneo = (EditText) findViewById(R.id.editTextEneo);
        editTextKiasi = (EditText) findViewById(R.id.editTextKiasi);
        editTextUkomo = (EditText) findViewById(R.id.editTextUkomo);
        next = (Button) findViewById(R.id.next);

        //initiating onClick listener
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //storing passed data into String variables
                final String biashara = editTextBiashara.getText().toString().trim();
                final String muda = editTextMuda.getText().toString().trim();
                final String eneo = editTextEneo.getText().toString().trim();

                final String kiasi = editTextKiasi.getText().toString().trim();


                final String ukomo = editTextUkomo.getText().toString().trim();

                //validating passed data
                if(TextUtils.isEmpty(biashara)){
                    editTextBiashara.setError("Tafadhali jaza Biashara husika");
                    editTextBiashara.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(muda)){
                    editTextMuda.setError("Tafadhali jaza muda husika");
                    editTextMuda.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(eneo)){
                    editTextEneo.setError("Tafadhali jaza Eneo husika");
                    editTextEneo.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(kiasi)){
                    editTextKiasi.setError("Tafadhali ingiza kiasi");
                    editTextKiasi.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(ukomo)){
                    editTextUkomo.setError("Tafadhali jaza ukomo wa mkopo");
                    editTextUkomo.requestFocus();
                    return;
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    Intent receive_data = new Intent(getApplicationContext(), Step3Activity.class);

                    //passing data
                    receive_data.putExtra("ukoo",ukoo);
                    receive_data.putExtra("kwanza",kwanza);
                    receive_data.putExtra("mengine",mengine);
                    receive_data.putExtra("mail",mail);
                    receive_data.putExtra("simu",simu);
                    receive_data.putExtra("mahali",mahali);
                    receive_data.putExtra("wilaya",wilaya);
                    receive_data.putExtra("chaguo",chaguo);
                    receive_data.putExtra("biashara", biashara);
                    receive_data.putExtra("muda", muda);
                    receive_data.putExtra("eneo", eneo);
                    receive_data.putExtra("kiasi", kiasi);
                    receive_data.putExtra("ukomo", ukomo);

                    //starting activity now
                    startActivity(receive_data);
                }
            }
        });
    }
}
