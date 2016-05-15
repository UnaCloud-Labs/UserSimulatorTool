# UserSimulatorTool

This tool loads CPU and RAM to desired percents for a period of time.

All the core source code is provided inside the Simulator folder. The RamTest folder is complementary.

## Instructions
In order to work, the Simulator class receives 3 filepaths by arguments.
* The first filepath specifies the location of the CPU load configuration file.
* The second filepath specifies the location of the RAM load configuration file.
* The third filepath specifies the location of the RamTest.jar that is needed in order for the RAM module to work (this .jar currently is stored in the dependency folder, the code is also provided in this repo).

The configuration files for both RAM and CPU load have the following layout:
* They consist of one or more rows.
* Each row in the file must be in the format: time,load
* For each row in the file the program will try to occupy the CPU/RAM to a value of ''load'' for the given ''time''.

##Example

If both the CPU and RAM configuration files only have the following row:
10,40

The simulator will launch a process that consumes 40% of the CPU for 10s.
It will also launch a series of processes that raises the RAM load in the machine to 40% for 10s.

##Notes
* This tool currently only works on Windows OS due to the way in which the RAM load module is implemented.
* If the current RAM load in the machine is greater than the specified load %, then the Simulator won't load RAM.
* As CPU load increases, the Simulator loses precision and starts to occupy greater or less percent than the specified in the configuration file.
* Example configuration files and additional explanation is given in the example folder inside Simulator.
