package com.example.aarambhapp_instructor.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.aarambhapp_instructor.R;
import com.example.aarambhapp_instructor.adapter.TeacherClassAdapter;
import com.example.aarambhapp_instructor.model.TeacherClassModel;
import com.example.aarambhapp_instructor.multipath.AarambhSharedPreference;
import com.example.aarambhapp_instructor.util.CommonUtilities;
import com.example.aarambhapp_instructor.util.Global;
import com.example.aarambhapp_instructor.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {
    RecyclerView teacher_class_recyclerview;
    ArrayList<TeacherClassModel>teacherClassModelArrayList;
    TeacherClassAdapter teacherClassAdapter;
    ImageView logout_icon;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
        listner();
        getDashBoardData();
    }

    private void getDashBoardData() {
        {
            if (!CommonUtilities.isOnline(DashboardActivity.this)) {
                Toast.makeText(DashboardActivity.this, "Please On Your Internet Connection", Toast.LENGTH_LONG).show();
                return;
            }
            Log.e("topic_id", AarambhSharedPreference.loadSchoolIdFromPreference(this));

            String url = Global.WEBBASE_URL + "getSchoolLiveData?schoolId=" + AarambhSharedPreference.loadSchoolIdFromPreference(this);
            Log.e("url", url);
            final String string_json = "Result";
            Log.e("string_json", string_json);
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String res = response.toString();
                        parseResponseDashBoard(res);
                        Log.e("res", res);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
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
                                parseResponseDashBoard(res.toString());

                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            }
                        }
                    }
                }) {
                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("schoolId", AarambhSharedPreference.loadSchoolIdFromPreference(DashboardActivity.this));
                        return params;
                    }

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
                        headers.put("Authorization", "Bearer " + AarambhSharedPreference.loadUserTokenFromPreference(DashboardActivity.this));
                        return headers;
                    }
                };
                VolleySingleton.getInstance(DashboardActivity.this).addToRequestQueue(stringRequest, string_json);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parseResponseDashBoard(String response) {

        Log.e("class_res", response);

        try {
            JSONObject jsonObject1 = new JSONObject(response);
            String status = jsonObject1.getString("status");
            boolean success = jsonObject1.getBoolean("success");
            if (success == true) {
                String error = jsonObject1.getString("message");
                //  Toast.makeText(this, error + "", Toast.LENGTH_LONG).show();
            } else if (success == false) {
                String Message = jsonObject1.getString("message");
                JSONArray jsonArray = jsonObject1.getJSONArray("Data");
                if (Message.equalsIgnoreCase("Data Found.")) {
//                    if (jsonArray.length()==0){
//                        pratices_test.setVisibility(View.VISIBLE);
//                        pratices_test.setText("No Record Found");
//                        //  Toast.makeText(PraticesListActivity.this,"No Record Found",Toast.LENGTH_LONG).show();
//
//                    }else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.e("chapter_length", String.valueOf(i));
                            Log.e("json", String.valueOf(jsonObject));
                            String LiveId = jsonObject.getString("LiveId");
                            String ClassId = jsonObject.getString("ClassId");
                            String SchoolId = jsonObject.getString("SchoolId");
                            String LiveClass = jsonObject.getString("LiveClass");

                            String StatusId = jsonObject.getString("StatusId");
                            String CreatedById = jsonObject.getString("CreatedById");
                            String ModifiedById = jsonObject.getString("ModifiedById");
                            String CreationDate = jsonObject.getString("CreationDate");
                            String ModificationDate = "";
                            try {
                                ModificationDate = jsonObject.getString("ModificationDate");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String studentclass = jsonObject.getString("studentclass");

                            TeacherClassModel tcm = new TeacherClassModel(LiveId, ClassId, SchoolId, LiveClass, StatusId, CreatedById, ModifiedById, CreationDate, ModificationDate,studentclass);
                            teacherClassModelArrayList.add(tcm);
                        }

                    teacherClassAdapter = new TeacherClassAdapter(this, teacherClassModelArrayList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
                    teacher_class_recyclerview.setLayoutManager(linearLayoutManager);
                    teacher_class_recyclerview.setAdapter(teacherClassAdapter);

                }else{
                    Toast.makeText(DashboardActivity.this,""+Message,Toast.LENGTH_LONG).show();

                }

//                } else {
//                    Toast.makeText(this, Message + "", Toast.LENGTH_LONG).show();
//
//                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void init() {
        teacherClassModelArrayList=new ArrayList<>();
        teacher_class_recyclerview=findViewById(R.id.teacher_class_recyclerview);
        logout_icon=findViewById(R.id.logout_icon);

    }

    private void listner() {
        logout_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setView(R.layout.exitpopup);
                final AlertDialog alert = builder.create();
                alert.show();
                //dialog.getWindow().setAttributes(windo);
                TextView dialog_cancel = (TextView) alert.findViewById(R.id.dialog_cancel);
                TextView dialog_ok = (TextView) alert.findViewById(R.id.dialog_ok);
                TextView dialog_exit = (TextView) alert.findViewById(R.id.tv_exit);
                TextView exitinformation = (TextView) alert.findViewById(R.id.exitinformation);
                LinearLayout ll_cancel = (LinearLayout) alert.findViewById(R.id.ll_cancel);
                LinearLayout ll_ok = (LinearLayout) alert.findViewById(R.id.ll_ok);
                exitinformation.setText("Do you want to exit from this App?");

                ll_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
                ll_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        AarambhSharedPreference.saveClassIdToPreference(DashBoardActivity.this, "NA");
//                        AarambhSharedPreference.saveStudentNameToPreference(DashBoardActivity.this, "NA");
//                        AarambhSharedPreference.saveStudentProfileToPreference(DashBoardActivity.this, "NA");
//                        AarambhSharedPreference.saveUserTokenToPreference(DashBoardActivity.this, "NA");
                        AarambhSharedPreference.logoutData(DashboardActivity.this);

                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            finish();
            finishAffinity();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(getApplicationContext(), "Tap again to exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}