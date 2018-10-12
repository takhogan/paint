<%@page contentType="text/html; charset=utf-8" %>
<html>
<head>
    <link rel="stylesheet" href="css/main.css">
</head>
  <body>
  <div class = "th-header-black">
      <h1>My Home Page</h1>
  </div>
  <div class = main-container>
      <div class = "th-header">
          <h1>Welcome to Paint</h1>
      </div>
      <div class = left-content>
          <div class = box>
              <div class = "th-header">
                  <h1> Enter Username: </h1>
              </div>
              <div class = box>
                  <form method = "post" action = "http://localhost:9999/paint/portfolio">
                      <input type = "text" name = "username">
                      <input type = "submit" value = "Submit">
                  </form>
              </div>
          </div>
      </div>
      <div class = right-content>
          <div class = right-item>
              <div class = th-header-black>
                  tbd
              </div>
          </div>
      </div>
  </div>
  </body>
</html>

