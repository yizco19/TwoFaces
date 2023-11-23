package com.example.ejerciciotwoface


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
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
    private var MAXIMO_INTENTOS : Int=5

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
            R.id.botonMenu -> {
                    findViewById<DrawerLayout>(R.id.drawer_layout).openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // una map de boton y su nombre
        val map = mapOf(
            R.id.botonAnimal to "Animal",
            R.id.botonComida to "Comida",
            R.id.botonLogo to "Logo",
            R.id.botonMario to "Mario",
            R.id.boton_reiniciar to "Reiniciar"
        )
        if(map[item.itemId] == null){
            return super.onOptionsItemSelected(item)
        }else {
            adaptador.setData(map[item.itemId]!!)
            findViewById<TextView>(R.id.textvida).text = "Vida: ${MAXIMO_INTENTOS-adaptador.getIntento()}"
            Toast.makeText(this, map[item.itemId], Toast.LENGTH_SHORT).show()
            findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
            return true
        }

    }



    override fun onCartaClick(position: Int) {
        if(adaptador.getIntento()<MAXIMO_INTENTOS){
            val cartaClickeada = adaptador.getItem(position)


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
                    adaptador.sumarIntento()

                    findViewById<TextView>(R.id.textvida).text = "Vida: ${MAXIMO_INTENTOS-adaptador.getIntento()}"
                    if(adaptador.getIntento()>=MAXIMO_INTENTOS){
                        Toast.makeText(this, "Perdiste", Toast.LENGTH_SHORT).show()
                    }
                    cartaClickeada.setActivo(true)
                    adaptador.notifyDataSetChanged()

                    Handler(Looper.getMainLooper()).postDelayed({
                        pareja!!.setActivo(false)
                        cartaClickeada.setActivo(false)
                        adaptador.notifyDataSetChanged()
                        pareja = null
                    }, 500)
                }
            }


            adaptador.notifyDataSetChanged()


        }


    }

    private fun checkWin() {
        if(adaptador.checkWin()){
            Toast.makeText(this, "Ganaste", Toast.LENGTH_SHORT).show()
        }
    }



}
