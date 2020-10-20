$(document).ready(function() {

/*
var commonHead = '';
$('head').append(commonHead);
*/

setStylishUI('main');
setDateTime('main');

//Colocando el copyright a todas las páginas en el main
$('main').append('<footer class="copyright">	&copy; Todos los derechos reservados, Yupana 2020 </footer>');

//Colocando el navegador
var navHtml = '<header>' 
	+' <a href=""><div class="nav-title">'
	+ '<img src="../images/main-img.png" alt="logo Yupana"> <span>Yupana</span>'
	+ '</div></a>'
	+ '<nav><ul>'
	+	'<a href="profile.html"><li> <i class="fas fa-user-circle"></i> <span id="t-username">Nombre_usuario</span></li></a>'
	+	'<a href="records.html"><li> <i class="fas fa-book"></i> <span>Registros</span></li></a>'
	+	'<a href="clients.html"><li> <i class="fas fa-address-book"></i> <span>Clientes</span></li></a>'
	+ '<a href="#"><li> <i class="fas fa-chart-bar"></i> <span>Revisión</span></li></a>'
	+	'<a href="learn_more.html"><li> <i class="fas fa-graduation-cap"></i> <span>Aprende más </span></li></a>'
	+	'<a href="../index.html" id="log-out"><li> <i class="fas fa-sign-out-alt"></i> <span>Cerrar sesión </span></li></a>'
	+	'</ul></nav>'
	+ '</header>';
$('.content.app').prepend(navHtml);

setActivePage();

/*CARDS*/
//Vista simple a Vista detalle
$('.cards .card .details .tohide').hide();
$('.actions .cards button#view').click(function(event) {
	if ($(this).attr('value') == 'todet') {
		$('.cards .card').addClass('det');
		$('.cards .card .details .tohide').show("fast");
		$('.cards .card .details .tosmall').addClass('tonormal');
		$('.cards .card .details .tosmall').removeClass('tosmall');
		$(this).html('Ocultar detalles <i class="fas fa-eye-slash"></i>');
		$(this).attr('value', 'toeasy');
	} else {
		$('.cards .card .details .tonormal').addClass('tonormal');
		$('.cards .card .details .tonormal').removeClass('tonormal');
		$('.cards .card .details .tohide').hide("fast");
		$('.cards .card').removeClass('det');
		$(this).html('Mostrar detalles <i class="fas fa-eye"></i>');
		$(this).attr('value', 'todet');
	}
});




/*ACTIONS*/
$(".actions .new button").click(function(event) {
	setPopup($(this).attr('id'));
});

/*FAST ACCESS btn*/
$("#f-new").click(function(event) {
	if ($(this).parent().children('.submenu').length > 0) {
		$(this).parent().children('.submenu').slideToggle("fast");
	}
	else {
		setPopup($(this).attr('id'));
	}
});

$(".fast-access .submenu button").click(function(event) {
	$('.fast-access div.submenu').slideUp("fast");
	setPopup($(this).attr('id'));
});

/*POP UPS GRANDES*/
function setPopup(idBtnPopup){
	var jqxhr = getContent(idBtnPopup);

	if (jqxhr != null) {
		jqxhr.done(function(data){
			popup('.content', idBtnPopup, data);

			switch (idBtnPopup) {
				case 'newSell': $('input#isdelivery').attr('checked', false);break;
				case 'newDelivery':  $('input#isdelivery').attr('checked', true);break;
			}
		});
	} else {
		alert("Acción no disponible.");
	}
};


function getContent(idBtnPopup) {
	var url = "";

	switch (idBtnPopup) {
		case 'newSell': url = "actions/pop-newSell.html";break;
		case 'newPayment': url = "actions/pop-newPayment.html"; break;
		case 'newDelivery':  url = "actions/pop-newSell.html"; break;
		case 'newSuscriptionD':  url = "actions/pop-newSuscriptionD.html"; break;
		default: return null; break;
	}

	return $.get(url);
}


$('main form button').click(function(event) {
	event.preventDefault();
});

//alert(window.location.pathname);
$('#frm-pagination button').click(function(event) {
	$(this).parent().children('button').removeClass('active');
	$(this).addClass('active');

	//var url = "gotopage?page=" $(this).attr('value');
	//req = initRequest();
	/*req.open("GET", url, true);
	req.onreadystatechange = callback;
	req.send(null);*/
});

function initRequest() {
    /*if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') != -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }*/
}


//
});