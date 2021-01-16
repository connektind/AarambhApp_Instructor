package com.example.aarambhapp_instructor.multipath;

import android.content.Context;
import android.content.SharedPreferences;

public class AarambhSharedPreference {
    public static final String PREFS_NAME = "Aarambh App Instructor";

    public static final String USER_TOKEN="user_token";

    public static final String SCHOOL_ID="School_ID";
    public static  final String TEACHER_ID="Teacher_id";
    public static final String FIREBASE_TOKEN="FireBase_token";
    public static  final String TEACHER_NAME="Teacher_name";


    public static void saveFireBase_tokenToPreference(Context context, String FireBase_token){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(FIREBASE_TOKEN,FireBase_token);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadFireBase_tokenFromPreference(Context ctx){
        String FireBase_token="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            FireBase_token = pref.getString(FIREBASE_TOKEN,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return FireBase_token;
    }
    public static void saveTeacherNameToPreference(Context context, String TeacherName){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(TEACHER_NAME,TeacherName);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadTeacherNameFromPreference(Context ctx){
        String TeacherName="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            TeacherName = pref.getString(TEACHER_NAME,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return TeacherName;
    }



    public static void logoutData(Context context){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void saveUserTokenToPreference(Context context, String userToken){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(USER_TOKEN,userToken);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadUserTokenFromPreference(Context ctx){
        String userToken="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            userToken = pref.getString(USER_TOKEN,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return userToken;
    }

    public static void saveSchoolIdToPreference(Context context, String schoolId){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(SCHOOL_ID, schoolId);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadSchoolIdFromPreference(Context ctx){
        String schoolId="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            schoolId = pref.getString(SCHOOL_ID,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return schoolId;
    }
    public static void saveTeacherIDToPreference(Context context, String teacher_id){
        try{
            SharedPreferences.Editor editor =  context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(TEACHER_ID,teacher_id);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String loadTeacherIdFromPreference(Context ctx){
        String userToken="";
        try{
            SharedPreferences pref = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            userToken = pref.getString(USER_TOKEN,"NA");
        }catch (Exception e){
            e.printStackTrace();
        }
        return userToken;
    }

}
