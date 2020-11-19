var setStylishUI = function(container) {

	//$(document).tooltip();

	/*var langs = [ "A# .NET", "A#", "A-0 System", "A+",
        "A++", "ABAP", "ABC", "ABC ALGOL", "ABLE", "ABSET", "ABSYS", "Abundance", "ACC",
        "Accent", "Ace DASL", "ACT-III", "Action!", "ActionScript", "Ada", "Adenine", "Agda",
        "Agilent VEE", "Agora", "AIMMS", "Alef", "ALF", "ALGOL 58", "ALGOL 60", "ALGOL 68",
        "Alice", "Alma", "AmbientTalk", "Amiga E", "AMOS", "AMPL", "APL", "AppleScript", "Arc",
        "ARexx", "Argus", "AspectJ", "Assembly language", "ATS", "Ateji PX", 'AutoHotkey', 'Autocoder', 'AutoIt',
        'AutoLISP / Visual LISP', 'Averest', 'AWK', 'Axum', 'B', 'Babbage', 'BAIL', 'Bash', 'BASIC',
        'bc', 'BCPL', 'BeanShell', 'Batch', 'Bertrand', 'BETA', 'Bigwig', 'Bistro', 'BitC', 'BLISS',
        'Blue', 'Bon', 'Boo', 'Boomerang', 'Bourne shell', 'BREW', 'BPEL', 'BUGSYS', 'BuildProfessional'];
	$('input').autocomplete({
		source: langs;
	});*/

  //Select
	$(container + ' select').each(function(index, element) {
		var replaceSelectHtml = '<div class="select';
		if ($(element).parent("label").attr('class') != null) {
			replaceSelectHtml += " "+ $(element).parent("label").attr('class');
		}
		replaceSelectHtml += '">';

		var defaultSelected = $(element).children('option').first();

		if ($(element).children('option[selected$="selected"]').length>0) {
			defaultSelected = $(element).children('option[selected$="selected"]').first();
		}

		replaceSelectHtml+='<div class="selected" value="' + defaultSelected.attr('value') + '">' + defaultSelected.text() +'<i class="fas fa-chevron-down"></i>'  + '</div>';
		replaceSelectHtml+='<ul>';

		$(element).children('option').each(function(i, el) {
			replaceSelectHtml+='<li class="option';
			if ($(el).attr('value')==defaultSelected.attr('value')) {
				replaceSelectHtml+= ' selected';
				$(el).attr('selected', 'true');
			}
			replaceSelectHtml+= '" value="' + $(el).attr('value') + '">'+$(el).text()+'</li>';
		});

		replaceSelectHtml+='</ul>';
		replaceSelectHtml+= '</div>';
		//aaa+= replaceSelectHtml;
		$(element).after(replaceSelectHtml);
	});
	$(container + ' select').hide();

	$(container + ' .select').click(function(event) {
		if ($(this).hasClass('opened')) {
			//$('*').off("click", slideToogleHide);
			$(this).removeClass('opened');
			$(this).children('ul').slideUp("fast");
		} else {
			$(this).addClass('opened');
			$(this).children('ul').slideDown("fast");
			clickAnywButItem(container + ' .select.opened' , container + ' .select.opened ul', "sTHide", function(){
				$(container +' .select.opened' ).removeClass('opened');
			});
		}
	});

	$(container + '  .select ul li.option').click(function(event) {
		$(this).parent().children('.select ul li.option').removeClass('selected');
		$(this).parent().parent().children('div.selected').attr('value', $(this).attr('value'));
		$(this).parent().parent().children('div.selected').html($(this).text() +'<i class="fas fa-chevron-down"></i>');
		$(this).parent().parent().parent().children('select').children('option').removeAttr('selected');
		$(this).parent().parent().parent().children('select').children('option[value$="'+ $(this).attr('value') + '"]').attr('selected', 'true');;
		$(this).addClass('selected');
	});

	// checkbox/*
	$(container + ' input[type=checkbox]').each(function(index, el) {
		$(el).parent('label').css('cursor', 'pointer');
		$(el).parent('label').prepend('<div class="checkbox"></div>');
		if ($(el).attr('checked') == 'checked') {
			$(el).parent().children('.checkbox').first().html('<i class="fas fa-check-square"></i>')
		} else {
			$(el).parent().children('.checkbox').first().html('<i class="far fa-square"></i>')
		}

		
		$(el).parent('label').click(function(event) {
			$(el).prop('checked',!$(el).prop('checked'));
		});

		$(el).change(function(event) {
			if (!$(this).prop('checked')) {
					$(el).parent('label').children('.checkbox').first().html('<i class="far fa-square"></i>');
				} else {
					$(el).parent('label').children('.checkbox').first().html('<i class="fas fa-check-square"></i>');
				}
		});

		$(el).hide();
	});
};


