package com.senai.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.senai.biscoitodasorte.R;
import com.senai.database.FraseDao;
import com.senai.entity.Frase;

public class ListagemActivity extends ListActivity {

	private ArrayList<Frase> listaFrases = new ArrayList<Frase>();
	private Frase item;
	private FraseDao fraseDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fraseDao = new FraseDao(this);
		recarregarLista();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Frase frase = (Frase)l.getItemAtPosition(position);
		this.item = frase;
		confirmarExclusao();
	}

	private void recarregarLista() {
		listaFrases = fraseDao.buscarTodos();
		ArrayAdapter<Frase> listaAdapter = new ArrayAdapter<Frase>(this, android.R.layout.simple_list_item_checked, listaFrases);
		this.setListAdapter(listaAdapter);
	}
	
	private void removeItemDaLista() {
		fraseDao.deletar(item);
		mostrarMensagem("Item removido: " + item.getId());
	}
	
	private void mostrarMensagem(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
	
	private void confirmarExclusao() {
	    AlertDialog.Builder alert = new AlertDialog.Builder(this);
	    alert.setTitle("Tem certeza que deseja execluir?");
	    
	    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	removeItemDaLista();
	        	recarregarLista();
	        }
	    });
	    alert.setNegativeButton("Não",null);
	    alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.listagem, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
