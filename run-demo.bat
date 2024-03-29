@echo off
REM Copyright (C) 2012 United States Government as represented by the Administrator of the
REM National Aeronautics and Space Administration.
REM All Rights Reserved.

REM Default to the ApplicationTemplate example if a class name is not provided
IF "%1"=="" (SET WWDEMO=com.mycompany.mavenworldwindsimple.HelloWorldWind) ELSE (SET WWDEMO=%*)

REM Run a WorldWind Demo
@echo Running %WWDEMO%
java -Xmx1024m -Dsun.java2d.noddraw=true -classpath .\mavenWorldWindSimple-1.0-SNAPSHOT.jar; %WWDEMO%
