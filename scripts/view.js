$(document).ready(function() {

/*
var commonHead = '';
$('head').append(commonHead);
*/

setStylishUI('main');
setStylishUI('.filters');
setDateTime('html');

//Colocando el copyright a todas las páginas en el main
$('main').append('<footer class="copyright">	&copy; Todos los derechos reservados, Yupana 2020 </footer>');

//Colocando el navegador
var navHtml = '<header>' 
	+ '<div id="nav-user">'
	+ '<div id="profile-more" class="hide-mobile"><i class="fas fa-user-circle"></i> <span id="t-username">Nomusuario</span></div>'
	+ '<a href="profile.html"><div class="mobile"><i class="fas fa-user-circle"></i> <span>Nomusuario<span></div></a>'
	+ '<div class="more hide-mobile">'
	+ '<a href="profile.html"><div><i class="fas fa-chart-bar"></i> Ir a perfil</div></a>'
	+ '<a href="../index.html" id="log-out"><div><i class="fas fa-sign-out-alt"></i> <span>Cerrar sesión </span></div></a>' 
	+ '</div>'
	+ '</div>'
	+ '<nav><ul>'
	+	'<a href="records.html"><li> <i class="fas fa-book"></i> <span>Registros</span></li></a>'
	+	'<a href="clients.html"><li> <i class="fas fa-address-book"></i> <span>Clientes</span></li></a>'
	+	'<a href="learn_more.html"><li> <i class="fas fa-graduation-cap"></i> <span>Aprende más </span></li></a>'
	+	'</ul></nav>'
	+'<div class="nav-title">'
	+ '<img src="../images/Yupana-logo_w_512x512.png" alt="logo Yupana" class="icon"> <img src="../images/Yupana-txt_w_512x256.png" alt="texto Yupana" class="text">'
	+ '</div>'
	+ '</header>';
$('.content.app').prepend(navHtml);
setActivePage();

$('#profile-more').click(function(event) {
	$('#nav-user .more').slideToggle("fast");
	clickAnywButItem("#profile-more" , "#nav-user .more", "sTHide");
});


/*ACTIONS*/

/* Filter */
$("#show-filter").click(function(event) {
	if( $("section.filters").css("display")=="none"){
		$("section.filters").fadeIn(250).css('width', '320px');

		if ($('section.filters').css('display')=="block") {
			clickAnywButItem("#show-filter" , "section.filters", "sTSideHide");
		} else {
			$("section.filters").css('width','0px').fadeOut(250);
		}
	}
	//else { $("#section.filters").css('width', '0px'); }
});

$("#close-filters").click(function(event) {
	$("section.filters").css('width','0px').fadeOut(250);
});


/*new*/

$(".actions .new button").click(function(event) {
	$('.actions .new').slideUp("fast");
	setPopup($(this).attr('id'));
});
$(".actions #new-more").click(function(event) {
	$('.actions .new').slideToggle("fast");
	clickAnywButItem(".actions #new-more" , ".actions .new", "sTHide");
});


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





/*FAST ACCESS btn*/
$("#f-new").click(function(event) {
	if ($(this).parent().children('.submenu').length > 0) {
		$(this).parent().children('.submenu').slideToggle("fast");
		clickAnywButItem(".fast-access #f-new" , ".fast-access .submenu", "sTHide");
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
				case 'newDelivery':
					$('input#isdelivery').attr('checked', true);
					$("input#isdelivery").parent().children('.checkbox').first().html('<i class="fas fa-check-square"></i>');
					break;
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