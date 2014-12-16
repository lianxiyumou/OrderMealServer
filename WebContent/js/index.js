
function load_banners () {
	// $('#content').html('banners'); 
	// document.createElement("")
	var createDiv = $("<div style=\"border:solid 1px #FF0000\">动态Banner</div>")
	$('#content').html(createDiv);
}

function load_trades () {
	var createDiv = $("<div style=\"border:solid 1px #FF0000\">交易所连接</div>")
	$('#content').html(createDiv);
}