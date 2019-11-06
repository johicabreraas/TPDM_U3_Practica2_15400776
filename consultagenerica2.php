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
$SQL="SELECT * FROM reciboPagos";
$respuesta=mysql_query($con,$SQL);

$numeroRenglones=mysql_num_rows($respuesta);              
if ($numeroRenglones>0) {
		while ($renglon=$respuesta->fetch_object()) {
			$Data[]	=$renglon;		
		}
		echo (json_encode($Data));

/*$renglon=mysql_fetch_row($respuesta)
if ($renglon) {
	do {
		echo "id: $renglon[0], descripcion: $renglon[1], Fecha: $renglon[2], Lugar: $renglon[3]";

			$renglon=mysql_fetch_row($respuesta);

	} while ($renglon);  */

}else {
	echo "No hay Data a Mostrar";
}

?>