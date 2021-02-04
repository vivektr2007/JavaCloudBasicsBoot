<script src="js/jquery-3.1.1.min.js"></script> 
<script src="js/jquery-1.12.4.js"></script>
<link rel="stylesheet" type="text/css" href="semantic/semantic.min.css">
<script src="semantic/semantic.min.js"></script>
<script src="semantic/components/popup.min.js"></script>
<link rel="stylesheet" type="text/css" href="semantic/components/popup.min.css">
<script>
$(function() {

	$('.menu .browse')
	  .popup({
	    inline     : true,
	    hoverable  : true,
	    position   : 'bottom left',
	    delay: {
	      show: 300,
	      hide: 800
	    }
	  });
});

</script>

<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">

<head>
	<meta charset="utf-8">
	<title>Java Cloud Basics</title>
</head>

<div class="some-wrapping-div">
  <div class="ui custom button">Show custom popup</div>
</div>
<div class="ui custom popup top left transition hidden">
  I'm not on the same level as the button, but i can still be found.
</div>


<div class="ui menu">
  <a class="browse item">
    Browse
    <i class="dropdown icon"></i>
  </a>
  <div class="ui fluid popup bottom left transition hidden" style="top: 557.2px; left: 0.800018px; bottom: auto; right: auto; width: 584.2px;">
    <div class="ui four column relaxed divided grid">
      <div class="column">
        <h4 class="ui header">Fabrics</h4>
        <div class="ui link list">
          <a class="item">Cashmere</a>
          <a class="item">Linen</a>
          <a class="item">Cotton</a>
          <a class="item">Viscose</a>
        </div>
      </div>
      <div class="column">
        <h4 class="ui header">Size</h4>
        <div class="ui link list">
          <a class="item">Small</a>
          <a class="item">Medium</a>
          <a class="item">Large</a>
          <a class="item">Plus Sizes</a>
        </div>
      </div>
      <div class="column">
        <h4 class="ui header">Colors</h4>
        <div class="ui link list">
          <a class="item">Neutrals</a>
          <a class="item">Brights</a>
          <a class="item">Pastels</a>
        </div>
      </div>
      <div class="column">
        <h4 class="ui header">Types</h4>
        <div class="ui link list">
          <a class="item">Knitwear</a>
          <a class="item">Outerwear</a>
          <a class="item">Pants</a>
          <a class="item">Shoes</a>
        </div>
      </div>
    </div>
  </div>
  <a class="item">
    <i class="cart icon"></i>
    Checkout
  </a>
</div>
