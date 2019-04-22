<!DOCTYPE html>
<html>
<head>
    <title>Earthquake Map</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="script.js"></script>
    <link rel="stylesheet" type="text/css" href="stylesheet.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
        <div class="about content">
            <h2>About the website</h2>
            <p>The Earthquake-inator is a fast, user friendly and powerful digital space that can be easily navigated by everyone interested in the shaking events around the Earth. See the current earthquake activity, share with your friends and get email alerts about affected area. 
            </p>
            <p>Earthquake-inator pulls live earthquake data from <a title="Link to external site" href="https://www.usgs.gov/">USGS</a>, and plots it on a dynamic map showing the earthquakes closest to you. </p>
        </div>
    </main>
    <footer>
            <p class="company">orcas</p>
            <p id="copyright">Copyright &copy 2019</p>
        </footer>
</body>

</html>