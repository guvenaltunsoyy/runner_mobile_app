package Database;


import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.runner_mobile_app.Runner;

public class ClientDb extends SQLiteOpenHelper {
    private static final String DBNAME = "RunnerAppDb";
    private static String RunnerTableName = "Runners";
    private static final String IdColumn ="id";
    private static final String UsernameColumn = "username";
    private static final String nameColumn = "name";
    private static final String AgeColumn = "age";
    private static final String PhonenumberColumn = "phonenumber";
    private static final String MailColumn = "mail";
    private static final String RuncountColumn = "runcount";
    private static final String TitleColumn = "title";
    private static final String ImageColumn ="image";
    private static final String StateColumn = "state";
    private static final String CREATE_TABLE = "CREATE IF NOT EXISTS TABLE " +
            RunnerTableName +"(\n" +
            "  id int PRIMARY KEY,\n" +
            "  username text,\n" +
            "  name Text,\n" +
            "  age int,\n" +
            "  phonenumber text,\n" +
            "  mail text,\n" +
            "  runcount INT,\n" +
            "  title text,\n" +
            "  image text,\n" +
            "  state bool\n" +
            ");";


    public ClientDb(Context context){
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public long insertRunner(Runner runner){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IdColumn, runner.getId());
        values.put(UsernameColumn, runner.getUsername());
        values.put(nameColumn, runner.getName());
        values.put(AgeColumn, runner.getAge());
        values.put(PhonenumberColumn, runner.getPhonenumber());
        values.put(MailColumn, runner.getMail());
        values.put(RuncountColumn, runner.getRuncount());
        values.put(TitleColumn, runner.getTitle());
        values.put(ImageColumn, runner.getImage());
        values.put(StateColumn, runner.isState());

        long id = db.insert(RunnerTableName, null, values);
        db.close();

        return id;
    }
    public void GetRunner(){
        String sql = "select * from "+ RunnerTableName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        db.close();
    }
}
