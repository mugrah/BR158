package horusgeo.br158;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBEndPerson extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "br158.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE = "end_person";

    private static final String ID = "id";
    private static final String RUA = "rua";
    private static final String NUM = "num";
    private static final String COMPL = "compl";
    private static final String BAIRRO = "bairro";
    private static final String CEP = "cep";
    private static final String MUNICIPIO = "municipio";
    private static final String UF_1 = "uf_1";
    private static final String COMARCA = "comarca_";
    private static final String UF_2 = "uf_2";
    private static final String P_REF = "p_ref";

    public DBEndPerson(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROP_TABLE = "CREATE TABLE " + TABLE + "(" +
                ID + " INTEGER NOT NULL UNIQUE," +
                RUA + " TEXT," +
                NUM + " TEXT," +
                COMPL + " TEXT," +
                BAIRRO + " TEXT," +
                CEP + " TEXT," +
                MUNICIPIO + " TEXT," +
                UF_1 + " TEXT," +
                COMARCA + " TEXT," +
                UF_2 + " TEXT," +
                P_REF + " TEXT" +
                ")";
        db.execSQL(CREATE_PROP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void addEndPerson(Endereco cadastro){

        ContentValues values = new ContentValues();

        values.put(ID, cadastro.getId());
        values.put(RUA, cadastro.getRua());
        values.put(NUM, cadastro.getNum());
        values.put(COMPL, cadastro.getCompl());
        values.put(BAIRRO, cadastro.getBairro());
        values.put(CEP, cadastro.getCep());
        values.put(MUNICIPIO, cadastro.getMunicipio());
        values.put(UF_1, cadastro.getUf1());
        values.put(COMARCA, cadastro.getComarca());
        values.put(UF_2, cadastro.getUf2());
        values.put(P_REF, cadastro.getpRef());

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE + " WHERE " + ID + " = " + cadastro.getId();
        Cursor cursor = db.rawQuery(query, null);

        try{
            if (cursor.getCount() > 0){
                db.update(TABLE, values, ID + "=" + cadastro.getId(), null);
            }else{
                db.insert(TABLE, null, values);
            }


        }finally {
            cursor.close();
        }
        db.close();

    }

    public void deleteEndPerson(Endereco cadastro){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, ID + " = " + cadastro.getId(), null);
        db.close();

    }

    public Endereco getEndPerson(Integer id){

        Endereco endereco = new Endereco();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE + " WHERE " + ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        try{
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                endereco.setId(cursor.getInt(0));
                endereco.setRua(cursor.getString(1));
                endereco.setNum(cursor.getString(2));
                endereco.setCompl(cursor.getString(3));
                endereco.setBairro(cursor.getString(4));
                endereco.setCep(cursor.getString(5));
                endereco.setMunicipio(cursor.getString(6));
                endereco.setUf1(cursor.getString(7));
                endereco.setComarca(cursor.getString(8));
                endereco.setUf2(cursor.getString(9));
                endereco.setpRef(cursor.getString(10));
            }
        }finally{
            cursor.close();
        }

        db.close();

        return endereco;

    }


}
