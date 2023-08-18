$(function() {
    $('.userinfo').click(function() {
        var username = $(this).find('td:first-child').text();
        location.href = '/admin/user?username=' + encodeURIComponent(username);
    })
});