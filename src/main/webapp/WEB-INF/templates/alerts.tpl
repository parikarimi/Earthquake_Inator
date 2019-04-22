<!DOCTYPE html>
<html>

<head>
    <title>Earthquake Map</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="stylesheet.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="script.js"></script>
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

        <div class="alertForm">
            <h2>Sign Up For Alerts</h2>
            <form action = "tracker?cmd=createMember" method = "post">
                <label>First Name</label><input placeholder="Jane" id="firstName" name = "fName">
                <label>Last Name</label><input placeholder="Smith" id="lastName" name = "lName">
                <label>Email</label><input placeholder="name@name.com" type="email" id="email" name = "emailAddress">
                <label>Phone Number</label><input placeholder="322-555-3433" type="tel" id="phone" name = "phone">
                <button class="submit" type="submit" onClick="showAlert()" >Submit</button>
            </form>
        </div>
    </main>
    <footer>
        <p class="company">orcas</p>
        <p id="copyright">Copyright &copy 2019</p>
    </footer>
</body>

</html>