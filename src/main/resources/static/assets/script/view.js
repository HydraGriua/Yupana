$(document).ready(function() {

    var user = "";
    if (localStorage.getItem('userY') != null) {
        user = localStorage.getItem('userY');
        user = JSON.parse(user);
    }

    //Colocando el copyright a todas las páginas en el main
    $('main').append('<footer class="copyright">	&copy; Todos los derechos reservados, Yupana 2020 </footer>');

    //Colocando el navegador
 


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
        clickAnywButItem("#profile-more", "#nav-user .more", "sTHide");
    });


    /*ACTIONS*/

    /* Filter */
    $("#show-filter").click(function(event) {
        if ($("section.filters").css("display") == "none") {
            $("section.filters").fadeIn(250).css('width', '320px');

            if ($('section.filters').css('display') == "block") {
                clickAnywButItem("#show-filter", "section.filters", "sTSideHide");
            } else {
                $("section.filters").css('width', '0px').fadeOut(250);
            }
        }
        //else { $("#section.filters").css('width', '0px'); }
    });

    $("#close-filters").click(function(event) {
        $("section.filters").css('width', '0px').fadeOut(250);
    });


    /*new*/

    $(".actions .new button").click(function(event) {
        $('.actions .new').slideUp("fast");
        setPopup($(this).attr('id'));
    });
    $(".actions #new-more").click(function(event) {
        $('.actions .new').slideToggle("fast");
        clickAnywButItem(".actions #new-more", ".actions .new", "sTHide");
    });
    $(".actions #newClient").click(function(event) {
        setPopup($(this).attr('id'));
    });


    /*FAST ACCESS btn*/
    $("#f-new").click(function(event) {
        if ($(this).parent().children('.submenu').length > 0) {
            $(this).parent().children('.submenu').slideToggle("fast");
            clickAnywButItem(".fast-access #f-new", ".fast-access .submenu", "sTHide");
        } else {
            setPopup($(this).attr('id'));
        }
    });

    $(".fast-access .submenu button").click(function(event) {
        $('.fast-access div.submenu').slideUp("fast");
        setPopup($(this).attr('id'));
    });

    /*POP UPS GRANDES*/
    function setPopup(idBtnPopup) {

        var jqxhr = getContent(idBtnPopup);


        if (jqxhr != null) {
            jqxhr.done(function(data) {
                alert("entro");
                popup('.content', idBtnPopup, data);

                switch (idBtnPopup) {
                    case 'newSell':
                        $('input#isdelivery').attr('checked', false);
                        break;
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
            case 'newSell':
                url = "http://localhost:8088/mystore/actions/pop-newSell.html";
                break;
            case 'newPayment':
                url = "/actions/pop-newPayment.html";
                break;
            case 'newDelivery':
                url = "/actions/pop-newSell.html";
                break;
            case 'newSuscriptionD':
                url = "/actions/pop-newSuscriptionD.html";
                break;
            case 'newClient':
                url = "http://localhost:8088/mystore/actions/pop-client.html";
                break;
            case 'editClient':
                url = "/actions/pop-client.html";
                break;
            default:
                return null;
                break;
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
    };


    //
});