


buildFolder:
	mkdir -p target

clean:
	rm -r target

cleanAndroid:
	rm -rf target/android || echo "it's clean"

emptyAndroid: cleanAndroid
	./phonegap2/lib/android/bin/create target/android org.instavan Instavan

android:
	cp -fr www/* target/android/assets/www/




androidBOOM: android
	./target/android/cordova/BOOM

cleanIOS:
	rm -rf target/ios || echo "it's clean"

emptyIOS: cleanIOS
	./phonegap2/lib/ios/bin/create target/ios instavan Instavan

ios:
	cp -fr www/* target/ios/www/


