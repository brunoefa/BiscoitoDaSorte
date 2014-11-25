package com.senai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.senai.biscoitodasorte.R;
import com.senai.database.FraseDao;
import com.senai.entity.Frase;

public class CadastrarActivity extends Activity {

	private FraseDao fraseDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar);
		fraseDao = new FraseDao(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cadastrar, menu);
		return true;
	}
	
	public void cancelar(View view) {
		iniciarSorteioDeFrase();
	}
	
	public void salvar(View view) {
		fraseDao.salvar(caputarFrase());
		mostrarMensagem("Frase salva com sucesso");
		limparCampo();
	}
	
	private Frase caputarFrase() {
		EditText etFrase = (EditText)findViewById(R.id.et_frase);
		return new Frase(etFrase.getText().toString());
	}
	
	private void limparCampo() {
		EditText etFrase = (EditText)findViewById(R.id.et_frase);
		etFrase.setText("");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.acao_sortear) {
			iniciarSorteioDeFrase();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void iniciarSorteioDeFrase() {
		Intent i = new Intent(this, FraseActivity.class);
		startActivity(i);
	}
	
	private void mostrarMensagem(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
