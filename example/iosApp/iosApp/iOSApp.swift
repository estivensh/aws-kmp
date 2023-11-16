import SwiftUI

@available(iOS 16.0, *)
@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
