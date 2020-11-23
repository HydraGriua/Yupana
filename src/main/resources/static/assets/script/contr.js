$(document).ready(function() {

	//var e = btoa(JSON.stringify(data));
	//var d = atob( e );// desencripta
	//JSON.parse( d ); //devuelve el JSON

var api = "https://yupana.herokuapp.com/api/";
var headers = new Headers();
headers.append('Content-Type', 'application/json');
var user = "";
if(localStorage.getItem('userY') != null) {
	user = localStorage.getItem('userY');
	user = JSON.parse(user);
}
/*HEADER*/


//USERS
//en login
// "https://yupana.herokuapp.com/api/users/1")

$('#signin').submit(function(event) {
	event.preventDefault();
	if ($('#signin input[name="password"]').val() != $('#signin input[name="confirm-password"]').val()) {
		$('#signin #paswderror').text('Las constraseñas no coinciden');
	} else {
		/*var dataSend = {username: $('#signin input[name="username"]').val(),
											email: $('#signin input[name="email"]').val(),
											password: $('#signin input[name="password"]').val()};*/
		alert("ok");
	}
});


$('#login').submit(function(event) {
	event.preventDefault();
	var urlAPI = api+"users/";
	urlAPI += 1;

	/*var dataSend = {username: $('#login input[name="username"]').val(),
											password: $('#login input[name="password"]').val()};*/

	/*$.ajax({
			url: $(this).attr('action'),
			type: 'POST',
			data: {users: JSON.stringify(dataSend) },
			dataType: 'json',
			success: function(json) {
				//window.location.href = "mystore/profile.html";
			},
			error: function(){
				alert("error");
			}
		});*/

	fetch(urlAPI).then(function(response){
		if(response.ok) {
			return response.json();
		} else {
			alert("Respuesta de red OK pero respuesta HTTP no OK");
		}
	}).then(function(json){
		user = json;
		localStorage.setItem('userY', JSON.stringify(user));
		window.location.href = "mystore/records.html";
	}).catch(function(err){
		alert("Ocurrió el siguiente error: " + err.message );
	});

});

/*  User
GET:
/users/{userId}		|obtener usuario por id
/users/dni={dni}   	|obtener usuario por dni

POST:
/users				|Crear usuario

PUT:
/users/{userId}		|Editar usuario

DELETE:
/users/{userId}		|Eliminar usuario
*/

$('#log-out').click(function(event) {
	event.preventDefault();
	localStorage.removeItem('userY');
	user = null;
	window.location.href = $(this).attr('href');
});


// EN PROFILE
if($("#user_data").html() != undefined){
	$("#user_data").ready(function(){
		$('#user_data input[name="nombres"]').attr('value',user.name);
		$('#user_data input[name="apepat"]').attr('value',user.firstLastname);
		$('#user_data input[name="apemat"]').attr('value',user.secondLastname);
		$('#user_data select[name="docTipo"] option[value$='+user.documentType+']').attr('select', 'selected');
		$('#user_data input[name="documento"]').attr('value', user.documentNumber);
		$('#user_data input[name="celular"]').attr('value', user.cellphone);
	});
}


// FLUJO
if($("#c-records").html() != undefined){
	/*var urlAPI = api+"users/";
	urlAPI += 1;
		
	fetch(urlAPI).then(function(response){
		if(response.ok) {
			return response.json();
		} else {
			alert("Respuesta de red OK pero respuesta HTTP no OK");
		}
	}).then(function(json){
		//user = json;
	}).catch(function(err){
		alert("Ocurrió el siguiente error: " + err.message );
	});*/
	//setCard();
	//alert(periodos['d' + 30]);
}
/*
<div class="card easy center"><!--det o easy-->
			<div class="top">
				<h1>Registro de venta al crédito</h1>
				<div class="r-code">Código de registro <span>NM1238913</span></div>
			</div>
			<div class="details">
				<div class="client">
					Cliente <span ><a href="#">Nombre completo Cliente</a></span>
				</div>
				<div class="date tosmall"><!--tonormal-->
					Fecha de emisión: <span>dd/mm/aaaa</span>
				</div>
				<div class="tInterest tohide">
					Tipo de interés
					<span>Tasa Simple Anual</span>
					<span class="subDet">sin capitalización</span>
				</div>
				<div  class="ti tosmall tohide">
					Tasa de interés
					<span>xx.xxxxxxx%</span>
				</div>
				<div class="money debt"><!--debt o payment-->
					<div class="tohide">
						<div class="subDet">Monto de venta <span>s/xx.xx</span></div>
						<div class="subDet">Monto de delivery <span>s/xx.xx</span></div>
					</div>
					<div>Monto a dado a crédito <span>S/XX.XX</span></div>
				</div>
				<div class="descrip merge tohide">
					Descripción:
					<span>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation.</span>
				</div>
			</div>
		</div> <!-- card -->
*/

if($("#c-clients").html() != undefined){
	var urlAPI = api+"sellers/" + 1 + "/wallets";
	//obtener todos los clientes
	fetch(urlAPI).then(function(response){
		if(response.ok) {
			return response.json();
		} else {
			alert("Respuesta de red OK pero respuesta HTTP no OK");
		}
	}).then(function(json){
		for (var i = 0; i < json.length; i++) {
			urlAPI = api + "wallets/"+ json[i].id +"/LastData";

			fetch(urlAPI).then(function(response){
				if(response.ok) {
					return response.json();
				} else {
					alert("Respuesta de red OK pero respuesta HTTP no OK");
				}
			}).then(function(jsonAux){
				var aux_html = '<div class="card easy center"><!--det o easy-->'
						+ '<div class="top">'
						+ '<h1>Cliente con delivery</h1>'
						+ '<div class="r-code">Documento <span>'+ jsonAux.documentNumber +'</span></div>'
						+ '</div><!--top-->'
						+ '<div class="details">'
						+ '<div class="client"> Cliente <span ><a href="#">'+ jsonAux.name + ' ' + jsonAux.firstLastname + ' ' + jsonAux.secondLastname +'</a></span> </div>'
						+ '<div class="tInterest tohide">Tipo de interés <span>' + jsonAux.currentRateType + ' ' + periodos['d' + jsonAux.currentRatePeriod] + '</span>';
				if(jsonAux.currentRateType.toUpperCase() == 'SIMPLE') {
					aux_html += '<span class="subDet">sin capitalización</span> </div>';
				} else {
					aux_html += '<span class="subDet">Capitaliza cada ' + jsonAux.currentCapitalization + ' días</span> </div>';
				}
				aux_html += '<div class="delivery tohide">Delivery'
						+ '<div class="row"><span>' + periodos['d' + 30] + '</span>'
						+ '<span>S/' + jsonAux.samount + '</span></div>'
						+ '<div class="subDet"><!--class="date tosmall" -- tonormal-->'
						+ 'Próximo pago: <span>' + jsonAux.sexpirationDate + '</span>'
						+ '</div>'
						+ '</div><!--delivery-->'
						+ '<div class="credleft tohide"><!--debt o payment-->'
						+ '<div>Crédito restante <span>S/'+  jsonAux.currentCreditLine +'</span></div>'
						+ '<div class="subDet">Crédito otorgado <span>S/'+ jsonAux.creditLine +'</span></div>'
						+ '</div>'
						+ '<div class="acum">'
						+ '<div class="money debt">Monto acumulado a cobrar <span> S/'+ jsonAux.totalDebt +'</span></div>'
						+ '<div  class="ti tohide">Tasa de interés <span>'+ jsonAux.currentInterestRate +'%</span></div>'
						+ '</div>'
						+ '<div class="mantenimiento money tohide">Mantenimiento de cuenta'
						+ '<div class="row">' + periodos['d' + 30] + '<span>S/'+ jsonAux.maintenancePrice +'</span></div>'
						+ '<div class="subDet"> Próximo pago: <span>'+ jsonAux.deadlineDate +'</span></div>'
						+ '</div>'
						+ '<div class="descrip merge tohide">Descripción'
						+ '<span>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation.</span>'
						+ '</div>'
						+ '</div> <!--details-->'
						+ '<div class="client-actions"> '
						+ '<button class="delete color-alarm-bg"><span>Eliminar</span> <i class="fas fa-trash-alt"></i></button>'
						+ '<button class="edit"><span>Editar</span> <i class="fas fa-edit"></i></button>'
						+ '<button class="new color-accent-bg"><span>Nuevo registro </span> <i class="fas fa-plus"></i></button>'
						+ '<a href="client-details.html"><button class="receipts color-secondary-bg">Ver detalle <i class="fas fa-clipboard-list"></i></button></a>'
						+ '</div><!--client-actions-->'
						+ '</div><!-- card -->';
				$("#c-clients").append(aux_html);
				$("#loading").hide();
				setCard();
			}).catch(function(err){
				alert("Ocurrió el siguiente error: " + err.message );
			});
		}
	}).catch(function(err){
		alert("Ocurrió el siguiente error: " + err.message );
	});
}


var sendcli = function(walletid){
	sessionStorage.setItem('cliente', walletid);
}

if(sessionStorage.getItem('cliente') != null) {
	if($("#h-registros").html() != undefined){
		$("#h-registros").ready(function(){
			//sessionStorage.getItem('cliente');
			//setCard();
			//tipoventa
			//tuvo delivery
			//fecha de emision
			//tasa de interes tipo, periodo capitalizacion %
			//monto de venta, de delivery, total
			//descripcion
		});
	}
	setCard();
}

//setCard();




});