//Colocando activa la página seleccionada
var setActivePage = function() {
	var arr_pathname = window.location.pathname.split("/");
	var path = arr_pathname[arr_pathname.length-1];
	$('a[href$="'+ path + '"]').children().addClass('active');
};


var setPageAlternations = function(firstContSelector, eToGoSecondSelector, secondContSelector, eToGoFirstSelector){
	$(secondContSelector).hide();

	$(eToGoSecondSelector).click(function(event) {
		$(firstContSelector).hide();
		$(secondContSelector).show();
	});

	$(eToGoFirstSelector).click(function(event) {
		$(secondContSelector).hide();
		$(firstContSelector).show();
	});
};


$('button').click(function(event) {
		event.preventDefault();
});


/**/
var clickAnywButItem = function(eSender,itemToHide,typeFunc,functionAdd){
	//Click Anywere to Hide One Time
	var anyButItem = $( "*" ).not( $(itemToHide).find("*") ).not( $(itemToHide) )
																	.not( $(eSender).find("*") ).not($(eSender));
	//anyButItem.css('color', 'red');
	switch(typeFunc){
		case "sTHide":
		anyButItem.on("click",{fx:functionAdd, ihide:$(itemToHide)},slideToogleHide);
		break;
		case "sTSideHide":
		anyButItem.on("click",{fx:functionAdd, ihide:$(itemToHide)},slideToogleSideHide);
		break;
		case "hidePop":
		anyButItem.on("click",{fx:functionAdd, ihide:$(itemToHide)},popHide);
		break;
	}
		
};

function slideToogleHide(e){
	if(e.target != e.currentTarget) return;
	if($(e.target).is('select')) return;
	if (e.data.ihide.css("display")=="block")
		e.data.ihide.slideUp("fast");
	e.data.fx();
	$('*').off("click", slideToogleHide);
};
function slideToogleSideHide(e){
	if(e.target != e.currentTarget) return;
	if (e.data.ihide.css("display")=="block")
		e.data.ihide.slideUp("fast").css('width','0px').fadeOut(250);
	e.data.fx();
	$('*').off("click", slideToogleSideHide);
};
function popHide(e){
	if(e.target != e.currentTarget) return;
	if (e.data.ihide.css("display")=="block")
		e.data.ihide.slideUp("fast").fadeOut("fast");
	e.data.fx();
	$('*').off("click", popHide);
};

/*POP ups*/

// creando el pop up

var popup = function(container, idBtnPopup, content){
	if (content != null) {
		var popUpHTML = '<div class="popup ext" id="pop-' + idBtnPopup + '" style="display: none;">'
			+ '<div class="popup int" style="display: none;">'
			+ '<button class="close"><i class="fas fa-times"></i></button>'
			+ '<div class="pop-content">'
			+ content + ''
			+ '</div>'
			+ '</div>'
			+ '</div>' ;
		$(container).append(popUpHTML);
		$(container).children('.popup.ext').fadeIn("fast");
		$(container).children('.popup.ext').children('.popup.int').fadeIn("fast");

		$('body').css('overflow', 'hidden');
		setPopUpCloseButtons();
		setStylishUI('.pop-content');
		setDateTime('.pop-content');

		$('.page').fadeOut("fast");
		$('.page').first().fadeIn("fast");

		if ($('.page').length > 1) {
			for (var i = 0; i < $('.page').length; i++) {
				if ($('.page:nth-child('+ (i+1) +') .footer .next').length > 0) {
					$('.page:nth-child('+ (i+1) +') .footer .next').click(function(event) {
						event.preventDefault();
						$(this).parent().parent().fadeOut("fast");
						$('.page:nth-child('+ ($(this).parent().parent().index() + 2) +')').fadeIn("fast");
					});
				}
			}
		}

	} else {
		alert("Acción no disponible");
	}
}

