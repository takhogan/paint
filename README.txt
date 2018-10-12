Some functionality is incomplete at the moment (Futures trading)
there is a setup file for the sql server called paintsetup.sql in the resources folder
the easiest way to run this will be to put it in the webapps folder of a tomcat server
The project structure is borrowed from example projects on how2j.cn

Short explanation:
This is a cryptocurrency market simulator that supports currency trading, bond/margin trading, and futures (still needs implementing)
There is the opportunity to add AI players who will execute orders based on market supply & demand and also create new currencies randomly.
The execution logic is in PaintController.java
Database access method are in TransactionService.java