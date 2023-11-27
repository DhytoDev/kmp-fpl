import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        InjectionKt.configureDi()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
