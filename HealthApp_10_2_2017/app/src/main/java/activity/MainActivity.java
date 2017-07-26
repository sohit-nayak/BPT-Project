package activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import helper.SQLiteHandler;
import helper.SessionManager;
import weka.classifiers.functions.SMO;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.HttpsURLConnection;

import static android.os.Build.ID;
import static android.provider.MediaStore.Audio.AudioColumns.TRACK;
import static android.provider.Telephony.Carriers.PASSWORD;
//import javax.activation.DataHandler;

//import weka.core.DenseInstance;
public class MainActivity extends Activity {

    private TextView txtName;
    private TextView txtUsername;
    private TextView txtAge;
    private TextView txtHeight;
    private TextView txtWeight;
    private TextView txtWorkertrade;
    private TextView txtSmokinghabit;
    private TextView txtAlcoholhabit;
    private TextView txtYourclass;
    private TextView txtYourapi;
    private TextView txtYourHr;
    private TextView txtyourcl;
    private TextView wbgt_value;
    private Button btnLogout;
//    private Button btnKnowyourclass;
    private Button btnHitAPI;
    private String heart;
    private ScheduledExecutorService scheduleTaskExecutor;

//    //ADDED NEW
//    private Button btnSearch;
//    EditText emailText;
//    TextView responseView;
//    ProgressBar progressBar;
//    //static final String API_KEY = "54bc737d5875a0b8";
//    static final String API_URL = "http://ergast.com/api/f1/2004/1/results.json";
////    static final String API_URL = "https://api.fullcontact.com/v2/person.json?";
//    for checking
//    //TILL HERE

    private SQLiteHandler db;
    private SessionManager session;
    private static final String TAG = "MyActivity";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtName = (TextView) findViewById(R.id.name);
        txtUsername = (TextView) findViewById(R.id.username);
        txtAge = (TextView) findViewById(R.id.age);
        txtHeight = (TextView) findViewById(R.id.height);
        txtWeight = (TextView) findViewById(R.id.weight);
        txtWorkertrade = (TextView) findViewById(R.id.workertrade);
        txtSmokinghabit = (TextView) findViewById(R.id.smokinghabit);
        txtAlcoholhabit = (TextView) findViewById(R.id.alcoholhabit);
        txtYourclass = (TextView) findViewById(R.id.yourclass);
        txtYourapi = (TextView) findViewById(R.id.yourapi);
        txtYourHr = (TextView) findViewById(R.id.yourhr);
        txtyourcl = (TextView) findViewById(R.id.yourcl);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        wbgt_value = (TextView) findViewById(R.id.wbgt_value);
//        btnKnowyourclass = (Button) findViewById(R.id.btnKnowyourclass);
        btnHitAPI = (Button) findViewById(R.id.btnHitAPI);
//        btnSearch=(Button) findViewById(R.id.queryButton);
        btnHitAPI.setOnClickListener(new MiClass1());
//        btnKnowyourclass.setOnClickListener(new MiClass2());
//        btnSearch.setOnClickListener((View.OnClickListener) new RetrieveFeedTask());
        // SqLite database handler

