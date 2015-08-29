###   MultiServer test task ###

A test solution with multi-purpose TCP/UDP servers including Echo, Time and Daytime service functions.

###   Building and running the application  ###

A maven tool is being used in order to build and run the application.
In order to make the whole process more convenient, a run.sh script has been created.
It contains compiling, packaging and launching the application.

###   Input parameters   ###
In order to assign different ports for included servers, up to 3 command line parameters can be used.
The first parameter will be assigned for an Echo server, 2nd - for a Time server and 3rd - for a Daytime server.
If there more than 3 parameters then the application exits with a corresponding message.