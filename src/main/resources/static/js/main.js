/**
 * 
 */

$(document).ready(function(){
	
	$('.table .eBtn').on('click', function(event){
		event.preventDefault();
		(var href = $(this).attr('href');
		
		$.get(href, function(users, status){
			$('.myForm #id').val(users.id);
			$('.myForm #email').val(users.email);
			$('.myForm #password').val(users.password);
			$('.myForm #fName').val(users.name);
			$('.myForm #sName').val(users.lastName);
			$('.myForm #status').val(users.active);
		});
		
		$('.myForm #exampleModal').modal();
		
	});
	
});