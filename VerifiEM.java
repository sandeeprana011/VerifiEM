package sirfireys.rana.verifiem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class VerifiEM extends ActionBarActivity {


    private boolean back = false;
    ConnectivityCheck connectivityCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        connectivityCheck = new ConnectivityCheck();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verifi_em, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        int position;
        String table_name = null;
        String prf_name;
        String urlStr;
        String sem;
//        http://sandeeprana011.hostfree.us/result/verifiem.php?table_name=NIU_SET&prf=5187&sem=7


        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if (intentResult != null) {
            String[] dataStr = intentResult.getContents().split("_"); //5187_7_4

            if (dataStr.length == 3) {
                position = Integer.valueOf(dataStr[2]);
                sem = dataStr[1];
                prf_name = dataStr[0];
                switch (position) {
                    case 0:
                        table_name = "NIU_ARCH";
                        break;
                    case 1:
                        table_name = "NIU_EDU";
                        break;
                    case 2:
                        table_name = "NIU_SET";
                        break;
                    case 3:
                        table_name = "NIU_SCI";
                        break;
                    case 4:
                        table_name = "NIU_LAW";
                        break;
                    case 5:
                        table_name = "NIU_JMC";
                        break;
                    case 6:
                        table_name = "NIU_NUR";
                        break;
                    case 7:
                        table_name = "NIU_FA";
                        break;
                    case 8:
                        table_name = "NIU_LA";
                        break;
                    case 9:
                        table_name = "NIU_DSI";
                        break;

                }

                table_name = "NIU_SET";
                String[] fin_Data = new String[]{sem, prf_name, table_name};
                int cStatus = connectivityCheck.connectionStatus(getApplicationContext());
                if (cStatus == ConnectivityCheck.TYPE_NOT_CONNECTED) {

                    setContentView(R.layout.lay_errorshow);
                    back = false;

                } else {

                    Stu_details stu_details = new Stu_details();
                    stu_details.execute(fin_Data);
                }
            }

        } else {

        }


    }


    class Stu_details extends AsyncTask<String, Integer, String> {
        Result_Data result_data;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            back = true;
        }

        @Override
        protected String doInBackground(String... params) {
            Result_Download result_download = new Result_Download();
            result_data = result_download.result(params[0], params[1], params[2]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setContentView(R.layout.stu_detail);

            new DownloadImageTask((ImageView) findViewById(R.id.student_pro_pic), (ProgressBar) findViewById(R.id.progressBar))
                    .execute("http://sandeeprana011.hostfree.us/student_dp/" + result_data.getPrf() + ".jpg");


            TextView name = (TextView) findViewById(R.id.res_op_name);
            name.setText(result_data.getName());

            TextView father_name = (TextView) findViewById(R.id.res_op_f_name);
            father_name.setText(result_data.getFather_name());

            TextView prf = (TextView) findViewById(R.id.res_op_prf);
            prf.setText(result_data.getPrf());

            TextView sem = (TextView) findViewById(R.id.res_op_sem);
            sem.setText(result_data.getSem());

            TextView course = (TextView) findViewById(R.id.res_op_course);
            course.setText(result_data.getCourse());

            TextView dob = (TextView) findViewById(R.id.res_op_dob);
            dob.setText(result_data.getDob());

            TextView enroll_nu = (TextView) findViewById(R.id.res_op_enroll_no);
            enroll_nu.setText(result_data.getEnroll_no());


            String publis = result_data.getPulish().replace(" ", "");

            if (publis.equalsIgnoreCase("Y")) {
                TextView publish_status = (TextView) findViewById(R.id.res_publish_status);
                publish_status.setText("Student is eligible.");
                publish_status.setTextColor(Color.argb(255, 60, 150, 60));

            } else if (publis.equalsIgnoreCase("N")) {
                TextView publish_status = (TextView) findViewById(R.id.res_publish_status);
                publish_status.setText("Student is not eligible.");
                publish_status.setTextColor(Color.RED);

            } else {
                TextView publish_status = (TextView) findViewById(R.id.res_publish_status);
                publish_status.setText("Status Error.");
                publish_status.setTextColor(Color.RED);

                Toast.makeText(getApplicationContext(), result_data.getPulish() + "--" + publis, Toast.LENGTH_SHORT).show();

            }


        }
    }

//    https://play.google.com/store/apps/details?id=sirfireys.rana.verifiem
    public void share(View view) {
        String urlShare = "Download VerifiEM from Play Store : \n \nhttps://play.google.com/store/apps/details?id=sirfireys.rana.verifiem";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, urlShare);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void aboutUS(View view) {
        Intent intent = new Intent(this, AboutUS.class);
        startActivity(intent);

//        Toast.makeText(getApplicationContext(),"About Us Called",Toast.LENGTH_SHORT).show();
    }

    public void scan(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onBackPressed() {
        if (back == true) {
            back = false;
            setContentView(R.layout.homescreen);
        } else {
            super.onBackPressed();
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        int responseCode;
        ProgressBar progressBar;

        Bitmap mIcon11 = BitmapFactory.decodeResource(getResources(), R.drawable.no_avatar);

        public DownloadImageTask(ImageView bmImage,ProgressBar progressBar) {
            this.bmImage = bmImage;
            this.progressBar=progressBar;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];

            try {
                URL url = new URL(urldisplay);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                responseCode = httpURLConnection.getResponseCode();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (responseCode != 404) {


                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

            } else {
                mIcon11 = BitmapFactory.decodeResource(getResources(), R.drawable.no_avatar);
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            progressBar.setVisibility(View.GONE);
            bmImage.setVisibility(View.VISIBLE);
            bmImage.setImageBitmap(result);
        }
    }


}
