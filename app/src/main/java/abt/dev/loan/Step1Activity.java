package abt.dev.loan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Step1Activity extends AppCompatActivity {

    private EditText editTextukoo;
    private EditText editTextKwanza;
    private EditText editTextMengine;
    private EditText editTextMail;
    private EditText editTextSimu;
    private EditText editTextMahali;
    private EditText editTextWilaya;

    private RadioButton first;
    private RadioButton repeat;
    private RadioGroup radioGroupChaguo;

    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step1);
        editTextukoo = (EditText) findViewById(R.id.editTextUkoo);
        editTextKwanza = (EditText) findViewById(R.id.editTextKwanza);
        editTextMengine = (EditText) findViewById(R.id.editTextMengine);
        editTextMail = (EditText) findViewById(R.id.editTextMail);
        editTextSimu = (EditText) findViewById(R.id.editTextSimu);
        editTextMahali = (EditText) findViewById(R.id.editTextMahali);
        editTextWilaya = (EditText) findViewById(R.id.editTextWilaya);
        radioGroupChaguo = (RadioGroup) findViewById(R.id.radioChaguo);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarStep1);


        next = (Button) findViewById(R.id.next);

        //storing data into string variables

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String ukoo = editTextukoo.getText().toString().toUpperCase().trim();
                final String kwanza = editTextKwanza.getText().toString().toUpperCase().trim();
                final String mengine = editTextMengine.getText().toString().toUpperCase().trim();
                final String mail = editTextMail.getText().toString().trim();
                final String simu = editTextSimu.getText().toString().trim();
                final String mahali = editTextMahali.getText().toString().trim();
                final String wilaya = editTextWilaya.getText().toString().trim();

                final String chaguo = ((RadioButton) findViewById(radioGroupChaguo.getCheckedRadioButtonId())).getText().toString();
                //Validate values

                //first let's validate
                if(TextUtils.isEmpty(ukoo)){
                    editTextukoo.setError("Tafadhali ingiza Jina la Ukoo");
                    editTextukoo.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(kwanza)){
                    editTextKwanza.setError("Tafadhali ingiza jina la kwanza");
                    editTextKwanza.requestFocus();
                    return;
                }if(TextUtils.isEmpty(simu)){
                    editTextSimu.setError("Tafadhali ingiza namba ya simu");
                    editTextSimu.requestFocus();
                    return;
                }if(TextUtils.isEmpty(mahali)){
                    editTextMahali.setError("Tafadhali jaza Mahali");
                    editTextMahali.requestFocus();
                    return;
                }if(TextUtils.isEmpty(wilaya)){
                    editTextWilaya.setError("Tafadhali jaza Wilaya");
                    editTextWilaya.requestFocus();
                    return;
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    Intent pass_data = new Intent(getApplicationContext(), Step2Activity.class);

                    //passing Data
                    pass_data.putExtra("ukoo",ukoo);
                    pass_data.putExtra("kwanza",kwanza);
                    pass_data.putExtra("mengine",mengine);
                    pass_data.putExtra("mail",mail);
                    pass_data.putExtra("simu",simu);
                    pass_data.putExtra("mahali",mahali);
                    pass_data.putExtra("wilaya",wilaya);
                    pass_data.putExtra("chaguo",chaguo);


                    //starting activity now
                    startActivity(pass_data);

                }
            }
        });

    }
}
