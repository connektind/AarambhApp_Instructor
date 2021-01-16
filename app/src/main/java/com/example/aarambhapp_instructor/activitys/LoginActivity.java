package com.example.aarambhapp_instructor.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aarambhapp_instructor.R;
import com.example.aarambhapp_instructor.multipath.AarambhSharedPreference;
import com.example.aarambhapp_instructor.util.CommonUtilities;
import com.example.aarambhapp_instructor.util.Global;
import com.example.aarambhapp_instructor.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
Button btn_login;
EditText login_mobile_no,login_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        listner();
    }

    private void init()
    {
        login_mobile_no=findViewById(R.id.login_mobile_no);
        login_password=findViewById(R.id.login_password);
        
        btn_login=findViewById(R.id.btn_login);
    }

    private void listner() {
        
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mobile = login_mobile_no.getText().toString().trim();
                final String password = login_password.getText().toString().trim();
                if (mobile.isEmpty()) {
                    login_mobile_no.setError("Please Enter Username/Mobile Number");
                    login_mobile_no.requestFocus();
                } else if (password.length() < 6) {
                    login_password.setError("Please Enter Password");
                    login_password.requestFocus();
                } else {
                    btn_login.setClickable(false);
//                    Intent intent=new Intent(LoginActivity.this, ParentDashBoard.class);
//                    startActivity(intent);
                    getLogin(mobile, password);
                }
            }
        });
    }

    private void getLogin(final String mobile, final String password) {
        if (!CommonUtilities.isOnline(LoginActivity.this)) {
            Toast.makeText(LoginActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
//        progressdialog = new ProgressDialog(this);
//        progressdialog.setCancelable(false);
//        progressdialog.setMessage("Loading...");
//        progressdialog.setTitle("Wait...");
//        progressdialog.show();

        String url = Global.WEBBASE_URL + "schoolLogin";

        JSONObject params = new JSONObject();
        String mRequestBody = "";

        try {

            params.put("username", mobile);
            params.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRequestBody = params.toString();
        Log.e("Request Body", mRequestBody);
        final String finalMRequestBody = mRequestBody;

        btn_login.setClickable(true);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String res = response.toString();
                parseResponseLoginUser(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //pd.dismiss();
                NetworkResponse response = error.networkResponse;

                Log.e("com.Aarambh", "error response " + response);

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.e("mls", "VolleyError TimeoutError error or NoConnectionError");
                } else if (error instanceof AuthFailureError) {                    //TODO
                    Log.e("mls", "VolleyError AuthFailureError");
                } else if (error instanceof ServerError) {
                    Log.e("mls", "VolleyError ServerError");
                } else if (error instanceof NetworkError) {
                    Log.e("mls", "VolleyError NetworkError");
                } else if (error instanceof ParseError || error instanceof VolleyError) {
                    Log.e("mls", "VolleyError TParseError");
                    Log.e("Volley Error", error.toString());
                }
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        // progressDialog.show();
                        parseResponseLoginUser(response.toString());

                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    }
                }

            }

        }) {

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                String json;
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        json = new String(volleyError.networkResponse.data,
                                HttpHeaderParser.parseCharset(volleyError.networkResponse.headers));
                    } catch (UnsupportedEncodingException e) {
                        return new VolleyError(e.getMessage());
                    }
                    return new VolleyError(json);
                }
                return volleyError;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }

            // Adding request to request queue
            @Override
            public byte[] getBody() {
                try {
                    return finalMRequestBody == null ? null : finalMRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", finalMRequestBody, "utf-8");
                    return null;
                }
            }

        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                Global.WEBSERVICE_TIMEOUT_VALUE_IN_MILLIS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        jsonObjectRequest.setShouldCache(false);
        // Adding request to request queue
        VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(jsonObjectRequest);
    }

    private void parseResponseLoginUser(String response) {
        try {
            //getting the whole json object from the response 4878704040
            JSONObject obj = new JSONObject(response);
            Log.e("login_resp", String.valueOf(obj));
            int status = obj.getInt("status");
            btn_login.setClickable(true);
            boolean success = obj.getBoolean("success");

            if (success == false) {
                String error = obj.getString("error");
                if (error.equalsIgnoreCase("Username does not exits.")) {
                    Toast.makeText(this, error + "", Toast.LENGTH_LONG).show();
                   // progressdialog.dismiss();
                } else if (error.equalsIgnoreCase("Password Mismatch.")) {
                    Toast.makeText(this, error + "", Toast.LENGTH_LONG).show();
                    //progressdialog.dismiss();
                } else {
                    Toast.makeText(this, error + "", Toast.LENGTH_LONG).show();
                    //progressdialog.dismiss();
                }

            } else if (success == true) {
                String msg = obj.getString("Message");
                String token = "";
                try {

                    token = obj.getString("Token");
                    AarambhSharedPreference.saveUserTokenToPreference(this,token);
                    Log.e("token_user", token);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (msg.equalsIgnoreCase("Login Successfull")) {
                    try {
                            JSONArray obj_array = obj.getJSONArray("Data");
                            Log.e("json_array:------", obj_array.toString());
                            for (int i = 0; i < obj_array.length(); i++) {
                                JSONObject jsonObject = obj_array.getJSONObject(i);
                                String TeacherId = jsonObject.getString("TeacherId");
                                AarambhSharedPreference.saveTeacherIDToPreference(this,TeacherId);
                                String TeacherName = jsonObject.getString("TeacherName");
                                AarambhSharedPreference.saveTeacherNameToPreference(this,TeacherName);
                                String TeacherMobile = jsonObject.getString("TeacherMobile");
                                String Username = jsonObject.getString("Username");
                                String Password = jsonObject.getString("Password");
                                String TeacherEmail = jsonObject.getString("TeacherEmail");
                                String TeacherAddress = jsonObject.getString("TeacherAddress");
                                String TeacherDOB = jsonObject.getString("TeacherDOB");
                                String StatusId = jsonObject.getString("StatusId");
                                String SchoolId = jsonObject.getString("SchoolId");
                                AarambhSharedPreference.saveSchoolIdToPreference(this,SchoolId);
                            }
                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(intent);

                            //getYoutubeDataUrl();
                            //getChannelVideos();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                   // Toast.makeText(this, msg + "", Toast.LENGTH_LONG).show();
                  //  progressdialog.dismiss();
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}