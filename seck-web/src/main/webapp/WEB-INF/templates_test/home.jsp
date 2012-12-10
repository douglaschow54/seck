<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>SECK Home</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">

        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/bootstrap-responsive.min.css">
        
        <!-- User Defined CSS -->
        <link rel="stylesheet" href="css/main.css">

        <script src="js/vendor/modernizr-2.6.1-respond-1.1.0.min.js"></script>
    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an outdated browser. <a href="http://browsehappy.com/">Upgrade your browser today</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to better experience this site.</p>
        <![endif]-->
        
        <div class="container-fluid">
        
        	<div class="row-fluid pagination-centered">
        		<div class="span12"><h1>SECK LOGO HERE</h1></div>
        	</div>
        	
        	<div class="row-fluid pagination-centered">
        		<div class="span12">
					<form class="form-search"  id="term" action="" method="get" >
					<div class="input-append">
					  <input name="user_input" type="text" class="input-xxlarge search-query" x-webkit-speech x-webkit-grammar="builtin:search" placeholder="Enter your search term...">
					  <button type="submit" class="btn"><i class="icon-search"></i> Search</button>
					  </div>
					</form>
				</div>
        	</div>
        
        </div>

        

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.8.2.min.js"><\/script>')</script>

        <script src="js/vendor/bootstrap.min.js"></script>

		<!-- User defined JS -->
        <script src="js/main.js"></script>
        <script type="text/javascript">
			function doSubmit(entryName, formName) {
				var theForm = document.getElementById(formName);
				if (formName == "") {
					formName = document.getElementById()
				}
				theForm.action = "/rest-test/" + entryName;
				theForm.submit();
			}

			

			

		</script>
        
    </body>
</html>