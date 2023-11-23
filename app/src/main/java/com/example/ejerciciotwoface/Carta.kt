package com.example.ejerciciotwoface

class Carta  {
        private var activo: Boolean = false
        private  var descubierto: Boolean = false
        private var valor: Int = 0
        private var imagen: Int = -1
        constructor( valor: Int, imagen: Int) {
            this.valor = valor
            this.imagen = imagen
        }
    constructor() {
    }
    fun getValor(): Int {
        return valor
    }
    fun getImagen(): Int {
        return imagen
    }
    fun setDescubierto() {
        this.descubierto = true
        this.activo=true
    }
    fun getDescubierto(): Boolean {
        return descubierto
    }

    fun getActivo(): Boolean {
        return activo
    }
    fun setActivo(b: Boolean) {
        this.activo = b
    }
    // Método clone para crear una copia de la carta
     fun clone(): Carta {
        return Carta( this.valor, this.imagen!!)
    }
    fun setImagen(imagen: Int) {
        this.imagen = imagen
    }
    fun getImagenActual(): Int {
        return (if (this.activo) this.imagen else R.drawable.cartatrasera)
    }



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Carta

        if (activo != other.activo) return false
        if (valor != other.valor) return false
        if (imagen != other.imagen) return false

        return true
    }

    override fun hashCode(): Int {
        var result = activo.hashCode()
        result = 31 * result + valor
        result = 31 * result + (imagen?.hashCode() ?: 0)
        return result
    }

    fun restart() {
        this.activo=false
        this.descubierto=false
    }

    fun comparar(cartaClickeada: Carta): Boolean {
        return (this.getValor() == cartaClickeada.getValor())
    }


}