package ittepic.edu.mx.tpdm_u3_practica2_15400776

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import org.json.JSONObject
import java.net.URL
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var Descripcion: EditText? = null
    var Monto: EditText? = null
    var FechaVencimiento: EditText? = null
    var Pagado: RadioButton?=null

    var btInsertar: Button?=null
    var btnmostrar: Button?=null
    var btcargar: Button? = null

    var etiqueta: TextView? = null

    var jsonRespuesta= ArrayList<JSONObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Descripcion = findViewById(R.id.descripcion)
        Monto = findViewById(R.id.monto)
        FechaVencimiento = findViewById(R.id.fechaVencimiento)
        Pagado = findViewById(R.id.pagado)

        btInsertar = findViewById(R.id.btninsertar)
        btnmostrar = findViewById(R.id.btnmostrar)
        btcargar = findViewById(R.id.btncargar)

        etiqueta = findViewById(R.id.etiqueta)


        btInsertar?.setOnClickListener {
            var conexionWeb = ConexionWeb(this)
            conexionWeb.agregarVariablesEnvio("descripcion", Descripcion?.text.toString()+"\n")
            conexionWeb.agregarVariablesEnvio("monto", Monto?.text.toString()+"\n")
            conexionWeb.agregarVariablesEnvio("fechavencimiento", FechaVencimiento?.text.toString()+"\n")
            conexionWeb.agregarVariablesEnvio("pagado", Pagado?.isChecked.toString()+"\n")
            conexionWeb.execute((URL("https://guarded-lowlands-29688.herokuapp.com/insertar2.php")))
            limpiarCampos()
        }

        btnmostrar?.setOnClickListener {
            val posicion = Descripcion?.text.toString().toInt()
            val jsonObject = jsonRespuesta.get(posicion)
            etiqueta?.setText("IDPago: " +jsonObject.getString("idpago")+
                    "\nDescripcion: " + jsonObject.getString("descripcion") +
                    "\nMonto: " + jsonObject.getString("monto") +
                    "\nFecha De Vencimiento: " + jsonObject.getString("fechavencimiento") +
                    "\nPagado: " + jsonObject.getString("pagado"))

        }

        btcargar?.setOnClickListener {
            var conexionWeb = ConexionWeb(this)
            conexionWeb.execute((URL("https://guarded-lowlands-29688.herokuapp.com/consultagenerica2.php")))

        }
    }


        fun limpiarCampos(){
            Descripcion?.setText("")
            Monto?.setText("")
            FechaVencimiento?.setText("")
            Pagado?.isChecked()
        }

    fun mostrarResultado(result: String) {

        val jsonarray = org.json.JSONArray(result)
        var total = jsonarray.length() - 1
        (0..total).forEach {
            jsonRespuesta.add(jsonarray.getJSONObject(it))
        }
        etiqueta?.setText(result)
    }
    }

