<!DOCTYPE html>
<html>
   <head>
      <title>Earthquake Map</title>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <script src='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.js'></script>
      <link href='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.css' rel='stylesheet' />
      <link rel="stylesheet" type="text/css" href="stylesheet.css">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <script src='script.js'></script>   
   </head>
   <body>
      <header>
        <div class="nav">
            <h1>Earthquake-inator</h1>
            <div class="dropdown">
                <button class="dropbtn"><i class="fa fa-bars"></i></button>
                <nav>
                    <div class="dropdown-content">
                        <ul class="navigation">
                            <li><a href="tracker?cmd=home">Home</a></li>
                            <li><a href="tracker?cmd=map">Map</a></li>
                            <li><a href="tracker?cmd=alerts">Alerts</a></li>
                            <li><a href="tracker?cmd=about">About</a></li>
                        </ul>
                    </div>
                </nav>
            </div>
    </header>
      <main>
         <div id="container">
            <h2 id="map-title">Earthquake Map</h2>
            <div id='map'>
               <script>loadMap();</script>
            </div>
         </div>
      </main>
      <footer>
         <p class="company">orcas</p>
         <p id="copyright">Copyright &copy 2019</p>
      </footer>
   </body>
</html>