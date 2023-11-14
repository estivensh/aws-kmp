import SwiftUI
import shared

struct ContentView: View {
	var greet = "Hi"
    var exampleViewModel = ExampleViewModel()
    var now = ClockSystem().now().plus(value: 60, unit: DateTimeUnit.companion.HOUR)

	var body: some View {
		Text(greet)
            .onAppear {
                    exampleViewModel.generatePresignedUrl(bucketName: "bucket", key: "key.png", expiration: ClockSystem().now().plus(value: 60, unit: DateTimeUnit.companion.HOUR)
                )
            }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
