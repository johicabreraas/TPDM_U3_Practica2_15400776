package ittepic.edu.mx.tpdm_u3_practica2_15400776

import android.app.ProgressDialog
import android.os.AsyncTask
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class ConexionWeb (p:MainActivity): AsyncTask<URL,Void,String>(){
    var puntero=p
    var variablesEnvio=ArrayList<String>()
    var dialogo=ProgressDialog(puntero)

    override fun onPreExecute() {   //antes de la ejecucion
        super.onPreExecute()
        dialogo.setTitle("Atencion")
        dialogo.setMessage("Conectando con servidor")
        dialogo.show()

    }

    fun agregarVariablesEnvio(clave:String,valor:String){
        var cad=clave+"&"+valor
        variablesEnvio.add(cad)


    }

    override fun doInBackground(vararg params: URL?): String {   // ejecucion
        var respuesta=""
        var cadenaEnvioPOST=""
        var total=variablesEnvio.size-1


        (0..total).forEach{            //creando cadena de clave valor para envio al post
            try {
                var data = variablesEnvio.get(it).split("&")
                cadenaEnvioPOST += data[0] + "=" + URLEncoder.encode(data[1], "utf-8") + " "
            }catch (err:UnsupportedEncodingException){

                respuesta="no se pudo codificar URL"
            }
        }

        cadenaEnvioPOST=cadenaEnvioPOST.trim() //QUITA LOS ESPACIOS EN BLACO DEL INICIO Y FINAL DE LA CADENA
        cadenaEnvioPOST=cadenaEnvioPOST.replace(" ","&")     //SUSTITULLE LOS ESTACIOS EN BLANDO DENTRO DE LA CADENA POR &


        var conexion :HttpURLConnection?=null
        try {
            //amarro conexion con servidor web/lenguaje web
            conexion=params[0]?.openConnection() as HttpURLConnection

            conexion?.doOutput=true
            conexion?.setFixedLengthStreamingMode(cadenaEnvioPOST.length)
            conexion?.requestMethod="POST"
            conexion?.setRequestProperty("Content-Type","application/x-www-form-encoded")

            //envio de variables ya codificadas
            var salida=BufferedOutputStream(conexion?.outputStream)      //pide la conexion y el flujo correspondiente
            salida.write(cadenaEnvioPOST.toByteArray())     //enviar la conexion
            salida.flush()
            salida.close()    //nos aseguramos que salga

            if (conexion?.responseCode==200){
                var flujoEntrada=InputStreamReader(conexion?.inputStream,"UTF-8")
                var entrada=BufferedReader(flujoEntrada)

                respuesta="""${entrada.readLine()}"""
                entrada.close()


            }else{
                respuesta="error"+conexion?.responseCode
            }

        }catch (err:IOException){
            respuesta="error IOExeption"
        }finally {
            if (conexion != null){
                conexion.disconnect()
            }
        }

        return respuesta
    }

    override fun onPostExecute(result: String?) {   // se ejecuta en cuanto termina de ejecutarse el doInBackground
        super.onPostExecute(result)

        dialogo.dismiss()
        puntero.mostrarResultado(result!!)
    }

}