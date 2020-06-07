package com.korzub.game1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME="battleships.db";

    public static final String PROFILE_TABLE_NAME="profile";
    public static final String SETTINGS_TABLE_NAME="settings";
    public static final String STATISTICS_TABLE_NAME="statistics";

    public static final String PROFILE_COL_1="Id_Profile";
    public static final String PROFILE_COL_2="Name";
    public static final String PROFILE_COL_3="Wins";
    public static final String PROFILE_COL_4="Losses";

    public static final String SETTINGS_COL1="Id_Settings";
    public static final String SETTINGS_COL2="Difficulty";

    public static final String STATISTICS_COL1="Id_Statistics";
    public static final String STATISTICS_COL2="Turns";
    public static final String STATISTICS_COL3="Difficulty";
    public static final String STATISTICS_COL4="Outcome";

    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String CREATE_PROFILE_TABLE="CREATE TABLE "+PROFILE_TABLE_NAME+"("+PROFILE_COL_1+" INTEGER PRIMARY KEY ,"+
                PROFILE_COL_2+" TEXT,"+
                PROFILE_COL_3+" INTEGER,"+
                PROFILE_COL_4+" INTEGER"+
                ")";

        String CREATE_SETTINGS_TABLE="CREATE TABLE "+SETTINGS_TABLE_NAME+"("+SETTINGS_COL1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                SETTINGS_COL2+" INTEGER "+
                ")";

        String CREATE_STATISTICS_TABLE="CREATE TABLE "+STATISTICS_TABLE_NAME+"("+STATISTICS_COL1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                STATISTICS_COL2+" INTEGER,"+
                STATISTICS_COL3+" TEXT,"+
                STATISTICS_COL4+" TEXT"+
                ")";

        sqLiteDatabase.execSQL(CREATE_PROFILE_TABLE);
        sqLiteDatabase.execSQL(CREATE_SETTINGS_TABLE);
        sqLiteDatabase.execSQL(CREATE_STATISTICS_TABLE);




        ContentValues profileContentValues=new ContentValues();
        profileContentValues.put(PROFILE_COL_2,"Player");
        profileContentValues.put(PROFILE_COL_3,0);
        profileContentValues.put(PROFILE_COL_4,0);

        sqLiteDatabase.insert(PROFILE_TABLE_NAME,null,profileContentValues);



        ContentValues settingsContentValues=new ContentValues();
        settingsContentValues.put(SETTINGS_COL2,0);

        sqLiteDatabase.insert(SETTINGS_TABLE_NAME,null,settingsContentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+PROFILE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SETTINGS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+STATISTICS_TABLE_NAME);


        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public ArrayList<String> getProfileData(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={PROFILE_COL_2,PROFILE_COL_3,PROFILE_COL_4};

        Cursor cursor=db.query(PROFILE_TABLE_NAME, columns,PROFILE_COL_1+"=?",
                new String[]{String.valueOf(id)},null,null,null,null);


        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        ArrayList<String> profileData=new ArrayList<String>();

        int count=cursor.getColumnCount();
        for(int i=0;i<count;i++)
        {
            String value=cursor.getString(i);
            profileData.add(value);
        }

        db.close();

        return profileData;
    }

    public void updateProfileName(String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PROFILE_COL_2, name);

        db.update(PROFILE_TABLE_NAME, values, PROFILE_COL_1 + "=" + 1, null);
        db.close();

    }
    public void addWin(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        String[] columns={PROFILE_COL_3};

        Cursor cursor=db.query(PROFILE_TABLE_NAME, columns,PROFILE_COL_1+"=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        String value=cursor.getString(0);
        int wins=Integer.parseInt(value);
        wins++;

        ContentValues values = new ContentValues();
        values.put(PROFILE_COL_3, Integer.toString(wins));

        db.update(PROFILE_TABLE_NAME, values, PROFILE_COL_1 + "=" + 1, null);
        db.close();
    }

    public void addLoss(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        String[] columns={PROFILE_COL_4};

        Cursor cursor=db.query(PROFILE_TABLE_NAME, columns,PROFILE_COL_1+"=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        String value=cursor.getString(0);
        int losses=Integer.parseInt(value);
        losses++;

        ContentValues values = new ContentValues();
        values.put(PROFILE_COL_4, Integer.toString(losses));

        db.update(PROFILE_TABLE_NAME, values, PROFILE_COL_1 + "=" + id, null);
        db.close();
    }

    public int getDifficulty(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();

        String[] columns={SETTINGS_COL2};

        Cursor cursor=db.query(SETTINGS_TABLE_NAME, columns,SETTINGS_COL1+"=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        String value=cursor.getString(0);
        int difficulty=Integer.parseInt(value);

        db.close();
        return difficulty;
    }

    public void saveDifficulty(int id,int difficulty)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SETTINGS_COL2, Integer.toString(difficulty));

        db.update(SETTINGS_TABLE_NAME, values, SETTINGS_COL1 + "=" + id, null);
        db.close();
    }
    public void saveGameResult(int turns,String difficulty,String outcome)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATISTICS_COL2,turns);
        values.put(STATISTICS_COL3,difficulty);
        values.put(STATISTICS_COL4,outcome);

        db.insert(STATISTICS_TABLE_NAME,null,values);

        db.close();
    }
    public ArrayList<GameResult> getAllGameResults()
    {
        ArrayList<GameResult> gameResults=new ArrayList<>();

        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+STATISTICS_TABLE_NAME+" ORDER BY "+ STATISTICS_COL1+" DESC";

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            do {
                GameResult gameResult=new GameResult(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));

                gameResults.add(gameResult);

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();


        return gameResults;
    }
}
