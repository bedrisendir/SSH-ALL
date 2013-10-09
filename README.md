clean:
ant clean

compile:
ant compile

run:
ant run

command line args hardcoded in build.xml
<target name="run" depends="jar">
    <java jar="${BUILD}/jar/SSHALL.jar" fork="true">
    <arg value="nodelist"/> /***** NODELIST FILE ****/
    <arg value="8"/> //***** NUMBER OF NODES *****/
</java>
