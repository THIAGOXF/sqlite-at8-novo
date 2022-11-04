package br.ulbra.com

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var edNome: EditText? = null
    private var edCpf: EditText? = null
    private var edTelefone: EditText? = null
    private var btSalvar: Button? = null
    private var dao: PessoaDAO? = null
    private var pessoa: Pessoa? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edNome = findViewById(R.id.edNome)
        edCpf = findViewById(R.id.edCpf)
        edTelefone = findViewById(R.id.edTelefone)
        btSalvar = findViewById(R.id.btSalvar)
        dao = PessoaDAO(this)
        val it = intent
        if (it.hasExtra("pessoa")) {
            pessoa = it.getSerializableExtra("pessoa") as Pessoa?
            edNome.setText(pessoa.getNome())
            edCpf.setText(pessoa.getCpf())
            edTelefone.setText(pessoa.getTelefone())
        }
    }

    fun salvar(view: View?) {
        if (pessoa == null) {
            val pessoa = Pessoa()
            pessoa.setNome(edNome!!.text.toString())
            pessoa.setCpf(edCpf!!.text.toString())
            pessoa.setTelefone(edTelefone!!.text.toString())
            val id: Long = dao.inserir(pessoa)
            Toast.makeText(this, "Pessoa inserida no ID de nº:$id", Toast.LENGTH_LONG).show()
        } else {
            pessoa.setNome(edNome!!.text.toString())
            pessoa.setCpf(edCpf!!.text.toString())
            pessoa.setTelefone(edTelefone!!.text.toString())
            dao.atualizar(pessoa)
            Toast.makeText(
                this,
                pessoa.getNome() + ", atualizado com sucesso !!!",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}