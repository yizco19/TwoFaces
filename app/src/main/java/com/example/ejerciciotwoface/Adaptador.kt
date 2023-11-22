package com.example.ejerciciotwoface

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class Adaptador(
    private val listener: OnCartaClickListener

): RecyclerView.Adapter<Adaptador.ViewHolder>()  {

    private var elementos:ArrayList<Carta>
    private var elementosAnimal:ArrayList<Carta>
    private var elementosComida:ArrayList<Carta>
    private var elementosLogo:ArrayList<Carta>
    private var pareja:Carta?=null
    private var intento=0

    init {
        elementos= ArrayList()
        elementosAnimal= ArrayList()
        elementosComida= ArrayList()
        elementosLogo= ArrayList()

        var i=0
        //bucle de animal usando imagenesAnimales
        Imagenes.imagenesAnimales.forEach {
            run {
                elementosAnimal.add(Carta( i, it))
                elementosAnimal.add(Carta( i, it))
                i++

            }
        }

        i=0
        Imagenes.imagenesComidas.forEach {
            run {
                elementosComida.add(Carta( i, it))
                elementosComida.add(Carta( i, it))
                i++
            }
        }
        i=0
        Imagenes.imagenesLogo.forEach {
            run {
                elementosLogo.add(Carta( i, it))
                elementosLogo.add(Carta( i, it))
                i++
            }
        }
        elementos=desordenar(elementosAnimal)


    }

    interface OnCartaClickListener {
        fun onCartaClick(position: Int)
    }

    //metodo desordena arraylist de carta recibe parameteros y devuelve
    fun desordenar(elementos:ArrayList<Carta>):ArrayList<Carta>{
        for(i in 0..elementos.size-1){
            var aux=elementos[i]
            var aleatorio=(0..elementos.size-1).random()
            elementos[i]=elementos[aleatorio]
            elementos[aleatorio]=aux
        }
        return elementos
    }



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagen: ImageView
        lateinit var item:Carta
        init {
            imagen = view.findViewById(R.id.imagenView)
            imagen.setOnClickListener {
                listener.onCartaClick(adapterPosition)
            }
                

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = elementos[position]
        holder.imagen.setImageResource(elementos[position].getImagenActual())

    }

    fun checkWin() : Boolean{
        return elementos.all { it.getDescubierto() }
    }


    override fun getItemCount(): Int {
        return elementos.size
    }

    override fun getItemId(position: Int): Long {
        return elementos[position].hashCode().toLong()
    }

    fun setAnimal() {
        elementos=elementosAnimal
        reiniciar()
        this.notifyDataSetChanged()
    }

    fun setComida() {
        elementos=elementosComida
        reiniciar()
        this.notifyDataSetChanged()
    }
    fun setMarcas() {
        elementos=elementosLogo
        reiniciar()
        this.notifyDataSetChanged()
    }

    fun reiniciar() {
        desordenar(elementos)
        pareja=null
        intento=0
        elementos.stream().forEach{it.restart()}
        this.notifyDataSetChanged()

    }

    fun getItem(position: Int): Carta {
        return elementos[position]
    }

    fun sumarIntento() {
        intento++
    }

    fun getIntento(): Int {
        return intento
    }


}

