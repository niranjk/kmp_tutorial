import SwiftUI
import shared

@main
struct iOSApp: App {
    let appContainer = AppDiContainer(factory: DiFactory())
        var body: some Scene {
            WindowGroup {
                let iosViewModelOwner = IOSMainViewModelOwner(appContainer: appContainer)
                ContentView(mainViewModel: iosViewModelOwner.mainViewModel)
            }
        }
}
