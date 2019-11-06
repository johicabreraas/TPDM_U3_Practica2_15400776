<?php

$conexion = mysql_connect(
	'us-cdbr-iron-east-05.cleardb.net',
	'b0c1ebafc31aa4',
	'50ab8d05',
	'heroku_7e8c1bc54e72add'
);

if (!$conexion) {
	echo "No hay conexion";
	return;
}

//obteniendo variables desde el formulario

$descripcion = $_POST['descripcion']:
$fechavencimiento = $_POST['fechavencimiento']:
$monto = $_POST['lugar']:
$pagado = $_POST['pagado']:
$id=$_POST["id"];

$SQL ="UPDATE reciboPagos SET descripcion='$descripcion', fechavencimiento='$fechavencimiento', monto='$monto', pagado='$pagado' WHERE id=$id";
  

  $respuesta = mysql_query($conexio,$SQL);
    if ($respuesta) {
    	echo "Datos Actualizados";
    }else{
    	echo "Error Datos No Actualizados";
    }

    
?>