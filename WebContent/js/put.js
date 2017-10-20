/**
 * js file for post.html
 * Please use modern web browser as this file will not attempt to be
 * compatible with older browsers. Use Chrome and open javascript console
 * or Firefox with developer console.
 * 
 * jquery is required
 */
$(document).ready(function() {
	
	var $put_example = $('#put_example')
		, $SET_prod_qty = $('#SET_prod_qty')
		, $SET_total_price = $('#SET_total_price');
	
	getInventory();
	
	
	$(document.body).on('click', ':button, .UPDATE_BTN', function(e) {
		var $this = $(this)
			, order_id = $(this).val()
			, $tr = $this.closest('tr')
			, prod_qty = $tr.find('.CL_prod_qty').text()
			, total_price = $tr.find('.CL_total_price').text()
			, order_date = $tr.find('.CL_order_date').text()
			, product_id = $tr.find('.CL_product_id').text()
			, customer_id = $tr.find('.CL_customer_id').text();
			
			console.log('product_id ------ '+ product_id);
			console.log('total_price ------ '+ total_price);
		
		$('#SET_order_id').val(order_id);
		$SET_prod_qty.text(prod_qty);
		$SET_total_price.val(total_price);
		$('#SET_order_date').text(order_date);
		$('#SET_product_id').text(product_id);
		$('#SET_customer_id').text(customer_id);
		$('#update_response').text("");
		
	});
	
	console.log('before4');
	
	$put_example.submit(function(e) {
		e.preventDefault(); //cancel form submit
		
		var obj = $put_example.serializeObject()
			, prod_qty = $SET_prod_qty.text()
			, total_price = $SET_total_price.val();
		
		updateInventory(obj, prod_qty, total_price);
	});
});

function updateInventory(obj, qty, total) {
	console.log('update inventory function');
	ajaxObj = {  
			type: "PUT",
			url: "http://localhost:8080/StorageAPI/api/v1/put/"+ qty + "/" + total,
			data: JSON.stringify(obj), 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			},
			success: function(data) {
				console.log(data);
				$('#update_response').text( data[0].MSG );
			},
			complete: function(XMLHttpRequest) {
				console.log( XMLHttpRequest.getAllResponseHeaders() );
				getInventory();
			}, 
			dataType: "json" //request JSON
		};
	return $.ajax(ajaxObj);
}

function getInventory() {
	
	var d = new Date()
		, n = d.getTime();
	
	ajaxObj = {  
			type: "GET",
			url: "http://localhost:8080/StorageAPI/api/v1/inventory/", 
			data: "ts="+n, 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			},
			success: function(data) { 
				
				console.log('data='+data);
				
				var html_string = "";
				
				$.each(data, function(index1, val1) {
					
					console.log('val1='+val1);
					
					html_string = html_string + templateGetInventory(val1);
				});
				
				$('#get_inventory').html("<table border='1'>" + html_string + "</table>");
			},
			complete: function(XMLHttpRequest) {
				//console.log( XMLHttpRequest.getAllResponseHeaders() );
			}, 
			dataType: "json" //request JSON
		};
		
	
	return $.ajax(ajaxObj);
}

function templateGetInventory(param) {
	console.log('templateGetInventory(param)');
	return '<tr>' +
				'<td class="CL_prod_qty">' + param.prod_qty + '</td>' +
				'<td class="CL_total_price">' + param.total_price + '</td>' +
				'<td class="CL_order_date">' + param.order_date + '</td>' +
				'<td class="CL_product_id">' + param.product_id + '</td>' +
				'<td class="CL_customer_id">' + param.customer_id + '</td>' +
				'<td class="CL_orders_BTN"> <button class="UPDATE_BTN" value=' + param.order_id + ' type="button">Update</button> </td>' +
			'</tr>';
}

