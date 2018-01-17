#!/bin/bash
# Install kjcalc in a location that works for most Linux systems
EXEC=/usr/bin/kjcalc
SHARE=/usr/share/kjcalc
mkdir -p $SHARE 
cp target/kjcalc-0.0.2-jar-with-dependencies.jar $SHARE/kjcalc.jar
rm -f $EXEC
echo "#!/bin/bash" > $EXEC
echo java -jar $SHARE/kjcalc.jar \"\$\@\" >> $EXEC
chmod 755 $EXEC
