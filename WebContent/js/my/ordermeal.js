function loadFood(){
	alert('loadFood');
	var aj = $.ajax( {    
		url:pre_url+'food/getAllFood.do',// ��ת�� action    
		data:{    
				 foodname : '���',    
				 type : 0 
		},    
		type:'post',    
		cache:false,    
		dataType:'json',    
		success:function(data) {  
			alert('success: '+data.foodlist);
			/*if(data.msg =="true" ){    
				// view("�޸ĳɹ���");    
				alert("�޸ĳɹ���");    
				//window.location.reload();    
			}else{    
				view(data.msg);    
			}    */
		 },    
		 error : function() {    
			  // view("�쳣��");    
			  alert("exception");    
		 }    
	}); 
	
}