import SwiftUI
import shared

struct ContentView: View {
    let timeZone = DateTimeHelperImpl().getCurrentTimeZoneId()
    
    
	var body: some View {
        let date = DateTimeHelperImpl().getDate(timezoneId:timeZone)
        Text(timeZone)
		Text(date)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
