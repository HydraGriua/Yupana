$(document).ready(function() {

var user = "";
if(localStorage.getItem('userY') != null) {
	user = localStorage.getItem('userY');
	user = JSON.parse(user);
}

//Colocando el copyright a todas las páginas en el main
$('main').append('<footer class="copyright">	&copy; Todos los derechos reservados, Yupana 2020 </footer>');

/*
var commonHead = '';
$('head').append(commonHead);
*/
setStylishUI('header');
setStylishUI('.filters');
setStylishUI('.actions');
setStylishUI('main');
setDateTime('html');
setCard();
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
	//setPopup($(this).attr('id'));
});
$(".actions #new-more").click(function(event) {
	$('.actions .new').slideToggle("fast");
	clickAnywButItem(".actions #new-more" , ".actions .new", "sTHide");
});
/*$(".actions #newClient").click(function(event) {
	setPopup($(this).attr('id'));
});*/


/*FAST ACCESS btn*/
$("#f-new").click(function(event) {
	if ($(this).parent().children('.submenu').length > 0) {
		$(this).parent().children('.submenu').slideToggle("fast");
		clickAnywButItem(".fast-access #f-new" , ".fast-access .submenu", "sTHide");
	}
});

$(".fast-access .submenu button").click(function(event) {
	$('.fast-access div.submenu').slideUp("fast");
});

$('main form button').click(function(event) {
	event.preventDefault();
});

//alert(window.location.pathname);
$('#frm-pagination button').click(function(event) {
	$(this).parent().children('button').removeClass('active');
	$(this).addClass('active');
});


//inputs de new-sell
$("input#monto_credito").bind('keyup change', function(event) {
	$('#totalD').val(getTotal([$('input#priceD').val(),$(this).val()]) );
});
$("input#priceD").bind('keyup change', function(event) {
	$('#totalD').val(getTotal([$('input#monto_credito').val(),$(this).val()]) );
});
$('input[type="checkbox"]#isdelivery').change(function(event) {
	if (!this.checked) {
		$('#totalD').val($('input#monto_credito').val());
		$('input#priceD').val(0);
	}
});

$('.pop-alert div.pop').hide();
$('.pop-alert > .icon-circle').click(function(event) {
	$(this).parent().children('div.pop').slideDown("fast");
});
$('.pop-alert div.pop .row > .icon-circle').click(function(event) {
	$(this).parent().parent().slideUp("fast");
});

});