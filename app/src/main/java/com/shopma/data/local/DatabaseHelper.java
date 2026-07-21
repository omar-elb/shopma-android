package com.shopma.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "shopma.db";
    private static final int DB_VERSION = 1;
    private static DatabaseHelper instance;

    public static final String TABLE_PANIER = "panier";
    public static final String COL_PANIER_ID = "id";
    public static final String COL_PANIER_PRODUCT_ID = "product_id";
    public static final String COL_PANIER_TITLE = "title";
    public static final String COL_PANIER_PRICE = "price";
    public static final String COL_PANIER_QUANTITY = "quantity";

    public static final String TABLE_COMMANDES = "commandes";
    public static final String COL_CMD_ID = "id";
    public static final String COL_CMD_DATE = "date";
    public static final String COL_CMD_NB_ARTICLES = "nb_articles";
    public static final String COL_CMD_MONTANT_TOTAL = "montant_total";
    public static final String COL_CMD_STATUT = "statut";

    private DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) instance = new DatabaseHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PANIER + " (" +
                COL_PANIER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PANIER_PRODUCT_ID + " INTEGER NOT NULL, " +
                COL_PANIER_TITLE + " TEXT NOT NULL, " +
                COL_PANIER_PRICE + " REAL NOT NULL, " +
                COL_PANIER_QUANTITY + " INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_COMMANDES + " (" +
                COL_CMD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CMD_DATE + " TEXT NOT NULL, " +
                COL_CMD_NB_ARTICLES + " INTEGER NOT NULL, " +
                COL_CMD_MONTANT_TOTAL + " REAL NOT NULL, " +
                COL_CMD_STATUT + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANIER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDES);
        onCreate(db);
    }

    // ---------- PANIER ----------

    public long addToCart(long productId, String title, double price) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_PANIER, null,
                COL_PANIER_PRODUCT_ID + "=?", new String[]{String.valueOf(productId)},
                null, null, null);

        long result;
        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_PANIER_ID));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PANIER_QUANTITY));
            ContentValues values = new ContentValues();
            values.put(COL_PANIER_QUANTITY, quantity + 1);
            db.update(TABLE_PANIER, values, COL_PANIER_ID + "=?", new String[]{String.valueOf(id)});
            result = id;
        } else {
            ContentValues values = new ContentValues();
            values.put(COL_PANIER_PRODUCT_ID, productId);
            values.put(COL_PANIER_TITLE, title);
            values.put(COL_PANIER_PRICE, price);
            values.put(COL_PANIER_QUANTITY, 1);
            result = db.insert(TABLE_PANIER, null, values);
        }
        cursor.close();
        return result;
    }

    public List<CartRow> getCart() {
        List<CartRow> items = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(TABLE_PANIER, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            items.add(new CartRow(
                    cursor.getLong(cursor.getColumnIndexOrThrow(COL_PANIER_ID)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(COL_PANIER_PRODUCT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PANIER_TITLE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PANIER_PRICE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_PANIER_QUANTITY))
            ));
        }
        cursor.close();
        return items;
    }

    public void updateQuantity(long cartId, int quantity) {
        ContentValues values = new ContentValues();
        values.put(COL_PANIER_QUANTITY, quantity);
        getWritableDatabase().update(TABLE_PANIER, values, COL_PANIER_ID + "=?", new String[]{String.valueOf(cartId)});
    }

    public void removeItem(long cartId) {
        getWritableDatabase().delete(TABLE_PANIER, COL_PANIER_ID + "=?", new String[]{String.valueOf(cartId)});
    }

    public void clearCart() {
        getWritableDatabase().delete(TABLE_PANIER, null, null);
    }

    public int getCartItemCount() {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT SUM(" + COL_PANIER_QUANTITY + ") FROM " + TABLE_PANIER, null);
        int count = 0;
        if (cursor.moveToFirst() && !cursor.isNull(0)) count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    // ---------- COMMANDES ----------

    public long addOrder(String date, int nbArticles, double montantTotal, String statut) {
        ContentValues values = new ContentValues();
        values.put(COL_CMD_DATE, date);
        values.put(COL_CMD_NB_ARTICLES, nbArticles);
        values.put(COL_CMD_MONTANT_TOTAL, montantTotal);
        values.put(COL_CMD_STATUT, statut);
        return getWritableDatabase().insert(TABLE_COMMANDES, null, values);
    }

    public List<OrderRow> getOrderHistory() {
        List<OrderRow> orders = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(TABLE_COMMANDES, null, null, null, null, null, COL_CMD_ID + " DESC");
        while (cursor.moveToNext()) {
            orders.add(new OrderRow(
                    cursor.getLong(cursor.getColumnIndexOrThrow(COL_CMD_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_CMD_DATE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_CMD_NB_ARTICLES)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COL_CMD_MONTANT_TOTAL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_CMD_STATUT))
            ));
        }
        cursor.close();
        return orders;
    }

    public static class CartRow {
        public final long id, productId;
        public final String title;
        public final double price;
        public final int quantity;
        public CartRow(long id, long productId, String title, double price, int quantity) {
            this.id = id; this.productId = productId; this.title = title;
            this.price = price; this.quantity = quantity;
        }
    }

    public static class OrderRow {
        public final long id;
        public final String date, statut;
        public final int nbArticles;
        public final double montantTotal;
        public OrderRow(long id, String date, int nbArticles, double montantTotal, String statut) {
            this.id = id; this.date = date; this.nbArticles = nbArticles;
            this.montantTotal = montantTotal; this.statut = statut;
        }
    }
}