//poniendo botones que cierran el pop up

var setPopUpCloseButtons = function() {
	$('.popup.ext form button').click(function(event) {
		event.preventDefault();
	});

	$('.popup.ext .popup.int .close').click(function(event) {
		$('body').css('overflow', 'auto');
		$(".popup.ext").fadeOut("fast");
		$(".popup.ext").remove();
	});
}


/*tiempo y hora actual*/
var setDateTime = function(container){
	var now = new Date();

	var day = ("0" + now.getDate()).slice(-2);
	var month = ("0" + (now.getMonth() + 1)).slice(-2);
	var today = now.getFullYear() + "-" + (month) + "-" + (day);
	$(container).find('input[type="date"][value="now"]').attr('value',today);
	var hoy = (day) + "/" + (month) + "/" + now.getFullYear();
	$(container).find('span[value="now"]').html(hoy);

	var hour = now.getHours();
	var mins = now.getMinutes();
  if(hour < 10) hour = '0' + hour; 
  if(mins < 10) mins = '0' + mins; 
  var time = hour + ':' + mins;
  $(container).find('input[type="time"][value="now"]').attr('value',time);
};


var setTabs =  function(arrTabs,arrPages) {
	$(arrTabs[0]).siblings(".active").removeClass('active');
	$(arrPages[0]).siblings($(arrPages[0]).get(0).nodeName).fadeOut('fast').removeClass('active');
	$(arrPages[0]).fadeIn('fast', function() {$(this).css('display', 'flex');}).addClass('active');

	if (arrTabs.length == arrPages.length) {
		for (var i = 0; i < arrTabs.length; i++) {
			$(arrTabs[i]).on('click', {eto: arrPages[i]}, function(event) {
				event.preventDefault();
				event.stopPropagation();
				$(event.data.eto).siblings(".active").fadeOut('fast').removeClass('active');
				$(event.data.eto).fadeIn('fast', function() {$(this).css('display', 'flex');}).addClass('active');
				$(this).siblings(".active").removeClass('active');
				$(this).addClass('active');
			});
		}
	}
};


/*CARDS*/
//Vista simple a Vista detalle
var setCard = function() {
	$('.cards .card .tohide').hide();
	$('.actions .cards button#view').click(function(event) {
		if ($(this).attr('value') == 'todet') {
			$('.cards .card').addClass('det');
			$('.cards .card .tohide').show("fast");
			$('.cards .card .details .tosmall').addClass('tonormal');
			$('.cards .card .details .tosmall').removeClass('tosmall');
			$('.cards .card').removeClass('easy');
			$(this).html('Ocultar detalles <i class="fas fa-eye-slash"></i>');
			$(this).attr('value', 'toeasy');
		} else {
			$('.cards .card').addClass('easy');
			$('.cards .card .details .tonormal').addClass('tonormal');
			$('.cards .card .details .tonormal').removeClass('tonormal');
			$('.cards .card .tohide').hide("fast");
			$('.cards .card').removeClass('det');
			$(this).html('Mostrar detalles <i class="fas fa-eye"></i>');
			$(this).attr('value', 'todet');
		}
	});
};


var periodos = {
	d1: 'Diario',
	d7: 'Semanal',
	d15: 'Quincenal',
	d30: 'Mensual',
	d60: 'Bimestral',
	d90: 'Trimestral',
	d120: 'Cuatrimestral',
	d180: 'Semestral',
	d360: 'Anual'
};

