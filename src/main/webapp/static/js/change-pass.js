/**
 * 
 */

  $(".show-password, .hide-password").on('click', function() {
    var passwordId = $(this).parents('div:first').find('input').attr('id');
    if ($(this).hasClass('show-password')) {
      $("#" + passwordId).attr("type", "text");
      $(this).parent().find(".show-password").hide();
      $(this).parent().find(".hide-password").show();
    } else {
      $("#" + passwordId).attr("type", "password");
      $(this).parent().find(".hide-password").hide();
      $(this).parent().find(".show-password").show();
    }
  });
