package com.example.ejerciciotwoface


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  ,Adaptador.OnCartaClickListener {

    private var adaptador: Adaptador = Adaptador(this)
    private var pareja: Carta? = null
    private var MAXIMO_INTENTOS : Int=2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutManager = GridLayoutManager(this, 4)
        findViewById<RecyclerView>(R.id.recyclerview).layoutManager = layoutManager
        findViewById<RecyclerView>(R.id.recyclerview).adapter = adaptador
        val navView: NavigationView = findViewById(R.id.nav_view)




        navView.setNavigationItemSelectedListener(this)
       setSupportActionBar(findViewById(R.id.menu_toolbar))

    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        getMenuInflater().inflate(R.menu.menu, menu);


        return super.onCreateOptionsMenu(menu);
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.botonSalir -> {
                finish()
                true
            }
            R.id.botonRestart -> {

                    findViewById<DrawerLayout>(R.id.drawer_layout).openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
             com.example.ejerciciotwoface.R.id.botonAnimal -> {
                adaptador.setAnimal()
                 Toast.makeText(this, "Animal", Toast.LENGTH_SHORT).show()
                 findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
                true
            }
            R.id.botonComida -> {
                adaptador.setComida()
                Toast.makeText(this, "Comida", Toast.LENGTH_SHORT).show()
                findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
                true
            }
            R.id.boton_reiniciar -> {
                adaptador.reiniciar()
                findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
                true
            }
            R.id.botonLogo -> {
                adaptador.setMarcas()
                Toast.makeText(this, "Logo", Toast.LENGTH_SHORT).show()
                findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    @SuppressLint("SuspiciousIndentation")
    override fun onCartaClick(position: Int) {
        val cartaClickeada = adaptador.getItem(position)

        if(adaptador.getIntento()>=MAXIMO_INTENTOS){
            Toast.makeText(this, "Perdiste", Toast.LENGTH_SHORT).show()
            adaptador.reiniciar()
        }else{
            if(pareja==null){
                pareja=cartaClickeada
                pareja!!.setActivo(true)

            }else{
                if(!pareja!!.equals(cartaClickeada) &&pareja!!.comparar(cartaClickeada)){
                    pareja!!.setDescubierto()
                    cartaClickeada.setDescubierto()
                    checkWin()
                    pareja=null
                }else{
                    //suma intento metodo
                    adaptador.sumarIntento()
                    pareja!!.setActivo(false)
                    cartaClickeada.setActivo(true)

                    /**
                     *                     cartaClickeada.setActivo(true)
                     *                     adaptador.notifyDataSetChanged()
                     *                     //espera 1 segundo
                     *                     Thread.sleep(1000)
                     *                     //desactiva
                     *                     cartaClickeada.setActivo(false)
                     *                     pareja!!.setActivo(false)
                     */
                    pareja=cartaClickeada
                }
            }
        }

            adaptador.notifyDataSetChanged()


    }

    private fun checkWin() {
        if(adaptador.checkWin()){
            Toast.makeText(this, "Ganaste", Toast.LENGTH_SHORT).show()
        }
    }


}
