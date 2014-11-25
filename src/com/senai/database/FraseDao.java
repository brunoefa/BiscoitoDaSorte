package com.senai.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.senai.entity.Frase;

public class FraseDao {
	
	private SQLiteDatabase database;
	private AppDatabase dbHelper;
	private String[] colunas = { AppDatabase.COLUNA_ID, AppDatabase.COLUNA_FRASE };

	public FraseDao(Context context) {
		dbHelper = new AppDatabase(context);
		database = dbHelper.getDatabase();
	}

	public void open() throws SQLException {
		database = dbHelper.getDatabase();
	}

	public void close() {
		dbHelper.closeConnection();
	}

	public Frase salvar(Frase f) {
		Frase novaFrase = null;
		ContentValues values = new ContentValues();

		values.put(AppDatabase.COLUNA_FRASE, f.getFrase());

		long insertId = database.insert(AppDatabase.TABELA_FRASE, null, values);
		Cursor cursor = database.query(AppDatabase.TABELA_FRASE, colunas,
				AppDatabase.COLUNA_ID + " = " + insertId, null, null, null,
				null);

		if (cursor.moveToFirst()) {
			novaFrase = cursorToFrase(cursor);
		}
		cursor.close();
		return novaFrase;
	}

	public Frase buscar(Integer id) {
		Frase f = null;
		Cursor cursor = database.query(AppDatabase.TABELA_FRASE, colunas,
				AppDatabase.COLUNA_ID + " = ?",
				new String[] { String.valueOf(id) }, null, null, null);

		if (cursor.moveToFirst()) {
			f = cursorToFrase(cursor);
		}
		cursor.close();
		return f;
	}
	
	public ArrayList<Frase> buscarTodos() {
		Frase f = null;
		ArrayList<Frase> listaFrases = new ArrayList<Frase>();
		Cursor cursor = database.query(AppDatabase.TABELA_FRASE, colunas, null, null, null, null, null);

		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				f = cursorToFrase(cursor);
				listaFrases.add(f);
				cursor.moveToNext();
			}
		}
		
		cursor.close();
		return listaFrases;
	}

	public void deletar(Frase frase) {
		database.delete(AppDatabase.TABELA_FRASE, AppDatabase.COLUNA_ID + " = ?", new String[]{String.valueOf(frase.getId())});
	}
	
	public Integer count() {
		Integer count = 0;
		String query = "select count(*) from " + AppDatabase.TABELA_FRASE + ";";
		Cursor cursor = database.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			count = cursor.getInt(0);
		}
		cursor.close();
		return count;
	}

	private Frase cursorToFrase(Cursor cursor) {
		Frase f = new Frase(cursor.getLong(0), cursor.getString(1));
		return f;
	}
}
