var setStylishUI = function(container) {

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
			$(this).removeClass('opened');
			$(this).children('ul').slideUp("fast");
		} else {
			$(this).addClass('opened');
			$(this).children('ul').slideDown("fast");
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
	$('a[href$="'+ path + '"] li').addClass('active');
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

var setDateTime = function(container){
	var now = new Date();

	var day = ("0" + now.getDate()).slice(-2);
	var month = ("0" + (now.getMonth() + 1)).slice(-2);
	var today = now.getFullYear() + "-" + (month) + "-" + (day);
	$(container).find('input[type="date"][value="now"]').attr('value',today);

	var hour = now.getHours();
	var mins = now.getMinutes();
  if(hour < 10) hour = '0' + hour; 
  if(mins < 10) mins = '0' + mins; 
  var time = hour + ':' + mins;
  $(container).find('input[type="time"][value="now"]').attr('value',time);
}

