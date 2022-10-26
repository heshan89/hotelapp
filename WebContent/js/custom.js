$(document).ready(function () {
  function incrementValue(e) {
    e.preventDefault();
    var fieldName = $(e.target).data('field');
    var parent = $(e.target).closest('div');
    var currentVal = parseInt(parent.find('input[name=' + fieldName + ']').val(), 10);

    if (!isNaN(currentVal)) {
      parent.find('input[name=' + fieldName + ']').val(currentVal + 1 * 10);
    } else {
      parent.find('input[name=' + fieldName + ']').val(0);
    }
  }

  function decrementValue(e) {
    e.preventDefault();
    var fieldName = $(e.target).data('field');
    var parent = $(e.target).closest('div');
    var currentVal = parseInt(parent.find('input[name=' + fieldName + ']').val(), 10);

    if (!isNaN(currentVal) && currentVal > 0) {
      parent.find('input[name=' + fieldName + ']').val(currentVal - 1 * 10);
    } else {
      parent.find('input[name=' + fieldName + ']').val(0);
    }
  }

  $('.input-group').on('click', '.button-plus', function(e) {
    incrementValue(e);
  });

  $('.input-group').on('click', '.button-minus', function(e) {
    decrementValue(e);
  });

  $(function() {
    $(".editinn").click(function () {
      $(this).toggleClass("hide");
      $(".saveinn").removeClass("hide");
      //e.preventDefault(); /*ignores actual link*/
    });
    $(".saveinn").click(function () {
      $(this).toggleClass("hide");
      $(".editinn").removeClass("hide");
      //e.preventDefault(); /*ignores actual link*/
    });
  });

  $('.adminadd').click(function(){
    $('.adminadhide').addClass('shown');
    $('.fillterarea').addClass('hide');
  });

  $('.filterview').click(function(){
    $('.adminadhide').removeClass('shown');
    $('.fillterarea').removeClass('hide');
  });
});