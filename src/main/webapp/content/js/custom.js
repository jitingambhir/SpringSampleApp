function setHeight() {
	top_header = $(".sidebar-logo").height();
	footer_height = $(".sidebar-contact").outerHeight();
	windowHeight = $(window).innerHeight();
	
	final_height = windowHeight - (top_header + footer_height);	
	$('.inner-sidebar-menu').css('height', final_height);
	}

	$(document).ready(function () {
	setHeight();
	$(window).resize(function () {
	 setHeight();
	});
});