        //NEW-----------------------------------
//        responseView = (TextView) findViewById(R.id.responseView);
//        emailText = (EditText) findViewById(R.id.emailText);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);

//        Button queryButton = (Button) findViewById(R.id.queryButton);
//        queryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new RetrieveFeedTask().execute();
//            }
//        });
        //END---------------------------------------
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String username = user.get("username");
        String age = user.get("age");
        String height = user.get("height");
        String weight = user.get("weight");
        String workertrade = user.get("workertrade");
        String smokinghabit = user.get("smokinghabit");
        String alcoholhabit = user.get("alcoholhabit");
        // Displaying the user details on the screen
        txtName.setText(name);
        txtUsername.setText(username);
        txtAge.setText(age);
        txtHeight.setText(height);
        txtWeight.setText(weight);
        txtWorkertrade.setText(workertrade);
        txtSmokinghabit.setText(smokinghabit);
        txtAlcoholhabit.setText(alcoholhabit);
//        CSVLoader loader = new CSVLoader();
//        Instances trainDataset = null;
//        AssetManager manager = getAssets();
//        try {
//            InputStream ttt = manager.open("StaticTrainData.csv");
//            loader.setSource(ttt);
//            SMO svm = new SMO();
//            trainDataset= loader.getDataSet();
//            trainDataset.setClassIndex(trainDataset.numAttributes()-1);
////            Toast.makeText(this,loader.getDataSet().toString(),Toast.LENGTH_LONG).show();
////            Toast.makeText(this,loader.getDataSet().toString(),Toast.LENGTH_LONG).show();
////            Toast.makeText(this,loader.getDataSet().toString(),Toast.LENGTH_LONG).show();
////            Toast.makeText(this,loader.getDataSet().toString(),Toast.LENGTH_LONG).show();
////            Toast.makeText(this,loader.getDataSet().toString(),Toast.LENGTH_LONG).show();
////            Toast.makeText(this,loader.getDataSet().toString(), Toast.LENGTH_LONG).show();
//            svm.buildClassifier(trainDataset);
//            Instance inst = new DenseInstance(trainDataset.numAttributes());
//            inst.setDataset(trainDataset);
//            inst.setValue(0, Integer.parseInt(user.get("age")));
//            inst.setValue(1, Integer.parseInt(user.get("height")));
//            inst.setValue(2, Integer.parseInt(user.get("weight")));
//            inst.setValue(3, Integer.parseInt(user.get("smokinghabit")));
//            inst.setValue(4, Integer.parseInt(user.get("alcoholhabit")));
//            inst.setValue(5, Integer.parseInt(user.get("workertrade")));
//            double predSVM = 0;
//            predSVM = svm.classifyInstance(inst);
//            String predString = trainDataset.classAttribute().value((int) predSVM);
//            String yourclass = "Your Class (type) is: " + predString;
//            txtYourclass.setText(yourclass);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        btnHitAPI.performClick();
                        MiClass1 htt = new MiClass1();
                        try {
                            String vin = htt.sendGet();
                            String server_val=htt.miclass2CustomMethod(vin);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }, 0, 60000);


        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        // extract wbgt value
        getWbgt();

    }

    public void getWbgt() {
        class getJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait...", null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine(333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333)) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                wbgt_value.setText(s);
            }
        }
        getJSON gj = new getJSON();
        gj.execute("http://www.vinaysammangi.esy.es/connect-1.php?id=1");
    }

    public void sendsms(View view) {
//        String phoneNumber = "9800116761";
//        String message = "Welcome to sms";
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
//        intent.putExtra("sms_body", message);
//        startActivity(intent);
    }
