package br.ulbra.com

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class ListarPessoaActivity : AppCompatActivity() {
    private var listview: ListView? = null
    private var dao: PessoaDAO? = null
    private var pessoas: MutableList<Pessoa>? = null
    private val pessoasFiltrados: MutableList<Pessoa> = ArrayList<Pessoa>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_pessoa_activity)
        listview = findViewById(R.id.lvPessoas)
        dao = PessoaDAO(this)
        pessoas = dao.obterTodos()
        pessoasFiltrados.addAll(pessoas!!)
        val adaptador: ArrayAdapter<Pessoa> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_list_item_1, pessoasFiltrados)
        listview.setAdapter(adaptador)
        registerForContextMenu(listview)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val i = menuInflater
        i.inflate(R.menu.menu_principal, menu)
        val sv = menu.findItem(R.id.app_bar_search).actionView as SearchView
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                procuraPessoa(s)
                return false
            }
        })
        return true
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val i = menuInflater
        i.inflate(R.menu.menu_contexto, menu)
    }

    fun procuraPessoa(nome: String) {
        pessoasFiltrados.clear()
        for (p in pessoas) {
            if (p.getNome().toLowerCase().contains(nome.lowercase(Locale.getDefault()))) {
                pessoasFiltrados.add(a)
            }
        }
        listview!!.invalidateViews()
    }

    fun cadastrar(item: MenuItem?) {
        val it = Intent(this, MainActivity::class.java)
        startActivity(it)
    }

    fun excluir(item: MenuItem) {
        val menuInfo = item.menuInfo as AdapterContextMenuInfo
        val pessoaExcluir: Pessoa = pessoasFiltrados[menuInfo.position]
        val dialog = AlertDialog.Builder(this).setTitle("Atenção")
            .setMessage("Tem certeza que deseja excluir" + pessoaExcluir.getNome().toString() + "?")
            .setNegativeButton("Nao", null).setPositiveButton(
                "Sim"
            ) { dialog, which ->
                pessoasFiltrados.remove(pessoaExcluir)
                pessoas!!.remove(pessoaExcluir)
                dao.excluir(pessoaExcluir)
                listview!!.invalidateViews()
            }.create()
        dialog.show()
    }

    fun atualizar(item: MenuItem) {
        val menuInfo = item.menuInfo as AdapterContextMenuInfo
        val pessoaAtualizar: Pessoa = pessoasFiltrados[menuInfo.position]
        val it = Intent(this, MainActivity::class.java)
        it.putExtra("pessoa", pessoaAtualizar)
        startActivity(it)
    }

    public override fun onResume() {
        super.onResume()
        pessoas = dao.obterTodos()
        pessoasFiltrados.clear()
        pessoasFiltrados.addAll(pessoas!!)
        listview!!.invalidateViews()
    }
}