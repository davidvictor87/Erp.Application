$(document).ready(function () {

    $('.nBtn, .table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text(); 

        if (text === 'Edit') {
            $.get(href, function (user, status) {
                $('.myForm #id').val(user.id);
                $('.myForm #email').val(user.email);
                $('.myForm #password').val(user.password);
                $('.myForm #name').val(user.name);
                $('.myForm #lastName').val(user.lastName);
                $('.myForm #active').val(user.active);
            });
            $('.myForm #exampleModal').modal();
        } else {
            $('.myForm #id').val('');
            $('.myForm #email').val('');
            $('.myForm #password').val('');
            $('.myForm #name').val('');
            $('.myForm #lastName').val('');
            $('.myForm #active').val('');
            $('.myForm #exampleModal').modal();
        }
    });

    $('.table .delBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });
});