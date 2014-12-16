
function load_productlist () {
	// $('#content').html('banners'); 
	// document.createElement("")
	
	var createDiv = $("<div style=\"border:solid 1px #FF0000\">动态Banner</div>");
	//<button type="button" class="btn btn-default">Default</button>
	var btn = $("<button type=\"button\" class=\"btn btn-default\">添加商品</button>");
	btn.attr("onclick","create_product();");
	btn.appendTo(createDiv);	
	$('#content').html(createDiv);
	
}

function create_product(createDiv){
	var form = $('<form role="form"/>');
	form.attr("id","add_food_form");
//	form.attr("method","post");
//	form.attr("action","http://192.168.80.109:8090/OrderMealServer/food/addFood.do");
	form.attr("onsubmit","return submitFood();");
	var food_group = $('<div class="form-group"/>');
	var foodlabel = $(' <label >菜名</label>');
	var foodinput = $('<input name="foodName" type="text" class="form-control" id="add_product_foodname" placeholder="">');
	foodlabel.appendTo(food_group);
	foodinput.appendTo(food_group);
	food_group.appendTo(form);
	
	var price_group = $('<div class="form-group"/>');
	var pricelabel = $(' <label >菜价</label>');
	var priceinput = $('<input name="foodPrice" type="text" class="form-control" id="add_product_foodprice" placeholder="">');
	pricelabel.appendTo(price_group);
	priceinput.appendTo(price_group);
	price_group.appendTo(form);
	
	var desc_group = $('<div class="form-group"/>');
	var desclabel = $(' <label for="exampleInputEmail1">描述</label>');
	var descinput = $('<input name="desc" type="text" class="form-control" id="exampleInputEmail1" placeholder="">');
	desclabel.appendTo(desc_group);
	descinput.appendTo(desc_group);	
	desc_group.appendTo(form);
	
	var foodtype_group = $('<div class="form-group"/>');
	var foodtypelabel = $(' <label >菜类别</label>');	
	foodtypelabel.appendTo(foodtype_group);
    var select = $('<select name="foodType" class="form-control"/>').appendTo(foodtype_group);
    var option1 = $("<option value=\"1\">text1</option>").appendTo(select);
    var option2 = $("<option value=\"2\">text2</option>").appendTo(select);
    var option3 = $("<option value=\"3\">text3</option>").appendTo(select);	
    foodtype_group.appendTo(form);
    
	var shop_group = $('<div class="form-group"/>');
	var shoplabel = $(' <label >所属商店</label>');	
	shoplabel.appendTo(shop_group);
    var select = $('<select name="shop" class="form-control"/>').appendTo(shop_group);
    var option1 = $("<option value=\"1\">text1</option>").appendTo(select);
    var option2 = $("<option value=\"2\">text2</option>").appendTo(select);
    var option3 = $("<option value=\"3\">text3</option>").appendTo(select);	
    shop_group.appendTo(form); 	
	
	var img_group = $('<div class="form-group"/>');
	var imglabel = $(' <label for="exampleInputFile">图片</label>');
	var imginput = $('<input name="foodImg" type="file" id="exampleInputFile">');
	imglabel.appendTo(img_group);
	imginput.appendTo(img_group);	
	img_group.appendTo(form);
	//var btn_submit = $('<button type="submit" class="btn btn-default">添加商品</button>');
	var btn_submit = $('<button type="submit"  class="btn btn-default">添加商品</button>');
//	btn_submit.attr("onclick","submitFood();");
	btn_submit.appendTo(form);
	
	$('#content').html(form);
	
}


function submitFood() {   
	
	var options = {
	    target:  '#content',
	    url:     'http://192.168.80.109:8090/OrderMealServer/food/addFood.do',
	    type:'post',
	    dataType: 'json',
	    success:    function(data) {
	        if(data.error == 0){
	        	$("#add_food_form").resetForm();
	        	load_productlist ();
	        }
	        
	    }
	};	
	
    // jquery 表单提交   
    $("#add_food_form").ajaxSubmit(options);   
       
    return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转   
}

function load_trades () {
	var createDiv = $("<div style=\"border:solid 1px #FF0000\">交易所连接</div>")
	$('#content').html(createDiv);
}