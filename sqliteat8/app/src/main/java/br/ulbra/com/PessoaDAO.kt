package br.ulbra.com

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase


class PessoaDAO(context: Context?) {
    private val conexao: Conexao
    private val banco: SQLiteDatabase

    init {
        conexao = Conexao(context)
        banco = conexao.writableDatabase
    }

    fun inserir(pessoa: Pessoa): Long {
        val values = ContentValues()
        values.put("nome", pessoa.getNome())
        values.put("cpf", pessoa.getCpf())
        values.put("telefone", pessoa.getTelefone())
        return banco.insert("pessoa", null, values)
    }

    fun obterTodos(): List<Pessoa> {
        val pessoas: MutableList<Pessoa> = ArrayList()
        val cursor = banco.query(
            "pessoa",
            arrayOf("id", "nome", "cpf", "telefone"),
            null,
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val p = Pessoa()
            p.setId(cursor.getInt(0))
            p.setNome(cursor.getString(1))
            p.setCpf(cursor.getString(2))
            p.setTelefone(cursor.getString(3))
            pessoas.add(p)
        }
        return pessoas
    }

    fun atualizar(pessoa: Pessoa) {
        val values = ContentValues()
        values.put("nome", pessoa.getNome())
        values.put("cpf", pessoa.getCpf())
        values.put("telefone", pessoa.getTelefone())
        banco.update("pessoa", values, "id = ?", arrayOf(pessoa.getId().toString()))
    }

    fun excluir(pessoa: Pessoa) {
        banco.delete("pessoa", "id = ?", arrayOf(pessoa.getId().toString()))
    }
}