//    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {
//        @Override
//        protected String doInBackground(Void... voids) {
//            return null;
//        }
//    }

    public class MiClass1 implements View.OnClickListener {
        private String vinay;

        @Override
        public void onClick(View v) {
            MiClass1 http = new MiClass1();
            try {
                this.vinay = http.sendGet();
                miclass2CustomMethod(vinay);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // getdatafrom();
        }

        private String miclass2CustomMethod(String vinay) {
            HashMap<String, String> user1 = db.getUserDetails();
            CSVLoader loader = new CSVLoader();
            Instances trainDataset = null;
            AssetManager manager = getAssets();
            try {
                InputStream ttt = manager.open("StaticTrainData1.csv");
                loader.setSource(ttt);
                SMO svm = new SMO();
                trainDataset = loader.getDataSet();
                trainDataset.setClassIndex(trainDataset.numAttributes() - 1);
                svm.buildClassifier(trainDataset);
                Instance inst = new DenseInstance(trainDataset.numAttributes());
                inst.setDataset(trainDataset);
                inst.setValue(0, Integer.parseInt(user1.get("age")));
                inst.setValue(1, Integer.parseInt(user1.get("height")));
                inst.setValue(2, Integer.parseInt(user1.get("weight")));
                inst.setValue(3, Integer.parseInt(user1.get("smokinghabit")));
                inst.setValue(4, Integer.parseInt(user1.get("alcoholhabit")));
                inst.setValue(5, Integer.parseInt(user1.get("workertrade")));
//                heart = "140";
                heart = vinay;
//                inst.setValue(6, Integer.parseInt(String.valueOf(txtYourapi)));
                String yourclass = "";
                if (heart != "") {
                    inst.setValue(6, Integer.parseInt(heart));
//                String hr = new MiClass1;
                    double predSVM = 0;
                    predSVM = svm.classifyInstance(inst);
                    String predString = trainDataset.classAttribute().value((int) predSVM);
                    //String yourclass = "You belong to " + "'"+ predString+ "'";
                    yourclass = predString;
                } else {
                    yourclass = "Null Prediction";
                }

                txtYourclass.setText(yourclass);
                String phoneNumber = "9932186594";
                String smsBody = "You belong to "+ yourclass ;
                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage(phoneNumber, null, smsBody, null, null);
                return yourclass;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        private void sendsms(String phoneNumber, String message) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, null, null);
        }

        private String sendGet() throws IOException, InterruptedException, JSONException {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MINUTE, -15);
            Date before_d = now.getTime();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String reportDate = df.format(before_d);
            String use_date = reportDate.substring(11, 16);
            txtYourHr.setText("Heart Rate at " + use_date);
            txtyourcl.setText("Your class at " + use_date);
            String url = "https://api.fitbit.com/1/user/-/activities/heart/date/today/1d/1min/time/" + use_date + "/" + use_date + ".json";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
//            con.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1REZYSkoiLCJhdWQiOiIyMjg0OFMiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJyc29jIHJzZXQgcmFjdCBybG9jIHJ3ZWkgcmhyIHJudXQgcnBybyByc2xlIiwiZXhwIjoxNDkwNTMyNTkyLCJpYXQiOjE0ODk5Mjc3OTJ9.PFmPCqiI8x7nJtnbPxKTjyBvYexhefNlHMV8FCPYLLA");
//            con.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1REZYSkoiLCJhdWQiOiIyMjg0OFMiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJyc29jIHJzZXQgcmFjdCBybG9jIHJ3ZWkgcmhyIHJudXQgcnBybyByc2xlIiwiZXhwIjoxNTIzMDQ5NDA3LCJpYXQiOjE0OTE1MTM0MDd9.GqJ-ZjPHA3nIgcTnGAg7BUNQPdx8EWIpIDv_ZpzC7fo");
            con.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1REZYSkoiLCJhdWQiOiIyMjg0OFMiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJyc29jIHJzZXQgcmFjdCBybG9jIHJ3ZWkgcmhyIHJudXQgcnBybyByc2xlIiwiZXhwIjoxNTIzMDQ5NDA3LCJpYXQiOjE0OTc3MzE4MjB9.MHi97wE-2k9xLraDla9P1zsAy-emXeEnKshL9EmtIaQ");
            int responseCode = con.getResponseCode();//
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            StringBuilder jsonString = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                jsonString.append(inputLine);
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObj = new JSONObject(jsonString.toString());
            JSONObject final_json = null;
            try {
                final_json = new JSONObject(jsonObj.getJSONObject("activities-heart-intraday").getJSONArray("dataset").get(0).toString());
                txtYourapi.setText(final_json.get("value").toString());
            } catch (JSONException e) {
                txtYourapi.setText("Null");
            }
            if (final_json != null) {
                return final_json.get("value").toString();
            }


            return "";
        }

    }



//    public class MiClass2 implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            sendSMS("+91-9800116761", "Hi You got a message!");
//        }
//
//        private void sendSMS(String phoneNumber, String message) {
//            SmsManager sms = SmsManager.getDefault();
//            sms.sendTextMessage(phoneNumber, null, message, null, null);
//        }
//
//    }


//    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {
//
//        private Exception exception;
//
//        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);
//            responseView.setText("");
//        }
//
//        protected String doInBackground(Void... urls) {
//            //String email = emailText.getText().toString();
//            // Do some validation here
//
//            try {
//                URL url = new URL(API_URL);
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                try {
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String line;
//                    while ((line = bufferedReader.readLine()) != null) {
//                        stringBuilder.append(line).append("\n");
//                    }
//                    bufferedReader.close();
//                    return stringBuilder.toString();
//                }
//                finally{
//                    urlConnection.disconnect();
//                }
//            }
//            catch(Exception e) {
//                Log.e("ERROR", e.getMessage(), e);
//                return null;
//            }
//        }
//
//        protected void onPostExecute(String response) {
//            if(response == null) {
//                response = "THERE WAS AN ERROR";
//            }
//            progressBar.setVisibility(View.GONE);
//            Log.i("INFO", response);
//            responseView.setText(response);
//            // TODO: check this.exception
//            // TODO: do something with the feed

//            try {
//                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
//                String requestID = object.getString("requestId");
//                int likelihood = object.getInt("likelihood");
//                JSONArray photos = object.getJSONArray("photos");
//                .
//                .
//                .
//                .
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }


//    private void predictUser(){
//        HashMap<String, String> user1 = db.getUserDetails();
//
//        finish();
//    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
