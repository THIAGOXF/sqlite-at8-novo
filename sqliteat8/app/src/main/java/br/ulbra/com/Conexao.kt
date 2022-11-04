package br.ulbra.com

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class Conexao(context: Context?) :
    SQLiteOpenHelper(context, name, null, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE pessoa(id integer primary key autoincrement,nome varchar(50),cpf varchar(50),telefone varchar(50))")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        private const val name = "banco.db"
        private const val version = 1
    }
}
