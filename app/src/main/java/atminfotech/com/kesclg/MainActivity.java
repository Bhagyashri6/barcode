package atminfotech.com.kesclg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText barcode;
    Button show,reset;
    String Barcode;
    TextView Group,Dept,Location,Asset,Serialno;
    String group,dept,loc,asset,serial;
    Boolean serverissue = false;
    public static ArrayList<ModelDeailyReport> listReport = new ArrayList<>();


    private static final String URL = "http://192.168.1.107:8087/WebService.asmx";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String SOAP_ACTION = "http://tempuri.org/GetQrcodeData";
    private static final String METHOD_NAME = "GetQrcodeData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barcode = (EditText) findViewById(R.id.barcode);
        reset =(Button)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Group.setText("");
                Dept.setText("");
                Location.setText("");
                Asset.setText("");
                Serialno.setText("");
            }
        });
        barcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Barcode = barcode.getText().toString();
                new  asyncQRdata(Barcode).execute();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        show =(Button)findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Barcode = barcode.getText().toString();
                new  asyncQRdata(Barcode).execute();
            }
        });

    }

    public class asyncQRdata extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        String Barcode, portt, truckk, contt;
        Bean beanobj = new Bean();
        int flag = 0;

        public asyncQRdata(String Barcode) {
            this.Barcode = Barcode;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listReport.clear();
            pd.setMessage("Please wait...");
            pd.show();

            //  tag =taggg.getText().toString();


        }

        protected Void doInBackground(Void... params) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("Tag", Barcode);
                Bean C = new Bean();
                PropertyInfo pi = new PropertyInfo();
                pi.setName("Bean");
                pi.setValue(C);
                pi.setType(C.getClass());
                request.addProperty(pi);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                envelope.addMapping(NAMESPACE, "Bean", new Bean().getClass());

                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 60 * 10000);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapObject response2 = (SoapObject) envelope.getResponse();
                Bean[] personobj = new Bean[response2.getPropertyCount()];
                Bean beanobj = new Bean();

                for (int j = 0; j < personobj.length; j++) {

                    SoapObject pii = (SoapObject) response2.getProperty(j);
                    beanobj.Group = pii.getProperty(0).toString();
                    beanobj.Dept = pii.getProperty(1).toString();
                    beanobj.Location = pii.getProperty(2).toString();
                    beanobj.Assetname = pii.getProperty(3).toString();
                    beanobj.Serialno = pii.getProperty(4).toString();

                    personobj[j] = beanobj;

                }

                group = beanobj.Group;
                dept = beanobj.Dept;
                loc = beanobj.Location;
                asset = beanobj.Assetname;
                serial = beanobj.Serialno;



            } catch (Exception e) {
                e.printStackTrace();
                pd.dismiss();

                serverissue = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            pd.dismiss();
            super.onPostExecute(aVoid);
            if (group ==null && dept == null && asset == null ){

            }else {
                Display();
                barcode.setText("");
            }
            if (serverissue) {
                MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(MainActivity.this)
                        .setTitle("Ooops!!!")
                        .setIcon(R.drawable.ic_launcher_background)
                        .setHeaderDrawable(R.color.colorPrimary)
                        .setDescription("Server is not responding...\n")
                        .withIconAnimation(true)
                        .setPositiveText("OK")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                Log.d("MaterialStyledDialogs", "Do something!");
                                // adapter.clear();
                                dialog.dismiss();
                            }
                        })
                        .show();
            }

        }
    }

    private void Display(){
        Group =(TextView)findViewById(R.id.group);
        Group.setText(group);

        Dept =(TextView)findViewById(R.id.dept);
        Dept.setText(dept);

        Location =(TextView)findViewById(R.id.location);
        Location.setText(loc);

        Asset =(TextView)findViewById(R.id.assetname);
        Asset.setText(asset);

        Serialno =(TextView)findViewById(R.id.serialno);
        Serialno.setText(serial);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_home) {
            Intent intent =new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
    }
}
