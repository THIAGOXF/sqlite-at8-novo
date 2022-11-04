package br.ulbra.com

import java.io.Serializable


class Pessoa : Serializable {
    var id: Int? = null
    var nome: String? = null
    var cpf: String? = null
    var telefone: String? = null

    override fun toString(): String {
        return nome!!
    }
}
