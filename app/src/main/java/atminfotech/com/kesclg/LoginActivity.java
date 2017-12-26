package atminfotech.com.kesclg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button login;
    private String uname, pwd,IMEI_no;
    private static final String URL = "http://192.168.1.107:8087/WebService.asmx";
    private static final String NAMESPACE = "http://tempuri.org/";

   /* private static final String METHOD_NAMEimemi = "ATMimei";
    private static final String SOAP_ACTIONimei = "http://tempuri.org/ATMimei";*/
    private static final String METHOD_NAMELogin = "KESLogin";
    private static final String SOAP_ACTIONLogin= "http://tempuri.org/KESLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login =(Button)findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                  /* Write your logic here that will be executed when user taps next button */

                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);

                }
                return false;
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname = username.getText().toString();
                pwd = password.getText().toString();

                new AsynkLogin(uname,pwd).execute();
            }
        });

    }

    class  AsynkLogin extends AsyncTask<Void,Void,Void> {

        ProgressDialog pd = new ProgressDialog(LoginActivity.this);

        String result= "false";
        String uname="",pwd="";


        public AsynkLogin(String uname, String pwd) {
            this.uname=uname;
            this.pwd=pwd;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                SoapObject request = new SoapObject(NAMESPACE,METHOD_NAMELogin);
                request.addProperty("username",uname.trim());
                request.addProperty("password",pwd);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE transportSE = new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTIONLogin,envelope);
                SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();

                result=soapPrimitive.toString();

            } catch (Exception e) {
                e.printStackTrace();
                pd.dismiss();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);

            if(result.contentEquals("true"))
            {
                pd.dismiss();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
            else {
                pd.dismiss();
                Toast.makeText(LoginActivity.this," username and password Incorrect..!",Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_shutdown) {
            new MaterialStyledDialog.Builder(LoginActivity.this)
                    .setTitle("Exit")
                    .setDescription("Do you really want to exit the application?")
                    .setIcon(R.mipmap.logoatm)
                    .setHeaderDrawable(R.color.colorPrimary)
                    .setPositiveText("Yes")
                    .withIconAnimation(true)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    })
                    .setNegativeText("No")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(true)
                    .show();

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
    }

    
}
