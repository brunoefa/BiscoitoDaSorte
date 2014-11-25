package com.senai.activity;

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sena.util.LoadFrases;
import com.senai.biscoitodasorte.R;
import com.senai.database.FraseDao;
import com.senai.entity.Frase;

public class FraseActivity extends Activity {

	private FraseDao fraseDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frase);
		fraseDao = new FraseDao(this);
		popularBanco();
	}

	private void popularBanco() {
		Integer count = fraseDao.count();
		if (count == 0) {
			LoadFrases loadFrases = new LoadFrases();
			List<String> listaFrases = loadFrases.getListaFrases();
			for (String frase : listaFrases) {
				Frase f = new Frase(frase);
				fraseDao.salvar(f);
			}
		}
	}
	
	public void mostrarFrase(View view) {
		int id = gerarIdAleatorio();
		Frase frase = fraseDao.buscar(id);
		TextView tvFrase = (TextView)findViewById(R.id.tv_frase);
		tvFrase.setText(frase.getFrase());
	}
	
	private int gerarIdAleatorio() {
		int minimo = 1;
		int maximo = fraseDao.count();;

	    Random random = new Random();
	    int randomNum = random.nextInt((maximo - minimo) + 1) + minimo;

	    return randomNum;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.frase, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.acao_cadastrar) {
			Intent i = new Intent(this, CadastrarActivity.class);
			startActivity(i);
		} else if (id == R.id.acao_listar) {
			Intent i = new Intent(this, ListagemActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
}
