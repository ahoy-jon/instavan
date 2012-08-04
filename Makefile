


buildFolder:
	mkdir -p target

clean:
	rm -r target

cleanAndroid:
	rm -r target/android

emptyAndroid: cleanAndroid
	./phonegap2/lib/android/bin/create target/android org.instavan Instavan

android: emptyAndroid
	cp -f *.js target/android/assets/www/
	cp -f *.html target/android/assets/www/
	cp -f *.css target/android/assets/www/

androidBOOM: android
	./target/android/cordova/BOOM



